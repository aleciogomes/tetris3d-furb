package cg.gl3d.object;

import cg.gl3d.controle.MatrizControle;

public class ElementoTetris {

	private final int n = 4;
	private boolean matriz[][];
	private int transladeX;
	private int transladeY;
	private Cube cube;
	private MatrizControle matrizControle;

	public ElementoTetris(MatrizControle matrizControle) {
		this.matrizControle = matrizControle;
		transladeX = 0;
		transladeY = 0;
		matriz = new boolean[this.n][this.n];

		clear();
	}

	private void clear() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				matriz[i][j] = false;
			}
		}
	}

	private void adjust() {
		int i, j;

		// trata as linhas
		for (i = 0; i < this.n; i++) {
			for (j = 0; j < this.n; j++) {
				if (matriz[i][j]) {
					break;
				}
			}

			if (j == this.n) {
				for (int a = i; a < this.n - 1; a++) {
					for (int b = 0; b < this.n; b++) {
						matriz[a][b] = matriz[a + 1][b];

						if (a == this.n - 2) {
							matriz[a + 1][b] = false;
						}
					}
				}
			}
		}

		// trata as colunas
		for (i = 0; i < this.n; i++) {
			for (j = 0; j < this.n; j++) {
				if (matriz[j][i]) {
					break;
				}
			}

			if (j == this.n) {
				for (int a = i; a < this.n - 1; a++) {
					for (int b = 0; b < this.n; b++) {
						matriz[b][a] = matriz[b][a + 1];

						if (a == this.n - 2) {
							matriz[b][a + 1] = false;
						}
					}
				}
			}
		}
	}

	public void create(TipoElemento tipo) {

		clear();

		float r = 0;
		float g = 0;
		float b = 0;

		switch (tipo) {
		case te: {
			matriz[0][0] = true;
			matriz[0][1] = true;
			matriz[0][2] = true;
			matriz[1][1] = true;

			g = 1;
			break;
		}
		case barra: {
			matriz[0][0] = true;
			matriz[1][0] = true;
			matriz[2][0] = true;
			matriz[3][0] = true;

			r = 1;
			break;
		}
		case quad: {
			matriz[0][0] = true;
			matriz[0][1] = true;
			matriz[1][0] = true;
			matriz[1][1] = true;

			b = 1;
			break;
		}
		case raio: {
			matriz[0][1] = true;
			matriz[0][2] = true;
			matriz[1][0] = true;
			matriz[1][1] = true;

			r = 1;
			b = 1;
			break;
		}
		case ele: {
			matriz[0][0] = true;
			matriz[1][0] = true;
			matriz[2][0] = true;
			matriz[2][1] = true;

			g = 1;
			b = 1;
			break;
		}
		}

		cube = new Cube();
		cube.setColor(r, g, b);
	}

	public void rotate() {
		boolean[][] novaMatriz = new boolean[this.n][this.n];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				novaMatriz[i][j] = matriz[this.n - 1 - j][i];
				
				if (matriz[i][j])
					matrizControle.removeCube(toPosicaoMatriz(i, j));
			}
		}
		matriz = novaMatriz;
		adjust();
		
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				if (matriz[i][j])
					matrizControle.putCube(cube, toPosicaoMatriz(i, j));
			}
		}
	}

	public boolean moveDown() {
		if (!canMove(-1, 0))
			return false;

		for (int i = n - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				if (matriz[i][j]) {
					PosicaoMatriz from = toPosicaoMatriz(i, j);
					PosicaoMatriz to = from.clone();
					to.row--;
					matrizControle.moveCube(cube, from, to);
				}
			}
		}
		transladeY--;
		return true;
	}
	
	public boolean moveLeft() {
		if (!canMove(0, -1))
			return false;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matriz[i][j]) {
					PosicaoMatriz from = toPosicaoMatriz(i, j);
					PosicaoMatriz to = from.clone();
					to.col--;
					matrizControle.moveCube(cube, from, to);
				}
			}
		}
		transladeX--;
		return true;
	}
	
	public boolean moveRight() {
		if (!canMove(0, 1))
			return false;

		for (int i = n - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				if (matriz[i][j]) {
					PosicaoMatriz from = toPosicaoMatriz(i, j);
					PosicaoMatriz to = from.clone();
					to.col++;
					matrizControle.moveCube(cube, from, to);
				}
			}
		}
		transladeX++;
		return true;
	}

	private boolean canMove(int y, int x) {
		boolean[] tested = new boolean[n];

		if (y < 0) {
			for (int i = n - 1; i >= 0; i--) {
				for (int j = 0; j < n; j++) {
					if (matriz[i][j] && !tested[j]) {
						tested[j] = true;

						if (!matrizControle.canMove(toPosicaoMatriz(i - y, j + x)))
							return false;
					}
				}
			}
		}
		if (x < 0) {
			for (int i = 0; i < n; i++) {
				if (!tested[i]) {
					for (int j = 0; j < n; j++) {
						if (matriz[i][j] && !tested[i]) {
							tested[i] = true;

							if (!matrizControle.canMove(toPosicaoMatriz(i - y, j + x)))
								return false;
						}
					}
				}
			}
		}
		else if (x > 0) {
			for (int i = 0; i < n; i++) {
				if (!tested[i]) {
					for (int j = n-1; j >= 0; j--) {
						if (matriz[i][j] && !tested[i]) {
							tested[i] = true;

							if (!matrizControle.canMove(toPosicaoMatriz(i - y, j + x)))
								return false;
						}
					}
				}
			}
		}
		return true;
	}

	private PosicaoMatriz toPosicaoMatriz(int i, int j) {
		return new PosicaoMatriz((i * -1) + transladeY, j + transladeX - MatrizControle.leftCol);
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

	public int getTransladeX() {
		return transladeX;
	}

	public void setTransladeY(int transladeY) {
		this.transladeY = transladeY;
	}

	public int getTransladeY() {
		return transladeY;
	}

}
