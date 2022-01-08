package pwclient;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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

	public static final byte begindata =10; //  data type = my position
	public static final byte tmy       =1; // data type =  position, radius, color = 4*6 byte
	public static final byte tmykilled =11;//killed data type =  position, radius, color = 4*6 byte 
	public static final byte tgamer  =2; // data type =  position, radius, color = 4*6 byte
	public static final byte tfood   =3; // data type =  position, radius = 4*3 byte
	public static final byte tlist_of_winners   =4; // data type 1 byte length, 16 byte - name 4 byte - weight(radius) = 21 byte 
	public static final byte tmyname =5; // data type =  1 byte length, 16 byte - name
	public static final byte lengthname =16; // data type =  1 byte length, 16 byte - name
    
	public static final int send_no_connection =3; // no connection server
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

	public void setmydata(byte[] rd, glist m_glist, int nb) { 
		player pl =null;
        for(gobject go: m_glist.m_clients)  
        	if (go instanceof player) {
        		pl=(player) go;
        		break;
        	}
        boolean delpl= rd[nb-1]==tmykilled;
		PVector plpos = new PVector(getnextint(rd, nb), getnextint(rd, nb+4)); 
     //   System.out.println("plpos.x= "+plpos.x+" plpos.y= "+plpos.y+"  "+(pl ==null));
		PVector pcenter=new PVector(m_glist.m_width/2, m_glist.m_height/2); 
		int pr=getnextint(rd, nb+8); //radius
        if (pl==null) {
        	pl=new player(pcenter, pr, new Color(getnextint(rd, nb+12), getnextint(rd, nb+16), getnextint(rd, nb+20)));
        	pl.poscl=plpos;
        	m_glist.m_clients.add(pl);
        }
        else {
        	if (PVector.sub(pcenter, plpos).mag()>pl.senseradius)  // changing player's position if it significantly differs from the server's data
        		pl.poscl=plpos;
        	pl.m_r=pr;
        }
    	pl.m_r=pr;
    	pl.m_color=new Color(getnextint(rd, nb+12), getnextint(rd, nb+16), getnextint(rd, nb+20));
    	pl.delobj=delpl;
//		System.out.println(" ===============  cl.c_radius+=1= "+pl.m_r);    	    					

	}
	
	public void setgamerdata(byte[] rd, List<gobject> m_clients, int nb) { // setting other players' data
		PVector gpos = new PVector(getnextint(rd, nb), getnextint(rd, nb+4)); // gamer position 
		int gr = getnextint(rd, nb+8); //radius
        Color gc = new Color(getnextint(rd, nb+12), getnextint(rd, nb+16), getnextint(rd, nb+20)); 
        m_clients.add(new gamer(gpos, gr, gc));
	}

	public void setfooddata(byte[] rd, List<gobject> m_clients, int nb) { // setting other players' data
		PVector fpos = new PVector(getnextint(rd, nb), getnextint(rd, nb+4)); // food position 
		int fr = getnextint(rd, nb+8); //radius
	//	System.out.println("  food x= "+ fpos.x+ " y= "+fpos.y+"  r="+fr);

        m_clients.add(new food(fpos, fr));
	}

	public int getmydatatobuffer(byte[] rd, List<gobject> m_clients, int nb) { // sending client's data
		player pl =null;
        for(gobject go:m_clients)  
        	if (go instanceof player) {
        		pl=(player) go;
        		rd[nb]=tmy;
        		nb+=1;
        		nb=setnextint(rd, nb, (int) pl.poscl.x);
        		nb=setnextint(rd, nb, (int) pl.poscl.y);
        		nb=setnextint(rd, nb, (int) pl.m_r);
        		nb=setnextint(rd, nb, (int) pl.m_color.getRed());
        		nb=setnextint(rd, nb, (int) pl.m_color.getGreen());
        		nb=setnextint(rd, nb, (int) pl.m_color.getBlue());
        		break;
        	}
        return nb;
	}
	
	public int getmynametoobuffer(byte[] rd,  glist m_glist, int nb) {
		rd[nb]  =tmyname;
		int lname = (m_glist.playername.length()>=lengthname) ? lengthname : m_glist.playername.length();
		rd[nb+1]=(byte) lname;
		nb+=2;
		System.arraycopy(m_glist.playername.getBytes(), 0, rd, nb, lname);
		nb+=lengthname;
		return nb;
	}
	
	public void printbuff(String comment, byte[] rd, int l) {
		/*
		System.out.print(comment+" " + l+"  ");
	    for (int i=0; i<l; i++)
	    	System.out.print(String.format("%02x ", rd[i]));
	    System.out.println(" ");
	    */
}

	public int setlistdata(byte[] rd, List<infoplayers> m_info, int nb) { //setting other players' data
        m_info.add(new infoplayers(getstrgwithbytes(rd, nb, tmyname), getnextint(rd, nb+lengthname+1)));
        return nb+lengthname+1+4;
	}
		
	//SEND data	
	public int outbject(byte[] rd, glist m_glist) {//send data to server
		outcontrolbegin(rd);
		int nb=10;
		nb=getmydatatobuffer(rd, m_glist.m_clients, nb);
		nb=getmynametoobuffer(rd, m_glist, nb);
		setnextint(rd, 4, nb);
		return nb;
	}

	//receive data	
	public boolean inobject(byte[] rd, glist m_glist,  int l) { //l=length DatagramPacket buffer
		
		if(l<begindata) return false;
		stategame =rd[8];
		String smypacket = getstrgwithbytes(rd, 0, 0);
	    if (!(agar.compareTo(smypacket)==0)) return false;
	    int ld=getnextint(rd, 4);
	    if (ld>l) return false;
	    int nb=begindata;
	    byte tdata;
	    
	    while (nb<ld) {
	    	tdata=rd[nb];
	    	nb+=1;
	    	if (tdata==tmykilled) tdata=tmy;
	    	switch (tdata) {
			case tmy: 
				if ((nb+24)>l) return false; 
				setmydata(rd, m_glist, nb);
				nb+=24;
 				break;
			case tgamer: 
				if ((nb+24)>l) return false;
			//	System.out.print("=======================================================================");
				setgamerdata(rd, m_glist.m_clients, nb);
				nb+=24;
				break;
			case tfood:
				if ((nb+12)>l) return false;
				setfooddata(rd, m_glist.m_clients, nb);
				
				nb+=12;
				break;
			case tlist_of_winners:
				if ((nb+1+lengthname+4)>l) return false;
				nb=setlistdata(rd, m_glist.m_info, nb);
				break;
			default: return false;
			} 
	    }
	    return true;
	}
	
}
