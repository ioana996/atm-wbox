package interview.wbox.atm.util;

import interview.wbox.atm.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by ioana on 11/28/2020.
 */
@Component
public class SMSService {
    public void sendSMS(String message, String phoneNumber) {
        System.out.println(message);
        System.out.println("Emergency level: Critical" );
        System.out.println("Phone number: " + phoneNumber);
    }
}
