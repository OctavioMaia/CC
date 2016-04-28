/*
 * Created by JFormDesigner on Thu Apr 28 23:19:05 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Rui Freitas
 */
public class MusicSearch extends JFrame {
	public MusicSearch() {
		initComponents();
	}

	private void buttonCancelarActionPerformed(ActionEvent e) {
		this.dispose();
	}

	private void buttonSearchActionPerformed(ActionEvent e) {
		//codigo para procurar por uma banda ou musica
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		label1 = new JLabel();
		textBanda = new JTextField();
		label2 = new JLabel();
		textMusica = new JTextField();
		buttonCancelar = new JButton();
		buttonSearch = new JButton();

		//======== this ========
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setText("Band:");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- label2 ----
		label2.setText("Music (.ext)");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- buttonCancelar ----
		buttonCancelar.setText("Cancelar");
		buttonCancelar.addActionListener(e -> buttonCancelarActionPerformed(e));

		//---- buttonSearch ----
		buttonSearch.setText("Search");
		buttonSearch.addActionListener(e -> buttonSearchActionPerformed(e));

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(label1, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
								.addComponent(label2, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(contentPaneLayout.createParallelGroup()
								.addComponent(textBanda)
								.addGroup(contentPaneLayout.createSequentialGroup()
									.addComponent(textMusica, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGap(0, 214, Short.MAX_VALUE)
							.addComponent(buttonSearch)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(buttonCancelar)))
					.addContainerGap())
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addGap(39, 39, 39)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label1)
						.addComponent(textBanda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(label2)
						.addComponent(textMusica, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(buttonCancelar)
						.addComponent(buttonSearch))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Rui Freitas
	private JLabel label1;
	private JTextField textBanda;
	private JLabel label2;
	private JTextField textMusica;
	private JButton buttonCancelar;
	private JButton buttonSearch;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
