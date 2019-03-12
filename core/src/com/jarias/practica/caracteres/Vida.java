package com.jarias.practica.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jarias.practica.managers.R;

public class Vida extends Caracter {

    private Animation<TextureAtlas.AtlasRegion> animacionVida;

    public final int VELOCIDAD_INICIAL = 5;
    public static final int ANCHURA = 64;

    public Vida(TextureRegion imagen, int posicionX) {
        super(imagen);
        posicion.y = Gdx.graphics.getHeight();
        posicion.x = posicionX;
        velocidad = new Vector2(0, -VELOCIDAD_INICIAL);

        animacionVida = new Animation<TextureAtlas.AtlasRegion>(0.15f, R.getAnimacion("vida"));
    }

    public void caer() {
        mover();
    }
}
