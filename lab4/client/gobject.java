package pwclient;
import java.awt.Graphics2D;

public class gobject {
    PVector position;
  	double m_x, m_y, m_r;
    double senseradius=0;
    double ominspeed=1;
    double omaxspeed=3;
    boolean delobj=false;
  
  public abstract void update(glist game, double delta_t);
    public abstract void draw(Graphics2D g);

    public double getRadius() {
        return m_r;
    }

}
