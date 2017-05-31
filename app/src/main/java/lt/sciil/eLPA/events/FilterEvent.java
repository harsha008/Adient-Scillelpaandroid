package lt.sciil.eLPA.events;

import lt.sciil.eLPA.database.model.FilterParameters;

public class FilterEvent {

    private final FilterParameters filterParameters;

    public FilterEvent(FilterParameters filterParameters) {
        this.filterParameters = filterParameters;
    }

    public FilterParameters getFilterParameters() {
        return filterParameters;
    }
}
