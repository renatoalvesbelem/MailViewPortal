package br.com.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JTable table;
	private String[] columnNames = { "Header" };
	private Object[][] data = {};
	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	private JButton btnGo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(83, 11, 477, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblIpDoPortal = new JLabel("IP do Portal");
		lblIpDoPortal.setBounds(10, 14, 86, 14);
		contentPane.add(lblIpDoPortal);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 68, 646, 373);
		contentPane.add(scrollPane);

		table = new JTable(data, columnNames);
		table.setEnabled(false);
		table.setModel(model);
		scrollPane.setViewportView(table);

		btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.addRow(new Object[] { "Teste" });
			}
		});
		btnGo.setBounds(570, 10, 89, 23);
		contentPane.add(btnGo);
	}
}
