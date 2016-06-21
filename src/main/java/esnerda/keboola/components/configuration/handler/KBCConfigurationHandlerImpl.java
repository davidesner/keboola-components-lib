/*
 */
package esnerda.keboola.components.configuration.handler;

import esnerda.keboola.components.KBCException;
import esnerda.keboola.components.appstate.LastState;
import esnerda.keboola.components.configuration.parser.ConfigParser;
import esnerda.keboola.components.configuration.IKBCParameters;
import esnerda.keboola.components.configuration.KBCConfig;
import esnerda.keboola.components.configuration.KBCOutputMapping;
import esnerda.keboola.components.configuration.parser.KbcConfigParser;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Basic implementation of KBCConfigurationEnvHandler facet. Initializes and
 * manages
 * the standard KBC docker environment
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2016
 */
class KBCConfigurationHandlerImpl implements KBCConfigurationEnvHandler {

    private KBCConfig config;
    private final boolean checkInputTables;
    private final Class statefileType;
    private final Class parametersType;
    ConfigFormat format;
    private String dataPath;
    private String outputTablesPath;
    private String inTablesPath;
    private final ConfigParser confParser;

    private File[] sourceTables;
    private LastState lastState;

    KBCConfigurationHandlerImpl(boolean checkInputTables, Class statefileType, Class parametersType, ConfigParser confParser) {
        this.checkInputTables = checkInputTables;
        this.statefileType = statefileType;
        this.parametersType = parametersType;
        this.confParser = confParser;
    }

    @Override
    public void proccessConfigFile(File confFile) throws KBCException {
        this.outputTablesPath = dataPath + File.separator + "out" + File.separator + "tables";
        this.inTablesPath = dataPath + File.separator + "in" + File.separator + "tables"; //parse config

        if (!confFile.isDirectory()) {
            throw new KBCException("Specified path is not a folder!", 1);
        } else {
            confFile = new File(confFile.getPath() + File.separator + "config." + confParser.getFormat().toLowerCase());
        }

        if (!confFile.exists()) {
            throw new KBCException("config.json does not exist!", 1);
        }
        //Parse config file
        try {

            this.config = this.confParser.parseConfigFile(confFile, this.parametersType);

        } catch (Exception ex) {
            throw new KBCException("Failed to parse config file. " + ex.getMessage());
        }
        if (!config.validate()) {
            throw new KBCException(config.getValidationError());
        }

        if (checkInputTables) {
            if (config.getStorage().getInputTables().getTables().isEmpty()) {
                throw new KBCException("No input tables found. Have you specified input mapping?");
            }

            List<KBCOutputMapping> inputMp = config.getStorage().getInputTables().getTables();
            this.sourceTables = new File[inputMp.size()];
            int i = 0;
            for (KBCOutputMapping t : inputMp) {
                File src = new File(t.getDestination());
                if (!src.exists()) {
                    throw new KBCException("Source file " + src.getName() + " does not exist!", 2);

                }
                this.sourceTables[i] = src;
                i++;
            }
        }

        if (statefileType != null) {
            File stateFile = new File(dataPath + File.separator + "in" + File.separator + "state." + confParser.getFormat().toLowerCase());
            if (stateFile.exists()) {
                try {
                    lastState = (LastState) this.confParser.parseFile(stateFile, LastState.class);
                } catch (IOException ex) {
                    throw new KBCException("Unable to parse state file! " + ex.getLocalizedMessage(), 2);
                }
            }
        }
    }

    @Override
    public File[] getInputTables() {
        return this.sourceTables;
    }

    @Override
    public IKBCParameters getParameters() {
        return this.config.getParams();
    }

    @Override
    public void validateConfig(File confFile) throws KBCException {

    }

    public String getDataPath() {
        return dataPath;
    }

    public String getOutputTablesPath() {
        return outputTablesPath;
    }

    public String getInTablesPath() {
        return inTablesPath;
    }

    public LastState getLastFile() {
        return this.lastState;
    }

}
