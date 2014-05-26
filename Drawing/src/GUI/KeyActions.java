package GUI;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import UTILS.Config;


public class KeyActions implements KeyListener{

	/**
	 * @param args
	 */
	private static Component window;
	
	public KeyActions(Component frame) {
		window = frame;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		
		if(ke.getKeyCode() == KeyEvent.VK_ESCAPE)
	    {
	        //code to execute if escape is pressed
			if (!Config.ACTIVELAYER.newPolygonInitiated()) {
				Config.ACTIVELAYER.removeObjInCourse();
				Config.ACTIVELAYER.reset();
				window.repaint();
			}	
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
