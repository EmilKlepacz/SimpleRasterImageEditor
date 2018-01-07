package fx.app.controllers;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;


public abstract class BasicController {

    protected Stage stage;
    protected List<Image> imageChanges;
    protected Image image;
    protected String imagePath;

    abstract void setStartImageInImageView(Image image);

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<Image> getImageChanges() {
        return imageChanges;
    }

    public void setImageChanges(List<Image> imageChanges) {
        this.imageChanges = imageChanges;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImagePath(String filePath){

        imagePath = filePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
