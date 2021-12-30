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
import javax.swing.SpinnerNumberModel;


public class gwindowUDPserver extends JComponent {
	private static final long serialVersionUID = 1L;
	glist m_glist;
    
    private gwindowUDPserver(glist lclients) {        
        m_glist = lclients;
        setPreferredSize(new Dimension((int)m_glist.getWidth(),(int)m_glist.getHeight()));
    }
    
    public static void newWindow(glist clist) {
    	int yp=15;
    	int xp=5;
        JFrame frame = new JFrame("LAB-4   UDP SERVER.");
        gwindowUDPserver c = new gwindowUDPserver(clist);

        JLabel lroomw = new JLabel("Game room width: ");   
        lroomw.setBounds(xp,yp,107,22);
        frame.add(lroomw); 
        JSpinner sproomwidth = new JSpinner(new SpinnerNumberModel(5000, 800, 99999, 1));   
        sproomwidth.setBounds(xp+110,yp,59,22);
        frame.add(sproomwidth); 

        JLabel lroomh = new JLabel("Game room hight: ");   
        lroomh.setBounds(xp+180,yp,110,22);
        frame.add(lroomh); 
        JSpinner sproomhight = new JSpinner(new SpinnerNumberModel(5000, 800, 99999, 1));   
        sproomhight.setBounds(xp+285,yp,59,22);
        frame.add(sproomhight); 
        
        JLabel lclients = new JLabel("COUNT CLIENT: ");   
        lclients.setBounds(xp+355,yp,90,22);
        frame.add(lclients); 
        JLabel lresult = new JLabel("777 ");   
        lresult.setBounds(xp+444,yp,55,22);
        frame.add(lresult); 
        
        yp=50;
        JLabel lport = new JLabel("� PORT: ");   
        lport.setBounds(xp+180,yp,70,22);
        frame.add(lport); 
        JSpinner spport = new JSpinner(new SpinnerNumberModel(278, 1, 65535, 1));   
        spport.setBounds(xp+285,yp,59,22);
        frame.add(spport); 

        JButton b=new JButton("START SERVER");
        b.setBounds(xp+17,yp,150,25);    
        frame.add(b); 

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(c, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
        try{
        	clist.setPort((Integer) spport.getValue());
    		clist.servert = new dtgserver( (Integer) spport.getValue());
            UDPreceivedata ct = new UDPreceivedata(clist.servert, clist);
            ct.start();
              //System.out.println("Start UDPreceivedata from the client: "+ct.isAlive());
        } catch (Exception ex) {
              System.out.println("Socket error: " + ex.getMessage());
        } 

        b.addActionListener(new ActionListener() { //button click
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	      if (clist.stopstart) b.setText("START SERVER" );
    	      else                 b.setText("STOP  SERVER" );
              clist.stopstart=!clist.stopstart;
              System.out.println(clist.stopstart+"  ...");
    	    }
    	});	
        stopstartgame(clist,frame,lresult);
    }

    	
   static void stopstartgame(glist hlistu, JFrame frame, JLabel lresult) {
		    //startgame=true;
	        long next_update = System.currentTimeMillis();
	        int interval = 1000 / hlistu.getFPS();
	        while(true) {
	            try {
	                long current_time = System.currentTimeMillis();
	                Thread.sleep(12);
	                if (!hlistu.stopstart) continue;
	                if (current_time>=next_update) 
	                {
	                	lresult.setText("ALL: "+hlistu.m_clients.size());
	                	hlistu.setallposition();
	                    next_update += interval;
	                    if (current_time>=next_update + interval*10) next_update = current_time + interval;
	                }
	                Thread.sleep(1);
	            }catch(Exception e) {
	                e.printStackTrace();
	            }
	        }
		    }   

}
