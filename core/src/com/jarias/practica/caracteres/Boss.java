package com.jarias.practica.caracteres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jarias.practica.managers.R;

public class Boss extends Caracter {

    public final int VELOCIDAD_INICIAL = 2;
    public int vidas;

    public Boss(TextureRegion imagen) {
        super(imagen);
        vidas = 10;
        posicion.set(Gdx.graphics.getWidth() / 2 - tamano.x / 2, Gdx.graphics.getHeight() - 160);
        velocidad = new Vector2(VELOCIDAD_INICIAL, 0);
        rect.height = 20;
    }

    public Disparo disparar() {
        Disparo disparo = new Disparo(R.getTextura("disparo_boss"));
        disparo.posicion.x = posicion.x + (tamano.x / 2) - disparo.tamano.x / 2;
        disparo.posicion.y = posicion.y + tamano.y;
        return disparo;
    }
}
