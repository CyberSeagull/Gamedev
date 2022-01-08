package pwclient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

class infoplayers {
	String name;
	int weight;
	infoplayers(String ip, int radius) {
		name=ip;
		weight=radius;
	}
}

class dtgpacketread {
	// reading packets semafor=false - packet can receive new data (dtgclient thread receives data and writes true)
	// sending packets semafor=true  - a packet has new data. read them and write false false  
	boolean semafor=false; 
    byte[]  sData  = new byte[4096];
    DatagramPacket rp;
    dtgpacketread() throws UnknownHostException {
	    rp = new DatagramPacket(sData, sData.length);
	    //  DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,IPAddress, SERVICE_PORT);
	}
}

public class glist {
    List<gobject> m_clients = new ArrayList<>();
    List<dtgpacketread> p_read = new ArrayList<>();
    List<infoplayers> m_info = new ArrayList<>();
    DefaultListModel<String> infowinners = new DefaultListModel<String>(); 
    
    dtgclient clientudp=null;
    boolean stopstart=false;
    
    String playername="№";
  
    int m_port=278;
    String m_ipserver=""; 
    inoutbuff iobuff = new inoutbuff();
    UDPsenddata clientsend;// = new UDPsenddata(clientudp, this); 
    
    int m_width, m_height;    
    int m_dx=10, m_dy=30;    
    int m_frames_per_second;
    public glist(int width, int height, int frames_per_second) {
        m_width = width;
        m_height = height;
        m_frames_per_second = frames_per_second;
    }
    
    public void setPort(int Port) {
        m_port=Port;
    }
    public void setipserver(String ip) {
    	m_ipserver=ip;
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
    
    public String getstrstategame() {
    	
        switch (iobuff.stategame) {
		case inoutbuff.send_info:
			return "List of winners.";
		case inoutbuff.send_no_connection:
			return "No connection to the server";
 		default:
			return " ";
		}
    }

    public void setclearobject()  {
    	for(int i=m_clients.size()-1; i>=0; i--)
    		if ( !(m_clients.get(i) instanceof player))
    		m_clients.remove(m_clients.get(i));
         
    	if (m_info.size()>0)
        	for(int i=m_info.size()-1; i>=0; i--)
        		m_info.remove(m_info.get(i));
    }
    
    public player getplayer(){
    	player pl=null;
        for(gobject go: m_clients)  
        	if (go instanceof player) {
        		pl=(player) go;
        		break;
        		}
    	return pl;
    }
    
    public void update() {
    	for(int i=p_read.size()-1; i>=0; i--){
    		dtgpacketread dtgp=p_read.get(i);
        	if (dtgp.semafor) {
        		setclearobject();
        		iobuff.inobject(dtgp.rp.getData(), this, dtgp.rp.getLength());
        		dtgp.semafor=false;
        		
        		if (iobuff.stategame== inoutbuff.send_info) {
        			if (infowinners.size()==0)
    		        for(infoplayers gow: m_info){ 
        	        	infowinners.add(0, String.format("   %-40s  ...............  ", gow.name)+gow.weight);
        	        }
        		}
        		else {
        			if (infowinners.size()>0)
        			for(int j=0; j<infowinners.size(); j++)
        				infowinners.remove(j);
        		}
        	}
    	}
    	player pl=getplayer();
    	if (pl==null) {
            int lengthbuffer=iobuff.outbject(clientsend.clientSocket.rData, this);
    		clientsend.udpsend(lengthbuffer);
    		return;
    	}
		
        PVector pcenter=new PVector(m_width/2, m_height/2);
        PVector posdelta=PVector.sub(pl.position, pcenter);
        
        pl.poscl.add(posdelta);
        PVector pdelta=PVector.sub(pl.poscl, pl.position);
        pl.position.x=m_width/2;
        pl.position.y=m_height/2;
        pl.m_x=m_width/2;
        pl.m_y=m_height/2;

        double delta_t = 1.0 / m_frames_per_second; 
    	pl.update(this, delta_t);
    	//*******************************
        int lengthbuffer=iobuff.outbject(clientsend.clientSocket.rData, this);
		clientsend.udpsend(lengthbuffer);

//        System.out.println("poscl.x= "+pl.poscl.x+" poscl.y= "+pl.poscl.y+" pcenter= "+pl.position.x+"  "+pl.position.y);

        for(gobject go: m_clients) {
        	if (!(go instanceof player)) {  
        	go.position=PVector.sub(go.poscl, pdelta);
        //	System.out.println("glist food m_x= "+go.position.x+"  m_y= "+go.position.y+" m_r= "+go.m_r+"  "+(go instanceof player));
        	go.update(this, delta_t);
        	}
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
