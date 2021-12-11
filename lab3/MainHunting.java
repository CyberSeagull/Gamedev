
import java.awt.Color;


public class MainHunting {
	public static void main(String[] args) throws Exception {

        hlist game = new hlist(800,600, 25);
        hobject hunter1 = new hunter(125,125 ,24, Color.WHITE,0);
        hobject wolf1 = new wolf(200, 200, 22,Color.DARK_GRAY, 0);
        
        hobject hare1 = new hare(555, 400, 18,Color.gray, 0);
        

        
        game.add(hunter1);
        game.add(wolf1);
        game.add(hare1);
        hwindow.newWindow(game);		
	}

}
