import java.awt.Color;
import java.awt.Graphics2D;

public class doe extends hobject {
  	double omediumspeed=2;
    double minsenseflock=35;
    double maxsenseflock=120;
        
    
    public doe(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        m_x = x;
        m_y = y;
        m_r = r;
        senseradius=170;
        omaxspeed=3.2;  
        ominspeed=0.7;  
        omediumspeed=(omaxspeed+ominspeed)/2;
        m_color=mycolor;
        acceleration = new PVector(0,0);
        velocity = new PVector(0,0);
        position = new PVector(x,y);        
        maxspeed = 3.2;
        maxforce = 0.1;        
        minsenseflock=m_r*1.4;
        maxsenseflock=m_r*2;//senseradius-m_r*3;

        circledistance =1;
        circleradius=1;
        anglechangestep=15;
        angle =0;    
        d=75;
    }
     // Separation
    public PVector separate (List<hobject> m_objects) {
      PVector steer = new PVector(0,0);
      int count = 0;
      for(hobject o2:m_objects) 
        if (o2!=this)  { //&& (o2 instanceof doe)) {
        	double d= PVector.sub(this.position, o2.position).mag();
        double distsep= ((o2 instanceof wolf)||(o2 instanceof hunter) ) ? senseradius: minsenseflock;
        if ((d > 0) && (d < distsep)) {
          PVector diff = PVector.sub(this.position, o2.position);
          diff.normalize();
          diff.div(d);        // Weight by distance
          steer.add(diff);
          count++;            
        }
      }
      if (count > 0) {
        steer.div((float)count);
      }
      if (steer.mag() > 0) {
        steer.normalize();
        steer.mult(omaxspeed);
        steer.sub(velocity);
        steer.limit(maxforce);
      }
      return steer;
    }
    
    // calculate the average velocity
    public PVector align (List<hobject> m_objects) {
      PVector sum = new PVector(0,0);
      int count = 0;
      for(hobject o2:m_objects) 
          if ((o2!=this) && (o2 instanceof doe)) {
          	double d= PVector.sub(this.position, o2.position).mag();
            if ((d > 0) && (d < maxsenseflock)) {
                sum.add(o2.velocity);
                count++;
              }
            }
      if (count > 0) {
        sum.div((double) count);
        sum.normalize();
        sum.mult(maxspeed);
        PVector steer = PVector.sub(sum,velocity);
        steer.limit(ominspeed);
        return steer;
      } else {
        return new PVector(0,0);
      }
    }
    
    // Cohesion
    public PVector cohesion (List<hobject> m_objects) {
      PVector sum = new PVector(0,0);
      int count = 0;
      
      for(hobject o2:m_objects) 
          if ((o2!=this) && (o2 instanceof doe)) {
          	double d= PVector.sub(this.position, o2.position).mag();
            if ((d > minsenseflock) && (d < senseradius)) {
                sum.add(o2.position);
                count++;
              }
            }
      
      if (count > 0) {
        sum.div(count);
  	    sum.sub(position); // desired
        sum.normalize();       
        sum.mult(maxspeed);
        sum.sub(velocity);
        sum.sub(velocity);  // steer
        sum.limit(maxforce);  // Limit to maximum steering force
      return sum;
      } else
        return new PVector(0,0);
    }    

    void flock(hlist game) {
        PVector sep = separate(game.m_objects);   // Separation
        PVector ali = align(game.m_objects);      // Alignment
        PVector coh = cohesion(game.m_objects);   // Cohesion
        // Arbitrarily weight these forces
        PVector bound = boundaries_(game.getWidth(), game.getHeight(), d+m_r/2, game.m_dx, game.m_dy );
        if (bound==null) bound= new PVector(0,0);
        bound.mult(4.5);
        sep.mult(2.5);
        ali.mult(1.0);
        coh.mult(1.0);
        // Add the force vectors to acceleration
        applyForce(bound);
        applyForce(sep);
        applyForce(ali);
        applyForce(coh);
    	
    }
   
            
    
    public void update(hlist game, double delta_t) {
    	flock(game);
    	
   	 if (!this.delobj)
   		delobj= (GetLengthtotheWalls(game.getWidth(), game.getHeight(), 
   				      position, m_r, game.getdx(), game.getdy())<-m_r/2); 
    velocity.add(acceleration);
    	velocity.limit(maxspeed);
    	position.add(velocity);
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
