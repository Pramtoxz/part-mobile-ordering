package ahm.parts.ordering.data.model.home.calendar;

import ahm.parts.ordering.ui.home.home.adapter.Displayable;

public class Calendar implements Displayable {
    public int id;
    public String name;

    public Calendar(String name) {
        this.name = name;
    }

    @Override
    public String display() {
        return name;
    }
}

