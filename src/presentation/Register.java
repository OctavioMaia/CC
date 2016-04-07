/*
 * Created by JFormDesigner on Wed Apr 06 11:33:57 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.*;

import Client.Client;

/**
 * @author Octavio Maia
 */
public class Register extends JFrame {
	
	private Menu fatherFrame;
	
	public Register(JFrame father) {
		this.fatherFrame=(Menu)father;
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
		int mensage = c.register(
				this.textFieldUsername.getText(),
				new String(this.passwordField.getPassword()),
				Integer.parseInt(this.textFieldPorta.getText())
				);
		
		switch (mensage) {
			case 0:
				ret = "Insucesso no registo";
				break;
			case 1:
				ret = "Registo realizado com sucesso";
				break;
			case 2:
				ret = "UserName já existente";
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
		setTitle("Registo");
		setResizable(false);
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
		label1.setText("Registo de utilizador");
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
							.addGap(87, 87, 87)
							.addComponent(label1))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(label5)
								.addComponent(label3))
							.addGap(18, 18, 18)
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(textFieldPorta, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGap(12, 12, 12)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
									.addComponent(label4)
									.addGap(18, 18, 18)
									.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
								.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
									.addComponent(buttonOK, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(18, 18, 18)
									.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addComponent(label1)
					.addGap(30, 30, 30)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15, 15, 15)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldPorta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(buttonOK, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setSize(375, 270);
		setLocationRelativeTo(null);
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
