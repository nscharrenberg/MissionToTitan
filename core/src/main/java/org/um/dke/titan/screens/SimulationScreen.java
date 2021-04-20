package org.um.dke.titan.screens;

import com.badlogic.gdx.ScreenAdapter;
import org.um.dke.titan.factory.FactoryProvider;

public class SimulationScreen extends ScreenAdapter {
    @Override
    public void show() {
        super.show();

        FactoryProvider.getGameRepository().create();
    }

    @Override
    public void render(float delta) {
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
