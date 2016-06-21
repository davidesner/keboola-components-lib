/*
 */
package test;

import esnerda.keboola.components.KBCException;
import esnerda.keboola.components.configuration.KBCParametersImpl;
import esnerda.keboola.components.configuration.handler.ConfigHandlerBuilder;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import esnerda.keboola.components.configuration.handler.KBCConfigurationEnvHandler;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2016
 */
public class test {

    public static void main(String[] args) {
        String dataPath = args[0];

        try {
            KBCConfigurationEnvHandler handler = ConfigHandlerBuilder.createDefault(KBCParametersImpl.class).build();
            handler.proccessConfigFile(new File(dataPath));
            KBCParametersImpl pars = (KBCParametersImpl) handler.getParameters();
        } catch (KBCException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
