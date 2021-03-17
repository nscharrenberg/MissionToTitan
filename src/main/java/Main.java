import controllers.ControllerInterface;
import controllers.SolarSystemController;
import domain.MovingObject;
import domain.Planet;
import factory.FactoryProvider;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.utils.DrawingContext;
import gui.javafx.utils.DrawingDetail;
import interfaces.StateInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import physics.gravity.ODEFunction;
import physics.gravity.ODESolver;
import physics.gravity.State;
import repositories.SolarSystemRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {
    private static Scene scene;
    


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mission To Titan");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.centerOnScreen();
        Group root = new Group();
        Scene scene = new Scene(root);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        FactoryProvider.getSettingRepository().setAppHeight(screenBounds.getHeight());
        FactoryProvider.getSettingRepository().setCanvasWidth(screenBounds.getWidth() - FactoryProvider.getSettingRepository().getSidebarWidth());

        DrawingContext drawingContext = new DrawingContext(root);
        drawingContext.changeCanvasSizeFromScene(scene);

        FactoryProvider.getDrawingManager().setContext(drawingContext);
        FactoryProvider.getUpdateManager().init();
        
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/icon/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml, ControllerInterface controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        loader.setController(controller);
        Pane flowPane = loader.load();
        scene.setRoot(flowPane);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
