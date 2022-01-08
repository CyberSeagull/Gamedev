import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
        JFrame frame = new JFrame("LAB-4   UDP SERVER  micro agar.");
        gwindowUDPserver c = new gwindowUDPserver(clist);

        JLabel lroomw = new JLabel("Game room width: ");   
        lroomw.setBounds(xp,yp,107,22);
        frame.add(lroomw); 
        JSpinner sproomwidth = new JSpinner(new SpinnerNumberModel(clist.m_width, 10000, 99999, 1));   
        sproomwidth.setBounds(xp+110,yp,59,22);
        frame.add(sproomwidth); 
	    sproomwidth.setEnabled(false);

        JLabel lroomh = new JLabel("Game room hight: ");   
        lroomh.setBounds(xp+180,yp,110,22);
        frame.add(lroomh); 
        JSpinner sproomhight = new JSpinner(new SpinnerNumberModel(clist.m_height, 10000, 99999, 1));   
        sproomhight.setBounds(xp+285,yp,59,22);
        frame.add(sproomhight); 
        
        JLabel lclients = new JLabel("Count object: ");   
        lclients.setBounds(xp+355,yp,90,22);
        frame.add(lclients); 
        JLabel lresult = new JLabel("0 ");   
        lresult.setBounds(xp+444,yp,55,22);
        frame.add(lresult);
        
        yp=50;
        JLabel lport = new JLabel("№ PORT: ");   
        lport.setBounds(xp+180,yp,70,22);
        frame.add(lport); 
        JSpinner spport = new JSpinner(new SpinnerNumberModel(278, 1, 65535, 1));   
        spport.setBounds(xp+285,yp,59,22);
        frame.add(spport); 
	
	JLabel linfo = new JLabel("Waiting... ");   
        linfo.setBounds(xp+355,yp,175,22);
        Font f = new Font("Default", Font.BOLD + Font.ITALIC, 16);
       
        linfo.setFont(f);
        frame.add(linfo);

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
        b.addActionListener(new ActionListener() { //button click
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	      if (clist.stopstart) {
    	    	  b.setText("START SERVER" );
    	    	  linfo.setText("Waiting...");
    	      }
    	      else  {
    	    	  b.setText("STOP  SERVER" );
      	    	  clist.startnewround(inoutbuff.send_new_game);// delete all
              	  clist.iobuff.stategame = inoutbuff.send_game;
              	linfo.setText("Next round");
        	      if (clist.servert == null) {
        	    	  try{
        	    		  clist.setPort((Integer) spport.getValue());
          	    		  clist.servert = new dtgserver( (Integer) spport.getValue());
        	              spport.setEnabled(false);
        	              UDPreceivedata ct = new UDPreceivedata(clist.servert, clist);
        	              ct.start();
        	              //  System.out.println("Start UDPreceivedata from the client: "+ct.isAlive());
        	    	  } catch (Exception ex) {
        	    		  System.out.println("Socket error: " + ex.getMessage());}
        	      } 
    	      }
    	      clist.stopstart=!clist.stopstart;
            //  System.out.println(clist.stopstart+"  ...");
    	    }
    	});	
        stopstartgame(clist,frame,lresult, linfo);
    }

   static void stopstartgame(glist hlistu, JFrame frame, JLabel lresult, JLabel linfo) {
		    //startgame=true;
	        long next_update = System.currentTimeMillis();
	        int interval = 1000 / hlistu.getFPS();
            long work_time = System.currentTimeMillis();
	        while(true) {
	            try {
	                long current_time = System.currentTimeMillis();
	                Thread.sleep(10);
	                if (!hlistu.stopstart) {
	                	work_time = System.currentTimeMillis();
	                	hlistu.iobuff.stategame = inoutbuff.send_game;
	                  //	linfo.setText("Next round.");
	                	continue;
	                }
	                switch (hlistu.iobuff.stategame) {
					case inoutbuff.send_game:
						if(System.currentTimeMillis()-work_time>hlistu.timeround) {//round is over -> showing results
		                	work_time = System.currentTimeMillis();
		                	hlistu.startnewround(inoutbuff.send_info);
		                	hlistu.iobuff.stategame= inoutbuff.send_info;
		                  	linfo.setText("List of winners.");
						}
						break;
					case inoutbuff.send_info:
						if(System.currentTimeMillis()-work_time>hlistu.breakbetweenrounds) {//break is over -> showing results
		                	work_time = System.currentTimeMillis();
		                	hlistu.startnewround(inoutbuff.send_game);
		                	hlistu.iobuff.stategame= inoutbuff.send_game;
			                linfo.setText("Next round.");
		                	hlistu.startnewround(hlistu.iobuff.stategame);
						}
						break;
					}
	                
	                if (current_time>=next_update) 
	                {
	                	lresult.setText(" "+hlistu.m_clients.size());
	                    next_update += interval;
	                    hlistu.update();
	                    if (current_time>=next_update + interval*10) next_update = current_time + interval;
	                }
	                Thread.sleep(1);
	            }catch(Exception e) {
	                e.printStackTrace();
	            }
	        }
		    }   

}
