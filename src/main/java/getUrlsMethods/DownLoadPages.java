package getUrlsMethods;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chu on 16-8-23.
 */
public class DownLoadPages {
    int i = 0;
    File file;


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

    public String downLoadPage(String url){
        String pageName = null;
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);

        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK){
                System.err.println("Method failed:" + getMethod.getStatusLine());
                pageName = null;
            }

            byte[] responseBody = getMethod.getResponseBody();

            pageName = "temp\\" + getPagename(url,getMethod.getResponseHeader("Content-Type").getValue());

            saveToLocal(responseBody,pageName);

        }catch (HttpException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return pageName;
    }


}
