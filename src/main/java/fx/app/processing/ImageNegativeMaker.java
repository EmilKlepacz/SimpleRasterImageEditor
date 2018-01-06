package fx.app.processing;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.plugin.MarvinPlugin;
import marvin.util.MarvinPluginLoader;

public class ImageNegativeMaker {

    public MarvinImage invertImage(String filePath){
        MarvinImage marvinImage = MarvinImageIO.loadImage(filePath);
        MarvinImagePlugin invertPlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert.jar");

        invertPlugin.process(marvinImage, marvinImage);
        marvinImage.update();

        return marvinImage;
    }

}
