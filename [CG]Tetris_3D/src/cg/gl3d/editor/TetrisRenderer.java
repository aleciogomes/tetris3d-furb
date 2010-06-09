package cg.gl3d.editor;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TetrisRenderer extends JPanel implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	private GLCanvas canvas;
	private GLCapabilities capabilities;
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	private TetrisPoint cameraEye;
	private int dragX = 0;

	private double raio = 20.0;
	private double angulo = 45.0;

	public TetrisRenderer() {

		cameraEye = new TetrisPoint();
		cameraEye.y = 2;
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
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(2.0, 0.0, 0.0);
		gl.glEnd();

		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(0.0, 2.0, 0.0);
		gl.glEnd();

		// eixo z
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(0.0, 0.0, 2.0);
		gl.glEnd();

		System.out.println("Angulo " + angulo);
		TetrisPoint p = Utils.pointFromAngulo(angulo, raio);
		cameraEye.x = p.x;
		cameraEye.z = p.z;

		System.out.println("Eye = " + cameraEye);
		gl.glLoadIdentity();
		glu.gluLookAt(cameraEye.x, cameraEye.y, cameraEye.z, 0, 0, 0, 0, 1, 0);

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

		double anguloAnterior = angulo;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP: {
			angulo += 1.0;
			break;
		}
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN: {
			angulo -= 1.0;
			break;
		}
		}

		if (anguloAnterior != angulo && glDrawable != null) {
			glDrawable.display();
		}
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

				if (angulo > 360.0 ) {
					angulo = 0;
				}
				
				if (angulo < 0){
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

}
