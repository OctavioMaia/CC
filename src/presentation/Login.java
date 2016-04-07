/*
 * Created by JFormDesigner on Wed Apr 06 22:42:03 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Client.Client;

/**
 * @author Rui Freitas
 */
public class Login extends JFrame {
	
	private Menu fatherFrame;
	
	public Login(JFrame father) {
		this.fatherFrame = (Menu)father;
		initComponents();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt){
                father.setVisible(true);
            }
        });
	}	

	private void buttonOKActionPerformed(ActionEvent e) {
		String ret="";
		Client c = fatherFrame.getCliente();
		//no futuro nao se vai inserir aqui a porta em que o cliente esra a escuta
		int mensage = c.login(this.textFieldUsername.getText(),
				new String(this.passwordField.getPassword()),
				Integer.parseInt(this.textFieldPorta.getText())
				);
		switch (mensage) {
			case 0:
				ret = "Password Incorreta";
				break;
			case 1:
				ret = "Login realizado com sucesso";
				break;
			case 2:
				ret = "O "+c.getUser()+" não se encontra registado";
				break;
			case 3:
				ret = "O "+c.getUser()+" já se encontra com login realizado";
				break;
			default:
				break;
		}
		
		JOptionPane.showMessageDialog(this,
			    ret,
			    "A plain message",
			    JOptionPane.PLAIN_MESSAGE);
			    
		if(mensage==1){
			fatherFrame.setVisible(true);
			fatherFrame.getLogoutButton().setEnabled(true);
			fatherFrame.getLoginButton().setEnabled(false);
			fatherFrame.getRegistarButton().setEnabled(false);
			fatherFrame.setStat(c.getUser()+"-->"+c.getIp());
			this.dispose();
			
		}
	}

	private void buttonCancelarActionPerformed(ActionEvent e) {
		fatherFrame.setVisible(true);
		this.dispose();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		buttonOK = new JButton();
		buttonCancelar = new JButton();
		label4 = new JLabel();
		label5 = new JLabel();
		label3 = new JLabel();
		textFieldUsername = new JTextField();
		textFieldPorta = new JTextField();
		label1 = new JLabel();
		passwordField = new JPasswordField();

		//======== this ========
		setTitle("Login");
		Container contentPane = getContentPane();

		//---- buttonOK ----
		buttonOK.setText("Ok");
		buttonOK.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonOK.addActionListener(e -> buttonOKActionPerformed(e));

		//---- buttonCancelar ----
		buttonCancelar.setText("Cancelar");
		buttonCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonCancelar.addActionListener(e -> buttonCancelarActionPerformed(e));

		//---- label4 ----
		label4.setText("Username:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 14));

		//---- label5 ----
		label5.setText("Password:");
		label5.setFont(new Font("Tahoma", Font.BOLD, 14));

		//---- label3 ----
		label3.setText("Port:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));

		//---- textFieldUsername ----
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- textFieldPorta ----
		textFieldPorta.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- label1 ----
		label1.setFont(new Font("Tahoma", Font.PLAIN, 20));

		//---- passwordField ----
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(19, 19, 19)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(label5)
									.addGap(18, 18, 18)
									.addComponent(passwordField))
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addGroup(contentPaneLayout.createParallelGroup()
										.addComponent(label4)
										.addComponent(label3))
									.addGap(16, 16, 16)
									.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(textFieldPorta, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
										.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)))
								.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
									.addComponent(buttonOK, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(18, 18, 18)
									.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(148, 148, 148)
							.addComponent(label1)))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1)
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldPorta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(buttonOK, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Rui Freitas
	private JButton buttonOK;
	private JButton buttonCancelar;
	private JLabel label4;
	private JLabel label5;
	private JLabel label3;
	private JTextField textFieldUsername;
	private JTextField textFieldPorta;
	private JLabel label1;
	private JPasswordField passwordField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
