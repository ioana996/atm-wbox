package interview.wbox.atm;

import interview.wbox.atm.exception.InsufficientBalanceException;
import interview.wbox.atm.model.Banknote;
import interview.wbox.atm.repository.BanknoteRepository;
import interview.wbox.atm.service.AtmService;
import interview.wbox.atm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.List;

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
		BanknoteRepository banknoteRepository = applicationContext.getBean(BanknoteRepository.class);
//		Banknote b1 = new Banknote(100, 50, 50);
//		Banknote b2 = new Banknote(50, 50, 50);
//		Banknote b3 = new Banknote(10, 100, 100);
//		Banknote b4 = new Banknote(5, 100, 100);
//		Banknote b5 = new Banknote(1, 100, 100);
//		atmService.createBanknote(b1);
//		atmService.createBanknote(b2);
//		atmService.createBanknote(b3);
//		atmService.createBanknote(b4);
//		atmService.createBanknote(b5);
		//List<Banknote> result = banknoteRepository.findAll();
		//System.out.println(result.get(0).getInitial_amount());
		//checkAtm(atmService);
		atmService.returnMoney(342);
	}

	public static void checkAtm(AtmService atmService) {
		Operations operations = new Operations();
		int amount = operations.readInput();
		atmService.returnMoney(amount);
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
				emailService.sendEmail("The balance of the 100 RON banknotes has fallen under 20%");
				break;
			case UNDER10:
				smsService.sendSMS("The balance of the 100 RON banknotes has fallen under 10%");
				break;
			case UNDER15:
				emailService.sendEmail("The balance of the 50 RON banknotes has fallen under 15%");
				break;
		}
	}
}
