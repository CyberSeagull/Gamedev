public class gfood extends gobject {
    public gfood(double x, double y, int radius, glist m_glist) {
     //   System.out.println(" x="+x+" y="+y+" x="+radius);
        position=new PVector(x, y);
        c_radius=radius;
		pgrid = m_glist.getmygrid(position);
    }	

}
