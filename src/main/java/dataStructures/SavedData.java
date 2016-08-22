package dataStructures;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;

/**
 * Created by chu on 16-8-22.
 */
public class SavedData {
    Environment env;
    Database db;
    EnvironmentConfig envConfig;
    DatabaseConfig dbCongig;
    private String fileName;
    private File file;
    private String setName;

    private void setSetName(String name){
        setName = name;
    }

    private void setFile(String name){
        fileName = name;
        file = new File(fileName);
        if (!file.exists()){
            file.mkdirs();
        }
    }



    public SavedData(String fileName,String setName){
        setFile(fileName);
        setSetName(setName);
        try {
            envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setTransactional(true);

            env = new Environment(file,envConfig);
            dbCongig = new DatabaseConfig();
            dbCongig.setAllowCreate(true);
            dbCongig.setTransactional(true);

            db = env.openDatabase(null,setName,dbCongig);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
