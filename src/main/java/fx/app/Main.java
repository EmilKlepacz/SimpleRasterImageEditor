package fx.app;

import fx.app.controllers.OpenFileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/start_view.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Simple Raster Image Editor 1.0");
        primaryStage.setScene(new Scene(root));

        //pass stage to openFileController
        OpenFileController openFileController = loader.getController();
        openFileController.setStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
