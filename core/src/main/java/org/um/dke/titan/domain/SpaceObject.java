package org.um.dke.titan.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.FindMin;
import org.um.dke.titan.utils.RotationMatrix;

public class SpaceObject {
    public static final Label.LabelStyle GENERIC_LABEL_STYLE = new Label.LabelStyle(new BitmapFont(), Color.PINK);

    protected String name;
    protected float mass;
    protected float radius;
    protected Vector3dInterface position;
    protected Texture texture;
    protected float zoomLevel;
    protected Label label;
    private int rotation = 0;

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

        if(texture.toString().equals("planets/Probe.png")) {
            double posX = this.position.getX(), posY = this.position.getY();
            Vector3dInterface bottomLeft = new Vector3D(posX, posY, 0);
            posX += (this.texture.getWidth()/2.0);
            posY += (this.texture.getHeight()/2.0);
            Vector3dInterface center = new Vector3D(posX, posY, 0);
            Vector3dInterface tmpOffset = center.sub(bottomLeft);
            Vector3dInterface rotatedOffset = RotationMatrix.rotate(rotation, tmpOffset);
            Vector3dInterface offset = tmpOffset.sub(rotatedOffset);
            System.out.println("X:"+this.position.getX()+", Y:"+this.position.getY());
            float offsetX = (float)(offset.getX()), offsetY = (float)(offset.getY());
            System.out.println("xOff:"+offsetX+", yOff"+offsetY);
            //rotation is counterclockwise
            TextureRegion tr = new TextureRegion(this.texture);
            batch.draw(tr, (float)(this.position.getX()+offsetX), (float)(this.position.getY()+offsetY), 0, 0, getDiameter(), getDiameter(), 1, 1, -1*rotation);
            rotation++;
        }else if (texture != null) {
            TextureRegion tr = new TextureRegion(this.texture);
            batch.draw(tr, (float)(this.position.getX() - texture.getWidth()/2.0), (float)(this.position.getY() - texture.getHeight()/2.0), 0, 0, getDiameter(), getDiameter(), 1, 1, 0);
        }
        if(rotation >= 359)
            rotation = 0;

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
