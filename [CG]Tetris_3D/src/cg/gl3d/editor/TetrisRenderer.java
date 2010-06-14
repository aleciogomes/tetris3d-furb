package cg.gl3d.editor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JPanel;

import cg.gl3d.controle.ElementMoved;
import cg.gl3d.controle.ElementoVivo;
import cg.gl3d.controle.MatrizControle;
import cg.gl3d.object.ElementoTetris;
import cg.gl3d.object.TipoElemento;

@SuppressWarnings("serial")
public class TetrisRenderer extends JPanel implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ElementMoved {

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
	private List<ElementoTetris> elementos;
	
	private ElementoVivo threadAnimacao;
	private MatrizControle matrizControle;

	public TetrisRenderer() {
		matrizControle = new MatrizControle();
		threadAnimacao = new ElementoVivo(this, matrizControle);

		cameraEye = new TetrisPoint();
		cameraEye.y = 4.0;
		cameraEye.z = raio;
		
		elementos = new ArrayList<ElementoTetris>();

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

		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = glDrawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		ElementoTetris e = new ElementoTetris();
		e.setTransladeX(transX);
		e.setTransladeY(transY);
		e.create(TipoElemento.te);
		elementos.add(e);
		
		threadAnimacao.setElemento(e);
		threadAnimacao.start();
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

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
		
		//box
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(-3.75, -0.25, 0.0);
		gl.glVertex3d(3.75, -0.25, 0.0);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(-3.75, -0.25, 0.0);
		gl.glVertex3d(-3.75, 10.25, 0.0);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(3.75, -0.25, 0.0);
		gl.glVertex3d(3.75, 10.25, 0.0);
		gl.glEnd();
		gl.glLineWidth(1.0f);

		for(ElementoTetris e : elementos){
			e.draw(gl);
		}

		TetrisPoint p = Utils.pointFromAngulo(angulo, raio);
		cameraEye.x = p.x;
		cameraEye.z = p.z;

		gl.glLoadIdentity();
		glu.gluLookAt(cameraEye.x, cameraEye.y, cameraEye.z, 0, 4.0, 0, 0, 1, 0);

		gl.glFlush();
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
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			glDrawable.display();
			return;
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
		if (e.getButton() == MouseEvent.BUTTON1) {
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

}
