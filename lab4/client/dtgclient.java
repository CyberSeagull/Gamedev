package pwclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class dtgclient extends DatagramSocket{
	InetAddress IPaddress;
	public dtgclient(int port, InetAddress IPaddress) throws SocketException, UnknownHostException {
		super(port);
       this.IPaddress = IPaddress;
	}

}
