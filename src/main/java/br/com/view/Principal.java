package br.com.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import br.com.component.NonEditableModel;
import br.com.control.HTMLPageControl;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneEmailTitle;
	private JTable table;
	private String[] columnNames = { "Header" };
	private Object[][] data = {};
	NonEditableModel model = new NonEditableModel(data, columnNames);
	private JButton btnGo;
	private JEditorPane editionPaneBodyEmail;
	private HTMLPageControl htmlPageControl;
	private JFormattedTextField txIp;

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

		JLabel lblIpDoPortal = new JLabel("IP do Portal");
		lblIpDoPortal.setBounds(10, 14, 86, 14);
		contentPane.add(lblIpDoPortal);

		scrollPaneEmailTitle = new JScrollPane();
		scrollPaneEmailTitle.setBounds(10, 68, 898, 246);
		contentPane.add(scrollPaneEmailTitle);

		table = new JTable(data, columnNames);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				preencheCorpoEmail(editionPaneBodyEmail, table.getSelectedRow());
			}
		});
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

		editionPaneBodyEmail = new JEditorPane();
		editionPaneBodyEmail.setEditable(false);
		editionPaneBodyEmail.setBounds(10, 371, 898, 185);

		JScrollPane scrollPaneBodyEmail = new JScrollPane(editionPaneBodyEmail);
		scrollPaneBodyEmail.setBounds(10, 343, 898, 352);
		contentPane.add(scrollPaneBodyEmail);
		txIp = new JFormattedTextField();
		txIp.setBounds(92, 11, 467, 20);
		contentPane.add(txIp);
	}

	public void preencheTable(NonEditableModel model, String iPServer) {
		htmlPageControl = new HTMLPageControl(iPServer);
		limpaInformacoesEmail();
		for (String tituloEmails : htmlPageControl.retornaTitulosEmails()) {
			model.addRow(new Object[] { tituloEmails.trim() });
		}
	}

	public void preencheCorpoEmail(JEditorPane editionPaneBodyEmail, Integer key) {
		editionPaneBodyEmail.setText("");
		editionPaneBodyEmail.setContentType("text/html");
		editionPaneBodyEmail.setText(htmlPageControl.retornaBodyEmail(key).toString());
		editionPaneBodyEmail.setCaretPosition(0);
	}

	private void limpaInformacoesEmail() {
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		editionPaneBodyEmail.setText("");
	}
}
