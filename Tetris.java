/*
 * Author: Gavin Uhran and Jonathon Fu
 * Version: April 12, 2018
 */

import java.util.ArrayList;

public class Tetris implements ArrowListener
{
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        tetris.play();
    }

    private BoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad activeTetrad;

    public Tetris()
    {
        grid = new BoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris");
        activeTetrad = new Tetrad(grid);
        display.setArrowListener(this);
    }

    public void upPressed()
    {
        activeTetrad.rotate();
        display.showBlocks();
    }

    public void downPressed()
    {
        activeTetrad.translate(1,0);
        display.showBlocks();
    }

    public void leftPressed()
    {
        activeTetrad.translate(0,-1);
        display.showBlocks();
    }

    public void rightPressed()
    {
        activeTetrad.translate(0,1);
        display.showBlocks();
    }
    
    public void spacePressed()
    {
        while (activeTetrad.translate(1,0))
            display.showBlocks();
    }

    public void play()
    {
        boolean forever = true;
        while (forever)
        {
            try { Thread.sleep(display.getCurrentSpeedMilliseconds()); } catch(Exception e) {}
            boolean flip = activeTetrad.translate(1,0);
            if (flip)
            {
                display.showBlocks();
                forever = true;
            }
            else
            {
                if (topRowsEmpty())
                {
                    clearCompletedRows();
                    activeTetrad = new Tetrad(grid);
                    display.showBlocks();
                    display.incrementScore(50);
                }
                else
                    restartGame();
            }
        }
    }

    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
            if (grid.get(new Location(row, i)) == null)
                return false;
        return true;
    }

    private void clearRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
        {
            grid.get(new Location(row,i)).removeSelfFromGrid();
            for (int k = row - 1; k > 0; k--)
            {
                Block temp = grid.get(new Location(k, i));
                if (temp != null)
                    temp.moveTo(new Location(k + 1, i));
            }
        }
    }

    private void clearCompletedRows()
    {
        int rowsCompleted = 0;
        for (int i = 0; i < grid.getNumRows(); i++)
            if (isCompletedRow(i))
            {
                clearRow(i);
                rowsCompleted++;
            }
        display.incrementScore(500 * rowsCompleted);
    }

    private boolean topRowsEmpty()
    {
        for (int k = 0; k < 2; k++)
            for (int i = 0; i < grid.getNumCols(); i++)
                if (grid.get(new Location(k, i)) != null)
                    return false;
        return true;
    }
    
    private void restartGame()
    {
        ArrayList<Location> locs = grid.getOccupiedLocations();
        for(int i = 0; i < locs.size(); i++)
            grid.remove(locs.get(i));
        display.setArrowListener(this);
        this.play();
        activeTetrad = new Tetrad(grid);
        display.resetScore();
    }
}