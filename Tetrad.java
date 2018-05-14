/**
 * Tetrad class to be completed for Tetris project
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import java.util.ArrayList;

public class Tetrad
{
    private Block[] blocks;

    public Tetrad(BoundedGrid<Block> grid)
    {
        blocks = new Block[4];
        
        for (int i = 0; i < 4; i++)
            blocks[i] = new Block();
        
        Color color = Color.BLACK;
        Location[] locs = new Location[4];
        int shape = (int)(Math.random() * 8);
        
        if (shape == 0) //LINE BLOCK
        {
            color = Color.RED;
            locs[1] = new Location(0,4);
            locs[0] = new Location(1,4);
            locs[2] = new Location(2,4);
            locs[3] = new Location(3,4);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        else if (shape == 1) //PYRAMID BLOCK
        {
            color = Color.ORANGE;
            locs[1] = new Location(0,3);
            locs[0] = new Location(0,4);
            locs[2] = new Location(0,5);
            locs[3] = new Location(1,4);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        else if (shape == 2) //SQUARE BLOCK
        {
            color = Color.CYAN;
            locs[0] = new Location(1,4);
            locs[1] = new Location(0,4);
            locs[2] = new Location(0,5);
            locs[3] = new Location(1,5);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        else if (shape == 3) //L BLOCK
        {
            color = Color.YELLOW;
            locs[2] = new Location(0,4);
            locs[1] = new Location(1,4);
            locs[0] = new Location(2,4);
            locs[3] = new Location(2,5);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        else if (shape == 4) //REVERSE L
        {
            color = Color.MAGENTA;
            locs[2] = new Location(0,5);
            locs[1] = new Location(1,5);
            locs[0] = new Location(2,5);
            locs[3] = new Location(2,4);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        else if (shape == 5) //S BLOCK
        {
            color = Color.BLUE;
            locs[2] = new Location(1,3);
            locs[1] = new Location(1,4);
            locs[0] = new Location(0,4);
            locs[3] = new Location(0,5);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        else //REVERSE S BLOCK
        {
            color = Color.GREEN;
            locs[1] = new Location(0,3);
            locs[0] = new Location(0,4);
            locs[2] = new Location(1,4);
            locs[3] = new Location(1,5);
            for (Block b : blocks)
                b.setColor(color);
            addToLocations(grid, locs);
        }
        
        addToLocations(grid, locs);
    }

    private void addToLocations(BoundedGrid<Block> grid, Location[] locs)
    {
        for (int i = 0; i < 4; i++)
            blocks[i].putSelfInGrid(grid, locs[i]);
    }

    private Location[] removeBlocks()
    {
        Location[] oldLocations = new Location[4];
        for (int i = 0; i < 4; i++)
        {
            oldLocations[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return oldLocations;
    }

    private boolean areEmpty(BoundedGrid<Block> grid, Location[] locs)
    {
        ArrayList<Location> location = grid.getOccupiedLocations();
        boolean flip = true;
        for (int i = 0; i < locs.length; i++)
        {
            for (Location loc : location)
                if (loc.equals(locs[i]))
                    flip = false;
            if (!grid.isValid(locs[i]) || !flip)
                return false;
        }
        return flip;
    }

    public boolean translate(int deltaRow, int deltaCol)
    {
        BoundedGrid<Block> grid;
        grid = blocks[0].getGrid();
        Location[] oldLocs = removeBlocks();
        Location[] newLocs = new Location[blocks.length];
        for (int i = 0; i < newLocs.length; i++)
        {
            newLocs[i] = new Location(oldLocs[i].getRow() + deltaRow, oldLocs[i].getCol() + deltaCol);
        }
        if (areEmpty(grid, newLocs))
        {
            addToLocations(grid, newLocs);
            return true;
        }
        else
        {
            addToLocations(grid, oldLocs);
            return false;
        }
    }
    
    public boolean rotate()
    {
        /*
        BoundedGrid<Block> grid = blocks[0].getGrid();
        Location[] oldLocs = removeBlocks();
        Location[] newLocs = new Location[blocks.length];
        int row = oldLocs[0].getRow();
        int col = oldLocs[0].getCol();
        for (int i = 0; i < newLocs.length; i++)
            newLocs[i] = new Location(row - col + oldLocs[i].getCol(), row + col - oldLocs[i].getRow());
        if (areEmpty(grid, newLocs))
        {
            addToLocations(grid, newLocs);
            return true;
        }
        else
        {
            addToLocations(grid, oldLocs);
            return false;
        }
        */
        
        BoundedGrid<Block> grid = blocks[0].getGrid();
        Location[] oldLocs = removeBlocks();
        for (int i = 0; i < 4; i++)
        {
            Location[] newLocs = new Location[blocks.length];
            int row = oldLocs[i].getRow();
            int col = oldLocs[i].getCol();
            for (int k = 0; k < newLocs.length; k++)
                newLocs[k] = new Location(row-col+oldLocs[k].getCol(), row+col-oldLocs[k].getRow());
            if (areEmpty(grid, newLocs))
            {
                addToLocations(grid, newLocs);
                return true;
            }
        }
        addToLocations(grid, oldLocs);
        return false;
    }
}