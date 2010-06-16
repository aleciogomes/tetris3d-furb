package cg.gl3d.object;

public class PosicaoMatriz {
	
	public int row;
	public int col;
	
	public PosicaoMatriz() {
		this(0, 0);
	}

	public PosicaoMatriz(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public PosicaoMatriz clone() {
		return new PosicaoMatriz(row, col);
	}

}
