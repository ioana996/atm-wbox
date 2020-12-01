package interview.wbox.atm.util;

import interview.wbox.atm.model.Banknote;

import java.util.List;

/**
 * Created by ioana on 11/28/2020.
 */
public class AtmValidator {

    public int[] calculateOptimalRepartition(int[] result, List<Banknote> atmBalance,
                                             int n,
                                             int[] divisor,
                                             int i) {
        if(n > 0 && i < divisor.length) {
            int x = n / divisor[i];
            if(x > 0 && atmBalance.get(i).getLeft_amount() > 0) {
                int aux;
                if(atmBalance.get(i).getLeft_amount() >= x) {
                    aux = x;
                    atmBalance.get(i).setLeft_amount(atmBalance.get(i).getLeft_amount() - x);
                    result[i] = aux;
                    n = n % divisor[i];
                } else {
                    aux = atmBalance.get(i).getLeft_amount();
                    atmBalance.get(i).setLeft_amount(0);
                    result[i] = aux;
                    n = n - aux * divisor[i];
                }
                i++;
            } else {
                i++;
            }
            return calculateOptimalRepartition(result,atmBalance, n, divisor, i);
        }
        return result;
    }
}
