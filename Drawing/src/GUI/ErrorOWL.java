package GUI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Label;

public class ErrorOWL extends Dialog {

	public ErrorOWL(MainPanel panelPrincipal,String error){
		super(new Frame(), "Error");
		setVisible(true);
		int locationX = panelPrincipal.getWidth()/2-150 + panelPrincipal.getX();
		int locationY = panelPrincipal.getHeight()/2-60 + panelPrincipal.getY();
		
		setLocation(locationX, locationY);
		setSize(300, 120);
		setResizable(false);
		Panel panel = new Panel();
		panel.setLayout(null);
		add(panel);
		
		Label labelError = new Label(error,Label.CENTER);
		labelError.setSize(300, 20);
		labelError.setLocation(0, 20);
		panel.add(labelError);
		
		Button buttonError = new Button("Close");
		buttonError.setSize(100, 40);
		buttonError.setLocation(100, 50);
		panel.add(buttonError);
		
		buttonError.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
        addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              dispose();
           }
        });
     }
	
	public static void main(String[] args) {
		new Error("Inconsistent Ontology.");
	}

}
