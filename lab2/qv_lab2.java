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
JLabel lpmove, lresult; 

qpanel board=new qpanel();  

qv(){      
setTitle("\"             Q     U     O     R     I     D     O     R   ");
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
lpmove.setBounds(phw/2,33,170,25);
add(lpmove);

lresult=new JLabel("  ");
lresult.setBounds(phw/2,50,170,25);
lresult.setForeground(Color.BLUE);
add(lresult);

//Point bsize = new Point(board.getSize().height,board.getSize().width); //2021

JLabel l1,l2,l3;
l1=new JLabel("l1 Label.");  
l1.setBounds(phw/2+35, 47, 270,30);  


l2=new JLabel("l2 Label.");  
l2.setBounds(phw/2-100,47, 200,30);  

l3=new JLabel("l3 Label.");  
l3.setBounds(20, 47, 200,30);  

rb1 = new JRadioButton("Play with computer");
rb2 = new JRadioButton("Two players");
rb1.setBounds(phw/4+7, 1, 134,20);        
rb2.setBounds(phw/4+7, 21,120,20);  
rb1.setSelected(true);   
ButtonGroup bg=new ButtonGroup();    
bg.add(rb1);bg.add(rb2);    

JCheckBox jbPC = new JCheckBox("First AI makes a move");
jbPC.setBounds(phw/4-15, 41,150,20);
add(jbPC);

b=new JButton("Start a new game?");
b.setBounds(phw/2,9,110,25);    
b.addActionListener(this);    
add(rb1);add(rb2);add(b);   
qdate.startgame=0;

	b.addActionListener(new ActionListener() { //button click
	    @Override
	    public void actionPerformed(ActionEvent e) {
    	   p1.initplayer(true);
	       p2.initplayer(false);

	       p1.comp=jbPC.isSelected() && rb1.isSelected(); 
	       p2.comp=(!jbPC.isSelected()) && rb1.isSelected(); 
	       
           lp1.setText(p1.comp ? "Computer . Walls:" :"Player 1. Walls:");
           lp2.setText(p2.comp ? "Computer . Walls:" :"Player 2. Walls:");
          qdate.mousexy.x=-1;	       
          qdate.mousexy.y=-1;
          lresult.setText("Game has begun!");
          qdate.startgame=1;
          setdatetoframe(qdate.mousexy);
	    }
	});	
	
	board.addMouseMotionListener(new MouseMotionAdapter(){
		@Override
	    public void mouseMoved(MouseEvent e) {
	        if (qdate.startgame==1) { 
	        Point pp =new Point( qm.getncells(e.getX(), e.getY() ) )  ;
	        if ( !( (pp.x==qdate.mousexy.x) &&  (pp.y==qdate.mousexy.y) ) ) {
		        qdate.mousexy.x=pp.x;
		        qdate.mousexy.y=pp.y;
		        setdatetoframe(pp);
	        }
	        }	       
	    }
	});
	
	board.addMouseListener(new MouseAdapter(){
		@Override
	    public void mouseClicked(MouseEvent e) {
	        if (qdate.startgame==1) { 
			
			Point nc =new Point( qm.getncells(e.getX(), e.getY() ) );
	        if (  !(p1.pmove && p1.comp || p2.pmove && p2.comp) ) { // it's not a computer who makes a move
			if ((nc.y==1)||(nc.y==2)) //setting a fence
	        	qmodel.setnewpartition(nc, p1, p2, qdate.le);
	        if (nc.y==0) //moving a pawn
	           qmodel.isrealgotopawn(nc.x, p1, p2);
	        }
        	  
	        setdatetoframe(nc);
	        }
	    }
	    public void mouseExited   (MouseEvent e){
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
    setdatetolabel();
    if (p1.pmove && p1.comp)  // an AI's making a move
    	qmodel.setnewmovecomp(p1, p2, qdate.le);    
    if (p2.pmove && p2.comp)  // same
    	qmodel.setnewmovecomp(p2, p1, qdate.le);    
     setdatetolabel();
     qmodel.setccoordtopaint(pp, board.pb, p1, p2, false);
     board.boardrepaint();
     
};

void setdatetolabel() {
    lp1w.setText(" "+p1.cpartition); 
    lp2w.setText(" "+p2.cpartition);
    if (p1.pmove)
         lpmove.setText("First player makes a move");
    if (p2.pmove) lpmove.setText("Second player makes a move");
	if (p1.counter>=(qd.ng-qd.nmatr) )  {
		lresult.setText("First player won");
		qdate.startgame=2;
	}  
	if (p2.counter<qd.nmatr)  {
		lresult.setText("Second player won");
		qdate.startgame=3;
	}
};

@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub	
}    
}   
