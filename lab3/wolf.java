
import java.awt.Color;
import java.awt.Graphics2D;

public class wolf extends hobject {
  	int timeoflife=100*25;  
        
    
    public wolf(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        m_x = x;
        m_y = y;
        m_r = r;
        senseradius=100;
        omaxspeed=3.1;       
        m_color=mycolor;
        acceleration = new PVector(0,0);
        velocity = new PVector(2,-2);
        position = new PVector(x,y);        
        maxspeed = 4;
        maxforce = 0.5;        
        
        d=22; //min distance to the wall
        circledistance =1;
        circleradius=1;
        anglechangestep=15;
        angle =0;    
    }

    
    
    public void update(hlist game, double delta_t) {
    	hobject hun =  game.getmindistance(this);//game.m_objects.get(0);
    	PVector target = new PVector(0, 0);
    	
//    	for(hobject go:game.m_objects) 
//	    System.out.println("------hun_x= "+go.getX()+"  hun_y= "+go.getY()+"  hun_y= "+go.m_x+" "+m_x );    		    
    	
    	if (!boundaries((double)game.getWidth(), game.getHeight(), d+m_r/2, game.m_dx, game.m_dy ))
    	{ 	
        		if (hun == null)
        		{ 
        			maxspeed=ominspeed;
             		getrandomtarget(target);
            		seek(target);
        		}
        			else
        		{
        			maxspeed=omaxspeed;
        			target.x=hun.getX();
        			target.y=hun.getY();
            		seek(target);
            		if (hun instanceof wolf)  
            			acceleration.mult(-1);
//                  	System.out.println(" hun.x= "+hun.getX()+" hun.y= "+hun.getY()+" rad "+target.x+" rad "+target.y);
        		}
    	}
    	velocity.add(acceleration);
    	velocity.limit(maxspeed);
    	position.add(velocity);
    	//boundaries(game.getWidth(), game.getHeight(), d+m_r/2);
    	//Reset accelerationelertion to 0 each cycle
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
