import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class hlist {

    List<hobject> m_objects = new ArrayList<>();
    double m_width, m_height;    
    int m_frames_per_second;
    
    public hlist(double width, double height, int frames_per_second) {
        m_width = width;
        m_height = height;
        m_frames_per_second = frames_per_second;
    }
    
    public double getWidth() {
        return m_width;
    }
    
    public double getHeight() {
        return m_height;
    }
    
    public int getFPS() {
        return m_frames_per_second;
    }
    
    public void add(hobject o) {
        m_objects.add(o);
    }
    
    public void remove(hobject o) {
        m_objects.remove(o);
    }
    
    public hobject collision(hobject o) {
        for(hobject o2:m_objects) {
            if (o2!=o && o.collision(o2)) return o2;
        }
        return null;
    }

    public void update() {
        double delta_t = 1.0 / m_frames_per_second;        
        for(hobject go:m_objects) { go.update(this, delta_t);     
	 //   System.out.println("------hun_x= "+go.getX()+"  hun_y= "+go.getY()+"  hun_y= "+go.m_x);
        }

        
    }
        
    public void draw(Graphics2D g) {
        for(hobject go:m_objects) go.draw(g);
    }
}
