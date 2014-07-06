package UTILS;

import java.awt.Cursor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import GUI.MyProgressBar;
import OWL.CM8toOWL;

public class MyTask extends SwingWorker<Void, Void> {
    /*
     * Main task. Executed in background thread.
     */
	
	private Method method;
	private Layer layer;
	private MyProgressBar bar;
	
	public MyTask(MyProgressBar procedure) {
		
		bar = procedure;
		addPropertyChangeListener(procedure);
	}

	@Override
    public Void doInBackground() throws InterruptedException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	System.out.println("Entro");
    	
    	Integer total = 0;
    	
    	bar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	
    	long initTime = System.currentTimeMillis();
    	
    	CM8toOWL CM8 = new CM8toOWL();
    	
    	for (Layer layer : Config.LAYERS) {
    		if (!layer.emptyObjList())
    			layer.setCM8(CM8);
    	}
    	
    	
    	/*********************************************************************************************/
    	
    	
    	long startTime = System.currentTimeMillis();
    	
		for (Layer layer : Config.LAYERS) {
			layer.assertDataForObjsInLayer();
			total += (int) Math.ceil(30.0/Config.LAYERS.size());
			setProgress(total);
			System.out.println(total);
		}	
			
		System.out.println(">>>>>>>>>>>>>>>>>>>>>Asserting Objs");
		System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds");

		
		/*********************************************************************************************/
		
		
		startTime = System.currentTimeMillis();
    	
		for (Layer layer : Config.LAYERS) {
			layer.asertSameIndividualsInLayer();
			total += (int) Math.ceil(10.0/Config.LAYERS.size());
			setProgress(total);
			System.out.println(total);
		}	
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>Asserting Same Objs");
		System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds");
			
    
    	/*********************************************************************************************/
    	
    	
		startTime = System.currentTimeMillis();
		
		for (Layer layer : Config.LAYERS) {
			layer.makeObjsDifferentInLayer();
			total += (int) Math.ceil(10.0/Config.LAYERS.size()); 
			setProgress(total);
			System.out.println(total);
		}
		
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>Different Objs");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds");
    	
    	
    	/*********************************************************************************************/
		
		CM8.saveOnto();
    	
    	if (!CM8.checkConsistency()) {
			JOptionPane.showMessageDialog(null,"Inconsistent Ontology","Error",JOptionPane.ERROR_MESSAGE);
			bar.updateProgress(100);
			return null;
		}
    	
    	
    	/*********************************************************************************************/
    	
    	
    	startTime = System.currentTimeMillis();
    	
    	for (Layer layer : Config.LAYERS) {
			layer.getAssertedDataInLayer();
			total += (int) Math.ceil(20.0/Config.LAYERS.size()); 
			setProgress(total);
			System.out.println(total);
		}	
    	
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>Getting Asserted");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds");
		
    	
    	/*********************************************************************************************/
    	
    	
    	startTime = System.currentTimeMillis();
    	
		for (Layer layer : Config.LAYERS) {
			layer.getInferredDataInLayer();
			total += (int) Math.ceil(30/Config.LAYERS.size()); 
			setProgress(total);
			System.out.println(total);
		}	
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>Getting Inferred");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds");
		
    	
    	/*********************************************************************************************/
    	
    	
		for (Layer layer : Config.LAYERS)
			Config.OUT.addAll(layer.OUT);
		
		
		Config.OUT.add("Total Time: "+(System.currentTimeMillis() - initTime)+" milliseconds");
		
		for (String salida : Config.OUT)
			System.out.println(salida);
		
		System.out.println("Terminando task");

		return null;
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
    	bar.finished();
    	bar.setCursor(null);
    }
    
}
