package ApplicationLogic;

import ApplicationInterface.MainWindow;
import ApplicationInterface.SplitPane;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by marius on 3/23/16.
 */
public class TreeNodeContextualMenu extends JPopupMenu {

    private File potentialAudioFile;
    private SplitPane splitPane;
    private JTable detailsTable;
    private JTextArea detailsPane;
    private JScrollPane scrollPanel;
    private MetadataProcesser utilities;
    public JPopupMenu menu;

    public TreeNodeContextualMenu(JTable detailsTable, JTextArea detailsPane,
                                  Container c, SplitPane splitPane, JScrollPane scrollPanel, MetadataProcesser utilities, File potentialAudioFile) {

        this.detailsTable = detailsTable;
        this.detailsPane = detailsPane;
        this.splitPane = splitPane;
        this.scrollPanel = scrollPanel;
        this.utilities = utilities;
        this.potentialAudioFile = potentialAudioFile;

        this.menu = new JPopupMenu();
        JMenuItem searchInDirectory = new JMenuItem("Search for mp3 files");
        this.menu.add(searchInDirectory);

        searchInDirectory.addActionListener(e -> {

            File file = (File) MainWindow.fileTree.getLastSelectedPathComponent();

            if (file != null)

                if (file.isDirectory()) {
                    c.remove( this.splitPane);
                    FileSearch fileSearch = new FileSearch();
                    fileSearch.searchFile(file);
                    ArrayList<File> files = fileSearch.getFileList();
                    this.detailsPane.setText("");
                    this.potentialAudioFile = new File(MainWindow.DEFAULT);

                    try {
                        this.utilities = new MetadataProcesser(files);

                        this.detailsTable = new JTable( this.utilities.getData(), MainWindow.COLUMN_NAMES);
                        this.detailsTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
                        this.detailsTable.setFillsViewportHeight(true);
                        this.detailsTable.setEnabled(false);



                        //creating split pane

                        this.splitPane = new SplitPane(this.scrollPanel,this.detailsTable);

                        c.add(this.splitPane, BorderLayout.CENTER);
                        c.revalidate();
                        c.repaint();

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    file = (File) MainWindow.fileTree.getLastSelectedPathComponent();
                    this.detailsPane.setText("");
                    c.remove( this.splitPane);

                    try {
                        this.utilities = new MetadataProcesser(file);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    if ( this.utilities.getInfo() != new String[]{""}) {
                        for (int i = 0; i <  this.utilities.getInfo().length; i++) {
                            this.detailsPane.append( this.utilities.getInfo()[i] + "\n");
                        }

                        this.splitPane = new SplitPane(this.scrollPanel, this.detailsPane);

                        c.add( this.splitPane, BorderLayout.CENTER);
                        c.revalidate();
                        c.repaint();
                        this.potentialAudioFile = file;
                    }
                }

        });


    }

    @Override
    public void show(Component invoker, int x, int y) {
        this.menu.show(invoker, x, y);
    }

    public JPopupMenu getMenu(){
        return this.menu;
    }

    public File getPotentialAudioFile(){
        return this.potentialAudioFile;
    }

}