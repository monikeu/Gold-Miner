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
public class GameManager extends JPanel implements ActionListener {

    private final double  beginX = 300, //beginning of line coord
            beginY = 50,
            r = 50; // default value fot the length of line == radius of line movements
    private final int width =600,// window params
            height = 500;
    private double endOfLinex = 250, //end of the line coords
            endOflineY =40,
            shotr = r; // length of line during game
    private  Timer timer;
    private float theta = 0; // angle
    private long  startTime; // time when game has started
    private boolean count = true, // if true then angle of line is increased
            shotDetected = false, // did the player shot?
            reverse =false, // is line drawn forwards(false) or backwards(true)?
            collision = false,  //is line coliided with rock? yes(true), no(false)
            keyEnabled = true;
    RockGenerator rockGenerator;
    ArrayList<Rock> rocks;
    private Rock rockCollided; // rock which is collided with line right now
    private int diffx,         /* the difference between coords of left upper corner of the rock
                                 and end of line coords when rock is collided with line */
            diffy,
            points = 0, //points scored in one level
            pointsRequired = 0, // points required to win
            defaultdelay = 16,
            time =0,
            level=0,
            total=0;

    private ButtonPanel buttonPanel;
    private Image background,player;
    private InsertScore insertScore;

    public GameManager(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
        initVars();
        setParams();
        initLevel();
        timer.start();
    }

    public void initVars(){

        player = (new ImageIcon("sprites/player.png") ).getImage();
        background  = (new ImageIcon("sprites/background.png") ).getImage();
        rockGenerator = new RockGenerator();
        timer = new Timer(defaultdelay, this);
    }

    private  void setParams(){
        setPreferredSize(new Dimension(width,height));
        setDoubleBuffered(true);
    }

    private void initLevel(){
        setVariables();
        rocks = rockGenerator.generate(pointsRequired);
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

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        drawBackground(g);
        drawRocks(g);
        drawPlayer(g);
        drawRotatedLine(g);
        drawText(g);

    }

    private void drawBackground(Graphics g){
        g.drawImage(background,0,0 ,this);
    }

    private void drawRocks(Graphics g){

        for(Rock rock : rocks){

            if(rock.isVisible()){
                g.drawImage(rock.getImage(), rock.getXCoord(), rock.getYCoord(), this);
            }
        }

    }

    private void drawPlayer(Graphics g){
        g.drawImage(player, 275,0,this);
    }

    private void drawRotatedLine(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        Line2D line = new Line2D.Double(endOfLinex, endOflineY,beginX, beginY);
        g2.setStroke(new BasicStroke(5));
        g2.draw(line);
    }

    private void drawText(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Verdana", Font.PLAIN, 18));
        g.drawString("Time: " + (60 - time) + " Level: "+ level, 420, 20 );
        g.drawString("Score: " + points + " Required: " + pointsRequired ,10 ,20);
    }

    private void usualLine(){
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

    private void longLine(){
        if(reverse)
            shotr-=1;
        else {
            shotr+=1;
            timer.setDelay(1);
        }

        if(endOfLinex < 0 || endOflineY < 0 || endOfLinex >width|| endOflineY >height) {
            reverse = true;
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
            rockCollided.setX( (int)endOfLinex -  diffx );
            rockCollided.setY((int)endOflineY - diffy);
        }


        if(theta <= 90) {
            endOfLinex = beginX - (shotr * Math.cos(Math.toRadians(theta)));
        }
        else {
            endOfLinex = beginX + (shotr * Math.cos(Math.toRadians(180 - theta)));
        }
        endOflineY = beginY + (shotr * Math.sin(Math.toRadians(theta)));

        if(!collision) {
            if(checkCollision()){
                collision = true;
                reverse = true;
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

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(!shotDetected) {
            timer.setDelay(defaultdelay);
            usualLine();
        }
        else {
            longLine();
        }

        if( (int)((System.currentTimeMillis()- startTime)/1000) > time )
            time++;

        if(checkIfGameWonOrOver()) // if true game is over
            gameOver();

        repaint();
    }

    private boolean checkIfGameWonOrOver(){
        if(time >=60){
            if(points >= pointsRequired){
                shotDetected=false;
                reverse = false;
                shotr=r;
                initLevel();
            }
            else {

                total+=points;
                return true;
            }

        }
        return false;
    }

    private void gameOver(){
        keyEnabled = false;
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

    public void keyTyped(KeyEvent e){
        e.getKeyCode();
        if(e.getKeyCode() == KeyEvent.VK_SPACE && keyEnabled){
            shotDetected = true;
        }
        if(e.getKeyCode()== KeyEvent.VK_Q && keyEnabled){
            this.setVisible(false);
            buttonPanel.setVisible(true);
        }
    }

}



