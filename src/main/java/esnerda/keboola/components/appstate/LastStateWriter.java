/*
 */
package esnerda.keboola.components.appstate;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import esnerda.keboola.components.configuration.ConfigFormat;

/**
 *
 * author David Esner <code>&lt;esnerda at gmail.com&gt;</code>
 * created 2016
 */
public class LastStateWriter {

    public static void writeStateFile(String resultStateFolderPath, LastState lstate, ConfigFormat format) throws IOException {
        final ObjectMapper mapper;
        switch (format) {
            case json:
                mapper = new ObjectMapper(new JsonFactory());
                break;
            default:
                mapper = new ObjectMapper(new JsonFactory());
                break;
        }
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.findAndRegisterModules();
        File stateFile = new File(resultStateFolderPath + File.separator + "state.json");
        mapper.writeValue(stateFile, lstate);
    }

}
