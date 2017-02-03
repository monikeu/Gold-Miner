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
    private JButton okButton;
    private JTextField textField;
    private String name = null;
    private boolean inserted = false;


    public  InsertScore(ButtonPanel buttonPanel, int score){
        this.buttonPanel = buttonPanel;
        this.score = score;
        setVars();
        setParams();
        addComponents();
    }

    private void setVars(){
        okButton = new JButton( "Ok");
        okButton.addActionListener(this);

        textField = new JTextField();
        textField.addActionListener(this);
    }

    private void addComponents(){
        add(okButton);
        add(textField);
    }

    private  void  setParams(){
        setPreferredSize(new Dimension(600,500));
        setLayout(new GridLayout(2,1));
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
            buttonPanel.rankManager.rankTable.add(9,new RankTableElement(score,name));
            buttonPanel.rankManager.sort();
            buttonPanel.rankManager.updateRank();
            buttonPanel.rankManager.saveRankToFile();

        }
    }

}
