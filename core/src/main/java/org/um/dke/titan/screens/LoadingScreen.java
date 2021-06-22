package org.um.dke.titan.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.um.dke.titan.Game;
import org.um.dke.titan.factory.FactoryProvider;

public class LoadingScreen extends ScreenAdapter {
    private final Game game = (Game) Gdx.app.getApplicationListener();
    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont textFont;
    private BitmapFont loadingFont;
    private Viewport viewport;
    private String titleText = "Mission to Titan";
    private String creditText = "Created by: \nJoaquin Monedero \nDino Pasic \nCarlo Peron \nFilip Rehburg \nDaan Schar \nNoah Scharrenberg";
    private String loadingText = "Loading";
    private GlyphLayout titleFontLayout = new GlyphLayout();
    private GlyphLayout creditFontLayout = new GlyphLayout();
    private GlyphLayout loadingFontLayout = new GlyphLayout();
    Texture texture;
    private int dotCount = 0;
    private static int maxDotCount = 3;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        texture = new Texture(Gdx.files.internal("splash.jpg"));
        createFonts();
        FactoryProvider.getSolarSystemRepository().init();
        loadGame();
    }

    private void createFonts() {
        FileHandle fontFile = Gdx.files.internal("fonts/Roboto-Black.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.RED;
        parameter.borderWidth = 0;
        parameter.size = 12;
        textFont = generator.generateFont(parameter);
        parameter.borderWidth = 5;
        parameter.size = 90;
        titleFont = generator.generateFont(parameter);
        parameter.borderWidth = 0;
        parameter.size = 30;
        loadingFont = generator.generateFont(parameter);
        generator.dispose();

        titleFontLayout.setText(titleFont, titleText);
        creditFontLayout.setText(textFont, creditText);
        loadingFontLayout.setText(loadingFont, loadingText);
    }
  
    private void loadGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FactoryProvider.getSolarSystemRepository().runPhysics();

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new SimulationScreen());
                    }
                });
            }
        }).start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        StringBuilder finalLoadingText = new StringBuilder(loadingText);
        for (int i = 0; i < dotCount; i++) {
            finalLoadingText.append(".");
        }

        dotCount++;

        if (dotCount > maxDotCount) {
            dotCount = 0;
        }

        loadingFontLayout.setText(loadingFont, finalLoadingText.toString());

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        titleFont.draw(batch, titleFontLayout, (Gdx.graphics.getWidth() - titleFontLayout.width) / 2, (Gdx.graphics.getHeight() + (titleFontLayout.height * 2)) / 2);
        textFont.draw(batch, creditFontLayout, (Gdx.graphics.getWidth() - titleFontLayout.width) / 2, ((Gdx.graphics.getHeight() + creditFontLayout.height) / 2) - titleFontLayout.height/2 - 30);
        loadingFont.draw(batch, loadingFontLayout, (Gdx.graphics.getWidth() + titleFontLayout.width - (loadingFontLayout.width * 2)) / 2, ((Gdx.graphics.getHeight() + loadingFontLayout.height) / 2) - titleFontLayout.height/2 + 10);
        batch.end();

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void dispose() {
        super.dispose();

        Gdx.app.exit();
    }
}
