package org.um.dke.titan.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class SpaceObject {
    public static final Label.LabelStyle GENERIC_LABEL_STYLE = new Label.LabelStyle(new BitmapFont(), Color.PINK);

    protected Label name;
    protected float mass;
    protected float radius;
    protected Vector3 position;
    protected Texture texture;
    protected float zoomLevel;

    public SpaceObject(String name, float mass, float radius, Vector3 position, float zoomLevel) {
        this.name = new Label(name, GENERIC_LABEL_STYLE);
        this.mass = mass;
        this.position = position;
        this.radius = radius;
        this.zoomLevel = zoomLevel;
    }

    public float getDiameter() {
        return this.radius * 2;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        Vector3 textPosition = new Vector3(this.position.x + getRadius(), this.position.y + getDiameter(), this.position.z);
        camera.project(textPosition);
        this.name.setPosition(textPosition.x, textPosition.y);

        batch.begin();

        if (texture != null) {
            batch.draw(this.texture, this.position.x, this.position.y, getDiameter(), getDiameter());
        }

        batch.end();
    }

    public Label getName() {
        return name;
    }

    public float getMass() {
        return mass;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setPosition(Vector3dInterface position) {
        this.position = new Vector3((float)position.getX(), (float)position.getY(), (float)position.getZ());
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

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getZ() {
        return this.position.z;
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
}
