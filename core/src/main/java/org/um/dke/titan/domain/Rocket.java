package org.um.dke.titan.domain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Queue;

public class Rocket extends MovingObject {
    private float rotation = 0;

    public Rocket(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity) {
        super(name, mass, radius, position, zoomLevel, velocity);
    }

    public Rocket(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Queue<Vector3dInterface> timeline) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        Vector3 textPosition = new Vector3((float)this.position.getX() + getRadius(), (float)this.position.getY() + getDiameter(), (float)this.position.getZ());
        camera.project(textPosition);
        this.label.setPosition(textPosition.x, textPosition.y);

        batch.begin();

        if (texture != null) {
            float a = (float)((getDiameter()/texture.getHeight())*texture.getWidth()/2.0);
            float ab = (float)((getDiameter()-a)/2.0);
            batch.draw(new TextureRegion(this.texture), (float)(this.position.getX() - texture.getWidth()/2.0 + ab), (float)(this.position.getY() - texture.getHeight()/2.0), a, getRadius(), getDiameter(), getDiameter(), 1, 1, rotation);
            rotation++;
        }

        if(rotation >= 359)
            rotation = 0;

        batch.end();
    }
}
