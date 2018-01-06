package fx.app.processing;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.plugin.MarvinPlugin;
import marvin.util.MarvinPluginLoader;

public class ImageProcessor {

    private static MarvinImage marvinImage;
    private static MarvinImagePlugin marvinImagePlugin;

    private static void setMarvinImage(String filePath){
        marvinImage = MarvinImageIO.loadImage(filePath);
    }

   private static void setMarvinImagePlugin(String pluginPath){
        marvinImagePlugin = MarvinPluginLoader.loadImagePlugin(pluginPath);
   }

    public static MarvinImage invertImage(String filePath){
        setMarvinImage(filePath);
        setMarvinImagePlugin("org.marvinproject.image.color.invert.jar");

        marvinImagePlugin.process(marvinImage, marvinImage);
        marvinImage.update();

        return marvinImage;
    }

//    public static MarvinImage histogramEqualization(String filePath){
//        setMarvinImage(filePath);
//        setMarvinImagePlugin();
//    }

}
