package interview.wbox.atm.service;

import interview.wbox.atm.model.Banknote;
import interview.wbox.atm.repository.BanknoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ioana on 11/28/2020.
 */
@Service
public class BanknoteService {

    @Autowired
    private BanknoteRepository banknoteRepository;

}
