package pwclient;
import java.awt.Graphics2D;

public class gamer extends gobject {
   public gamer(PVector pos, double r, Color mycolor)  {
        position = pos;//new PVector(x,y);        
        poscl    = new PVector(pos.x,pos.y);
        m_color=mycolor;
        m_r = r;
    }
	@Override
	public void update(glist game, double delta_t) {
		
		Visibleobject = (GetLengthtotheWalls(game.getWidth(), game.getHeight(), position, m_r, game.m_dx, game.m_dy )>0);		
	}
	@Override
	public void draw(Graphics2D g) {
		Graphics2D gg = (Graphics2D) g.create();
        	gg.setColor(m_color);
        	gg.fillOval((int)(position.x-m_r/2), (int)(position.y-m_r/2), (int)m_r, (int)m_r);
//    		System.out.println("!! m_x= "+m_x+"  m_y= "+m_y+" m_r= "+m_r+"  ");
        	gg.dispose(); 
		
	}

}
