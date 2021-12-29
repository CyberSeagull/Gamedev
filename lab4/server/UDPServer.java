import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer{
  
  public static void main(String[] args) throws IOException{
	  glist game = new glist(500,100, 25);
	  gwindowUDPserver.newWindow(game);		


  }
  
}
