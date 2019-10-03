package dev.czora.discord;

import com.google.inject.Inject;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import sonia.scm.repository.NamespaceAndName;
import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryManager;
import sonia.scm.repository.RepositoryPermissions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static dev.czora.discord.DiscordContext.NAME;
import static sonia.scm.ContextEntry.ContextBuilder.entity;
import static sonia.scm.NotFoundException.notFound;

@Path(DiscordConfigurationResource.DISCORD_CONFIG_PATH_V2)
public class DiscordConfigurationResource {

    static final String DISCORD_CONFIG_PATH_V2 = "v2/config/discord";

    private final RepositoryManager repositoryManager;
    private final DiscordConfigurationMapper discordConfigurationMapper;
    private final DiscordContext context;

    @Inject
    public DiscordConfigurationResource(
            RepositoryManager repositoryManager,
            DiscordConfigurationMapperImpl discordConfigurationMapper,
            DiscordContext context) {
        this.repositoryManager = repositoryManager;
        this.discordConfigurationMapper = discordConfigurationMapper;
        this.context = context;
    }



    @GET
    @Path("/{namespace}/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "success"),
            @ResponseCode(code = 401, condition = "not authenticated / invalid credentials"),
            @ResponseCode(code = 403, condition = "not authorized, the current user has no privileges to read the configuration"),
            @ResponseCode(code = 404, condition = "not found, no repository with the specified namespace and name available"),
            @ResponseCode(code = 500, condition = "internal server error")
    })
    public Response getForRepository(@PathParam("namespace") String namespace, @PathParam("name") String name) {
        Repository repository = loadRepository(namespace, name);
        RepositoryPermissions.custom(NAME, repository).check();

        return Response.ok(discordConfigurationMapper.map(context.getConfiguration(repository), repository)).build();
    }

    @PUT
    @Path("/{namespace}/{name}")
    @Consumes({MediaType.APPLICATION_JSON})
    @StatusCodes({
            @ResponseCode(code = 204, condition = "update success"),
            @ResponseCode(code = 400, condition = "Invalid body,"),
            @ResponseCode(code = 401, condition = "not authenticated / invalid credentials"),
            @ResponseCode(code = 403, condition = "not authorized, the current user does not have the privilege to change the configuration"),
            @ResponseCode(code = 404, condition = "not found, no repository with the specified namespace and name available"),
            @ResponseCode(code = 500, condition = "internal server error")
    })
    public Response updateForRepository(@PathParam("namespace") String namespace, @PathParam("name") String name, DiscordConfigurationDto updatedConfig) {
        Repository repository = loadRepository(namespace, name);
        context.storeConfiguration(discordConfigurationMapper.map(updatedConfig), repository);

        return Response.noContent().build();
    }

    private Repository loadRepository(String namespace, String name) {
        Repository repository = repositoryManager.get(new NamespaceAndName(namespace, name));
        if (repository == null) {
            throw notFound(entity(new NamespaceAndName(namespace, name)));
        }
        return repository;
    }

}
