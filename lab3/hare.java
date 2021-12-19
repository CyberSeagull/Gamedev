

import java.awt.Color;
import java.awt.Graphics2D;

public class hare extends hobject {
  	  
    
    public hare(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        m_x = x;
        m_y = y;
        m_r = r;
        senseradius=100;
        omaxspeed=4;
        m_color=mycolor;
        acceleration = new PVector(0,0);
        velocity = new PVector(0,0);
       // velocity.mult(5);;
        position = new PVector(x,y);        
//        r = 6;
        maxspeed = 4;
        maxforce = 0.14;      
        circledistance =1;
        circleradius=1;
        anglechangestep=15;
        angle =0;
        d=75;
        
       // m_collision_box = new extentcollision(m_x, m_y, m_r);
    }

        
    public void update(hlist game, double delta_t) {
    	hobject hun =  game.getmindistance(this);//game.m_objects.get(0);
    	PVector target = new PVector(0, 0);
    	//PVector target = new PVector(22, 22);
//    	arrive(target);
        	if (!boundaries(game.getWidth(), game.getHeight(), d+m_r/2, game.m_dx, game.m_dy ))
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
    
    
    
}
