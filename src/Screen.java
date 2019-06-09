import javafx.scene.Scene;

import java.io.IOException;

/**
 * Represents a screen.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 5 minutes
 * <br>Added window() method.
 * </p>
 * @since 1.11
 */
public interface Screen {
    /**
     * @return the scene of any screen
     * @throws IOException if there is an input or output exception when finding an image
     */
    public Scene window() throws IOException;
}
