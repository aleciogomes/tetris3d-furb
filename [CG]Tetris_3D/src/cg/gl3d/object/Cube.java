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
	private final double sizeCube;

	/**
	 * Posi��o atual do cubo na matriz.
	 */
	private int posAtual;
	
	private int r;
	private int g;
	private int b;

	/**
	 * Construtor padr�o
	 */
	public Cube() {
		this.glut = new GLUT();
		this.sizeCube = 0.5d;
	}
	
	public void setColor(int r, int g, int b){
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
	public void draw(GL gl, int translateX, int translateY, int translateZ, int red, int green, int blue) {
		gl.glColor3b((byte)red, (byte)green, (byte)blue);
		gl.glPushMatrix();
		gl.glTranslated(translateX * sizeCube, translateY * sizeCube, translateZ * sizeCube);
		gl.glScaled(sizeCube, sizeCube, sizeCube);
		glut.glutSolidCube(1.0f);
		gl.glPopMatrix();
		gl.glFlush();
	}

	/*
	 * M�todo acessores
	 */
	public int getPosAtual() {
		return posAtual;
	}

	public void setPosAtual(int posAtual) {
		this.posAtual = posAtual;
	}
}
