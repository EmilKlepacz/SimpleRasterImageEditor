package fx.app.controllers;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GreyscaleController extends BasicController{

    @FXML
    private ImageView imageViewGreyscale;

    @Override
    void setStartImageInImageView(Image image) {
        imageViewGreyscale.setImage(image);
    }

    @Override
    void addChangesToImage(Image image) {
        imageViewGreyscale.setImage(image);
        addChangesToHistory(image);
    }

    public void greyscale(){

    }
}
