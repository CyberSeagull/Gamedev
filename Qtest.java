import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Qtest {
	player p1 = new player(true);
	player p2 = new player(true);
	qm game = new qm();
	qd qdate = new qd();

	@Test
	void test() {
		game.initialplayers(p1, p2);
		p2.counter=12;
		p1.counter=7;
 	   game.setedge(p1, p2);
	   assertTrue(game.moveallowed(p1, qdate));

	   /*for (int i=0; i<qd.ng; i++) {
			System.out.println();
			for (int j=0; j<qd.ng; j++)
				System.out.print(game.wedge[i][j]+" ");}
		System.out.println("\n -----------Distance between nodes-----------------");
		for (int i=0; i<qd.ng; i++) 
			   System.out.print( qdate.le[i]+" ");*/

	}

}
