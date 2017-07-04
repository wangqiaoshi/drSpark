package cn.qs.bean;

import cn.qs.utils.Time;

/**
 * Created by wqs on 16/11/6.
 */
public class Point {
    private Long timestamp;
    private float count=0;



    public String toString(){
        return String.format("[%d,%f]",timestamp,count);
    }
    public Point(long timestamp,float count){
        this.timestamp = Time.convertToHour(timestamp);
        this.count = count;
    }
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public void addCount(float c){
        count+=c;
    }
}
