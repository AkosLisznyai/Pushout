/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bead2;

import java.util.ArrayList;

/**
 * The players
 * @author Lisznyai Ákos
 */
public class Player {
    private int pieceCount;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private final Boolean first;
    
    /**
     * Initializes a player
     * @param n The amount of pieces a player gets
     * @param b If true, the player has the white pieces and goes first.
     */
    public Player(int n, Boolean b)
    {
        this.pieceCount = n;
        this.first = b;
        for(int i = 0; i<n; i++)
        {
            Piece temp = new Piece(i,b, this);
            this.pieces.add(temp);
        }
    }
    
    public Boolean getFirst()
    {
        return this.first;
    }
    
    public int getCount()
    {
        return this.pieceCount;
    }
    
    /**
     * Discards a piece that was pushed off of the board.
     * @param i The id of the piece
     */
    public void losePiece(int i)
    {
        this.pieceCount--;
        this.pieces.remove(i);
        
        if(!this.pieces.isEmpty())
        {
            for(int j = 0; j < this.pieceCount; j++)
            {
                this.pieces.get(j).setId(j);
            }
        }
    }
    
    public ArrayList<Piece> getPieces()
    {
        return this.pieces;
    }
    
}
