package org.um.dke.titan;


import com.badlogic.gdx.ApplicationAdapter;
import org.um.dke.titan.factory.FactoryProvider;

public class Game extends ApplicationAdapter {
	@Override
	public void create() {
		FactoryProvider.getGameRepository().create();
	}

	@Override
	public void render() {
		FactoryProvider.getGameRepository().render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		FactoryProvider.getGameRepository().resize(width, height);
	}

	@Override
	public void dispose() {
		FactoryProvider.getGameRepository().dispose();
	}


}
