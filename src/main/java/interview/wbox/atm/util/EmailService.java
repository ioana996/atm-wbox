package interview.wbox.atm.util;

import org.springframework.stereotype.Component;

/**
 * Created by ioana on 11/28/2020.
 */
@Component
public class EmailService {
    public void sendEmail(String message) {
        System.out.println(message);
    }
}