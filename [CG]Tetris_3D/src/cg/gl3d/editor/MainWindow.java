package cg.gl3d.editor;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Janela principal do editor de polígonos 2D
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Cria a janela com a barra de ferramentas e a área de edição
	 */
	public MainWindow() {
		super("Tétris 3D");   
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
	 * Método de inicialização do editor
	 * @param args Argumentos de inicialização passados pela JVM
	 */
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
	
}
