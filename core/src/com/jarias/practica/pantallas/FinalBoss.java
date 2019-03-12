package com.jarias.practica.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jarias.practica.caracteres.Boss;
import com.jarias.practica.caracteres.Disparo;
import com.jarias.practica.caracteres.Enemigo;
import com.jarias.practica.caracteres.Nave;
import com.jarias.practica.managers.R;

import static com.jarias.practica.caracteres.Nave.Estado.*;

public class FinalBoss implements Screen {

    private SpriteBatch batch;
    private Texture fondo;
    private Nave nave;
    private Boss boss;
    private boolean estaPausado = false;
    private Music music;
    private boolean activado;
    private BitmapFont bitmapFont;
    private int vidas;
    private Disparo disparo;
    private Disparo disparoBoss;

    public FinalBoss(Music music, boolean activado){
        this.music = music;
        this.activado = activado;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        vidas = 5;
        nave = new Nave(R.getTextura("spaceship_down"));
        boss = new Boss(R.getTextura("enemigofinal"));

        fondo = new Texture(Gdx.files.internal("core/assets/ui/fondo.png"));
    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            estaPausado = !estaPausado;
            music.pause();
        }

        if (!estaPausado) {
            moverBoss();

            nave.actualizar(dt);
            comprobarTeclado();

            comprobarBordes();

            if (disparo != null) {
                disparo.mover();
                if (disparo.rect.overlaps(boss.rect)){
                    boss.vidas--;
                    R.getSonido("core/assets/sounds/explosion.mp3").play();
                    disparo = null;
                }

                if(disparo != null)
                    if (disparo.posicion.y > Gdx.graphics.getHeight())
                        disparo = null;
            }

            if (disparoBoss != null) {
                disparoBoss.mover();
                if (disparoBoss.rect.overlaps(nave.rect)){
                    nave.vidas--;
                    R.getSonido("core/assets/sounds/bruh.mp3").play();
                    disparoBoss = null;
                }

                if(disparoBoss != null)
                    if (disparoBoss.posicion.y > Gdx.graphics.getHeight())
                        disparoBoss = null;
            }
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0);
        bitmapFont.draw(batch, "Vidas: " + nave.vidas, 5, Gdx.graphics.getHeight() - 10);
        nave.pintar(batch);
        boss.pintar(batch);
        if (disparo != null)
            disparo.pintar(batch);
        if (disparoBoss != null)
            disparoBoss.pintar(batch);
        batch.end();
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

    private void moverBoss() {
        boss.mover(boss.velocidad);
        if (boss.posicion.x < 0) {
            boss.velocidad.x = 2;
        }
        if ((boss.posicion.x + boss.tamano.x) > Gdx.graphics.getWidth()) {
            boss.velocidad.x = -2;
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
