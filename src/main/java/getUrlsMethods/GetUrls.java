package getUrlsMethods;

/**
 * Created by chu on 16-8-22.
 */

import com.sleepycat.je.Database;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class GetUrls {

    private Parser parser;

    public Set<String> getLinkSet() {
        return linkSet;
    }

    private NodeFilter filter = new NodeClassFilter(LinkTag.class);


    private Set<String> linkSet = new HashSet<String>();

    public Set<String> getUrls(String url){
        try {
            parser = new Parser(url);

            NodeList list = parser.extractAllNodesThatMatch(filter);
            for (int i = 0;i < list.size();i++){
                Node node = list.elementAt(i);

                if (node instanceof LinkTag){
                    LinkTag linkTag = (LinkTag) node;

                    String linkUrl = linkTag.getLink();
//把抓取的url，通过判断是否曾经抓取过，放到数据库中；同时实现网页内容的抓取和存储要调用DownloadPages方法；（DownloadPages方法
//需要修改； ）

//这里只简单实现抓取到hashset中；
                    linkSet.add(linkUrl);

                }
            }

        }catch (ParserException e){
            e.printStackTrace();
        }


        return linkSet;


    }



}
