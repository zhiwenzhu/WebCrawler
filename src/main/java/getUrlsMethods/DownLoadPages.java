package getUrlsMethods;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.groovy.vmplugin.v7.IndyInterface;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chu on 16-8-23.
 */
public class DownLoadPages {
    int i = 0;
    File file;
    byte[] responseBody;
    String pageName;

    public String getPageMd5Result() {
        return pageMd5Result;
    }

    private String pageMd5Result;



    public void makeNewFile(){
        file = new File("file"+i);
        if (!file.exists()){
            file.mkdirs();
            i++;
        }
    }

    public long getDirSize(){
        File file = new File("file"+i);
        long size = 0;
        if (file.exists() && file.isDirectory()){
            size = file.length();
            File[] files = file.listFiles();
            for (int j = 0;j < files.length;j++){
                if (files[j].isFile()){
                    size += files[j].length();
                }

            }
        }

        return size;
    }

    public String getPagename(String url,String contentTYpe){
        url = url.substring(7);
        if (contentTYpe.indexOf("html") != -1){
            url = url.replaceAll("[\\?/:*|<>\"]","_") + ".html";
            return url;
        }else {
            return url.replaceAll("[\\?/:*|<>\"]","_") + contentTYpe.substring(contentTYpe.lastIndexOf("/") + 1);
        }
    }

    public void saveToLocal(byte[] data,String pageName){
        if (getDirSize() > 1000000000){
            makeNewFile();
        }
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(new File(file,pageName)));

            for (int i = 0; i < data.length; i++) {
                outputStream.write(data[i]);
            }
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


//该方法需被拆成两个方法，主程序在调用的时候按顺序执行，使得不做重复的工作；
//程序先获得responseBody的内容，判断是否已经下载过该网页，如果没有则继续执行，如果有就直接跳过，执行下一条；

//    public String downLoadPage(String url){
//        String pageName = null;
//        HttpClient httpClient = new HttpClient();
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//        GetMethod getMethod = new GetMethod(url);
//
//        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
//        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
//
//        try {
//            int statusCode = httpClient.executeMethod(getMethod);
//            if (statusCode != HttpStatus.SC_OK){
//                System.err.println("Method failed:" + getMethod.getStatusLine());
//                pageName = null;
//            }
//
//            byte[] responseBody = getMethod.getResponseBody();
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] bytes = md5.digest(responseBody);
//            StringBuffer buffer = new StringBuffer();
//            for (byte b : bytes){
//                buffer.append(b);
//            }
//
//            pageMd5Result = buffer.toString();
//
//            pageName = "temp\\" + getPagename(url,getMethod.getResponseHeader("Content-Type").getValue());
//
//            saveToLocal(responseBody,pageName);
//
//        }catch (HttpException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//
//        return pageName;
//    }


    public boolean pageExist(String url,Environment env,Database db){
        boolean exist = false;
//        Environment env = null;
//        Database db = null;
//        Cursor cursor = null;

//        try {
//            File file = new File(fileName);
//            if (file.exists()){
//                env = new Environment(new File(fileName),null);
//                db = env.openDatabase(null,dbName,null);
//                cursor = db.openCursor(null,null);
//            }
//
//        }catch (DatabaseException e){
//            e.printStackTrace();
//        }

        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);

        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK){
                System.err.println("Method failed:" + getMethod.getStatusLine());

            }

            responseBody = getMethod.getResponseBody();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(responseBody);
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes){
                buffer.append(b);
            }

            pageMd5Result = buffer.toString();

            pageName = "temp\\" + getPagename(url,getMethod.getResponseHeader("Content-Type").getValue());
        }catch (Exception e){
            e.printStackTrace();
        }

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        EntryBinding keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        keyBinding.objectToEntry(pageMd5Result,keyEntry);

        Cursor cursor = db.openCursor(null,null);

        OperationStatus status = cursor.getSearchKey(keyEntry,dataEntry,LockMode.DEFAULT);

        if (status == OperationStatus.SUCCESS){
            exist = true;
        }

        try {
            if (cursor != null){
                cursor.close();
            }
            if (db != null){
                db.close();
            }

            if (env != null){
                env.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return exist;
    }


    public String downLoadPage(String url){

        if (responseBody != null){
            saveToLocal(responseBody,pageName);
        }

        return pageName;

    }





}
