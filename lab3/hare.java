

import java.awt.Color;
import java.awt.Graphics2D;

public class hare extends hobject {
  	PVector velocity;// = new PVector();
  	PVector acceleration;// = new PVector();
  	Color m_color;
  	double maxforce;    // Maximum steering force
  	double maxspeed;    // Maximum speed  	
  	double r;
    double d;
    double dcircle;
    double anglestep=15;
    extentcollision m_collision_box;
    double circledistance =1;
    double circleradius=1;
    int anglechangestep=15;
    int angle =0;   
    
    public hare(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        m_x = x;
        m_y = y;
        m_r = r;
        senseradius=100;
        omaxspeed=4;
        m_color=mycolor;
        acceleration = new PVector(0,0);
        velocity = new PVector(3,-2);
       // velocity.mult(5);;
        position = new PVector(x,y);        
//        r = 6;
        maxspeed = 4;
        maxforce = 0.1;      
        d=75;
        
       // m_collision_box = new extentcollision(m_x, m_y, m_r);
    }

    void applyForce(PVector force) {
      acceleration.add(force);
    }
    double map(double val, double start1, double stop1, double start2, double stop2) {
    	if (Math.abs(stop1-start1)>0.0001)
    	return val*(stop2-start2)/(stop1-start1);
    	else return val;
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
        PVector desired = PVector.sub(target,position);  
                // Scale to maximum speed
        desired.setmag(maxspeed);
        // Steering = Desired minus velocity
        PVector steer = PVector.sub(desired,velocity);
        steer.limit(maxforce);  // Limit to maximum steering force
        
        applyForce(steer);
      }
            
    boolean boundaries(double width, double height, double d) {
    	PVector desired = null;
    	
        if (position.x < d) 
          desired = new PVector(maxspeed, velocity.y);
        else 
        	if (position.x > width -d) 
        		desired = new PVector(-maxspeed, velocity.y);
        if (position.y < d) 
          desired = new PVector(velocity.x, maxspeed);
        else if (position.y > height-d) 
          desired = new PVector(velocity.x, -maxspeed);
        if (desired != null) {
        	desired.normalize();
            desired.mult(maxspeed);
            PVector steer = PVector.sub(desired, velocity);
            steer.limit(maxforce);
            applyForce(steer);
          return true;
        } 
       else return false;
      }
    public void getrandomtarget(PVector futurepos) { //Wandering
      double rnd= Math.random();
  	  if (rnd<0.5) angle+=anglechangestep;
  	  else if (rnd<1) angle-=anglechangestep;
  	  double angleradr = Math.toRadians(angle);

  	  futurepos.x=velocity.x;
      futurepos.y=velocity.y;
      futurepos.setmag(circledistance);
      futurepos.add(position);  	  
      PVector vector=new PVector( Math.cos(angleradr), Math.sin(angleradr));
      vector.setmag(circleradius);
      futurepos.add(vector);
    
    public void update(hlist game, double delta_t) {
    	hobject hun =  game.getmindistance(this);//game.m_objects.get(0);
    	PVector target = new PVector(0, 0);
    	//PVector target = new PVector(22, 22);
//    	arrive(target);
        	if (!boundaries(game.getWidth(), game.getHeight(), d+m_r/2))
    	{ 	
        		if (hun == null)
        		{ 
        			maxspeed=ominspeed;
             		getrandomtarget(target);
            		seek(target);
  //                System.out.println(" angle= "+angle+" rad "+target.x+" rad "+target.y);
        		}
        			else

        		{
        			maxspeed=omaxspeed;
        			target.x=hun.getX();
        			target.y=hun.getY();
            		seek(target);
                  	acceleration.mult(-1);
//                  	System.out.println(" hun.x= "+hun.getX()+" hun.y= "+hun.getY()+" rad "+target.x+" rad "+target.y);
        		}
        	
    	}
    	velocity.add(acceleration);
    	velocity.limit(maxspeed);
    	position.add(velocity);
    	// Reset accelerationelertion to 0 each cycle
    	acceleration.mult(0);
    	m_x=position.x;
    	m_y=position.y;		    
    }
    // draw circle  
    public void draw(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.setColor(m_color);
        gg.fillOval((int)(m_x-m_r/2), (int)(m_y-m_r/2), (int)m_r, (int)m_r);
//    	System.out.println("!! m_x= "+m_x+"  m_y= "+m_y+" m_r= "+m_r+"  ");
        gg.dispose();        
    }
    
    public extentcollision getCollisionBox() {
        return m_collision_box;
    }
    
}
