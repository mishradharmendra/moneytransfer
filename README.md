# moneytransfer REST API using the Javalin and google Juice Framework
Sample Money Transfer API
This is a simple  Api to simulate transferring money to and from accounts from a bank.
As this is a Maven project please build this project with the following via a terminal in the working project directory:

This project is created using hte Javalin and google juice framework its a micro framework to create the REST services.

Please following following instruction to run the application. This app does not required third party applicatdion server to run
one can run the application as a stand-alone application,


run

1. mvn clean

2. mvn install

Above will create the jar file inside the target folder

3> open command prompt or terminal and run

java -jar <jar Name> <portNumber>
 
e.g.

java -jar money-transfer-1.0-SNAPSHOT.jar 7000

NOTE:<Jar Name can be different if one has change name of application>

Note: Above project is created using java 11 so it assume you are running on java 11 atleast.

Following are the endpoint URLs on which are exposed in the API

get("/api/moneybank/accounts"); --- to get all the accounts in the system

post("/api/moneybank/accounts"); --add the account to the system, Following is json request is expected

"{\n" +
 "\"name\" : \"d harmendra\",\n" +
 "\"amount\" : 1200\n" +
 "}"
 

delete("/api/moneybank/accounts/:account-id"); -- to delete account from the system


get("/api/moneybank/accounts/balance/:account-id"); -- give account balance for an account


get("/api/moneybank/accounts/transaction/:account-id"); --give all transaction happend to the account


post("/api/moneybank/accounts/addBalance"); --add Balance to the account, Following is json request is expected

"{\n" +
 "\"name\" : \"dharmendra\",\n" +
 "\"amount\" : 1200\n" +
 "}";
 

post("/api/moneybank/accounts/withdraw"); -- withdraw amount from an account,Following is json request is expected

"{\n" +
 "\"name\" : \"dharmendra\",\n" +
 "\"amount\" : 1200\n" +
 "}";
 

post("/api/moneybank/accounts/transfer"); -- Money transfer API, Following is json request is expected

"{\n" +
  "\"accountFrom\" : 1,\n" +
  "\"accountTo\" : 2,\n" +
  "\"amount\" : 500\n" +
  "}";
 
 
 There are some feature that needs to be added related to currency, Now system assumes both amount in same currency.
 Added the Test cases which are self explanatory.
 
 
