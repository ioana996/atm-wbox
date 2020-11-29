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
        List<Banknote> atmBalance = banknoteRepository.findAllDesc();
        int[] result = new int[5];
        int i = 0;
        int[] divisor = {100, 50, 10, 5, 1};

        //atmBalance.sort(Comparator.comparing(Banknote::getValue).reversed());
        // validate that required amount is a positive integer
        if(amount > 0 && Math.floor(amount) == amount) {
            // notify the client for withdrawals that surpass 200 RON
            if(amount > 200) {
                publishCustomEvent(EventType.BIGAMOUNT);
            }
            // check there is enough money in the atm
            if(checkAtmBalance(atmBalance) >= amount) {
                // determine the optimal banknote repartition
                atmValidator.calculateOptimalRepartition(result, atmBalance, amount,
                        divisor, i);
                // validate that there are enough banknotes for the optimal repartition
                if(calculatedAmount(result) == amount) {
                    // print optimal repartition
                    printOptimalAmount(atmBalance, result);
                    // update atm balance
                    for(int j = 0; j < result.length; j++) {
                        if(result[j] != 0) {
                            Banknote b = atmBalance.get(j);
                            banknoteRepository.save(b);
                            // notify admin for critical changes in the balance
                            notifyAdmin(b);
                        }
                    }
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

    public void publishCustomEvent(EventType type) {
        CustomEvent customEvent = new CustomEvent(this, type);
        applicationEventPublisher.publishEvent(customEvent);
    }

    public float calculateThresholdAmount(float threshold, Banknote b) {
        float result = (threshold * (float)b.getInitial_amount()) / (float)100;
        return result;
    }

    public void notifyAdmin(Banknote b) {
        switch(b.getValue()) {
            case 100:
                float thresholdAmount1 = calculateThresholdAmount(10, b);
                float thresholdAmount2 = calculateThresholdAmount(20, b);
                if((float)b.getLeft_amount() < thresholdAmount1) {
                    publishCustomEvent(EventType.UNDER10);
                } else if(((float)b.getLeft_amount() >= thresholdAmount1)
                        && ((float)b.getLeft_amount() < thresholdAmount2)) {
                    publishCustomEvent(EventType.UNDER20);
                }
                break;
            case 50:
                float thresholdAmount = calculateThresholdAmount(15, b);
                if((float)b.getLeft_amount() < thresholdAmount) {
                    publishCustomEvent(EventType.UNDER15);
                }
                break;
        }
    }

    public void printOptimalAmount(List<Banknote> atmBalance, int[] result) {
        System.out.println();
        for(int j = 0; j < result.length; j++) {
            System.out.println(atmBalance.get(j).getValue() + " RON: " + result[j] + " banknotes");
        }
        System.out.println();
    }
}
