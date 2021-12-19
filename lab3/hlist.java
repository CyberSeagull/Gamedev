import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class hlist {

    List<hobject> m_objects = new ArrayList<>();
    boolean stopstart=false;
    int m_width, m_height;    
    int m_dx=10, m_dy=30;    
    int m_frames_per_second;
    int countsectimeoflife=77;
    int r_width;
    int r_height;
    
    public hlist(int width, int height, int frames_per_second) {
        m_width = width;
        m_height = height;
	r_width = width-2*m_dx;
        r_height = height-2*m_dy;
        m_frames_per_second = frames_per_second;
    }
    
    public int getWidth() {
        return m_width;
    }
    
    public int getHeight() {
        return m_height;
    }
	public int getdx() {
        return m_dx;
    }
    
    public int getdy() {
        return m_dy;
    }
    
    public int getFPS() {
        return m_frames_per_second;
    }
    
    public void add(hobject o) {
        m_objects.add(o);
    }
    
    public void remove(hobject o) {
        m_objects.remove(o);
    }
    
    public static int rnd(int max) // return range [0;max]
	{	return (int) (Math.random() * ++max);
	}

    public void setclearobject(int counthare, int countwolf, int countdoe) throws Exception {
    	int rhare=18, rwolf=24, rdoe=20;
    	for(int i=m_objects.size()-1; i>=0; i--)
    	   m_objects.remove(m_objects.get(i));

    	hunter hunter1 = new hunter(28,50 ,28, Color.WHITE,0);
        m_objects.add(hunter1);

    	for (int i = 0; i < counthare; i++) 
        	m_objects.add(new hare( rnd(r_width-2*rhare)+m_dx+rhare, rnd(r_height-2*rhare)+m_dy+rhare, rhare, Color.green, 0) );

    	for (int i = 0; i < countwolf; i++) 
        	m_objects.add(new wolf( rnd(r_width-2*rwolf)+m_dx+rwolf, rnd(r_height-2*rwolf)+m_dy+rwolf, rwolf, Color.gray, 0) );

    	for (int i = 0; i < countdoe; i++) {
            int grcount=3+rnd(7);        
            int x=rnd(r_width-5*rdoe)+m_dx+rdoe;
            int y=rnd(r_height-5*rdoe)+m_dy+rdoe;
    		for (int j = 0; j < grcount; j++)
    		m_objects.add(new doe( x+rnd(4*rdoe), y+rnd(4*rdoe), rdoe, Color.yellow, 0) );
    	}
    }
    public hobject getmindistance(hobject o) {
    	double d=9999;
    	double d2=0;
    	hobject res=null;
    	for(hobject o2:m_objects) 
            if (o2!=o) {
            	d2= PVector.sub(o.position, o2.position).mag();
//           	 System.out.println("d= "+d+"  d2= "+d2);
            	if (d>d2) {
            		d=d2; res=o2;//index = m_objects.indexOf(o2);
            	}
            }
    	
    	  if (o instanceof wolf)
    	  if ((d<o.getRadius()) && (o instanceof wolf) && (!(res instanceof wolf)) )
    	  {
    		  if (!res.delobj)
    			  ((wolf) o).timeoflife=m_frames_per_second*countsectimeoflife;
    		  res.delobj=true;
//        	  if (res instanceof doe) System.out.println("I'm wolf. I eat doe. = "+m_objects.size());
//        	  else  System.out.println("I'm wolf. I eat. =  "+m_objects.size());
    	  } else
    		  o.delobj = ((wolf) o).timeoflife--<0;    		  
    	// System.out.println("o.senseradius= "+o.senseradius+"  d= "+d);
    	 if (!o.delobj)
    			 o.delobj= (o.GetLengthtotheWalls(m_width, m_height, o.position, o.getRadius(), m_dx, m_dy)<-o.getRadius()/2);    	  
    
    	if (d<o.senseradius)
          return res;
    	else return null;
    }

    public void update() {
        double delta_t = 1.0 / m_frames_per_second;        
        for(hobject go:m_objects) { 
		go.update(this, delta_t);     
	 //   System.out.println("------hun_x= "+go.getX()+"  hun_y= "+go.getY()+"  hun_y= "+go.m_x);
        }
	for(int i=m_objects.size()-1; i>=0; i--)
        if (m_objects.get(i).delobj) 
		m_objects.remove(m_objects.get(i));
    }
        
    public void draw(Graphics2D g) {
        for(hobject go:m_objects) go.draw(g);
    }
}
