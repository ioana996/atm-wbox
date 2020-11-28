package interview.wbox.atm.service;

import interview.wbox.atm.exception.InvalidAmountException;
import interview.wbox.atm.model.Banknote;
import interview.wbox.atm.repository.BanknoteRepository;
import interview.wbox.atm.util.AtmValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ioana on 11/28/2020.
 */
@Service
public class AtmService {

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
        for(int j = 0; j < 5; j++) {
            System.out.println(atmBalance.get(j).getValue());
        }
        // validate that required amount is a positive integer
        if(amount > 0 && Math.floor(amount) == amount) {
            // TODO check balance is bigger than amount or throw exception
            atmValidator.calculateOptimalRepartition(result, atmBalance, amount,
                    divisior, i);
            for(int j = 0; j < 5; j++) {
                System.out.println("Resultat:" + result[j]);
            }
            for(int j = 0; j < 5; j++) {
                System.out.println(atmBalance.get(j).getValue() + " ## " +
                        atmBalance.get(j).getLeft_amount());
            }
        } else throw new InvalidAmountException();
    }
}
