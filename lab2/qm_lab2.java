import java.awt.Point;

// qm stands for quoridor model
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
	
	public void setedge(player p1, player p2) {
    	int i, j;// ip,
      //  boolean dirup, zeroedge;
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
		    // pawns     first one goes from his side (square 5,3) to the other (square 1,x)
		    // pawns     the other one goes from his side (square 1,3) to the other (square 5,x)
		/*
		i=p2.counter;
		dirup=p1.directionup;
		ip=p1.counter;
		  if ( ( i / qd.nmatr < qd.nmatr-1) && ( i / qd.nmatr>0 ) ) //pawn is neither on its initial line nor the opposite
			    zeroedge=(wedge[i][i+1]<qd.maxedge) && (wedge[i-1][ i]<qd.maxedge);	  
		  else  zeroedge=false;
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
	    */
	}

	//===============================================================================================
	// square's or fence's number if the mouse is located there 0 - on the square 1- on the right 2- up -1 - not in the playing field
	public static Point getncells(int x, int y) { // mouse position  =cellwh, ph
		Point nc = new Point();
		nc.y=-1;
		int gamep=(qd.ph+qd.cellwh)*qd.nmatr;
		y = gamep+qd.ph-y; //y is getting bigger upwards
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
	
	public  boolean isrealgotopawnplayer(int xn, player p1, player p2) {// player1 is almost always the one who makes a move  p1== xpawnn except for jumping over the pawn
		int xp=p1.counter;		
		boolean gotoreal=((xp==xn-1)||(xp==xn+1)||(xp==xn+qd.nmatr)||(xp==xn-qd.nmatr)) && (!(xn==p2.counter)); //!(xn==p2.counter) - there's another pawn on the way
        if (gotoreal && (xp==xn-1))
        	gotoreal=(xn%qd.nmatr>0); //upper line on the right
        
        if (gotoreal && (xp==xn+1))
        	gotoreal=(xp%qd.nmatr>0); // upper line on the left
        
        if (gotoreal && (xp==xn+qd.nmatr))
        	gotoreal=(xn>=0); //down
        if (gotoreal && (xp==xn-qd.nmatr))
        	gotoreal=(xn<qd.ng); //up
		
		if (gotoreal)
			gotoreal=isgotopawn(xn, xp, p1, p2);

		if (!gotoreal)   //jumping over the pawn up
			if ( (p1.counter==p2.counter-qd.nmatr) && (xn==p1.counter+2*qd.nmatr) )
				gotoreal=isgotopawn(xn-qd.nmatr, xp, p1, p2) && isgotopawn(xn, xp+qd.nmatr, p1, p2);			
		
		if (!gotoreal)   //jumping over the pawn down
			if ( (p1.counter==p2.counter+qd.nmatr) && (xn==p1.counter-2*qd.nmatr) )
				gotoreal=isgotopawn(xn+qd.nmatr, xp, p1, p2) && isgotopawn(xn, xp-qd.nmatr, p1, p2); 

		if (!gotoreal)   //jumping over the pawn on the left
			if ( (p1.counter==p2.counter+1) && (xn==p1.counter-2) && (xn/qd.nmatr==p1.counter/qd.nmatr) )
				gotoreal=isgotopawn(xn+1, xp, p1, p2) && isgotopawn(xn, xp-1, p1, p2);
		if (!gotoreal)   //jumping over the pawn on the right
			if ( (p1.counter==p2.counter-1) && (xn==p1.counter+2) && (xn/qd.nmatr==p1.counter/qd.nmatr) )
				gotoreal=isgotopawn(xn-1, xp, p1, p2) && isgotopawn(xn, xp+1, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally up and right the opponent is up
			if ( (p1.counter==p2.counter-qd.nmatr) && (xn==p1.counter+1+qd.nmatr) && ((xn-qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) ) // same line
				gotoreal=isgotopawn(xn-1, xp, p1, p2) && isgotopawn(xn-1, xn, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally up and left the opponent is up
			if ( (p1.counter==p2.counter-qd.nmatr) && (xn==p1.counter-1+qd.nmatr) && ((xn-qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) )
				gotoreal=isgotopawn(xn+1, xp, p1, p2) && isgotopawn(xn, xn+1, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally down and right the opponent is down
			if ( (p1.counter==p2.counter+qd.nmatr) && (xn==p1.counter+1-qd.nmatr) && ((xn+qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) ) // same line
				gotoreal=isgotopawn(xn-1, xp, p1, p2) && isgotopawn(xn-1, xn, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally down and left the opponent is down
			if ( (p1.counter==p2.counter+qd.nmatr) && (xn==p1.counter-1-qd.nmatr) && ((xn+qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) )
				gotoreal=isgotopawn(xn+1, xp, p1, p2) && isgotopawn(xn, xn+1, p1, p2);
		
		if (!gotoreal)   //jumping over the pawn diagonally up and right the opponent is on the right
			if ( (p1.counter==p2.counter-1) && (xn==p1.counter+1+qd.nmatr) && ((xn-qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) ) // same line
				gotoreal=isgotopawn(xn-qd.nmatr, xp, p1, p2) && isgotopawn(xn-qd.nmatr, xn, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally down and right the opponent is on the right
			if ( (p1.counter==p2.counter-1) && (xn==p1.counter+1-qd.nmatr) && ((xn+qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) )
				gotoreal=isgotopawn(xn+qd.nmatr, xp, p1, p2) && isgotopawn(xn, xn+qd.nmatr, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally up and left the opponent is on the left
			if ( (p1.counter==p2.counter+1) && (xn==p1.counter-1+qd.nmatr) && ((xn-qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) ) // same line
				gotoreal=isgotopawn(xn-qd.nmatr, xp, p1, p2) && isgotopawn(xn-qd.nmatr, xn, p1, p2);
		if (!gotoreal)   //jumping over the pawn diagonally down and left  the opponent is on the left
			if ( (p1.counter==p2.counter+1) && (xn==p1.counter-1-qd.nmatr) && ((xn+qd.nmatr)/qd.nmatr==p1.counter/qd.nmatr) )
				gotoreal=isgotopawn(xn+qd.nmatr, xp, p1, p2) && isgotopawn(xn, xn+qd.nmatr, p1, p2);
        gotoreal=gotoreal && (xn>=0) && (xn<qd.ng);

		if (gotoreal) {
			p1.counter=xn;
			++p1.countpawn;
			p1.pmove=false;
			p2.pmove=true;
		}
					
		return gotoreal;
	}
	public  boolean isrealgotopawn(int xn, player p1, player p2) {//movepawn - making a move
		boolean gotoreal=false;
		if (p1.pmove)
			gotoreal= isrealgotopawnplayer(xn, p1, p2);
		else
			gotoreal= isrealgotopawnplayer(xn, p2, p1);
		return gotoreal;
	}

	public boolean isgotopawn(int xn, int xp, player p1, player p2) {
		boolean gotop=true;
	    for (int i=0;i<qd.npartition; i++) {
	    	if (xn==xp+1) {//move on the right
		    	  if (  (p1.partition[i][0]>=0) && (p1.partition[i][0]==p1.partition[i][1]-qd.nmatr) )
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
		int i, n=0; //n- number of all the fences set + squares which need to be redrawn
		
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
	    case 1: if (issetnewpartition(nc.x, false, p1, p2)) {  // 1 - setting the fence on the right
	    	    	  pb[n]=setpartitiontopaint(nc.x, false);
	  		    	++n;}
			    break;
	    case 2: if (issetnewpartition(nc.x, true, p1, p2)) {
			          pb[n]=setpartitiontopaint(nc.x, true); // 2 - up
		  		    	++n;}
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
	    		setedge(p1, p2);   //2021 
	    		setp=moveallowed(p1, le);
	    		setedge(p2, p1);   //2021 
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

	public int minway (player pl, player p2, int[] le) {//if the player can reach the opposite side
		setedge(pl, p2);
		Dijkstra( pl.counter, le);
		int endboard=0;
		if (pl.directionup) {
			endboard=qd.ng-qd.nmatr;		
		}		
		int x=qd.maxedge;
		for (int i=0; i<qd.nmatr; i++) {
			if (le[i+endboard]<x)
				x=le[i+endboard];
		}
		return x;
	}

	public boolean moveallowed (player pl, int[] le) {//if the player can reach the opposite side

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
	//	<<<<<<<<<<<<<<<<<<<<<< the opponent makes a move
	public boolean setmoveenemy(String sg, player p1, player p2, int[] le) {
		boolean newmove=false;
        String scoord=sg.substring(sg.indexOf(qd.stremty)+1);
        String scom=sg.substring(0,sg.indexOf(qd.stremty));
        String strpawn="abcdefghi";
        String stredge="stuvwxyz";
        String strdigit="123456789";
        
		if (!(scom.length()==4) || (scoord.length()<2)) return newmove; 
		Point nc= new Point(0,-1); // moving a pawn or setting a fence
		
		if ( (scom.contains(qd.strwall)) && (scoord.length()==3)) // checking a fence
			if (scoord.indexOf('h')==2 )  nc.y=2;  // checking fences 0 - pawn 1- up 2- on the right
		else
			if (scoord.charAt(2)=='v')  nc.y=1;
           	if (scom.contains(qd.strjump) || scom.contains(qd.strmove))
			nc.y=0;
		if (nc.y<0) return newmove;
		int j= (nc.y==0) ? strpawn.indexOf(scoord.charAt(0)) : stredge.indexOf(scoord.charAt(0)); 
		int i= strdigit.indexOf(scoord.charAt(1)); 
        if ((i<0) || (j<0)) return newmove; 
        
        nc.x=qd.nmatr*(qd.nmatr-i-1)+j;        
		if (nc.y>0) nc.x=nc.x-qd.nmatr; //edge
		
		//System.out.println(scom.length()+"  "+scom+"="+scoord+"  "+scoord.length()+" nc.x="+nc.x+" nc.y="+nc.y);
        if (  !(p1.pmove && p1.comp || p2.pmove && p2.comp) )  // it's not an ai who makes a move
        	newmove = (nc.y>0) ? setnewpartition(nc, p1, p2, le) : isrealgotopawn(nc.x, p1, p2);
	   return newmove;  
	}
	
	public static int rnd(int max) // return range [0;max]
	{	return (int) (Math.random() * ++max);
	}

	//<<<<<<<<<<<< ai's making a move <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	public boolean setnewmovecomp(player p1, player p2, int[] le) {
		//System.out.println("--------------------------------------------------------------------------------"+ " p1c="+p1.counter+" p2c="+p2.counter+" p1move="+p1.pmove);		
		return  newmoveminimax(p1, p2, le, 0, -qd.maxedge, qd.maxedge)<qd.maxedge 			
	}
    public boolean  moveplayer(Point nc, player p1, player p2, int[] le) // player's making a move
    {
    	return (nc.y==0) ? isrealgotopawn(nc.x, p1, p2) : setnewpartition(nc, p1, p2, le);
    }

	public String setstringmove(Point nc, int pc) {
		String strmove="";
        String strpawn="ABCDEFGHI";
        String stredge="STUVWXYZ";
        String strdigit="123456789";
		int ncx=nc.x;
		if (nc.y>0) ncx=nc.x+qd.nmatr; //edge
		int j= ncx % qd.nmatr; 
		int i= qd.nmatr-(ncx/qd.nmatr+1);
	//	System.out.println(" i= "+i+" j=  "+j+" nc.x="+nc.x+" nc.y="+nc.y);
		if (nc.y==0)
			strmove=((ncx==pc+1) || (ncx==pc-1) || (ncx==pc+qd.nmatr) || (ncx==pc-qd.nmatr)) ? qd.strmove : qd.strjump;
		else strmove=qd.strwall;
		strmove=strmove+qd.stremty;
	//	System.out.println(" i= "+i+" j=  "+j+" nc.x="+nc.x+" nc.y="+nc.y+"   "+strmove);
		if (nc.y==0) strmove=strmove+strpawn.substring(j,j+1)+strdigit.substring(i, i+1);
		if (nc.y>0) {
			 strmove=strmove+stredge.substring(j,j+1)+strdigit.substring(i, i+1);
			 if (nc.y==2) strmove=strmove+'h';
			 else         strmove=strmove+'V'; 
		}
	   return strmove;  
	}

	public void copyplayer(player source, player dest) {
		dest.counter    =source.counter;
		dest.cpartition =source.cpartition;
		dest.pmove      =source.pmove;
		dest.directionup=source.directionup;
		dest.countpawn  =source.countpawn;
		dest.comp       =source.comp;
		for (int i = 0; i < source.partition.length; i++) 
	        System.arraycopy(source.partition[i], 0, dest.partition[i], 0, source.partition[i].length);		
	}

	//p1 is the one who makes the first move
	public int newmoveminimax(player p1, player p2, int[] le, int recursiveLevel, int alpha, int beta) { 
		Point pway= new Point(-1,0);
		int testm=qd.maxedge;
		int minmax = p1.pmove ? qd.minval : qd.maxval;
		//System.out.println("             recursiveLevel="+recursiveLevel+" p1.pmove="+p1.pmove+" p1.up="+p1.directionup+" p1.counter="+p1.counter+" p2.counter="+p2.counter+" minmax="+minmax+" p1.comp="+p1.comp+" #### countpawn="+p1.countpawn);
		boolean movecomp = (p1.comp && p1.pmove);
		if (recursiveLevel >= qd.minmaxlevel *2) {
			int mm2= p2.pmove ? minway(p1, p2, le) : minway(p2, p1, le); // the lowest level for the opponent
			//System.out.println("MAX LEVEL     "+p1.pmove+" recursiveLevel= "+recursiveLevel+"===================== "+" minway="+mm2);
			return mm2; //p2.pmove ? minway(p1, p2, le) : minway(p2, p2, le); // the lowest level
		}
		player dp1 = new player(p1.directionup);
		player dp2 = new player(p2.directionup);
		Point nc= new Point(0,-1); // moving pawn or setting fence
		int playerup=-1; // moving up
	    for (int i = 0; i < 11; i++) { 
		//for (int i = 0; i < 4; i++) {
         //   if (p1.pmove) {  
	    	copyplayer(p1, dp1); copyplayer(p2, dp2);
	    //	} else {copyplayer(p2, dp1); copyplayer(p1, dp2); }            
			int pcounter=p1.pmove ? p1.counter: p2.counter;
			if (p1.pmove) playerup=p1.directionup ? 1 : -1;
			else playerup=p2.directionup ? 1 : -1;
			
			switch (i) {    	
	    	case 0: nc.x=pcounter+  playerup*qd.nmatr; break; // up
	    	case 1: nc.x=pcounter+2*playerup*qd.nmatr; break; // up jump
	    	case 2: nc.x=pcounter-1; break; // left
	    	case 3: nc.x=pcounter+1; break; // right
	    	case 4: nc.x=pcounter-2; break; // left jump
	    	case 5: nc.x=pcounter+2; break; // right jump
	    	case 6: nc.x=pcounter-  playerup*qd.nmatr; break; // down
	    	case 7: nc.x=pcounter-2*playerup*qd.nmatr; break; // down jump
			} 	    	
	    	nc.y=0; //moving a pawn
	    	if (i>7) { // checking fences  0 - pawn 1- on the right 2- up -1 - not in the playing field
	    		pcounter=p1.pmove ? p2.counter: p1.counter;
	    	if ( (p1.countpawn>1) && ( (p1.pmove ? p1.cpartition: p2.cpartition) >0) ) {
	    		// playerup  1 - moving down -1 - moving upwards           
                if (rnd(7-p1.countpawn)>7) continue;     
	    		nc.x=(playerup==-1) ? pcounter-i/10 : pcounter-i/10-qd.nmatr;//-  playerup*qd.nmatr;
	    	    nc.y=2-i % 2; 
	    	}else continue;
	    	} 
	    	if (moveplayer(nc, dp1, dp2, le)) {
		    	testm=	newmoveminimax(dp1, dp2, le, recursiveLevel+1, alpha, beta);
                if ((testm >= minmax && p1.pmove) ||  (testm <=minmax && p2.pmove)) // choosing the best one if the conditions are equal
	               {  if ((testm==minmax) && ( (pway.x>=0) && (pway.x<qd.ng) && (pway.y==0) ) && movecomp ) { 
	            	  int minnew = p1.pmove ? minway(p1, p2, le) : minway(p2, p1, le);
	            	  int pawn = p1.counter;
	            	  p1.counter=pway.x;
	            	  int minold =  p1.pmove ? minway(p1, p2, le)+1 : minway(p2, p1, le)+1;
	            	  p1.counter=pawn;
	            //       System.out.println("? = ?  "+i+" recursiveLevel= "+recursiveLevel+"  nc.x= "+nc.x+"  pcounter= "+pcounter+"  pway.x= "+pway.x+"  minmax= "+minmax);
	            	  if (minnew<minold) {minmax = testm; pway.x=nc.x;pway.y=nc.y;} } 
                	  else  {
	                   minmax = testm;  
	                   pway.x=nc.x;
	                   pway.y=nc.y;
                	}
	               if (movecomp)
	                alpha=Math.max(alpha, testm);
	                else
	                  beta=Math.min(beta, testm);
	               if (beta < alpha) break; 
	               }			
			}
	    }
	    
	    if (pway.x < 0 || pway.x > 80)  {//NOT_INITIALIZED
      	  //System.out.println("NOT_INITIALIZED  pway.x="+pway.x+" p1c="+p1.counter+" p2c="+p2.counter+" p1move="+p1.pmove);
	        return p2.pmove ? minway(p1, p2, le) : minway(p2, p1, le);
	    }
		//making a move after choosing the best one
	    if (recursiveLevel == 0)
	    {   String strmovecomp=setstringmove(pway, p1.pmove ? p1.counter: p2.counter);
            System.out.println(strmovecomp);
	    	moveplayer(pway, p1, p2, le);	    	
	    }
	    return minmax;
	}
	
}
class player {
	int[][] partition = new int [qd.npartition][2];
	boolean directionup; // pawns direction
	int counter; // pawn
	int countpawn=0;
	int cpartition;
	boolean pmove;
	boolean comp;
	public player (boolean directionup) {		
		this.directionup = directionup;
		initplayer(directionup);
		comp=false;
	}
	public void initplayer(boolean dup) {			
	    for (int i=0; i<qd.npartition; i++)
	    { this.partition[i][0]=-10;        // number of the square (from 0 to 80)
	      this.partition[i][1]=-10;
	      this.countpawn=0;
	    }
	    cpartition=qd.npartition;
	    pmove=dup;
	    this.directionup=dup;
		if (dup)
     	   counter=qd.GetNCell(1, qd.nmatr/2 +1); // setting initial pawns location
		else	
	       counter=qd.GetNCell(qd.nmatr, qd.nmatr/2 +1);
	}
}
