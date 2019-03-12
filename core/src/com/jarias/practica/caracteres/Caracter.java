package com.jarias.practica.caracteres;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class Caracter implements Disposable {

    public Vector2 posicion;
    public Vector2 velocidad;
    public Vector2 tamano;
    public TextureRegion imagen;
    public Rectangle rect;

    public Caracter(TextureRegion imagen) {
        this.imagen = imagen;
        tamano = new Vector2(imagen.getRegionWidth(), imagen.getRegionHeight());
        posicion = new Vector2(0, 0);
        rect = new Rectangle(posicion.x, posicion.y, tamano.x, tamano.y);
    }

    public void mover() {
        mover(velocidad);
    }

    public void mover(Vector2 movimiento) {
        posicion.add(movimiento);
        rect.x = posicion.x;
        rect.y = posicion.y;
    }

    public void pintar(SpriteBatch batch) {
        batch.draw(imagen, posicion.x, posicion.y);
    }

    @Override
    public void dispose() {

    }
}
