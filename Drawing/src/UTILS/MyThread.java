package UTILS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import GUI.MyProgressBar;

public class MyThread extends Thread {

		Method method;
		Layer layer;
		MyProgressBar progressBar;
		
		public MyThread(MyProgressBar p, Method m, Layer l) {
			method = m;
			layer = l;
			progressBar = p;
		}
		
		public void run(){

				try {
					
					Thread.sleep(200);
					method.invoke(layer);
					System.out.println("Metodo invocado");
					progressBar.updateProgress((int) Math.ceil(25.0/Config.LAYERS.size()));
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Thread interrupted");
					e.printStackTrace();
				}	
				//layer.assertDataForObjsInLayer();
					
					
		}
		
}
