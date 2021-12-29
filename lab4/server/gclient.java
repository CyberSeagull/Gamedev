import java.net.InetAddress;


public class gclient {
  InetAddress senderAddress;
    boolean rflag1,rflag2;
    boolean sflag1,sflag2;
    public byte[] rBuff1 = new byte[1024]; //receivingDataBuffer
    public byte[] sBuff1 = new byte[1024]; //sendingDataBuffer
    public byte[] rBuff2 = new byte[1024]; //receivingDataBuffer
    public byte[] sBuff2 = new byte[1024]; //sendingDataBuffer

    public gclient(InetAddress senderAddress) throws Exception {
        this.senderAddress = senderAddress;
    }	
    public InetAddress getIP() {
        return senderAddress;
    }

}
