package UTILS;

import java.awt.Cursor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    	Thread.sleep(500);
    	Integer total = 0;
    	
    	bar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	
    	long startTime = System.currentTimeMillis();
    	
    	CM8toOWL CM8 = new CM8toOWL();
    	
    	for (Layer layer : Config.LAYERS) {
    		if (!layer.emptyObjList())
    			layer.setCM8(CM8);
    	}
    	    	
		for (Layer layer : Config.LAYERS) {
			Thread.sleep(500);
			if (!layer.emptyObjList()) {
				layer.assertDataForObjsInLayer();
				total += 25/Config.LAYERS.size();
				setProgress(total);
			}
		}	
			
		System.out.println("Pase assert");
		
		for (Layer layer : Config.LAYERS) {
			Thread.sleep(500);
			if (!layer.emptyObjList()) {
				layer.makeObjsDifferent();
				total += 25/Config.LAYERS.size(); 
				setProgress(total);
			}
		}
		
		System.out.println("Pase diff");
		
		for (Layer layer : Config.LAYERS) {
			Thread.sleep(500);
			if (!layer.emptyObjList()) {
				layer.getAssertedDataInLayer();
				total += 25/Config.LAYERS.size(); 
				setProgress(total);
			}	
		}	
		
		System.out.println("Pase getAsserted");
		
		for (Layer layer : Config.LAYERS) {
			Thread.sleep(500);
			if (!layer.emptyObjList()) {
				layer.getInferredDataInLayer();
				total += 25/Config.LAYERS.size(); 
				setProgress(total);
			}	
		}	
		
		System.out.println("Pase getInferred");
		
		for (Layer layer : Config.LAYERS)
			Config.OUT.addAll(layer.OUT);
		
		long endTime = System.currentTimeMillis();
//		
		Config.OUT.add("Total Time: "+(endTime - startTime)+" milliseconds");
		
		for (String salida : Config.OUT)
			System.out.println(salida);
			
//		method.invoke(layer);

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
