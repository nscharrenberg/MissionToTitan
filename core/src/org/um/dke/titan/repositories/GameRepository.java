package org.um.dke.titan.repositories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObject;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.repositories.interfaces.IGameRepository;
import org.um.dke.titan.utils.FileImporter;
import org.um.dke.titan.utils.VectorConverter;

import java.util.Map;

public class GameRepository implements IGameRepository {
    private Viewport viewport;
    private OrthographicCamera camera;

    private SpriteBatch batch;
    private SpaceObject toFollow;

    private boolean isFocussing;

    private Stage stage;

    private float CAMERA_ZOOM_SPEED = (float)5;
    private static final float MINIMUM_CAMERA_ZOOM = (float)1e5;
    private static final float CAMERA_MOVE_SPEED = (float)1000;

    private Label planetFocusLbl, cameraZoomLbl, cameraLbl, planetChooserLbl;

    @Override
    public void create() {
        FileImporter.load("data_20200401");

        this.camera = new OrthographicCamera();
        this.camera.zoom = (float)8e6;
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.batch = new SpriteBatch();
        this.stage = new Stage();

        this.toFollow = null;
        this.isFocussing = false;

        for (Planet object : FactoryProvider.getSolarSystemRepository().getPlanets().values()) {
            object.addActor(stage);
        }

        this.planetChooserLbl = new Label("(1) Sun (2) Earth (3) Jupiter (4) Saturn (5) Luna (6) Titan", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.planetChooserLbl.setPosition(15, Gdx.graphics.getHeight() - 25);
        this.cameraLbl = new Label(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z) , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.cameraLbl.setPosition(15, Gdx.graphics.getHeight() - 50);
        this.planetFocusLbl = new Label("Following: None", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.planetFocusLbl.setPosition(15, Gdx.graphics.getHeight() - 100);
        this.cameraZoomLbl = new Label("Zoom(Z/X): " + this.camera.zoom, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.cameraZoomLbl.setPosition(15, Gdx.graphics.getHeight() - 75);
        stage.addActor(planetChooserLbl);
        stage.addActor(planetFocusLbl);
        stage.addActor(cameraZoomLbl);
        stage.addActor(cameraLbl);

        // Start from Earth
        focusToPlanet(FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.EARTH.getName()));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        inputListener(Gdx.graphics.getDeltaTime());

        if(toFollow != null && !isFocussing) {
            follow();
        }

        batch.setProjectionMatrix(camera.combined);

        // TODO: Add Starry Night Background Image

        for (Planet object : FactoryProvider.getSolarSystemRepository().getPlanets().values()) {
            object.render(batch, camera);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();

        for (Planet object : FactoryProvider.getSolarSystemRepository().getPlanets().values()) {
            object.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void inputListener(float deltaTime) {
        if (!isFocussing) {
            uiControls(deltaTime);
        } else {
            focus(deltaTime);
            isFocussing = false;
        }

        followPlanet(deltaTime);

        camera.update();
    }

    private void uiControls(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            this.camera.zoom += CAMERA_ZOOM_SPEED * deltaTime * camera.zoom;
            cameraZoomLbl.setText("Zoom(Z/X): " + this.camera.zoom);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X) && this.camera.zoom > MINIMUM_CAMERA_ZOOM) {
            this.camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime * camera.zoom;
            cameraZoomLbl.setText("Zoom(Z/X): " + this.camera.zoom);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }
    }

    private void followPlanet(float deltaTime) {
        SpaceObject found = null;

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.EARTH.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.SUN.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.JUPITER.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.SATURN.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            found = FactoryProvider.getSolarSystemRepository().getMoonByName(SpaceObjectEnum.EARTH.getName(), SpaceObjectEnum.MOON.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            found = FactoryProvider.getSolarSystemRepository().getMoonByName(SpaceObjectEnum.SATURN.getName(), SpaceObjectEnum.TITAN.getName());
        }

        if (found != null) {
            focusToPlanet(found);
//            focus(deltaTime);
        }
    }

    private void unfollow() {
        this.toFollow = null;
        planetFocusLbl.setText("Following: None");
    }

    private void follow() {
        this.camera.position.x = (float)this.toFollow.getX();
        this.camera.position.y = (float)this.toFollow.getY();
        this.camera.update();

        cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
    }

    private void focusToPlanet(SpaceObject object) {
        this.isFocussing = true;
        this.toFollow = object;
        planetFocusLbl.setText(String.format("Following: %s", object.getName().getText().toString()));
    }

    private void focus(float deltaTime) {
        if (this.toFollow == null) {
            return;
        }

        this.camera.position.slerp(VectorConverter.convertToVector3(this.toFollow.getPosition()), deltaTime);
        this.camera.zoom = this.toFollow.getZoomLevel();

        cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        cameraZoomLbl.setText("Zoom(Z/X): " + this.camera.zoom);
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public SpaceObject getToFollow() {
        return toFollow;
    }

    @Override
    public void setToFollow(SpaceObject toFollow) {
        this.toFollow = toFollow;
    }

    @Override
    public boolean isFocussing() {
        return isFocussing;
    }

    @Override
    public void setFocussing(boolean focussing) {
        isFocussing = focussing;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public float getCAMERA_ZOOM_SPEED() {
        return CAMERA_ZOOM_SPEED;
    }

    @Override
    public void setCAMERA_ZOOM_SPEED(float CAMERA_ZOOM_SPEED) {
        this.CAMERA_ZOOM_SPEED = CAMERA_ZOOM_SPEED;
    }

    public static float getMinimumCameraZoom() {
        return MINIMUM_CAMERA_ZOOM;
    }

    public static float getCameraMoveSpeed() {
        return CAMERA_MOVE_SPEED;
    }

    @Override
    public Label getPlanetFocusLbl() {
        return planetFocusLbl;
    }

    @Override
    public void setPlanetFocusLbl(Label planetFocusLbl) {
        this.planetFocusLbl = planetFocusLbl;
    }

    @Override
    public Label getCameraZoomLbl() {
        return cameraZoomLbl;
    }

    @Override
    public void setCameraZoomLbl(Label cameraZoomLbl) {
        this.cameraZoomLbl = cameraZoomLbl;
    }

    @Override
    public Label getCameraLbl() {
        return cameraLbl;
    }

    @Override
    public void setCameraLbl(Label cameraLbl) {
        this.cameraLbl = cameraLbl;
    }
}
