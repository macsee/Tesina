package GUI;
import java.awt.Dialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import UTILS.Config;


public class ResultWindow extends Dialog{

	/**
	 * @param args
	 */
	
	public ResultWindow() {
		super(new JFrame(), "Output");
        setResizable(false);
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        JScrollPane textScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(textScroll);
        pack();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
        
        String out = "";
        
        for (String val : Config.OUT) {
        	out += val+"\n";  
        }
        	
        textArea.setText(out);
        setVisible(true);
    }
   
}
