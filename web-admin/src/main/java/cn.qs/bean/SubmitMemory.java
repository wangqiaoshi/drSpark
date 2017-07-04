package cn.qs.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqs on 16/11/6.
 */
public class SubmitMemory {

    private String name;
    private List<Point> points = new ArrayList<Point>();

    public SubmitMemory(String name){
        this.name = name;
    }

    public void addPoint(long timestamp,float count){
        Point point = new Point(timestamp,count);
        points.add(point);
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        int size = points.size();
        for(int i=0;i<size;i++){

            if(i==size-1)
                buffer.append(points.get(i));
            else
                buffer.append(points.get(i)).append(",");

        }
        buffer.append("]");
        return String.format("{name:\"%s\",data:%s}",name,buffer.toString());
    }

}
