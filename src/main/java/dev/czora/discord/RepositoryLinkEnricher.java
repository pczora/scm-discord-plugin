package dev.czora.discord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import sonia.scm.api.v2.resources.LinkBuilder;
import sonia.scm.api.v2.resources.ScmPathInfoStore;
import sonia.scm.plugin.Extension;
import sonia.scm.repository.NamespaceAndName;
import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryManager;
import sonia.scm.web.AbstractRepositoryJsonEnricher;


@Extension
public class RepositoryLinkEnricher extends AbstractRepositoryJsonEnricher {

    private final Provider<ScmPathInfoStore> scmPathInfoStore;
    private final RepositoryManager repositoryManager;

    @Inject
    public RepositoryLinkEnricher(ObjectMapper objectMapper, Provider<ScmPathInfoStore> scmPathInfoStore, RepositoryManager repositoryManager) {
        super(objectMapper);
        this.scmPathInfoStore = scmPathInfoStore;
        this.repositoryManager = repositoryManager;
    }

    @Override
    protected void enrichRepositoryNode(JsonNode repositoryNode, String namespace, String name) {
        Repository repository = repositoryManager.get(new NamespaceAndName(namespace, name));

        String linkBuilder = new LinkBuilder(scmPathInfoStore.get().get(), DiscordConfigurationResource.class)
                .method("getForRepository")
                .parameters(namespace, name)
                .href();
        this.addLink(repositoryNode, "discordConfig", linkBuilder);

    }
}