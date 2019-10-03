// @flow

import { ConfigurationBinder as cfgBinder } from "@scm-manager/ui-components";
import LocalDiscordConfiguration from "./LocalDiscordConfiguration";

cfgBinder.bindRepositorySetting("/discord", "scm-discord-plugin.nav-link", "discordConfig", LocalDiscordConfiguration)