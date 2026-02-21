import javax.swing.*;

void main() {
    System.setProperty("sun.java2d.opengl", "true");
    JFrame frame =new JFrame("fractal level 8");
    frame.setDefaultCloseOperation(3);
    //frame.setSize(600,600);
    frame.setResizable(false);
    frame.add(new sirpinski(frame));
    frame.pack();
    frame.setVisible(true);
    frame.requestFocusInWindow();
}