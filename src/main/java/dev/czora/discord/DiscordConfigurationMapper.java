package dev.czora.discord;

import com.google.inject.Inject;
import de.otto.edison.hal.Links;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import sonia.scm.api.v2.resources.LinkBuilder;
import sonia.scm.api.v2.resources.ScmPathInfoStore;
import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryPermissions;

import static de.otto.edison.hal.Link.link;
import static de.otto.edison.hal.Links.linkingTo;

@Mapper
public abstract class DiscordConfigurationMapper {
    private static final String NAME = "DISCORD";
    @Inject
    private ScmPathInfoStore scmPathInfoStore;

    public abstract DiscordConfigurationDto map(DiscordConfiguration config, @Context Repository repository);

    public abstract DiscordConfiguration map(DiscordConfigurationDto dto);

    @AfterMapping
    void appendLinks(@MappingTarget DiscordConfigurationDto target, @Context Repository repository) {
        Links.Builder linksBuilder = linkingTo().self(self(repository));
        if (RepositoryPermissions.custom(NAME, repository).isPermitted()) {
            linksBuilder.single(link("update", update(repository)));
        }
        if (RepositoryPermissions.custom(NAME, repository).isPermitted()) {
            target.add(linksBuilder.build());
        }
    }

    private String self(Repository repository) {
        LinkBuilder linkBuilder = new LinkBuilder(scmPathInfoStore.get(), DiscordConfigurationResource.class);
        return linkBuilder.method("getForRepository").parameters(repository.getNamespace(), repository.getName()).href();
    }

    private String update(Repository repository) {
        LinkBuilder linkBuilder = new LinkBuilder(scmPathInfoStore.get(), DiscordConfigurationResource.class);
        return linkBuilder.method("updateForRepository").parameters(repository.getNamespace(), repository.getName()).href();
    }
}
