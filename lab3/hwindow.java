import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;



	public class hwindow extends JComponent {
		private static final long serialVersionUID = 1L;
		hlist m_hlist;
	    
	    private hwindow(hlist lobject) {        
	        m_hlist = lobject;
	        setPreferredSize(new Dimension((int)m_hlist.getWidth(),(int)m_hlist.getHeight()));
	    }
	    
	    public static void newWindow(hlist hlist) {
	        JFrame frame = new JFrame("      H U N T I N G ");
	        hwindow c = new hwindow(hlist);
	        frame.getContentPane().setLayout(new BorderLayout());
	        frame.getContentPane().add(c, BorderLayout.CENTER);
	        frame.pack();
	        frame.setResizable(false);
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.repaint();

	        long next_update = System.currentTimeMillis();
	        int interval = 1000 / hlist.getFPS();
	        while(true) {
	            try {
	                long current_time = System.currentTimeMillis();
	                if (current_time>=next_update) {
	                    hlist.update();
	                    frame.repaint();
	                    next_update += interval;
		                Thread.sleep(3);
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
	        
	        g2d.fillRect(0,0, (int)(m_hlist.getWidth()), (int)(m_hlist.getHeight()));
	        m_hlist.draw(g2d);
	    }    
	}
