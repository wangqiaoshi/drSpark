package cn.qs.bean;

/**
 * Created by wqs on 16/11/4.
 */
public class Memory {

    private String name;
    private double memorySize;
    private double y;

    public Memory(String name,double memorySize){
        this.name = name;
        this.memorySize = memorySize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(double memorySize) {
        this.memorySize = memorySize;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public String toString(){
        return "{name:\""+name+"\",y:"+y+"}";
    }

}


