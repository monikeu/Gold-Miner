import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Monikeu on 18.01.2017.
 */
public class RankManager extends JButton implements ActionListener {

    public ArrayList<RankTableElement> rankTable;
    private JButton backButton;
    private ButtonPanel buttonPanel;
    private int minScore;
    private JLabel rank[];
    private JLabel rankHeader;

    public RankManager(ButtonPanel buttonPanel){
        this.buttonPanel = buttonPanel;
        rankTable = new ArrayList<RankTableElement>();

        readTableFromFile();
        initVars();
        addComponents();
        setParams();

    }

    private void initVars(){
        rankHeader  = new JLabel("Rank");
        rank = new JLabel[10];
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        minScore = rankTable.get(9).getPoints();
    }

    private void addComponents(){

        add(rankHeader);

        for(int i=0; i<10;i++) {
            rank[i] = new JLabel(printRank(i));
            add(rank[i]);
        }

        add(backButton);
    }

    private void setParams(){
        setPreferredSize(new Dimension(600,500));
        setLayout(new GridLayout(12,1,10,10));
    }

    private void readTableFromFile(){

        Object rankTableElement = new Object();
        try {
            FileInputStream fr = new FileInputStream("files/rank.txt");
            ObjectInputStream br = new ObjectInputStream(fr);

            for(int i=0;i<10;i++)
                if ((rankTableElement = br.readObject()) != null)
                    rankTable.add((RankTableElement) rankTableElement);

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String printRank(int i){
        return i+1 + ". " + rankTable.get(i).getName() + " " +  rankTable.get(i).getPoints()+ '\n';
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == backButton){
            this.setVisible(false);
            buttonPanel.setVisible(true);
        }
    }

    public void sort(){
        Collections.sort(rankTable);
        Collections.reverse(rankTable);
        deleteFromListIfLongerThan10();
    }

    public void saveRankToFile(){
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream("files/rank.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for(int i=0;i<10;i++) {
                objectOutputStream.writeObject(rankTable.get(i));
            }
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateRank(){
        for(int i=0; i<10;i++) {
            rank[i].setText(printRank(i));
        }
    }

    private void deleteFromListIfLongerThan10(){

        for(int i=10;i< rankTable.size() ; i++){
            rankTable.remove(i);
        }
    }

    public int getMinScore() {
        return minScore;
    }

}

