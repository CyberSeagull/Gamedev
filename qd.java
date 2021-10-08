// qd stands for quoridor data


public class qd {
	public static final int ng   =81;   // 81 squares
	public static final int nmatr=9; // 9  squares per each line
    public static final int npartition=10; // 10 fences per one player
    public static final int maxedge=100000; // setting vertex = infinity (here 100000)

    public int[] le        = new int [qd.ng];     // total weight of all vertexes from the initial node
    public boolean[] flag  = new boolean [qd.ng]; // check if the node was visited

    public static int GetNCell(int i, int j) { 
		return (nmatr)*(i-1)+j-1;
	}//squares coordinates
}

