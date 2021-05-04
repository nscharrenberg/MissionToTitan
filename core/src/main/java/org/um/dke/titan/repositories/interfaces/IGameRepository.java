package org.um.dke.titan.repositories.interfaces;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObject;

import java.util.Map;

public interface IGameRepository {
    void load();

    void create();

    void render();

    void dispose();

    void resize(int width, int height);

    Viewport getViewport();

    void setViewport(Viewport viewport);

    OrthographicCamera getCamera();

    void setCamera(OrthographicCamera camera);

    SpriteBatch getBatch();

    void setBatch(SpriteBatch batch);

    SpaceObject getToFollow();

    void setToFollow(SpaceObject toFollow);

    boolean isFocussing();

    void setFocussing(boolean focussing);

    Stage getStage();

    float getCAMERA_ZOOM_SPEED();

    void setCAMERA_ZOOM_SPEED(float CAMERA_ZOOM_SPEED);

    Label getPlanetFocusLbl();

    void setPlanetFocusLbl(Label planetFocusLbl);

    Label getCameraZoomLbl();

    void setCameraZoomLbl(Label cameraZoomLbl);

    Label getCameraLbl();

    void setCameraLbl(Label cameraLbl);

    int getTimeToSkip();

    Game getGame();

    void setGame(Game game);

    boolean isGdx();

    void setGdx(boolean gdx);

    boolean isPaused();
}
