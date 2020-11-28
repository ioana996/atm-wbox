package interview.wbox.atm;

import interview.wbox.atm.exception.InsufficientBalanceException;
import interview.wbox.atm.model.Banknote;
import interview.wbox.atm.repository.BanknoteRepository;
import interview.wbox.atm.service.AtmService;
import interview.wbox.atm.util.AtmValidator;
import interview.wbox.atm.util.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class AtmApplication {

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
		atmService.returnMoney(8433);
	}

	public static void checkAtm(AtmService atmService) {
		Operations operations = new Operations();
		int amount = operations.readInput();
		atmService.returnMoney(amount);
	}
}
