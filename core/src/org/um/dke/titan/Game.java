package org.um.dke.titan;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import org.um.dke.titan.screens.LoadingScreen;

public class Game extends com.badlogic.gdx.Game {
	private final AssetManager assetManager = new AssetManager();

	@Override
	public void create() {
//		FactoryProvider.getGameRepository().load();
		setScreen(new LoadingScreen());
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
