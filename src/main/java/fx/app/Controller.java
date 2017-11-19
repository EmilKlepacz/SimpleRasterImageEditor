package fx.app;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;


public class Controller {

    @FXML
    private MenuItem openFromFileItem;

    @FXML
    private ImageView imageView;

    private Stage stage;

    private void setImageFromFileInImageView(File file) throws MalformedURLException, FileNotFoundException {
        if (file != null) {
            String imageFilePath = file.toURI().toURL().toString();
            Image image = new Image(imageFilePath);
            imageView.setImage(image);
        } else throw new FileNotFoundException();
    }

    private void openFileChooserAndSetImage() throws MalformedURLException, FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Portable Map formats (*.pbm, *.pgm, *.ppm)", "*.pbm", "*.pgm", "*.ppm")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        setImageFromFileInImageView(selectedFile);
    }

    public void handleOpenFromFile() {
        try {
            openFileChooserAndSetImage();
        } catch (MalformedURLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
