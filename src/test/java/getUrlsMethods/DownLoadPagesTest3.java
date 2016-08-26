package getUrlsMethods;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import dataStructure.CreatADatabase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chu on 16-8-26.
 */
public class DownLoadPagesTest3 {

//保存完页面信息后，不关掉数据库环境和数据库；（重新测试）
    @Test
    public void downLoadPage() throws Exception {
        CreatADatabase database = new CreatADatabase("TestDB","mydb");

        Environment env = database.getEnv();
        Database db = database.getDb();
//        Cursor cursor = db.openCursor(null,null);

        DownLoadPages downLoadPages = new DownLoadPages();
        boolean succsee = downLoadPages.downLoadPage("http://www.baidu.com",env,db);
        String key = "";
        if (succsee){
           key = downLoadPages.getPageMd5Result();
        }

        EntryBinding stringBinding = TupleBinding.getPrimitiveBinding(String.class);
        DatabaseEntry keyEhtry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        stringBinding.objectToEntry(key,keyEhtry);
        stringBinding.objectToEntry("http://www.baidu.com",dataEntry);

        db.put(null,keyEhtry,dataEntry);

        System.out.println(db.count());



//        cursor.close();
//
//
//        db.close();
//        env.close();


    }

}