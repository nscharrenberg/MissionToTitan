import controllers.ControllerInterface;
import controllers.SolarSystemController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        SolarSystemController primaryController = new SolarSystemController();
        loader.setController(primaryController);

        stage.centerOnScreen();
        Pane flowPane = loader.load();
        scene = new Scene(flowPane, 500, 500);

        stage.setMaximized(true);
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
