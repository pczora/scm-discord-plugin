package dev.czora.discord;

import com.google.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.util.List;

@Singleton
public class DiscordHandler {
    JDABuilder jdaBuilder;
    JDA jda;


    public DiscordHandler() {
        this.jdaBuilder = new JDABuilder("INSERT TOKEN HERE");
        try {
            this.jda = jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void sendTextMessage(String textMessage) {
        List<TextChannel> textChannels = jda.getTextChannelsByName("scmm2-discord-plugin-test", true);
        MessageBuilder messageBuilder = new MessageBuilder();
        Message message = messageBuilder.setContent(textMessage).build();
        textChannels.get(0).sendMessage(message).queue();
    }
}
