package ApplicationLogic;

import java.io.File;
import java.util.*;

/**
 * Created by marius on 3/21/16.
 */

/**
 * Class that implements the search engine for a directory (searching for .mp3 files)
 */
public class FileSearch {

    private Set<File> fileList = new HashSet<>();
    
    /**
     *
     * @param myFile Search for a directory
     */
    public void searchFile(File myFile) {

        LinkedList<File> stack = new LinkedList<>();
        stack.push(myFile);
        while(!stack.isEmpty()) {
            File child = stack.pop();
            if (child.isDirectory() && !this.isEmpty(child)) {
                for(File f : child.listFiles()) stack.push(f);
            } else if (child.isFile() && child.getName().endsWith(".mp3")) {
                this.fileList.add(child);
            }
        }
    }

    /**
     *
     * @return Returning the listOfFiles
     */
    public ArrayList<File> getFileList(){

        return new ArrayList<>(this.fileList);
    }

    private boolean isEmpty(File myFile){

        if (myFile.listFiles() == null)
            return true;

        return false;
    }
}
