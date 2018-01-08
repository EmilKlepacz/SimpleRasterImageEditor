package fx.app.processing;

import marvin.gui.MarvinPluginWindow;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.statistic.MarvinHistogram;
import marvin.statistic.MarvinHistogramEntry;
import marvin.util.MarvinPluginLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessorMarvin {

    private static MarvinImage marvinImage;
    private static MarvinImagePlugin marvinImagePlugin;

    private static void setMarvinImage(String filePath) {
        marvinImage = MarvinImageIO.loadImage(filePath);
    }

    private static void setMarvinImage(BufferedImage bufferedImage, String formatName) {
        marvinImage = new MarvinImage(bufferedImage, formatName);
    }

    private static void setMarvinImagePlugin(String pluginPath) {
        marvinImagePlugin = MarvinPluginLoader.loadImagePlugin(pluginPath);
    }

    private static void processMarvinImageAndUpdate() {
        marvinImagePlugin.process(marvinImage, marvinImage);
        marvinImage.update();
    }

    public static MarvinImage invertImage(String filePath) {
        setMarvinImage(filePath);
        setMarvinImagePlugin("org.marvinproject.image.color.invert.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static MarvinImage histogramEqualization(String filePath) {
        setMarvinImage(filePath);
        setMarvinImagePlugin("org.marvinproject.image.equalization.histogramEqualization.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static MarvinImage edgeDetectorSobel(String filePath) {
        setMarvinImage(filePath);
        setMarvinImagePlugin("org.marvinproject.image.edge.edgeDetector.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static MarvinImage sepia(String filePath) {
        setMarvinImage(filePath);
        setMarvinImagePlugin("org.marvinproject.image.color.sepia.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    //@TODO przy dodawaniu nowych funkcji dublujcie je żeby przyjmowały też takie parametry jak poniżej
    public static MarvinImage invertImage(BufferedImage bufferedImage, String formatName) {
        setMarvinImage(bufferedImage, formatName);
        setMarvinImagePlugin("org.marvinproject.image.color.invert.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static MarvinImage histogramEqualization(BufferedImage bufferedImage, String formatName) {
        setMarvinImage(bufferedImage, formatName);
        setMarvinImagePlugin("org.marvinproject.image.equalization.histogramEqualization.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static MarvinImage edgeDetectorSobel(BufferedImage bufferedImage, String formatName) {
        setMarvinImage(bufferedImage, formatName);
        setMarvinImagePlugin("org.marvinproject.image.edge.edgeDetector.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static MarvinImage sepia(BufferedImage bufferedImage, String formatName) {
        setMarvinImage(bufferedImage, formatName);
        setMarvinImagePlugin("org.marvinproject.image.color.sepia.jar");

        processMarvinImageAndUpdate();
        return marvinImage;
    }

    public static void showColorHistogram(BufferedImage bufferedImage, String formatName) {
        setMarvinImage(bufferedImage, formatName);
        setMarvinImagePlugin("org.marvinproject.image.histogram.colorHistogram.jar");

        processMarvinImageAndUpdate();
    }

    public static void showGreyHistogram(BufferedImage bufferedImage, String formatName){
        setMarvinImage(bufferedImage, formatName);
        setMarvinImagePlugin("org.marvinproject.image.histogram.grayHistogram.jar");

        processMarvinImageAndUpdate();
    }

}
