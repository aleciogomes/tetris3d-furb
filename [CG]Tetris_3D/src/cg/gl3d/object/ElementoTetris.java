package cg.gl3d.object;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

public class ElementoTetris {

	private final int n = 4;
	private int matriz[][];
	private int transladeX;
	private int transladeY;
	private List<Cube> cubes;

	public ElementoTetris() {
		transladeX = 0;
		transladeY = 0;
		matriz = new int[this.n][this.n];
		cubes = new ArrayList<Cube>();

		clear();
	}

	private void clear() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				matriz[i][j] = 0;
			}
		}
	}

	private void adjust() {
		int i, j;
		
		// trata as linhas
		for (i = 0; i < this.n; i++) {
			for (j = 0; j < this.n; j++) {
				if (matriz[i][j] != 0) {
					break;
				}
			}

			if (j == this.n) {
				for (int a = i; a < this.n - 1; a++) {
					for (int b = 0; b < this.n; b++) {
						matriz[a][b] = matriz[a+1][b];
						
						if(a == this.n - 2){
							matriz[a+1][b] = 0;
						}
					}
				}
			}
		}
		
		// trata as colunas
		for (i = 0; i < this.n; i++) {
			for (j = 0; j < this.n; j++) {
				if (matriz[j][i] != 0) {
					break;
				}
			}

			if (j == this.n) {
				for (int a = i; a < this.n - 1; a++) {
					for (int b = 0; b < this.n; b++) {
						matriz[b][a] = matriz[b][a+1];
						
						if (a == this.n - 2){
							matriz[b][a+1] = 0;
						}
					}
				}
			}
		}
	}

	public void create(TipoElemento tipo) {

		clear();

		switch (tipo) {
		case te: {
			matriz[0][0] = 1;
			matriz[0][1] = 1;
			matriz[0][2] = 1;
			matriz[1][1] = 1;
			
			for (int i = 0; i < 4; i++) {
				Cube c = new Cube();
				c.setColor(0, 1, 0);
				cubes.add(c);
			}
			
			break;
		}
		}
	}

	public void rotate() {
		int novaMatriz[][] = new int[this.n][this.n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				novaMatriz[i][j] = matriz[this.n - 1 - j][i];
			}
		}
		matriz = novaMatriz;
		adjust();
	}

	public void draw(GL gl) {
		int cubo = 0;
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				if (matriz[i][j] == 1) {
					cubes.get(cubo++).draw(gl, j + transladeX, (i * -1) + transladeY, 0);
					System.out.println("Desenhei um cubo");
				}
			}
		}
	}

	public void print() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print(matriz[i][j] + ", ");
			}
			System.out.println("");
		}
	}

	public void setTransladeX(int transladeX) {
		this.transladeX = transladeX;
	}

	public void setTransladeY(int transladeY) {
		this.transladeY = transladeY;
	}
	
	
}
