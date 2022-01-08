package pwclient;
import java.awt.Color;
import java.awt.Graphics2D;

public class food  extends gobject {

	public food(PVector pos, double r)  {
        position = pos;//new PVector(x,y);
        poscl    = new PVector(pos.x,pos.y);
        m_r = r;
        m_color = Color.yellow;
        Visibleobject=false;
    }

	@Override
	public void update(glist game, double delta_t) {
		
		Visibleobject = (GetLengthtotheWalls(game.getWidth(), game.getHeight(), position, m_r, game.m_dx, game.m_dy )>0);
	}
    public void draw(Graphics2D g) {
    	if (Visibleobject) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.setColor(m_color);
        gg.fillOval((int)(position.x-m_r/2), (int)(position.y-m_r/2), (int)m_r, (int)m_r);
        gg.dispose();     
    	}
	}
}
