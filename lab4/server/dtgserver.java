import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class dtgserver  extends DatagramSocket{
    byte[] rData = new byte[4096];
    DatagramPacket rp = new DatagramPacket(rData, rData.length);
    byte[] sData = new byte[4096];
    DatagramPacket sp = new DatagramPacket(sData, sData.length);

	public dtgserver(int iport) throws SocketException {
		super(iport);
        this.setSoTimeout(10000);
        sp.setPort(iport);
	}
}

class UDPreceivedata extends Thread
{
    private dtgserver serverSocket;
    private glist m_glist;
    public UDPreceivedata(dtgserver server, glist m_glist) {
        this.serverSocket = server;
        this.m_glist=m_glist;
    }
    
    @Override
    public void run() {
         { 
             while(true) {
            	 try {
                     sleep(1);
                     if (!m_glist.stopstart) {
                         sleep(100);
                     	continue;
                     }
					  try {    	  
						   serverSocket.receive(serverSocket.rp);
						   gclient cl=null;
	
						   String  sip = serverSocket.rp.getAddress().toString();
 						  // System.out.println("receive: IP= "+sip+"  Length= "+serverSocket.rp.getLength())  ;
						  
						   for(gobject go: m_glist.m_clients)  
							   if (go instanceof gclient)
								   if ( ((gclient)go).sip.compareTo(sip)==0) {
									   cl=(gclient) go; 
									   break;
				        	}
						   if (cl==null) { //System.out.println("cl is null")  ;
							   cl = new gclient(serverSocket.rp.getAddress(), m_glist);
							   m_glist.m_clients.add(cl);
						   }
						   
						   m_glist.iobuff.receiveclient(serverSocket.rData, cl, serverSocket.rp.getLength());
						   cl.last_update = System.currentTimeMillis();

						   int lengthdata=m_glist.iobuff.sendclient(serverSocket.sData, m_glist, cl); 
						   serverSocket.sp.setLength(lengthdata);
						   serverSocket.sp.setAddress(cl.senderAddress);
						   serverSocket.sp.setAddress(cl.senderAddress);

						   serverSocket.send(serverSocket.sp);
						   
	                    } catch (Exception ex) {
	                        System.out.println("Socket error: " + ex.getMessage());
	                    } 
						   
                 }catch(Exception e) {
                     e.printStackTrace();
                 }
             }
        }
    }
    }   
