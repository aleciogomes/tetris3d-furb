package cg.gl3d.editor;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Janela principal do editor de pol�gonos 2D
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Cria a janela com a barra de ferramentas e a �rea de edi��o
	 */
	public MainWindow() {
		super("T�tris 3D");   
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initComponents();
	}	
	
	/**
	 * Inicializa os componentes da tela
	 */
	private void initComponents() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
//		contentPane.add(createToolBar(), BorderLayout.NORTH);
//		contentPane.add(createEditor(), BorderLayout.CENTER);
	}
	
	/**
	 * M�todo de inicializa��o do editor
	 * @param args Argumentos de inicializa��o passados pela JVM
	 */
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
	
}
