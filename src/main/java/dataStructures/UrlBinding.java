package dataStructures;

import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

import java.net.URL;

/**
 * Created by chu on 16-8-23.
 */
public class UrlBinding {
    public void objectToEntry(Object object,TupleOutput to){
        URL url = (URL) object;
        to.writeString(url.toString());
    }

    public Object entryToObject(TupleInput ti){
        URL url = null;
        try {
            url = new URL(ti.readString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return url;

    }

}
