package ApplicationInterface;

import ApplicationLogic.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by marius on 3/21/16.
 */

/**
 * Class used for setting up the main window
 */
public class MainWindow extends JFrame {

    public static final String[] COLUMN_NAMES = {"Name",
            "Size",
            "Artist",
            "Genre",
            "Album",
            "Path"};
    private FileSystem model;

    public static final String DEFAULT = FileSystemView.getFileSystemView().getRoots()[0].getAbsolutePath(); //getting the root path of OS

    private JTable detailsTable = new JTable();

    private JTextArea detailsPane = new JTextArea();

    private File potentialAudioFile;

    private SplitPane splitPane;

    private MetadataProcesser utilities;

    private JMenuBar menuBar;

    private JMenu menu;

    private JMenuItem item1, item2;

    private  JScrollPane scrollPanel;

    public static  JTree fileTree;


    public MainWindow(String title){

        super(title);

        Container c = getContentPane();

        Menu menu = new Menu(this);       //adding menu to the application

        this.menuBar = menu.getMenuBar();
        this.menu = menu.getMenu();
        this.item1 = menu.getItem1();
        this.item2 = menu.getItem2();


        //setting up fileSystem model

        model = new FileSystem(new File(DEFAULT));

        potentialAudioFile = new File(DEFAULT);

        //setLayoutManager
        setLayout(new BorderLayout()); //it lets you add components standard way and simple

        //create swing components

        detailsPane.setEditable(false);

        JButton playButton = new JButton("<html><body><h1 style=\"color:blue;\">Play</h1></body></html>");
        JButton addToFavButton = new JButton("<html><body><h1 style=\"color:red;\">AddToFavorites</h1></body></html>");

        JPanel buttons = new JPanel(new GridLayout(0,2)); //to display the buttons equally stretched
        buttons.add(playButton);
        buttons.add(addToFavButton);

        MainWindow.fileTree = new JTree(model);

        scrollPanel = new JScrollPane(MainWindow.fileTree);
        splitPane = new SplitPane(scrollPanel, detailsTable);

        c.add(splitPane, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);

        TreeNodeContextualMenu popupMenu = new TreeNodeContextualMenu
                (detailsTable,detailsPane,c,splitPane,scrollPanel,utilities,potentialAudioFile);

        fileTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger()){
                    try {
                        fileTree.setSelectionPath(fileTree.getPathForLocation(e.getX(), e.getY())); //select the item
                        popupMenu.show(e.getComponent(), e.getX(), e.getY()); //and show the menu

                    }
                    catch (NullPointerException e1){
                        e1.printStackTrace();
                        System.out.println("Clicked outside Jtree");
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    try {
                        fileTree.setSelectionPath(fileTree.getPathForLocation(e.getX(), e.getY())); //select the item
                        popupMenu.show(e.getComponent(), e.getX(), e.getY()); //and show the menu

                    }
                    catch (NullPointerException e1){
                        e1.printStackTrace();
                        System.out.println("Clicked outside Jtree");
                    }
                }
            }
        });


        //seting up our potential audio file when we click a file in the tree
        fileTree.addTreeSelectionListener(e -> {

             try {
                 File currentFile = (File) fileTree.getLastSelectedPathComponent();
                 if (currentFile != null)
                   if (!currentFile.isDirectory()) {
                       potentialAudioFile = currentFile;

                 }
             }
             catch (NullPointerException e1){
                 e1.printStackTrace();
                 System.out.println("Clicked outside Jtree");
             }

        });

        //Action events

        playButton.addActionListener(e -> {
            if(!potentialAudioFile.getName().endsWith(".mp3")){
                JOptionPane.showMessageDialog(this, "Please choose an audio file.");
            }
            else{
                AudioFileOptions audio = new AudioFileOptions();

                audio.playSound(potentialAudioFile);
            }

        });

        addToFavButton.addActionListener(e -> {
            if(!potentialAudioFile.getName().endsWith(".mp3")){
                JOptionPane.showMessageDialog(this, "Please choose an audio file.");
            }
            else{
                AudioFileOptions audio = new AudioFileOptions();

                try {
                    audio.addToFavorites(potentialAudioFile, fileTree);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

}