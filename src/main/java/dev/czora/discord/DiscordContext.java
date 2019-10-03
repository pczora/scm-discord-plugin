package dev.czora.discord;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import sonia.scm.repository.Repository;
import sonia.scm.store.ConfigurationStore;
import sonia.scm.store.ConfigurationStoreFactory;


@Singleton
public class DiscordContext {

    public static final String NAME = "discord";

    @Inject
    public DiscordContext(ConfigurationStoreFactory storeFactory) {
        this.storeFactory = storeFactory;
    }

    public void storeConfiguration(DiscordConfiguration configuration, Repository repository) {
        createStore(repository).set(configuration);
    }


    public DiscordConfiguration getConfiguration(Repository repository) {
        return createStore(repository).getOptional().orElse(new DiscordConfiguration());
    }

    private ConfigurationStore<DiscordConfiguration> createStore(Repository repository) {
        return storeFactory.withType(DiscordConfiguration.class).withName(NAME).forRepository(repository).build();
    }


    private ConfigurationStoreFactory storeFactory;
}
