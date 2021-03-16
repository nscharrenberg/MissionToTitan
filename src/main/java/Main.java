import controllers.ControllerInterface;
import controllers.SolarSystemController;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.utils.DrawingContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mission To Titan");
        stage.centerOnScreen();
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500);
        DrawingContext drawingContext = new DrawingContext(root);
        drawingContext.changeCanvasSizeFromScene(scene);

        Circle circle = new Circle(50, new AbsolutePoint(100, 100));
        drawingContext.drawShape(circle);

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
}
