import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class hunter extends hobject {
	Color m_color=Color.green;
    public static final int M_STEERING = 0;
    public static final int M_THROTTLE = 1;
    public static final int M_BRAKE = 2;
    public static final int M_SHOT  = 3;
	
    int countball=77; // shots
    boolean keyboardState[] = new boolean[KeyEvent.KEY_LAST];
    boolean drawball = false;
    
    // physics:
    double m_alpha;     // rotation
    double m_speed=0;
    double m_max_velocity = 150;
    double m_min_velocity = -100;
    
  
    public hunter(double x, double y, double r, Color mycolor, double alpha) throws Exception {
        position = new PVector(x,y);        
        m_color=mycolor;
        m_x = x;
        m_y = y;
        m_r = r;
        m_alpha = alpha;
        
        
        SetKeyState();
        
    }
    public static final int key_accelerate = KeyEvent.VK_UP;
    public static final int key_brake = KeyEvent.VK_DOWN;
    public static final int key_left = KeyEvent.VK_LEFT;
    public static final int key_right = KeyEvent.VK_RIGHT;
    
    public  void  SetKeyState() {
    	for(int i = 0;i<KeyEvent.KEY_LAST;i++) keyboardState[i] = false;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
            new KeyEventDispatcher() {
                public boolean dispatchKeyEvent(KeyEvent ke) {
                   synchronized (hunter.class) {
                        switch (ke.getID()) {
                        case KeyEvent.KEY_PRESSED:
                            keyboardState[ke.getKeyCode()] = true;
                            //if (keyboardState[key_left]) System.out.println("pressed: " + keyboardState[key_left]);
                            break;

                        case KeyEvent.KEY_RELEASED:
                            keyboardState[ke.getKeyCode()] = false;
                            break;
                        }
                        return false;
                    }
                }
            });    
    }

    
    public  void updatek(double keycontrol[]) {
        keycontrol[M_STEERING] = 0;
        keycontrol[M_THROTTLE] = 0;
        keycontrol[M_BRAKE] = 0; 
	keycontrol[M_SHOT]  = 0;
        if (keyboardState[key_left] && !keyboardState[key_right]) keycontrol[M_STEERING] = -1;
        if (keyboardState[key_right] && !keyboardState[key_left]) keycontrol[M_STEERING] = +1;
        if (keyboardState[key_accelerate]) keycontrol[M_THROTTLE] = 1;
        if (keyboardState[key_brake]) keycontrol[M_BRAKE] = 1;
	if (keyboardState[key_space]) keycontrol[M_SHOT] = 1;
     //   System.out.println("key_left " + keyboardState[key_accelerate]+" j "+ keycontrol[1]);                
    }
      
    
    public void update(hlist game, double delta_t) {
        double keycontrol[] = {0,0,0,0};
        updatek(keycontrol);
      //  acceleration = new PVector(0,0);
        // 
        double old_x = m_x;
        double old_y = m_y;
        double old_angle = m_alpha;
   
        
        // update velocity, position and angle:
        double brake_strength = (m_speed>0 ? 150:100);
        double acceleration = keycontrol[M_THROTTLE]*100 - 
                              keycontrol[M_BRAKE]*brake_strength;
        m_speed*=0.99;   // drag
        m_speed += acceleration * delta_t;
        if (m_speed>m_max_velocity) m_speed = m_max_velocity;
        if (m_speed<m_min_velocity) m_speed = m_min_velocity;        
        m_x += Math.cos(m_alpha)*m_speed * delta_t;
        m_y += Math.sin(m_alpha)*m_speed * delta_t;
        double turning_rate = keycontrol[M_STEERING]*m_speed;
        m_alpha+=turning_rate*delta_t/70;
    //    System.out.println("!! m_speed= "+m_speed+"  brake= "+brake_strength+" acceleration= "+acceleration+"  "+Math.sin(m_alpha));     
        
         position.x = m_x;        
        position.y = m_y;        
        drawball=(keycontrol[M_SHOT]==1) && (countball>0);
        if (drawball) countball--;
        // check for collisions:
        double xy = GetLengthtotheWalls(game.getWidth(), game.getHeight(), position, m_r, game.m_dx, game.m_dy );
        if (  (xy<0)  ) //  
        {
            m_x = old_x;
            m_y = old_y;
            m_alpha = old_angle;
            m_speed = 0;

        }
        ;

        
    }
    // draw circle  
    public void draw(Graphics2D g) {
        Graphics2D gg = (Graphics2D) g.create();
        gg.setColor(m_color);
        gg.translate(m_x, m_y);
        gg.rotate(m_alpha);
        gg.translate(-m_x,-m_y);
        gg.fillOval((int)(m_x-m_r/2), (int)(m_y-m_r/2), (int)m_r, (int)m_r);
        gg.setColor(Color.red);
        double lengthshot=drawball ? m_r*2 : m_r/2;
        gg.fillRect((int)(m_x), (int)(m_y), (int)(lengthshot), 2);
//        gg.fillRect((int)(m_x), (int)(m_y), (int)(m_r/2), 2);
        gg.dispose();        
    }
    
    
}
