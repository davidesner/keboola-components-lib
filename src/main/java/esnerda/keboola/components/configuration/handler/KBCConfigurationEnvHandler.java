/*
 */
package esnerda.keboola.components.configuration.handler;

import esnerda.keboola.components.KBCException;
import esnerda.keboola.components.configuration.IKBCParameters;
import java.io.File;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2016
 */
public interface KBCConfigurationEnvHandler {

    public void proccessConfigFile(File confFile) throws KBCException;

    public File[] getInputTables();

    public IKBCParameters getParameters();

    public void validateConfig(File confFile) throws KBCException;

    public static enum ConfigFormat {
        yml, json
    }
}
