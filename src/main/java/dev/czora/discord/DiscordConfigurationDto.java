package dev.czora.discord;

import de.otto.edison.hal.HalRepresentation;
import de.otto.edison.hal.Links;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiscordConfigurationDto extends HalRepresentation {

    private String token;
    private String channel;

    @Override
    @SuppressWarnings("squid:S1185") // We want to have this method available in this package
    protected HalRepresentation add(Links links) {
        return super.add(links);
    }
}
