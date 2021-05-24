package org.um.dke.titan.simulation;

import com.badlogic.gdx.assets.AssetManager;
import org.um.dke.titan.factory.FactoryProvider;

public class Game extends com.badlogic.gdx.Game {
    private final AssetManager assetManager = new AssetManager();

    public Game() {
    }

    public Game(boolean isGdx) {
        FactoryProvider.getGameRepository().setGdx(isGdx);
    }

    @Override
    public void create() {
        FactoryProvider.getGameRepository().setGame(this);
        setScreen(new StaticScreen());
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
