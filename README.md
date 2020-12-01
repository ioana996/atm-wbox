# ATM 

A Java program that simulates an ATM machine. It reads a sum from the keyboard and returns the optimal repartition of the sum (the smallest number of banknotes that the ATM can return to the client considering the amount of banknotes left in the machine). The program also provides a secured REST API that allows ADMIN users to check the ATM's balance and refill it.  

## Technologies 

Java, Spring, Spring Boot, Hibernate, Spring Data JPA, PostgreSQL, Spring Security, Maven 

## Run the app

```bash
mvn spring-boot:run
```

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

## Secured API

The program implements a secured REST API that allows RBAC via OAuth2. The API only allows ADMIN users to interact with the ATM remotely. Each user has to make a POST request to the */oauth/token* to get an access token, followed by another http request to the endpoints that interact with the database. 

The security implementation simulates an *Authorization Server*, as well as a *Resource Server* according to the OAuth2 terminology. In this case, our Resource Server is the REST API. 

- **Request to get an access token**  

Create a POST request as shown in the image below:  

![image](https://user-images.githubusercontent.com/27513879/100792024-b418f900-3422-11eb-9b8b-89340a3392fd.png)  

Also go to Authorization tab on Postman, select *Basic Auth* and then insert the client id and password with which the resource server is identified at the Autorization Server. See picture below:

![image](https://user-images.githubusercontent.com/27513879/100791800-63090500-3422-11eb-918e-4b3d54c8f93f.png)  
Now we can proceed with the request. We should get a response as seen in the picture below:

![image](https://user-images.githubusercontent.com/27513879/100792417-4ae5b580-3423-11eb-9452-fff28771e120.png)  

Since we got an access token, now we can go to the next step and make an authorized request to the REST API.  
- **REST API request**  
There are two main endpoints in this API, to check the amount left in the ATM, as well as refilling it.  
For refilling the ATM, copy the access token from the previous step and make a new request with Postman as follows:  make a POST request with the access token sent as a query parameter, a header with Content-Type set to application/json and a body that looks like the JSON below.  
 
 ```JSON
 [
        {
            "value":100,
            "initial_amount":50,
            "left_amount":50
        },
        {
            "value":50,
            "initial_amount":50,
            "left_amount":50
        },
        {
            "value":10,
            "initial_amount":100,
            "left_amount":100
        },
        {
            "value":5,
            "initial_amount":100,
            "left_amount":100
        },
        {
            "value":1,
            "initial_amount":100,
            "left_amount":100
        }
]
 ```
Below is a screenshot of the request:  
![image](https://user-images.githubusercontent.com/27513879/100793485-d6ac1180-3424-11eb-9da9-0405ba4a5143.png)  

The request to check the ATM balance is more simple. You should make a GET request to the endpoint /atm/getBalance with the access token as a query parameter. You will receive as a response a JSON with a format identical to the one shown above that represents a mapping of the database.  

## Other features
The program also implements an Event Notification System that notifies the admin when the balance drops under a certain limit, as well as the client when an amount higher than 200 RON is made from their account. 
