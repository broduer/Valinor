import java.awt.*;
import java.io.File;

/*
 * @project universal-launcher
 * @author Patrity - https://github.com/Patrity
 * Created on - 1/1/2021
 */
public class Config {
    /*
     * The name that appears on the task bar
     */
    public static final String NAME = "Valinor Launcher";

    /*
     * Connection address of where to obtain the jar
     */
    public static final String URL = "https://valinor-os.com/client/Valinor-C.jar";

    /*
     * The name of the jar to launch once downloaded
     */
    public static final String FILE_NAME = "Valinor-C.jar";

    /*
     * The path to save the jar once it's downloaded.
     * Leaving it at user home is a safe bet,
     * change "example" to the name of your organization
     */
    public static String CLIENT_PATH = System.getProperty("user.home") + File.separator + "Valinor" + File.separator;

    /*
     * image dimensions of your logo.png
     */
    public static final int LOGO_WIDTH = 604;
    public static final int LOGO_HEIGHT = 340;

    /*
     * Colors for the progress bar and progress text
     */
    public static final Color FOREGROUND = Color.BLACK;
    public static final Color BACKGROUND = Color.lightGray;
}
