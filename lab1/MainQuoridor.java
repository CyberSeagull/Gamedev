//import java.awt.Point;
import java.util.Scanner;

public class MainQuoridor {
	public static void main(String[] args) {
		
		new qv(); 
		
		// for lab2 comment new qv(); and remove the comments from below 
		 /*
        Scanner in = new Scanner(System.in);
        qd cdate = new qd();
		qm cmodel = new qm();
		player p1 = new player(true);
		player p2 = new player(false);
		cdate.startgame=0;
        String sg = "";
//		System.out.println("move E2");

		do {
	        try {  
			sg = in.nextLine();
  //  		System.out.println("move E2");
   // 		System.out.println(" EXO  "+sg);
			sg = sg.toLowerCase(); //777777
			if (sg.indexOf(qd.stremty)>=0)
				cmodel.setmoveenemy (sg, p1, p2, cdate.le);
			if (sg.toUpperCase().contains(qd.strexit.toUpperCase()))
	        	break;
	        if (sg.toUpperCase().contains(qd.strblack.toUpperCase())) {// starting a new game
	        	cdate.startgame=1;
	     	   p1.initplayer(true);
		       p2.initplayer(false);
		       p2.comp=true;
		       p1.comp=false; //77777
		       } 
	        if (sg.toUpperCase().contains(qd.strwhite.toUpperCase())) {// starting a new game
	           cdate.startgame=1;
	     	   p1.initplayer(true);
		       p2.initplayer(false);
		       p1.comp=true;
		       p2.comp=false; //777777777
		       }
	        
	        if (p1.pmove && p1.comp) { // ai's making a move
	         	cmodel.setnewmovecomp(p1, p2, cdate.le);
	         //	System.out.println(p1.counter);
	        }
	        if (p2.pmove && p2.comp)  // ai's making a move
	        	cmodel.setnewmovecomp(p2, p1, cdate.le);    

	    	if (p1.counter>=(qd.ng-qd.nmatr) )
	    		cdate.startgame=2;  
	    	if (p2.counter<qd.nmatr)
	    		cdate.startgame=3; 

	        } catch (Exception e) { 
	            System.out.println("Input problem "+e);
	        }               
	        System.gc();				
		    } while (cdate.startgame<=1); //// 0 - waiting for the game to start  1- game has begun 2-3 - game has ended
        
		System.out.printf(" GAME OVER \n");
		
        in.close();        
		 */
	}
 
}
