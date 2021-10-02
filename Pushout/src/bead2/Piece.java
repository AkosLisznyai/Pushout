/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bead2;

/**
 * The pieces
 * @author Lisznyai Ákos
 */
public class Piece {
    
    private int id;
    private final Boolean white;
    private int posX;
    private int posY;
    private final Player owner;
    
    
    /**
     * Initializes a piece
     * @param i The id of the piece
     * @param f If true, the piece is white
     * @param o The owner of the piece
     */
    public Piece(int i ,Boolean f, Player o)
    {
        this.id = i;
        this.white = f;
        this.owner = o;
    }
    
    /**
     * Generates a random position for the piece.
     */
    public void newPos()
    {
        this.posX = (int)(Math.random()*(owner.getCount()));
        this.posY = (int)(Math.random()*(owner.getCount()));
    }
    
    /**
     * Pushes the piece in a certain direction.
     * @param d The direction the piece is pushed towards.
     */
    public void push(String d)
    {
        if("-x".equals(d))
        {
            this.posX--;
        }
        else if("+x".equals(d))
        {
            this.posX++;
        }
        else if("-y".equals(d))
        {
            this.posY--;
        }
        else if("+y".equals(d))
        {
            this.posY++;
        }
    }
    
    public int getX()
    {
        return this.posX;
    }
    
    public int getY()
    {
        return this.posY;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int i)
    {
        this.id = i;
    }
    
    public Player getOwner()
    {
        return this.owner;
    }
    
    public Boolean isWhite()
    {
        return this.white;
    }
}
