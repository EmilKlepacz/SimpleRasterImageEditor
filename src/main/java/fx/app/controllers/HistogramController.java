package fx.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HistogramController implements BasicController {
    @FXML
    private ImageView imageViewHistogram;

    private Image image;

    private String imagePath;

    public void setImage(Image image) {
        this.image = image;
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        imageViewHistogram.setImage(image);
    }

    @Override
    public void setImagePath(String filePath) {
        imagePath = filePath;
    }


}