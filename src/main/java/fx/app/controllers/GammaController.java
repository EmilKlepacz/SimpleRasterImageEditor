package fx.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GammaController extends BasicController {
    @FXML
    private ImageView imageViewGamma;

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    void addChangesToImage(Image image) {
        imageViewGamma.setImage(image);
        addChangesToHistory(image);
    }

    public void undoActionForGammaController(){
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewGamma.setImage(image);
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }
}
