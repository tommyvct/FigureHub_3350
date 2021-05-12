## Personal Budget-Balancing System (PBBS)

https://user-images.githubusercontent.com/7903172/118054082-3e0eab80-b34b-11eb-9097-d2a835851d53.mp4

---

### Background

1. Project name: **Personal Budget-Balancing System (PBBS)**.

2. Repository link: [PBBS Repository](https://github.com/tommyvct/FigureHub_3350).

	* You can view all the source code files from the link above.

3. Developer logs: [PBBS Developer log (iteration 3)](https://github.com/tommyvct/FigureHub_3350/projects/6).

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

4. **Test framworks** JUnit 3 and JUnit 4 are applied to the tests.
	* Junit 3 is applied to test suites `RunUnitTests.java` and `RunIntegrationTests.java`.
	* Junit 4 is applied to test suite `RunAcceptanceTests.java`.

5. **Database** copies locate at `FigureHub_3350/app/db/` and `FigureHub_3350/app/src/main/assets/db/`.

---

### Features

1. **New User Stories**: added and updated some stories for the purpose of providing more features.
	* A PDF file *Iteration_3_user_stories.pdf* is attached within submission.

2. In the android emulator, **PBBS** can be found under the *Applications* page. The first time the user enters the PBBS app, it shows a *welcome* interface and requires input of user's name, followed by a *continue* button once input is done.

3. The default interface after inputting the name is the **Home** page. There are 4 menu options in total and are listed at the bottom part of the screen. In addition to Home, the app provides navigation options for Cards, Budget, and Transactions. Each of these 3 menus has a **add** button at the bottom right corner for their sub-menus.

	**Change Name**: Home page shows a welcome slogan but also offers a feature that the user can choose to change the username.
	
	**Notification**: Home page now contains the notification feature:
	* Notifications for when over budget or almost over budget will be shown on the Home page.
	* Notifications if a credit card is due it will be shown on the Home page.
	
4. Regulations about **Card**: Card page allows various inputs:

	* *Card number* is intentionally left unchecked, they could be anything that is not null or empty.
	* There are numerous kinds of "card" numbers, an ordinary MasterCard or Visa have a 16-digit card number, while an American Express card have 15, and even some debit cards have 19 digits.
	* Some private companies who provide a financial product without a legit bank card (namely EQ Bank in Canada and Ant Financial in China).
	* The time invested on implementing these checks have minimum returns to the quality and experience of the app.

5. **Card** page contains detailed information of each card the user has.

	* Cards now are splited into 2 kinds: credit and debit.
	* Credit cards have the specific field *Payment Day* while debit cards do not.
	* If a card is labelled as inactive, the word **"Inactive"** will be shown at which card shown on the list.

	The add button brings the user to the **Add Card** page, which brings up a form that requires input.

	* *Card Name*: a description or name of the card (eg. My BMO).
	* *Card Number*: a series of digits print on this card (eg. 1002100310041005).
	* *Expire Date (month & year)*: expire month and year of this card (eg. 12, 2025).
	* *Holder Name*: the user's full name shown on the card (eg. Joshua Smallwood).
	* *Payment Day*: CREDIT CARD ONLY, due day of next payment from the current month (eg. 15).

	Tap any card on the list will direct to the **View Card** page.

	* Allows user to view the card balance and a line chart of spending for that card over time.
	* Update a card: tap the button at the bottom allows user to re-enter all the info of a card, or activate/deactivate card in the **Update Card** page.
	* Debit cards have the specific button *View Accounts* that the **Bank Accounts** page can be accessed by tapping this button. It lists all the bank accounts that are associated with the very debit card.

6. **Budget** page is to let the user set up a limit for each consumption category. The add button brings the user to **Add Budget Category** page, which brings up a form that requires input.

	* *Desciption*: name of this category (eg. Snack food).
	* *Limit*: amount of money as the cost upperlimit (eg. 50).

	Tap any budget on the list will direct to the **View Budget Category** page.

	* The total amount and the amount has spent for a budget are visualized together in pie chart form for user viewing purpose.
	* Update a budget category: tap the button at the bottom allows user to re-enter all the info of a budget category in the **Update Budget Category** page.

7. **Transactions** page aims to record each transaction the user made. The add button brings the user to **Add Transaction** page, which brings up a form that requires input.

	* *Amount*: Amount of money spent (eg. 20).
	* *Date and Time*: Time when this transaction happened (Implemented as a date and time selector).
	* *Description*: What is the purpose of this transaction (eg. Bought Groceries).
	* *Card*: Information of credit card used for this transaction (Implemented as a dropdown).
	* *Budget Category*: Budget category this transaction belongs and the limit (Implemented as a dropdown).

	Tap any transaction on the list will direct to the **Update Transaction** page.

	* Allows user to re-enter all the info of a transaction, as well selecting the budget category this transaction belongs to, and the card this transaction was made.
	* Update or delete a transaction: 2 buttons are provided to let user update or delete a transaction.

8. **Bank Accounts** is applied to link a debit card with one or more bank accounts (eg. TD Access <-> TD checking & saving).

	* *Account Name*: a description or name of the bank account (eg. House savings).
	* *Account Number*: user's bank account number (eg. 2001963).
	* *Linked Card*: the debit card that a bank issued with at least 1 bank account (eg. BMO Access).

	Bank accounts associated to a debit card can be viewed in the Card page as stated in point 5. Adding a new bank account and update an existing one can be performed on the **Bank Accounts** page. 
	
9. **Unit**, **Integration** and **Acceptance** tests are categorized and performed.

	* *Unit tests*: including tests for layers of object, business, and persistence.
	* *Integration tests*: including tests for HSQLDB integration, and seam between layers of business and persistence.
	* *Acceptance tests*: including tests for big user stories that cover 1) Maintain a budget, 2) Manage transactions, 3) Credit card information.
	> In acceptance tests, applying ramdon numbers to avoid duplicated budget categories has a slightly tiny chance to generate the same number that causes repeated testing leading to fail. Can be solved by allowing duplicated budget categories.

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

> No bugs left in this iteration. 

---

### References

* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart/tree/master/MPChartLib/src/main/java/com/github/mikephil/charting) by Philipp Jahoda.

* [Test execution order](https://github.com/junit-team/junit4/wiki/Test-execution-order) from junit-team/junit4.

---

### License

- All rights reserved by members in the development team: Group 4 of COMP3350 summer 2020, University of Manitoba.
