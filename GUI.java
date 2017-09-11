package abc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton buttonSend;

	private JLabel lblusername;

	private JTextField txtusername;

	private JLabel lblpass;

	private JPasswordField txtpass;

	private JLabel lblto;

	private JTextField txtto;

	private JLabel lblsub;

	private JLabel lblmess;

	private JTextArea txtmess;

	private JTextField txtsub;

	public GUI() {
		setTitle("SEND MAIL");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		createGUI();
	}

	private void createGUI() {
		JPanel panelNorth, panelCenter, panelSouth;

		panelNorth = new JPanel();
		add(panelNorth, BorderLayout.NORTH);

		panelNorth.setPreferredSize(new Dimension(450, 100));

		panelNorth.setBorder(BorderFactory.createTitledBorder("DANG NHAP"));
		panelNorth.setBackground(Color.green);
		panelNorth.setPreferredSize(new Dimension(0, 100));

		panelNorth.add(lblusername = new JLabel("From:           "));
		panelNorth.add(txtusername = new JTextField());
		txtusername.setPreferredSize(new Dimension(400, 30));

		panelNorth.add(lblpass = new JLabel("Pass:           "));
		panelNorth.add(txtpass = new JPasswordField());
		txtpass.setPreferredSize(new Dimension(400, 30));

		// --------------------------------------------------------
		add(panelSouth = new JPanel(), BorderLayout.SOUTH);

		panelSouth.add(buttonSend = new JButton("SEND"));
		buttonSend.setBounds(10, 20, 80, 30);
		buttonSend.addActionListener(this);

		// ------------------------------------------------------
		add(panelCenter = new JPanel(), BorderLayout.CENTER);
		panelCenter.add(lblto = new JLabel("TO:             "));
		panelCenter.add(txtto = new JTextField());
		txtto.setPreferredSize(new Dimension(400, 30));

		panelCenter.add(lblsub = new JLabel("Subject:   "));
		panelCenter.add(txtsub = new JTextField());
		txtsub.setPreferredSize(new Dimension(400, 30));

		panelCenter.add(lblmess = new JLabel("Message: "));
		panelCenter.add(txtmess = new JTextArea());
		txtmess.setPreferredSize(new Dimension(400, 150));

	}

	public static void main(String[] args) {
		new GUI().setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o.equals(buttonSend)) {
			try {
				Sendmail.send("smtp.gmail.com", txtto.getText(), txtusername.getText(),
						String.copyValueOf(txtpass.getPassword()), txtsub.getText(), txtmess.getText());
				JOptionPane.showMessageDialog(null, "Mail da duoc gui !!");
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Gui mail khong thanh cong !!");
			}

		}
	}

}