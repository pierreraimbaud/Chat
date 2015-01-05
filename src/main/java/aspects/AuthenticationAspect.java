package aspects;

import java.util.HashMap;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import server.IChatRoom;
import client.model.SampleLoginModule;
import client.user.IUser;
import com.sun.security.auth.callback.DialogCallbackHandler;

@SuppressWarnings("restriction")
@Aspect
public class AuthenticationAspect {

	//PointCut

	@Pointcut("execution(* client.user.IUser.setDisplayMessageCmd(..))")
	public void setDisplayMessageCmd() {};


	// Fonction annexe
	private boolean testLogin(SampleLoginModule lc) {
		boolean correctLogin = false;
		for (int i = 0; i < 3 && !correctLogin; i++) {
			try {
				lc.login();
				correctLogin = true;
			} catch (LoginException le) {}
		}
		return correctLogin;
	}

	//Advices

	@SuppressWarnings({"rawtypes" })
	@After("setDisplayMessageCmd()")
	public void tryLogin(JoinPoint jp) {
		IUser user = (IUser) jp.getThis();
		IChatRoom room = user.getRoom();
		SampleLoginModule lc = new SampleLoginModule(room);
		lc.initialize(new Subject(), new DialogCallbackHandler(), null, new HashMap());
		if (!testLogin(lc)) {			
			System.exit(-1);
		}
	}
}