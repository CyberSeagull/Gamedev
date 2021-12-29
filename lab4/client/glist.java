package pwclient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class glist {
    List<gobject> m_clients = new ArrayList<>();
    public boolean stopstart=false;
    int m_width, m_height;    
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
    
    public void setclearobject(int countfood) throws Exception {
    	for(int i=m_clients.size()-1; i>=0; i--)
    		m_clients.remove(m_clients.get(i));

//    	for (int i = 0; i < countfood; i++) 
//    		m_clients.add(new food( rnd(r_width-2*rhare)+m_dx+rhare, rnd(r_height-2*rhare)+m_dy+rhare, rhare, Color.green, 0) );
    }
    


}
