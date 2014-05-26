package GUI;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import UTILS.Config;


public class MainPanel extends JPanel{
		
	public MainPanel(int width, int height) {
			
		setBounds(5, 45, width, height);
		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.WHITE);
		
		addMouseListener(new MouseActions(this));
		addMouseMotionListener(new MouseMotion(this));
	}
	
	public void paint(Graphics g) {
				
		super.paint(g);
		
    	g.drawImage(Config.ACTIVELAYER.getImage(),0,0,800,600,this);
    
        Config.ACTIVELAYER.drawLines(g);
        Config.ACTIVELAYER.drawInterCircle(g);
        //Utils.ACTIVELAYER.drawFromCentroid(g);
   
        if (Config.DRAW_DIFFERENCE)
        	Config.ACTIVELAYER.drawDiff(g);
        
        if (Config.DRAW_INTERSECTION)
        	Config.ACTIVELAYER.drawInter(g);
        
        Config.ACTIVELAYER.drawObjects(g);   
    }
	
	/**
	 * @param args
	 */
}
