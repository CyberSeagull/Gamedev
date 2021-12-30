package pwclient;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    	int xp=10;
        JFrame frame = new JFrame("LAB-4   UDP CLIENT.");
        gwindowUDPclient c = new gwindowUDPclient(clist);

        JLabel lresult = new JLabel("777 ");   
        lresult.setBounds(xp+444,yp,55,22);
//        frame.add(lresult); 
        
        yp=3;

        JButton b=new JButton("START GAME");
        b.setBounds(xp,yp,110,25);    
        frame.add(b); 

        JLabel lport = new JLabel("� PORT: ");   
        lport.setBounds(xp+130,yp+1,70,22);
        frame.add(lport); 
        JSpinner spport = new JSpinner(new SpinnerNumberModel(278, 1, 65535, 1));   
        spport.setBounds(xp+185,yp+2,59,22);
        frame.add(spport); 
        
        JLabel lip = new JLabel("IP server: ");   
        lip.setBounds(xp+270,yp+1,70,22);
        frame.add(lip); 
        JTextField tfip = new JTextField("127.0.0.1", 5);
        tfip.setBounds(xp+335,yp+1,100,25);    
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
              try {
            	  clist.setclearobject(  20);//(Integer)(Integer)sphare.getValue(), (Integer)spwolf.getValue(), (Integer)spdoe.getValue() );
              } catch (Exception e1) {
				e1.printStackTrace();
			}
              
              System.out.println(clist.stopstart+"  ...");
    
    	    }
    	});	
        stopstartgame(clist,frame,lresult);

    }

    	
   static void stopstartgame(glist m_glist, JFrame frame, JLabel lresult) {
    long next_update = System.currentTimeMillis();
    while(true) {
        try {
            long current_time = System.currentTimeMillis();
            int interval = 1000 / m_glist.getFPS();
          //  System.out.println(m_glist.stopstart+"  ssssssssssssssst...");
            Thread.sleep(1);
 
            if (!m_glist.stopstart) 
            	continue;
            if (current_time>=next_update) 
            {
            	m_glist.update();
            	lresult.setText(" "+m_glist.m_clients.size());
            //	if (m_glist.m_clients.size()==1)
                frame.repaint();
                next_update += interval;
                if (current_time>=next_update + interval*10) next_update = current_time + interval;
                
                System.out.println("Waiting for a client to connect...");
               System.out.println(m_glist.stopstart+" 2  Waiting for a client to connect...");
            }
            Thread.sleep(1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    }   
    
   public void paint(Graphics g) {
       Graphics2D g2d = (Graphics2D)g;
   //    g2d.setColor( new Color(8,8,8));
   //    g2d.fillRect(0, 0, (int)(m_hlist.getWidth()), (int)(m_hlist.getHeight()));
       g2d.setColor( new Color(155,196,226));
   	//System.out.println("!! lmin= "+999+" C.x=  "+getWidth()+" C.y=  "+ getHeight()  ); 	        
       g2d.fillRect(m_glist.getdx(), m_glist.getdy(), m_glist.getWidth()-m_glist.getdx(), m_glist.getHeight()-m_glist.getdy());
       
       //g2d.fillRect(10,30, (int)(m_hlist.getWidth())-10, (int)(m_hlist.getHeight())-30);
       //g2d.fillRect(10, 30, (m_hlist.getWidth()-m_hlist.getdx()), (m_hlist.getHeight()-m_hlist.getdy()));
       m_glist.draw(g2d);
   }      
    
    
}
