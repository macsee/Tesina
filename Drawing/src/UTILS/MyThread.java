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
					long start = System.currentTimeMillis();
					method.invoke(layer);
					progressBar.updateProgress((int) Math.ceil(25.0/Config.LAYERS.size()));
					System.out.println("Time thread: "+(System.currentTimeMillis()-start));
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//layer.assertDataForObjsInLayer();
					
					
		}
		
}
