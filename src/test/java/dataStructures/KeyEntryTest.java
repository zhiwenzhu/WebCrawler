package dataStructures;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;
import com.sleepycat.je.*;
//import com.sun.java.util.jar.pack.Package;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import org.junit.Assert;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.net.MalformedURLException;
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

        Cursor cursor = db1.openCursor(null,null);
        DatabaseEntry adata = new DatabaseEntry();
        DatabaseEntry akey = new DatabaseEntry();

        while (cursor.getNext(akey,adata,LockMode.DEFAULT) == OperationStatus.SUCCESS){
            System.out.print(akey.getData() + "\t");
            System.out.println(adata.getData());
        }








    }

    @Test
    public void urlToEntry1() throws Exception {

    }

    @Test
    public void dataEntry() throws Exception {

    }

    @Test
    public void testUrlToSting() throws Exception{
        URL url = new URL("http://www.baidu.com");
        System.out.println(url);
        System.out.println(url.toString());

        URL url1 = null;
        try {
            url1 = new URL("wfffsfsdfd");

        }catch (MalformedURLException e){
            e.printStackTrace();
            System.out.println("Incprrect url");
        }

        System.out.println(url1);

        URL url2 = new URL("www.baidu.com");         //非合法的url格式；
        System.out.println(url2);
    }

    @Test
    public void urlToEntry2() throws Exception{

        File file = new File("testFile2");
        if (!file.exists()){
            file.mkdirs();
        }
        Environment env = null;
        Database db = null;
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);

        env = new Environment(file,envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTransactional(true);
        db = env.openDatabase(null,"testdb2",dbConfig);

        EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(int.class);
        DatabaseEntry keyEntry = new DatabaseEntry();
        keyBinding.objectToEntry(0,keyEntry);

        URL url = new URL("http://www.baidu.com");
        StoredClassCatalog classCatalog = new StoredClassCatalog(db);
        EntryBinding dataBinding = new SerialBinding(classCatalog,URL.class);

        DatabaseEntry dataEntry = new DatabaseEntry();
        dataBinding.objectToEntry(url,dataEntry);

        System.out.println(dataBinding.entryToObject(dataEntry));
        System.out.println(keyBinding.entryToObject(keyEntry));



//        System.out.println(db);
//        System.out.println(db.count());
//
        Cursor cursor = db.openCursor(null,null);

        DatabaseEntry firstkey = new DatabaseEntry();
        DatabaseEntry firstData = new DatabaseEntry();
        if (cursor.getNext(firstkey,firstData,LockMode.DEFAULT) == OperationStatus.SUCCESS){
//            System.out.println(keyBinding.entryToObject(firstkey));
//            System.out.println(dataBinding.entryToObject(firstData));

            System.out.println(firstkey);
            System.out.println(firstData);
            System.out.println(firstkey.toString());
        }

//        while (cursor.getNext(firstkey,firstData,LockMode.DEFAULT) == OperationStatus.SUCCESS){
//            System.out.println(firstkey + "\t"+firstData);
//        }




//        db.put(null,keyEntry,dataEntry);



    }


    @Test
    public void urlToEntry3() throws Exception{
        File file = new File("testFile3");
        if (!file.exists()){
            file.mkdirs();
        }
        Environment env = null;
        Database db = null;
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);

        env = new Environment(file,envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTransactional(true);
        db = env.openDatabase(null,"testdb3",dbConfig);

        EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(int.class);
        DatabaseEntry keyEntry = new DatabaseEntry();
        keyBinding.objectToEntry(0,keyEntry);

        DatabaseEntry dataEntry = new DatabaseEntry();


//        URL url = new URL("http://www.baidu.com");


        class UrlBinding extends TupleBinding{
            public void objectToEntry(Object object,TupleOutput to){
                URL url = (URL) object;
                to.writeString(url.toString());
            }

            public Object entryToObject(TupleInput ti){
                URL url = null;
                try {
                    url = new URL(ti.readString());
                }catch (Exception e){
                    e.printStackTrace();
                }

                return url;

            }
        }
//
//        UrlBinding urlBinding = new UrlBinding();
//        urlBinding.objectToEntry(new URL("http://www.baidu.com"),dataEntry);
//
//        System.out.println(dataEntry.getData());
//        System.out.println(urlBinding.entryToObject(dataEntry));
//        System.out.println(dataEntry.);

    }




}