package pwclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient{
  
  public final static int SERVICE_PORT = 277;
  
  public static void main(String[] args) throws IOException{
    glist game = new glist(900, 600, 25);
		  gwindowUDPclient.newWindow(game);
  }
}
