package cg.gl3d.controle;

import java.awt.event.KeyEvent;

import cg.gl3d.object.ElementoTetris;

public class ElementoVivo extends Thread {

	private ElementoTetris elemento;
	private MatrizControle matrizControle;
	private LogicaTetris listener;
	private long refreshRate;

	public ElementoVivo(LogicaTetris listener, MatrizControle matrizControle) {
		this.elemento = null;
		this.matrizControle = matrizControle;
		this.listener = listener;
		this.refreshRate = 1000;
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
			if (now - time > refreshRate) {
				// isto não é correto. O certo é se basear na matriz de controle
				if (elemento.getTransladeY() != 1) {
					elemento.setTransladeY(elemento.getTransladeY() - 1);
					time = now;
					listener.elementMoved();
				}
				else{
					// reseta a taxa de atualiação
					refreshRate = 1000;
					listener.generateElement();
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE: {
			elemento.rotate();
			break;
		}
		case KeyEvent.VK_UP: {
			refreshRate = 1000;
			listener.elementMoved();
			break;
		}
		case KeyEvent.VK_DOWN: {
			refreshRate = 100;
			listener.elementMoved();
			break;
		}
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
