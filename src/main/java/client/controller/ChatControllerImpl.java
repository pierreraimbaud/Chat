package client.controller;

import java.rmi.RemoteException;

import server.IChatRoom;
import client.cmd.ICmd;
import client.user.IUser;
import client.view.IChatUI;

public class ChatControllerImpl implements IChatController {

	private IChatUI view;
	private IUser user;
	private IChatRoom room;

	public IChatUI getView() {
		return view;
	}

	public IUser getUser() {
		return user;
	}

	public IChatRoom getRoom() {
		return room;
	}

	public void setView(IChatUI view) {
		this.view = view;
	}

	public void setUser(IUser user) {
		this.user = user;
	}

	public void setRoom(IChatRoom room) {
		this.room = room;
	}

	//Init pour commandes
	public void init() {
		view.setPostMessageCmd(new ICmd(){
			public void execute() {
				handlePostMessageCmd();
			}});
		view.setUnregisterCmd(new ICmd(){
			public void execute() {
				handleUnregisterCmd();
			}});
		user.setGetNameCmd(new ICmd(){
			public void execute() {
				handleRequestPseudoCmd();
			}});
		user.setDisplayMessageCmd(new ICmd(){
			public void execute() {
				handleDisplayMessageCmd();
			}}); 
	}

	public void handlePostMessageCmd(){
		try {
			room.postMessage(user.getName(), view.getMessage());
		} catch (RemoteException e) {}
	}

	private void handleUnregisterCmd(){
		try {
			room.unsubscribe(user.getName());
		} catch (RemoteException e) {}
	}

	private void handleRequestPseudoCmd(){
		String pseudo = view.requestPseudo();
		if (pseudo == null) {
			System.exit(0);
		} else {
			user.setName(pseudo);
		}
	}

	private void handleDisplayMessageCmd() {
		String val = user.getDisplayMessage();
		if (val != null){
			view.displayMessage(val);
		}
	}
}