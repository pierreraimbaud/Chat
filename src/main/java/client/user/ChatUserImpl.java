package client.user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.IChatRoom;
import client.cmd.ICmd;

public class ChatUserImpl extends UnicastRemoteObject implements Runnable, IChatUser, IUser {

	private static final long serialVersionUID = 1L;

	private String name = null;
	private IChatRoom room = null;	
	private ICmd getNameCmd;
	private ICmd displayMessageCmd;
	private String displayMessage = null;

	public ChatUserImpl() throws RemoteException {
		super(); // Appel au constructeur de UnicastRemoteObject
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IChatRoom getRoom () {
		return this.room;
	}

	public void setRoom(IChatRoom room) {
		this.room = room;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setGetNameCmd(ICmd getNameCmd) {
		this.getNameCmd = getNameCmd;
	}

	public void setDisplayMessageCmd(ICmd displayMessageCmd) {
		this.displayMessageCmd = displayMessageCmd;
	}

	public void run() {
		try {
			if (getNameCmd != null) {
				getNameCmd.execute();
			}
			this.room.subscribe(this, this.name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void displayMessage(String message) throws RemoteException {
		if (displayMessageCmd != null && message != null) {
			displayMessage = message;
			displayMessageCmd.execute();
		}
	}
}