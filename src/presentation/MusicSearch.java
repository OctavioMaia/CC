/*
 * Created by JFormDesigner on Thu Apr 28 23:19:05 BST 2016
 */

package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;

/**
 * @author Rui Freitas
 */
public class MusicSearch extends JFrame {
	
	private Menu fatherFrame;
	
	public MusicSearch(JFrame father) {
		this.fatherFrame = (Menu)father;
		initComponents();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt){
                father.setVisible(true);
            }
        });
	}

	private void buttonCancelarActionPerformed(ActionEvent e) {
		this.fatherFrame.setVisible(true);
		this.dispose();
	}

	private void buttonRequestActionPerformed(ActionEvent e) {
		Map<String, String> res = fatherFrame.cliente.consultRequest(this.textBanda.getText(), this.textMusic.getText(), this.textExt.getText());
		if(res!=null){
			if(res.size()==0){
				JOptionPane.showMessageDialog(this,
					    "Nenhum utilizador contem a musica pedida",
					    "Music Request Message",
					    JOptionPane.PLAIN_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(this,
					    "Existem utilizadores com esta musica mas a função de comunicação ainda nao está disponivel",
					    "Music Request Message",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}
		this.fatherFrame.setVisible(true);
		this.dispose();
	}



	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Rui Freitas
		label1 = new JLabel();
		textBanda = new JTextField();
		label2 = new JLabel();
		textMusic = new JTextField();
		buttonExit = new JButton();
		buttonRequest = new JButton();
		label3 = new JLabel();
		textExt = new JTextField();

		//======== this ========
		setTitle("Music Request");
		Container contentPane = getContentPane();

		//---- label1 ----
		label1.setText("Band:");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- label2 ----
		label2.setText("Music:");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 14));

		//---- buttonExit ----
		buttonExit.setText("Exit");
		buttonExit.addActionListener(e -> buttonCancelarActionPerformed(e));

		//---- buttonRequest ----
		buttonRequest.setText("Request");
		buttonRequest.addActionListener(e -> buttonRequestActionPerformed(e));

		//---- label3 ----
		label3.setText("Ext(.ext)");
		label3.setFont(new Font("Tahoma", Font.PLAIN, 14));

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
									.addComponent(textMusic, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))))
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
							.addGap(0, 234, Short.MAX_VALUE)
							.addComponent(buttonRequest)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(buttonExit))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(label3, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addGap(10, 10, 10)
							.addComponent(textExt, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 182, Short.MAX_VALUE)))
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
						.addComponent(textMusic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(label3)
						.addComponent(textExt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(buttonExit)
						.addComponent(buttonRequest))
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
	private JTextField textMusic;
	private JButton buttonExit;
	private JButton buttonRequest;
	private JLabel label3;
	private JTextField textExt;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
