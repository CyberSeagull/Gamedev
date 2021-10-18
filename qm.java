// qm stands for quoridor model

import java.awt.Point;
	
public class qm {
    int[][] wedge   = new int [qd.ng][qd.ng]; // all possible weights of vertexes

	public int [] Dijkstra(int xst, int[] le) {
		int lmin;
		int i,j;
   	    boolean[] flag  = new boolean [qd.ng];
		for (i=0; i<qd.ng; i++)
		{
			flag[i]= false;
			le[i]  = wedge[xst][i];
		}
		le[xst]=0;
		int ind=0;
		int u=0;
		
		for (i=0; i<qd.ng; i++)
		{
			lmin=qd.maxedge;
			for (j=0; j<qd.ng; j++)
				if ( (!flag[j]) && (le[j]<lmin) )
				{
			        lmin=le[j];
		            ind=j;
				}
			u=ind;
			flag[u]=true;
			for (j=0; j<qd.ng; j++)
				if ( (!flag[j]) && (wedge[u][j]<qd.maxedge) && (le[u]+wedge[u][j]<le[j]) )
				{
					le[j]=le[u]+wedge[u][j];
				}
		}		

		return le;
	}

	public void setedgepartition(int x1, int x2) {
		if (x1+1==x2)  // horizontal fence
		{
			wedge[x1][x1+qd.nmatr]=qd.maxedge;
			wedge[x2][x2+qd.nmatr]=qd.maxedge;
			wedge[x1+qd.nmatr][x1]=qd.maxedge;
			wedge[x2+qd.nmatr][x2]=qd.maxedge;
		} 
		else  // vertical fence
		{
		    wedge[x1][x1+1]=qd.maxedge;
		    wedge[x2][x2+1]=qd.maxedge;
		    wedge[x1+1][x1]=qd.maxedge;
		    wedge[x2+1][x2]=qd.maxedge;
	    }
	}

	public void initialplayers(player p1, player p2) {
		p1.initplayer(true);
		p2.initplayer(false);
	}
	
//	public void setedge(int nplayer, player p1, player p2) {
	public void setedge(player p1, player p2) {//p1 - the player which makes a move
    	int i, ip, j;
        boolean dirup, zeroedge;
		for (i=0; i<qd.ng; i++)
			for (j=0; j<qd.ng; j++)
			{
			      wedge[i][j]=qd.maxedge;
			      if (i==j)        //vertex weight =0
			        wedge[i][j]=0;
			      if ( (i==j-1) && ( ((j) % qd.nmatr)>0 ) ) //square on the right
			        wedge[i][j]=1;
			      if ( (i-1==j) && ( ((i) % qd.nmatr)>0 ) ) //square on the left
			        wedge[i][j]=1;
			      if (i==j-qd.nmatr) //square in the front
			        wedge[i][j]=1;
			      if (i-qd.nmatr==j)  //square behind
			        wedge[i][j]=1;
			}
		
	      // vertexes
		for (i=0; i<qd.npartition; i++) {
		    if (p1.partition[i][0]>=0) 
		    	setedgepartition(p1.partition[i][0], p1.partition[i][1]);
		    if (p2.partition[i][0]>=0) 
		    	setedgepartition(p2.partition[i][0], p2.partition[i][1]);			
		}
		    // pawns     one goes from his side (square 5,3) to the other (square 1,x)
		    // pawns     the other goes from his side (square 1,3) to the other (square 5,x)
		i=p2.counter;
		dirup=p1.directionup;
		ip=p1.counter;
	
		  if ( ( i / qd.nmatr < qd.nmatr-1) && ( i / qd.nmatr>0 ) ) //pawn is neither on his initial line nor the opposite
			    zeroedge=(wedge[i][i+1]<qd.maxedge) && (wedge[i-1][ i]<qd.maxedge);	  
		  else 
			    zeroedge=false;

	    wedge[i][i]=qd.maxedge; // squares are unavailable if there is other pawn
	    if ( i % qd.nmatr < qd.nmatr-1) // there is a square on the right
	    {
		      wedge[i][i+1]=qd.maxedge;
		      wedge[i+1][i]=qd.maxedge;
	    }
	    if ( i % qd.nmatr>0 ) // there is a square on the left
	    {
		      wedge[i][i-1]=qd.maxedge;
		      wedge[i-1][i]=qd.maxedge;
	    }
	    if ( i / qd.nmatr < qd.nmatr-1)  // there is a square ahead
	    {
		      wedge[i][i+qd.nmatr]=qd.maxedge;
		      wedge[i+qd.nmatr][i]=qd.maxedge;
	    }
	    if ( i / qd.nmatr>0 ) // there is a square behind
	    {
		      wedge[i][i-qd.nmatr]=qd.maxedge;
		      wedge[i-qd.nmatr][i]=qd.maxedge;
	    }

	    if (zeroedge) 
	      if (dirup)
	      {
	    	  if (ip==i-qd.nmatr)
		  	          wedge[ip][i+qd.nmatr]=1;
	      }
	      else
	      {
		        if (ip==i+qd.nmatr)
		  	          wedge[ip][i-qd.nmatr]=1;		  		
	      }
	}
	
		// square's or fence's number if there's a mouse situated 0 - in the square, 1 - on the right, 2 - up, -1 - not in the playing field
	public static Point getncells(int x, int y) { // mouse position  =cellwh, ph
		Point nc = new Point();
		nc.y=-1;
		int gamep=(qd.ph+qd.cellwh)*qd.nmatr;
		y = gamep+qd.ph-y; // getting bigger from down to up
        if ( (x<qd.ph) || (y<qd.ph) || (x>gamep+qd.ph-1) || (y>gamep+qd.ph-1) )
    		return nc;
        int xc= (x-qd.ph) / (qd.ph+qd.cellwh);
        int yc= (y-qd.ph) / (qd.ph+qd.cellwh);
        nc.x=xc+yc*qd.nmatr;
        
        if ( ((x-qd.ph) % (qd.ph+qd.cellwh)<qd.cellwh) && ((y-qd.ph) % (qd.ph+qd.cellwh)<qd.cellwh)  )
    		nc.y=0;
        else { 
        	if ( ((x-qd.ph) % (qd.ph+qd.cellwh)>=qd.cellwh) && ((y-qd.ph) % (qd.ph+qd.cellwh)<qd.cellwh)  )
    		nc.y=1;
        	else
            	if ( ((x-qd.ph) % (qd.ph+qd.cellwh)<=qd.cellwh) && ((y-qd.ph) % (qd.ph+qd.cellwh)>=qd.cellwh)  )
            		nc.y=2;
           }
		
        return nc;
	}

	public static int xtopaint(int ncell) {
		 return (ncell % qd.nmatr)*(qd.cellwh+qd.ph)+qd.ph;
	}
	public static int ytopaint(int ncell) {
		 return (qd.nmatr-1-ncell / qd.nmatr)*(qd.cellwh+qd.ph)+qd.ph;
	}
	
	public static pboard setpartitiontopaint(int x1, boolean horizontalfence) { // horizontal fence
		pboard pb=new pboard();
		if (horizontalfence)//(x1+1==x2)  // horizontal fence
			{pb.x=xtopaint(x1);
             pb.y=ytopaint(x1)-qd.ph;
             pb.w=qd.ph+qd.cellwh*2;
             pb.h=qd.ph; }
		else 
            {pb.x=xtopaint(x1)+qd.cellwh;
             pb.y=ytopaint(x1)-qd.ph-qd.cellwh;
             pb.h=qd.ph+qd.cellwh*2;
             pb.w=qd.ph; }
		pb.c1=qd.cpart;
		return pb;
	}

	public static pboard setpawngo(int xn) { 
		pboard pb=new pboard();
	    pb.x=xtopaint(xn); pb.y=ytopaint(xn);
	    pb.w=qd.cellwh; pb.h=qd.cellwh; pb.c1=qd.cpawngo;
		return pb;
	}
	
	public  boolean isrealgotopawnplayer(int xn, player p1, player p2) {// player1 is almost always the one who makes a move  p1== xpawn
		int xp=p1.counter;		
		boolean gotoreal=((xp==xn-1)||(xp==xn+1)||(xp==xn+qd.nmatr)||(xp==xn-qd.nmatr)) && (!(xn==p2.counter)); //!(xn==p2.counter) there's another pawn on the way
        if (gotoreal && (xp==xn-1)) 
        	gotoreal=(xn%qd.nmatr>0); //upper line on the right
        if (gotoreal && (xp==xn+1)) 
        	gotoreal=(xp%qd.nmatr>0); // upper line on the left
		
		if (gotoreal)
			gotoreal=isgotopawn(xn, xp, p1, p2);

		if (!gotoreal)   //jumping over the pawn up
			if (p1.directionup && (p1.counter==p2.counter-qd.nmatr) && (xn==p1.counter+2*qd.nmatr)) 
				gotoreal=isgotopawn(xn-qd.nmatr, xp, p1, p2) && isgotopawn(xn, xp+qd.nmatr, p1, p2);
			
		if (!gotoreal)   //jumping over the pawn down
			if ( (!p1.directionup) && (p1.counter==p2.counter+qd.nmatr) && (xn==p1.counter-2*qd.nmatr) ) 
				gotoreal=isgotopawn(xn+qd.nmatr, xp, p1, p2) && isgotopawn(xn, xp-qd.nmatr, p1, p2);//check if there are any fences
					
		if (gotoreal) {
			p1.counter=xn;
			p1.pmove=false;
			p2.pmove=true;
		}
		return gotoreal;
	}
	public  boolean isrealgotopawn(int xn, player p1, player p2) {//xpawn jumping over the pawn
		boolean gotoreal=false;
		if (p1.pmove)
			gotoreal= isrealgotopawnplayer(xn, p1, p2);
		else
			gotoreal= isrealgotopawnplayer(xn, p2, p1);
		return gotoreal;
	}

	public static boolean isgotopawn(int xn, int xp, player p1, player p2) {
		boolean gotop=true;
	    for (int i=0;i<qd.npartition; i++) {
	    	if (xn==xp+1) {// move on the right
		    	  if (  (p1.partition[i][0]>=0) && (p1.partition[i][0]==p1.partition[i][1]-qd.nmatr) ) //vertically
		    		  gotop=gotop && !( (xp==p1.partition[i][0]) ||  (xp==p1.partition[i][1]) ); 
		    	  if (  (p2.partition[i][0]>=0) && (p2.partition[i][0]==p2.partition[i][1]-qd.nmatr) )
		    		  gotop=gotop && !( (xp==p2.partition[i][0]) ||  (xp==p2.partition[i][1]) ); 
	    	   }
	    	if (xn==xp-1) {//move on the left
		    	  if (  (p1.partition[i][0]>=0) && (p1.partition[i][0]==p1.partition[i][1]-qd.nmatr) )
		    		  gotop=gotop && !( (xn==p1.partition[i][0]) ||  (xn==p1.partition[i][1]) ); 
		    	  if (  (p2.partition[i][0]>=0) && (p2.partition[i][0]==p2.partition[i][1]-qd.nmatr) )
		    		  gotop=gotop && !( (xn==p2.partition[i][0]) ||  (xn==p2.partition[i][1]) ); 
	    	   }
	    	if (xn==xp+qd.nmatr) {//up
		    	  if (  (p1.partition[i][0]>=0) && (p1.partition[i][0]==p1.partition[i][1]-1) )
		    		  gotop=gotop && !( (xp==p1.partition[i][0]) ||  (xp==p1.partition[i][1]) ); 
		    	  if (  (p2.partition[i][0]>=0) && (p2.partition[i][0]==p2.partition[i][1]-1) )
		    		  gotop=gotop && !( (xp==p2.partition[i][0]) ||  (xp==p2.partition[i][1]) ); 
	    	   }
	    	if (xn==xp-qd.nmatr) {//down
		    	  if (  (p1.partition[i][0]>=0) && (p1.partition[i][0]==p1.partition[i][1]-1) )
		    		  gotop=gotop && !( (xn==p1.partition[i][0]) ||  (xn==p1.partition[i][1]) ); 
		    	  if (  (p2.partition[i][0]>=0) && (p2.partition[i][0]==p2.partition[i][1]-1) )
		    		  gotop=gotop && !( (xn==p2.partition[i][0]) ||  (xn==p2.partition[i][1]) ); 
	    	   }

	    	
	    	if (!gotop) break;
	    	}
		return gotop;
	}

	public  void setccoordtopaint(Point nc, pboard[] pb, player p1, player p2, boolean isclick) { // mouse position  =cellwh, ph
		int i, n=0; // n- number of all the fences set + squareswhich need to be redrawn
		
		for (i=0; i<qd.ng; i++) 
			pb[i].x=-10;
		for (i=0; i<qd.npartition; i++) {
		    if (p1.partition[i][0]>=0) {
		    	//System.out.println(p1.partition[i][0]);
		    	pb[n]=setpartitiontopaint(p1.partition[i][0], (p1.partition[i][0]+1==p1.partition[i][1]));
		    	n=n+1;
		    }
		    if (p2.partition[i][0]>=0) {
		    	pb[n]=setpartitiontopaint(p2.partition[i][0], (p2.partition[i][0]+1==p2.partition[i][1]));
		    	n=n+1;
		    }
		}
		
	    switch (nc.y) {
	    // setting mouse's position                  // "number= "+pp.x+" (0-square; 1-on the right; 2-up; -1 not on the field
	    case 0: pb[n].x=xtopaint(nc.x); pb[n].y=ytopaint(nc.x);
			    pb[n].w=qd.cellwh; pb[n].h=qd.cellwh; pb[n].c1=qd.ccell;
		    	++n;
		    	if ((nc.x==p1.counter) || (nc.x==p2.counter) ) {// colouring possible squares to move
		    		int xpawn=nc.x;
			    	int xn=nc.x-qd.nmatr;  //down
		    		if ( (xn>=0) && isgotopawn(xn, xpawn, p1, p2) ) {
		    			pb[n]=setpawngo(xn); ++n;
		    		}
			    	xn=nc.x+qd.nmatr;//up
		    		if ( (xn<qd.ng)  && isgotopawn(xn, xpawn, p1, p2) ) {
		    			pb[n]=setpawngo(xn); ++n;
		    		}
			    	xn=nc.x-1;//left
		    		if ( (xn>=0)  && isgotopawn(xn, xpawn, p1, p2) && (xpawn%qd.nmatr>0) ) {
		    			pb[n]=setpawngo(xn); ++n;
		    		}
			    	xn=nc.x+1;//right
		    		if ( (xn<qd.ng)  && isgotopawn(xn, xpawn, p1, p2)  && (xn%qd.nmatr>0) ) {
		    			pb[n]=setpawngo(xn); ++n;
		    		}

		    	}
		    	break;
	    case 1: if (issetnewpartition(nc.x, false, p1, p2)) {  // 1 setting the fence on the right
	    	    	  pb[n]=setpartitiontopaint(nc.x, false);
	  		    	++n;}
			    break;
	    case 2: if (issetnewpartition(nc.x, true, p1, p2)) {
			          pb[n]=setpartitiontopaint(nc.x, true); // 2-up
		  		    	++n;}
				break;
        default:
            break;
       }		
		// drawing pawns
		pb[n].x=xtopaint(p1.counter)+10;
		pb[n].y=ytopaint(p1.counter)+10;
		pb[n].w=qd.cellwh-20;
		pb[n].h=qd.cellwh-20;
		pb[n].c1=qd.cpawn1;
		++n;
		pb[n].x=xtopaint(p2.counter)+10;
		pb[n].y=ytopaint(p2.counter)+10;
		pb[n].w=qd.cellwh-20;
		pb[n].h=qd.cellwh-20;
		pb[n].c1=qd.cpawn2;			
	}
	// if it's possible to put a fence
	public boolean issetnewpartition(int x, boolean horizontalfence,  player p1, player p2) { //x - fence's square
		boolean newset=(x/qd.nmatr<qd.nmatr-1) && (x%qd.nmatr<qd.nmatr-1);
		
		for (int i=0; i<qd.npartition; i++) {
		    if ( (p1.partition[i][0]>=0) && newset )
		    	newset=auditsetpartition(x, horizontalfence, p1.partition[i][0], p1.partition[i][1]);
		    if ( (p2.partition[i][0]>=0) && newset )
		    	newset=auditsetpartition(x, horizontalfence, p2.partition[i][0], p2.partition[i][1]);
		}
		return newset;
	}

	public boolean setnewpartitionplayer(Point nc, player p1, player p2, int[] le) {
	    boolean setp=false;	    
    	if(p1.cpartition>0)
    		if (issetnewpartition(nc.x, nc.y==2, p1, p2)) {	    		
	    		if (nc.y==2) p1.partition[qd.npartition-p1.cpartition][1]=nc.x+1;	    			
	    		else         p1.partition[qd.npartition-p1.cpartition][1]=nc.x+qd.nmatr;	    			
	    		p1.partition[qd.npartition-p1.cpartition][0]=nc.x;
	    		
	    		setp=moveallowed(p1, le);
	    		if (setp) setp=moveallowed(p2, le);
	    		if (setp) {
	    			p1.pmove=false;
	    			p2.pmove=true;
	    			p1.cpartition--;
	    		} else { 
	    			p1.partition[qd.npartition-p1.cpartition][0]=-10;
	    			p1.partition[qd.npartition-p1.cpartition][1]=-10;
	    		}	    				    	 
	    }
		return setp;
	}
	
	public boolean setnewpartition(Point nc, player p1, player p2, int[] le) {
	    boolean setp=false;	    
	    if ( p1.pmove) setp=setnewpartitionplayer(nc, p1, p2, le);
	    else           setp=setnewpartitionplayer(nc, p2, p1, le);   
		return setp;
	}
	
	public boolean auditsetpartition(int x, boolean horizontalfence,  int x1, int x2) { //x1, x2 - where the fence is set
		boolean newset = true;
		if (horizontalfence)  { //setting horizontal fence
			if (x1+1==x2) // horizontal fence
				 newset=!(  (x==x1) || (x==x2)|| (x+1==x1) || (x+1==x2)  );
			else newset=!(x==x1); // vertical fence
	    } else
			if (x1+1==x2) // horizontal fence
				newset=!(x==x1); 
			else newset=!(  (x==x1) || (x==x2)|| (x+qd.nmatr==x1) || (x+qd.nmatr==x2)  ); // vertical fence
		return newset;
	}
	
	//PC is making a move
	public static int rnd(int max) // return diapason [0;max]
	{	return (int) (Math.random() * ++max);
	}
	public boolean setnewmovecomp(Point bsize, player p1, player p2, int[] le) {
		boolean newmove=false;
		Point nc= new Point(-1,-1);
		
		for (int end=0; end<qd.maxedge; end++) {
			if  (rnd(100) > 7)  {
				for (int k=0; k<5 ; k++) {
    			nc = getncells(rnd(bsize.x), rnd(bsize.y) );
			    if (nc.y==0) break;
			   }}
			else {
				nc.x=p2.counter-qd.nmatr;
				if (nc.x>=0) nc.y=0;
				else nc.y=-1;
			}
	        if ((nc.y==1)||(nc.y==2)) //setting a fence
	        	newmove=setnewpartition(nc, p1, p2, le);	        
	        if (nc.y==0) //moving a pawn
	        	newmove=isrealgotopawn(nc.x, p1, p2); 			
			if (newmove) break; 
		}
		return newmove;
	}
	
	public boolean moveallowed (player pl, int[] le) {// check if the player can reach the other side
		Dijkstra( pl.counter, le);
		int endboard=0;
		if (pl.directionup) {
			endboard=qd.ng-qd.nmatr;		
		}				
		for (int i=0; i<qd.nmatr; i++) {
			if (le[i+endboard]<qd.maxedge)
				return true;
		}
			
		return false;
	}

}
class player {
	int[][] partition = new int [qd.npartition][2];
	boolean directionup; // pawns direction
	int counter; // pawn
	int cpartition;
	boolean pmove;
	boolean comp;  //playing with a pc 
	public player (boolean directionup) {
		this.directionup = directionup;
		initplayer(directionup);
		comp=false;
	}
	public void initplayer(boolean dup) {
		for (int i=0; i<qd.npartition; i++)
		{ this.partition[i][0]=-1;        // number of the square (from 0 to 80)
			this.partition[i][1]=-1;
		}
		this.directionup=dup;
		if (dup)
			counter=qd.GetNCell(1, qd.nmatr/2 +1); // setting initial pawns location
		else
			counter=qd.GetNCell(qd.nmatr, qd.nmatr/2 +1);
	}
}


