package deprecated;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import dataStructures.CreatADatabase;

import java.net.URL;

/**
 * Created by chu on 16-8-22.
 */

/**
 * 该类被UrlBinding取代；
 */
public class KeyEntry {
    static long value = 0;
    private CreatADatabase creatADatabase;
    private DatabaseEntry theKey;
    private DatabaseEntry theData;
    private StoredClassCatalog classCatalog;
    private EntryBinding keyBinding;

    public KeyEntry(){

    }

    public DatabaseEntry urlToEntry(URL url,String fileName,String setName){
        creatADatabase = new CreatADatabase(fileName,setName);

        classCatalog = new StoredClassCatalog(creatADatabase.getDb());
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
