package org.um.dke.titan.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class SpaceObject {
    public static final Label.LabelStyle GENERIC_LABEL_STYLE = new Label.LabelStyle(new BitmapFont(), Color.PINK);

    protected String name;
    protected float mass;
    protected float radius;
    protected Vector3dInterface position;
    protected Texture texture;
    protected float zoomLevel;
    protected Label label;

    public SpaceObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel) {
        this.name = name;
        this.mass = mass;
        this.position = position;
        this.radius = radius;
        this.zoomLevel = zoomLevel;
    }

    public float getDiameter() {
        return this.radius * 2;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        Vector3 textPosition = new Vector3((float)this.position.getX() + getRadius(), (float)this.position.getY() + getDiameter(), (float)this.position.getZ());
        camera.project(textPosition);
        this.label.setPosition(textPosition.x, textPosition.y);

        batch.begin();

        if (texture != null) {
            batch.draw(this.texture, (float)this.position.getX(), (float)this.position.getY(), getDiameter(), getDiameter());
        }

        batch.end();
    }

    public void addActor(Stage stage) {
        this.label = new Label(name, GENERIC_LABEL_STYLE);
        stage.addActor(this.label);
    }

    public void dispose() {
        texture.dispose();
    }

    public String getName() {
        return name;
    }

    public float getMass() {
        return mass;
    }

    public Vector3dInterface getPosition() {
        return position;
    }

    public void setPosition(Vector3dInterface position) {
        this.position = position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public double getZ() {
        return this.position.getZ();
    }

    public void setTexture(String path) {
        Texture texture = new Texture(Gdx.files.internal(path));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        this.texture = texture;
    }

    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
