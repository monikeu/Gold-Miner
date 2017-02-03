import javax.swing.*;
/**
 * Created by Monikeu on 07.01.2017.
 */
//main frame
public class MainPanel extends JFrame {

    private ButtonPanel buttonPanel;
    private KAdapter keyadapter;

    public MainPanel() {
        initVar();
        addComponents();
        setParams();
    }

    private void initVar(){
        buttonPanel = new ButtonPanel(this);
        keyadapter = new KAdapter(buttonPanel);
    }

    private  void  addComponents(){
        add(buttonPanel);
        addKeyListener(keyadapter);
    }

    private void setParams(){
        setResizable(false);
        pack();
        setTitle("Gold Miner");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
    }
}