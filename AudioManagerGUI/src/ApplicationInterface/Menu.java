package ApplicationInterface;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by marius on 3/22/16.
 */

/**
 * Contextual menu for the application
 */
public class Menu {


    private JMenuBar menuBar;

    private JMenu menu;

    private JMenuItem item1 , item2;

    /**
     *
     * @param frame Main windows for which we apply this menu
     */
    public Menu(JFrame frame){

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);

        menuBar.add(menu);

        JMenuItem item1 = new JMenuItem("Change Look 'n feel -> Nimbus");
        JMenuItem item2 = new JMenuItem("Change Look 'n feel -> original");
        menu.add(item1);
        menu.add(item2);

        item1.addActionListener(e -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e1) {
                // If Nimbus is not available, you can set the GUI to another look and feel.
            }
            SwingUtilities.updateComponentTreeUI(frame);
        });

        item2.addActionListener(e ->{
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            SwingUtilities.updateComponentTreeUI(frame);
        });

        frame.setJMenuBar(menuBar);


        this.item1 = item1;
        this.item2 = item2;
        this.menuBar = menuBar;
        this.menu = menu;
    }

    /**
     *
     * @return The MenuBar
     */
    public JMenuBar getMenuBar(){
        return this.menuBar;
    }

    /**
     *
     * @return The Menu
     */
    public JMenu getMenu(){
        return this.menu;
    }

    /**
     *
     * @return First option
     */
    public JMenuItem getItem1(){
        return this.item1;
    }

    /**
     *
     * @return Second Option
     */
    public JMenuItem getItem2(){
        return this.item2;
    }

}
