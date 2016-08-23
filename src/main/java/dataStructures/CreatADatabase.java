package dataStructures;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;

/**
 * Created by chu on 16-8-22.
 */
public class CreatADatabase {
    Environment env;

    public Environment getEnv() {
        return env;
    }

    Database db;
    private EnvironmentConfig envConfig;
    private DatabaseConfig dbCongig;
    private String fileName;
    private File file;
    private String dbName;

    private void setDbName(String name){
        dbName = name;
    }

    private void setFile(String name){
        fileName = name;
        file = new File(fileName);
        if (!file.exists()){
            file.mkdirs();
        }
    }




    public CreatADatabase(String fileName, String dbName){
        setFile(fileName);
        setDbName(dbName);
        try {
            envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setTransactional(true);

            env = new Environment(file,envConfig);
            dbCongig = new DatabaseConfig();
            dbCongig.setAllowCreate(true);
            dbCongig.setTransactional(true);

            db = env.openDatabase(null,dbName,dbCongig);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
