package getUrlsMethods;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by chu on 16-8-25.
 */
public class GetUrlsTest {
    @Test
    public void getUrls() throws Exception {

        GetUrls getUrls = new GetUrls();
        getUrls.getUrls("http://www.baidu.com");

        int size = getUrls.getLinkSet().size();

        System.out.println(size);

        Iterator iterator = getUrls.getLinkSet().iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

}