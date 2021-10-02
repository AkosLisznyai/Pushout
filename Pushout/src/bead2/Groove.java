/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bead2;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * The spaces on the board. They are buttons.
 * @author Lisznyai Ákos
 */
public class Groove extends JButton{
    
    private final int posX;
    private final int posY;
    private Boolean occupied = false;
    private Piece occupant;
    private Boolean selectable = false;
    private Boolean chosen = false;
    
    /**
     * Initializes a groove/button.
     * @param tile The picture shown on the button.
     * @param x The x position of the button on the grid
     * @param y The y position of the button on the grid
     */
    public Groove(ImageIcon tile, int x, int y)
    {
        super(tile);
        this.posX = x;
        this.posY = y;
    }
    
    public int getPosX()
    {
        return this.posX;
    }
    
    public int getPosY()
    {
        return this.posY;
    }
    
    /**
     * Puts a piece onto the groove.
     * @param p The piece that's put on the groove.
     */
    public void Occupy(Piece p)
    {
        this.occupant = p;
        this.occupied = true;
    }
    
    public Boolean isOcc()
    {
        return this.occupied;
    }
    
    
    public Piece getOcc()
    {
        return this.occupant;
    }
    
    /**
     * Sets the occupied condition of the groove to false.
     */
    public void notOccupied()
    {
        this.occupied = false;
    }
    
    public Boolean isSelectable()
    {
        return this.selectable;
    }
    
    
    /**
     * Sets the selectability of the button.
     * @param s If true, the button will become selectable.
     */
    public void setSelect(Boolean s)
    {
        this.selectable = s;
    }
    
    public Boolean isChosen()
    {
        return this.chosen;
    }
    
    /**
     * Changes if the button/groove is selected/chosen or not.
     * @param c If true, the button has been selected/chosen.
     */
    public void setChosen(Boolean c)
    {
        this.chosen = c;
    }
     
    
}
