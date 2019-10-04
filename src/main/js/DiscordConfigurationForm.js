//@flow

import React from "react";
import {
    AddEntryToTableField,
    Checkbox,
    Configuration,
    InputField, LabelWithHelpIcon, RemoveEntryOfTableButton
} from "@scm-manager/ui-components";
import {translate} from "react-i18next";

type LocalConfiguration = {
    token: string,
    channel: string
}

type Props = {
    initialConfiguration: Configuration,
    readOnly: boolean,
    onConfigurationChange: (Configuration, boolean) => void,
    t: (string) => string
}

type State = LocalConfiguration & {
    configurationChanged: boolean
}

class DiscordConfigurationForm extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            ...props.initialConfiguration
        };
    }

    valueChangeHandler = (value: string, name: string) => {
        this.setState({
            [name]: value
        }, () => this.props.onConfigurationChange({...this.state}, true));
    };

    render(): React.ReactNode {
        const {t, readOnly} = this.props;
        return (
            <>
                {this.renderConfigChangedNotification()}
                <InputField name={"token"}
                            label={t("scm-discord-plugin.form.token")}
                            helpText={t("scm-discord-plugin.form.tokenHelp")}
                            disabled={readOnly}
                            value={this.state.token}
                            onChange={this.valueChangeHandler}/>
                <InputField name={"channel"}
                            label={t("scm-discord-plugin.form.channel")}
                            helpText={t("scm-discord-plugin.form.channelHelp")}
                            disabled={readOnly}
                            value={this.state.channel}
                            onChange={this.valueChangeHandler}/>
            </>
        );
    }

    renderConfigChangedNotification = () => {
        if (this.state.configurationChanged) {
            return (
                <div className="notification is-primary">
                    <button
                        className="delete"
                        onClick={() =>
                            this.setState({...this.state, configurationChanged: false})
                        }
                    />
                    {this.props.t("scm-jenkins-plugin.configurationChangedSuccess")}
                </div>
            );
        }
        return null;
    };
}

export default translate("plugins")(DiscordConfigurationForm);
