package client.view;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import client.cmd.ICmd;

public interface IChatUI {

	public void window_windowClosing(WindowEvent e);

	public void btnSend_actionPerformed(ActionEvent e);

	public void displayMessage(String message);

	public String requestPseudo();

	public String getMessage();

	public void setUnregisterCmd(ICmd unregister);

	public void setPostMessageCmd(ICmd postMessage);
}