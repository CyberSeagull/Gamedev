package pwclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class dtgclient extends DatagramSocket{
	 byte[] rData = new byte[4096];
    DatagramPacket sp;// new DatagramPacket(rData, rData.length); // send packet

	public dtgclient(int port, String ip) throws SocketException, UnknownHostException {
//		super();
		super(port);
		sp = new DatagramPacket(rData, rData.length, InetAddress.getByName(ip), port); 
        this.setSoTimeout(1000);
	}
}

class UDPsenddata {
	public dtgclient clientSocket;
    private glist m_glist;
    public UDPsenddata(dtgclient client, glist m_glist) {
        this.clientSocket = client;
        this.m_glist=m_glist;
    }
    
    public void udpsend(int lengthb) {
    	try {    	  
    		
    		m_glist.iobuff.printbuff(" Send ", clientSocket.rData, lengthb);
    		
    		clientSocket.sp.setLength(lengthb);
//			System.out.println("Send UDPreceivedata from the client: ");
    		clientSocket.send(clientSocket.sp);
//			System.out.println("Send UDPreceivedata from the client: ");
    	} catch (Exception ex) {
    		System.out.println("Socket client error send: " + ex.getMessage());
    	} 
    }
}   

class UDPreceivedata extends Thread
{
    dtgclient clientSocket;
    private glist m_glist;
    public UDPreceivedata(dtgclient client, glist m_glist) {
        this.clientSocket = client;
        this.m_glist=m_glist;
    }
    
    @Override
    public void run() {
        //System.out.println("Start UDPreceivedata from the client: ");
    	while(true) {
    		try {
    			sleep(1);
    			if (!m_glist.stopstart) {
    				sleep(100);
    				continue;
    			}
    			//					  System.out.println("Waiting server data...");
    			try {    	  
    				dtgpacketread dtgrp=null;
    				for(dtgpacketread go: m_glist.p_read) 
    					if (!go.semafor) {
    						dtgrp=go; break;
    					}
    				if (dtgrp==null) {
    					System.out.println("dtgrp is null")  ;
    					dtgrp = new dtgpacketread();//(" ipserver ", clientSocket.getPort());/////////?????????????
    					m_glist.p_read.add(dtgrp);
    				}
    				clientSocket.receive(dtgrp.rp);

    				m_glist.iobuff.printbuff(" Rece ", dtgrp.rp.getData(), dtgrp.rp.getLength());
    			
    				
    				
    				dtgrp.semafor=true;
    				//System.out.println("Socket client error.rp...");
    			} catch (Exception ex) {
    				m_glist.iobuff.stategame= inoutbuff.send_no_connection;
    				System.out.println("Socket client error: " + ex.getMessage());
    			} 
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
}   

