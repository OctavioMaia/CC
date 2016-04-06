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
	
	protected String stat;
	protected Client cliente;
	
	public Menu(String ipServer, int portServer) {
		this.stat = null;
		this.cliente = new Client(ipServer, portServer);
		initComponents();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
            	super.windowActivated(e);
            	if(stat==null){
            		state.setText("?????");
            	}else{
            		state.setText(stat);
            	}
            }
        });
	}

	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Client getCliente() {
		return cliente;
	}
	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
	
	public JButton getLoginButton() {
		return loginButton;
	}
	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}
	public JButton getRegistarButton() {
		return registarButton;
	}
	public void setRegistarButton(JButton registarButton) {
		this.registarButton = registarButton;
	}
	public JButton getLogoutButton() {
		return logoutButton;
	}
	public void setLogoutButton(JButton logoutButton) {
		this.logoutButton = logoutButton;
	}
	private void registarButtonMouseClicked(MouseEvent e) {
		Register res = new Register(this);
		res.setVisible(true);
		this.setVisible(false);
	}

	private void sairButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	private void logoutButtonActionPerformed(ActionEvent e) {
		cliente.logout();
		this.registarButton.setEnabled(true);
		this.loginButton.setEnabled(true);
	}

	private void loginButtonActionPerformed(ActionEvent e) {
		Login log = new Login(this);
		log.setVisible(true);
		this.setVisible(false);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		sairButton = new JButton();
		label1 = new JLabel();
		loginButton = new JButton();
		registarButton = new JButton();
		logoutButton = new JButton();
		state = new JLabel();

		//======== this ========
		setTitle("Menu");
		setResizable(false);
		Container contentPane = getContentPane();

		//---- sairButton ----
		sairButton.setText("Sair");
		sairButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		sairButton.addActionListener(e -> sairButtonActionPerformed(e));

		//---- label1 ----
		label1.setText("Estado:");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- loginButton ----
		loginButton.setText("Login");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginButton.addActionListener(e -> loginButtonActionPerformed(e));

		//---- registarButton ----
		registarButton.setText("Registar");
		registarButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		registarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				registarButtonMouseClicked(e);
			}
		});

		//---- logoutButton ----
		logoutButton.setText("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logoutButton.setEnabled(false);
		logoutButton.addActionListener(e -> logoutButtonActionPerformed(e));

		//---- state ----
		state.setFont(new Font("Tahoma", Font.PLAIN, 14));

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
							.addGap(18, 18, 18)
							.addComponent(state, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
						.addComponent(loginButton, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
						.addComponent(logoutButton, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
						.addComponent(registarButton, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(label1)
						.addComponent(state))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
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
	private JLabel state;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
