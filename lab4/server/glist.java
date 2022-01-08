import java.awt.Color;
import java.awt.Point;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;




class Sortbyweight implements Comparator<infoplayers> { 
    public int compare(infoplayers a, infoplayers b) { 
    	return  a.weight-b.weight; 
    }
} 

class infoplayers {
	String name;
	int weight;
	infoplayers(String ip, int radius) {
		name=ip;
		weight=radius;
	}
}

class dtgpacketsend {
	boolean semafor=false; 
    byte[] sData = new byte[2048];
    DatagramPacket sp;
    dtgpacketsend() {
	    sp = new DatagramPacket(sData, sData.length);
	}
}

public class glist {
    List<gobject> m_clients = new ArrayList<>();
    List<infoplayers> m_info = new ArrayList<>();
    List<dtgpacketsend> p_send = new ArrayList<>();
    int m_port=278;
 
    inoutbuff iobuff = new inoutbuff();
    long timeround =120000l; // round = 2 minute
    long breakbetweenrounds = 12000l; // 20 sek
    
    int radius=22;
    int radiusfood=9;
  
    public   dtgserver servert=null;

	public boolean stopstart=false;
    int m_width=25000, m_height=25000;    
    int resolution = 500;
    int s_width, s_height;    
    int cols;// = m_width/resolution;
    int rows;// = m_height/resolution;
    PVector[][] fieldgrid;

    int m_frames_per_second;

    public glist(int width, int height, int frames_per_second) {
        s_width = width;
        s_height = height;
        m_frames_per_second = frames_per_second;
     //   gamegrid();
    }

    public int getFPS() {
        return m_frames_per_second;
    }
    
    public int getWidth() {
        return s_width;
    }
    
    public int getHeight() {
        return s_height;
    }
    public void setPort(int Port) {
        m_port=Port;
    }
    
    public static int rnd(int max) // return range [0;max]
	{	return (int) (Math.random() * ++max);
	}
    
    public void startnewround(int deleteclient) {
    	//System.out.println(" startnewround="+deleteclient);
    	gclient cl;
    	// information about players 
    	if (deleteclient==inoutbuff.send_info) {
    		for(int i=m_info.size()-1; i>=0; i--)
    			m_info.remove(m_info.get(i));
    		for(gobject go: m_clients)  
    			if (go instanceof gclient) {
    				cl=(gclient) go;
    				String name=cl.delobj ? "Killed "+cl.name: cl.name;
    				m_info.add( new infoplayers(name, cl.c_radius) );
    			}  
    		Collections.sort(m_info, new Sortbyweight());
    		return;
    	}
    	//reset players, remove food 
    	for(int i=m_clients.size()-1; i>=0; i--) {
			gobject go = m_clients.get(i);
    		if ( (go instanceof gclient) && (deleteclient==inoutbuff.send_game) ) {
    			go.c_radius=radius;
    			go.delobj=false;
    		}
    		else
    			m_clients.remove(go);
    	}
    	
    	//scatter food
    	int countfood=rnd(m_width*m_height/70000)+m_width*m_height/1000000;    	
    	//System.out.println(" countfood="+countfood);
    	for (int i = 0; i < countfood; i++) {
    		m_clients.add(new gfood( rnd(m_width-200)+100, rnd(m_height-200)+100, radiusfood, this));
    	}
    }

    
    public Point getmygrid(PVector position)  {
        return new Point( (int) (position.x/resolution), (int) (position.y/resolution));
    }
    
    public void updatestate(gclient cl) {
    //	System.out.println(cl.delobj+"   mls= "+cl.timelife+" "+System.currentTimeMillis()+"  "+cl.last_update+"  "+(System.currentTimeMillis()-cl.last_update));    	
		if (cl.delobj) return;
		double dist=0;
    	for(gobject go: m_clients) 
    		if ((go !=cl) && (!go.delobj))  {
    			dist=PVector.sub(cl.position, go.position).mag();
    			if ( dist<(cl.c_radius/2+go.c_radius/2) )
    				if (go instanceof gfood) {
    					go.delobj=true;
    					cl.c_radius+=1;
    				} 
    				else { // determine the winner of the collision 
    					int newradius=(int) Math.sqrt(Math.pow(cl.c_radius,2)+Math.pow(go.c_radius,2));
    					if	(cl.c_radius>go.c_radius) {
    						cl.c_radius=newradius;
    						go.delobj=true;
    					} 
    					else {
    						go.c_radius=newradius;
    						cl.delobj=true;
    					}
    				}
    		} 
    }

    public void update() {
//    	cl.delobj= (System.currentTimeMillis()-cl.last_update >  cl.timelife);
    	for(int i=m_clients.size()-1; i>=0; i--) {
			gobject cl = m_clients.get(i);
			if (cl instanceof gclient) {
				if (System.currentTimeMillis()- ((gclient) cl).last_update > cl.timelife) // давно не приходять дані	
	    			m_clients.remove(cl);
				else
	    			if (!cl.delobj) 
	    				updatestate((gclient) cl);
			}
    	}

			/*    	for(gobject go: m_clients) 
    		if (go instanceof gclient) 
    			if (!go.delobj) 
    				updatestate((gclient) go);*/
    }

    
}
