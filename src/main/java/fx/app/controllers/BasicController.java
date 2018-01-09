package fx.app.controllers;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public abstract class BasicController{

    protected static final Logger logger = Logger.getLogger(BasicController.class);

    protected Stage stage;
    protected List<Image> imageChanges;
    protected Image image;
    protected String originalImagePath;

    protected BasicController()
    {
        this.imageChanges = new ArrayList<>();
    }

    protected abstract void setStartImageInImageView(Image image);

    protected abstract void addChangesToImage(Image image);

    protected abstract void handleUndoAction();

    protected void handleSaveAction(OpenFileController openFileController, Image image)
    {
        this.image = image;
        try {
            openFileController.addChangesToImage(image);
            openFileController.setImage(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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

    protected String getImageFormat(){
        return originalImagePath.substring(originalImagePath.lastIndexOf(".") + 1).trim();
    };

    public Stage getStage() { return stage; }

    public void setStage(Stage stage) { this.stage = stage; }

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

    public void setOriginalImagePath(String filePath){

        originalImagePath = filePath;
    }

    public String getOriginalImagePath() {
        return originalImagePath;
    }
}
