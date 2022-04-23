package by.discord.lottery.config;

import by.discord.lottery.listener.LotteryEventsListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@Component
public class BotConfig {

    private static final String token = "OTY3Mzg5MDk0NTc5Njk5NzUy.YmPlTQ.to8Yoq9ow2DwL8gkFmdgoRlsJDI";
    public static JDA jda;

    public BotConfig(LotteryEventsListener lotteryEventsListener) throws LoginException {
        jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(lotteryEventsListener);
    }

}
