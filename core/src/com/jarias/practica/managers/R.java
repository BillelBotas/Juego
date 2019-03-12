package com.jarias.practica.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

public class R {

    public static AssetManager assets = new AssetManager();

    public static void cargarRecursos() {
        assets.load("core/assets/atlas.pack.atlas", TextureAtlas.class);

        assets.load("core/assets/sounds" + File.separator + "bruh.mp3", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "pew.wav", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "explosion.mp3", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "death.mp3", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "heal.mp3", Sound.class);
        assets.load("core/assets/sounds" + File.separator + "music.mp3", Music.class);
    }

    public static boolean update() {
        return assets.update();
    }

    public static TextureRegion getTextura(String nombre) {
        return assets.get("core/assets/atlas.pack.atlas", TextureAtlas.class).findRegion(nombre);
    }

    public static Array<TextureAtlas.AtlasRegion> getAnimacion(String nombre) {
        return assets.get("core/assets/atlas.pack.atlas", TextureAtlas.class).findRegions(nombre);
    }

    public static Sound getSonido(String nombre) {
        return assets.get(nombre, Sound.class);
    }

    public static Music getMusica(String nombre) {

        return assets.get(nombre, Music.class);
    }

}
