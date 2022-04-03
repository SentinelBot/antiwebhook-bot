package wtf.sentinel.antiwebhook;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import wtf.sentinel.antiwebhook.listener.WebhookListener;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

/**
 * @author Brennan / skateboard
 * @since 10/10/2021
 **/
public class Bot {
    private final JDA jda;

    public Bot() throws LoginException {
        this.jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.watching("the server"))
                .enableIntents(EnumSet.allOf(GatewayIntent.class))
                .addEventListeners(new WebhookListener())
                .build();
    }

}
