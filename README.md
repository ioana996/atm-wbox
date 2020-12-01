# ATM 

A Java program that simulates an ATM machine. It reads a sum from the keyboard and returns the optimal repartition of the sum (the smallest number of banknotes that the ATM can return to the client considering the amount of banknotes left in the machine). The program also provides a secured REST API that allows ADMIN users to check the ATM's balance and refill it.

## Technologies 

Java, Spring, Spring Boot, Hibernate, Spring Data JPA, PostgreSQL, Spring Security, Maven 

## Project Structure

```bash
├───main
   ├───java
      └───interview
          └───wbox
              └───atm
                  ├───controller
                  ├───dto
                  ├───exception
                  ├───model
                  ├───repository
                  ├───security
                  ├───service
                  └───util
```

## Repartition Algorithm

The algoritm for the repartition of a sum according to the banknotes left in the ATM is a recursive algorithm.  
```java
public int[] calculateOptimalRepartition(int[] result, 
                                         List<Banknote> atmBalance,
                                         int n,
                                         int[] divisor,
                                         int i);
```  

Received parameters:
- **int[] result**   
A vector of size 5 where the results are written. Each index corresponds to a banknote considering a reversed order (100, 50, 10, 5, 1). Each number written in the *result* vector represents the number of banknotes needed from a certain value.    
**ex.**  [5, 0, 2, 0, 0]     
In this case there are needed 5 banknotes of 100 RON and 2 of 10 RON. No banknotes of 50, 5 or 1 RON are needed.  
- **List <Banknote> atmBalance**    
 This parameter is a list of the rows in the database where a track of the ATM balance is kept. Each entry has the format described in the table above. During the iterations of the repartition algorithm, only the *left_amount* column is updated to the number of banknotes left after the withdrawal of the sum. At the end of the process, this list is used to update the database.      

|     value       | initial_amount | left_amount   |  
| :-------------: | :------------: | :-----------: |  
|      100        |       50       |      37       |    
  
- **int n**     
This number represents the sum inserted by a client that wants to make a withdrawal.  
- **int[] divisior**  
This is a vector which contains the values of the banknotes in the bank in reversed order. They are used as divisors of the initial amount inserted by a client.
**ex.** [100, 50, 10, 5, 1]  

- **int i**    
This is the iteration step that goes from 0 to 5.

**How it works?**

On each iteration step *i*, the algorithm divides the amount to be withdrawn by the value of the banknote present in the *divisor* vector at index *i*. If the result is greater than 0 it means that we need banknotes from the current value. Therefore the algorithm proceeds next with the validation of the amount present in the database. If there are enough banknotes in the database, we substract the number of the banknotes needed from the *left_amount* and also update the *result* vector at the index corresponding to the current step of the iteration, namely *i*. In case there are not enough banknotes left in the atm for this operation, we take the ones left in the atm, update the value in the list to 0, as well as the values in the *result* vector to the number of banknotes extracted from the database and go to the next iteration step. When the inserted sum *(n)* is 0, it means we calculated the optimal repartition of the withdrawal amount. 

At the end of the recursive algorithm, the amount is recalculated using the *result* vector. If it equals the initial value inserted by a client it means that there are enough banknotes in the atm to proceed with the withdrawal and we update the database with the values present in the *atmBalance* list. 
