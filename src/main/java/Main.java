import wtf.sentinel.antiwebhook.Bot;

import javax.security.auth.login.LoginException;

/**
 * @author Brennan / skateboard
 * @since 10/10/2021
 **/
public class Main {

    public static void main(String[] args) {
        try {
            new Bot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
