package client.controller;

import server.IChatRoom;
import client.user.IUser;
import client.view.IChatUI;

public interface IChatController {

	public void init();

	public IChatUI getView();

	public IUser getUser();

	public IChatRoom getRoom();

	public void setView(IChatUI view);

	public void setUser(IUser user);

	public void setRoom(IChatRoom room);
}