import java.util.ArrayList;
import java.util.List;

public class glist {
    List<gclient> m_clients = new ArrayList<>();
    public boolean stopstart=false;
    int m_width, m_height;    
    int m_dx=10, m_dy=30;    
    int m_frames_per_second;
    int countsectimeoflife=77;
    int r_width;
    int r_height;
    public glist(int width, int height, int frames_per_second) {
        m_width = width;
        m_height = height;
        r_width = width-2*m_dx;
        r_height = height-2*m_dy;
        
        m_frames_per_second = frames_per_second;
    }

}
