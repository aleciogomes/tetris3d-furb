package cg.gl3d.object;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

/**
 * 
 * @author Beto since: 09/06/2010
 * 
 *         Classe respons�vel por renderizar um cubo na tela.
 * 
 */
public class Cube implements Object3D {
	private GLUT glut;

	/**
	 * Tamanho padr�o do cubo
	 */
	private static final double sizeCube = 0.5d;
	
	private float r;
	private float g;
	private float b;

	/**
	 * Construtor padr�o
	 */
	public Cube() {
		this.glut = new GLUT();
	}
	
	public void setColor(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * 
	 * @param gl
	 *            : objeto da classe CL, respons�vel pelo desenho
	 * @param translateX
	 *            : posi��o no eixo x que ser� transladado
	 * @param translateY
	 *            : posi��o no eixo y que ser� transladado
	 * @param translateZ
	 *            : posi��o no eixo z que ser� transladado
	 */
	public void draw(GL gl, int translateX, int translateY, int translateZ) {
		draw(gl, translateX, translateY, translateZ, r, g, b);
	}

	/**
	 * 
	 * @param gl
	 *            : objeto da classe CL, respons�vel pelo desenho
	 * @param translateX
	 *            : posi��o no eixo x que ser� transladado
	 * @param translateY
	 *            : posi��o no eixo y que ser� transladado
	 * @param translateZ
	 *            : posi��o no eixo z que ser� transladado
	 * @param red
	 *            : escala vermelho da cor
	 * @param green
	 *            : escala verde da cor
	 * @param blue
	 *            : escala azul da cor
	 */
	public void draw(GL gl, int translateX, int translateY, int translateZ, float red, float green, float blue) {
		gl.glPushMatrix();
		gl.glTranslated(translateX * sizeCube, translateY * sizeCube, translateZ * sizeCube);
		gl.glScaled(sizeCube, sizeCube, sizeCube);
		gl.glColor3f(0, 0, 0);
		glut.glutWireCube(1.0f); //"borda" do cube
		gl.glColor3f(red, green, blue);
		glut.glutSolidCube(1.0f);
		gl.glPopMatrix();
		gl.glFlush();
	}
}
