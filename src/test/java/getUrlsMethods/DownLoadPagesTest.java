package getUrlsMethods;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.groovy.transform.SynchronizedASTTransformation;
import org.junit.Assert;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by chu on 16-8-24.
 */
public class DownLoadPagesTest {
    @Test
    public void getPagename() throws Exception {

    }

    @Test
    public void saveToLocal() throws Exception {

    }

//    @Test
//    public void downLoadPage() throws Exception {
//        DownLoadPages pages = new DownLoadPages();
//        pages.makeNewFile();
//        String s = pages.downLoadPage("http://www.163.com");
//        System.out.println(s);
//    }

    @Test
    public void testGetFileContent() throws Exception{
        File file = new File("file0");
//        if (file.exists() && file.isFile()){
//            System.out.println(file.length());
//        }else {
//            System.out.println("file not exists");
//        }

        System.out.println(file.exists());
        System.out.println(file.isFile());

        System.out.println(file.length());

        File file1 = new File("file0","temp\\www.163.com.html");

        System.out.println(file1.exists());
        System.out.println(file1.length());
    }

    @Test
    public void testGetDirSize() throws Exception{
//        File file = new File("file0");
//        System.out.println(file.exists());

        DownLoadPages pages = new DownLoadPages();

        System.out.println(pages.getDirSize());
    }

//测试byte数组转化String类；
    @Test
    public void testByteToStringBuffer() throws Exception{
        byte[] bytes = {1,2,3,4,5,6,7,8,9,10};
        StringBuffer  buffer = new StringBuffer();
        for (byte b : bytes){
            buffer.append(b);
        }

        System.out.println(buffer.toString());
        System.out.println(buffer);
    }

//测试 MessageDigest类的基本用法；
    @Test
    public void testMessageDigest() throws Exception{
        String s = "zhuzhiwen4524{{dasasffffasds WEQEQWDSsddsdfsfgdgrtyhthytutyutyuthreuye5t6y5e6ytrhyERTET    TRYRYRTYTY U??///GG";
        System.out.println(s.length());

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(s.getBytes());

        String result = null;

        StringBuffer buffer = new StringBuffer();


        for (byte b : bytes){
            buffer.append(b);
        }

        result = buffer.toString();

        System.out.println(result);

        System.out.println(md.getAlgorithm());
        System.out.println(md.getDigestLength());
        System.out.println(bytes.length);
        System.out.println(result.length());

//        System.out.println(md.);

    }

//测试某个URL对应网页信息体源码的信息提取；
    @Test
    public void testGetBodyToBytes() throws Exception{
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://www.163.com");
        byte[] result = null;

        int status = httpClient.executeMethod(getMethod);
        if (status == HttpStatus.SC_OK){
            result = getMethod.getResponseBody();
        }

        System.out.println(result.length);

        String s = new String(result);
        System.out.println(s.length());

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] bytes = md.digest(result);
        String mdResult = null;
        StringBuffer buffer = new StringBuffer();


        for (byte b : bytes){
            buffer.append(b);
        }

        mdResult = buffer.toString();

        System.out.println(mdResult);
        System.out.println(mdResult.length());
        System.out.println(bytes.length);
    }

//测试URL和String之间的关系；
    @Test
    public void testUrlToString() throws Exception{
        String s = "http://www.baidu.com";
        String s1 = "www.baidu.com";

        URL url = new URL(s);
//        URL url1 = new URL(s1);

        System.out.println(url);
//        System.out.println(url1);

//        Assert.assertTrue(s == url.toString());

        System.out.println(url.toString());
        System.out.println(s.equals(url.toString()));
        System.out.println(s == url.toString());

        System.out.println(s.compareTo(url.toString()));

//只有使用equals方法才能比较是否相等；“==”用来判断两个句柄是否指向同一个对象；

        String s2 = "http://www.baidu.com";

        System.out.println(s == s2);
        System.out.println(s.equals(s2));


    }

}