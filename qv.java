// it had to be a view, but became a controller with some view functionality

import javax.swing.*;

//import javafx.scene.shape.Ellipse;

import java.awt.*; // AWT package is responsible for creating GUI
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
   
public class qv extends JFrame implements ActionListener{    
	private static final long serialVersionUID = 1L;

	qd qdate = new qd();
	qm qmodel = new qm();
	player p1 = new player(true);
	player p2 = new player(false);

JRadioButton rb1,rb2;    
JButton b;    
JLabel lp1,lp2, lp1w, lp2w;
JLabel lpmove;

//private volatile int draggedAtX, draggedAtY;
qpanel board=new qpanel();  

qv(){      
setTitle("              Q  U  O  R  I  D  O  R");
int ftop=10;	
int phw=(qd.ph+qd.cellwh)*qd.nmatr+qd.ph;
this.setBounds(ftop,ftop,50+phw,130+phw);    
board.setBounds(20,70,phw, phw);    
//board.setBackground(Color.gray);  
add(board);

lp1=new JLabel("Player 1. Walls: ");
lp1.setBounds(25, 10, 110,30);  
add(lp1); 
lp2=new JLabel("Computer . Walls: ");  
lp2.setBounds(phw-phw/4, 10, 270,30);  
add(lp2); 
lp1w=new JLabel(" 10");  
lp1w.setBounds(130, 10, 270,30);  
add(lp1w); 
lp2w=new JLabel(" 10");  
lp2w.setBounds(phw-phw/4+105, 10, 110,30);  
add(lp2w); 
lpmove=new JLabel(" First player makes a move");
lpmove.setBounds(phw/2,35,170,25); 
add(lpmove);


JLabel l1,l2,l3;
l1=new JLabel("l1 Label.");  
l1.setBounds(phw/2+35, 47, 270,30);  


l2=new JLabel("l2 Label.");  
l2.setBounds(phw/2-100,47, 200,30);  


l3=new JLabel("l3 Label.");  
l3.setBounds(20, 47, 200,30);  
//add(l3);
//add(l1); 
//add(l2);

rb1 = new JRadioButton("Play with computer");
rb2 = new JRadioButton("Two players");
rb1.setBounds(phw/4+25, 3,90,30);        
rb2.setBounds(phw/4+25, 30,90,30);  
rb1.setSelected(true);
ButtonGroup bg=new ButtonGroup();    
bg.add(rb1);bg.add(rb2);    

b=new JButton("Start a new game?");
b.setBounds(phw/2,9,170,25);
b.addActionListener(this);    
add(rb1);add(rb2);add(b);   


	b.addActionListener(new ActionListener() { //button click
	    @Override
	    public void actionPerformed(ActionEvent e) {
    	   p1.initplayer(true);
	       p2.initplayer(false);
	       lp1w.setText(" "+p1.cpartition); 
	       lp2w.setText(" "+p2.cpartition);
   	       if(rb2.isSelected())  
   	         	lp2.setText("Player 2. Walls:");
   	       else lp2.setText("Computer . Walls:");
   	        	
		       qdate.mousexy.x=0;
		       qdate.mousexy.y=0;
		       qmodel.setccoordtopaint( qdate.mousexy, board.pb, p1, p2, false);
		       board.boardrepaint();
	    }
	});	
	
	board.addMouseMotionListener(new MouseMotionAdapter(){
		@Override
	    public void mouseMoved(MouseEvent e) {
//	        l2.setText("X= "+e.getX()+"  Y= "+e.getY()+"  Y= "+( (qd.ph+qd.cellwh)*qd.nmatr+qd.ph-e.getY())     );
	        
	        Point pp =new Point( qm.getncells(e.getX(), e.getY() ) )  ;
	        if ( !( (pp.x==qdate.mousexy.x) &&  (pp.y==qdate.mousexy.y) ) ) {
		        qdate.mousexy.x=pp.x;
		        qdate.mousexy.y=pp.y;
		    //    qmodel.setccoordtopaint(pp, board.pb, p1, p2, false);
		   //     board.boardrepaint();
		        setdatetoframe(pp);

	        }	       
	    }
	});
	
	board.addMouseListener(new MouseAdapter(){
		@Override
	    public void mouseClicked(MouseEvent e) {
	        l3.setText("X= "+e.getX()+"  Y= "+e.getY()  );
	        Point nc =new Point( qm.getncells(e.getX(), e.getY() ) );// "number= "+pp.x+" (0-square; 1-on the right; 2-up; -1 not on the field
	        if ((nc.y==1)||(nc.y==2)) //setting a fence
	        	qmodel.setnewpartition(nc, p1, p2, qdate.le);
	        if (nc.y==0) //making a move
	        	qmodel.isrealgotopawn(nc.x, p1, p2);
	        setdatetoframe(nc);
	    }
	    public void mouseExited   (MouseEvent e){
//	        l1.setText("X= "+e.getX()+"  Y= "+e.getY());    
	    }
	});
/*
addMouseMotionListener(new MouseAdapter() {
    @Override
    public void mouseMoved(MouseEvent e) {
    }*/

setLayout(null);    
setVisible(true);    
}  
void setdatetoframe(Point pp) {
    qmodel.setccoordtopaint(pp, board.pb, p1, p2, false);
    board.boardrepaint();
    lp1w.setText(" "+p1.cpartition); 
    lp2w.setText(" "+p2.cpartition);
    if (p1.pmove)
         lpmove.setText("First player makes a move");
    else lpmove.setText("Second player makes a move");

};

@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub	
}    
}   