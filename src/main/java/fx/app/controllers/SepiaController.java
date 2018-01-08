package fx.app.controllers;


import fx.app.processing.ImageProcessorMarvin;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

import java.awt.image.BufferedImage;

public class SepiaController extends BasicController {

    @FXML
    ImageView imageViewSepia;

    private OpenFileController openFileController;

    @Override
    public void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    @Override
    protected void addChangesToImage(Image image) {
        imageViewSepia.setImage(image);
        addChangesToHistory(image);
        saveTemporaryFile(imageViewSepia.getImage());
    }

    public void undoActionForSepiaController(){
        handleUndoAction();
    }

    public void saveActionForSepiaController(){
        handleSaveAction(this.openFileController, imageViewSepia.getImage());
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewSepia.setImage(image);
        saveTemporaryFile(imageViewSepia.getImage());
    }
    public void sepia(){

        BufferedImage buff = SwingFXUtils.fromFXImage(image, null);
        MarvinImage sepiaMarvinImage = ImageProcessorMarvin.sepia(buff, getImageFormat());
        Image sepiaImage = SwingFXUtils.toFXImage(sepiaMarvinImage.getBufferedImage(), null);
        addChangesToImage(sepiaImage);
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }
}
