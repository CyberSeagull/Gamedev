import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class inoutbuff {
    // byte 0-3 = agar  
    //  byte[4-7] = data length in bytes 
    //  byte[8] = 0 - game 1-info list_of_winners    , byte[9] = not used -- 0 newly created; 1- existing; 2 -list of winners 
	//  structure of the next byte 
	// data type 1 my position - Position.x, 2-Position.y, 3 - radius (12 byte)
	//             my color - r, g, b (12 byte)
	// data type 2 other players - Position.x, 2-Position.y, 3 - radius (12 byte)  color - r, g, b (12 byte)
	// data type 3 food - Position.x, 2-Position.y, 3 - length (12 byte)
	// data type 4 16 byte = name + int (4 byte) 

	public static final byte begindata =10; // data type  = my position
	public static final byte tmy     =1; // data type =  position, radius, color = 4*6 byte current player
	public static final byte tmykilled=11;//killed data type =  position, radius, color = 4*6 byte 
	public static final byte tgamer  =2; // data type =  position, radius, color = 4*6 byte other player
	public static final byte tfood   =3; // data type =  position, radius = 4*3 byte
	public static final byte tlist_of_winners   =4; // data type 1 byte length, 16 byte - name 4 byte - weight(radius) = 21 byte 
	public static final byte tmyname =5; // data type =  1 byte length, 16 byte - name
	public static final byte lengthname =16; // data type =  1 byte length, 16 byte - name
    
	public static final int send_no_connection =3; // no connection to server
	public static final int send_new_game =2; // begin new game
	public static final int send_info =1; // list_of_winners
	public static final int send_game =0; // information about players and food 
    int stategame = send_game;//	
	
    static String agar ="agar"; // controlling whether it's our client or smth else
    byte[] b4 = new byte[4];
    byte[] b16= new byte[lengthname];

	public int setnextint(byte [] rd, int nb, int value) {
	       rd[nb+0]= (byte)(value >>> 24);
	       rd[nb+1]= (byte)(value >>> 16);
	       rd[nb+2]= (byte)(value >>> 8);
	       rd[nb+3]= (byte)value;
	       return nb+4;
 };
	public int getnextint(byte[] rd, int nb) {
		System.arraycopy(rd, nb, b4, 0, 4);
		return ByteBuffer.wrap(b4).getInt();
	}

	public void outcontrolbegin(byte [] rd) {
		System.arraycopy(agar.getBytes(), 0, rd, 0, 4);
	}

	public String getstrgwithbytes(byte [] rd, int nb, int t) { // t =0 - agar, tmyname=5 назва
		String str="";
		if (t==tmyname) {
			System.arraycopy(rd, nb+1, b16, 0, lengthname);
		    str=new String(b16);
			str=str.substring(0,rd[nb]);
		}
		if (t==0) {
			System.arraycopy(rd, nb, b4, 0, 4);
		    str=new String(b4);
		}
		return str;
	}
	
//------------------------
	public void setmydata(byte[] rd, gclient cl, int nb) { 
		cl.position.x = getnextint(rd, nb); 
		cl.position.y = getnextint(rd, nb+4); 
		cl.setmygrid();
	}

	public boolean receiveclient(byte[] rd, gclient cl, int l) {
		if(l<begindata) return false;
		String smypacket = getstrgwithbytes(rd, 0, 0);
		if (!(agar.compareTo(smypacket)==0)) return false;

		int ld=getnextint(rd, 4);
	 	
	 	if (ld>l) return false;
	    int nb=begindata;
	    byte tdata;
	    while (nb<ld) {
	    	tdata=rd[nb];
	    	nb+=1;
	    	switch (tdata) {
			case tmy: 
				if ((nb+24)>l) return false; 
				setmydata(rd, cl, nb);
				nb+=24;

				break;
			case tmyname: 
				if ((nb+1+lengthname)>l) return false;
				cl.name=  getstrgwithbytes(rd, nb, tmyname);
				nb+=1+lengthname;
 				break;
			default: return false;
			} 
	    }
	    return true;
	}
	public int getmyinfoplayerstobuffer(byte[] rd,  infoplayers info, int nb) {
		rd[nb]  =tlist_of_winners;
		int lname = (info.name.length()>=lengthname) ? lengthname : info.name.length();
		rd[nb+1]=(byte) lname;
		nb+=2;
		System.arraycopy(info.name.getBytes(), 0, rd, nb, lname);
		nb+=lengthname;
		nb=setnextint(rd, nb, info.weight);
		return nb;
	}
	
	public int sendclient(byte[] rd, glist m_glist, gclient cl) {
		outcontrolbegin(rd);
		int nb=begindata;
		//System.out.println("name is "+cl.name+" stategame= "+stategame);
		rd[8]=(byte) stategame;
		if ( !(stategame == send_info)) {
		rd[nb]=cl.delobj ? tmykilled: tmy;
		nb++;
		nb=setnextint(rd, nb, (int) cl.position.x);
		nb=setnextint(rd, nb, (int) cl.position.y);
		nb=setnextint(rd, nb, (int) cl.c_radius);
		nb=setnextint(rd, nb, (int) cl.c_r);
		nb=setnextint(rd, nb, (int) cl.c_g);
		nb=setnextint(rd, nb, (int) cl.c_b);
//		System.out.println("name is "+cl.name);
//		System.out.println("  player grid x= "+cl.pgrid.x+"  y= "+cl.pgrid.y+" x= "+cl.position.x+" y= "+cl.position.y);

		for(gobject go: m_glist.m_clients)
    		if ( (go!=cl) && (!go.delobj)) 
				if ( (Math.abs(cl.pgrid.x-go.pgrid.x)<=1) &&  (Math.abs(cl.pgrid.y-go.pgrid.y)<=1)  ) {
//					System.out.println("  food   grid x= "+go.pgrid.x+"  y= "+go.pgrid.y+" x= "+go.position.x+" y= "+go.position.y);

					if (go instanceof gclient) {
						rd[nb]=tgamer;
						nb++;
						nb=setnextint(rd, nb, (int) go.position.x);
						nb=setnextint(rd, nb, (int) go.position.y);
						nb=setnextint(rd, nb, (int) go.c_radius);
						nb=setnextint(rd, nb, (int) go.c_r);
						nb=setnextint(rd, nb, (int) go.c_g);
						nb=setnextint(rd, nb, (int) go.c_b);
					}
					if (go instanceof gfood) {
						rd[nb]=tfood;
						nb++;
						nb=setnextint(rd, nb, (int) go.position.x);
						nb=setnextint(rd, nb, (int) go.position.y);
						nb=setnextint(rd, nb, (int) go.c_radius);
					}

    	}
		} 
		else {//tlist_of_winners
			for(infoplayers go: m_glist.m_info) {
				nb=getmyinfoplayerstobuffer(rd, go, nb);
			}
		}
		setnextint(rd, 4, nb);
		return nb;
	}
	
}