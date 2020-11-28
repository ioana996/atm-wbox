package interview.wbox.atm.util;

import java.util.Scanner;

/**
 * Created by ioana on 11/28/2020.
 */
public class Operations {

    public int readInput() {
        int amount = 0;
        Scanner console = new Scanner(System.in);
        System.out.println("Insert amount to be withdrawn: ");
        amount = console.nextInt();
        return amount;
    }
}
