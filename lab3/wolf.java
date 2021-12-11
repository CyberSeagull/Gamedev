
import java.awt.Color;
import java.awt.Graphics2D;

public class wolf extends hobject {
  	PVector velocity;// = new PVector();
  	PVector acceleration;// = new PVector();
  	Color m_color;
  	double maxforce;    // Maximum steering force
  	double maxspeed;    // Maximum speed  	
    extentcollision m_collision_box;
        
    
    public wolf(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        m_x = x;
        m_y = y;
        m_r = r;
        m_color=mycolor;
        acceleration = new PVector(0,0);
        velocity = new PVector(0,-2);
        position = new PVector(x,y);        
        maxspeed = 4;
        maxforce = 0.1;        
        
    //    m_collision_box = new extentcollision(m_x, m_y, m_r);
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
            
    
    public void update(hlist game, double delta_t) {
    	hobject hun = game.m_objects.get(0);
    	
//    	for(hobject go:game.m_objects) 
//	    System.out.println("------hun_x= "+go.getX()+"  hun_y= "+go.getY()+"  hun_y= "+go.m_x+" "+m_x );    		    
    	
    	seek(new PVector(hun.getX(), hun.getY()));
    		    // Update velocity
    		    velocity.add(acceleration);
    		    // Limit speed
    		    velocity.limit(maxspeed);
    		    position.add(velocity);
    		    // Reset accelerationelertion to 0 each cycle
    		    acceleration.mult(0);
    		    
    		    m_x=position.x;
    		    m_y=position.y;		    
    //		    System.out.println("------hun_x= "+hun.getX()+"  hun_y= "+hun.getY()+"  hun_y= "+hun.m_x );    		    
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
