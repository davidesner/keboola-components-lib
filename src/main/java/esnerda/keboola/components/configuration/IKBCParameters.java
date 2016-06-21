/*
 */
package esnerda.keboola.components.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2016
 */
public abstract class IKBCParameters {

    /**
     * Returns list of required fields missing in config
     *
     * @return
     */
    private List<String> getMissingFields(Map<String, Object> parametersMap) {

        List<String> missing = new ArrayList<>();
        for (int i = 0; i < getRequiredFields().length; i++) {
            Object value = parametersMap.get(getRequiredFields()[i]);
            if (value == null) {
                missing.add(getRequiredFields()[i]);
            }
        }

        if (missing.isEmpty()) {
            return null;
        }
        return missing;
    }

    protected final String missingFieldsMessage(Map<String, Object> parametersMap) {

        List<String> missingFields = getMissingFields(parametersMap);
        String msg = "";
        if (missingFields != null && missingFields.size() > 0) {
            msg = "Required config fields are missing: ";
            int i = 0;
            for (String fld : missingFields) {
                if (i < missingFields.size()) {
                    msg += fld + ", ";
                } else {
                    msg += fld;
                }
            }
        }
        return msg;
    }

    protected abstract String[] getRequiredFields();

    protected abstract boolean validateParametres() throws ValidationException;
}
