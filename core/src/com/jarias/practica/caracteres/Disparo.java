package com.jarias.practica.caracteres;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Disparo extends Caracter {

    public Disparo(TextureRegion imagen) {
        super(imagen);
        velocidad = new Vector2(0,  11);
    }
}
