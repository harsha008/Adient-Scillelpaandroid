package lt.adient.mobility.eLPA.events;

import lt.adient.mobility.eLPA.database.model.FilterParameters;

public class FilterEvent {

    private final FilterParameters filterParameters;

    public FilterEvent(FilterParameters filterParameters) {
        this.filterParameters = filterParameters;
    }

    public FilterParameters getFilterParameters() {
        return filterParameters;
    }
}
