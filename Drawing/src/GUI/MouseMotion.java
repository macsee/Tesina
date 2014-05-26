package GUI;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import UTILS.*;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;


public class MouseMotion implements MouseMotionListener {

	/**
	 * @param args
	 */
	private static Component window;
	
	public MouseMotion(Component frame) {
		window = frame;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		
		Config.MOUSE_POINT = me.getPoint();
	
		ObjGeom obj = Config.ACTIVELAYER.getNearPolygon(me.getPoint());
		
		if (obj != null) {
			
			Geometry segmentGeom = new GeometryFactory().createLineString(new Coordinate[] {
					new Coordinate (obj.getCentroidX(),obj.getCentroidY()),
					new Coordinate (me.getX(), me.getY())
			});
			
			Config.ACTIVELAYER.setLine(segmentGeom);
			Config.ACTIVELAYER.setGluePoint(obj.getIntersection(segmentGeom));
		}
		
		window.repaint();	
	}

}
