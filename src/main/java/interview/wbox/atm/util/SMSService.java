package interview.wbox.atm.util;

import org.springframework.stereotype.Component;

/**
 * Created by ioana on 11/28/2020.
 */
@Component
public class SMSService {
    public void sendSMS(String message) {
        System.out.println(message);
    }
}
