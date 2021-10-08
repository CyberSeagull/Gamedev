// qm stands for quoridor model
//model is not finished yet

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

	public void initialplayers(player p1, player p2) { // initializing players for each new game
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
	public boolean moveallowed (player pl, qd qdate) {// check if the player can reach the other side
		Dijkstra( pl.counter, qdate.le);
		int endboard=0;
		if (pl.directionup) {
			endboard=qd.ng-qd.nmatr;		
		}				
		for (int i=0; i<qd.nmatr; i++) {
			if (qdate.le[i+endboard]<qd.maxedge)
				return true;
		}
			
		return false;
	}

}
class player {
	int[][] partition = new int [qd.npartition][2];
	boolean directionup; // pawns direction
	int counter; // pawn
	public player (boolean directionup) {
		this.directionup = directionup;
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


