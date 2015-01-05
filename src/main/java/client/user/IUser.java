package client.user;

import server.IChatRoom;
import client.cmd.ICmd;

public interface IUser {
		
	public String getName(); 

	public void setName(String name);
	
	public IChatRoom getRoom();
	
	public void setRoom(IChatRoom room);

	public String getDisplayMessage();

	public void setGetNameCmd(ICmd getNameCmd);
	
	public void setDisplayMessageCmd(ICmd displayMessageCmd);
}