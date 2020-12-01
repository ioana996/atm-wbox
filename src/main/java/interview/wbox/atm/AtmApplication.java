package interview.wbox.atm;

import interview.wbox.atm.service.AtmService;
import interview.wbox.atm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.*;

@SpringBootApplication
public class AtmApplication implements ApplicationListener<CustomEvent> {

	@Autowired
	private ClientNotificationService clientNotificationService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SMSService smsService;

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(AtmApplication.class, args);
		AtmService atmService = applicationContext.getBean(AtmService.class);

		for(int j = 0; j < 10; j++) {
			checkAtm(atmService);
		}
	}

	private static void checkAtm(AtmService atmService) {
		int amount = readInput();
		atmService.returnMoney(amount);
	}

	private static int readInput() {
		int amount;
		Scanner console = new Scanner(System.in);
		System.out.println("Insert amount to be withdrawn: ");
		amount = console.nextInt();
		return amount;
	}

	@Override
	public void onApplicationEvent(CustomEvent customEvent) {

		EventType eventType = customEvent.getType();

		switch(eventType) {
			case BIGAMOUNT:
				clientNotificationService.notifyClient("A withdrawal that surpasses the " +
						"amount of 200 RON is being made from your account");
				break;
			case UNDER20:
				emailService.sendEmail("The balance of the 100 RON banknotes has fallen under 20%",
										"jdoe@gmail.com");
				break;
			case UNDER10:
				smsService.sendSMS("The balance of the 100 RON banknotes has fallen under 10%",
						"0737564899");
				break;
			case UNDER15:
				emailService.sendEmail("The balance of the 50 RON banknotes has fallen under 15%",
						"jdoe@gmail.com");
				break;
		}
	}
}
