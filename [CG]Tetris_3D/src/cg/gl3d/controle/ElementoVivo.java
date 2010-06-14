package cg.gl3d.controle;

import java.awt.event.KeyEvent;

import cg.gl3d.object.ElementoTetris;

public class ElementoVivo extends Thread {

	private ElementoTetris elemento;
	private MatrizControle matrizControle;
	private ElementMoved listener;

	public ElementoVivo(ElementMoved listener, MatrizControle matrizControle) {
		this.elemento = null;
		this.matrizControle = matrizControle;
		this.listener = listener;
	}

	public ElementoTetris getElemento() {
		return elemento;
	}

	public void setElemento(ElementoTetris elemento) {
		this.elemento = elemento;
	}

	@Override
	public void run() {
		long time = 0;
		while (true) {
			if (time == 0) {
				time = System.currentTimeMillis();
			}

			long now = System.currentTimeMillis();
			if (now - time > 1000) {
				// isto n‹o Ž correto. O certo Ž se basear na matriz de controle
				if (elemento.getTransladeY() != 1) {
					elemento.setTransladeY(elemento.getTransladeY() - 1);
					time = now;
					listener.elementMoved();
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_R: {
			elemento.rotate();
			break;
		}
		case KeyEvent.VK_UP: {
			elemento.setTransladeY(elemento.getTransladeY() + 1);
			listener.elementMoved();
			break;
		}
			// case KeyEvent.VK_DOWN:{
			// elemento.setTransladeY(elemento.getTransladeY() - 1);
			// listener.elementMoved();
			// break;
			// }
		case KeyEvent.VK_LEFT: {
			elemento.setTransladeX(elemento.getTransladeX() - 1);
			listener.elementMoved();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			elemento.setTransladeX(elemento.getTransladeX() + 1);
			listener.elementMoved();
			break;
		}
		}
	}
}
