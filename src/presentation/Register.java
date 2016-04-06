/*
 * Created by JFormDesigner on Wed Apr 06 11:33:57 BST 2016
 */

package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * @author Octavio Maia
 */
public class Register extends JFrame {
	public Register() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		buttonOK = new JButton();
		buttonCancelar = new JButton();
		label4 = new JLabel();
		label5 = new JLabel();
		label3 = new JLabel();
		textFieldUsername = new JTextField();
		textFieldPassword = new JTextField();
		textFieldPorta = new JTextField();
		label1 = new JLabel();

		//======== this ========
		setTitle("Registo");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- buttonOK ----
		buttonOK.setText("Ok");
		buttonOK.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(buttonOK);
		buttonOK.setBounds(175, 190, 60, 30);

		//---- buttonCancelar ----
		buttonCancelar.setText("Cancelar");
		buttonCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(buttonCancelar);
		buttonCancelar.setBounds(240, 190, 100, 30);

		//---- label4 ----
		label4.setText("Username:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(label4);
		label4.setBounds(20, 60, label4.getPreferredSize().width, 25);

		//---- label5 ----
		label5.setText("Password:");
		label5.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(label5);
		label5.setBounds(20, 100, label5.getPreferredSize().width, 20);

		//---- label3 ----
		label3.setText("Port:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(label3);
		label3.setBounds(20, 140, label3.getPreferredSize().width, 25);

		//---- textFieldUsername ----
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(textFieldUsername);
		textFieldUsername.setBounds(110, 60, 230, textFieldUsername.getPreferredSize().height);

		//---- textFieldPassword ----
		textFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(textFieldPassword);
		textFieldPassword.setBounds(110, 100, 230, textFieldPassword.getPreferredSize().height);

		//---- textFieldPorta ----
		textFieldPorta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(textFieldPorta);
		textFieldPorta.setBounds(110, 140, 230, textFieldPorta.getPreferredSize().height);

		//---- label1 ----
		label1.setText("Registo de utilizador");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(label1);
		label1.setBounds(new Rectangle(new Point(95, 5), label1.getPreferredSize()));

		contentPane.setPreferredSize(new Dimension(375, 270));
		setSize(375, 270);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JButton buttonOK;
	private JButton buttonCancelar;
	private JLabel label4;
	private JLabel label5;
	private JLabel label3;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;
	private JTextField textFieldPorta;
	private JLabel label1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
