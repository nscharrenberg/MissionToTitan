import controllers.ControllerInterface;
import factory.FactoryProvider;
import gui.javafx.utils.DrawingContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Function;

public class Main extends Application {
    private static Scene scene;
    


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mission To Titan");
        HBox root = new HBox();
        Scene scene = new Scene(root);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        FactoryProvider.getSettingRepository().setAppHeight(screenBounds.getHeight());
        FactoryProvider.getSettingRepository().setCanvasWidth(screenBounds.getWidth() - FactoryProvider.getSettingRepository().getSidebarWidth());

        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.setResizable(false);
        stage.centerOnScreen();

        addControls(root);

        DrawingContext drawingContext = new DrawingContext(root);
        drawingContext.changeCanvasSizeFromScene(scene);

        FactoryProvider.getDrawingManager().setContext(drawingContext);
        FactoryProvider.getUpdateManager().init();
        
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/icon/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    private void addControls(Pane root) {
        VBox box = new VBox();
        box.setMinWidth(FactoryProvider.getSettingRepository().getSidebarWidth());
        Text title = new Text("Controls");
        box.setAlignment(Pos.TOP_CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font(18));
        box.getChildren().add(title);

        addTextField(box, "Frame Interval", Integer.toString(FactoryProvider.getSettingRepository().getRefreshInterval()));
        addCheckbox(box, "Enable GUI Scaling", FactoryProvider.getSettingRepository().isGuiFormatting());

        box.getChildren().add(new Text("Pre-Processing"));

        addTextField(box, "Days to calculate", Integer.toString(FactoryProvider.getSettingRepository().getDayCount()));
        addTextField(box, "Years to calculate", Integer.toString(FactoryProvider.getSettingRepository().getYearCount()));

        Button saveBtn = new Button("Save Settings");
        saveBtn.setOnAction((e) -> {
            TextField frameIntervalTxt = (TextField)box.lookup("Frame Interval");
            CheckBox guiScalingTxt = (CheckBox) box.lookup("Enable GUI Scaling");
            TextField dayCountTxt = (TextField)box.lookup("Days to calculate");
            TextField yearCountTxt = (TextField)box.lookup("Years to calculate");

            System.out.println(FactoryProvider.getSettingRepository().getRefreshInterval());

            FactoryProvider.getSettingRepository().setRefreshInterval(Integer.parseInt(frameIntervalTxt.getText()));
            FactoryProvider.getSettingRepository().setDayCount(Integer.parseInt(dayCountTxt.getText()));
            FactoryProvider.getSettingRepository().setYearCount(Integer.parseInt(yearCountTxt.getText()));
            FactoryProvider.getSettingRepository().setGuiFormatting(guiScalingTxt.selectedProperty().getValue());

            System.out.println(FactoryProvider.getSettingRepository().getRefreshInterval());
        });

        box.getChildren().add(saveBtn);

        box.setSpacing(10);
        root.getChildren().add(box);

    }

    private void addCheckbox(Pane root, String labelText, boolean initValue) {
        CheckBox box = new CheckBox(labelText);
        box.setId(labelText);
        box.setSelected(initValue);
        root.getChildren().add(box);
    }


    private void addTextField(Pane root, String labelTxt, String initValue) {
        Label label = new Label(labelTxt);
        TextField textField = new TextField();
        textField.setId(labelTxt);
        textField.setText(initValue);
        root.getChildren().addAll(label, textField);
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
