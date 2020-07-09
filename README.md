## Personal Budget-Balancing System (PBBS)

* Iteration 1 - README.md

---

### Background

1. Project name: **Personal Budget-Balancing System (PBBS)**.
2. Repository link: [PBBS Repository](https://github.com/tommyvct/FigureHub_3350).
	* You can view all the source code files from the link above.
3. Developer logs: [PBBS Developer log (iteration 1)](https://github.com/tommyvct/FigureHub_3350/projects/2).
	* The developer logs are written by members in the development team, that record the process of how this project proceeds and what actions members performed.
	* You can view the developer logs from the link above. It is also sotred as a text document in the main zip file directory.

4. Team members:
	* *Azizul Hakim*,	*Terra Jentsch*,	*Joshua Smallwood*,	*Tommy Wu*,	*Hao Zheng*.
	
	>Names are represented in ascending order based on the first letter of surnames of develpers.

---

### Structure

Excluding default android files of the emulator, source code files of this project are sturctured by using paralled source directories for the system and its tests respectively.

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

4.  **JUnit 3** test framework is applied to the tests. A test suite to run all the unit tests is provided, in addition to test every class within business and objects packages from the systemm. Classes that have their corresponded test classes have same package name.

---

### Features

1. In the android emulator, **PBBS** can be found under the *Applications* page. The first time the user enter in the PBBS app, it shows a *welcome* interface and requires input of user's name, followed by a *continue* button once input is done.

2. The default interface after input name and click *continue* is the **Home** page. There are 4 menu options in total and are listed at the bottom part of the screen. In addition to Home, the app provides Cards, Budget, and Transactions. Each of these 3 menus has a **add** button at the bottom right corner for their sub-menus.
	>Home page so far has no features but a welcome slogn, will be complete in next interation.

3. **Cards** page contains detailed information of each credit card the user add in. The add button brings the user to *Add Card* page, where requires input of **Card Name**, **Card Number**, **Expire Date (month & year)**, **Payment Day**, and **Holder Name**. A card information includes:
	* Card Name: a description the user made to this card
	* Card Number: a series of digits print on this card
	* Expire Date (month & year): expire month and year of this card
	* Payment Day: due day of next payment from the current month
	* Holder Name: the user's full name shown on the card

4. **Budget** page is to let the user setup a limit for each consumption category. The add button brings the user to *Add Budget Category* page, where requires input of **Description** and **Limit**. A budget category includes:
	* Desciption: name of this category
	* Limit: amount of money as the cost upperlimit

5. **Transactions** page aims to record each transaction the user made. The add button brings the user to *Add Transaction* page, where requires input of **Description**, **Date & Time**, and **Amount** to fill values in, and **Card** and **Budget Category** to select. A transaction record includes:
	* Amount: Amount of money spent
	* Date and Time: Time when this transaction happened
	* Description: What is the purpose of this transaction
	* Card: Information of credit card used for this transaction
	* Budget Category: Budget category this transaction belongs and the limit

>More features will be performed in next iteration.

---

### Environment
>(Delete this after done) clearly describe which Android systems your app was tested on, including emulator and hardware.

The PBBS was tested on an **android app emulator** with device **Nexus 7 API 23**.

>(Delete this after done) Describe exactly the environment(s) used, to ensure the markers can replicate an environment and avoid unexpected results. Remember that regardless of the hardware, it must run on the emulator with system image 6.0 (Marshmallow, API level 23).

The development environment makes use of **SDK Android 6.0 (Marshmallow) API 23** and **JDK 8**.

---

### License

All rights reserved by members in the development team: Group 4 of COMP3350 summer 2020, University of Manitoba.

---
