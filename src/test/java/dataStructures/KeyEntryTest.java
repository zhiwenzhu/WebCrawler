package dataStructures;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import org.junit.Assert;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by chu on 16-8-23.
 */
public class KeyEntryTest {
    @Test
    public void urlToEntry() throws Exception {
        CreatADatabase adatabase = new CreatADatabase("testDatabase","testdb");

        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
        }catch (Exception e){
            e.printStackTrace();
        }

        StoredClassCatalog classCatalog = new StoredClassCatalog(adatabase.db);
        EntryBinding binding = new SerialBinding(classCatalog,URL.class);
        DatabaseEntry theData = new DatabaseEntry();

        binding.objectToEntry(url,theData);

        System.out.println(adatabase.db.count());
        int i = 1;
        DatabaseEntry theData1 = new DatabaseEntry();
        EntryBinding binding1 = TupleBinding.getPrimitiveBinding(int.class);
        binding1.objectToEntry(i,theData1);




        adatabase.db.put(null,theData,theData1);

        System.out.println(adatabase.db.count());

        try {
            if (adatabase.db != null){
                adatabase.db.close();
            }

            if (adatabase.getEnv() != null){
                adatabase.getEnv().close();
            }
        }catch (DatabaseException e){
            e.printStackTrace();
        }

//        System.out.println(adatabase.db.count());

//        Environment env1 = adatabase.getEnv();
//        System.out.println(env1.getDatabaseNames().get(0));

//        adatabase.getEnv().evictMemory();
//        System.out.println(adatabase.getEnv().getDatabaseNames().toString());

        Environment env1 = new Environment(new File("testDatabase"),null);

        Database db1 =  env1.openDatabase(null,"testdb",null);

//        System.out.println(adatabase.db.count());

        System.out.println(env1.getDatabaseNames().get(0));
        System.out.println(db1.count());






    }

    @Test
    public void urlToEntry1() throws Exception {

    }

    @Test
    public void dataEntry() throws Exception {

    }

}