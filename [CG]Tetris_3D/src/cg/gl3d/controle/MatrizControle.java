package cg.gl3d.controle;

import javax.media.opengl.GL;

import cg.gl3d.object.Cube;

public class MatrizControle {
	
	private Cube matriz[][];
	private final int rowCount = 20;
	private final int columnCount = 10;
	
	public MatrizControle(){
		matriz = new Cube[rowCount][columnCount];
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				matriz[i][j] = null;
			}
		}
	}
	
	public void setCube(int row, int column, Cube cube){
		matriz[row][column] = cube;
	}
	
	// teste
	public boolean isBusy(int row, int column){
		return matriz[row][column] != null;
	}
	
	public int getLineCount(){
		return rowCount;
	}
	
	public int getRowCount(){
		return columnCount;
	}
	
	public void draw(GL gl){
		for(Cube[] linha : matriz){
			for(Cube c : linha){
				if(c != null){
					c.draw(gl, 0, 0, 0);
				}
			}
		}
	}

}
