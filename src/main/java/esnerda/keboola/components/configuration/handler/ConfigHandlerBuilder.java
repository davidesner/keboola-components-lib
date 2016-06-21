/*
 */
package esnerda.keboola.components.configuration.handler;

import esnerda.keboola.components.KBCException;
import esnerda.keboola.components.configuration.ConfigFormat;
import esnerda.keboola.components.configuration.parser.KbcConfigParser;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2016
 */
public class ConfigHandlerBuilder {

    private boolean checkInputTables;
    private Class statefileType;
    private Class parametersType;
    ConfigFormat format;

    public static ConfigHandlerBuilder create(Class parametersType) {
        return new ConfigHandlerBuilder(parametersType);
    }

    /**
     * Creates ConfigHandler with default parameters. Sets the environment
     * without input tables and without state file. Expect default json format.
     *
     * @param parametersType
     * @return
     */
    public static ConfigHandlerBuilder createDefault(Class parametersType) {
        ConfigHandlerBuilder c = new ConfigHandlerBuilder(parametersType).
                hasInputTables(false)
                .setFormat(ConfigFormat.json)
                .setStateFileType(null);

        return c;
    }

    private ConfigHandlerBuilder(Class pType) {
        this.parametersType = pType;
    }

    public ConfigHandlerBuilder hasInputTables(boolean value) {
        this.checkInputTables = value;
        return this;
    }

    public ConfigHandlerBuilder setFormat(ConfigFormat f) {
        this.format = f;
        return this;
    }

    public ConfigHandlerBuilder setParametersType(Class pType) {
        this.parametersType = pType;
        return this;
    }

    public ConfigHandlerBuilder setStateFileType(Class sType) {
        this.statefileType = sType;
        return this;
    }

    public KBCConfigurationEnvHandler build() throws KBCException {
        try {
            return new KBCConfigurationHandlerImpl(checkInputTables, statefileType, parametersType, new KbcConfigParser(format.name()));
        } catch (Exception ex) {
            throw new KBCException(ex.getMessage());
        }
    }

}
