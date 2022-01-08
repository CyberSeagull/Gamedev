package pwclient;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;

public class gobject {
    PVector position = new PVector(-1,-1);
	PVector poscl    = new PVector(-1,-1);
    Point pgrid;
    Color m_color;//=Color.green;
  	int c_r=0;
  	int c_g=0;
  	int c_b=0;
  	double m_x, m_y, m_r;
    double senseradius=0;
    double ominspeed=1;
    double omaxspeed=3;
    boolean delobj=false;
  
  public abstract void update(glist game, double delta_t);
    public abstract void draw(Graphics2D g);

    public double getRadius() {
        return m_r;
    }
    public  double GetLengthtotheWalls(double x_w, double y_h, PVector pos, double r, double dx, double dy) {
    	double lmin; //length min
        double lcentr=r/2;
        PVector C =new PVector(pos.x-dx, pos.y-dy);
        x_w=x_w-dx;
        y_h=y_h-dy;
        lmin=C.x-lcentr; // left wall
        if (lmin > (x_w-(C.x+lcentr)) ) lmin = (x_w-(C.x+lcentr)); //right wall
        if (lmin > (y_h-(C.y+lcentr)) ) lmin = (y_h-(C.y+lcentr)); //down wall
        if (lmin > (C.y-lcentr) )       lmin = C.y-lcentr; //top wall
    //	System.out.println("!! lmin= "+lmin+" C.x=  "+C.x+" C.y=  "+C.y+"   "+lcentr+"   "+ Math.abs(Math.cos(r.ang))  );
    	return lmin;     
    }

}
