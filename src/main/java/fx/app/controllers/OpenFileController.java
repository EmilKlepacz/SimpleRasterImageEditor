package fx.app.controllers;

import fx.app.processing.ImageProcessorMarvin;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class OpenFileController extends BasicController {

    private static final String GAMMA_VIEW_PATH = "/views/gamma_view.fxml";
    private static final String GAMMA_STAGE_TITLE = "Gamma correction";
    private static final String NEGATIVE_VIEW_PATH = "/views/negative_view.fxml";
    private static final String NEGATIVE_STAGE_TITLE = "Negative operation";
    private static final String FILTERING_VIEW_PATH = "/views/filtering_view.fxml";
    private static final String FILTERING_STAGE_TITLE = "Filtering";
    private static final String HISTOGRAM_VIEW_PATH = "/views/histogram_view.fxml";
    private static final String HISTOGRAM_STAGE_TITLE = "Histogram based operations";
    private static final String GEOMETRIC_VIEW_PATH = "/views/geometric_view.fxml";
    private static final String GEOMETRIC_STAGE_TITLE = "Geometric transformations";
    private static final String GREYSCALE_VIEW_PATH = "/views/greyscale_view.fxml";
    private static final String GREYSCALE_STAGE_TITLE = "Greyscale";
    private static final String BLACK_AND_WHITE_VIEW_PATH = "/views/black_and_white_view.fxml";
    private static final String BLACK_AND_WHITE_STAGE_TITLE = "Black and White";
    private static final String SEPIA_VIEW_PATH = "/views/sepia_view.fxml";
    private static final String SEPIA_STAGE_TITLE = "Sepia";

    private static final int MIN_SPINNER_WIDTH_VAL = 1;
    private static final int MAX_SPINNER_WIDTH_VAL = 100000;
    private static final int ON_START_SPINNER_WIDTH_VAL = 0;
    private static final int MIN_SPINNER_HEIGHT_VAL = 1;
    private static final int MAX_SPINNER_HEIGHT_VAL = 100000;
    private static final int ON_START_SPINNER_HEIGHT_VAL = 0;
    private String fileInformation = "";
    private String fileSize = "";
    private String URLPath = "";

    @FXML
    private Button gammaBtn;
    @FXML
    private Button negativeBtn;
    @FXML
    private Button filteringBtn;
    @FXML
    private Button histogramBtn;
    @FXML
    private Button geometricBtn;
    @FXML
    private ImageView imageView;
    @FXML
    private Button greyscaleBtn;
    @FXML
    private Button blackWhiteBtn;
    @FXML
    private Button sepiaBtn;
    @FXML
    private MenuItem colorHistogramItem;
    @FXML
    private MenuItem greyHistogramItem;

    @FXML
    private MenuItem fileSaveItem;

    private void setOperationButtons(){
        if (image == null) {
            disableOperationButtons();
        }else{
            enableOperationButtons();
        }
    }

    private void disableOperationButtons() {
        gammaBtn.setDisable(true);
        negativeBtn.setDisable(true);
        filteringBtn.setDisable(true);
        histogramBtn.setDisable(true);
        geometricBtn.setDisable(true);
        greyscaleBtn.setDisable(true);
        blackWhiteBtn.setDisable(true);
        sepiaBtn.setDisable(true);
    }

    private void enableOperationButtons() {
        gammaBtn.setDisable(false);
        negativeBtn.setDisable(false);
        filteringBtn.setDisable(false);
        histogramBtn.setDisable(false);
        geometricBtn.setDisable(false);
        greyscaleBtn.setDisable(false);
        blackWhiteBtn.setDisable(false);
        sepiaBtn.setDisable(false);
    }

    private void enableSaveMenuItems(){
        fileSaveItem.setDisable(false);
    }

    private void disableSaveMenuItems(){
        fileSaveItem.setDisable(true);
    }
    private void setSaveMenuItems() {
        if (image == null) {
            disableSaveMenuItems();
        }else{
            enableSaveMenuItems();
        }
    }

    private void enableHistogramMenuItems(){
        colorHistogramItem.setDisable(false);
        greyHistogramItem.setDisable(false);
    }

    private void disableHistogramMenuItems(){
        colorHistogramItem.setDisable(true);
        greyHistogramItem.setDisable(true);
    }
    private void setHistogramMenuItems() {
        if (image == null) {
            disableHistogramMenuItems();
        }else{
            enableHistogramMenuItems();
        }
    }

    //method return image Path of image in ImageView
    @Override
    protected void setStartImageInImageView(Image image) {
        this.image = image;
        addChangesToImage(image);
    }


    @Override
    protected void addChangesToImage(Image image) {
        imageView.setImage(image);
        addChangesToHistory(image);
    }

    public void undoActionForOpenFileController() {
        handleUndoAction();
        setOperationButtons();
        setHistogramMenuItems();
        setSaveMenuItems();
    }

    @Override
    public void handleUndoAction() {
        setPreviousImageAsActualAndErase();
        if(image!=null)
            imageView.setImage(image);
    }

    public void saveActionForOpenFileController() {

        File originalFile = new File(originalImagePath);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(originalFile.getParentFile());
        fileChooser.setInitialFileName(originalFile.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("formats (*.pbm, *.pgm, *.ppm, *.png, *.jpeg)", "*.pbm", "*.pgm", "*.ppm", "*.png", "*.jpeg")
        );


        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            saveFile(saveFile);
        }
    }

    private boolean saveFile(File file) {
        try {
            // retrieve image

            String extension = "";

            int i = file.getName().lastIndexOf('.');
            int p = Math.max(file.getName().lastIndexOf('/'), file.getName().lastIndexOf('\\'));

            if (i > p) {
                extension = file.getName().substring(i + 1);
            }
            BufferedImage bi = SwingFXUtils.fromFXImage(this.imageView.getImage(), null);
            ImageIO.write(bi, extension, file);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            setAlertIcon(alert);
            alert.setTitle("Error");
            alert.setHeaderText("File saving error:");
            if (e.getMessage().equals(""))
                alert.setContentText("Unable to load this file! ");
            else
                alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
        return true;
    }

    private void setImageFromFileInImageView(ImageReader reader) throws IOException {

        BufferedImage bi = reader.read(0);
        setStartImageInImageView(SwingFXUtils.toFXImage(bi, null));
        setOperationButtons();
        setHistogramMenuItems();
        setSaveMenuItems();
    }

    private void openFileChooserAndSetImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(getClass().getClassLoader().getResource("images").getPath()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("formats (*.pbm, *.pgm, *.ppm, *.png, *.jpeg, *.jpg, *.gif)", "*.pbm", "*.pgm", "*.ppm", "*.png", "*.jpeg", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            processImage(selectedFile);
            logger.info(String.format("File %s loaded successfull!", selectedFile.getName()));
        }
    }

    public void handleOpenFromFile() {
        try {
            fileInformation = "No metadata to display";
            openFileChooserAndSetImage();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            setAlertIcon(alert);
            alert.setTitle("Error");
            alert.setHeaderText("File loading error:");
            if (e.getMessage().equals(""))
                alert.setContentText("Unable to load this file! ");
            else
                alert.setContentText(e.getMessage());
            alert.showAndWait();
            logger.error(e);
        }
    }

    private void handleOpenFromURL(String url) throws IOException {
        fileInformation = "No metadata to display";
        URLPath = url;
        BufferedImage img = ImageIO.read(new URL(url));
        String extension = url.substring(url.lastIndexOf(".") + 1).trim();
        File f = new File("temp." + extension);
        ImageIO.write(img, extension, f);

        if (f.length() < 5) {
            URLError();
        } else {
            processImage(f);
            logger.info(String.format("File %s from %s loaded successfull!", f.getName(), url));
        }
    }

    public void handleOpenFileInformation() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        setAlertIcon(alert);


        if (originalImagePath != null) {
            String fileName = originalImagePath.substring(originalImagePath.lastIndexOf("\\") + 1).trim();
            alert.setTitle(fileName);
        } else
            alert.setTitle("Load file first!");
        if (alert.getTitle().contains("temp"))
            alert.setTitle(URLPath);
        alert.setHeaderText(fileSize);
        if (fileInformation.equals("")) {
            alert.setContentText("No file loaded");
            alert.getDialogPane().setPrefSize(200, 100);
        } else if (fileInformation.equals("No metadata to display")) {
            alert.setContentText(fileInformation);
            alert.getDialogPane().setPrefSize(300, 100);
        } else if (fileInformation.contains("JPEG")) {
            alert.setContentText(fileInformation);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(400, 490);
        } else {
            alert.setContentText(fileInformation);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(400, 700);
        }

        alert.showAndWait();
    }

    public void handleOpenAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        setAlertIcon(about);
        about.setTitle("Version 1.0");
        about.setHeaderText("Simple Raster Image Editor");
        about.setContentText("AUTHORS:\nPaweł Rymer\nEmil Klepacz\nJakub Nyćkowiak\n\nLIBRARIES USED:\nImageJ\nMarvin\nmmscomputing");

        Image image = new Image(getClass().getResource("/images/logopwr_64x64.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        about.setGraphic(imageView);

        about.showAndWait();
    }


    public void openGammaWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GAMMA_VIEW_PATH));
            Parent root = loader.load();

            //set start image in Gamma View as copy of loaded image
            //by GammaController before showing window
            GammaController gammaController = loader.getController();
            gammaController.setImage(image);
            gammaController.setOriginalImagePath(originalImagePath);
            gammaController.setStartImageInImageView(image);
            gammaController.setOpenFileController(this);

            createAndShowNewStage(GAMMA_STAGE_TITLE, new Scene(root));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSepiaWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SEPIA_VIEW_PATH));
            Parent root = loader.load();

            //set start image in Gamma View as copy of loaded image
            //by GammaController before showing window
            SepiaController sepiaController = loader.getController();
            sepiaController.setImage(image);
            sepiaController.setOriginalImagePath(originalImagePath);
            sepiaController.setStartImageInImageView(image);
            sepiaController.setOpenFileController(this);

            createAndShowNewStage(SEPIA_STAGE_TITLE, new Scene(root));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //set Image and ImagePath in new window
    public void openNegativeWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(NEGATIVE_VIEW_PATH));
            Parent root = loader.load();

            NegativeController negativeController = loader.getController();
            negativeController.setImage(image);
            negativeController.setOriginalImagePath(originalImagePath);
            negativeController.setStartImageInImageView(image);
            negativeController.setOpenFileController(this);

            createAndShowNewStage(NEGATIVE_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFilteringWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FILTERING_VIEW_PATH));
            Parent root = loader.load();

            FilteringController filteringController = loader.getController();
            filteringController.setImage(image);
            filteringController.setOriginalImagePath(originalImagePath);
            filteringController.setStartImageInImageView(image);
            filteringController.setOpenFileController(this);
            filteringController.saveActionForFilteringController();

            createAndShowNewStage(FILTERING_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openHistogramWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(HISTOGRAM_VIEW_PATH));
            Parent root = loader.load();

            HistogramController histogramController = loader.getController();
            histogramController.setImage(image);
            histogramController.setOriginalImagePath(originalImagePath);
            histogramController.setStartImageInImageView(image);
            histogramController.setOpenFileController(this);

            createAndShowNewStage(HISTOGRAM_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openGeometricWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GEOMETRIC_VIEW_PATH));
            Parent root = loader.load();

            GeometricController geometricController = loader.getController();
            geometricController.setImage(image);
            geometricController.setOriginalImagePath(originalImagePath);
            geometricController.setStartImageInImageView(image);
            geometricController.setOpenFileController(this);

            geometricController.setWidthSpinnerValue(MIN_SPINNER_WIDTH_VAL, MAX_SPINNER_WIDTH_VAL, ON_START_SPINNER_WIDTH_VAL);
            geometricController.setHeightSpinnerValue(MIN_SPINNER_HEIGHT_VAL, MAX_SPINNER_HEIGHT_VAL, ON_START_SPINNER_HEIGHT_VAL);

            geometricController.setTextInHeightValText(String.valueOf((int) image.getHeight()));
            geometricController.setTextInWidthValText(String.valueOf((int) image.getHeight()));

            createAndShowNewStage(GEOMETRIC_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGreyscaleWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(GREYSCALE_VIEW_PATH));
            Parent root = loader.load();

            GreyscaleController greyscaleController = loader.getController();
            greyscaleController.setImage(image);
            greyscaleController.setOriginalImagePath(originalImagePath);
            greyscaleController.setStartImageInImageView(image);
            greyscaleController.setOpenFileController(this);

            createAndShowNewStage(GREYSCALE_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openBlackWhiteWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(BLACK_AND_WHITE_VIEW_PATH));
            Parent root = loader.load();

            BlackWhiteController blackWhiteController = loader.getController();
            blackWhiteController.setImage(image);
            blackWhiteController.setOriginalImagePath(originalImagePath);
            blackWhiteController.setStartImageInImageView(image);
            blackWhiteController.setOpenFileController(this);

            createAndShowNewStage(BLACK_AND_WHITE_STAGE_TITLE, new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUrlWindow() {
        TextInputDialog dialog = new TextInputDialog();
        setAlertIcon(dialog);
        dialog.setTitle("Load from url");
        dialog.setHeaderText("Look, put your url to file below");
        dialog.setContentText("Please enter file url:");

        Image image = new Image(getClass().getResource("/images/url.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        dialog.setGraphic(imageView);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                handleOpenFromURL(dialog.getEditor().getCharacters().toString());
            } catch (Exception e) {
                URLError();
            }
        }
    }

    private void URLError() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        setAlertIcon(errorAlert);
        errorAlert.setContentText("WRONG URL WAS USED!");
        errorAlert.showAndWait();
        openUrlWindow();
    }

    private void createAndShowNewStage(String stageTitle, Scene scene) {
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        Image image = new Image(getClass().getResource("/images/logopwr_64x64.png").toExternalForm());
        stage.getIcons().add(image);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void processImage(File originalFile) throws IOException {

            double bytes = originalFile.length();
            fileSize = "File Size: " + String.format("%.2f", bytes / 1024) + "kb";

            ImageIO.scanForPlugins();
            ImageInputStream iis = ImageIO.createImageInputStream(originalFile);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();
                // attach source to the reader
                reader.setInput(iis, true);
                // read metadata of first image

                readAndDisplayMetadata(reader);

                setImageFromFileInImageView(reader);

                originalImagePath = originalFile.getAbsolutePath();
            }
    }

    private void readAndDisplayMetadata(ImageReader reader) throws IOException {
        IIOMetadata metadata = reader.getImageMetadata(0);
        if (metadata != null) {
            String[] names = metadata.getMetadataFormatNames();
            for (String name : names) {
                fileInformation = "Format name: " + name + "\n";
                displayMetadata(metadata.getAsTree(name));
            }
        }
    }

    private void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    private void indent(int level) {
        for (int i = 0; i < level; i++)
            fileInformation += ("    ");
    }

    private void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        fileInformation += "<" + node.getNodeName();
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                fileInformation += " " + attr.getNodeName() +
                        "=\"" + attr.getNodeValue() + "\"";
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            fileInformation += "/>\n";
            return;
        }

        // children, so close current tag
        fileInformation += ">\n";
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        fileInformation += "</" + node.getNodeName() + ">\n";
    }

    public List<Image> getImageChanges() {
        return imageChanges;
    }

    public void setImageChanges(List<Image> imageChanges) {
        this.imageChanges = imageChanges;
    }

    public void showColorHistogram() {
        BufferedImage buff = SwingFXUtils.fromFXImage(image, null);
        ImageProcessorMarvin.showColorHistogram(buff, getImageFormat());
    }

    public void showGreyHistogram() {
        BufferedImage buff = SwingFXUtils.fromFXImage(image, null);
        ImageProcessorMarvin.showGreyHistogram(buff, getImageFormat());
    }

    public void cleanImage(){
        if(image!=null) {
            image = null;
            addChangesToImage(new Image("/images/default_start_img.gif"));
            setOperationButtons();
            setHistogramMenuItems();
            setSaveMenuItems();
        }

    }

    private void setAlertIcon(Alert alert){
        Stage alertIcon;
        alertIcon = (Stage) alert.getDialogPane().getScene().getWindow();
        alertIcon.getIcons().add(new Image(getClass().getResource("/images/logopwr_64x64.png").toExternalForm())); // To add an icon
    }

    private void setAlertIcon(TextInputDialog dialog){
        Stage dialogIcon;
        dialogIcon = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogIcon.getIcons().add(new Image(getClass().getResource("/images/logopwr_64x64.png").toExternalForm())); // To add an icon
    }
}
