
import javax.swing.*;
import java.awt.*;

/**
 * Created by Monikeu on 07.01.2017.
 */
public class Rock {

    private KindOfRock kindOfRock;
    private int weight; /// 0-4
    private int widthNHeighth;
    private int xcoord, ycoord;
    private int value;
    private Image image;
    private boolean visibility = true;
    private int[][] valueTable = {  {50,100,150,200,250},
                                    {100,200,300,400,500},
                                    {150,300,450,600,750}  };  ///// sprawdź jak to wyświetla

    private int[] widthNHeigthTable = { 25,35,45,55,65 };



    public void setWidthNHeighth(int weight){
        widthNHeighth = widthNHeigthTable[weight];
    }

    public void setStone(int weight, int x, int y){
        kindOfRock = KindOfRock.STONE;
        image = (new ImageIcon("src/stone.png")).getImage();
           System.out.print("Tworzę " + kindOfRock + " \n") ;
        setRest(weight,  x,  y, 0);
    }

    public void setGold(int weight, int x, int y){
        kindOfRock = KindOfRock.GOLD;
        image = (new ImageIcon("src/gold.png")).getImage();
           System.out.print("Tworzę " + kindOfRock + " \n") ;
        setRest(weight,   x,  y, 1);

    }

    public void setDiamond(int weight, int x, int y){
        kindOfRock = KindOfRock.DIAMOND;
        image = (new ImageIcon("src/diamond.png")).getImage();
            System.out.print("Tworzę " + kindOfRock + " \n") ;
        setRest(weight,   x,  y, 2);
    }

    private void setRest(int weight,  int x, int y, int typeNumber){
        this.weight = weight;
        xcoord=x;
        ycoord=y;
        value = valueTable[typeNumber][weight];
            System.out.print("Ustawiono resztę \n") ;
        image = image.getScaledInstance(widthNHeighth,widthNHeighth,Image.SCALE_DEFAULT);
    }

    public void setX(int a){
        xcoord=a;
    }

    public void  setY(int a){
        ycoord=a;
    }

    public int getWeight(){
        return weight;
    }

    public int getWidthNHeighth(){
        return widthNHeighth;
    }

    public int getXCoord(){
        return xcoord;
    }

    public int getYCoord(){
        return ycoord;
    }

    public int getValue(){
        return value;
    }

    public Rectangle getBounds(){
        return new Rectangle( xcoord, ycoord ,widthNHeighth ,widthNHeighth );
    }

    public Image getImage(){
        return image;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean a){
        visibility = a;
    }

    public KindOfRock getKindOfRock(){
        return kindOfRock;
    }

}
