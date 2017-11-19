package portablemap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    private static BufferedReader br = null;

    private static void setBufferedReader(String file) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(file));
    }

    private static boolean lineHasComment(String line) throws IOException {
        Pattern pattern = Pattern.compile("#.*"); //regex for find comment pattern
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) return true;
        return false;
    }

    private static boolean matchesHeading(String string) {
        return string.matches("P[123456]"); //P1, P2, P3, P4, P5, P6
    }


    public static PortableMap readFromFile(String file) throws IOException {

        setBufferedReader(file);
        String heading = null;

        String nextLine = br.readLine();
        // checking comment in header
        while (lineHasComment(nextLine)) {

            String[] nextLineSplitted = nextLine.split("#");
            for (String element : nextLineSplitted) {
                String elementNoWhiteSpaces = element.replaceAll("\\s", "");
                if (matchesHeading(elementNoWhiteSpaces)) {
                    heading = elementNoWhiteSpaces;
                    break;
                }
            }

            nextLine = br.readLine();
        }

        if (heading == null) {
            heading = nextLine;
        }

        String line = br.readLine(); // in this line width and height
        Scanner scanner = new Scanner(line);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        line = br.readLine();

        PortableMap pbm = null;
        switch (heading) {
            case "P1":
                pbm = new PbmAscii(width, height, HeadingPmASCII.P1);

                int i = 0, j = 0;
                while (line != null) {

                    // skip the empty line
                    if (line.isEmpty()) {
                        line = br.readLine();
                        continue;
                    }

                    String[] numbersInLine = line.split(" +");
                    for (String number : numbersInLine) {
                        byte pixel = Byte.valueOf(number);
                        ((PbmAscii) pbm).setPixelInImage(i, j, pixel);
                        j = ++j % width;
                        i = (j == 0) ? ++i % height : i;
                    }

                    line = br.readLine();

                }
                if (br != null)
                    br.close();
                break;

            case "P2":
                break;

            case "P3":
                break;

            case "P4":
                break;

            case "P5":
                break;
        }
        return pbm;

    }

}




