package UTILS;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import OWL.CM8toOWL;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.IntersectionMatrix;


public class Layer {

	/**
	 * @param args
	 */
	
	private LinkedList<ObjGeom> SHPS = new LinkedList<ObjGeom> ();
	private DefaultListModel DISPLAYLIST = new DefaultListModel();
    private LinkedList<Shape> INTERSECTIONS = new LinkedList<Shape> ();
    private LinkedList<Shape> DIFFERENCES = new LinkedList<Shape> ();
    private Set<String> RELATIONS = new HashSet<String>();
    
    private Geometry LINE;
    private Point ACTUAL_POINT;
    private Point MOUSE_POINT;
    private Point GLUE_POINT;
    private boolean DRAWING_ADJACENT_POLYGON_IN_PROGRESS;
    
    public String LAYER_RESOLUTION;
    public String LAYERID;
    
    private Image IMAGE;
    private ObjGeom LAST_OBJ;
    private boolean DRAWING_ON;
    private boolean NEW_POLYGON;
    
    private boolean DRAW_INTERSECTION;
    private boolean DRAW_DIFFERENCE;
    
    private Geometry DIBUJO;
    
    public ArrayList<String> OUT = new ArrayList<String>();
    public ArrayList<String> AUX = new ArrayList<String>();
    
    private CM8toOWL CM8;
    public String CURRENTDIR = ".";
  
    public Layer(String layerid, String resolution) {
    	LINE = null;
        ACTUAL_POINT = null;
        //MOUSE_POINT = null;
        GLUE_POINT = null;
        DRAWING_ADJACENT_POLYGON_IN_PROGRESS = false;
        
        LAST_OBJ = null;
        DRAWING_ON = false;
        NEW_POLYGON = true;
        
        DRAW_INTERSECTION = false;
        DRAW_DIFFERENCE = false;
        
        LAYER_RESOLUTION = resolution;
        LAYERID = layerid;
    }
    
    public void reset() {
    	NEW_POLYGON = true;
    	ACTUAL_POINT = null;
    	GLUE_POINT = null;
    	//DRAWING_ADJACENT_POLYGON_IN_PROGRESS = false;
    }
    
    public void clean() {
    	Config.OBJCOUNT -= SHPS.size()-1; // Debo restarle el tamaño de la lista de SHPS de la capa porque OBJCOUNT contiene el numero total de poligonos entre todas las capas
    	SHPS.clear();
    	DIFFERENCES.clear();
    	INTERSECTIONS.clear();
    	NEW_POLYGON = true;
    	ACTUAL_POINT = null;
    	GLUE_POINT = null;
    	LINE = null;
    	LAST_OBJ = null;
    	Config.cleanDefaultList();
    	DISPLAYLIST.clear();
    }
     
	public void setImage(String img) {
		try {
			System.out.println(img);
			IMAGE = ImageIO.read(new File(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}; 
	}
	
	public Image getImage() {
		return IMAGE;
	}
	
	public void setObjsGeom(LinkedList<ObjGeom> list) {
		SHPS.clear();
		for (ObjGeom obj : list) {
			addObj(obj);
			Config.cleanDefaultList();
			Config.addToDefaultList(obj);
		}
		reset();
	}
	
	public LinkedList<ObjGeom> getObjsGeom() {
		return SHPS;
	}
	
//	public DefaultListModel getDefaultList() {
//		return DISPLAYLIST;
//	}
	
	public void continuePolygon() {
		NEW_POLYGON = false;
	}
	
	public boolean adjacentPolygonInitiated() {
		return GLUE_POINT != null;
	}
	
	public Point getGluePoint() {
		return GLUE_POINT;
	}
	
	public boolean drawingON() {
		return DRAWING_ON;
	}
	
	public void allowDrawing() {
		DRAWING_ON = true;
	}
	
	public boolean emptyObjList() {
		return SHPS.isEmpty();
	}
	
	public void setGluePoint(Point p) {
		GLUE_POINT = p;
	}
	
	public void beginAdjacentPolygon(ObjGeom obj) {
		obj.addPoint(GLUE_POINT);
		ACTUAL_POINT = GLUE_POINT;
		GLUE_POINT = null;
	}
	
	public boolean newPolygonInitiated() {
		return NEW_POLYGON;
	}
	
	public void addObj(ObjGeom obj) {
		obj.setId(Config.OBJCOUNT);
		obj.setRESOLUTION(LAYER_RESOLUTION);
		Config.OBJCOUNT++;
		obj.setLocalID(SHPS.size());
		//SHPS.addFirst(obj);
		SHPS.addLast(obj);
		//Config.addToDefaultList(obj);
		NEW_POLYGON = false;
	}
	
	
	public Point getActualPoint() {
		return ACTUAL_POINT;
	}
	
	public void setActualPoint(Point p) {
		ACTUAL_POINT = p;
	}
	
	public Geometry getLine() {
		return LINE;
	}
	
	public void setLine(Geometry l) {
		LINE = l;
	}
	
	public ObjGeom getObj(int index) {
		if (index < SHPS.size()) 
			return SHPS.get(index);
		else
			return null;
	}
	
	public ObjGeom getLastObjGeom() {
		
		if (!SHPS.isEmpty()){
			//return (ObjGeom) SHPS.getFirst();
			return (ObjGeom) SHPS.getLast();
		}	
		
		NEW_POLYGON = true;
		return null;
		
	}
	
	public ObjGeom getFirstObjGeom() {
		
		if (!SHPS.isEmpty())
			return (ObjGeom) SHPS.getFirst();
			//return (ObjGeom) SHPS.getLast();
		else
			return null;
		
	}
	
	public void removeLastObjGeom() {
		NEW_POLYGON = true;
		if (!SHPS.isEmpty()) {
			//SHPS.removeFirst();
			SHPS.removeLast();
			Config.removeFromDefaultList(0);
			Config.OBJCOUNT--;
		}	
	}
	
	public void removeObjInCourse() {
		if (!SHPS.isEmpty()) {
			//SHPS.removeFirst();
			SHPS.removeLast();
			Config.OBJCOUNT--;
		}
	}
	
	public void removeSelectedObject(ObjGeom obj) {
		Config.removeFromDefaultList(obj.getLocalID());
		SHPS.remove(obj);
		Config.OBJCOUNT--;
		return;
	}
	
	public ObjGeom getSelectedObj(Point p) {
			
		for (ObjGeom polygon : SHPS) {
				
			if (polygon.containsPolygon(p)) {
				System.out.println("Poligono seleccionado: "+polygon.getId());
				LAST_OBJ = polygon;
				return polygon;
			}	
				
		}
		
		return null;
	}
	
	public ObjGeom getNearPolygon(Point p) {
		
		ObjGeom pol = null;
		GLUE_POINT = null;
		Geometry mouseCursor = new GeometryFactory().createPoint(new Coordinate (p.x,p.y));
		
		for (ObjGeom polygon : SHPS) { // Recordar que a partir de que hago click siempre tengo un poligono. Aunque sea vacio
			
			if (polygon.getMyPolygon() != null)			
				if (polygon.getMyPolygon().getBoundary().distance(mouseCursor) < 10) {
					pol = polygon;
					LAST_OBJ = polygon; // Necesito guardar el poligono cercano al punto ya que una vez que empiezo a dibujar
				}						// el punto se aleja del poligono y pierdo la referencia
				
		}
		
		//OBJ_SELECTED = pol;
		return pol;
	}
	
	public Polygon convertGeometryToPolygon(Geometry obj) {
		
		Polygon pol = new Polygon();
		
		for (Coordinate coord : obj.getCoordinates()) {
			pol.addPoint((int) coord.x, (int) coord.y);
		}
		
		return pol;
	}
	
	public boolean significantIntersection(ObjGeom ob1, ObjGeom ob2) {
		
		Geometry res = ob1.getMyPolygon().intersection(ob2.getMyPolygon());
		//System.out.println(res.getArea());
		
		if (!res.isEmpty()) // Si es que hay interseccion
			return res.getArea() > 2;
		
		return false;
		
		// Si existe area en comun entre los dos polígonos y es menor que 1, entonces considero que la interseccion no fue hecha a proposito
		// de lo contrario existe verdaderamente la interseccion de interiores.		
	}
	
	/** PRIMITIVAS CM8 
	
	xº - yº = Ø : x es parte de (esta incluido en) y => P(x,y) 
	xº - yº ≠ Ø : x no es parte de (no esta incluido en) y => Dx(x,y)
	-------> interiorDifference(x,y)
	
	yº - xº = Ø : x contiene a y => P-1(x,y)
	yº - xº ≠ Ø : x no contiene a y => Dy(x,y)
	-------> interiorDifference(y,x)
	
	yº ∩ xº = Ø : x es discreto de y => DR(x,y)
	yº ∩ xº ≠ Ø : x se superpone a y => O(x,y)
	-------> interiorIntersection(x,y)
	
	∂x ∩ ∂y = Ø : x no comparte frontera con y => NA(x,y)
	∂x ∩ ∂y ≠ Ø : x comparte frontera con y => A(x,y)
	-------> boundaryIntersection(x,y)
	
	**/
	
	public boolean interiorDifference(ObjGeom ob1, ObjGeom ob2) {
		
		Geometry x = ob1.getMyPolygon();
		Geometry y = ob2.getMyPolygon();
		
		Geometry res = x.difference(y); // xº - yº
			
		DIFFERENCES.add(convertGeometryToPolygon(res));
		
		return !res.isEmpty();   
		
		// Si es True entonces la diferencia es ≠ Ø entonces Dx(x,y) se cumple o Dy(x,y) si es alreves
		// Si es False entonces la diferencia es = Ø entonces P(x,y) se cumple o P-1(x,y) si es alreves
		
	}
	
	public boolean interiorIntersection(ObjGeom ob1, ObjGeom ob2) {
		
		Geometry x = ob1.getMyPolygon();
		Geometry y = ob2.getMyPolygon();
			
		if (significantIntersection(ob1,ob2)) { // Controlo si la interseccion es verdadera o un error de dibujo
			Geometry res = x.intersection(y);
			INTERSECTIONS.add(convertGeometryToPolygon(res));
			return x.relate(y,"T********"); // De acuerdo a la matriz de interseccion, una T en el primer caracter de la cadena
		}							//  significa interseccion de interiores, xº ∩ yº ≠ Ø
		else
			return false;
		
		// Si es True entonces la interseccion es ≠ Ø entonces O(x,y) se cumple
		// Si es False entonces interseccion es = Ø entonces DR(x,y)
		
	}

	public boolean boundaryIntersection(ObjGeom ob1, ObjGeom ob2) {
		
		Geometry x = ob1.getMyPolygon();
		Geometry y = ob2.getMyPolygon();
		//System.out.println(x.relate(y));	
		return x.relate(y,"****T****"); // De acuerdo a la matriz de interseccion, una T en el quinto caracter de la cadena 
										// significa interseccion de fronteras, ∂x ∩ ∂y ≠ Ø 
		
		// Si es True entonces la interseccion es ≠ Ø entonces NA(x,y)
		// Si es False entonces la interseccion es = Ø entonces A(x,y) se cumple
		

	}
	
	
	public void checkCM8FromRClick(ObjGeom ob1) {
		
//		setCM8(new CM8toOWL());
//		assertDataForObjGeom(ob1);
//		makeObjsDifferentInLayer();		
//		asertSameIndividualsInLayer();		
//		getAssertedDataInLayer();
//		getInferredDataInLayer();
		
	}
	
	public void checkCM8PrimitivesForPolygon(ObjGeom ob1) {
				
		for (ObjGeom ob2 : SHPS) {
			
			String outCM8 = "";
			String outTXT = "";
						
			if (ob2.getMyPolygon() != null & ob2.getId() != ob1.getId()) {
							
				outCM8 += "x = Polygon "+ob1.getId()+", y = Polygon "+ob2.getId()+" :: \n";
				
				if (interiorDifference(ob1, ob2)) {
					outTXT += "xº - yº ≠ Ø ==> x is not contained in y \n";
					outCM8 += "NP(x,y) - ";
					RELATIONS.add(CM8.assertRelation("NP", ob1, ob2));
				}	
				else {
					outTXT += "xº - yº = Ø ==> x is contained in y \n";
					outCM8 += "P(x,y) - ";
					RELATIONS.add(CM8.assertRelation("P", ob1, ob2));
				}	
				
				if (interiorDifference(ob2, ob1)) {
					outTXT += "yº - xº ≠ Ø ==> x does not contain y \n";
					outCM8 += "NP-1(x,y) - ";
					RELATIONS.add(CM8.assertRelation("NPi", ob1, ob2));
				}	
				else {
					outTXT += "yº - xº = Ø ==> x contains y \n";
					outCM8 += "P-1(x,y) - ";
					RELATIONS.add(CM8.assertRelation("Pi", ob1, ob2));
				}	
				
				if (interiorIntersection(ob1, ob2)) {
					outTXT += "xº INTERSECT yº ≠ Ø ==> x overlaps y \n";
					outCM8 += "O(x,y) - ";
					RELATIONS.add(CM8.assertRelation("O", ob1, ob2));
				}	
				else {
					outTXT += "xº INTERSECT yº = Ø ==> x interior disjoint from y \n";
					outCM8 += "DR(x,y) - ";
					RELATIONS.add(CM8.assertRelation("DR", ob1, ob2));
				}	
				
				if (boundaryIntersection(ob1, ob2)) {
					outTXT += "∂x INTERSECT ∂y ≠ Ø ==> x share boundary with y \n";
					outCM8 += "A(x,y)\n";
					RELATIONS.add(CM8.assertRelation("A", ob1, ob2));
				}	
				else {
					outTXT += "∂x INTERSECT ∂y = Ø ==> x does not share boundary with y \n";
					outCM8 += "NA(x,y)\n";
					RELATIONS.add(CM8.assertRelation("NA", ob1, ob2));
				}
				
				AUX.add(outCM8);
				AUX.add(outTXT);
				
			}
			
		}

		AUX.add("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		AUX.add("");
	}
	
	
	
	
	public void assertDataForObjGeom(ObjGeom obj) {
		
		CM8.assertIndividual(obj); //asserting ob1 as individual
		CM8.assertObjProperty(obj, "hasElongation", obj.getELONGATION());
		CM8.assertObjProperty(obj, "hasWidth", obj.getWIDTH());
		CM8.assertObjProperty(obj, "hasLength", obj.getLENGTH());
		CM8.assertObjProperty(obj, "hasShape", obj.getFORM());
		CM8.assertObjProperty(obj, "hasTexture", obj.getTEXTURE());
		CM8.assertObjProperty(obj, "hasDensity", obj.getDENSITY());
		CM8.assertObjProperty(obj, "hasSurface", obj.getSURFACE());
				
		CM8.assertBooleanProperty(obj, "hasAlignment", obj.getALIGN());
		CM8.assertBooleanProperty(obj, "hasDiscontinuity", obj.getDISCONTINUE());
		
		if (obj.isUsable() || obj.classificationForced() || Config.FORCEDETECTION) {
			CM8.assertObjProperty(obj, "hasResolution", obj.getRESOLUTION());
			System.out.println("Setting resolution for object P"+obj.getId());
		}	
				
		checkCM8PrimitivesForPolygon(obj);	
	}
	
	public void assertDataForObjsInLayer() {
						
		/**
		 * Realizo un assert en la ontología de todos los datos contenidos en cada ObjGeom
		 **/
		
		for (ObjGeom obj : SHPS)
			if (obj.getMyPolygon() != null)//Hago esto para evitar que un click accidental sea considerado como ObjGeom
				assertDataForObjGeom(obj);
		
		// Lo tengo que hacer por separado porque sino no puedo utilizar la información de todos los objetos para el cálculo
		// de isComposedOf
		
		for (ObjGeom obj : SHPS)
			if (obj.getMyPolygon() != null) { //Hago esto para evitar que un click accidental sea considerado como ObjGeom				
				if (obj.classificationForced() || Config.FORCEDETECTION) {
					CM8.countObjsRelatedWith(obj, "isAdjacentTo");
					CM8.countObjsRelatedWith(obj, "isComposedOf");
					CM8.countObjsRelatedWith(obj, "isIncludedIn");
				}	
			}
		
	}	
	
	public void  makeObjsDifferentInLayer() {
		for (ObjGeom obj : SHPS)
			CM8.makeAllIndsDifferentFrom(obj);
	}
	
	public void asertSameIndividualsInLayer() {
		for (ObjGeom obj : SHPS)
			CM8.assertSameIndividuals(obj);
	}
	
	public void getAssertedDataInLayer() {
		
		int i;
		
		OUT.add("*********************************************** LAYER "+LAYER_RESOLUTION+" ***********************************************");
		OUT.add("*********************************************************************************************************\n");
		
		if (SHPS.isEmpty()) {
			OUT.add("");
			OUT.add("No objects detected in this layer");
			OUT.add("");
			return;
		}
		
		for (i=0;i<AUX.size()-2;i++)
			OUT.add(AUX.get(i));
		
		OUT.add("*********************************************************************************************************");
		OUT.add("*************************************** Asserted Data for Objects ***************************************");
		OUT.add("*********************************************************************************************************\n");
	
		
		for (ObjGeom objgeom : SHPS)
			OUT.addAll(CM8.getAssertedDataForObject(objgeom));
		
		OUT.add("");
		
		OUT.add("*********************************************************************************************************");
		OUT.add("******************************** Asserted Data for Spatial Relationships ********************************");
		OUT.add("*********************************************************************************************************\n");
		
		for (String rel : RELATIONS)
			OUT.addAll(CM8.getAssertedDataForSpatialRelation(rel));
		
		OUT.add("");
		
	}
	
	public void getInferredDataInLayer() {
		
		if (SHPS.isEmpty())
			return;
	
		OUT.add("*********************************************************************************************************");
		OUT.add("*************************************** Inferred Data for Objects ****************************************");
		OUT.add("**********************************************************************************************************\n");
		
		for (ObjGeom objgeom : SHPS)
				OUT.addAll(CM8.getInferredDataForObject(objgeom));
		
		OUT.add("");
		
		System.out.println("Inferred data for objects finished");
		
		OUT.add("*********************************************************************************************************");
		OUT.add("******************************* Inferred Data for Spatial Relationships *********************************");
		OUT.add("*********************************************************************************************************\n");
		
		for (String rel : RELATIONS)
			OUT.addAll(CM8.getInferredDataForSpatialRelation(rel));
	
		OUT.add("");
		
	}
		
	/**********************************************************************************
	 * DRAWING FUNCTIONS
	 * @param g
	 ***********************************************************************************/
	
	public void drawLines(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		   
        g2d.setPaint(Color.red);
        g2d.setStroke(new BasicStroke(2));
		
		if (ACTUAL_POINT != null && !NEW_POLYGON)
			g.drawLine(ACTUAL_POINT.x,ACTUAL_POINT.y,Config.MOUSE_POINT.x,Config.MOUSE_POINT.y);

	}
	
	public void drawInterCircle(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.red);
	    g2d.setStroke(new BasicStroke(2));
	    
		if (GLUE_POINT != null)
			g.drawOval(GLUE_POINT.x-5, GLUE_POINT.y-5, 10, 10);
	}
	
	public void drawFromCentroid(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.red);
	    g2d.setStroke(new BasicStroke(2));
	    
	    if (LAST_OBJ != null) {
	    	g2d.draw(convertGeometryToPolygon(LINE));
	    }	
	}
			
	public void drawInter(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
    	
		g2d.setPaint(Color.blue);
	    g2d.setStroke(new BasicStroke(2));
	   
    	for (Shape pol : INTERSECTIONS) {
    		g2d.draw(pol);
    		g2d.fill(pol);
    	}
	}
	
	public void drawDiff(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
    	
		g2d.setPaint(Color.red);
	    g2d.setStroke(new BasicStroke(2));
	    
    	for (Shape pol : DIFFERENCES) {
    		g2d.draw(pol);
    		g2d.fill(pol);
    	}	
	}
	
	
	public void drawObjects(Graphics g) {
		
		for (ObjGeom obj :SHPS) {
        	obj.drawSegment(g);
        	obj.drawPolygon(g);
        }
    	
	}
	
	public void printObjects(LinkedList<ObjGeom> list) {
		
		if (list.isEmpty())
			System.out.println("NINGUNO");
		
		for (ObjGeom obj : list) {
    		System.out.println(obj.getId());
    	}
		
		System.out.println();
	}
	
	public void printLayer() {
		for (String str : OUT) {
    		System.out.println(str);
    	}
	}
	
	public void setCM8(CM8toOWL cm8) {
		CM8 = cm8;
	}
	
	public void changeResolution(String newRes) {
		LAYER_RESOLUTION = newRes;
		for (ObjGeom obj : SHPS)
			obj.setRESOLUTION(newRes);
		
		Config.refreshLayerList(this);
	}
	
	public LinkedList<String> createCSV() {
		
		LinkedList<String> listado = new LinkedList<String>();
		
		if (emptyObjList())
			return listado;
		
		listado.add("ID;CLASS;WIDTH;LENGTH;SURFACE;ELONGATION;FORM;TEXTURE;DENSITY;RESOLUTION;DISCONTINUE;ALIGN;FORCECLASSIF");
//		listado.add("ID;CLASS;WIDTH;LENGTH;SURFACE;ELONGATION;FORM;TEXTURE;DENSITY;SAMEIND;RESOLUTION;DISCONTINUE;ALIGN");
		
		for (ObjGeom obj : SHPS) {
			
			String forced = "No";
			if (obj.classificationForced())
				forced = "Yes";
				
			
			listado.add(obj.getLocalID()+";"+
					obj.getCLASE()+";"+
					obj.getWIDTH()+";"+
					obj.getLENGTH()+";"+
					obj.getSURFACE()+";"+
					obj.getELONGATION()+";"+
					obj.getFORM()+";"+
					obj.getTEXTURE()+";"+
					obj.getDENSITY()+";"+
					obj.getRESOLUTION()+";"+
					obj.getDISCONTINUE()+";"+
					obj.getALIGN()+";"+
					forced);
		}
			
		return listado;
	}
	
	public void readCSV(String filename) {
		
		BufferedReader br = null;
		
		try {
			 
			String line = "";
			boolean firstline = true;
			
			br = new BufferedReader(new FileReader(filename));
			
			String[] attr = {"","","","","","","","","","","","",""};
//			Integer sameInd = null;
			int i = 0;
		
			while ((line = br.readLine()) != null) {
								
				if (!firstline) {
					
					String[] orig = line.split(";");				
					System.arraycopy(orig, 0, attr, 0, orig.length);
					
					ObjGeom obj = getObj(i);
					i++;
					
					if (obj == null) {
						br.close();
						JOptionPane.showMessageDialog(null,"There are more objects in file than polygons in the selected Layer","Warning",JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					//"ID;CLASS;WIDTH;LENGTH;SURFACE;ELONGATION;FORM;TEXTURE;DENSITY;RESOLUTION;DISCONTINUE;ALIGN;FORCECLASSIF"
					
					obj.setCLASE(attr[1]);
					obj.setWIDTH(attr[2]);
					obj.setLENGTH(attr[3]);
					obj.setSURFACE(attr[4]);
					obj.setELONGATION(attr[5]);
					obj.setFORM(attr[6]);
					obj.setTEXTURE(attr[7]);
					obj.setDENSITY(attr[8]);
					obj.setDISCONTINUE(attr[10]);
					obj.setALIGN(attr[11]);
					
					if (attr[12].equals("Yes"))
						obj.setCLASSIFIABLE();
					
				}
				
				firstline = false;	
			}
			
			if (!attr[9].contentEquals(LAYER_RESOLUTION))
				if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,"Objects in file have different resolution than active Layer.\n Change active Layer resolution?","Warning",JOptionPane.OK_CANCEL_OPTION))
					changeResolution(attr[9]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void printMatrix(IntersectionMatrix patern, ObjGeom ob1, ObjGeom ob2) {
		System.out.println("   P"+ob2.getId()+"   I  B");
		System.out.println("P"+ob1.getId()+" I |  "+patern.get(0,0)+"  "+patern.get(0,1)+" |");
		System.out.println("   B |  "+patern.get(1,0)+"  "+patern.get(1,1)+" |");
	}
}
