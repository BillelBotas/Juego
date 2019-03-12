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
import com.jarias.practica.managers.R;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class PantallaMenuPrincipal implements Screen {

    private Stage stage;
    private Music music;
    private boolean activado;

    public PantallaMenuPrincipal(){
        this.activado = true;
    }
    public PantallaMenuPrincipal(boolean activado){
        this.activado = activado;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        music = R.getMusica("core/assets/sounds/music.mp3");
        music.setLooping(true);
        music.setVolume(0.15f);
        if(activado)
            music.play();

        VisTable tabla = new VisTable();
        tabla.setFillParent(true);
        stage.addActor(tabla);

        VisTextButton btnJugar = new VisTextButton("Jugar");
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new FinalBoss(music, activado));
                dispose();
            }
        });

        VisTextButton btnOpciones = new VisTextButton("Opciones");
        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuConfiguracion(music));
                dispose();
            }
        });

        VisTextButton btnSalir = new VisTextButton("Salir");
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                System.exit(0);
            }
        });

        tabla.row();
        tabla.add(btnJugar).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btnOpciones).center().width(200).height(50).pad(5);
        tabla.row();
        tabla.add(btnSalir).left().width(200).height(50).pad(5);

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
