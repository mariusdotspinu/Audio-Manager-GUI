package ApplicationLogic;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by marius on 3/22/16.
 */

/**
 * Utilities for metadata managing
 */
public class MetadataProcesser {

    private ArrayList<ArrayList<String>> allInfo;
    private String[] names;
    private final int NUMBER_OF_COLUMNS = 6;

    /**
     *
     * @return We are setting up the data for our table ->song informations ,and returning it
     */
    public Object[][] getData() {

        Object[][] data = new Object[allInfo.size()][NUMBER_OF_COLUMNS];

        for (int i = 0 ; i < allInfo.size();i++)
            for(int j = 0 ; j < allInfo.get(i).size();j++)
                data[i][j] = allInfo.get(i).get(j);



        return data;
    }

    /**
     *
     * @param files Files that were listed by our searchManager (.mp3) , and adding the metadata of them
     * @throws FileNotFoundException
     */
    public MetadataProcesser(ArrayList<File> files) throws FileNotFoundException {

        ArrayList<String> info = new ArrayList<>();
        allInfo = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {

            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(files.get(i));
            ParseContext context = new ParseContext();

            try {
                parser.parse(inputstream, handler, metadata, context);
            } catch (IOException | SAXException | TikaException e) {
                e.printStackTrace();
            }

            info.add(files.get(i).getName());
            info.add("" + (float) files.get(i).getPath().length() / 1024 + " MB");

            if (metadata.get("xmpDM:artist") != null) {
                info.add(metadata.get("xmpDM:artist"));
            }
            else{
                info.add("No artist found");
            }

            if (metadata.get("xmpDM:genre") != null) {
                info.add(metadata.get("xmpDM:genre"));
            }
            else{
                info.add("No genre found");
            }

            if (metadata.get("xmpDM:album") != null) {
                info.add(metadata.get("xmpDM:album"));
            }
            else {
                info.add("No album found");
            }

            info.add(files.get(i).getPath());

            if (info.size() > 0) {
                allInfo.add(info);
                info = new ArrayList<>();
            }

        }
    }
    
    /**
     *
     * @param file Same thing as above only , we're doing it for one file
     * @throws FileNotFoundException
     */
    public MetadataProcesser(File file) throws FileNotFoundException {

        File returnedFile = null;
        FileSearch searchFile = new FileSearch();

        searchFile.searchFile(file);
        try {
            returnedFile = searchFile.getFileList().get(0); //if it's a file it will always return one file
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("No file returned");
        }

        if (returnedFile != null) {

            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(returnedFile);
            ParseContext context = new ParseContext();

            try {
                parser.parse(inputstream, handler, metadata, context);
            } catch (IOException | SAXException | TikaException e) {
                e.printStackTrace();
            }

            String[] keys = metadata.names();

            this.names = new String[metadata.size()];
            for (int i = 0 ; i < metadata.size();i++)
                this.names[i] = metadata.get(keys[i]);

        }
    }

    /**
     *
     * @return Returning the metadata information
     */
    public String[] getInfo(){

        if (this.names != null){
            return this.names;
        }
        return new String[]{""};
    }
}
