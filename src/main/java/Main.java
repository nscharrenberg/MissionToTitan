import controllers.ControllerInterface;
import controllers.SolarSystemController;
import factory.FactoryProvider;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.utils.DrawingContext;
import gui.javafx.utils.DrawingDetail;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import physics.gravity.ODEFunction;
import physics.gravity.ODESolver;
import physics.gravity.State;
import repositories.SolarSystemRepository;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mission To Titan");
        stage.centerOnScreen();
        Group root = new Group();
        Scene scene = new Scene(root, 700, 700);
        DrawingContext drawingContext = new DrawingContext(root);
        drawingContext.changeCanvasSizeFromScene(scene);

        FactoryProvider.getDrawingManager().setContext(drawingContext);
//        FactoryProvider.getDrawingManager().init();
//        FactoryProvider.getDrawingManager().update();
        FactoryProvider.getSolarSystemFactory().init();
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
        //launch();

    }
}
