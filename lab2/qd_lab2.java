import java.awt.Color;
import java.awt.Point;

// qd stands for quoridor data

class pboard {
	int x,y; // start painting a fence or a square
	int w,h; // size of a fence or a square
	Color c1;
}

public class qd {
	public static final int ng   =81;   // 81 squares
	public static final int nmatr=9; // 9  squares per each line
    public static final int npartition=10; // 10 fences per one player
    public static final int maxedge=100000; // setting vertex = infinity (here 100000)
    public static final int maxval=maxedge; // max value for the minimax
    public static final int minval=0; // min value for the minimax
    public static final int minmaxlevel=1; // level for the minimax

    // consol input output  
    public static final String strwhite = "white"; 
    public static final String strblack = "black"; 
    public static final String strmove  = "move"; 
    public static final String strjump  = "jump"; 
    public static final String strwall  = "wall"; 
    public static final String strexit  = "exit"; 
    public static final String stremty  = " "; 
                               
    //String strp   = ""; //coordinates
    public int startgame =0;// 0 - waiting for the game to start  1-game has begun 2-3 game has ended
    // board    
    public static final Color cpart  =Color.GRAY; // fence's colour
    public static final Color ccell  =Color.LIGHT_GRAY; // chosen square colour
    public static final Color cpawn1 =new Color(17, 120, 100); // first pawn's colour
    public static final Color cpawn2 =new Color(128,0,0); // second pawn's colour
    public static final Color cpawngo=Color.green; // colour of the square to which the pawn goes
    public static final Color cboard =new Color(155,196,226); // iniyial background
    public static final Color ccells =new Color(255,228,181); // all squares' colours
    public static final int cellwh=40; // cell's size
    public static final int ph=20;    //fence's size
    public Point mousexy = new Point(-1,-1); // mouse's coordinates
    
    public int[] le        = new int [qd.ng];     // total weight of all vertexes from the initial node
    public boolean[] flag  = new boolean [qd.ng]; // check if the node was visited
    
    
    public pboard[] pb = new pboard [qd.ng];
	
    public qd () {				
		for (int i=0; i<qd.ng; i++) 
			pb[i]= new pboard();
	}
    
    public static int GetNCell(int i, int j) { 
    	return (nmatr)*(i-1)+j-1;		
	}//squares coordinates
  
}
