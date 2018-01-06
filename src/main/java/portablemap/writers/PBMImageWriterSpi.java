package portablemap.writers;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import java.io.IOException;
import java.util.Locale;

public class PBMImageWriterSpi extends ImageWriterSpi {

    private static final String vendorName="mm's computing";
    private static final String version="0.0.1";
    private static final String writerClassName="portablemap.writers.PBMImageWriter";
    private static final String[] names={"pbm","PBM"};
    private static final String[] suffixes={"pbm","PBM"};
    private static final String[] MIMETypes={"image/pbm"};
    private static final String[] readerSpiNames={"portablemap.readers.PPMImageReaderSpi"};

    private static final boolean supportsStandardStreamMetadataFormat = false;
    private static final String nativeStreamMetadataFormatName = null;
    private static final String nativeStreamMetadataFormatClassName = null;
    private static final String[] extraStreamMetadataFormatNames = null;
    private static final String[] extraStreamMetadataFormatClassNames = null;
    private static final boolean supportsStandardImageMetadataFormat = false;
    private static final String nativeImageMetadataFormatName =null;//"uk.co.mmscomputing.imageio.ppm.PPMFormatMetadata 0.0.1";
    private static final String nativeImageMetadataFormatClassName =null;//"uk.co.mmscomputing.imageio.ppm.PPMFormatMetadata";
    private static final String[] extraImageMetadataFormatNames = null;
    private static final String[] extraImageMetadataFormatClassNames = null;

    public PBMImageWriterSpi(){
        super(	vendorName,		version,
                names,			suffixes,
                MIMETypes,		writerClassName,
                STANDARD_OUTPUT_TYPE,	readerSpiNames,
                supportsStandardStreamMetadataFormat,
                nativeStreamMetadataFormatName,
                nativeStreamMetadataFormatClassName,
                extraStreamMetadataFormatNames,
                extraStreamMetadataFormatClassNames,
                supportsStandardImageMetadataFormat,
                nativeImageMetadataFormatName,
                nativeImageMetadataFormatClassName,
                extraImageMetadataFormatNames,
                extraImageMetadataFormatClassNames
        );
    }

    public ImageWriter createWriterInstance(Object extension)throws IOException {
        return new PBMImageWriter(this);
    }

    public boolean canEncodeImage(ImageTypeSpecifier type){
//    return (type.getBufferedImageType()==BufferedImage.TYPE_INT_RGB);
        return true;
    }

    public String getDescription(Locale locale){
        return "mmsc pbm encoder";
    }

}