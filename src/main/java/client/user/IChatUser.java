package client.user;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatUser extends Remote {	
	public void displayMessage(String message) throws RemoteException; 
}