// @flow
import React from "react";
import {Title, Configuration} from "@scm-manager/ui-components";
import { translate } from "react-i18next";
import DiscordConfigurationForm from "./DiscordConfigurationForm";

type Props = {
    link: string,
    t: (string) => string
};

class DiscordConfiguration extends React.Component<Props> {

    render(): React.ReactNode {
        const {t, link} = this.props;
        return (
            <>
                <Title title={t("scm-discord-plugin.form.header")}/>
                <Configuration link={link} t={t} render={props => <DiscordConfigurationForm {...props}/>}/>
            </>
        );
    }
}

export default translate("plugins")(DiscordConfiguration);
