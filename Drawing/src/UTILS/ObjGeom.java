package UTILS;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;


public class ObjGeom implements Cloneable{

	/**
	 * @param args
	 * @return 
	 */
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	};
	
	private LinkedList<Coordinate> POINTS = new LinkedList<Coordinate> ();
	private LinkedList<Geometry> GEOMETRY = new LinkedList<Geometry> ();
	
	private Polygon polygon = null;
	private int centX;
	private int centY;
	private Geometry myPolygon = null;
	private int id;
	private int localID;
	
	private String CLASE = "GeoObject";
	private String WIDTH = "";
	private String LENGTH = "";
	private String SURFACE = "";
	private String ELONGATION = "";
	private String FORM = "";
	private String TEXTURE = "";
	private String DENSITY = "";
	private ObjGeom SAMEIND = null;
	private String RESOLUTION = "";
    
	public ObjGeom() {
		System.out.println("-------------------> Se creo un objeto nuevo");
	}
	
    public void addPoint(Point p) {
    	POINTS.addFirst(new Coordinate((int) p.x, (int) p.y));
    }
    
    public Coordinate getFirstPoint() {
    	
    	if (POINTS.isEmpty())
    		return null;
    	else
    		return POINTS.getLast();
    }
    
    public Coordinate getLastPoint() {
    	
    	if (POINTS.isEmpty())
    		return null;
    	else
    		return POINTS.getFirst();
    }
     
    public LinkedList<Coordinate> getPoints() {
    	
    	return POINTS;
    }
    
    public Coordinate[] getCoordinates() {
    	
    	Coordinate[] listaCoord = new Coordinate[POINTS.size()];
    	int i = 0;
    	
    	for (Coordinate coord : POINTS ) {
    		listaCoord[i] = coord; 
    		i++;
    	}
    	return listaCoord;
    	
    }
    
    public void removeLastSegment() {
    	  	
    	if (!POINTS.isEmpty()) {
    		POINTS.removeFirst();
    	    if (POINTS.size() == 1)
    			POINTS.removeFirst();
    	}
    	
    	polygon = null;
    	myPolygon = null;
    	
    	printPoints();
    }
    
    public boolean emptyPoints() {
    	return POINTS.isEmpty();
    }
    
    public int numberOfPoints() {
    	return POINTS.size();
    }
    
    public boolean minimumPoints() {
    	return POINTS.size() > 2;
    }
    
    public void printPoints() {
    	for (Coordinate point : POINTS)
    		System.out.println(point);
    }
    
    public void drawSegment(Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.setStroke(new BasicStroke(2));
    	g2d.setPaint(Color.red);
    	    	
    	if (POINTS.size() > 1) {
    		
    		Coordinate[] lista = getCoordinates();
    		int i;
    		
    		for (i = 0; i < lista.length - 1; i++)
    			g.drawLine((int)lista[i].x, (int)lista[i].y, (int)lista[i+1].x, (int)lista[i+1].y);

    	}
    }
    
    public void closePolygon() {
    	
    	Coordinate coord = getFirstPoint();
		addPoint(new Point((int) coord.x, (int) coord.y));
    }
        
    public void makePolygon() {
    	
    	Coordinate[] lista = getCoordinates();
    	myPolygon = new GeometryFactory().createPolygon(lista); // Genero un Poligono Geom para futuras operaciones
    	centX = (int) myPolygon.getCentroid().getX(); // Busco el centroide para poder escribir el id en ese punto
    	centY = (int) myPolygon.getCentroid().getY();
    	
      	polygon = new Polygon();
      		
    	int i;
    	
    	for (i = 0;i < lista.length;i++)
    		polygon.addPoint((int)lista[i].x, (int)lista[i].y);
    	
    }
   
    public Geometry getMyPolygon() {
    	return myPolygon;
    }
    
    public void setMyPolygon(Geometry geom) {
    	myPolygon = geom;
    }
    
    public void setPolygon(Polygon pol) {
    	polygon = pol;
    }
    
    public Polygon getPolygon() {
    	return polygon;
    }
    
    public void setId(int ident) {
    	id = ident;
    }
    
    public int getId() {
    	return id;
    }
    
    public void setLocalID(int id) {
    	localID = id;
    }
    
    public int getLocalID() {
    	return localID;
    }
    
    public void drawPolygon(Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	g2d.setStroke(new BasicStroke(2));
    	g2d.setPaint(Color.cyan);
    
        if (polygon != null) {	
        	g.drawPolygon ( polygon );
        	g.setFont(new Font("", Font.BOLD, 15));
        	
        	g.drawString("P"+id, (int) myPolygon.getCentroid().getX(), (int) myPolygon.getCentroid().getY());
        }	
        
    	
    }
    
    public Point getFrom8Neighbors(Geometry intersect) {
    	
    	int i,j;
    	
    	int coordX = (int) intersect.getCoordinate().x;
    	int coordY = (int) intersect.getCoordinate().y;
    	
    	Point punto = new Point(coordX, coordY);
    	
    	/** 8 Neighbors of (x,y)
		 * x-1,y-1
		 * x-1,y
		 * x-1,y+1
		 * x,y-1
		 * x,y
		 * x,y+1
		 * x+1,y-1
		 * x+1,y
		 * x+1,y+1
		 */
    	
    	if (getPolygon().contains(punto))
    		return punto;
    	else {
	    	for (i = -1; i< 2; i++) {
	    		for (j = -1; j< 2; j++) {
	    			punto = new Point(coordX+i, coordY+j);
	    			if (getPolygon().contains(punto))
	    				return punto;
	    		}
	    	}
    	}
    	
    	return null;
    	
     }
    
    public Point getIntersection(Geometry line) {
    	
    	Geometry intersect = myPolygon.getBoundary().intersection(line);
    	
		if (!intersect.isEmpty()) {
			//Reviso los 8 vecinos para encontrar el punto mas cercano a la interseccion perteneciente al poligono
			Point finalPoint = getFrom8Neighbors(intersect);
			System.out.println("Raw Intersection: "+intersect+" - Enhanced Intersection: "+finalPoint);
			//return new Point((int) intersect.getCentroid().getX(), (int) intersect.getCentroid().getY());
			return finalPoint;
		}
		else
			return null;
    }
    
    public boolean containsPolygon(Point p) {
    	
    	if (polygon == null)
    		return false;
    		
    	return polygon.contains(p);
    }
    
    public int getCentroidX() {
    	return centX;
    }
    
    public int getCentroidY() {
    	return centY;
    }
    
    public void setCLASE(String clase) {
    	this.CLASE = clase;
    }
    
    public String getCLASE() {
    	return CLASE;
    }
    
	public String getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(String wIDTH) {
		WIDTH = wIDTH;
	}

	public String getLENGTH() {
		return LENGTH;
	}

	public void setLENGTH(String lENGTH) {
		LENGTH = lENGTH;
	}

	public String getSURFACE() {
		return SURFACE;
	}

	public void setSURFACE(String sURFACE) {
		SURFACE = sURFACE;
	}

	public String getELONGATION() {
		return ELONGATION;
	}

	public void setELONGATION(String eLONGATION) {
		ELONGATION = eLONGATION;
	}

	public String getFORM() {
		return FORM;
	}

	public void setFORM(String fORM) {
		FORM = fORM;
	}

	public String getTEXTURE() {
		return TEXTURE;
	}

	public void setTEXTURE(String tEXTURE) {
		TEXTURE = tEXTURE;
	}

	public String getDENSITY() {
		return DENSITY;
	}

	public void setDENSITY(String dENSITY) {
		DENSITY = dENSITY;
	}
	
	public ObjGeom getSAMEIND() {
		return SAMEIND;
	}

	public void setSAMEIND(ObjGeom IND) {
		SAMEIND = IND;
	}
	
	public String getRESOLUTION() {
		return RESOLUTION;
	}

	public void setRESOLUTION(String res) {
		RESOLUTION = res;
	}
  
}
