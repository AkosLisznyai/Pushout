/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bead2;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The game's mechanics and it's elements.
 * @author Lisznyai Ákos
 */
public class Game {
    
    private final int size;
    private int duration;
    private Groove[][] field;
    
    private Player white;
    private Player black;
    
    private final JPanel gamePanel;
    private final JLabel turnLabel;
    
    private int chosenX;
    private int chosenY;
    private Boolean eSelected = false;
    
    private Boolean wStep = false;
    private Boolean bStep = false;
    
    private final ImageIcon etile = new ImageIcon("src/bead2/img/empty.png");
    private final ImageIcon estile = new ImageIcon("src/bead2/img/emptySelect.png");
    private final ImageIcon wtile = new ImageIcon("src/bead2/img/white.png");
    private final ImageIcon wstile = new ImageIcon("src/bead2/img/whiteSelect.png");
    private final ImageIcon wctile = new ImageIcon("src/bead2/img/whiteChosen.png");
    private final ImageIcon btile = new ImageIcon("src/bead2/img/black.png");
    private final ImageIcon bstile = new ImageIcon("src/bead2/img/blackSelect.png");
    private final ImageIcon bctile = new ImageIcon("src/bead2/img/blackChosen.png");
    
    /**
     * Initializes the game.
     * @param n The size of the board. Also determines the duration of the game and the number of pieces each player gets.
     */
    public Game(int n)
    {
        this.size = n;
        this.duration = n*5;
        this.white = new Player(n, true);
        this.black = new Player(n, false);
        this.gamePanel = new JPanel();
        this.gamePanel.setLayout(new GridLayout(n,n));
        this.field = new Groove[n][n];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j <n; j++)
            {
                Groove temp = new Groove(this.etile, i, j);
                temp.addActionListener(new PushListener(i, j));
                temp.setPreferredSize(new Dimension(100, 100));
                this.field[i][j] = temp;
                gamePanel.add(temp);
            }
        }
        
        for(Piece p : this.white.getPieces())
        {
            Boolean set = false;
            while(!set)
            {
                p.newPos();
                if(!this.field[p.getX()][p.getY()].isOcc())
                {
                    this.field[p.getX()][p.getY()].Occupy(p);
                    this.field[p.getX()][p.getY()].setIcon(this.wtile);
                    set = true;
                }
            }
        }
        for(Piece p : this.black.getPieces())
        {
            Boolean set = false;
            while(!set)
            {
                p.newPos();
                if(!this.field[p.getX()][p.getY()].isOcc())
                {
                    this.field[p.getX()][p.getY()].Occupy(p);
                    this.field[p.getX()][p.getY()].setIcon(this.btile);
                    set = true;
                }
            }
        }
        
        this.turnLabel = new JLabel(this.duration + " kör van hátra!");
        this.turnLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.init();
    }
    
    /**
     * Starts the game by making all white pieces selectable.
     */
    private void init()
    {
        for(Piece p : this.white.getPieces())
        {
            this.field[p.getX()][p.getY()].setSelect(true);
            this.field[p.getX()][p.getY()].setIcon(this.wstile);
        }
    }
    
    /**
     * Determines if the game is over, announces the winner(if there is one) and restarts the game with the same board size.
     */
    public void gameOver()
    {
        if(this.duration == 0 && this.white.getCount() == this.black.getCount())
        {
            JOptionPane.showMessageDialog(gamePanel, "Döntetlen!", "Vége a játéknak",JOptionPane.PLAIN_MESSAGE);
            reset();
        }
        else if((this.duration == 0 && this.white.getCount() > this.black.getCount()) || this.black.getCount() == 0)
        {
            JOptionPane.showMessageDialog(gamePanel, "Fehér játékos nyert!", "Vége a játéknak",JOptionPane.PLAIN_MESSAGE);
            reset();
        }
        else if((this.duration == 0 && this.white.getCount() < this.black.getCount()) || this.white.getCount() == 0)
        {
            JOptionPane.showMessageDialog(gamePanel, "Fekete játékos nyert!", "Vége a játéknak",JOptionPane.PLAIN_MESSAGE);
            reset();
        }
    }
    
    /**
     * Completely resets the game
     */
    private void reset()
    {
        for(Groove[] g : this.field)
        {
            for(Groove i : g)
            {
                i.Occupy(null);
                i.notOccupied();
                i.setSelect(false);
                i.setIcon(this.etile);
            }
        }
        this.duration = this.size*5;
        
        this.white = new Player(this.size, true);
        this.black = new Player(this.size, false);
        
        for(Piece p : this.white.getPieces())
        {
            Boolean set = false;
            while(!set)
            {
                p.newPos();
                if(!this.field[p.getX()][p.getY()].isOcc())
                {
                    this.field[p.getX()][p.getY()].Occupy(p);
                    this.field[p.getX()][p.getY()].setIcon(this.wtile);
                    set = true;
                }
            }
        }
        for(Piece p : this.black.getPieces())
        {
            Boolean set = false;
            while(!set)
            {
                p.newPos();
                if(!this.field[p.getX()][p.getY()].isOcc())
                {
                    this.field[p.getX()][p.getY()].Occupy(p);
                    this.field[p.getX()][p.getY()].setIcon(this.btile);
                    set = true;
                }
            }
        }
        this.eSelected = false;
        this.wStep = false;
        this.bStep = false;
        
        this.turnLabel.setText(this.duration + " kör van hátra!");
        
        init();
    }
    
    /**
     * Determines what happens when a button/groove is pressed.
     */
    private class PushListener implements ActionListener 
    {
        
        private final int cordX;
        private final int cordY;
        
        /**
         * Assigns the listener to a button
         * @param x The x position of the assigned button
         * @param y The y position of the assigned button
         */
        public PushListener(int x, int y)
        {
            this.cordX = x;
            this.cordY = y;
        }
        
        /**
         * Selects the button/groove, makes other pieces unselectable
         */
        private void select()
        {
            if(field[cordX][cordY].getOcc().isWhite())
            {
                field[cordX][cordY].setChosen(true);
                eSelected = true;
                chosenX = cordX;
                chosenY = cordY;
                field[cordX][cordY].setIcon(wctile);
                for(Piece p : white.getPieces())
                {
                    if(field[cordX][cordY].getOcc().getId() != p.getId())
                    {
                        field[p.getX()][p.getY()].setSelect(false);
                        field[p.getX()][p.getY()].setIcon(wtile);
                    }
                }
            }
            else
            {
                field[cordX][cordY].setChosen(true);
                eSelected = true;
                chosenX = cordX;
                chosenY = cordY;
                field[cordX][cordY].setIcon(bctile);
                for(Piece p : black.getPieces())
                {
                    if(field[cordX][cordY].getOcc().getId() != p.getId())
                    {
                        field[p.getX()][p.getY()].setSelect(false);
                        field[p.getX()][p.getY()].setIcon(btile);
                    }
                }
            }
            
            if(cordX + 1 >= 0 && cordX+1 < size)
            {
                field[cordX+1][cordY].setSelect(true);
                if(field[cordX+1][cordY].isOcc() && field[cordX+1][cordY].getOcc().isWhite())
                {
                    field[cordX+1][cordY].setIcon(wstile);
                }
                else if(field[cordX+1][cordY].isOcc() && !field[cordX+1][cordY].getOcc().isWhite())
                {
                    field[cordX+1][cordY].setIcon(bstile);
                }
                else
                {
                    field[cordX+1][cordY].setIcon(estile);
                }
            }
            
            if(cordX - 1 >= 0 && cordX-1 < size)
            {
                field[cordX-1][cordY].setSelect(true);
                if(field[cordX-1][cordY].isOcc() && field[cordX-1][cordY].getOcc().isWhite())
                {
                    field[cordX-1][cordY].setIcon(wstile);
                }
                else if(field[cordX-1][cordY].isOcc() && !field[cordX-1][cordY].getOcc().isWhite())
                {
                    field[cordX-1][cordY].setIcon(bstile);
                }
                else
                {
                    field[cordX-1][cordY].setIcon(estile);
                }
            }
            
            if(cordY + 1 >= 0 && cordY+1 < size)
            {
                field[cordX][cordY+1].setSelect(true);
                if(field[cordX][cordY+1].isOcc() && field[cordX][cordY+1].getOcc().isWhite())
                {
                    field[cordX][cordY+1].setIcon(wstile);
                }
                else if(field[cordX][cordY+1].isOcc() && !field[cordX][cordY+1].getOcc().isWhite())
                {
                    field[cordX][cordY+1].setIcon(bstile);
                }
                else
                {
                    field[cordX][cordY+1].setIcon(estile);
                }
            }
            
            if(cordY - 1 >= 0 && cordY - 1 < size)
            {
                field[cordX][cordY-1].setSelect(true);
                if(field[cordX][cordY-1].isOcc() && field[cordX][cordY-1].getOcc().isWhite())
                {
                    field[cordX][cordY-1].setIcon(wstile);
                }
                else if(field[cordX][cordY-1].isOcc() && !field[cordX][cordY-1].getOcc().isWhite())
                {
                    field[cordX][cordY-1].setIcon(bstile);
                }
                else
                {
                    field[cordX][cordY-1].setIcon(estile);
                }
            }
        }
        
        /**
         * De-selects a selected piece, makes other pieces selectable
         */
        private void deSelect()
        {
            if(cordX + 1 >= 0 && cordX+1 < size)
            {
                field[cordX+1][cordY].setSelect(false);
                if(field[cordX+1][cordY].isOcc() && field[cordX+1][cordY].getOcc().isWhite())
                {
                    field[cordX+1][cordY].setIcon(wtile);
                }
                else if(field[cordX+1][cordY].isOcc() && !field[cordX+1][cordY].getOcc().isWhite())
                {
                    field[cordX+1][cordY].setIcon(btile);
                }
                else
                {
                    field[cordX+1][cordY].setIcon(etile);
                }
            }
            
            if(cordX - 1 >= 0 && cordX-1 < size)
            {
                field[cordX-1][cordY].setSelect(false);
                if(field[cordX-1][cordY].isOcc() && field[cordX-1][cordY].getOcc().isWhite())
                {
                    field[cordX-1][cordY].setIcon(wtile);
                }
                else if(field[cordX-1][cordY].isOcc() && !field[cordX-1][cordY].getOcc().isWhite())
                {
                    field[cordX-1][cordY].setIcon(btile);
                }
                else
                {
                    field[cordX-1][cordY].setIcon(etile);
                }
            }
            
            if(cordY + 1 >= 0 && cordY+1 < size)
            {
                field[cordX][cordY+1].setSelect(false);
                if(field[cordX][cordY+1].isOcc() && field[cordX][cordY+1].getOcc().isWhite())
                {
                    field[cordX][cordY+1].setIcon(wtile);
                }
                else if(field[cordX][cordY+1].isOcc() && !field[cordX][cordY+1].getOcc().isWhite())
                {
                    field[cordX][cordY+1].setIcon(btile);
                }
                else
                {
                    field[cordX][cordY+1].setIcon(etile);
                }
            }
            
            if(cordY - 1 >= 0 && cordY - 1 < size)
            {
                field[cordX][cordY-1].setSelect(false);
                if(field[cordX][cordY-1].isOcc() && field[cordX][cordY-1].getOcc().isWhite())
                {
                    field[cordX][cordY-1].setIcon(wtile);
                }
                else if(field[cordX][cordY-1].isOcc() && !field[cordX][cordY-1].getOcc().isWhite())
                {
                    field[cordX][cordY-1].setIcon(btile);
                }
                else
                {
                    field[cordX][cordY-1].setIcon(etile);
                }
            }
            
            if(field[cordX][cordY].getOcc().isWhite())
            {
                field[cordX][cordY].setChosen(false);
                eSelected = false;
                field[cordX][cordY].setIcon(wstile);
                for(Piece p : white.getPieces())
                {
                    if(field[cordX][cordY].getOcc().getId() != p.getId())
                    {
                        field[p.getX()][p.getY()].setSelect(true);
                        field[p.getX()][p.getY()].setIcon(wstile);
                    }
                }
            }
            else
            {
                field[cordX][cordY].setChosen(false);
                eSelected = false;
                field[cordX][cordY].setIcon(bstile);
                for(Piece p : black.getPieces())
                {
                    if(field[cordX][cordY].getOcc().getId() != p.getId())
                    {
                        field[p.getX()][p.getY()].setSelect(true);
                        field[p.getX()][p.getY()].setIcon(bstile);
                    }
                }
            }
        }
        
        /**
         * De-selects everything.
         */
        private void neutral()
        {
            eSelected = false;
            for(Groove[] g : field)
            {
                for(Groove s : g)
                {
                    s.setChosen(false);
                    s.setSelect(false);
                    if(s.isOcc())
                    {
                        if(s.getOcc().isWhite())
                        {
                            s.setIcon(wtile);
                        }
                        else
                        {
                            s.setIcon(btile);
                        }
                    }
                    else
                    {
                        s.setIcon(etile);
                    }
                }
            }
        }
        
        /**
         * Determines the direction a piece might be moving towards.
         * @return The direction the piece is moving towards.
         * @throws NullPointerException 
         */
        private String direction() throws NullPointerException
        {
            if(cordX != chosenX)
            {
                if(cordX < chosenX)
                {
                    return "-x";
                }
                else if(cordX > chosenX)
                {
                    return "+x";
                }
            }
            else
            {
                if(cordY < chosenY)
                {
                    return "-y";
                }
                else if(cordY > chosenY)
                {
                    return "+y";
                }
            }
            return null;
        }
        
        /**
         * Determines if a piece has been pushed off of the board.
         * @param a The piece that is examined
         * @return If true, the piece has been pushed off.
         * @throws NullPointerException 
         */
        private Boolean isOut(Piece a) throws NullPointerException
        {
            if(a.getX() < 0)
            {
                return true;
            }
            else if(a.getX() >= size)
            {
                return true;
            }
            else if(a.getY() < 0)
            {
                return true;
            }
            else if(a.getY() >= size)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        /**
         * If a piece has been pushed off, it gets removed.
         * @param a The piece that has been pushed off.
         */
        private void pushOff(Piece a)
        {
            if(isOut(a))
            {
                a.getOwner().losePiece(a.getId());
            }
        }
        
        /**
         * Moves a piece into an empty groove
         * @param p The piece that is moved
         * @param g The groove that the piece is moving into
         */
        private void pushEmpty(Piece p, Groove g)
        {
            if(p.getX() == g.getPosX() && p.getY() == g.getPosY() && !g.isOcc())
            {
                g.Occupy(p);
                if(p.isWhite())
                {
                    g.setIcon(wtile);
                }
                else
                {
                    g.setIcon(btile);
                }
            }
        }
        
        /**
         * Moves a piece into a groove that's occupied by an other piece, pushing out of the way
         * @param p The piece that moves into the groove
         * @param g The groove the piece moves into
         * @param d The direction the piece gets pushed towards
         * @return The piece that has been pushed out of the way.
         * @throws NullPointerException 
         */
        private Piece pushOther(Piece p, Groove g, String d) throws NullPointerException
        {
            Piece out;
            if(p.getX() == g.getPosX() && p.getY() == g.getPosY() && g.isOcc())
            {
                g.getOcc().push(d);
                out = g.getOcc();
                g.Occupy(p);
                if(p.isWhite())
                {
                    g.setIcon(wtile);
                }
                else
                {
                    g.setIcon(btile);
                }
                return out;
            }
            return null;
        }
        
        /**
         * Moves the pieces on the board
         */
        private void move()
        {
            Groove loc = field[cordX][cordY];
            String dir = this.direction();
            Boolean end = false;
            Piece p1;
            Piece p2;
            if(!loc.isOcc())
            {
                field[chosenX][chosenY].getOcc().push(dir);
                field[chosenX][chosenY].notOccupied();
                pushEmpty(field[chosenX][chosenY].getOcc(), loc);
            }
            else
            {
                field[chosenX][chosenY].getOcc().push(dir);
                field[chosenX][chosenY].notOccupied();
                p1 = pushOther(field[chosenX][chosenY].getOcc(), loc, dir);
                
                if(isOut(p1))
                {
                    pushOff(p1);
                }
                else if(!field[p1.getX()][p1.getY()].isOcc())
                {
                    pushEmpty(p1,field[p1.getX()][p1.getY()]);
                }
                else if(field[p1.getX()][p1.getY()].isOcc())
                {
                    while(!end)
                    {
                        p2 = pushOther(p1, field[p1.getX()][p1.getY()], dir);
                        if(isOut(p2))
                        {
                            pushOff(p2);
                            end = true;
                        }
                        else if(!field[p2.getX()][p2.getY()].isOcc())
                        {
                             pushEmpty(p2,field[p2.getX()][p2.getY()]);
                             end = true;
                        }
                        p1 = p2;
                    }
                }
                
            }
        }
        
        /**
         * Determines what happens when a button is pressed; Selects, De-selects and moves.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(field[cordX][cordY].isChosen())
            {
                deSelect();
            }
            else if(field[cordX][cordY].isSelectable())
            {
                if(!eSelected)
                {
                    select();
                }
                else
                {
                    if(!wStep && !bStep)
                    {
                        move();
                        neutral();
                        wStep=true;
                        gameOver();
                        if(wStep)
                        {
                            for(Piece p : black.getPieces())
                            {
                                field[p.getX()][p.getY()].setSelect(true);
                                field[p.getX()][p.getY()].setIcon(bstile);
                            }
                        }
                    }
                    else if(wStep && !bStep)
                    {
                        move();
                        neutral();
                        gameOver();
                        bStep = true;
                        duration--;
                        gameOver();
                        turnLabel.setText(duration + " kör van hátra!");
                        for(Piece p : white.getPieces())
                        {
                            field[p.getX()][p.getY()].setSelect(true);
                            field[p.getX()][p.getY()].setIcon(wstile);
                        }
                        wStep = false;
                        bStep = false;
                    }
                }
            }
        }
    }
    
    public JPanel getGame()
    {
        return this.gamePanel;
    }
    
    public JLabel getTurns()
    {
        return this.turnLabel;
    }
    
}
