import java.awt.Color;
import java.awt.Graphics2D;

public abstract class hobject {
    double m_x, m_y, m_r;
    double senseradius=0;
    double ominspeed=1;
    double omaxspeed=3;
    boolean delobj=false;
    double circledistance =1;
    double circleradius=1;
    int anglechangestep=15;
    int angle =0;
    Color m_color;
  	double maxforce;    // Maximum steering force
  	double maxspeed;    // Maximum speed  	
    double d;

  	PVector position;// = new PVector();
    PVector velocity;// = new PVector();
  	PVector acceleration;
    
    public abstract void update(hlist game, double delta_t);
    public abstract void draw(Graphics2D g);
    
    
    public double getX() {
        return m_x;
    }
    
    public double getY() {
        return m_y;
    }

    public double getRadius() {
        return m_r;
    }

    double map(double val, double start1, double stop1, double start2, double stop2) {
      	if (Math.abs(stop1-start1)>0.0001)
      	return val*(stop2-start2)/(stop1-start1);
      	else return val;
      }

    void applyForce(PVector force) {
        acceleration.add(force);
      }
      
      void arrive(PVector target) {
          PVector desired = PVector.sub(target, position);     
          double d = desired.mag();
          if (d < 100) {   // Scale with arbitrary damping within 100 pixels
            double m = map(d,0,100,0,maxspeed);
            desired.setmag(m);
          } else 
          	 desired.setmag(maxspeed);
      //The usual steering = desired - velocity
          PVector steer = PVector.sub(desired,velocity);
          steer.limit(maxforce);
          applyForce(steer);
        }

      void seek(PVector target) {
          PVector desired = PVector.sub(target, position);  
          desired.setmag(maxspeed);  // Scale to maximum speed
          PVector steer = PVector.sub(desired,velocity); // Steering = Desired minus velocity
          steer.limit(maxforce);  // Limit to maximum steering force
          applyForce(steer);
        }
              
       public  PVector boundaries_(double width, double height, double d, double dx, double dy) {
    	  PVector desired = null;
      	  width=width-dx;
      	  height=height-dy;
      	  PVector pos = new PVector(position.x-dx, position.y-dy);;
      	
          if (pos.x < d+dx) 
            desired = new PVector(maxspeed, velocity.y);
          else 
          	if (pos.x > width -d) 
          		desired = new PVector(-maxspeed, velocity.y);
          if (pos.y < d+dx) 
            desired = new PVector(velocity.x, maxspeed);
          else if (pos.y > height-d) 
            desired = new PVector(velocity.x, -maxspeed);
          if (desired != null) {
          	desired.normalize();
              desired.mult(maxspeed);
              PVector steer = PVector.sub(desired, velocity);
              steer.limit(maxforce);
              return steer;
          //    applyForce(steer);
           //   System.out.println("!! m_x= "+m_x+"  m_y= "+m_y+" m_r= "+(width -d)+"  ");
            //  return true;
          } else return null;// new PVector(0,0);
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
          
      public void getrandomtarget(PVector futurepos) { //Wandering
        double rnd= Math.random();
    	  if (rnd<0.5) angle+=anglechangestep;
    	  else if (rnd<1) angle-=anglechangestep;
    	  double angleradr = Math.toRadians(angle);

    	  futurepos.x=velocity.x;
        futurepos.y=velocity.y;
        
    	  //PVector futurepos=new PVector(velocity.x , velocity.y);
        futurepos.setmag(circledistance);
        futurepos.add(position);  	  
        PVector vector=new PVector( Math.cos(angleradr), Math.sin(angleradr));
        vector.setmag(circleradius);
//        System.out.println(" angle= "+angle+" rad "+Math.cos(angleradr)+" rad "+angleradr);
        futurepos.add(vector);
      }

}
