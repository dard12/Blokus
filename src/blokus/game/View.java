package blokus.game;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

/**
 * View class for displaying information on the Blokus board.
 * @author Hsing
 *
 */
public class View implements Application {
    private Window window = null;
 
    /**
     * Initialize interface based on bxml file upon startup.
     */
    @Override
    public void startup(Display display, Map<String, String> properties)
        throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window)bxmlSerializer.readObject(View.class, "interface.bxml");
        window.open(display);
        }
 
    @Override
    public boolean shutdown(boolean optional) {
        if (window != null)
            window.close();
 
        return false;
    }
 
    @Override
    public void suspend() {
    }
 
    @Override
    public void resume() {
    }
    
    /**
     * Runner to enable running as normal java application.
     * @param args console
     */
    public static void main(String[] args) {
        DesktopApplicationContext.main(View.class, args);
        Game.runGame();
    }
}