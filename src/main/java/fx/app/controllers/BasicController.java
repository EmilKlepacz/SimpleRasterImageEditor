package fx.app.controllers;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public abstract class BasicController {

    protected Stage stage;
    protected List<Image> imageChanges;
    protected Image image;
    protected String imagePath;

    public BasicController()
    {
        this.imageChanges = new ArrayList<>();
    }

    abstract void setStartImageInImageView(Image image);

    abstract void addChangesToImage(Image image);

    public abstract void handleUndoAction();

    protected void setPreviousImageAsActualAndErase(){
        if(imageChanges.size()>1) {
            this.image = imageChanges.get(imageChanges.size()-2);
            imageChanges.remove(imageChanges.size()-1);
        }
    }

    protected void addChangesToHistory(Image image)
    {
        if(imageChanges.size()>=10) {
            imageChanges.remove(0);
            imageChanges.add(image);
        } else {
            imageChanges.add(image);
        }
    }

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
