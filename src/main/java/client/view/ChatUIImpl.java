package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import client.cmd.ICmd;

public class ChatUIImpl implements IChatUI {

	private ICmd unregister;
	private ICmd postMessage;

	private String title = "Logiciel de chat de l'ISTIC";
	private JFrame window = new JFrame(this.title);
	private JTextArea txtOutput = new JTextArea();
	private JTextField txtMessage = new JTextField();
	private JButton btnSend = new JButton("Envoyer");

	public ChatUIImpl() {
		JPanel panel = (JPanel) this.window.getContentPane();
		JScrollPane sclPane = new JScrollPane(txtOutput);
		panel.add(sclPane, BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(this.txtMessage, BorderLayout.CENTER);
		southPanel.add(this.btnSend, BorderLayout.EAST);
		panel.add(southPanel, BorderLayout.SOUTH);

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				window_windowClosing(e);
			}
		});
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSend_actionPerformed(e);
			}
		});
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				if (event.getKeyChar() == '\n')
					btnSend_actionPerformed(null);
			}
		});

		// Initialisation des attributs
		this.txtOutput.setBackground(new Color(100, 220, 80));
		this.txtOutput.setEditable(false);
		this.window.setSize(500, 400);
		this.window.setVisible(true);
		this.txtMessage.requestFocus();
	}

	public void window_windowClosing(WindowEvent e) {
		if (unregister != null) {
			unregister.execute();			
		}
		System.exit(-1);
	}

	public void btnSend_actionPerformed(ActionEvent e) {
		if (postMessage != null) {
			postMessage.execute();
			this.txtMessage.setText(null);
			this.txtMessage.requestFocus();
		}		
	}

	public void displayMessage(String message){
		if (message != null) {
			this.txtOutput.append(message + "\n");
			this.txtOutput.moveCaretPosition(this.txtOutput.getText().length());
		}
	}

	public String requestPseudo() {
		String pseudo = JOptionPane.showInputDialog(
				this.window, "Entrez votre pseudo : ",
				this.title,  JOptionPane.OK_OPTION
				);        
		return pseudo;
	}

	public String getMessage() {
		return this.txtMessage.getText();
	}

	public void setUnregisterCmd(ICmd unregister) {
		this.unregister = unregister;
	}

	public void setPostMessageCmd(ICmd postMessage) {
		this.postMessage = postMessage;
	}
}