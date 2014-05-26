package GUI;
import java.awt.Component;
import java.awt.PopupMenu;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import UTILS.*;


public class MouseActions implements MouseListener{

	/**
	 * @param args
	 */

	private static PopupMenu menu;
	private static RClickMenu popMenu;
	private static Component window;
	
	private static ObjGeom obj;
	private static boolean MOUSERELEASED = false;
	
	public MouseActions(Component frame) {
		popMenu = new RClickMenu(frame);
		menu = popMenu.getPopMenu();
		window = frame;
		frame.add(menu);
//		popMenu = pop;
//		menu = pop.getPopMenu();
		
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		
		if (!Config.OS.contains("win"))
			if (Config.ACTIVELAYER.drawingON()) {
				
				if (e.isPopupTrigger()) { //si se desea mostrar el menu click derecho...
					showRightClickMenu(e);
			    }
				else {
					
					Config.ACTIVELAYER.setActualPoint(e.getPoint());
					
					if (Config.ACTIVELAYER.newPolygonInitiated()) {	
						obj = new ObjGeom();
						Config.ACTIVELAYER.addObj(obj);
					}
					else 
						obj = Config.ACTIVELAYER.getLastObjGeom();
					
					if (Config.ACTIVELAYER.adjacentPolygonInitiated())					
						Config.ACTIVELAYER.beginAdjacentPolygon(obj);
					else 
						obj.addPoint(e.getPoint());
					
					window.repaint();
				}
				
			}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (Config.OS.contains("win"))
			if (Config.ACTIVELAYER.drawingON()) {
				
				if (e.isPopupTrigger()) { //si se desea mostrar el menu click derecho...
					showRightClickMenu(e);
			    }
				else {
					
					Config.ACTIVELAYER.setActualPoint(e.getPoint());
					
					if (Config.ACTIVELAYER.newPolygonInitiated()) {	
						obj = new ObjGeom();
						Config.ACTIVELAYER.addObj(obj);
					}
					else 
						obj = Config.ACTIVELAYER.getLastObjGeom();
					
					if (Config.ACTIVELAYER.adjacentPolygonInitiated())					
						Config.ACTIVELAYER.beginAdjacentPolygon(obj);
					else 
						obj.addPoint(e.getPoint());
					
					window.repaint();
				}
				
			}		
	}
	
	public static void closePolygon(ObjGeom obj) {
		
		
		if  (obj.minimumPoints() & obj != null) {
			
			obj.closePolygon();
			obj.makePolygon(); //Armo el poligono con los puntos que tengo hasta el momento
				
			//setClass(obj); // muestro la ventana para setear las caracteristicas de la region
			popMenu.disableClose();
			
			//reseteo los clicks para que vuelva a empezar todo
			
			Config.ACTIVELAYER.reset();
			Config.ACTIVELAYER.addToDefaultList(obj);
			
			System.out.println("Cerrando poligono");
			obj.printPoints();
			
			window.repaint();
		}
			
	}
	
	public static void setClass(ObjGeom obj) {
		
		SetClass set = new SetClass(obj);
		set = null;
		
	}
	
	public void showRightClickMenu(MouseEvent e) {
		if (!Config.ACTIVELAYER.emptyObjList()) {
			
			//obtengo el polygon al que apunta el mouse. Null si no existe el polygon.
				
			if (Config.ACTIVELAYER.getSelectedObj(e.getPoint()) != null) { //compruebo que haya algun polygon apuntado para activar las opciones para polygons.
				popMenu.disableClose();
				popMenu.enableAll();
			}	
			else {
				popMenu.disableAll();
				
				if (Config.ACTIVELAYER.newPolygonInitiated())
					popMenu.disableClose();
				else
					popMenu.enableClose();
			}
				
		}	
		else {
			popMenu.disableClose();
			popMenu.disableAll();
		}	
		
		menu.show(e.getComponent(), e.getX(), e.getY()); //... mostramos el menu en la ubicacion del mouse
		
	}
	
	public void this_mousePressed(MouseEvent e) {
	       //mostrarPopupMenu(e);
	}

}
