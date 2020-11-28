package interview.wbox.atm.util;

import org.springframework.context.ApplicationEvent;

/**
 * Created by ioana on 11/28/2020.
 */
public class CustomEvent  extends ApplicationEvent {

    private EventType type;

    public CustomEvent(Object source, EventType type) {
        super(source);
        this.type = type;
    }
    public EventType getType() {
        return type;
    }
}
