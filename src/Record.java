import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Record {
    private String name;
    private int percent;

    public Record(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public int getPercent() {
        return percent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String toString() {
        return name + " " + percent;
    }
}
