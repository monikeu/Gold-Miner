import org.w3c.dom.css.Counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by Monikeu on 07.01.2017.
 */
public class Manager extends JPanel implements ActionListener {

    private final double  beginX = 300,
            beginY = 50,
            r = 50;
    private double endOfLinex = 250,
            endOflineY =40,
            shotr = r;
    private  Timer timer;
    private final int width =600,
            height = 500;
    private float theta = 0;
    private  long  startTime;
    private boolean count = true, // if true then angle is increased
            shotDetected = false,
            reverse =false, // is line drawn forwards(false) or backwards(true)?
            collision = false,
            keyEnabled = true;
    RockGenerator rockGenerator;
    ArrayList<Rock> rocks;
    private Rock rockCollided;
    private int diffx,
            diffy,
            points = 0,
            pointsRequired = 0,
            defaultdelay = 16,
            time =0,
            level=0,
            total=0;

    private ButtonPanel buttonPanel;
    private Image background,player;
    private InsertScore insertScore;

    public Manager(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public void start(){

        player = (new ImageIcon("src/player.png") ).getImage();
        background  = (new ImageIcon("src/background.png") ).getImage();
        setPreferredSize(new Dimension(width,height));
        setDoubleBuffered(true);
        rockGenerator = new RockGenerator();
        drawlevel();

        timer = new Timer(defaultdelay, this);

        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        g.drawImage(background,0,0 ,this);
        drawRocks(g);
        g.drawImage(player, 275,0,this);
        drawRotatedLine(g);
        drawText(g,"Score: " + points + " Required: " + pointsRequired ,10 ,20 );
        drawText(g,"Time: " + time + " Level: "+ level, 420, 20);
        if(checkIfGameWonOrOver()) // if true game is over
            drawGameOver();


    }

    private void drawRotatedLine(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        Line2D line = new Line2D.Double(endOfLinex, endOflineY,beginX, beginY);
        g2.setStroke(new BasicStroke(5));
        g2.draw(line);
        //System.out.print("Rysuję linie \n");
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(!shotDetected) {
            timer.setDelay(defaultdelay);
            drawUsual();
        }
        else {
            drawLongLine();
            System.out.print("delay " + timer.getDelay() +"\n");
        }

        if( (int)((System.currentTimeMillis()- startTime)/1000) > time )
            time++;

        repaint();
    }

    private void drawUsual(){
        if (theta == 180) count = false;
        if(theta == 0) count = true;

        if(count) theta+=1;
        else theta-=1;

        if(theta <= 90) {
            endOfLinex = beginX - (r * Math.cos(Math.toRadians(theta)));
        }
        else {
            endOfLinex = beginX + (r * Math.cos(Math.toRadians(180 - theta)));
        }
        endOflineY = beginY + (r * Math.sin(Math.toRadians(theta)));
    }

    private void drawLongLine(){
       if(reverse) {shotr-=1; System.out.print(("Zmniejszam \n"));}
       else {
            shotr+=1;
            timer.setDelay(1);
       }

       if(endOfLinex < 0 || endOflineY < 0 || endOfLinex >width|| endOflineY >height) {
           reverse = true;
           System.out.print("jestem tutejjjjjjjjjjj\n");
           timer.setDelay(1);
       }

       if(shotr == r){
           if(collision){
               rockCollided.setVisibility(false);
               points+=rockCollided.getValue();
               collision = false;
               timer.setDelay(defaultdelay);
           }
           reverse = false;
           shotDetected = false;
           return;
       }

       if(collision){
           System.out.print("jestem tutej 1 ustawiam nowe współrzedne obrazka \n");
           rockCollided.setX( (int)endOfLinex -  diffx );
           rockCollided.setY((int)endOflineY - diffy);
       }


        if(theta <= 90) {
            System.out.print("jestem tutej \n");
            endOfLinex = beginX - (shotr * Math.cos(Math.toRadians(theta)));
        }
        else {
            System.out.print("jestem tutej 3 \n");
            endOfLinex = beginX + (shotr * Math.cos(Math.toRadians(180 - theta)));
        }
        endOflineY = beginY + (shotr * Math.sin(Math.toRadians(theta)));

        System.out.print("j estem tutej 2 \n");
        if(!collision) {
            if(checkCollision()){
                collision = true;
                reverse = true;
            }
        }

    }

    public void keyTyped(KeyEvent e){
        e.getKeyCode();
        if(e.getKeyCode() == KeyEvent.VK_SPACE && keyEnabled){
            shotDetected = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER && !keyEnabled){
            reset();
        }

        if(e.getKeyCode()== KeyEvent.VK_Q && !keyEnabled){
            //System.exit(0);
            this.setVisible(false);
            buttonPanel.setVisible(true);
        }
    }

    private void drawRocks(Graphics g){

        for(Rock rock : rocks){

            if(rock.isVisible()){
               g.drawImage(rock.getImage(), rock.getXCoord(), rock.getYCoord(), this);
               //System.out.print("Rysuję " + rock.getKindOfRock() + " "+ rock.getXCoord() + " "+rock.getYCoord() +"\n");
            }
        }

    }

    //if the end of the line collides with any of objects
    private boolean checkCollision(){

        for( Rock rock : rocks) {

            if (rock.isVisible()) { //checking if rock is visible

                if ( ((int)endOflineY > rock.getYCoord()) &&
                        ((int)endOflineY < rock.getYCoord() + rock.getWidthNHeighth()) &&
                        ((int)endOfLinex > rock.getXCoord()) &&
                        ((int)endOfLinex < rock.getXCoord() + rock.getWidthNHeighth())) {

                    diffx = (int)endOfLinex - rock.getXCoord();
                    diffy = (int)endOflineY - rock.getYCoord();
                    timer.setDelay(defaultdelay * (rock.getWeight()+1) /2);

                    rockCollided = rock;
                    System.out.print("kolizja!!!! \n");

                    return true;
                }
            }
        }
        return false;
    }

    private void drawText(Graphics g, String text, int x, int y){
       g.setColor(Color.black);
       g.setFont(new Font("Verdana", Font.PLAIN, 18));
       g.drawString(text,x,y );
    }

    private boolean checkIfGameWonOrOver(){
        if(time >=60){
            if(points >= pointsRequired){
                shotDetected=false;
                reverse = false;
                shotr=r;

                drawlevel();
            }
            else {

                total+=points;
                return true;
            }

        }
        return false;
    }

    private void drawlevel(){
        setVariables();
        rocks = rockGenerator.generate(pointsRequired);
    }

    private void drawGameOver(){
        keyEnabled = false;
        //drawText(g, "GAME OVER", 240,200);
        //drawText(g, "Your score: " + total, 235,230);
        //drawText(g, "You've reached level " + level, 200,260);
        //drawText(g, "Press ENTER to play again", 190,290);
       // drawText(g,"Press Q to quit",220,320);
        timer.stop();
        this.setVisible(false);
        if(buttonPanel.rankManager.getMinScore() < total) {

            System.out.print(buttonPanel.rankManager.getMinScore()+ " " + total + "\n");
            insertScore = new InsertScore(buttonPanel, total);
            buttonPanel.mainPanel.add(insertScore);
            insertScore.setVisible(true);
        }
        else{
            buttonPanel.setVisible(true);
        }


    }

    private void setVariables(){
        level++;
        theta=0;
        total+=points;
        pointsRequired+=500;
        time=0;
        points =0;
        endOfLinex = 250;
        endOflineY =40;
        reverse =false;
        shotDetected = false;
        collision = false;
        startTime = System.currentTimeMillis();
    }

    private void reset(){
        level =0;
        total=0;
        pointsRequired=0;
        keyEnabled = true;
        drawlevel();
        timer.start();
    }

}



