package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.security.auth.login.FailedLoginException;

import client.user.IChatUser;

public interface IChatRoom extends Remote {
	public void subscribe(IChatUser user, String pseudo) throws RemoteException;
	public void unsubscribe(String pseudo) throws RemoteException;
	public void postMessage(String pseudo, String message) throws RemoteException;
    public void log(String msg) throws RemoteException;
	public boolean authentification(String username, char[] password) throws FailedLoginException, RemoteException;
}