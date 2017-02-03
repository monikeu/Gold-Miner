import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Monikeu on 17.01.2017.
 */


public class ButtonPanel extends JButton implements ActionListener
{
    private JButton startButton;
    private JButton exitButton;
    private JButton rankButton ;
    public GameManager gameManager;
    public MainPanel mainPanel;
    public RankManager rankManager;


    public ButtonPanel( MainPanel mainPanel) {

        this.mainPanel=mainPanel;
        initVar();
        addComponents();
        setParams();
    }

    private void initVar(){
        rankManager =new RankManager(this);

        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        rankButton = new JButton("Rank");

        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        rankButton.addActionListener(this);

        startButton.setPreferredSize(new Dimension(100,50));
        exitButton.setPreferredSize(new Dimension(100,50));
        rankButton.setPreferredSize(new Dimension(100,50));
    }

    private void addComponents(){
        add(startButton);
        add(rankButton);
        add(exitButton);
    }

    private void  setParams(){
        setPreferredSize(new Dimension(600,500));
        setLayout(new GridLayout(3,1, 50,50));
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == exitButton)
            System.exit(0);
        else if(source == startButton){
            gameManager = new GameManager(this);
            mainPanel.add(gameManager);
            this.setVisible(false);
        }
        else if(source == rankButton){
            mainPanel.add(rankManager);
            rankManager.setVisible(true);
            this.setVisible(false);
        }
    }

}
