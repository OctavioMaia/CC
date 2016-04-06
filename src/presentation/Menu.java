/*
 * Created by JFormDesigner on Wed Apr 06 11:37:12 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Octavio Maia
 */
public class Menu extends JFrame {
	public Menu() {
		initComponents();
	}

	private void buttonRegistarActionPerformed(ActionEvent e) {
		JFrame registar = new Register();
		registar.setVisible(true);
	}

	private void button1ActionPerformed(ActionEvent e) {
		this.dispose();
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Octavio Maia
		buttonRegistar = new JButton();
		button1 = new JButton();

		//======== this ========
		setTitle("Menu");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- buttonRegistar ----
		buttonRegistar.setText("Registar");
		buttonRegistar.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonRegistar.addActionListener(e -> {
			button1ActionPerformed(e);
			buttonRegistarActionPerformed(e);
			buttonRegistarActionPerformed(e);
		});
		contentPane.add(buttonRegistar);
		buttonRegistar.setBounds(15, 220, 95, 30);

		//---- button1 ----
		button1.setText("Sair");
		button1.setFont(new Font("Tahoma", Font.BOLD, 14));
		button1.addActionListener(e -> button1ActionPerformed(e));
		contentPane.add(button1);
		button1.setBounds(280, 220, 75, 30);

		contentPane.setPreferredSize(new Dimension(385, 295));
		setSize(385, 295);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Octavio Maia
	private JButton buttonRegistar;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
