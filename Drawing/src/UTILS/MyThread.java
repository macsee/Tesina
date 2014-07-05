package UTILS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import GUI.MyProgressBar;

public class MyThread extends Thread {

		Method method;
		Layer layer;
		MyProgressBar progressBar;
		double percent;
		
		public MyThread(MyProgressBar p, Method m, Layer l, double perc) {
			method = m;
			layer = l;
			progressBar = p;
			percent = perc;
		}
		
		public void run(){

				try {
					long start = System.currentTimeMillis();
					method.invoke(layer);
					progressBar.updateProgress((int) Math.ceil(percent/Config.LAYERS.size()));
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
