import java.io.Serializable;

/**
 * Created by Monikeu on 18.01.2017.
 */
public class RankTableElement implements Comparable<RankTableElement>,Serializable  {

    private int points;
    private String name;

    public  RankTableElement(){}

    public RankTableElement(int a, String b){
        points = a;
        name = b;
    }

    @Override
    public int compareTo(RankTableElement r){
        return points -r.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }





}
