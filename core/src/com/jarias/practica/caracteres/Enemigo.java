package com.jarias.practica.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Enemigo extends Caracter {

    public final int VELOCIDAD_INICIAL = 5;
    public static final int ANCHURA = 64;

    public Enemigo(TextureRegion imagen, int posicionX) {
        super(imagen);
        posicion.y = Gdx.graphics.getHeight();
        posicion.x = posicionX;
        velocidad = new Vector2(0, -VELOCIDAD_INICIAL);
    }

    public void arrollar() {
        mover();
    }
}
