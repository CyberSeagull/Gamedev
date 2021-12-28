import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class gwindowUDPserver extends JFrame {
	private static final long serialVersionUID = 1L;
	glist m_glist;
	JRadioButton rb1,rb2;    
	JButton b;    
	JSpinner spinner;
	JLabel lp1,lp2, lp1w, lp2w;
	JLabel lpmove, lresult; //2021
    boolean startgame=false;
    
    public  gwindowUDPserver() {
        setBounds(500, 500, 500, 125);
    	setTitle("LAB-4   UDP SERVER.");
        int xl=20;
        int yt=3;
        JLabel lwolf = new JLabel("COUNT CLIENT: ");   

        JButton b=new JButton("START SERVER");
        b.setBounds(550,yt,110,25);    
        add(b); 

        JLabel lresult = new JLabel("ALL: ");   
        lresult.setBounds(400, 333,75,22);
        add(lresult); 
        
//        setResizable(false);
        
        setLayout(null);    
        setVisible(true);    
        
        
  //      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //      setVisible(true);
  //      repaint();

        b.addActionListener(new ActionListener() { //button click
    		
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
   // 	      startgame=true;
    	      if (m_glist.stopstart) b.setText("START GAME" );
    	      else                 b.setText("STOP  GAME" );
    	      m_glist.stopstart=!m_glist.stopstart;
              /*
    	      try {
            	  hlist.setclearobject(  (Integer)sphare.getValue(), (Integer)spwolf.getValue(), (Integer)spdoe.getValue() );
              } catch (Exception e1) {
				e1.printStackTrace();
			}*/
              
              //getValue()
      //          Thread.sleep(12);
             // stopstartgame(hlist,frame);
        //        Thread.sleep(12);
    	    }
    	});	
        stopstartgame(m_glist, this, lresult);

        
    }
    
	   


   static void stopstartgame(glist m_glist, JFrame frame, JLabel lresult) {
    //startgame=true;
    long next_update = System.currentTimeMillis();
    int interval = 1;
    while(true) {
        try {
            long current_time = System.currentTimeMillis();
            if (!m_glist.stopstart) continue;
            if (current_time>=next_update) 
//                synchronized (hlist.class) 
            {
            	//m_glist.update();
            	int counto=m_glist.m_clients.size();
            	lresult.setText("ALL: "+m_glist.m_clients.size());
            	if (m_glist.m_clients.size()==1)
                frame.repaint();
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
    //    g2d.setColor( new Color(8,8,8));
    //    g2d.fillRect(0, 0, (int)(m_hlist.getWidth()), (int)(m_hlist.getHeight()));
    //    g2d.setColor( new Color(8,127,8));
    	//System.out.println("!! lmin= "+999+" C.x=  "+getWidth()+" C.y=  "+ getHeight()  ); 	        
//        g2d.fillRect(m_hlist.getdx(), m_hlist.getdy(), m_hlist.getWidth()-m_hlist.getdx(), m_hlist.getHeight()-m_hlist.getdy());
        
        //g2d.fillRect(10,30, (int)(m_hlist.getWidth())-10, (int)(m_hlist.getHeight())-30);
        //g2d.fillRect(10, 30, (m_hlist.getWidth()-m_hlist.getdx()), (m_hlist.getHeight()-m_hlist.getdy()));
  //      m_hlist.draw(g2d);
    }    
}