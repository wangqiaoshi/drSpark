package cn.qs.bean;

/**
 * Created by wqs on 16/11/3.
 */
public class TotalCore {

    private String name;
    private double cores;
    private double y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCores() {
        return cores;
    }

    public void setCores(double cores) {
        this.cores = cores;
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
