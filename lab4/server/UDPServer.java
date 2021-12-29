import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer{
  
  public final static int SERVICE_PORT=277;//50001;
 
   public static void main(String[] args) throws IOException{
	  glist game = new glist(800,600, 25);
	  gwindowUDPserver.newWindow(game);		
    	

  }

  
}
