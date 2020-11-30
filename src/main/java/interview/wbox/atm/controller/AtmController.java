package interview.wbox.atm.controller;

import interview.wbox.atm.model.Banknote;
import interview.wbox.atm.repository.BanknoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ioana on 11/29/2020.
 */
@RestController
public class AtmController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_CLIENT = "ROLE_CLIENT";

    @Autowired
    private BanknoteRepository banknoteRepository;

    @PostMapping(value = "/addMoney", consumes = "application/json", produces = "application/json")
    public void addMoney (@RequestBody List<Banknote> refillBanknotes) {
        for (Banknote rb : refillBanknotes) {
            Banknote b = banknoteRepository.findByValue(rb.getValue());
            b.setLeft_amount(rb.getInitial_amount() + b.getLeft_amount());
            b.setInitial_amount(b.getLeft_amount());
            banknoteRepository.save(b);
        }
    }

    @RequestMapping(value = "/getBalance", produces = "application/json")
    public List<Banknote> getAtmBalance () {
        return banknoteRepository.findAll();
    }

}
