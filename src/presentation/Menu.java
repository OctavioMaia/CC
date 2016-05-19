/*
 * Created by JFormDesigner on Wed Apr 06 11:37:12 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

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
            		state.setText("-----");
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
		this.state.setText(".....");
		this.buttonChangeFolder.setEnabled(false);
	}

	private void loginButtonActionPerformed(ActionEvent e) {
		Login log = new Login(this);
		log.setVisible(true);
		this.setVisible(false);
		this.buttonChangeFolder.setEnabled(true);
	}

	private void musicRequestButtonActionPerformed(ActionEvent e) {
		if(this.folder.getText()!=null){
			MusicSearch ms = new MusicSearch(this);
			ms.setVisible(true);
			this.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(this,
				    "É necessário existir uma pasta para a musica",
				    "Menu Folder Music Message",
				    JOptionPane.PLAIN_MESSAGE);
		}
		
	}

	protected boolean changeFolderMusic(){
		boolean res = false;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int returnValue = fileChooser.showOpenDialog(null);
	    if (returnValue == JFileChooser.APPROVE_OPTION){
		    this.cliente.setFolderMusic(fileChooser.getSelectedFile().getAbsolutePath());
		    this.folder.setText(fileChooser.getSelectedFile().getAbsolutePath());
		    System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
		    res=true;
	    }
	    return res;
	}
	
	private void buttonChangeFolderActionPerformed(ActionEvent e) {
		changeFolderMusic();
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
		musicRequestButton = new JButton();
		label2 = new JLabel();
		folder = new JLabel();
		buttonChangeFolder = new JButton();

		//======== this ========
		setTitle("Menu");
		setResizable(false);
		Container contentPane = getContentPane();

		//---- sairButton ----
		sairButton.setText("Exit");
		sairButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		sairButton.addActionListener(e -> sairButtonActionPerformed(e));

		//---- label1 ----
		label1.setText("State");
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

		//---- musicRequestButton ----
		musicRequestButton.setText("Music Request");
		musicRequestButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		musicRequestButton.addActionListener(e -> musicRequestButtonActionPerformed(e));

		//---- label2 ----
		label2.setText("Music Folder:");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- folder ----
		folder.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- buttonChangeFolder ----
		buttonChangeFolder.setText("Change");
		buttonChangeFolder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonChangeFolder.setEnabled(false);
		buttonChangeFolder.addActionListener(e -> buttonChangeFolderActionPerformed(e));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(0, 0, Short.MAX_VALUE)
							.addComponent(sairButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
						.addComponent(loginButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(logoutButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(musicRequestButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(registarButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(label2)
								.addComponent(label1))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(state, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(folder, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(buttonChangeFolder)))))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(0, 9, Short.MAX_VALUE)
							.addComponent(label1))
						.addComponent(state, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(buttonChangeFolder)
						.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(folder, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addComponent(label2)))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
					.addComponent(registarButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(loginButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(logoutButton)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(musicRequestButton)
					.addGap(82, 82, 82)
					.addComponent(sairButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setSize(525, 395);
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
	private JButton musicRequestButton;
	private JLabel label2;
	private JLabel folder;
	private JButton buttonChangeFolder;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
