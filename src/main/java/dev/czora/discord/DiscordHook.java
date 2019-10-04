package dev.czora.discord;


import com.github.legman.Subscribe;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sonia.scm.EagerSingleton;
import sonia.scm.plugin.Extension;
import sonia.scm.repository.PostReceiveRepositoryHookEvent;
import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryHookType;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Collection;


@Extension @EagerSingleton
public class DiscordHook {

    private static final Logger logger =
            LoggerFactory.getLogger(DiscordHook.class);
    public static final Collection<RepositoryHookType> TYPES =
            Arrays.asList(RepositoryHookType.POST_RECEIVE);

    private DiscordHandler discordHandler;

    @Inject
    public DiscordHook(DiscordHandler discordHandler) {
        this.discordHandler = discordHandler;
    }

    @Subscribe
    public void onEvent(PostReceiveRepositoryHookEvent event) {
        Repository repository = event.getRepository();
        if (repository != null) {
            try {
                this.discordHandler.sendTextMessage(repository, "Received event for repo " + event.getRepository().getName());
            } catch (LoginException | InterruptedException e) { // TODO: Handle errors in a sensible manner
                e.printStackTrace();
            }
        }
    }
}
