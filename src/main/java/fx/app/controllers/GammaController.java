package fx.app.controllers;

import ij.ImagePlus;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class GammaController extends BasicController {

    @FXML
    private ImageView imageViewGamma;

    @FXML
    private Slider gammaCorrectionSlider;

    private OpenFileController openFileController;

    //@TODO podstawiac zmieniony obraz za pomoca tej funkcji
    @Override
    protected void addChangesToImage(Image image) {
        imageViewGamma.setImage(image);
        addChangesToHistory(image);
        saveTemporaryFile(imageViewGamma.getImage());
    }

    public void undoActionForGammaController(){
        handleUndoAction();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        imageViewGamma.setImage(image);
    }

    public void saveActionForGammaController(){
        handleSaveAction();
    }

    @Override
    protected void handleSaveAction() {
        try {
            openFileController.addChangesToImage(image);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // this image is copy of image in start_view
    @Override
    public void setStartImageInImageView(Image image) {
        addChangesToImage(image);
    }

    public OpenFileController getOpenFileController() {
        return openFileController;
    }

    public void setOpenFileController(OpenFileController openFileController) {
        if(this.openFileController == null)
            this.openFileController = openFileController;
    }

    public void gammaCorrection() throws IOException {

        ImageInputStream iis = ImageIO.createImageInputStream(new File(temporaryImagePath));
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {

            // pick the first available ImageReader
            ImageReader reader = readers.next();
            // attach source to the reader
            reader.setInput(iis, true);
            // read metadata of first image

            BufferedImage bi = reader.read(0);
            bi = gammaCorrection(bi, gammaCorrectionSlider.getValue());
            Image convertedGamma = SwingFXUtils.toFXImage(bi, null);
            addChangesToImage(convertedGamma);
        }
        iis.close();
    }

    private static BufferedImage gammaCorrection(BufferedImage original, double gamma) {

        int alpha, red, green, blue;
        int newPixel;

        double gamma_new = 1 / gamma;
        int[] gamma_LUT = gamma_LUT(gamma_new);

        BufferedImage gamma_cor = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {

                // Get pixels by R, G, B
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();

                red = gamma_LUT[red];
                green = gamma_LUT[green];
                blue = gamma_LUT[blue];

                // Return back to original format
                newPixel = colorToRGB(alpha, red, green, blue);

                // Write pixels into image
                gamma_cor.setRGB(i, j, newPixel);

            }

        }

        return gamma_cor;

    }

    // Create the gamma correction lookup table
    private static int[] gamma_LUT(double gamma_new) {
        int[] gamma_LUT = new int[256];

        for(int i=0; i<gamma_LUT.length; i++) {
            gamma_LUT[i] = (int) (255 * (Math.pow((double) i / (double) 255, gamma_new)));
        }

        return gamma_LUT;
    }

    // Convert R, G, B, Alpha to standard 8 bit
    private static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }
}
