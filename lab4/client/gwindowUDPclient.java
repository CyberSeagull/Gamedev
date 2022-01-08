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
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
        JFrame frame = new JFrame("LAB-4   UDP CLIENT mini agar.");
        gwindowUDPclient c = new gwindowUDPclient(clist);
        
        yp=3;

        JButton b=new JButton("START GAME");
        b.setBounds(xp,yp,110,25);    
        frame.add(b); 

        JLabel lport = new JLabel("№ Port:");   
        lport.setBounds(xp+115,yp+1,60,22);
        frame.add(lport); 
        JSpinner spport = new JSpinner(new SpinnerNumberModel(278, 1, 65535, 1));   
        spport.setBounds(xp+160,yp+2,59,22);
        frame.add(spport); 
        
        JLabel lip = new JLabel("IP server:");   
        lip.setBounds(xp+225,yp+1,70,22);
        frame.add(lip); 
        
        JTextField tfip = new JTextField("192.20.12.23", 5);
        tfip.setBounds(xp+280,yp+1,95,25);    
        frame.add(tfip); 

        JLabel lname = new JLabel("Name:");   
        lname.setBounds(xp+377,yp+1,50,22);
        frame.add(lname);
        String sname = "Чайка "+String.valueOf( (int) (Math.random() * 1000)  );
        JTextField tfname = new JTextField(sname, 5);
        tfname.setBounds(xp+415,yp+1,77,25);    
        frame.add(tfname); 

        JLabel lresult = new JLabel("...");   
        lresult.setBounds(xp+200,yp+45,75,22);
        frame.add(lresult);
        lresult.setVisible(false);
        //==================================
        JLabel lginfo = new JLabel("List of winners");   
        lginfo.setBounds(200,45,200,22);
        frame.add(lginfo); 
        lginfo.setVisible(false);

//        for (String string : data2) clist.infowinners.add(0, string);
        JPanel pinfo = new JPanel();
        JList<String> linfo = new JList<String>(clist.infowinners);
        JScrollPane spinfo = new JScrollPane(linfo);
        spinfo.setPreferredSize(new Dimension(290, 390));
        pinfo.setBounds(100, 75, 300, 400);    
        pinfo.add(spinfo);
        frame.add(pinfo);
        pinfo.setVisible(false);
        //=========================================

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
    	      if (clist.stopstart) {
    	    	  b.setText("START GAME" );
        	      lresult.setText(" ");
    	      }
    	      else {
    	    	//  clist.startnewraund();
        	      if (clist.clientudp == null) {
        	    	  try{
        	    		  clist.setPort((Integer) spport.getValue());
        	    		  clist.setipserver(tfip.getText());
          	    		  clist.clientudp = new dtgclient( (Integer) spport.getValue(), tfip.getText());
        	              spport.setEnabled(false);
        	              tfip.setEnabled(false);
        	    		
        	              if (clist.clientsend==null)
        	            	  clist.clientsend = new UDPsenddata(clist.clientudp, clist); 
        	              

        	              UDPreceivedata udpcl = new UDPreceivedata(clist.clientudp, clist);
        	              udpcl.start();
        	              
        	              //  System.out.println("Start UDPreceivedata from the client: "+ct.isAlive());
        	    	  } catch (Exception ex) {
        	    		  System.out.println("Socket error: " + ex.getMessage());}
        	      } 
        	      b.setText("STOP  GAME" );
        	      clist.playername=tfname.getText();
        	      lresult.setText("Waiting ...");
    	      }
              clist.stopstart=!clist.stopstart;
              try {
            	  clist.setclearobject();//(Integer)(Integer)sphare.getValue(), (Integer)spwolf.getValue(), (Integer)spdoe.getValue() );
              } catch (Exception e1) {
				e1.printStackTrace();
			}
              
              System.out.println(clist.stopstart+"  ...");
    
    	    }
    	});	
        stopstartgame(clist, frame, lname, lginfo, pinfo);

    }

   static void stopstartgame(glist m_glist, JFrame frame, JLabel lresult, JLabel lginfo, JPanel pinfo) {
    long next_update = System.currentTimeMillis();
    while(true) {
        try {
            long current_time = System.currentTimeMillis();
            int interval = 1000 / m_glist.getFPS();
            Thread.sleep(1);
          //  lginfo.setVisible(false); 
            if (!m_glist.stopstart) {
            	lginfo.setVisible(false);
            	pinfo.setVisible(false);
            	continue;
            }
            if (current_time>=next_update) 
            {
            	m_glist.update();
                lginfo.setVisible(m_glist.iobuff.stategame>0);
                lginfo.setText( m_glist.getstrstategame());
                pinfo.setVisible(m_glist.iobuff.stategame==inoutbuff.send_info);
            	
                frame.repaint();
                lresult.setText(" "+m_glist.m_clients.size());
                next_update += interval;
                if (current_time>=next_update + interval*10) next_update = current_time + interval;
                
            }
            Thread.sleep(1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    }   
    
   public void paint(Graphics g) {
       Graphics2D g2d = (Graphics2D)g;
       g2d.setColor( new Color(155,196,226));
       g2d.fillRect(m_glist.getdx(), m_glist.getdy(), m_glist.getWidth()-m_glist.getdx(), m_glist.getHeight()-m_glist.getdy());
       m_glist.draw(g2d);
   }    
    
    
}
