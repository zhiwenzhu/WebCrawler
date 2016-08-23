package getUrlsMethods;

import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
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

    @Test
    public void downLoadPage() throws Exception {
        DownLoadPages pages = new DownLoadPages();
        pages.makeNewFile();
        String s = pages.downLoadPage("http://www.163.com");
        System.out.println(s);
    }

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

}