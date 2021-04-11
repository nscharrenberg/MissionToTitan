package org.um.dke.titan;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.um.dke.titan.domain.SpaceObject;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
	public static final float WIDTH = 1524;
	public static final float HEIGHT = 864;
	private Viewport viewport;
	private OrthographicCamera camera;

	private SpriteBatch batch;
	private SpaceObject earth;
	private List<SpaceObject> objects;

	private Stage stage;

	private Texture backgroundImage;

	@Override
	public void create() {
		Texture texture = new Texture(Gdx.files.internal("planets/Earth.png"));
		texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		earth = new SpaceObject("Earth", (float)5.97219e24, (float)6371e3, new Vector3(0,0,0));
		earth.setTexture(texture);
		backgroundImage = new Texture(Gdx.files.internal("planets/starry-night-sky.png"));

		this.objects = new ArrayList<>();
		this.objects.add(earth);

		this.camera = new OrthographicCamera();
		this.camera.zoom = (float)4000e1;
		this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		this.batch = new SpriteBatch();
		this.stage = new Stage();

		for (SpaceObject object : this.objects) {
			stage.addActor(object.getName());
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();

		batch.setProjectionMatrix(camera.combined);

		// TODO: Add Starry Night Background Image

		for (SpaceObject object : this.objects) {
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

		for (SpaceObject object : this.objects) {
			object.getTexture().dispose();
		}
	}
}
