package com.jarias.practica.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jarias.practica.caracteres.Nave;
import com.jarias.practica.caracteres.Enemigo;
import com.jarias.practica.caracteres.Disparo;
import com.jarias.practica.caracteres.Vida;
import com.jarias.practica.managers.R;

import java.sql.Time;

import static com.jarias.practica.caracteres.Nave.Estado.*;

public class PantallaJuego implements Screen {

    private Texture fondo;
    private SpriteBatch batch;
    private Nave nave;
    private Disparo disparo;
    private Array<Enemigo> enemigos;
    private long tiempoUltimoEnemigo = 0;
    private long ritmoEnemigos = 300;
    private Array<Vida> aVidas;
    private long tiempoUltimaVida = 0;
    private long ritmoVidas = 15000;
    private boolean estaPausado = false;
    private BitmapFont bitmapFont;
    private int puntos;
    private Music music;
    private boolean activado;

    public PantallaJuego(Music music, boolean activado){
        this.music = music;
        this.activado = activado;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        fondo = new Texture(Gdx.files.internal("core/assets/ui/fondo.png"));
        bitmapFont = new BitmapFont();
        puntos = 0;
        nave = new Nave(R.getTextura("spaceship_down"));
        enemigos = new Array<Enemigo>();
        aVidas = new Array<Vida>();

        tiempoUltimaVida = TimeUtils.millis();
        tiempoUltimoEnemigo = TimeUtils.millis();
    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            estaPausado = !estaPausado;
            music.pause();
        }

        if (!estaPausado) {
            nave.actualizar(dt);
            comprobarTeclado();

            generarEnemigos();
            generarVidas();
            comprobarBordes();

            for (Vida vida : aVidas) {
                vida.caer();
                if ((vida.posicion.y + vida.tamano.y) < 0) {
                    aVidas.removeValue(vida, true);
                }
                if (vida.rect.overlaps(nave.rect)) {
                    R.getSonido("core/assets/sounds/heal.mp3").play();
                    aVidas.removeValue(vida, true);
                    if (nave.vidas < 5) {
                        nave.vidas++;
                    }
                }
            }

            for (Enemigo enemigo : enemigos) {
                enemigo.arrollar();
                if ((enemigo.posicion.y + enemigo.tamano.y) < 0) {
                    enemigos.removeValue(enemigo, true);
                }
                if (enemigo.rect.overlaps(nave.rect)) {
                    enemigos.removeValue(enemigo, true);
                    if(nave.vidas > 1) {
                        R.getSonido("core/assets/sounds/bruh.mp3").play();
                        nave.vidas--;
                    }else {
                        R.getSonido("core/assets/sounds/death.mp3").play();
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaGameOver(music, activado));
                    }
                }
            }

            if (disparo != null) {
                disparo.mover();
                for(Enemigo enemigo : enemigos){
                    if (disparo.rect.overlaps(enemigo.rect)){
                        enemigos.removeValue(enemigo, true);
                        puntos += 100;
                        R.getSonido("core/assets/sounds/explosion.mp3").play();
                        disparo = null;
                        break;
                    }
                }
                if(disparo != null)
                    if (disparo.posicion.y > Gdx.graphics.getHeight())
                        disparo = null;
            }
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0);
        bitmapFont.draw(batch, "Puntuación: " + puntos, 5, Gdx.graphics.getHeight() - 10);
        bitmapFont.draw(batch, "Vidas: " + nave.vidas, 5, Gdx.graphics.getHeight() - 25);
        nave.pintar(batch);
        for (Vida vida : aVidas)
            vida.pintar(batch);
        for (Enemigo enemigo : enemigos)
            enemigo.pintar(batch);
        if (disparo != null)
            disparo.pintar(batch);
        batch.end();
    }

    private void generarEnemigos() {
        if ((TimeUtils.timeSinceMillis(tiempoUltimoEnemigo)) > ritmoEnemigos) {
            int posicionX = MathUtils.random(0, Gdx.graphics.getWidth() - Enemigo.ANCHURA);
            Enemigo enemigo = new Enemigo(R.getTextura("enemigo"), posicionX);
            enemigos.add(enemigo);
            tiempoUltimoEnemigo = TimeUtils.millis();
        }
    }

    private void generarVidas() {
        if ((TimeUtils.timeSinceMillis(tiempoUltimaVida)) > ritmoVidas) {
            int posicionX = MathUtils.random(0, Gdx.graphics.getWidth() - Vida.ANCHURA);
            Vida vida = new Vida(R.getTextura("vida"), posicionX);
            aVidas.add(vida);
            tiempoUltimaVida = TimeUtils.millis();
        }
    }

    private void comprobarBordes() {
        if (nave.posicion.x < 0)
            nave.posicion.x = 0;

        if ((nave.posicion.x + nave.tamano.x) > Gdx.graphics.getWidth())
            nave.posicion.x = Gdx.graphics.getWidth() - nave.tamano.x;
    }

    private void comprobarTeclado() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nave.mover(nave.velocidad);
            nave.estado = DERECHA;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nave.mover(new Vector2(-10, 0));
            nave.estado = IZQUIERDA;
        } else {
            nave.estado = QUIETO;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (disparo == null) {
                disparo = nave.disparar();
                R.getSonido("core/assets/sounds/pew.wav").play();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        nave.dispose();
    }
}
