package com.zhiwen.crawler.common.strategy;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.common.util.ResolveFilePathUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhiwenzhu on 17/1/9.
 */
public class GetSeedUrlsStrategy implements CrawlerSrategy {

    public String getToCrawlerMessage(String filePath) {
        String[] dirs = ResolveFilePathUtil.splitFilePath(filePath);
//        dirs[0] = DirectoryPath.CONFIG_STORE_PATH;

        String tempDir = DirectoryPath.CONFIG_STORE_PATH + DirectoryPath.DATE_URL_FILE_PATH + dirs[2];
        String result = "";


        try {
            File file = new File(tempDir);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String s = "";
            int i = 0;
            for ( ; ; i++) {
                s = reader.readLine();
                if (s == null || dirs[3].equals(s)) {
                    break;
                }
            }

            if ((s = reader.readLine()) != null) {
                StringBuffer sb = new StringBuffer();
                sb.append("/").append(dirs[1]).append("/").append(dirs[2]).append("/").append(s);
                result = sb.toString();

                if (i > 50) {
                    reWriteFile(file, i);
                }
            }
            else {
                try {
                    File file1 = new File(DirectoryPath.URL_DATE_DIR_STORE_FILE);
                    BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                    for ( ; ; ) {
                        s = reader1.readLine();
                        if (s == null || dirs[2].equals(s));
                        break;
                    }

                    if ((s = reader1.readLine()) != null) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("/").append(dirs[1]).append("/").append(s).append("/");
                        File file2 = new File(DirectoryPath.CONFIG_STORE_PATH + DirectoryPath.DATE_URL_FILE_PATH + s);
                        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
                        if ((s = reader2.readLine()) != null) {
                            sb.append(s);
                            result = sb.toString();
                        }
                    }
                } catch (Exception e) {

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<String> getToCrawlerUrl(String filePath) {

        List urlList = new LinkedList();

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s = "";

            while ((s = reader.readLine()) != null) {
                urlList.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        String newPath = getToCrawlerMessage(filePath);
//        if (StringUtils.isNotBlank(newPath)) {
//            File file = new File(DirectoryPath.Next_CRAWLER_FILE_NAME);
//            try {
//                OutputStream ops = new FileOutputStream(file);
//                ops.write(newPath.getBytes());
//                ops.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


        return urlList;
    }

    private void reWriteFile(File file, int removedLine) {
        File tempFile = new File(DirectoryPath.CONFIG_STORE_PATH + DirectoryPath.DATE_URL_FILE_PATH + "temp");

        try {
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            for (int i = 0; i < removedLine; i++) {
                reader.readLine();
            }

            String s = "";
            while ((s = reader.readLine()) != null) {
                byte[] bytes = (s + "\n").getBytes();
                FileWriteUtil.writeToFile(tempFile, bytes, true);
            }

            file.delete();

            tempFile.renameTo(file);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File(DirectoryPath.CONFIG_STORE_PATH + DirectoryPath.DATE_URL_FILE_PATH + "test");

        GetSeedUrlsStrategy getSeedUrlsStrategy = new GetSeedUrlsStrategy();

        getSeedUrlsStrategy.reWriteFile(file, 5);
    }
}
