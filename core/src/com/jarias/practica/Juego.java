package com.jarias.practica;

import com.badlogic.gdx.Game;
import com.jarias.practica.pantallas.PantallaSplash;

public class Juego extends Game {
	
	@Override
	public void create () {
		setScreen(new PantallaSplash());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
