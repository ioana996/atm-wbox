package interview.wbox.atm.service;

import interview.wbox.atm.exception.InsufficientBalanceException;
import interview.wbox.atm.exception.InvalidAmountException;
import interview.wbox.atm.exception.NoOptimalRepartitionException;
import interview.wbox.atm.model.Banknote;
import interview.wbox.atm.repository.BanknoteRepository;
import interview.wbox.atm.util.AtmValidator;
import interview.wbox.atm.util.CustomEvent;
import interview.wbox.atm.util.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ioana on 11/28/2020.
 */
@Service
public class AtmService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BanknoteRepository banknoteRepository;

    public Banknote createBanknote(Banknote banknote) {
        return banknoteRepository.save(banknote);
    }

    public void returnMoney(int amount) {
        AtmValidator atmValidator = new AtmValidator();
        List<Banknote> atmBalance = banknoteRepository.findAll();
        int[] result = new int[5];
        int i = 0;
        int[] divisior = {100, 50, 10, 5, 1};
        int[] bank = new int[5];

        atmBalance.sort(Comparator.comparing(Banknote::getValue).reversed());
        // validate that required amount is a positive integer
        if(amount > 0 && Math.floor(amount) == amount) {
            if(amount > 200) {
                // TODO notify the client
                CustomEvent customEvent = new CustomEvent(this, EventType.BIGAMOUNT);
                applicationEventPublisher.publishEvent(customEvent);
            }
            // check there is enough money in the atm
            if(checkAtmBalance(atmBalance) >= amount) {
                atmValidator.calculateOptimalRepartition(result, atmBalance, amount,
                        divisior, i);
                if(calculatedAmount(result) == amount) {
                    // TODO update database and check percentages
                } else throw new NoOptimalRepartitionException();
            } else throw new InsufficientBalanceException();
        } else throw new InvalidAmountException();
    }

    public Integer checkAtmBalance(List<Banknote> atmBalance) {
        int balance = 0;
        for(int i = 0; i < atmBalance.size(); i++) {
            balance += atmBalance.get(i).getValue() * atmBalance.get(i).getLeft_amount();
        }
        return balance;
    }
    public Integer calculatedAmount(int[] result) {
        int calculatedAmount = result[0] * 100 + result[1] * 50 +
                result[2] * 10 + result[3] * 5 + result[4];
        return calculatedAmount;
    }
}
