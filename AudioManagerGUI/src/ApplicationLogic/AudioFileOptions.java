package ApplicationLogic;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by marius on 3/22/16.
 */

/**
 * Class used for audio files operations
 */
public class AudioFileOptions {

    /**
     *
     * @param file If file is audio format (.mp3) , a third party program will run the file
     */
    public void playSound(File file) {

        try{
            Desktop.getDesktop().open(file); //play the file with default third party software from os
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file File (song name ) to be added to the favorite list
     * @param tree The tree which we will create a new directory called FavoriteSongsList (if it doesn't exist already)
     * @throws IOException
     */
    public void addToFavorites(File file, JTree tree) throws IOException {

        File dir = null;

        try{

            dir = new File(System.getProperty("user.home") + "/FavoriteSongsList/");
            if (!dir.exists())
                dir.mkdir();


        }catch(Exception e){
            e.printStackTrace();
        }

        File addedFile = new File(dir, String.valueOf(file.getName()));
        System.out.println(addedFile.getPath());
        addedFile.createNewFile();

        tree.updateUI();


    }
}


