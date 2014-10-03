package GUI;
import java.awt.Component;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import OWL.CM8toOWL;
import UTILS.*;


public class RClickMenu {

	/**
	 * @param args
	 */
	PopupMenu popup;
	MenuItem menuAddPolygon;
	MenuItem menuClosePolygon;
	MenuItem menuCheckRelPolygon;
	MenuItem menuRemovePolygon;
	
	public RClickMenu(final Component frame) {
		 popup = new PopupMenu(); //creamos el menu saliente
	       
	     menuClosePolygon = new MenuItem("Close Polygon");
	     menuClosePolygon.setEnabled(false);
	     
	     popup.add(menuClosePolygon); //agregamos elemento
	     menuClosePolygon.addActionListener(new ActionListener() {
	    	   
	    	 @Override
	    	 public void actionPerformed(ActionEvent arg0) {
	    		 MouseActions.closePolygon(Config.ACTIVELAYER.getLastObjGeom());
	    	 }
	     });		
	     	 
	     menuAddPolygon = new MenuItem("Settings");
	     menuAddPolygon.setEnabled(false);
	     
	     popup.add(menuAddPolygon); //.. y otro elemento
	     
	     menuAddPolygon.addActionListener(new ActionListener() {
	    	   
	    	 @Override
	    	 public void actionPerformed(ActionEvent arg0) {
	    		 
	    		ObjGeom obj = Config.ACTIVELAYER.getSelectedObj(Config.MOUSE_POINT);
	    		
	    		 if ( obj != null) {
	    			 System.out.println("Setting class for Polygon: "+obj.getId());
	    			 MouseActions.setClass(obj,Config.ACTIVELAYER);
	    		 }	
	    	 }
	     });
	     
	     menuCheckRelPolygon = new MenuItem("Check CM8 Primitives for Polygon");
	     menuCheckRelPolygon.setEnabled(false);
	     
	     popup.add(menuCheckRelPolygon); //.. y otro elemento
	     
	     menuCheckRelPolygon.addActionListener(new ActionListener() {
	    	   
	    	 @Override
	    	 public void actionPerformed(ActionEvent arg0) {
	    		
	    		 ObjGeom obj = Config.ACTIVELAYER.getSelectedObj(Config.MOUSE_POINT);
	    		 
	    		 if (obj != null) {
	    			 System.out.println("Checking spatial relationships for Polygon: "+obj.getId());
	    			 Config.OUT.clear();
	    			 //Config.ACTIVELAYER.checkCM8FromRClick(obj);
	    			 Config.ACTIVELAYER.checkCM8PrimitivesForPolygon(obj);
	    			 new ResultWindow();
	    		 }	
	    	 }
	     });
	     
	     menuRemovePolygon = new MenuItem("Remove");
	     menuRemovePolygon.setEnabled(false);
	     
	     popup.add(menuRemovePolygon); //.. y otro elemento
	     
	     menuRemovePolygon.addActionListener(new ActionListener() {
	    	   
	    	 @Override
	    	 public void actionPerformed(ActionEvent arg0) {
	    		
	    		 ObjGeom obj = Config.ACTIVELAYER.getSelectedObj(Config.MOUSE_POINT);
	    		 
	    		 if (obj != null) {
	    			 System.out.println("Removing Polygon: "+obj.getId());
	    			 Config.ACTIVELAYER.removeSelectedObject(obj);
	    			 frame.repaint();
	    		 }	
	    	 }
	     });
	  }
	 
	
	public void disableAdd() {
		menuAddPolygon.setEnabled(false);
	}
	
	public void enableAdd() {
		menuAddPolygon.setEnabled(true);
	}
	
	public void enableClose() {
		menuClosePolygon.setEnabled(true);
	}
	
	public void disableClose() {
		menuClosePolygon.setEnabled(false);
	}
	
	public void enableCheck() {
		menuCheckRelPolygon.setEnabled(true);
	}
	
	public void disableCheck() {
		menuCheckRelPolygon.setEnabled(false);
	}

	public void enableAll() {
		menuCheckRelPolygon.setEnabled(true);
		menuAddPolygon.setEnabled(true);
		menuRemovePolygon.setEnabled(true);
	}
	
	public void disableAll() {
		menuCheckRelPolygon.setEnabled(false);
		menuAddPolygon.setEnabled(false);
		menuRemovePolygon.setEnabled(false);
	}
	
	public PopupMenu getPopMenu() {
		 return popup; 
	}
}
