package dataStructures;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import sun.util.cldr.CLDRLocaleProviderAdapter;

import java.net.URL;

/**
 * Created by chu on 16-8-22.
 */
public class KeyEntry {
    static long value = 0;
    SavedData savedData;
    DatabaseEntry theKey;
    DatabaseEntry theData;
    StoredClassCatalog classCatalog;
    EntryBinding keyBinding;

    public KeyEntry(){

    }

    public DatabaseEntry urlToEntry(URL url,String fileName,String setName){
        savedData = new SavedData(fileName,setName);

        classCatalog = new StoredClassCatalog(savedData.db);
        keyBinding = new SerialBinding(classCatalog,URL.class);

        theKey = new DatabaseEntry();
        keyBinding.objectToEntry(url,theKey);


        return theKey;
    }

    public DatabaseEntry urlToEntry(URL url){
        theKey = new DatabaseEntry();
        keyBinding.objectToEntry(url,theKey);
        return theKey;
    }


    public DatabaseEntry dataEntry(){
        theData = new DatabaseEntry();
        EntryBinding myBinding = TupleBinding.getPrimitiveBinding(long.class);
        myBinding.objectToEntry(value,theData);

        value++;

        return theData;
    }
}
