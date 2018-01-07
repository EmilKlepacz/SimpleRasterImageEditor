package fx.app.controllers;

import fx.app.processing.ImageProcessorMarvin;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvin.image.MarvinImage;

public class HistogramController extends BasicController {
    @FXML
    private ImageView imageViewHistogram;

    public void setImage(Image image) {
        this.image = image;
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        imageViewHistogram.setImage(image);
    }

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    void addChangesToImage(Image image) {
        imageViewHistogram.setImage(image);
        addChangesToHistory(image);
    }

    public void equalization(){
        MarvinImage histogramEqualImg = ImageProcessorMarvin.histogramEqualization(imagePath);
        Image histogramEqualImage = SwingFXUtils.toFXImage(histogramEqualImg.getBufferedImage(), null);
        imageViewHistogram.setImage(histogramEqualImage);
    }
}
