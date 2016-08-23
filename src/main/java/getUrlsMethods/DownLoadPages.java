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
    public String getPagename(String url,String contentTYpe){
        url = url.substring(7);
        if (contentTYpe.indexOf("html") != -1){
            url = url.replaceAll("[\\?/:*|<>\"]","_") + ".html";
            return url;
        }else {
            return url.replaceAll("[\\?/:*|<>\"]","_") + contentTYpe.substring(contentTYpe.lastIndexOf("/") + 1);
        }
    }

    public void saveToLocal(byte[] data,String pagePath){
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(new File(pagePath)));

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
        String pagePath = null;
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);

        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK){
                System.err.println("Method failed:" + getMethod.getStatusLine());
                pagePath = null;
            }

            byte[] responseBody = getMethod.getResponseBody();

            pagePath = "temp\\" + getPagename(url,getMethod.getResponseHeader("Content-Type").getValue());

            saveToLocal(responseBody,pagePath);

        }catch (HttpException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


        return pagePath;
    }


}
