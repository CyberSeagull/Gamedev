import java.awt.BorderLayout;
import java.awt.Color;
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


public class gwindowUDPserver extends JComponent {
	private static final long serialVersionUID = 1L;
	glist m_glist;
    
    private gwindowUDPserver(glist lclients) {        
        m_glist = lclients;
        setPreferredSize(new Dimension((int)m_glist.getWidth(),(int)m_glist.getHeight()));
    }
    
    public static void newWindow(glist clist) {
    	
        JFrame frame = new JFrame("LAB-4   UDP SERVER.");
        gwindowUDPserver c = new gwindowUDPserver(clist);
        JLabel lclients = new JLabel("COUNT CLIENT: ");   
        lclients.setBounds(35,3,90,22);
        frame.add(lclients); 

        JLabel lresult = new JLabel("0777 ");   
        lresult.setBounds(200,3,75,22);
        frame.add(lresult); 
        
        JButton b=new JButton("START SERVER");
        b.setBounds(350,3,150,25);    
        frame.add(b); 

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(c, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();

        stopstartgame(clist,frame,lresult);
        b.addActionListener(new ActionListener() { //button click
    		
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	      if (clist.stopstart) b.setText("START SERVER" );
    	      else                 b.setText("STOP  SERVER" );
              clist.stopstart=!clist.stopstart;
              System.out.println(clist.stopstart+"  ...");
              
/*
              try {
            	  clist.setclearobject(  );
              } catch (Exception e1) {
				e1.printStackTrace();
			}
              */
              //getValue()
      //          Thread.sleep(12);
             // stopstartgame(hlist,frame);
        //        Thread.sleep(12);
    	    }
    	});	

    }

    	
   static void stopstartgame(glist m_glist, JFrame frame, JLabel lresult) {
    long next_update = System.currentTimeMillis();
    int interval = 1;
    while(true) {
        try {
            long current_time = System.currentTimeMillis();

      //      System.out.println(m_glist.stopstart+"  Waiting for a client to connect...");
    
            if (!m_glist.stopstart) continue;
    //        if (current_time>=next_update) 
//                synchronized (glist.class) 
            {
            	//m_glist.update();
            	lresult.setText(" "+m_glist.m_clients.size());
            	if (m_glist.m_clients.size()==1)
                frame.repaint();
                next_update += interval;
                if (current_time>=next_update + interval*10) next_update = current_time + interval;
                
                System.out.println("Waiting for a client to connect...");
               System.out.println(m_glist.stopstart+"  Waiting for a client to connect...");
                
                
          	  try{
              	DatagramSocket serverSocket = new DatagramSocket(278);
              	serverSocket.setSoTimeout(10000);
                byte[] receivingDataBuffer = new byte[1024];
                byte[] sendingDataBuffer = new byte[1024];
                DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                System.out.println("Waiting for a client to connect...");
              	  try {    	  
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
                    } catch (Exception ex) {
                        System.out.println("Socket error: " + ex.getMessage());
                    } 
              	  
                serverSocket.close();
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
