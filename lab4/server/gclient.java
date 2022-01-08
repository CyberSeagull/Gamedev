import java.net.InetAddress;


public class gclient extends gobject {
	InetAddress senderAddress;
    public String sip;
    long last_update = System.currentTimeMillis();
  	int status;
  	String name ="";
  	glist m_glist;

    public gclient(InetAddress senderAddress, glist m_glist) throws Exception {
    	int w=m_glist.m_width;
    	int h=m_glist.m_height;
        this.senderAddress = senderAddress;
        this.sip=senderAddress.toString();
        this.m_glist=m_glist;
        name=sip;
        c_radius= m_glist.radius;
        position= new PVector( glist.rnd(w/2)+w/4, glist.rnd(h/2)+h/4); //random center+-w/4
		pgrid = m_glist.getmygrid(position);
        status=0;
        last_update = System.currentTimeMillis();
    }	

    public String getIP() {
        return senderAddress.toString();
    }
    public void setmygrid() {
    	pgrid = m_glist.getmygrid(position);
	    
    }
}
