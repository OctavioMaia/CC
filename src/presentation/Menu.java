/*
 * Created by JFormDesigner on Wed Apr 06 11:37:12 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Client.Client;

/**
 * @author Octavio Maia
 */
public class Menu extends JFrame {
	
	protected Client cliente;
	
	public Menu(String ipServer, int portServer) {
		this.cliente = new Client(ipServer, portServer);
		initComponents();
	}

	public Client getCliente() {
		return cliente;
	}
	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}

	private void registarButtonMouseClicked(MouseEvent e) {
		Register res = new Register(this);
		res.setVisible(true);
		this.setVisible(false);
	}

	private void button1ActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void sairButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		sairButton = new JButton();
		label1 = new JLabel();
		loginButton = new JButton();
		registarButton = new JButton();
		logoutButton = new JButton();

		//======== this ========
		setTitle("Menu");
		setResizable(false);
		Container contentPane = getContentPane();

		//---- sairButton ----
		sairButton.setText("Sair");
		sairButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		sairButton.addActionListener(e -> {
			button1ActionPerformed(e);
			sairButtonActionPerformed(e);
		});

		//---- label1 ----
		label1.setText("Estado:");

		//---- loginButton ----
		loginButton.setText("Login");

		//---- registarButton ----
		registarButton.setText("Registar");
		registarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				registarButtonMouseClicked(e);
			}
		});

		//---- logoutButton ----
		logoutButton.setText("Logout");

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(0, 274, Short.MAX_VALUE)
							.addComponent(sairButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(label1)
							.addGap(0, 312, Short.MAX_VALUE))
						.addComponent(loginButton, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
						.addComponent(logoutButton, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
						.addComponent(registarButton, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label1)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
					.addComponent(registarButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(loginButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(logoutButton)
					.addGap(25, 25, 25)
					.addComponent(sairButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setSize(385, 295);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Rui Freitas
	private JButton sairButton;
	private JLabel label1;
	private JButton loginButton;
	private JButton registarButton;
	private JButton logoutButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
