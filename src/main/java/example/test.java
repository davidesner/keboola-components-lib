/*
 */
package example;

import esnerda.keboola.components.KBCException;
import esnerda.keboola.components.appstate.LastState;
import esnerda.keboola.components.configuration.ConfigFormat;
import esnerda.keboola.components.configuration.IKBCParameters;
import example.Config.KBCParametersImpl;
import esnerda.keboola.components.configuration.handler.ConfigHandlerBuilder;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import esnerda.keboola.components.configuration.handler.KBCConfigurationEnvHandler;
import esnerda.keboola.components.configuration.tableconfig.ManifestFile;
import esnerda.keboola.components.configuration.tableconfig.StorageTable;
import example.Config.LastStateImpl;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2016
 */
public class test {

    public static void main(String[] args) {
        String dataPath = ("./test/data/");

        try {

            KBCConfigurationEnvHandler handler = ConfigHandlerBuilder.create(KBCParametersImpl.class)
                    .hasInputTables(true)
                    .setStateFileType(LastStateImpl.class)
                    .build();

            //process the configuration
            handler.processConfigFile(new File(dataPath));

            //get parameters
            KBCParametersImpl pars = (KBCParametersImpl) handler.getParameters();
            System.out.println("Client ID parameter is: " + pars.getClientId());

            //get statefile
            LastStateImpl ls = (LastStateImpl) handler.getStateFile();
            System.out.println("Last run was at: " + ls.getLastRunDate());

            //get input tables
            List<StorageTable> sTables = handler.getInputTables();

            System.out.println("Provided Input tables are:");
            for (StorageTable s : sTables) {
                System.out.println(s.getCsvTable().getName());
            }

            ls.setLastRunDate(Instant.MIN);
            //write state file
            handler.writeStateFile(ls);

            //write manifest file
            File newFile = new File(handler.getOutputTablesPath() + File.separator + "newFile.csv");
            try {
                newFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }

            handler.writeManifestFile(new ManifestFile(newFile.getName(), handler.getOutputTablesPath(), true, new String[]{"id"}, ",", "\""));

            System.out.println("Finished successfuly..");
        } catch (KBCException ex) { // print error to sterr and exit with appropriate status code
            System.err.printf(ex.getMessage());
            System.exit(ex.getSeverity());
        }

    }
}
