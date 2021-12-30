package pwclient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class glist {
    List<gobject> m_clients = new ArrayList<>();
        public boolean stopstart=false;
        int m_width, m_height;
        int m_dx=10, m_dy=30;
        int m_frames_per_second;
    public glist(int width, int height, int frames_per_second) {
            m_width = width;
        m_height = height;
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
    
    
    public void setclearobject(int countfood) throws Exception {
    	for(int i=m_clients.size()-1; i>=0; i--)
    		m_clients.remove(m_clients.get(i));
    	player pl = new player (28,50 ,28, Color.WHITE,0);
    	m_clients.add(pl);

//    	for (int i = 0; i < countfood; i++) 
//    		m_clients.add(new food( rnd(r_width-2*rhare)+m_dx+rhare, rnd(r_height-2*rhare)+m_dy+rhare, rhare, Color.green, 0) );
    }
    
    public void update() {
        double delta_t = 1.0 / m_frames_per_second; 
        for(gobject go:m_clients) { 
        	go.update(this, delta_t);
        	
       	 //   System.out.println("------hun_x= "+go.getX()+"  hun_y= "+go.getY()+"  hun_y= "+go.m_x);
        }
        	for(int i=m_clients.size()-1; i>=0; i--)
        	if (m_clients.get(i).delobj) {
        		m_clients.remove(m_clients.get(i));
        	}

        
    }
        
    public void draw(Graphics2D g) {
        for(gobject go:m_clients) go.draw(g);
    }


}
