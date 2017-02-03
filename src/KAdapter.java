import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Monikeu on 20.01.2017.
 */
public class KAdapter extends KeyAdapter {

    ButtonPanel buttonPanel;

    public KAdapter(ButtonPanel buttonPanel){
        this.buttonPanel = buttonPanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        buttonPanel.gameManager.keyTyped(e);
    }
}
