package GUI;
import java.awt.Color;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MenuBarJ {
	
	private JPanel panelMenu;
	
	public MenuBarJ() {
		
		//panelMenu = new JPanel();
        //panelMenu.setBackground(Color.white);
        //panelMenu.setSize(800, 80);	
	}

    public JMenuBar createMenuBar()
    {
        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();
        //Add a JMenu
        
        menuBar.setBackground(Color.LIGHT_GRAY);
        // Now we want to fill each of the menus.
        // Starters. This is a simple Menu, with three MenuItems.
        
        JMenu file = new JMenu("File");
        file.setBackground(Color.LIGHT_GRAY);
        file.setBorder(null);
        
        file.add(new JMenuItem("Open image"));
        file.add(new JMenuItem("Save segmentation"));
        file.add(new JMenuItem("Load segmentation file"));
        
        // Main Courses
        // This menu is a little more complex.
        // We have a sub-menu within the menu,
        // and have put in a radio-button group for the sideorders.
        // There is also a Separator down there in line 74
		
        JMenu edit = new JMenu("Edit");
        edit.setBackground(Color.LIGHT_GRAY);
        edit.setBorder(null);
        
        edit.add(new JMenuItem("Copy"));
        edit.add(new JMenuItem("Paste"));
        edit.addSeparator();
        edit.add(new JCheckBoxMenuItem("Opcion 1"));
        edit.add(new JCheckBoxMenuItem("Opcion 2"));
        edit.add(new JCheckBoxMenuItem("Opcion 3"));				  
        // Desserts
        // This is to display the CheckBoxMenuItem
        // and another SubMenu with more CheckBoxes in it.
		
        menuBar.add(file);
        menuBar.add(edit);
        
        return menuBar;
    }

}
