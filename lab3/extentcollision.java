
public class extentcollision {
    public PVector C;
    public double r;
    public extentcollision(double x, double y, double radius) {
        C = new PVector(x,y);
        r = radius;
    }
		
	public static double GetLengthtotheWalls(double x_w, double y_h, PVector C, double r) {
    	double lmin; //length min
        double lcentr=r/2;
        lmin=C.x-lcentr; // left wall
        if (lmin > (x_w-(C.x+lcentr)) ) lmin = (x_w-(C.x+lcentr)); //right wall
        if (lmin > (y_h-(C.y+lcentr)) ) lmin = (y_h-(C.y+lcentr)); //down wall
        if (lmin > (C.y-lcentr) )       lmin = C.y-lcentr; //top wall
    //	System.out.println("!! lmin= "+lmin+" C.x=  "+C.x+" C.y=  "+C.y+"   "+lcentr+"   "+ Math.abs(Math.cos(r.ang))  );
    	return lmin;     
    }
    
    public static boolean CircleCollision(extentcollision cc1, extentcollision cc2)
    {
     	PVector CentrD = new PVector(cc2.C.x-cc1.C.x, cc2.C.y-cc1.C.y);
    	double CircleD = CentrD.mag();
        return (CircleD<=cc1.r+cc2.r);
    }    

}
