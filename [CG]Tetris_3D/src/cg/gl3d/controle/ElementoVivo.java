package cg.gl3d.controle;

import java.awt.event.KeyEvent;

import cg.gl3d.object.ElementoTetris;

public class ElementoVivo extends Thread {

	private ElementoTetris elemento;
	private LogicaTetris listener;
	private long refreshRate;

	public ElementoVivo(LogicaTetris listener) {
		this.elemento = null;
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
				if (elemento.moveDown()) {
					time = now;
					listener.elementMoved();
				} else {
					refreshRate = 1000; // reseta a taxa de atualiação
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
			elemento.moveLeft();
			listener.elementMoved();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			elemento.moveRight();
			listener.elementMoved();
			break;
		}
		}
	}
}
