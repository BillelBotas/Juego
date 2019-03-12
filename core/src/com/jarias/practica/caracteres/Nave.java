package com.jarias.practica.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jarias.practica.managers.R;

import static com.jarias.practica.caracteres.Nave.Estado.QUIETO;

public class Nave extends Caracter {

    public enum Estado {
        DERECHA, IZQUIERDA, QUIETO
    }

    public final int VELOCIDAD_INICIAL = 10;

    public int vidas;

    private Animation<TextureAtlas.AtlasRegion> animacionDerecha;
    private Animation<TextureAtlas.AtlasRegion> animacionIzquierda;
    public Estado estado;
    private float tiempo;

    public Nave(TextureRegion imagen) {
        super(imagen);
        vidas = 5;
        velocidad = new Vector2(VELOCIDAD_INICIAL, 0);

        posicion.set(Gdx.graphics.getWidth() / 2 - tamano.x / 2, 50);
        animacionDerecha = new Animation<TextureAtlas.AtlasRegion>(0.15f, R.getAnimacion("spaceship_right"));
        animacionIzquierda = new Animation<TextureAtlas.AtlasRegion>(0.15f, R.getAnimacion("spaceship_left"));
        estado = QUIETO;
        tiempo = 0;
    }

    public void actualizar(float dt) {
        tiempo += dt;

        switch (estado) {
            case DERECHA:
                imagen = animacionDerecha.getKeyFrame(tiempo, false);
                break;
            case IZQUIERDA:
                imagen = animacionIzquierda.getKeyFrame(tiempo, false);
                break;
            case QUIETO:
                imagen = R.getTextura("spaceship_down");
                break;
        }
    }

    public Disparo disparar() {
        Disparo disparo = new Disparo(R.getTextura("disparo"));
        disparo.posicion.x = posicion.x + (tamano.x / 2) - disparo.tamano.x / 2;
        disparo.posicion.y = posicion.y + tamano.y;
        return disparo;
    }
}
