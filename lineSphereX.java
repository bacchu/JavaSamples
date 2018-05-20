/** Copyright - Trivikram Prasad
    Date: May 19, 2018
    All Rights Reserved
 */

// Program to calculate the points of intersection of a vector with a Sphere
import java.util.Scanner;


// DEFINE ALL CLASSES

// 3D Point class
class Point3D{
	double x,y,z; 	//Coordinates of a point
	
	public Point3D (double x, double y, double z)
	{
		this.x = x;
		this.y = y; 
		this.z = z;
	}
}

// Sphere Class
class Sphere{
	double cX, cY, cZ; // Coordinates of the sphere center
	double cR;		// Radius of sphere
	
	public Sphere (double cX, double cY, double cZ, double cR)
	{
		this.cX = cX;
		this.cY = cY;
		this.cZ = cZ;
		this.cR = cR;
	}
}

// String class -- only for formatting output
class StringPoint{
	String pX, pY, pZ; // Converting coordinate values to string values for formatting
	
	public StringPoint (String pX, String pY, String pZ)
	{
		this.pX = pX;
		this.pY = pY;
		this.pZ = pZ;		
	}
}

// The main class. This is where the real s**t happens
public class lineSphereX {
	public static void main(String[] args){
		double a,b,c; 	// Coefficients of the quadratic equation
		double discr;	// Square of the discriminant of the quadratic
		double t1 = 0.0; // parameter 1
		double t2 = 0.0; // parameter 2
			
		Scanner in = new Scanner(System.in);
		
		// Read coordinates of the first point on the line
		System.out.print("Enter x, y, z coordinates of the 1st point on the line: ");
		Point3D point1 = readLineInputCoordinates(in);
		
		// Read coordinates of the second point on the line
		System.out.print("Enter x, y, z coordinates of the 2nd point on the line: ");
		Point3D point2 = readLineInputCoordinates(in);
		
		// Read center coordinates and radius of sphere
		System.out.print("Enter x, y, z center coordinates and RADIUS 'r' of the sphere: ");
		Sphere sphCenter= readSphereInputCoordinates(in);
		
		in.close();
		
		//Calculate coefficients: 'a', 'b', 'c' for the equation 'ax^2 + bx + c = 0
		a = Math.pow(point2.x - point1.x,2) + Math.pow(point2.y - point1.y,2) + Math.pow(point2.z - point1.z,2);
		b = 2 * ((point2.x-point1.x) * (point1.x-sphCenter.cX) + (point2.y-point1.y) * (point1.y-sphCenter.cY)+ (point2.z-point1.z) * (point1.z-sphCenter.cZ));
		c = Math.pow((point1.x - sphCenter.cX),2) +  Math.pow((point1.y - sphCenter.cY),2) +  Math.pow((point1.z - sphCenter.cZ),2) - Math.pow(sphCenter.cR, 2);
		discr = Math.pow(b, 2) - (4*a*c);
		
		// No intersection points
		if (discr < 0)
		{
			System.out.println("\nVector does not intersect the sphere");
			return;		
		}
		else if(discr == 0)  // Single intersection point
		{
			
			System.out.println("\nVector intersects the sphere at a single point:");
			
			if (a > 0)
			{
				t1 = -b/(2*a);
		
				StringPoint XPoint = findXPoint(point1, point2, t1);
			
				System.out.println("The tangent to sphere is at " + XPoint.pX + ","+ XPoint.pY + "," + XPoint.pZ + "\n");
			}
			else{
				System.out.println("Not a line\n");
			}
		}
			
		else // Two intersection points
		{
			System.out.println("\nVector intersects the sphere at two points");
			t1 = (-b - Math.sqrt(discr))/(2*a); // 1st Vector equation parameter
			t2 = (-b + Math.sqrt(discr))/(2*a); // 2nd Vector equation parameter
			
			// Find the points of intersection
			StringPoint XPoint1 = findXPoint(point1, point2, t1);
			StringPoint XPoint2 = findXPoint(point1, point2, t2);
			
			System.out.println("1st point of vector intersection with sphere: " + XPoint1.pX + "," + XPoint1.pY + "," + XPoint1.pZ);
			System.out.println("2nd point of vector intersection with sphere: " + XPoint2.pX + "," + XPoint2.pY + "," + XPoint2.pZ + "\n");
		}
		
		System.exit(0);		
	}

	// Function for user input of sphere center coordinates and radius
	private static Sphere readSphereInputCoordinates(Scanner input) {
		
		double sphere_ctr [] = new double[4];
		Sphere input_sphere = new Sphere(0,0,0,0);
		for (int i = 0; i < 4; i++)
		{
			sphere_ctr[i] = input.nextDouble();
		}
		input_sphere.cX= sphere_ctr[0];
		input_sphere.cY = sphere_ctr[1];
		input_sphere.cZ = sphere_ctr[2];
		input_sphere.cR = sphere_ctr[3];
		
		return input_sphere;		
	}

	// Function for user inputs of point coordinates
	private static Point3D readLineInputCoordinates(Scanner input) {
		
		double line_pt [] = new double[3];
		Point3D input_coords = new Point3D(0,0,0);	
		for (int i = 0; i < 3; i++)
		{
			line_pt[i] = input.nextDouble();
		}
		input_coords.x = line_pt[0];
		input_coords.y = line_pt[1];
		input_coords.z = line_pt[2];
		
		return input_coords;	
	}

	// Function to calculate the point of intersection
	private static StringPoint findXPoint(Point3D point1, Point3D point2, double t) {
		 
		Point3D intPoint = new Point3D(0,0,0);
		StringPoint XStrPoint = new StringPoint("","","");
		
		// Parametric equation of the form L = P + tU
		// where 'L' is the intersection point, 'P' is the point on the line and
		// U is the unit vector (Point2 - Point1)
		intPoint.x = point1.x + t * (point2.x - point1.x);
		intPoint.y = point1.y + t * (point2.y - point1.y);		
		intPoint.z = point1.z + t * (point2.z - point1.z);
			
		XStrPoint = formatString(intPoint);
		
		return XStrPoint;	
	}

	// Function to format the coordinates to string for display/output to console
	private static StringPoint formatString(Point3D intPoint) {
		
		StringPoint strPoint = new StringPoint("","","");
		
		strPoint.pX = String.format("%.2f", intPoint.x);
		strPoint.pY= String.format("%.2f", intPoint.y);
		strPoint.pZ = String.format("%.2f", intPoint.z);
		
		return strPoint;
	}
}