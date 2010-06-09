package cg.gl3d.editor;


public class Utils {
	
	public static TetrisPoint pointFromAngulo(double angulo, double raio) {
		angulo = angulo * 2 * Math.PI;
		angulo = angulo / 360;

		return new TetrisPoint(Math.cos(angulo) * raio, 0.0, Math.sin(angulo) * raio);
	}

}
