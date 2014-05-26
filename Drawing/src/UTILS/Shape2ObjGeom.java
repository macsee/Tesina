package UTILS;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.crypto.dsig.TransformException;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;


public class Shape2ObjGeom {

	/**
	 * @param args
	 */
	private static LinkedList<Geometry> list;
	private static double resultX;
	private static double resultY;
	private static double originX;
	private static double originY;
	
	private static double minLat;
	private static double minLong;
	private static double maxLat;
	private static double maxLong;
	
	
	public Shape2ObjGeom(String shape, String points) throws IOException {
		// TODO Auto-generated constructor stub
		File file = new File(shape);
		
	    Map<String, URL> map = new HashMap<String, URL>();      
	    map.put("url", file.toURI().toURL());

	    DataStore dataStore = DataStoreFinder.getDataStore(map);

	    //SimpleFeatureSource featureSource = dataStore.getFeatureSource(file.getAbsolutePath()); //this works too
	    SimpleFeatureSource featureSource = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);        
	    SimpleFeatureCollection collection = featureSource.getFeatures();
	    SimpleFeatureIterator iterator = collection.features();
	    
	    list = new LinkedList<Geometry>();
	    
	    try {
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                list.add((Geometry) feature.getDefaultGeometry());
            }
        } finally {
            iterator.close(); // IMPORTANT
        }
	    
	    String line = "";
	    
	    minLat = 0;
	    minLong = 0;
	    maxLat = 0;
	    maxLong = 0;
	    
	    BufferedReader br = new BufferedReader(new FileReader(points));
	    
		while ((line = br.readLine()) != null) {
		
		        // use comma as separator
			String[] values = line.split(",");
			
			System.out.println(values[3]);
			
			if (values[2].equals("0") & values[3].equals("0")) {
				minLat = Double.valueOf(values[0]);
				minLong = Double.valueOf(values[1]);
			}
			
			if (values[2].equals("800") & values[3].equals("-600")) {
				maxLat = Double.valueOf(values[0]);
				maxLong = Double.valueOf(values[1]);
			}	
		}
 
	    
	}
	
	public ObjGeom reproject(Geometry geom) {
		
		Polygon polygon = new Polygon();
		Coordinate[] coordPol = geom.getCoordinates();
		int i = 0;
		
		for (Coordinate coord : geom.getCoordinates()) {
        	
			double x = coord.x;
        	double y = coord.y;
        	
        	double xx = Math.abs((x - originX))*800/resultX;
        	double yy = Math.abs((originY - y))*600/resultY;
        	
        	System.out.println("("+xx+","+yy+")");
        	
        	coordPol[i] = new Coordinate(xx,yy);
        	polygon.addPoint((int) xx, (int) yy);
        	i++;
        	
        }
		
		ObjGeom obj = new ObjGeom();
		obj.setMyPolygon(new GeometryFactory().createPolygon(coordPol));
		obj.setPolygon(polygon);
	
		return obj;
	}
	
	public LinkedList<ObjGeom> adjustProyection() throws MismatchedDimensionException, TransformException, FactoryException, org.opengis.referencing.operation.TransformException {
		
//		int coordX = (int) coord.y;
//		int coordY = (int) coord.x;
//		
//		double minLat = 7.764116;
//		double minLong = 48.597496;
//		double maxLat = 7.767276;
//		double maxLong = 48.595908;
		
//		CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326");
//        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:3857");
//        
//        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, false);
//        
        GeometryFactory geometryFactory = new GeometryFactory();
        
     
//        Geometry origin = (JTS.transform(geometryFactory.createPoint(new Coordinate(minLat, minLong)),transform)); // (0,0)
		Geometry origin = (geometryFactory.createPoint(new Coordinate(minLat, minLong))); // (0,0)

		originX = origin.getCentroid().getX();
        originY = origin.getCentroid().getY();
        
        System.out.println(origin);
        
        double maxX = geometryFactory.createPoint(new Coordinate(maxLat, minLong)).getCentroid().getX(); // (800,0)
 
        double maxY = geometryFactory.createPoint(new Coordinate(minLat,  maxLong)).getCentroid().getY(); // (0,600)
        
        resultX = Math.abs(maxX - originX);
        resultY = Math.abs(originY - maxY);
        
        System.out.println("ResultadoX: "+resultX);
        System.out.println("ResultadoY: "+resultY);
        
        System.out.println();
        
        LinkedList<ObjGeom> objList = new LinkedList<ObjGeom>(); // Lista con todas las Geometries reproyectadas.
        
        for (Geometry geom : list) {
        	//Geometry geomConvert = JTS.transform(geom, transform);
        	objList.add(reproject(geom));
        }
        
        return objList;
	}

}
