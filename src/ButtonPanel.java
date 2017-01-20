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
    public Manager manager;
    public MainPanel mainPanel;
    public RankManager rankManager;


    public ButtonPanel( MainPanel mainPanel) {

        //this.manager = manager;
        this.mainPanel=mainPanel;

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

        setPreferredSize(new Dimension(600,500));

        add(startButton);
        add(rankButton);
        add(exitButton);

        setLayout(new GridLayout(3,1, 50,50));


    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == exitButton)
            System.exit(0);
        else if(source == startButton){
            manager = new Manager(this);
            mainPanel.add(manager);
            manager.start();
            this.setVisible(false);
        }
        else if(source == rankButton){
            mainPanel.add(rankManager);
            rankManager.setVisible(true);
            this.setVisible(false);
        }
    }

}
