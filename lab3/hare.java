

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
    extentcollision m_collision_box;
        
    
    public hare(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        m_x = x;
        m_y = y;
        m_r = r;
        m_color=mycolor;
        acceleration = new PVector(0,0);
        velocity = new PVector(3,-2);
        velocity.mult(5);;
        position = new PVector(x,y);        
//        r = 6;
        maxspeed = 5;
        maxforce = 0.15;      
        d=39;
        
       // m_collision_box = new extentcollision(m_x, m_y, m_r);
    }

    void applyForce(PVector force) {
      acceleration.add(force);
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
            
    void boundaries(double width, double height, double d) {
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
        }
      }
    
    public void update(hlist game, double delta_t) {
    	boundaries(game.getWidth(), game.getHeight(), d+m_r/2);
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
