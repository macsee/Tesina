package GUI;

import java.awt.Button;
import java.awt.Container;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import UTILS.Config;
import UTILS.Layer;
import UTILS.MyMultiTask;
import UTILS.MyTask;
import UTILS.MyThread;


public class MyProgressBar extends JDialog  implements ActionListener, PropertyChangeListener {
	
	static MainPanel frmMain;
	static Container pane;
	static JButton btnDo;
	static JProgressBar barDo;
	private Label label;
	private int total = 0;
	private MyMultiTask mytask = null;
	
	public MyProgressBar(MainPanel frame) {
		// TODO Auto-generated constructor stub
		
		setAlwaysOnTop(true);
		frmMain = frame;
		pane = getContentPane();
		setSize(350, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pane.setLayout(null); //Use the null layout
		
		label = new Label("Running...");
		label.setBounds(140,10,50,10);
		pane.add(label);
		
		barDo = new JProgressBar(0, 100); //Min value: 0 Max value: 100
		barDo.setBounds(15,25,320,50);
		barDo.setValue(0);
		barDo.setStringPainted(true);
		pane.add(barDo);

		Button btnCancel = new Button("Cancel");
		btnCancel.setBounds(125, 70, 100, 40);
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mytask.cancel(true); // Cancelo la operacion
				frmMain.repaint();
				frmMain.MouseON(); // Activo el mouse
				dispose();
			}
		});
		pane.add(btnCancel);

		setResizable(false);
		setVisible(true);
		
		//Aca hago todo!!!!!
    	frmMain.mouseOFF(); // Desactivo el mouse
		mytask = new MyMultiTask(frmMain, this);
		mytask.execute();
	}
	
	public void updateProgress(int value) {
		// TODO Auto-generated method stub
		total+= value;
		System.out.println(total);
		barDo.setValue(total);
		repaint();
	}
	
	public void finished() {
		label.setText("Finished");
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		 if ("progress" == evt.getPropertyName()) {
	            int progress = (Integer) evt.getNewValue();
	            barDo.setValue(progress);
	        } 
	}
			
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}