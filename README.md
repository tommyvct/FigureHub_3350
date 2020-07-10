## Personal Budget-Balancing System (PBBS)

* Iteration 1 - README.md

---

### Background

1. Project name: **Personal Budget-Balancing System (PBBS)**.
2. Repository link: [PBBS Repository](https://github.com/tommyvct/FigureHub_3350).
	* You can view all the source code files from the link above.
3. Developer logs: [PBBS Developer log (iteration 1)](https://github.com/tommyvct/FigureHub_3350/projects/2).
	* The developer logs are written by members in the development team, used to record the process of how this project proceeded and what actions members performed.
	* You can view the developer logs from the link above. It is also sorted as a text document in the main zip file directory.

4. Team members:
	* *Azizul Hakim, 7848052*,	*Terra Jentsch, 7613907*,	*Joshua Smallwood, 7826555*,	*Tommy Wu, 7852291*,	*Hao Zheng, 7870389*.
	>Names are represented in ascending order based on the first letter of surnames of developers.

---

### Structure

Excluding default android studio project resources and files, source code files of this project are structured by using paralled source directories for the system and its tests respectively.

1. The resource directory includes 2 sub-directories **main** *(for system)* and **test** *(for tests)*.
2. Each sub-directory contains a package named **comp3350** and its sub-package **pbbs** to indicate the name and license of this project.
3. For the system files, there are 5 sub-packages as **layers** under **pbbs**:

|Package|Function|
|:-:|:-:|
|Application|Control the entire system with switch on/off the access to the database.|
|Presentation|Include authorization, welcome interface, add activites to each object,<br/>main activity and its fragments based on home and objects.|
|Business|Provide safe accesses to the database for modification purposes,<br/>including interactive activities with the presentation layer.|
|Persistence|Setup the database, integrate all the behaviors each object could have.<br/>(*In iteration 1, this will populate a stub database instead of a real one*)|
|Objects|Construct basic components and regulations for each object<br/>in order to provide fundamental information for the persistence layer.|

4.  **JUnit 3** test framework is applied to the tests. A test suite, `AllTests.java` to run all the unit tests is provided, in addition to test every class within business and objects packages from the system.

---

### Features

1. In the android emulator, **PBBS** can be found under the *Applications* page. The first time the user enters the PBBS app, it shows a *welcome* interface and requires input of user's name, followed by a *continue* button once input is done.

2. The default interface after inputting the name is the **Home** page. There are 4 menu options in total and are listed at the bottom part of the screen. In addition to Home, the app provides navigation options for Cards, Budget, and Transactions. Each of these 3 menus has a **add** button at the bottom right corner for their sub-menus.
	>Home page so far has no features but a welcome slogan, this will be more fleshed out in the next interation.

3. **Cards** page contains detailed information of each credit card the user has. The add button brings the user to the *Add Card* page, which brings up a form that requires input of **Card Name**, **Card Number**, **Expire Date (month & year)**, **Payment Day**, and **Holder Name**, defined as follows:
	* Card Name: a description or name of the card *(ex. Mastercard)*
	* Card Number: a series of digits print on this card *(ex. 1002100310041005)*
	* Expire Date (month & year): expire month and year of this card *(ex. 12, 2025)*
	* Payment Day: due day of next payment from the current month *(ex. 15)*
	* Holder Name: the user's full name shown on the card *(ex. Joshua Smallwood)*

4. **Budget** page is to let the user setup a limit for each consumption category. The add button brings the user to *Add Budget Category* page, which brings up a form that requires input of **Description** and **Limit**. A budget category includes:
	* Desciption: name of this category *(ex. Snack food)*
	* Limit: amount of money as the cost upperlimit *(ex. 50)*

5. **Transactions** page aims to record each transaction the user made. The add button brings the user to *Add Transaction* page, which brings up a form that requires input of **Description**, **Date & Time**, and **Amount** to fill values in, and **Card** and **Budget Category** to select. A transaction record includes:
	* Amount: Amount of money spent *(ex. 20)*
	* Date and Time: Time when this transaction happened *(Implemented as a date and time selector)*
	* Description: What is the purpose of this transaction *(ex. Bought Groceries)*
	* Card: Information of credit card used for this transaction *(Implemented as a dropdown)*
	* Budget Category: Budget category this transaction belongs and the limit *(Implemented as a dropdown)*

>More features will be created in the next iteration.

---

### Environment

The PBBS was tested on following environments:
|Emulator/Device|OS|
|:-:|:-:|
|Nexus 7 Emulator (amd64)|Android 6 Marshmallow (API Level 23)|
|Nexus 7 Emulator (amd64)|Android 10 Q (API Level 29)|
|Xiaomi Mi 5S (Qualcomm Snapdragon 821)| Android 8 Oreo (API Level 26)|
|Xiaomi Mi Note LTE (Qualcomm Snapdragon 810) | Android 6 Marshmallow (API Level 23)|
|Samsung Note 9 (Qualcomm Snapdragon 845)|Android 10 Q (API Level 29)|


The development environment makes use of **Android 11 (R) API Level 30 SDK**, targed to Android 11, with minimum SDK version of **Android 6 (Marshmallow) API Level 23**.

**JDK 8** is also used.


---

### Known Bugs
- There is a bug on all upload forms. You can press the submit button multiple times when everything is filled out before going back to the main list view, resulting in duplicate records being added to the database.

	Fix: Move the snackbar to previous activity, which was the original design. 
    
    Due to unfamiliarity, current design is relatively easier to understand and implement. 



### License

All rights reserved by members in the development team: Group 4 of COMP3350 summer 2020, University of Manitoba.

