package ApplicationInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by marius on 3/21/16.
 */
public class SplitPane extends JSplitPane {
    /**
     *
     * @param panel Scroll panel which will be added to our tree and the splitpane will be between it and a JComponent(Table or Text Area)
     * in our case
     * @param textArea Widget used to display information about directories or audio files
     */
    public SplitPane(JScrollPane panel, JComponent textArea){
        super(JSplitPane.HORIZONTAL_SPLIT, panel, textArea);
        super.setOneTouchExpandable(true);
        super.setDividerLocation(300);





        Dimension minimumSize = new Dimension(100, 60);
        panel.setMinimumSize(minimumSize);
        textArea.setMinimumSize(minimumSize);
    }
}
