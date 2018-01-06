import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import portablemap.FileUtils;
import portablemap.models.PbmAscii;

import java.io.IOException;

public class PbmAsciiTest extends AbstractTestModel<PbmAscii> {

    @Before
    @Override
    public void beforeTest()
    {
        try {
            object = (PbmAscii) FileUtils.readFromFile(getClass().getResource("test2P1.pbm").getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void printImageMeasuresTest(){
        object.printImageToConsole();
    }



    @Override
    @Ignore
    public void afterTest() {

    }

}
