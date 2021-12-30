import java.util.ArrayList;
import java.util.List;

public class glist {
    List<gclient> m_clients = new ArrayList<>();
    int m_port=278;
	public   dtgserver servert;

	public boolean stopstart=false;
    int m_width, m_height;    
    int m_frames_per_second;
    public glist(int width, int height, int frames_per_second) {
        m_width = width;
        m_height = height;
        m_frames_per_second = frames_per_second;
    }

    public int getFPS() {
        return m_frames_per_second;
    }
    
    public int getWidth() {
        return m_width;
    }
    
    public int getHeight() {
        return m_height;
    }
    public void setPort(int Port) {
        m_port=Port;
    }
    
    public static int rnd(int max) // return range [0;max]
	{	return (int) (Math.random() * ++max);
	}
    
    public void setclearobject(int countfood) throws Exception {
    	for(int i=m_clients.size()-1; i>=0; i--)
    		m_clients.remove(m_clients.get(i));
    		    
//    	for (int i = 0; i < countfood; i++) 
//    		m_clients.add(new food( rnd(r_width-2*rhare)+m_dx+rhare, rnd(r_height-2*rhare)+m_dy+rhare, rhare, Color.green, 0) );
    }
    public void setnextint(byte [] rd, int nb, int value) {
	       rd[0]= (byte)(value >>> 24);
	       rd[1]= (byte)(value >>> 16);
	       rd[2]= (byte)(value >>> 8);
	       rd[3]= (byte)value;
    };
    	
    public void getdataforplayer(gclient cl) {
    	//  byte[0] = the amount of data starting from the third byte, byte[1] = 0 newly created; 1- existing 
    	//  structure of the next byte 
    	// data type 1 main position - Position.x, 2-Position.y, 3 - radius (12 byte)
    	// data type 2 main color - r, g, b (12 byte)
    	// data type 3 other players - Position.x, 2-Position.y, 3 - radius (12 byte)
    	// data type 4 food - Position.x, 2-Position.y, 3 - length (12 byte)
    	};
   

    	public void setallposition() {
    		
    	}
    
    
}
