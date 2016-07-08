/*
 */
package example.Config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import esnerda.keboola.components.configuration.IKBCParameters;
import esnerda.keboola.components.configuration.ValidationException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * @created 2015
 */
public class KBCParametersImpl extends IKBCParameters {

    private final static String[] REQUIRED_FIELDS = {"clientId", "clientMd5", "daysPeriod"};
    private final Map<String, Object> parametersMap;

    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("#clientMd5")
    private String clientMd5;

    @JsonProperty("daysPeriod")
    private Integer period;

    public KBCParametersImpl() {
        parametersMap = new HashMap();

    }

    @JsonCreator
    public KBCParametersImpl(@JsonProperty("clientId") String clientId, @JsonProperty("#clientMd5") String clientMd5,
            @JsonProperty("daysPeriod") String period) throws ParseException {
        parametersMap = new HashMap();
        this.clientId = clientId;
        this.clientMd5 = clientMd5;

        //set range
        if (period != null) {

        } else {

        }

        //set param map
        parametersMap.put("clientId", clientId);
        parametersMap.put("clientMd5", clientMd5);
        parametersMap.put("daysPeriod", period);

    }

    @Override
    public boolean validateParametres() throws ValidationException {
        //validate date format
        String error = "";

        error += this.missingFieldsMessage(parametersMap);

        if (error.equals("")) {
            return true;
        } else {

            throw new ValidationException("Validation error: " + error);
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientMd5() {
        return clientMd5;
    }

    public void setClientMd5(String clientMd5) {
        this.clientMd5 = clientMd5;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Map<String, Object> getParametersMap() {
        return parametersMap;
    }

    @Override
    protected String[] getRequiredFields() {
        return REQUIRED_FIELDS;
    }

}
