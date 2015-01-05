package aspects;

import java.rmi.RemoteException;

import javax.security.auth.login.LoginException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import server.IChatRoom;

@Aspect
public class LogAspect {

	private int nbLoginAttempts = 0;

	//Pointcuts
	@Pointcut("execution(* server.IChatRoom.authentification(..))")
	public void authentification() {};

	@Pointcut("execution(* server.IChatRoom.subscribe(..))")
	public void subscribe() {};

	@Pointcut("execution(* server.IChatRoom.unsubscribe(..))")
	public void unsubscribe() {};

	@Pointcut("execution(* server.IChatRoom.postMessage(..))")
	public void postMessage() {};


	//Advices

	@AfterThrowing(pointcut = "authentification()", throwing = "e")
	public void AfterThrowingLoginException(LoginException e) {
		nbLoginAttempts++;
		if (nbLoginAttempts < 3) {
			System.err.println("[LOG]\tLogin ou mot de passe incorrect(s)");
		} else {
			System.err.println("[LOG]\t3 tentatives ratees - Fermeture");
		}		
	}

	@Before("subscribe()")
	public void BeforeSubscribe(JoinPoint jp){
		String pseudo = (String) jp.getArgs()[1];
		IChatRoom room = (IChatRoom) jp.getThis();
		try {
			room.log("[LOG]\tConnexion de " + pseudo);
		} catch (RemoteException e) {}
	}

	@Before("unsubscribe()")
	public void BeforeUnsubscribe(JoinPoint jp){
		String pseudo = (String) jp.getArgs()[0];
		IChatRoom room = (IChatRoom) jp.getThis();
		try {
			room.log("[LOG]\tDeconnexion de " + pseudo);
		} catch (RemoteException e) {}
	}

	@Before("postMessage()")
	public void BeforePostMessage(JoinPoint jp){
		String pseudo = (String) jp.getArgs()[0];
		String message = (String) jp.getArgs()[1];
		IChatRoom room = (IChatRoom) jp.getThis();
		try {
			room.log("[LOG]\t" + pseudo + " dit : " + message);
		} catch (RemoteException e) {}
	}

	@AfterThrowing(pointcut = "unsubscribe()", throwing = "e")
	public void AfterThrowingUnsubscribe(RemoteException e){
		System.err.println("[LOG]\tDesinscription impossible");
	}

	@AfterThrowing(pointcut = "postMessage()", throwing = "e")
	public void AfterThrowingPostMessage(RemoteException e){
		System.err.println("[LOG]\tEnvoi du message impossible"); 
	}
}