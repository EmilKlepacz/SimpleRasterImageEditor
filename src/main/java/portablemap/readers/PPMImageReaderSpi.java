package portablemap.readers;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class PPMImageReaderSpi extends ImageReaderSpi {

    private static final String vendorName="mm's computing";
    private static final String version="0.0.1";
    private static final String readerClassName="uk.co.mmscomputing.imageio.ppm.PPMImageReader";
    private static final String[] names={"pbm","PBM","pgm","PGM","ppm","PPM"};
    private static final String[] suffixes={"pbm","PBM","pgm","PGM","ppm","PPM"};
    private static final String[] MIMETypes={"image/pbm","image/pgm","image/ppm"};
    private static final String[] writerSpiNames={"uk.co.mmscomputing.imageio.ppm.PPMImageWriterSpi"};

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

    public PPMImageReaderSpi(){
        super(	vendorName,		version,
                names,			suffixes,
                MIMETypes,		readerClassName,
                STANDARD_INPUT_TYPE,	writerSpiNames,
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

    public ImageReader createReaderInstance(Object extension)throws IOException {
        return new PPMImageReader(this);
    }

    public boolean canDecodeInput(Object source)throws IOException{
        if(!(source instanceof ImageInputStream)) { return false; }
        ImageInputStream stream = (ImageInputStream)source;
//    stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        byte[] pn = new byte[2];
        try{
            stream.mark();
            stream.readFully(pn);
            stream.reset();
        }catch(IOException e){
            return false;
        }
        return(pn[0]==(byte)'P')&&((pn[1]==(byte)'4')||(pn[1]==(byte)'5')||(pn[1]==(byte)'6'));
    }

    public String getDescription(Locale locale){
        return "mmsc ppm decoder";
    }
}