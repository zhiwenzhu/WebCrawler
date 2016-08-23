package getUrlsMethods;

/**
 * Created by chu on 16-8-22.
 */

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import java.net.URL;

/**
 *
 */
public class GetUrls {
    public static void getUrls(String url){
        Parser parser = null;
        try {
            parser = new Parser(url);
        }catch (ParserException e){
//            e.printStackTrace();
            System.out.println("Please enter a correct url");

        }
    }

}
