import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Monikeu on 07.01.2017.
 */
public class RockGenerator {

    private ArrayList<Rock> rocks;

    public ArrayList<Rock> generate(int minTotalValue){

        Random randomGenerator = new Random();
        Rock a = new Rock();
        rocks = new ArrayList<Rock>();
        int valueOfRocks=0;

        //adding first rock to the array
        //random weigth, xcoord and ycoord
        int x,y,weigth;
        weigth =randomGenerator.nextInt(5);
        a.setWidthNHeighth(weigth);
        while ( (x = (randomGenerator.nextInt(601 - a.getWidthNHeighth())  ))  > 600);
        while ( (y = (randomGenerator.nextInt(401- a.getWidthNHeighth()) + 100 ))  > 500);
        a.setStone(weigth,x,y);
        rocks.add(a);
        valueOfRocks+=a.getValue();

        while(valueOfRocks < minTotalValue + 1000) {

            a = new Rock();
            //random weigth, xcoord and ycoord
            weigth =randomGenerator.nextInt(5);
            a.setWidthNHeighth(weigth);
            while (( (x = (randomGenerator.nextInt(601 - a.getWidthNHeighth())  ))  > 600) && x>0);
            while (( (y = (randomGenerator.nextInt(401 -  a.getWidthNHeighth()) + 100  ))  > 500) && y>65);

            //creating random rocks where probability of getting diamond < gold < stone
            switch ( randomGenerator.nextInt(6)) {
                case 0:
                    a.setDiamond(weigth , x,y);
                    break;

                case 1:
                case 2:
                    a.setGold(weigth , x,y);
                    break;

                case 3:
                case 4:
                case 5:
                    a.setStone(weigth , x,y);
            }


            //add new rock only if it doesn't collide with existing ones, at the beginning there is one rock in the array
            int count = 0;
            for ( Rock rock : rocks ) {

                Rectangle b = rock.getBounds();

                if(!b.intersects(a.getBounds())){
                   count++;
                }
            }

            //add and increase total value of rocks
            if(count == rocks.size()) {
                rocks.add(a);
                valueOfRocks += a.getValue();
            }
        }
        return rocks;
    }
}
