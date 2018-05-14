/**
 * BlockDisplay class provided for Tetris project
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// Used to display the contents of a game board
public class BlockDisplay implements KeyListener
{
    private static final Color BACKGROUND = Color.BLACK;

    private int score = 0;
    
    private BoundedGrid<Block> board;
    private JFrame frame;
    private ArrowListener listener;
    private BufferedImage image;
    
   
    public BlockDisplay(BoundedGrid<Block> board)
    {
        this.board = board;

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        frame = new JFrame();
        image = new BufferedImage(board.getNumCols()*30,board.getNumRows()*30,BufferedImage.TYPE_INT_RGB);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.add(new JLabel(new ImageIcon(image)));
        showBlocks();
        frame.pack();
        frame.setVisible(true);
        
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void visible()
    {
        frame.setVisible(!frame.isVisible());
    }

    public void incrementScore(int amount)
    {
        score += amount;
    }
    
    //Redraws the board to include the pieces and border colors.
    public void showBlocks()
    {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(BACKGROUND);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(new Color(150,0,255));
        graphics.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
        graphics.dispose();
        for (int row = 0; row < image.getHeight()/30; row++)
            for (int col = 0; col < image.getWidth()/30; col++)
            {
                Location loc = new Location(row, col);
                Block square = board.get(loc);
                if (square != null)
                {
                    graphics = image.createGraphics();
                    graphics.setColor(square.getColor());
                    graphics.fillRect(col*30+1, row*30+1, 28, 28);
                    graphics.dispose();
                }
        }
        frame.repaint();
        frame.setTitle("Tetris | Score: " + score);
    }

    // Sets the title of the window.
    public void setTitle(String title)
    {
        frame.setTitle("Tetris | Score: " + score);
    }

    public void resetScore()
    {
        score = 0;
    }
    
    public void keyTyped(KeyEvent e)
    {
    }

    public void keyReleased(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
    }

    public void setArrowListener(ArrowListener listener)
    {
        this.listener = listener;
    }
    
    public int getCurrentSpeedMilliseconds()
    {
        if (score <= 1000) 
           return 1000;
        else if (score <= 2000 ) 
           return 950;
        else if (score <= 3000) 
           return 900;
        else if (score <= 4000) 
           return 850;
        else if (score <= 5000) 
           return 800;
        else if (score <= 6000) 
           return 750;
        else if (score <= 7000) 
           return 700;
        else if (score <= 8000)    
           return 650;
        else if (score <= 9000) 
           return 600;
        else if (score <= 10000) 
           return 550;
        else if (score <= 11000) 
           return 500;
        else if (score <= 12000) 
           return 450;
        else if (score <= 13000) 
           return 400;
        else if (score <= 14000) 
           return 350;
        else if (score <= 15000) 
           return 300;
        else if (score <= 16000) 
           return 250;
        else if (score <= 17000) 
           return 200;
        else if (score <= 18000) 
           return 150;
        else if (score <= 19000) 
           return 100;
        else 
           return 50;
    }
}