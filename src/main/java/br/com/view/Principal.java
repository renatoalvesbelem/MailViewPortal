package br.com.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.component.NonEditableModel;
import br.com.control.HTMLPageControl;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txIp;
	private JScrollPane scrollPaneEmailTitle;
	private JTable table;
	private String[] columnNames = { "Header" };
	private Object[][] data = {};
	NonEditableModel model = new NonEditableModel(data, columnNames);
	private JButton btnGo;
	private JTextArea textAreaBodyEmail; 

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 934, 782);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txIp = new JTextField();
		txIp.setBounds(83, 11, 477, 20);
		contentPane.add(txIp);
		txIp.setColumns(10);

		JLabel lblIpDoPortal = new JLabel("IP do Portal");
		lblIpDoPortal.setBounds(10, 14, 86, 14);
		contentPane.add(lblIpDoPortal);

		scrollPaneEmailTitle = new JScrollPane();
		scrollPaneEmailTitle.setBounds(10, 68, 898, 246);
		contentPane.add(scrollPaneEmailTitle);

		table = new JTable(data, columnNames);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(model);
		scrollPaneEmailTitle.setViewportView(table);

		btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				preencheTable(model, txIp.getText());
			}
		});

		btnGo.setBounds(570, 10, 89, 23);
		contentPane.add(btnGo);
		
		textAreaBodyEmail = new JTextArea();
		textAreaBodyEmail.setLineWrap(true);
		textAreaBodyEmail.setBounds(10, 371, 898, 185);
		
		
		JScrollPane scrollPaneBodyEmail = new JScrollPane(textAreaBodyEmail);
		scrollPaneBodyEmail.setBounds(10, 371, 898, 324);
		contentPane.add(scrollPaneBodyEmail);
	}

	public void preencheTable(NonEditableModel model, String iPServer) {
		HTMLPageControl htmlPageControl = new HTMLPageControl(iPServer);
		for (String tituloEmails : htmlPageControl.retornaTitulosEmails()) {
			model.addRow(new Object[] { tituloEmails });
		}

	}
}
