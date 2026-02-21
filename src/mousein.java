import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mousein extends MouseAdapter {
    JFrame frac;
    mousein(JFrame frac){
        this.frac=frac;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        frac.requestFocusInWindow();
    }

}
