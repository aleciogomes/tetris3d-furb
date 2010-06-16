package cg.gl3d.editor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JPanel;

import cg.gl3d.controle.ElementoVivo;
import cg.gl3d.controle.LogicaTetris;
import cg.gl3d.controle.MatrizControle;
import cg.gl3d.object.Cube;
import cg.gl3d.object.ElementoTetris;
import cg.gl3d.object.TipoElemento;

@SuppressWarnings("serial")
public class TetrisRenderer extends JPanel implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, LogicaTetris, MouseWheelListener {

	private GLCanvas canvas;
	private GLCapabilities capabilities;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	private TetrisPoint cameraEye;
	private int dragX = 0;
	private int transY = 20;
	private int transX = 0;

	private double raio = 17.0;
	private double angulo = 90.0;

	private ElementoVivo threadAnimacao;
	private MatrizControle matrizControle;
	private Random random;

	public TetrisRenderer() {
		matrizControle = new MatrizControle();
		threadAnimacao = new ElementoVivo(this);
		random = new Random();

		cameraEye = new TetrisPoint();
		cameraEye.y = 4.0;
		cameraEye.z = raio;

		/*
		 * Cria um objeto GLCapabilities para especificar o número de bits por
		 * pixel para RGBA
		 */
		capabilities = new GLCapabilities();
		capabilities.setRedBits(8);
		capabilities.setBlueBits(8);
		capabilities.setGreenBits(8);
		capabilities.setAlphaBits(8);

		/*
		 * Cria um objecto GLCanvas e adiciona o Editor como listener dos
		 * eventos do GL, do teclado e do mouse.
		 */
		canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);

		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = glDrawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		gl.glEnable(GL.GL_DEPTH_TEST);

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		//TODO mover isso daqui... deixar como random mesmo
		ElementoTetris e = new ElementoTetris(matrizControle);
		e.setTransladeX(transX);
		e.setTransladeY(transY);
		e.create(TipoElemento.raio);

		threadAnimacao.setElemento(e);
		threadAnimacao.start();
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		drawAxes();
		drawBox();
		drawGrid();
		
		matrizControle.draw(gl);

		lookAt();
		
		gl.glFlush();
	}

	private void drawAxes() {
		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(-4.75, 0.0, 0.0);
		gl.glVertex3d(-2.75, 0.0, 0.0);
		gl.glEnd();

		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(-4.75, 0.0, 0.0);
		gl.glVertex3d(-4.75, 2.0, 0.0);
		gl.glEnd();

		// eixo z
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(-4.75, 0.0, 0.0);
		gl.glVertex3d(-4.75, 0.0, 2.0);
		gl.glEnd();
	}

	private void drawBox() {
		// box
		gl.glLineWidth(2.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(-2.75, -0.25, 0.25);
		gl.glVertex3d(3.25, -0.25, 0.25);
		gl.glVertex3d(3.25, -0.25, -0.25);
		gl.glVertex3d(-2.75, -0.25, -0.25);
		gl.glEnd();

		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(-2.75, -0.25, 0.25);
		gl.glVertex3d(-2.75, 10.25, 0.25);
		gl.glVertex3d(-2.75, 10.25, -0.25);
		gl.glVertex3d(-2.75, -0.25, -0.25);
		gl.glEnd();

		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(3.25, -0.25, 0.25);
		gl.glVertex3d(3.25, 10.25, 0.25);
		gl.glVertex3d(3.25, 10.25, -0.25);
		gl.glVertex3d(3.25, -0.25, -0.25);
		gl.glEnd();
	}
	
	private void drawGrid() {
		gl.glLineWidth(1.0f);
		gl.glColor3f(0.8f, 0.8f, 0.8f);
		
		double variacao = 0.25;
		
		for (int i = 0; i < 20; i++) {
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3d(-2.75, variacao, (angulo > 180 && angulo < 360) ? -0.25 : 0.25);
			gl.glVertex3d(3.25, variacao, (angulo > 180 && angulo < 360) ? -0.25 : 0.25);
			gl.glEnd();
			variacao += 0.5;
		}
		
		variacao = -2.75;
		
		for (int i = 0; i < 12; i++) {
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3d(variacao, -0.25, (angulo > 180 && angulo < 360) ? -0.25 : 0.25);
			gl.glVertex3d(variacao, 10.25, (angulo > 180 && angulo < 360) ? -0.25 : 0.25);
			gl.glEnd();
			variacao += 0.5;
		}
	}
	
	private void lookAt() {
		TetrisPoint p = Utils.pointFromAngulo(angulo, raio);
		cameraEye.x = p.x;
		cameraEye.z = p.z;
		gl.glLoadIdentity();
		glu.gluLookAt(cameraEye.x, cameraEye.y, cameraEye.z, 0, cameraEye.y, 0, 0, 1, 0);
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int width, int height) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-2.0f, 2.0f, -2.0, 2.0, 5.0f, 60.0f);
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			glDrawable.display();
			return;
		}

		// lado oposto da camera, inverte os comandos
		if (angulo > 180 && angulo < 360) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				e.setKeyCode(KeyEvent.VK_RIGHT);
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				e.setKeyCode(KeyEvent.VK_LEFT);
			}
		}

		threadAnimacao.keyPressed(e);

		switch (e.getKeyCode()) {
		case KeyEvent.VK_B: {
			transY--;
			break;
		}
		}

		glDrawable.display();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragX == 0) {
			dragX = e.getX();
		}

		if (dragX - e.getX() != 0) {
			angulo += dragX - e.getX();
			dragX = e.getX();

			if (angulo > 360.0) {
				angulo = 0;
			}

			if (angulo < 0) {
				angulo = 360;
			}

			glDrawable.display();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragX = 0;
	}

	@Override
	public void elementMoved() {
		glDrawable.display();
	}

	@Override
	public void generateElement() {
		ElementoTetris e = new ElementoTetris(matrizControle);
		e.setTransladeX(transX);
		e.setTransladeY(transY);
		e.create(TipoElemento.values()[random.nextInt(TipoElemento.count.ordinal())]);
		threadAnimacao.setElemento(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			cameraEye.y += (e.getWheelRotation() * 0.1) * -1;
			glDrawable.display();
		}
	}

}
