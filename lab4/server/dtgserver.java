import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class dtgserver  extends DatagramSocket{

		byte[] rData = new byte[1024];
    DatagramPacket rp = new DatagramPacket(rData, rData.length);

	public dtgserver(int port) throws SocketException {
		super(port);
        this.setSoTimeout(10000);

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
        	 //System.out.println("Start UDPreceivedata from the client: ");
             while(true) {
            	 try {
                     sleep(1);
                     if (!m_glist.stopstart) {
                         sleep(100);
                     	continue;
                     }
					  //System.out.println("Waiting for a client to connect...");
					  try {    	  
						   serverSocket.receive(serverSocket.rp);
						   gclient cl=null;
						   String  sip = serverSocket.rp.getAddress().toString();
						  
						   for(gclient go: m_glist.m_clients)  
							   if (go.sip.compareTo(sip)==0) {
				        		cl=go; break;
				        	}
						   if (cl==null) {
							   cl = new gclient(serverSocket.rp.getAddress(), m_glist.m_width, m_glist.m_height, serverSocket.rp.getPort());
							   m_glist.m_clients.add(cl);
						   }
						   cl.setData(serverSocket.rp.getData(), serverSocket.rp.getLength());
						   
						   m_glist.getdataforplayer(cl); 
						   serverSocket.send(cl.sp);
						   
	                    } catch (Exception ex) {
	                       // System.out.println("Socket error: " + ex.getMessage());
	                    } 
						   
                 }catch(Exception e) {
                     e.printStackTrace();
                 }
             }
        }
    }
}   
