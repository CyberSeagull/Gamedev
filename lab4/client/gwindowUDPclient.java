package pwclient;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

	
public class gwindowUDPclient extends JComponent {
	private static final long serialVersionUID = 1L;
	glist m_glist;
    
    private gwindowUDPclient(glist lclients) {        
        m_glist = lclients;
        setPreferredSize(new Dimension((int)m_glist.getWidth(),(int)m_glist.getHeight()));
    }
    
    public static void newWindow(glist clist) {
    	int yp=15;
    	int xp=5;
        JFrame frame = new JFrame("LAB-4   UDP CLIENT.");
        gwindowUDPclient c = new gwindowUDPclient(clist);

        JLabel lresult = new JLabel("777 ");   
        lresult.setBounds(xp+444,yp,55,22);
//        frame.add(lresult); 
        
        yp=5;

        JButton b=new JButton("START GAME");
        b.setBounds(xp,yp,110,25);    
        frame.add(b); 

        JLabel lport = new JLabel("¹ PORT: ");   
        lport.setBounds(xp+130,yp,70,22);
        frame.add(lport); 
        JSpinner spport = new JSpinner(new SpinnerNumberModel(278, 1, 65535, 1));   
        spport.setBounds(xp+200,yp,59,22);
        frame.add(spport); 
        
        JLabel lip = new JLabel("IP server: ");   
        lip.setBounds(xp+270,yp,70,22);
        frame.add(lip); 
        JTextField tfip = new JTextField("127.0.0.1", 5);
        tfip.setBounds(xp+335,yp,100,25);    
        frame.add(tfip); 

        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(c, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();

        b.addActionListener(new ActionListener() { //button click
    		
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	      if (clist.stopstart) b.setText("START GAME" );
    	      else                 b.setText("STOP  GAME" );
              clist.stopstart=!clist.stopstart;
              System.out.println(clist.stopstart+"  ...");
    
    	    }
    	});	
        stopstartgame(clist,frame,lresult);

    }

    	
   static void stopstartgame(glist m_glist, JFrame frame, JLabel lresult) {
    long next_update = System.currentTimeMillis();
    int interval = 1;
    while(true) {
        try {
            long current_time = System.currentTimeMillis();

          //  System.out.println(m_glist.stopstart+"  ssssssssssssssst...");
            Thread.sleep(1);
 
            if (!m_glist.stopstart) {
        //    	System.out.println(m_glist.stopstart+"  CONTINUE...");
            	continue;
            }
    //        if (current_time>=next_update) 
//                synchronized (glist.class)
          System.out.println(m_glist.stopstart+"  1  Waiting for a client to connect...");
                        
            {
            	//m_glist.update();
            	lresult.setText(" "+m_glist.m_clients.size());
            //	if (m_glist.m_clients.size()==1)
                frame.repaint();
                next_update += interval;
                if (current_time>=next_update + interval*10) next_update = current_time + interval;
                
                System.out.println("Waiting for a client to connect...");
               System.out.println(m_glist.stopstart+" 2  Waiting for a client to connect...");
                
                
          	  try{

          	      InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

          		dtgclient clientSocket = new dtgclient(278, IPAddress);
          		clientSocket.setSoTimeout(500);
                byte[] receivingDataBuffer = new byte[1024];
                byte[] sendingDataBuffer = new byte[1024];
                DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
          		  
                                
                System.out.println("Waiting for a client to connect...");
              	  try {    	  
              		clientSocket.receive(inputPacket);
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
                clientSocket.send(outputPacket);
                    } catch (Exception ex) {
                        System.out.println("Socket error: " + ex.getMessage());
                    } 
              	  
              	clientSocket.close();
              }
              catch (SocketException e){
                e.printStackTrace();
              }                
                                
            }
            Thread.sleep(1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    }   
    
    
}
