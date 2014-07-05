package UTILS;

import java.awt.Cursor;
import java.awt.peer.PanelPeer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import GUI.MainPanel;
import GUI.MyProgressBar;
import OWL.CM8toOWL;

public class MyMultiTask extends SwingWorker<Void, Void> {
    /*
     * Main task. Executed in background thread.
     */
	
	private MyProgressBar bar;
	
	public MyMultiTask(MyProgressBar progressBar) {
		
		bar = progressBar;
//		addPropertyChangeListener(procedure);
	}

	@Override
    public Void doInBackground() throws InterruptedException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
    	System.out.println("Empezando task");
    	    	
    	Integer total = 0;
    	
    	bar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	
    	long initTime = System.currentTimeMillis();
    	
    	CM8toOWL CM8 = new CM8toOWL();
    	
    	for (Layer layer : Config.LAYERS) {
    		if (!layer.emptyObjList())
    			layer.setCM8(CM8);
    	}
    	
    	
    	long startTime = System.currentTimeMillis();
    	doProcess(Layer.class.getMethod("assertDataForObjsInLayer"));
		System.out.println(">>>>>>>>>>>>>>>>>>>>>Asserting Objs");
		System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds\n");

		startTime = System.currentTimeMillis();
    	doProcess(Layer.class.getMethod("asertSameIndividualsInLayer"));
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>Same Objs");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds\n");
		
    	startTime = System.currentTimeMillis();
    	doProcess(Layer.class.getMethod("assertLayerResolution"));
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>Asserting Resolution");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds\n");
    	
		startTime = System.currentTimeMillis();
    	doProcess(Layer.class.getMethod("makeObjsDifferentInLayer"));
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>Different Objs");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds\n");
    	
    	
		CM8.saveOnto();
    	
    	if (!CM8.checkConsistency()) {
			JOptionPane.showMessageDialog(null,"Inconsistent Ontology","Error",JOptionPane.ERROR_MESSAGE);
			bar.updateProgress(100);
			return null;
		}	
    	
    	startTime = System.currentTimeMillis();
    	doProcess(Layer.class.getMethod("getAssertedDataInLayer"));	
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>Getting Asserted Objs");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds\n");
		
    	startTime = System.currentTimeMillis();
		doProcess(Layer.class.getMethod("getInferredDataInLayer"));
		System.out.println(">>>>>>>>>>>>>>>>>>>>>Getting Inferred Objs");
    	System.out.println("Time: "+(System.currentTimeMillis() - startTime)+" milliseconds\n");
		
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
     
    public void doProcess(Method m) {
    	
    	MyThread[] threads = new MyThread[Config.LAYERS.size()];
    	
    	int i = 0;
		for (Layer layer : Config.LAYERS) {
			try {
				if (!isCancelled()) {
					System.out.println("Thread: "+i);
					threads[i] = new MyThread(bar,m,layer);
					threads[i].run();
					i++;
				}	
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		for (i = 0; i < Config.LAYERS.size(); i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
