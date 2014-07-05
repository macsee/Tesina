package GUI;
import java.awt.CheckboxMenuItem;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import UTILS.*;



public class MenuBarAWT {

	private Simulator ventana;
	private Layer thr1;
	private Layer hr1;
	private Layer hr2;
	
	public MenuBarAWT (JFrame frame) {
		
		ventana = (Simulator) frame;

	}

    public MenuBar createMenuBar()
    {
        //Create the menu bar.
        MenuBar menuBar = new MenuBar();
        //Add a JMenu
        
        Menu file = new Menu("File");
        
        MenuItem open = new MenuItem("Open Image");
        open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				new LoadImages(ventana);
				
			}
		});
        
        file.add(open);
         
        MenuItem load = new MenuItem("Load Raster");
        load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new LoadRasterShape(ventana);
				
			}
		});
        file.add(load);
        
        MenuItem save = new MenuItem("Save clasification");
        save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (Config.ACTIVELAYER.emptyObjList()) {
					JOptionPane.showMessageDialog(null, "No objects were found in the active Layer!");
					return;
				}
				
				new Save(ventana);
				
			}
		});
        file.add(save);
        
        MenuItem quit = new MenuItem("Quit"); 
        quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Exit....");
				System.exit(0);
			}
		});
        file.add(quit);

		
        Menu edit = new Menu("Edit");
              
        MenuItem undo = new MenuItem("Undo");
        undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("Undo...");
				Config.ACTIVELAYER.continuePolygon(); // Para que al borrar segmentos se puedan dibujar nuevos segmentos a partir del ultimo punto del poligono actual
				
				ObjGeom obj = Config.ACTIVELAYER.getLastObjGeom(); // Obtengo el objeto actual.
				
				if (obj != null) { // Si no hay ninguno todav’a no hago nada
					
					obj.removeLastSegment(); // Borro el ultimo segmento dibujado. Si no hay ninguno, no hago nada.
					
					if (obj.emptyPoints()) { // Chequeo si todav’an quedan puntos en el poligono. Si no es asi remuevo el objeto entero
						Config.ACTIVELAYER.removeLastObjGeom();
						//Utils.NEW_POLYGON = true; // Habilito para que se puedan dibujar nuevos poligonos - Puesto en removeLasObjGeom
						System.out.println("Ya no quedan objetos");
					}
					else {
						//Utils.NEW_POLYGON = false; // Si todav’a quedan puntos entonces los puntos que se agreguen
											//	tienen que pertenecer al poligono actual
						System.out.println("Todavia quedan objetos");
					}
				}
				
				ventana.repaint();				
			}
		});
      
        edit.add(undo);
        
        
        Menu debug = new Menu("Debug");
        
        CheckboxMenuItem chk1 = new CheckboxMenuItem("Show intersections"); 
        debug.add(chk1);
        chk1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getStateChange() == arg0.SELECTED)
					Config.DRAW_INTERSECTION = true;
				else
					Config.DRAW_INTERSECTION = false;
				
				ventana.repaint();
			}
		});
        
        CheckboxMenuItem chk2 = new CheckboxMenuItem("Show differences"); 
        debug.add(chk2);
        chk2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getStateChange() == arg0.SELECTED)
					Config.DRAW_DIFFERENCE = true;
				else
					Config.DRAW_DIFFERENCE = false;
				
				ventana.repaint();
				
			}
		});
        
        MenuItem console = new MenuItem("Show result window"); 
        debug.add(console);
        console.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ResultWindow();
			}
		});      				  
		
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(debug);
        
        return menuBar;
    }

}
