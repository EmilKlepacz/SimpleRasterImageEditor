package fx.app.controllers;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FilteringController extends BasicController {

    @FXML
    ImageView imageViewFiltering;

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        imageViewFiltering.setImage(image);
    }
}
