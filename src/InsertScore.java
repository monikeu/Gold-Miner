import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Monikeu on 18.01.2017.
 */
public class InsertScore extends JButton implements ActionListener {

    private ButtonPanel buttonPanel;
    private int score;
    private  JButton okButton;
    private JTextField textField;
    private String name = null;
    private boolean inserted = false;


    public  InsertScore(ButtonPanel buttonPanel, int score){
        this.buttonPanel = buttonPanel;
        this.score = score;
        setPreferredSize(new Dimension(600,500));
        setLayout(new GridLayout(1,2));
        addComponents();
    }

    private void addComponents(){
        okButton = new JButton( "Ok");
        okButton.addActionListener(this);
        add(okButton);
        textField = new JTextField();
        textField.addActionListener(this);
        add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == okButton ){
            this.setVisible(false);
            buttonPanel.setVisible(true);
        }
        name = textField.getText();

        if(name!=null && !inserted){
            inserted = true;
            for(int i=0;i<10;i++){
                System.out.print("element " + i + ": " + buttonPanel.rankManager.rankTable.get(i).getName() + "\n");
            }

            buttonPanel.rankManager.rankTable.add(9,new RankTableElement(score,name));
            System.out.print("wpisałem " + score + " " + name + "\n");

            for(int i=0;i<10;i++){
                System.out.print("element " + i + ": " + buttonPanel.rankManager.rankTable.get(i).getName() + "\n");
            }

            buttonPanel.rankManager.sort();

            for(int i=0;i<10;i++){
                System.out.print("element " + i + ": " + buttonPanel.rankManager.rankTable.get(i).getName() + "\n");
            }

            buttonPanel.rankManager.updateRank();
            buttonPanel.rankManager.saveRankToFile();

        }
    }

}
