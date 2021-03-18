import controllers.ControllerInterface;
import factory.FactoryProvider;
import gui.javafx.utils.DrawingContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

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

        System.out.println("DEBUG");
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

        Label frameIntervalLbl = new Label("Frame Interval:");
        TextField frameIntervalTxt = new TextField();
        frameIntervalTxt.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        frameIntervalTxt.setText(Integer.toString(FactoryProvider.getSettingRepository().getRefreshInterval()));

        CheckBox guiScalingTxt = new CheckBox("Enable GUI Scaling");
        guiScalingTxt.setSelected(FactoryProvider.getSettingRepository().isGuiFormatting());

        box.getChildren().addAll(frameIntervalLbl, frameIntervalTxt, guiScalingTxt);

        box.getChildren().add(new Text("Pre-Processing"));

        Label dayCountLbl = new Label("Days to calculate:");
        TextField dayCountTxt = new TextField();
        dayCountTxt.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        dayCountTxt.setText(Integer.toString(FactoryProvider.getSettingRepository().getDayCount()));

        Label yearCountLbl = new Label("Days to calculate:");
        TextField yearCountTxt = new TextField();
        yearCountTxt.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        yearCountTxt.setText(Integer.toString(FactoryProvider.getSettingRepository().getYearCount()));

        box.getChildren().addAll(dayCountLbl, dayCountTxt, yearCountLbl, yearCountTxt);

        Button saveBtn = new Button("Save & Restart");
        saveBtn.setOnAction((e) -> {
            FactoryProvider.getSettingRepository().setRefreshInterval(Integer.parseInt(frameIntervalTxt.getText()));
            FactoryProvider.getSettingRepository().setDayCount(Integer.parseInt(dayCountTxt.getText()));
            FactoryProvider.getSettingRepository().setYearCount(Integer.parseInt(yearCountTxt.getText()));
            FactoryProvider.getSettingRepository().setGuiFormatting(guiScalingTxt.selectedProperty().getValue());

            // TODO: Restart Pre-processing and simulation.
            FactoryProvider.getUpdateManager().stop();
            FactoryProvider.getUpdateManager().init();
        });

        box.getChildren().addAll(saveBtn);

        box.setSpacing(10);
        root.getChildren().add(box);

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
