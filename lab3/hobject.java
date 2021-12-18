import java.awt.Graphics2D;

public abstract class hobject {
    double m_x, m_y, m_r;
    double senseradius=0;
    double ominspeed=1;
    double omaxspeed=3;
    boolean delobj=false;

  	PVector position;// = new PVector();
    
    public abstract void update(hlist game, double delta_t);
    public abstract void draw(Graphics2D g);
    public abstract extentcollision getCollisionBox();

    
    public double getX() {
        return m_x;
    }
    
    public double getY() {
        return m_y;
    }

    public double getRadius() {
        return m_r;
    }

    public boolean collision(hobject o) {
        if (getCollisionBox()==null || o.getCollisionBox()==null) return false;
        return extentcollision.CircleCollision(getCollisionBox(), o.getCollisionBox());
    }
}
