package org.um.dke.titan;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.um.dke.titan.domain.SpaceObject;

import java.util.HashMap;
import java.util.Map;

public class Game extends ApplicationAdapter {
	private Viewport viewport;
	private OrthographicCamera camera;

	private SpriteBatch batch;
	private SpaceObject earth, sun, moon;
	private Map<String, SpaceObject> objects;
	private SpaceObject toFollow;

	private boolean isFocussing;

	private Stage stage;

	private float CAMERA_ZOOM_SPEED = (float)5;
	private static final float MINIMUM_CAMERA_ZOOM = (float)1e5;
	private static final float CAMERA_MOVE_SPEED = (float)1000;

	@Override
	public void create() {
		earth = new SpaceObject("Earth", (float)5.97219e24, (float)6371e3, new Vector3((float)-1.471922101663588e+11,(float)-2.860995816266412e+10,0), (float) (6371e3/1e2));
		earth.setTexture("planets/Earth.png");

		sun = new SpaceObject("Sun", (float)1.988500e30, (float)696340e3, new Vector3((float)-6.806783239281648e+08,(float)1.080005533878725e+09,0), (float)8e6);
		sun.setTexture("planets/Sun.png");

		moon = new SpaceObject("Moon", (float)7.349e22, (float)1737.4e3, new Vector3((float)-1.472343904597218e+11,(float)-2.822578361503422e+10,0), (float) (6371e3/1e2));
		moon.setTexture("planets/Moon.png");

		this.objects = new HashMap<>();
		this.objects.put("Earth", earth);
		this.objects.put("Sun", sun);
		this.objects.put("Moon", moon);

		this.camera = new OrthographicCamera();
		this.camera.zoom = (float)8e6;
		this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		this.batch = new SpriteBatch();
		this.stage = new Stage();

		this.toFollow = null;
		this.isFocussing = false;

		for (SpaceObject object : this.objects.values()) {
			stage.addActor(object.getName());
		}
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

		for (SpaceObject object : this.objects.values()) {
			object.render(batch, camera);
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();

		for (SpaceObject object : this.objects.values()) {
			object.getTexture().dispose();
		}
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
			System.out.println(this.camera.zoom);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.X) && this.camera.zoom > MINIMUM_CAMERA_ZOOM) {
			this.camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime * camera.zoom;
			System.out.println(this.camera.zoom);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.position.y += CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
			this.toFollow = null;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.position.y -= CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
			this.toFollow = null;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.position.x -= CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
			this.toFollow = null;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.position.x += CAMERA_MOVE_SPEED * deltaTime* camera.zoom;
			this.toFollow = null;
		}
	}

	private void followPlanet(float deltaTime) {
		SpaceObject found = null;

		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			found = this.objects.get("Earth");
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			found = this.objects.get("Sun");
		} else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
			found = this.objects.get("Moon");
		}

		if (found != null) {
			focusToPlanet(found);
			focus(deltaTime);
		} else {
			System.out.println("not found");
		}
	}

	private void follow() {
		this.camera.position.x = this.toFollow.getX();
		this.camera.position.y = this.toFollow.getY();
		this.camera.update();
	}

	private void focusToPlanet(SpaceObject object) {
		this.isFocussing = true;
		this.toFollow = object;
	}

	private void focus(float deltaTime) {
		if (this.toFollow == null) {
			return;
		}

		this.camera.position.slerp(this.toFollow.getPosition(), deltaTime);
		this.camera.zoom = this.toFollow.getZoomLevel();
	}
}
