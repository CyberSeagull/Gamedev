//view

import java.awt.*;
import javax.swing.JPanel;

//import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;

public class qpanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public pboard[] pb = new pboard [qd.ng];
	public qpanel() {
		for (int i=0; i<qd.ng; i++) {
			pb[i]= new pboard();
			pb[i].x=-10;
		}
			
	};

public void boardrepaint() {
	//	revalidate();
		repaint();
	};
	
	public void 		paintnew(Graphics g) {
		for (int i=0; i<qd.ng; i++)
		if (pb[i].x>=0) {
			g.setColor(pb[i].c1);
			if( (pb[i].c1==qd.cpawn1) || (pb[i].c1==qd.cpawn2) )
				g.fillOval(pb[i].x, pb[i].y, pb[i].w, pb[i].h);
			else 
				g.fillRect(pb[i].x, pb[i].y, pb[i].w, pb[i].h);
				
		}
	};
			
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	setBackground(qd.cboard); // setting the background colour
    	g.setColor(qd.ccells); // setting up the colour
		for (int i=0; i<qd.nmatr; i++)
			for (int j=0; j<qd.nmatr; j++)
		    	g.fillRect(qd.ph+(qd.ph+qd.cellwh)*i, qd.ph+(qd.ph+qd.cellwh)*j, qd.cellwh, qd.cellwh);

		paintnew(g);

    }
}