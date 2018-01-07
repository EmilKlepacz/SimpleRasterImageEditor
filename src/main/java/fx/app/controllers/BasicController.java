package fx.app.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class BasicController {

    protected Stage stage;
    protected List<Image> imageChanges;
    protected Image image;
    protected String originalImagePath;
    protected String temporaryImagePath;

    protected BasicController()
    {
        this.imageChanges = new ArrayList<>();
    }

    protected abstract void setStartImageInImageView(Image image);

    protected abstract void addChangesToImage(Image image);

    protected abstract void handleUndoAction();

    protected abstract void handleSaveAction();

    protected boolean saveTemporaryFile(Image image){
        try {
            File tmpFile = new File(temporaryImagePath);

            String extension = "";

            int i = tmpFile.getName().lastIndexOf('.');
            int p = Math.max(tmpFile.getName().lastIndexOf('/'), tmpFile.getName().lastIndexOf('\\'));

            if (i > p) {
                extension = tmpFile.getName().substring(i+1);
            }
            BufferedImage bi = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bi, extension, tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    protected void setPreviousImageAsActualAndErase(){
        if(imageChanges.size()>1) {
            this.image = imageChanges.get(imageChanges.size()-2);
            imageChanges.remove(imageChanges.size()-1);
        }
    }

    protected void addChangesToHistory(Image image)
    {
        this.image = image;
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

    public void setOriginalImagePath(String filePath){

        originalImagePath = filePath;
    }

    public String getOriginalImagePath() {
        return originalImagePath;
    }

    public String getTemporaryImagePath() {
        return temporaryImagePath;
    }

    public void setTemporaryImagePath(String temporaryImagePath) {
        this.temporaryImagePath = temporaryImagePath;
    }
}
