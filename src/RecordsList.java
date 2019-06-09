import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a list of records.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 1 hour
 * <br>Added readRecord() method.
 * <br>Added clearRecords() method.
 * </p>
 * @since 1.11
 */
public class RecordsList {
    /**
     * Reads the records from a file into an ArrayList.
     *
     * @return the ArrayList of records.
     * @since 1.11
     */
    public static ArrayList<Record> readRecords() {
        ArrayList<Record> arr = new ArrayList<>(0);
        try {
            Scanner in = new Scanner(new File("Interconnected-Isolation/assets/files/records.txt"));
            while (in.hasNextLine()) {
                String line = in.nextLine();
                arr.add(new Record(line.substring(0, line.indexOf(' ')), Integer.parseInt(line.substring(line.indexOf(' ') + 1))));
            }
        } catch (IOException e) {
        }
        return arr;
    }

    /**
     * Clears all the records from the file.
     *
     * @since 1.11
     */
    public static void clearRecords() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("Interconnected-Isolation/assets/files/records.txt"));
            out.close();
        } catch (IOException e) {
        }
    }
}
