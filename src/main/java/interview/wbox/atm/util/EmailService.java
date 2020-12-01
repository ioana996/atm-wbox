package interview.wbox.atm.util;

import interview.wbox.atm.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by ioana on 11/28/2020.
 */
@Component
public class EmailService {
    public void sendEmail(String message, String email) {
        System.out.println(message);
        System.out.println("Emergency level: Warning" );
        System.out.println("Email address: " + email);
    }
}
