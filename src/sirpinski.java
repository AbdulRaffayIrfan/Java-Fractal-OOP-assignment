import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;

public class sirpinski extends JPanel{

    private int ORDER =8;
    JFrame frame;
    keyin key;
    Point2D.Double p1,p2,p3;
    int sidelength=300;
    boolean defaultlength=true;

    sirpinski(JFrame frame){
        this.frame= frame;
        this.key= new keyin(this);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(650,600));
        frame.addMouseListener(new mousein(frame));
        frame.addKeyListener(key);
        textfeild_sidelength();
        sound();
    }

    sirpinski(JFrame frame ,int sidelength){
        this.frame= frame;
        this.key= new keyin(this);
        frame.addKeyListener(key);
        defaultlength=false;
        this.sidelength=sidelength;
        sound();
    }

    public void textfeild_sidelength(){
        JTextField textField = new JTextField(4);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredText = textField.getText();
                System.out.println("Entered text: " + enteredText);
                defaultlength=false;
                sidelength=Integer.parseInt(enteredText);
            }
        });

        textField.setBounds(135, 13, 70, 20);
        this.add(textField);
    }

    private void sound(){
        try {
            URL soundFile = sirpinski.class.getResource("/sound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            gainControl.setValue(-20.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //clip.start();

        } catch (UnsupportedAudioFileException e) {
            System.err.println("The specified audio file is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading the audio file.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("A sound line is unavailable.");
            e.printStackTrace();
        }
    }

    // getter and setter for the number of steps of
    // recurion ie steps to reach base case
    public int getbasecase(){
        return ORDER;
    }
    public void setbasecase(int ord){
        this.ORDER=ord;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);

        if(defaultlength){
            p1 = new Point2D.Double(getWidth() / 2 + 5, 40);
            p2 = new Point2D.Double(30, getHeight() - 40);
            p3 = new Point2D.Double(getWidth() - 20, getHeight() - 40);
        }else{
            int h = (int) (sidelength * Math.sqrt(3) / 2);
            p2 = new Point2D.Double(30,560);
            p1 = new Point2D.Double(p2.x+sidelength/2,p2.y-h );
            p3 = new Point2D.Double(p2.x+sidelength,p2.y);
        }

        run(g2d, ORDER, p1, p2, p3);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("Side Length", 20, 30);
        g.drawString("W to increase level", 20, 50);
        g.drawString("S to decrease level", 20, 70);
        repaint();
    }

    private void run(Graphics2D g2d, int order, Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        if (order == 0) {
            // Base case: draw the filled triangle
            Path2D.Double triangle = new Path2D.Double();
            triangle.moveTo(p1.x, p1.y);
            triangle.lineTo(p2.x, p2.y);
            triangle.lineTo(p3.x, p3.y);
            triangle.closePath();
            g2d.fill(triangle);
        } else {
            // Recursive step: calculate midpoints and call function for 3 new triangles
            Point2D.Double p12 = midpoint(p1, p2);
            Point2D.Double p23 = midpoint(p2, p3);
            Point2D.Double p31 = midpoint(p3, p1);

            // Recursively draw three smaller triangles
            run(g2d, order - 1, p1, p12, p31);
            run(g2d, order - 1, p12, p2, p23);
            run(g2d, order - 1, p31, p23, p3);
        }
    }

    public void decreaseorder(){
        if (ORDER>0&&ORDER<13){
            this.ORDER--;
        }
        frame.setTitle("fractal level "+this.ORDER);
    }

    public void increaseorder(){
        if (ORDER>=0&&ORDER<12){
            this.ORDER++;
        }
        frame.setTitle("fractal level "+this.ORDER);
    }

    private Point2D.Double midpoint(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

}
