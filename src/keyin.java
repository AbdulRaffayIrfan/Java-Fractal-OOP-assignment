
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyin implements KeyListener {

    sirpinski frac;
    public keyin(sirpinski frac) {
        this.frac=frac;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            frac.decreaseorder();
            //System.out.println("pressed");
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            frac.increaseorder();
            //System.out.println("pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
