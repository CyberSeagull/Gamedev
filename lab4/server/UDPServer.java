import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer{
  
  public final static int SERVICE_PORT=277;//50001;
 
  public static void main(String[] args) throws IOException{
    try{
    	gwindowUDPserver p = new gwindowUDPserver();
    	
    	

      
      
      DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
      System.out.println("Waiting for a client to connect...");
   
boolean t=true;
      while(t)
      {
      
      
      serverSocket.receive(inputPacket);
      
      
      String receivedData = new String(inputPacket.getData());
      System.out.println("Sent from the client: "+receivedData);
      
      
      sendingDataBuffer = receivedData.toUpperCase().getBytes();
      
      
      InetAddress senderAddress = inputPacket.getAddress();
      int senderPort = inputPacket.getPort();
      System.out.println("senderPort: "+senderPort+"  IP: "+senderAddress.toString());
      
      
      DatagramPacket outputPacket = new DatagramPacket(
        sendingDataBuffer, sendingDataBuffer.length,
        senderAddress,senderPort
      );
      
      
      serverSocket.send(outputPacket);

      
      }
      
     
      serverSocket.close();
    }
    catch (SocketException e){
      e.printStackTrace();
    }
  }

  
}