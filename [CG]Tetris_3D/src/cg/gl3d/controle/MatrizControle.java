package cg.gl3d.controle;

import javax.media.opengl.GL;

import cg.gl3d.object.Cube;
import cg.gl3d.object.PosicaoMatriz;

public class MatrizControle {
	
	public static final int rowCount = 21;
	public static final int colCount = 12;
	public static final int leftCol = -5;

	private Cube matriz[][];
	
	public MatrizControle(){
		matriz = new Cube[rowCount][colCount];
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				matriz[i][j] = null;
			}
		}
	}
	
	public void removeCube(PosicaoMatriz pos) {
		matriz[pos.row][pos.col] = null;
	}
	
	public void putCube(Cube cube, PosicaoMatriz pos) {
		matriz[pos.row][pos.col] = cube;
	}
	
	public void moveCube(Cube cube, PosicaoMatriz from, PosicaoMatriz to) {
		matriz[from.row][from.col] = null;
		matriz[to.row][to.col] = cube;
	}
	
	public boolean canMove(PosicaoMatriz pos) {
		if (pos.row < 0 || pos.row >= rowCount || pos.col < 0 || pos.col >= colCount)
			return false;
		
		return matriz[pos.row][pos.col] == null;
	}
	
	public void draw(GL gl){
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				if (matriz[r][c] != null) 
					matriz[r][c].draw(gl, c + leftCol, r, 0);
			}
		}
	}

}
