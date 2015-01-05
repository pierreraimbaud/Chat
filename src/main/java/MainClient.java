
import java.rmi.RemoteException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClient {
	
	public static void main(String[] args) throws RemoteException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
		Runnable chat = (Runnable) context.getBean("chatUserImpl");
		new Thread(chat).start();
		context.close();
	}
}