package getUrlsMethods;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import dataStructure.CreatADatabase;
import javafx.scene.chart.PieChart;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chu on 16-8-26.
 */
public class DownLoadPagesTest2 {


    @Test
    public void downLoadPage() throws Exception {
        CreatADatabase database = new CreatADatabase("TestDB","mydb");

        Environment env = database.getEnv();
        Database db = database.getDb();

        DownLoadPages downLoadPages = new DownLoadPages();
        System.out.println(db.count());

        downLoadPages.downLoadPage("http://www.baidu.com",env,db);



        String s = downLoadPages.getPageMd5Result();


        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        EntryBinding stringBinding = TupleBinding.getPrimitiveBinding(String.class);

        stringBinding.objectToEntry(s,keyEntry);
        stringBinding.objectToEntry("http://www.baidu.com",dataEntry);

//        db.put(null,keyEntry,dataEntry);
//        System.out.println(db.count());

//        boolean b = downLoadPages.pageExist("http://www.baidu.com",env,db);
//
//        System.out.println(b);



    }


    @Test
    public void downLoadPage2() throws Exception{
        CreatADatabase database = new CreatADatabase("TestDB","mydb");

        Environment env = database.getEnv();
        Database db = database.getDb();




        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        EntryBinding stringBinding = TupleBinding.getPrimitiveBinding(String.class);

//        stringBinding.objectToEntry(s,keyEntry);
        stringBinding.objectToEntry("http://www.baidu.com",dataEntry);

        db.put(null,keyEntry,dataEntry);

        System.out.println(db.count());


    }

    @Test
    public void db() throws Exception{
        CreatADatabase database = new CreatADatabase("TestDB","mydb");

        Environment env = database.getEnv();
        Database db = database.getDb();

        System.out.println(db.count());

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        EntryBinding stringBinding = TupleBinding.getPrimitiveBinding(String.class);
        stringBinding.objectToEntry("http://www.baidu.com",dataEntry);

        Cursor cursor = db.openCursor(null,null);

        if (cursor.getSearchBoth(keyEntry,dataEntry,LockMode.DEFAULT) == OperationStatus.SUCCESS){
            String key = (String) stringBinding.entryToObject(keyEntry);
            String data = (String) stringBinding.entryToObject(dataEntry);

            System.out.println(key);
            System.out.println(data);
        }




    }

    @Test
    public void pageExist() throws Exception{
//先打开数据库，用DownloadPages对象的pageExist（）方法，获取网页指纹；
        CreatADatabase database = new CreatADatabase("TestDB","mydb");

        Environment env = database.getEnv();
        Database db = database.getDb();

        DownLoadPages downLoadPages = new DownLoadPages();


        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        EntryBinding stringBinding = TupleBinding.getPrimitiveBinding(String.class);



//        db.put(null,keyEntry,dataEntry);

        boolean b = downLoadPages.pageExist("http://www.baidu.com",env,db);
        String s = downLoadPages.getPageMd5Result();

        stringBinding.objectToEntry(s,keyEntry);
        stringBinding.objectToEntry("http://www.baidu.com",dataEntry);

        System.out.println(s);
        System.out.println(b);

//往数据库中加入，网页指纹和对应url的键值对；主要是用来判断，相同的两个页面，两次获取的指纹是否一致；
        CreatADatabase database2 = new CreatADatabase("TestDB","mydb");
        Environment env2 = database2.getEnv();
        Database db2 = database2.getDb();
        db2.put(null,keyEntry,dataEntry);

        System.out.println(db2.count());
//
        DatabaseEntry dataEntry2 = new DatabaseEntry();
//
        Cursor cursor = db2.openCursor(null,null);
//
        if (cursor.getSearchKey(keyEntry,dataEntry2,LockMode.DEFAULT) == OperationStatus.SUCCESS) {
//
            System.out.println(stringBinding.entryToObject(keyEntry));
            System.out.println(stringBinding.entryToObject(dataEntry2));
        }
//
//        System.out.println();
//        System.out.println(s);
    }




}