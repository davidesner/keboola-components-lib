/*
 */
package example.Config;

import com.fasterxml.jackson.annotation.JsonProperty;
import esnerda.keboola.components.appstate.LastState;
import java.time.Instant;

/**
 *
 * @author David Esner <esnerda at gmail.com>
 * created 2016
 */
public class LastStateImpl implements LastState {

    @JsonProperty("lastRunDate")
    private Instant lastRunDate;

    public LastStateImpl(Instant lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public Instant getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(Instant lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public LastStateImpl() {
    }

}
