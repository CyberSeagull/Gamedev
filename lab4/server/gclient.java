import java.net.InetAddress;


public class gclient {
  InetAddress senderAddress;
    public String sip;

    byte[] sData = new byte[2048];
    DatagramPacket sp;
    
    PVector position;
    int c_radius=20;
  	int c_r=(int) glist.rnd(200)+20;
  	int c_g=(int) glist.rnd(200)+20;
  	int c_b=(int) glist.rnd(200)+20;
  	int status;
    byte[] b4= new byte[4];

    public gclient(InetAddress senderAddress, int w, int h, int Port) throws Exception {
        this.senderAddress = senderAddress;
        this.sip=senderAddress.toString();
        position.x = glist.rnd(w-100)+50;
        position.y = glist.rnd(h-100)+50;
        status=0;
        sp = new DatagramPacket(sData, sData.length, senderAddress, Port);
    }	

    public int getnextint(byte [] rd, int nextbyte) {
		System.arraycopy(rd, 0, b4, nextbyte, 4);
		return ByteBuffer.wrap(b4).getInt();
    };

    
    public String getIP() {
        return senderAddress.toString();
    }
	//  byte[0] = the amount of data starting from the third byte, byte[1] = 0 newly created; 1- existing 
	//  structure of the next byte 
	// data type 1 main position - Position.x, 2-Position.y, 3 - radius (12 byte)
	// data type 2 main color - r, g, b (12 byte)
	// data type 3 other players - Position.x, 2-Position.y, 3 - radius (12 byte)
	// data type 4 food - Position.x, 2-Position.y, 3 - length (12 byte)
    public void setData(byte [] rd, int l) {
    	if (l<2) return; //newly created
    //	int n=rd[0]; //the amount of data starting from the third byte
    	if (rd[1]==0) return; //newly created ..it is necessary to transfer back his position
    	int nextbyte=3;
    	if (rd[2]==1) {
    		position.x=getnextint(rd, nextbyte); //ByteBuffer.wrap(b4).getInt();
    		nextbyte+=4;
    		position.y=getnextint(rd, nextbyte); //ByteBuffer.wrap(b4).getInt();
    		nextbyte+=4;
    		c_radius=getnextint(rd, nextbyte); //ByteBuffer.wrap(b4).getInt();
    	}
    };

}
