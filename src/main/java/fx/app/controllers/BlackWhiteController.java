package fx.app.controllers;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlackWhiteController extends BasicController {
    @FXML
    ImageView imageViewBlackWhite;

    @Override
    void setStartImageInImageView(Image image) {
        imageViewBlackWhite.setImage(image);
    }

    @Override
    void addChangesToImage(Image image) {
        imageViewBlackWhite.setImage(image);
        addChangesToHistory(image);
    }

    public void blackAndWhite(){

    }
}
