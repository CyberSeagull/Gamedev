
public class PVector {

    double x,y;
    public PVector(double x_, double y_) {
        x=x_;
        y=y_;
    }
        
    void add(PVector v) {
        x+=v.x;
        y+=v.y;
    }

    public  void sub(PVector v) {
        x-=v.x;
        y-=v.y;
    }

    public static PVector sub(PVector v, PVector subv) {
    	PVector res = new PVector(v.x, v.y); 
        res.sub(subv);
        return res;
    }
    
    void mult(double n) {
    	   x *= n;
    	   y = y * n;
    	 }

    void div(double n) {
    	 if (n != 0) { 
    		 x = x / n;
    		 y = y / n;
    	  }
    	}
    
    double mag() {
  	  return Math.sqrt(x*x + y*y);
  	}
  
    void normalize() {
    	double m = mag();
    	 if (m != 0) {
    	   div(m);
    	 }
    	}
    
    void limit(double max) {
  	  if ( mag()>Math.abs(max) ) {
  		normalize();
  		mult(max);
  	  }
  	}

    void setmag(double val) {
        normalize();
        mult( val );
   	}
    
    void rotate(double ang) {
        double t;
        double cosa = Math.cos(ang);
        double sina = Math.sin(ang);
        t = x; 
        x = t*cosa + y*sina; 
        y = -t*sina + y*cosa;
    }
}
