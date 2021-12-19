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


	public class hwindow extends JComponent {
		private static final long serialVersionUID = 1L;
		hlist m_hlist;
		JRadioButton rb1,rb2;    
		JButton b;    
		JSpinner spinner;
		JLabel lp1,lp2, lp1w, lp2w;
		JLabel lpmove, lresult; //2021
	        boolean startgame=false;
	    
	    private hwindow(hlist lobject) {        
	        m_hlist = lobject;
	        setPreferredSize(new Dimension((int)m_hlist.getWidth(),(int)m_hlist.getHeight()));
	    }
	    
	    public static void newWindow(hlist hlist) {
	        JFrame frame = new JFrame("      H U N T I N G ");
	        hwindow c = new hwindow(hlist);
	        int xl=20;
	        int yt=3;
		    //wolf
	        JLabel lwolf = new JLabel("WOLF: ");   
	        lwolf.setBounds(xl+15,yt,40,22);
	        frame.add(lwolf); 
	        JSpinner spwolf = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));   
	        spwolf.setBounds(xl+57,yt,45,22);
	        frame.add(spwolf); 
		    //hare
	        JLabel lhare = new JLabel("HARE: ");   
	        lhare.setBounds(xl+125,yt,40,22);
	        frame.add(lhare); 
	        
	        JSpinner sphare = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
	        sphare.setBounds(xl+167,yt,45,22);
	        //sphare.setValue(7);
	        frame.add(sphare); 
		    //DOE
	        JLabel ldoe = new JLabel("DOE: ");   
	        ldoe.setBounds(xl+235,yt,40,22);
	        frame.add(ldoe); 
	        JSpinner spdoe = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));   
	        spdoe.setBounds(xl+277,yt,45,22);
	        spdoe.setValue(2);
	        frame.add(spdoe); 

	        JButton b=new JButton("START GAME");
	        b.setBounds(550,yt,110,25);    
	        frame.add(b); 

	        JLabel lresult = new JLabel("ALL: ");   
	        lresult.setBounds(400,yt,75,22);
	        frame.add(lresult); 
	        
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
	   // 	      startgame=true;
	    	      if (hlist.stopstart) b.setText("START GAME" );
	    	      else                 b.setText("STOP  GAME" );
	              hlist.stopstart=!hlist.stopstart;
	              try {
	            	  hlist.setclearobject(  (Integer)sphare.getValue(), (Integer)spwolf.getValue(), (Integer)spdoe.getValue() );
	              } catch (Exception e1) {
					e1.printStackTrace();
				}
	              //getValue()
	      //          Thread.sleep(12);
	             // stopstartgame(hlist,frame);
	        //        Thread.sleep(12);
	    	    }
	    	});	
            stopstartgame(hlist,frame,lresult);

	    }
		   


	   static void stopstartgame(hlist hlistu, JFrame frame, JLabel lresult) {
	    //startgame=true;
        long next_update = System.currentTimeMillis();
        int interval = 1000 / hlistu.getFPS();
        while(true) {
            try {
                long current_time = System.currentTimeMillis();
                Thread.sleep(12);
                if (!hlistu.stopstart) continue;
                if (current_time>=next_update) 
//                    synchronized (hlist.class) 
                {
                	hlistu.update();
                	lresult.setText("ALL: "+hlistu.m_objects.size());
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
	        g2d.setColor( new Color(8,127,8));
	    	//System.out.println("!! lmin= "+999+" C.x=  "+getWidth()+" C.y=  "+ getHeight()  ); 
	        
	        g2d.fillRect(m_hlist.getdx(), m_hlist.getdy(), m_hlist.getWidth()-m_hlist.getdx(), m_hlist.getHeight()-m_hlist.getdy());
	        m_hlist.draw(g2d);
	    }    
	}
