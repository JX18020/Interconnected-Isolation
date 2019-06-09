/**
 * Represents a player record.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.11 - Julia Xie
 * <br>DateL 2019/06/07
 * <br>Time Spent: 30 minutes
 * <br>Added constructor.
 * <br>Added getName() method.
 * <br>Added getPercent() method.
 * <br>Added toString() method.
 * </p>
 * @since 1.11
 */
public class Record {
    private String name;
    private int percent;

    /**
     * Constructor which creates a record using the specified parameters.
     *
     * @param name    the player's name
     * @param percent the percent the player improved
     * @since 1.11
     */
    public Record(String name, int percent) {
        this.name = name;
        this.percent = percent;
    }

    /**
     * @return the player's name
     * @since 1.11
     */
    public String getName() {
        return name;
    }

    /**
     * @return the percent the player improved
     * @since 1.11
     */
    public int getPercent() {
        return percent;
    }

    /**
     * @return a string with the name and percent of the player
     * @since 1.11
     */
    public String toString() {
        return name + " " + percent;
    }
}
