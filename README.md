## Personal Budget-Balancing System (PBBS)

* Iteration 2 - README.md

---

### Background

1. Project name: **Personal Budget-Balancing System (PBBS)**.
2. Repository link: [PBBS Repository](https://github.com/tommyvct/FigureHub_3350).
	
	* You can view all the source code files from the link above.
3. Developer logs: [PBBS Developer log (iteration 2)](https://github.com/tommyvct/FigureHub_3350/projects/3).
	* The developer logs are written by members in the development team, used to record the process of how this project proceeded and what actions members performed.
	* You can view the developer logs from the link above. It is also sorted as a text document in the main zip file directory.

4. Team members:
	* *Azizul Hakim (7848052)*,	*Terra Jentsch (7613907)*,	*Joshua Smallwood (7826555)*,	*Tommy Wu (7852291)*,	*Hao Zheng (7870389)*.
	
	> Names are represented in ascending order based on the first letter of surnames of developers.

---

### Structure

Excluding default android studio project resources and files, source code files of this project are structured by using paralled source directories for the system and its tests respectively.

1. The resource directory includes 2 sub-directories **main** *(for system)* and **test** *(for tests)*.
2. Each sub-directory contains a package named **comp3350** and its sub-package **pbbs** to indicate the name and license of this project.
3. For the system files, there are 5 sub-packages as **layers** under **pbbs**:

|   Package    |                           Function                           |
| :----------: | :----------------------------------------------------------: |
| Application  | Control the entire system with switch on/off the access to the database. |
| Presentation | Include authorization, welcome interface, add activites to each object,<br/>main activity and its fragments based on home and objects. |
|   Business   | Provide safe accesses to the database for modification purposes,<br/>including interactive activities with the presentation layer. |
| Persistence  | Setup the database, integrate all the behaviors each object could have.<br/>(*In iteration 1, this will populate a stub database instead of a real one*) |
|   Objects    | Construct basic components and regulations for each object<br/>in order to provide fundamental information for the persistence layer. |

4.  **JUnit 3** test framework is applied to the tests. A test suite, `AllTests.java` to run all the unit tests is provided, in addition to test every class within business and objects packages from the system.

---

### Features

1. In the android emulator, **PBBS** can be found under the *Applications* page. The first time the user enters the PBBS app, it shows a *welcome* interface and requires input of user's name, followed by a *continue* button once input is done.

2. The default interface after inputting the name is the **Home** page. There are 4 menu options in total and are listed at the bottom part of the screen. In addition to Home, the app provides navigation options for Cards, Budget, and Transactions. Each of these 3 menus has a **add** button at the bottom right corner for their sub-menus.

	**Change Name**: Home page shows a welcome slogan but also offers a feature that the user can choose to change the username.
	
3. **Card** page contains detailed information of each card the user has.
	* Cards now are splited into 2 kinds: credit and debit.
	* Credit cards have specific field *Payment Day* while debit cards do not.
	
	The add button brings the user to the **Add Card** page, which brings up a form that requires input.
	* *Card Name*: a description or name of the card (eg. My BMO).
	* *Card Number*: a series of digits print on this card (eg. 1002100310041005).
	* *Expire Date (month & year)*: expire month and year of this card (eg. 12, 2025).
	* *Holder Name*: the user's full name shown on the card (eg. Joshua Smallwood).
	* *Payment Day*: CREDIT CARD ONLY, due day of next payment from the current month (eg. 15).
	
	Tap any card on the list will direct to the **View Card** page.
	* Allows user to view the card balance and a line chart of spending for that card over time.
	* Update a card: tap the button at the bottom allows user to re-enter all the info of a card, or activate/deactivate card in the **Update Card** page.
		* If a card is labelled as inactive, the word "Inactive" will be shown at which card shown on the list.
	
4. **Budget** page is to let the user set up a limit for each consumption category. The add button brings the user to **Add Budget Category** page, which brings up a form that requires input.
	* *Desciption*: name of this category (eg. Snack food).
	* *Limit*: amount of money as the cost upperlimit (eg. 50).

	Tap any budget on the list will direct to the **View Budget Category** page.
	* The total amount and the amount has spent for a budget are visualized together in pie chart form for user viewing purpose.
	* Update a budget category: tap the button at the bottom allows user to re-enter all the info of a budget category in the **Update Budget Category** page.

5. **Transactions** page aims to record each transaction the user made. The add button brings the user to **Add Transaction** page, which brings up a form that requires input.
	* *Amount*: Amount of money spent (eg. 20).
	* *Date and Time*: Time when this transaction happened (Implemented as a date and time selector).
	* *Description*: What is the purpose of this transaction (eg. Bought Groceries).
	* *Card*: Information of credit card used for this transaction (Implemented as a dropdown).
	* *Budget Category*: Budget category this transaction belongs and the limit (Implemented as a dropdown).
	
	Tap any transaction on the list will direct to the **Update Transaction** page.
	* Allows user to re-enter all the info of a transaction, as well selecting the budget category this transaction belongs to, and the card this transaction was made.
	* Update or delete a transaction: 2 buttons are provided to let user update or delete a transaction.

6.	**Bank Accounts**: incompleted in this iteration.
	* *Account Name*: a description or name of the bank account (eg. House savings).
	* *Account Number*: user's bank account number (eg. 2001963).
	* *Linked Card*: the debit card that a bank issued with at least 1 bank account (eg. BMO Access).
	
	Purpose is to link a debit card with one or more bank accounts (eg. TD Access <-> TD checking & saving). Layers of objects and business and their tests are completed. May create another menu option in the app interface to list all bank accounts.

	> More features involving *Bank Accounts* will be created in the next iteration.

---

### Environments

The PBBS was tested on following environments:
|               Emulator/Device                |                  OS                  |
| :------------------------------------------: | :----------------------------------: |
|           Nexus 7 Emulator (amd64)           | Android 6 Marshmallow (API Level 23) |
|           Nexus 7 Emulator (amd64)           |     Android 10 Q (API Level 29)      |
|    Xiaomi Mi 5S (Qualcomm Snapdragon 821)    |    Android 8 Oreo (API Level 26)     |
| Xiaomi Mi Note LTE (Qualcomm Snapdragon 810) | Android 6 Marshmallow (API Level 23) |
|   Samsung Note 9 (Qualcomm Snapdragon 845)   |     Android 10 Q (API Level 29)      |


The development environment makes use of **Android 11 (R) API Level 30 SDK**, targed to Android 11, with minimum SDK version of **Android 6 (Marshmallow) API Level 23**. Coding language is Java and **JDK 8** is applied.

---

### Known Bugs

> No bugs left so far in this iteration. 

---

### License

- All rights reserved by members in the development team: Group 4 of COMP3350 summer 2020, University of Manitoba.
