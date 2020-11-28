package interview.wbox.atm.util;

import org.springframework.stereotype.Component;

/**
 * Created by ioana on 11/28/2020.
 */
@Component
public class ClientNotificationService {
    public void notifyClient(String message) {
        System.out.println(message);
    }
}
