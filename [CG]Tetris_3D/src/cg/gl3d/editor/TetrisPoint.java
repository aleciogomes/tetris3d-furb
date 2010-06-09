package cg.gl3d.editor;


public class TetrisPoint {
	
	public double x;
	public double y;
	public double z;
	
	public TetrisPoint(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public TetrisPoint() {
		this(0.0, 0.0, 0.0);
	}
	
	public void move(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "TetrisPoint(x = "+ x +"; y = "+ y +"; z = "+ z +")";
	}

}
