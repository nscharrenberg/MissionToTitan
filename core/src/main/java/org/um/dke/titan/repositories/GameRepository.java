package org.um.dke.titan.repositories;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.um.dke.titan.domain.*;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState;
import org.um.dke.titan.repositories.interfaces.IGameRepository;
import org.um.dke.titan.screens.LoadingScreen;

import java.util.Map;

public class GameRepository implements IGameRepository {
    private static int DEFAULT_SKIP_SPEED = 50;
    private static int DEFAULT_SKIP_SPEED_INCREMENT = 1;
    private boolean isGdx = true;
    private Game game = null;
    private Viewport viewport;
    private OrthographicCamera camera;

    private SpriteBatch batch;
    private SpaceObject toFollow;
    private boolean isFocussing;

    private Stage stage;

    private float CAMERA_ZOOM_SPEED = (float)5;
    private static final float MINIMUM_CAMERA_ZOOM = (float)5;
    private static final float CAMERA_MOVE_SPEED = (float)1000;

    private Label planetFocusLbl, cameraZoomLbl, cameraLbl, planetChooserLbl, speedLabel, timeLabel;

    private int timeToSkip = DEFAULT_SKIP_SPEED;
    private boolean paused = true;
    private int time = 0;


    @Override
    public void load() {
        FactoryProvider.getSolarSystemRepository().init();
        FactoryProvider.getSolarSystemRepository().preprocessing();
    }

    @Override
    public void create() {
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

        for (MovingObject object : FactoryProvider.getSolarSystemRepository().getRockets().values()) {
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
        this.speedLabel = new Label("Speed: Faster(P) or Slower (O) or default(I): " + this.timeToSkip, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.speedLabel.setPosition(15, Gdx.graphics.getHeight() - 125);
        this.timeLabel = new Label("Current Time: " + this.time, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeLabel.setPosition(15, Gdx.graphics.getHeight() - 150);

        stage.addActor(planetChooserLbl);
        stage.addActor(planetFocusLbl);
        stage.addActor(cameraZoomLbl);
        stage.addActor(cameraLbl);
        stage.addActor(speedLabel);
        stage.addActor(timeLabel);

        // Start from Earth
        focusToPlanet(FactoryProvider.getSolarSystemRepository().getRocketByName(SpaceObjectEnum.SHIP.getName()));
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

        int whoIsDone = 0;

        SystemState currentState = (SystemState) FactoryProvider.getSolarSystemRepository().getTimeLineArray()[time];

        for (Map.Entry<String, PlanetState> entry : currentState.getPlanets().entrySet()) {
            MovingObject found = FactoryProvider.getSolarSystemRepository().getPlanetByName(entry.getKey());

            if (found == null) {
                found = FactoryProvider.getSolarSystemRepository().getRocketByName(entry.getKey());
            }

            if (found == null) {
                throw new NullPointerException("Unable to find space object");
            }

            found.render(batch, camera);

            found.setPosition(entry.getValue().getPosition());
        }

        if (isPaused()) {
            return;
        }

        if (time >= 0) {
            time+=FactoryProvider.getGameRepository().getTimeToSkip();
            timeLabel.setText("Current Time: " + time);
        }

        if (time > FactoryProvider.getSolarSystemRepository().getTimeLineArray().length-1 || time < 0) {
            timeToSkip = DEFAULT_SKIP_SPEED;
            time = 0;
            timeLabel.setText("Current Time: " + time);

            FactoryProvider.getSolarSystemRepository().refresh();
            game.setScreen(new LoadingScreen());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();

        for (Planet object : FactoryProvider.getSolarSystemRepository().getPlanets().values()) {
            object.dispose();
        }

        for (MovingObject object : FactoryProvider.getSolarSystemRepository().getRockets().values()) {
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
        // Zoom In
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            this.camera.zoom += CAMERA_ZOOM_SPEED * deltaTime * camera.zoom;
            cameraZoomLbl.setText("Zoom(Z/X): " + this.camera.zoom);
        }

        // Zoom Out
        if (Gdx.input.isKeyPressed(Input.Keys.X) && this.camera.zoom > MINIMUM_CAMERA_ZOOM) {
            this.camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime * camera.zoom;
            cameraZoomLbl.setText("Zoom(Z/X): " + this.camera.zoom);
        }

        // Move Up
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        // Move Down
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        // Move Left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        // Move Right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
            unfollow();
            cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
        }

        // Pause / Resume day
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            paused = !paused;
            cameraZoomLbl.setText("Zoom(Z/X): " + this.camera.zoom + " Time on timeline: " + FactoryProvider.getSolarSystemRepository().getRocketByName(SpaceObjectEnum.SHIP.getName()).getTimeOnTimeLine());

        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            timeToSkip = timeToSkip + DEFAULT_SKIP_SPEED_INCREMENT;
            speedLabel.setText("Speed: Faster(P) or Slower (O) or default(I): " + this.timeToSkip);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            timeToSkip = timeToSkip - DEFAULT_SKIP_SPEED_INCREMENT;
            speedLabel.setText("Speed: Faster(P) or Slower (O) or default(I): " + this.timeToSkip);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            timeToSkip = DEFAULT_SKIP_SPEED;
            speedLabel.setText("Speed: Faster(P) or Slower (O) or default(I): " + this.timeToSkip);
        }
    }

    private void followPlanet(float deltaTime) {
        SpaceObject found = null;

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.MERCURY.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.SUN.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.VENUS.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.EARTH.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.MOON.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.MARS.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.JUPITER.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.SATURN.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            found = FactoryProvider.getSolarSystemRepository().getPlanetByName(SpaceObjectEnum.TITAN.getName());
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            found = FactoryProvider.getSolarSystemRepository().getRocketByName(SpaceObjectEnum.SHIP.getName());
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
        this.camera.position.x = (float) this.toFollow.getPosition().getX() + this.toFollow.getRadius();
        this.camera.position.y = (float) this.toFollow.getPosition().getY() + this.toFollow.getRadius();
        this.camera.update();

        cameraLbl.setText(String.format("Move (Arrow Keys): X(%s), Y(%s), Z(%s)", this.camera.position.x, this.camera.position.y, this.camera.position.z));
    }

    private void focusToPlanet(SpaceObject object) {
        this.isFocussing = true;
        this.toFollow = object;
        planetFocusLbl.setText(String.format("Following: %s", object.getName()));
    }

    private void focus(float deltaTime) {
        if (this.toFollow == null) {
            return;
        }

//        this.camera.position.slerp(VectorConverter.convertToVector3(this.toFollow.getPosition()), deltaTime);
        this.camera.position.x = (float) this.toFollow.getPosition().getX();
        this.camera.position.y = (float) this.toFollow.getPosition().getY();
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

    @Override
    public int getTimeToSkip() {
        return timeToSkip;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean isGdx() {
        return isGdx;
    }

    @Override
    public void setGdx(boolean gdx) {
        isGdx = gdx;
    }

    @Override
    public boolean isPaused() {
        return this.paused;
    }
}
