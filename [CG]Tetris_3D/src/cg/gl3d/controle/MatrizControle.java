package cg.gl3d.controle;

public class MatrizControle {
	
	private int matriz[][];
	private final int rowCount = 20;
	private final int columnCount = 10;
	
	public MatrizControle(){
		matriz = new int[rowCount][columnCount];
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				matriz[i][j] = 0;
			}
		}
	}
	
	public void setStatus(int row, int column, boolean busy){
		matriz[row][column] = busy ? 1 : 0;
	}
	
	// teste
	public boolean isBusy(int row, int column){
		return matriz[row][column] == 1;
	}
	
	public int getLineCount(){
		return rowCount;
	}
	
	public int getRowCount(){
		return columnCount;
	}

}
