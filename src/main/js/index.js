// @flow

import { ConfigurationBinder as cfgBinder } from "@scm-manager/ui-components";
import DiscordConfiguration from "./DiscordConfiguration";

cfgBinder.bindRepositorySetting("/discord", "scm-discord-plugin.nav-link", "discordConfig", DiscordConfiguration);