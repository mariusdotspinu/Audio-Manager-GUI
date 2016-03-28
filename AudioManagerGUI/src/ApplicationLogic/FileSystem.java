package ApplicationLogic;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by marius on 3/21/16.
 */

/**
 *Class that is used to give the JTree an exploration view for any os
 */
public class FileSystem implements TreeModel {

    private File root;
    private ArrayList<TreeModelListener> listeners;

    /**
     * Filter for not showing the hidden files of the OS
     */
    private FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            File direct = new File(name);
            return !direct.isHidden();
        }
    };



    /**
     *
     * @param root Setting up the root
     */
    public FileSystem(File root){
        this.root = root;
        this.listeners = new ArrayList<>();
    }


    /**
     *
     * @return Return the root
     */
    @Override
    public Object getRoot() {
        return root;
    }

    /**
     *
     * @param parent Directory
     * @param index Index
     * @return Return the Child at that index
     */
    @Override
    public Object getChild(Object parent, int index) {

        File directory = (File) parent;

        String[] children = ((File) parent).list(filter);

        return new FileSystem.TreeFile(directory, children[index]);




    }

    /**
     *
     * @param parent Directory or leaf
     * @return Return number of children
     */
    @Override
    public int getChildCount(Object parent) {

        File file = (File) parent;

        if(file.isDirectory()) {
            String[] listOfFiles = file.list(filter);

            if (listOfFiles != null) {

                return listOfFiles.length;
            }


        }
        return 0;
    }

    /**
     *
     * @param node Node to be checked
     * @return T/F if the node is leaf
     */
    @Override
    public boolean isLeaf(Object node) {

        File file = (File) node;
        return file.isFile();

    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) newValue;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
        Object[] changedChildren = { targetFile };
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }
    
    /**
     *
     * @param parent Directory
     * @param child Child
     * @return Return the position of the child
     */
    @Override
    public int getIndexOfChild(Object parent, Object child) {

        File directory = (File) parent;
        File file  = (File) child;
        String[] children = directory.list(filter);

        for (int i = 0 ; i < children.length ; i++){
            if(file.getName().equals(children[i])){
               return i;
            }
        }

        return -1;

    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
      listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
       listeners.remove(l);
    }

    private class TreeFile extends File {

        public TreeFile(File parent, String child) {
            super(parent, child);
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
