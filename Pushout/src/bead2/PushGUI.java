package bead2;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * The window the game is displayed in.
 * @author Lisznyai Ákos
 */
public class PushGUI {
       
    private JFrame window;
    private Game pushOut;
    
    public PushGUI()
    {
        this.window = new JFrame("Kitolás");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.pushOut = new Game(3);
        this.window.getContentPane().add(this.pushOut.getGame(), BorderLayout.CENTER);
        this.window.getContentPane().add(this.pushOut.getTurns(), BorderLayout.SOUTH);
        
        JMenuBar menuMain = new JMenuBar();
        this.window.setJMenuBar(menuMain);
        JMenu menuNew = new JMenu("Új játék");
        menuMain.add(menuNew);
        JMenu sizeMenu = new JMenu("Méret");
        menuNew.add(sizeMenu);
        int[] sizes = new int[]{3,4,6};
        for(int s : sizes)
        {
            JMenuItem sizeMenuItem = new JMenuItem(s + "x" + s);
            sizeMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    window.getContentPane().remove(pushOut.getGame());
                    window.getContentPane().remove(pushOut.getTurns());
                    pushOut = new Game(s);
                    window.getContentPane().add(pushOut.getGame(),BorderLayout.CENTER);
                    window.getContentPane().add(pushOut.getTurns(), BorderLayout.SOUTH);
                    window.pack();
                }
            });
        }
        JMenuItem exit = new JMenuItem("Kilépés");
        menuMain.add(exit);
        exit.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent a) 
            {
                System.exit(0);
            }
        });
        
        
        window.pack();
        window.setVisible(true);
    }
    
    
    
    
}
