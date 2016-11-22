import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqs on 16/11/16.
 */
public class JavaTest {



    public static void main(String[] args){


        Map<String,String> map = new HashMap<String,String>();
        System.out.println(System.identityHashCode(map));
        JavaTest test = new JavaTest();
        test.put(map);

    }



    public void put(Map<String,String> map){
        map.put("wang","24");
        System.out.println(System.identityHashCode(map));

    }

}
