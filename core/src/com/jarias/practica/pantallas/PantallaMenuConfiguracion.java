package com.jarias.practica.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;

public class PantallaMenuConfiguracion implements Screen {

    private Stage stage;
    private Music music;
    private VisCheckBox cbMusica;
    private boolean activado;
    private Texture fondo;
    private VisSelectBox<String> sbDificultad;
    private Array<String> aDificultad;

    public PantallaMenuConfiguracion(Music music){
        this.music = music;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();
        fondo = new Texture(Gdx.files.internal("core/assets/ui/juego.jpg"));

        aDificultad = new Array<String>();
        aDificultad.add("Normal","Dificil");

        VisTable tabla = new VisTable();
        tabla.setFillParent(true);
        stage.addActor(tabla);

        cbMusica = new VisCheckBox("Música");
        if (music.isPlaying()) {
            cbMusica.setChecked(true);
            activado = false;
        }
        else {
            cbMusica.setChecked(false);
            activado = true;
        }

        cbMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!cbMusica.isChecked())
                    music.pause();
                else
                    music.play();
            }
        });

        sbDificultad = new VisSelectBox<String>();
        sbDificultad.setItems(aDificultad);

        VisTextButton btnAtras = new VisTextButton("Atrás");
        btnAtras.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal(activado));
                dispose();
            }
        });

        tabla.row();
        tabla.add(cbMusica).left().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(sbDificultad).center().width(200).height(25).pad(5);
        tabla.row();
        tabla.add(btnAtras).center().width(200).height(50).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act(dt);
        stage.draw();
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
        stage.dispose();
    }
}
