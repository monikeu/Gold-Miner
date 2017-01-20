import javax.swing.*;
import java.awt.*;

/**
 * Created by Monikeu on 06.01.2017.
 */
public class Main {


    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainPanel ex = new MainPanel();
                ex.setVisible(true);
            }
        });
    }
}
