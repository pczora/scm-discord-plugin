package dev.czora.discord;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import sonia.scm.repository.Repository;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DiscordHandler {
    private Map<Repository, JDA> jdas;
    private DiscordContext context;


    @Inject
    public DiscordHandler(DiscordContext context) {
        this.context = context;
        this.jdas = new HashMap<>();
    }

    void sendTextMessage(Repository repository, String textMessage) throws LoginException, InterruptedException {
        JDA jda = getJdaForRepo(repository);
        String channel = this.context.getConfiguration(repository).getChannel();
        List<TextChannel> textChannels = jda.getTextChannelsByName(channel, true); // TODO: What if channel does not exist?
        MessageBuilder messageBuilder = new MessageBuilder();
        Message message = messageBuilder.setContent(textMessage).build();
        textChannels.get(0).sendMessage(message).queue();
    }

    private JDA getJdaForRepo(Repository repository) throws LoginException, InterruptedException {
        if (!this.jdas.containsKey(repository)) {
            JDA jda = createJdaForRepo(repository);
            this.jdas.put(repository, jda);
        }
        return this.jdas.get(repository);

    }

    private JDA createJdaForRepo(Repository repository) throws LoginException, InterruptedException {
        DiscordConfiguration configuration = this.context.getConfiguration(repository);
        JDABuilder builder = new JDABuilder(configuration.getToken());
        return builder.build().awaitReady(); // TODO: Dont wait blocking
    }
}
