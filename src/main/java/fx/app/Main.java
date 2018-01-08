package fx.app;

import fx.app.controllers.OpenFileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

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
        openFileController.getStage().setOnCloseRequest(event -> {
            try {
                if(openFileController.getTemporaryImagePath()!=null)
                    Files.delete(Paths.get(openFileController.getTemporaryImagePath()));
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", Paths.get(openFileController.getTemporaryImagePath()));
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", Paths.get(openFileController.getTemporaryImagePath()));
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
        });

        Image image = new Image(getClass().getResource("/images/logopwr_64x64.png").toExternalForm());
        primaryStage.getIcons().add(image);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
