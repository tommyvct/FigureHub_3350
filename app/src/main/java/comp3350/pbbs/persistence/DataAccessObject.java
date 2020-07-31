package comp3350.pbbs.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import comp3350.pbbs.objects.BankAccount;
import comp3350.pbbs.objects.Card;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.objects.BudgetCategory;

/**
 * DataAccessObject
 * Group4
 * PBBS
 *
 * This class defines the HSQL database for the persistence layer.
 */
public class DataAccessObject implements DataAccess {
	private Connection con;    // for DB switch
	private Statement stmt; // statement
	private String dbName;    // name of DB
	private String dbType;    // type of DB
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Constructor of DB
	 *
	 * @param dbName the DB name
	 */
	public DataAccessObject(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Opens the database by connecting the hsqldb file.
	 *
	 * @param dbPath Database path
	 */
	public void open(String dbPath) {
		String url;
		try {
			dbType = "HSQL";
			url = "jdbc:hsqldb:file:" + dbPath;
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			con = DriverManager.getConnection(url, "PBBS", "");
			System.out.println("Opened " + dbType + " database " + dbPath);
			if (getBudgetsSize() == 0 && getCardsSize() == 0 && getTransactionsSize() == 0)
				DataAccess.populateData(this);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	/**
	 * This method is returning the DB name
	 */
	public String getDBName() {
		return dbName;
	}

	/**
	 * This method commits all changes to the DB then terminate it
	 */
	public void close() {
		try {
			stmt = con.createStatement();
			stmt.execute("SHUTDOWN COMPACT");
			con.close();
			System.out.println("Closed " + dbType + " database " + dbName);
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Methods to return the list of BudgetCategories
	 *
	 * @return budgets list
	 */
	public List<BudgetCategory> getBudgets() {
		List<BudgetCategory> toReturn = new ArrayList<BudgetCategory>();
		try {
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM BUDGETCATEGORY");
			while (results.next()) {
				String budgetName = results.getString("BUDGETNAME");
				double budgetLimit = results.getDouble("BUDGETLIMIT");
				BudgetCategory budgetCategory = new BudgetCategory(budgetName, budgetLimit);
				toReturn.add(budgetCategory);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}

		return toReturn;
	}

	/**
	 * This method will find if a budget exist or not
	 *
	 * @return True if found, false if not found
	 */
	public boolean findBudgetCategory(BudgetCategory currentBudget) {
		boolean result = false;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM BUDGETCATEGORY WHERE BUDGETNAME='"
					+ currentBudget.getBudgetName() + "' AND BUDGETLIMIT="
					+ currentBudget.getBudgetLimit();
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				int count = results.getInt("CNT");
				if (count == 1) {
					result = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * This method will insert a new budget category with the budgets ArrayList.
	 *
	 * @return true, if inserted successfully
	 */
	public boolean insertBudgetCategory(BudgetCategory newBudget) {
		boolean result = false;
		String values;
		try {
			values = "'" + newBudget.getBudgetName() + "', " + newBudget.getBudgetLimit();
			String cmdString = "INSERT INTO BUDGETCATEGORY (BUDGETNAME, BUDGETLIMIT) " +
					"VALUES(" + values + ")";
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * This method will be used to update a Budget.
	 *
	 * @return True if the budget category was updated successfully, or false if not
	 */
	public boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
		boolean toReturn = false;
		String values, where, cmdString;
		try {
			values = "BUDGETNAME='" + newBudget.getBudgetName()
					+ "', BUDGETLIMIT=" + newBudget.getBudgetLimit();
			where = "WHERE BUDGETNAME='" + currentBudget.getBudgetName()
					+ "' AND BUDGETLIMIT=" + currentBudget.getBudgetLimit();
			cmdString = "UPDATE BUDGETCATEGORY SET " + values + " " + where;
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			if (updateCount == 1) {
				toReturn = true;
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return toReturn;
	}

	/**
	 * This method will return the size of the budget.
	 *
	 * @return size of the budget list
	 */
	public int getBudgetsSize() {
		int count = 0;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM BUDGETCATEGORY";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				count = results.getInt("CNT");
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return count;
	}

	/**
	 * method: find a bank account exist or not in the database
	 *
	 * @param toFind a bank account needs to be found from the database
	 * @return true if this bank account has been added into the database
	 */
	@Override
	public boolean findBankAccount(BankAccount toFind) {
		boolean result = false;
		try {
			// Get card
			String cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + toFind.getLinkedCard().getCardName() +
					"' AND CARDNUM='" + toFind.getLinkedCard().getCardNum() +
					"' AND HOLDERNAME='" + toFind.getLinkedCard().getHolderName() +
					"' AND EXPIREMONTH=" + toFind.getLinkedCard().getExpireMonth() +
					" AND EXPIREYEAR=" + toFind.getLinkedCard().getExpireYear() +
					" AND PAYDATE=" + toFind.getLinkedCard().getPayDate();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();

			cmdString = "SELECT COUNT(*) AS CNT FROM BANKACCOUNT WHERE ACCOUNTNAME='"
					+ toFind.getAccountName() + "' AND ACCOUNTNUMBER='"
					+ toFind.getAccountNumber() + "' AND CARDID=" + cardID;
			stmt = con.createStatement();
			results = stmt.executeQuery(cmdString);
			while (results.next()) {
				int count = results.getInt("CNT");
				if (count == 1) {
					result = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;

	}

	/**
	 * method: add a bank account into the database
	 *
	 * @param newAccount a bank account needs to be added into the database
	 * @return true if this bank account does not exist in the database
	 */
	@Override
	public boolean insertBankAccount(BankAccount newAccount) {
		boolean result = false;
		String values;

		try {
			// Get card
			String cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + newAccount.getLinkedCard().getCardName() +
					"' AND CARDNUM='" + newAccount.getLinkedCard().getCardNum() +
					"' AND HOLDERNAME='" + newAccount.getLinkedCard().getHolderName() +
					"' AND EXPIREMONTH=" + newAccount.getLinkedCard().getExpireMonth() +
					" AND EXPIREYEAR=" + newAccount.getLinkedCard().getExpireYear() +
					" AND PAYDATE=" + newAccount.getLinkedCard().getPayDate();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();

			values = "'" + newAccount.getAccountName() + "', '" + newAccount.getAccountNumber() +
					"', " + cardID;
			cmdString = "INSERT INTO BANKACCOUNT (ACCOUNTNAME, ACCOUNTNUMBER, CARDID) " +
					"VALUES(" + values + ")";
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * method: update a bank account existed in the database
	 *
	 * @param toUpdate   an old bank account needs to be replaced
	 * @param newAccount a new bank account will replace the other one
	 * @return true if the old bank account does exist in the database
	 */
	@Override
	public boolean updateBankAccount(BankAccount toUpdate, BankAccount newAccount) {
		boolean result = false;
		String values, where;
		try {
			// Get first credit card
			String cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + newAccount.getLinkedCard().getCardName() +
					"' AND CARDNUM='" + newAccount.getLinkedCard().getCardNum() +
					"' AND HOLDERNAME='" + newAccount.getLinkedCard().getHolderName() +
					"' AND EXPIREMONTH=" + newAccount.getLinkedCard().getExpireMonth() +
					" AND EXPIREYEAR=" + newAccount.getLinkedCard().getExpireYear() +
					" AND PAYDATE=" + newAccount.getLinkedCard().getPayDate();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int newCardID = results.getInt("ID");
			results.close();
			// Get first credit card
			cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + toUpdate.getLinkedCard().getCardName() +
					"' AND CARDNUM='" + toUpdate.getLinkedCard().getCardNum() +
					"' AND HOLDERNAME='" + toUpdate.getLinkedCard().getHolderName() +
					"' AND EXPIREMONTH=" + toUpdate.getLinkedCard().getExpireMonth() +
					" AND EXPIREYEAR=" + toUpdate.getLinkedCard().getExpireYear() +
					" AND PAYDATE=" + toUpdate.getLinkedCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int currentCardID = results.getInt("ID");
			results.close();
			values = "ACCOUNTNAME='" + newAccount.getAccountName()
					+ "', ACCOUNTNUMBER='" + newAccount.getAccountNumber()
					+ ", CARDID=" + newCardID;
			where = "WHERE ACCOUNTNAME='" + toUpdate.getAccountName()
					+ "' AND ACCOUNTNUMBER='" + toUpdate.getAccountNumber()
					+ " AND CARDID=" + currentCardID;
			stmt = con.createStatement();
			cmdString = "UPDATE BANKACCOUNT SET " + values + " " + where;
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * method: to get all the bankAccounts from the database
	 *
	 * @return a list of bankAccounts
	 */
	@Override
	public List<BankAccount> getAllBankAccounts() {
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		try {
			String cmdString = "SELECT * FROM BANKACCOUNT AS BA INNER JOIN CARD AS C ON " +
					"BA.CARDID=C.ID";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				String accountName = results.getString("ACCOUNTNAME");
				String accountNumber = results.getString("ACCOUNTNUMBER");
				// Get card
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				Card card = new Card(cardName, cardNum, name, expireMonth, expireYear);
				BankAccount bankAccount = new BankAccount(accountName, accountNumber, card);
				bankAccounts.add(bankAccount);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return bankAccounts;
	}

	/**
	 * method: get the bank account from a debit card
	 *
	 * @param from the debit card
	 * @return BankAccount ArrayList links this debit card
	 */
	@Override
	public List<BankAccount> getAccountsFromDebitCard(Card from) {
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		try {
			// Get card
			String cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + from.getCardName() +
					"' AND CARDNUM='" + from.getCardNum() +
					"' AND HOLDERNAME='" + from.getHolderName() +
					"' AND EXPIREMONTH=" + from.getExpireMonth() +
					" AND EXPIREYEAR=" + from.getExpireYear() +
					" AND PAYDATE=" + from.getPayDate();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			cmdString = "SELECT * FROM BANKACCOUNT WHERE CARDID=" + cardID;
			stmt = con.createStatement();
			results = stmt.executeQuery(cmdString);
			while (results.next()) {
				String accountName = results.getString("ACCOUNTNAME");
				String accountNumber = results.getString("ACCOUNTNUMBER");
				BankAccount bankAccount = new BankAccount(accountName, accountNumber, from);
				bankAccounts.add(bankAccount);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return bankAccounts;
	}

	/**
	 * This method will find if a card exist or not.
	 *
	 * @param currCard card to find
	 * @return the card object.
	 */
	@Override
	public boolean findCard(Card currCard) {
		boolean result = false;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM CARD WHERE" +
					" CARDNAME='" + currCard.getCardName() +
					"' AND CARDNUM='" + currCard.getCardNum() +
					"' AND HOLDERNAME='" + currCard.getHolderName() +
					"' AND EXPIREMONTH=" + currCard.getExpireMonth() +
					" AND EXPIREYEAR=" + currCard.getExpireYear() +
					" AND PAYDATE=" + currCard.getPayDate();
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				int count = results.getInt("CNT");
				if (count == 1) {
					result = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * This method inserts a new card in the database.
	 *
	 * @return true, if it inserted properly
	 */
	@Override
	public boolean insertCard(Card newCard) {
		String values;
		boolean toReturn = false;
		if (!findCard(newCard)) {
			try {
				values = "'" + newCard.getCardName()
						+ "', '" + newCard.getCardNum()
						+ "', '" + newCard.getHolderName()
						+ "', " + newCard.getExpireMonth()
						+ ", " + newCard.getExpireYear()
						+ ", " + newCard.getPayDate()
						+ ", 1";
				String cmdString = "INSERT INTO CARD (CARDNAME, CARDNUM, HOLDERNAME," +
						" EXPIREMONTH, EXPIREYEAR, PAYDATE, ISACTIVE) VALUES(" + values + ")";
				stmt = con.createStatement();
				int updateCount = stmt.executeUpdate(cmdString);
				checkWarning(stmt, updateCount);
				toReturn = true;
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		return toReturn;
	}

	public boolean updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
		boolean toReturn = false;
		String values, where, cmdString;
		try {
			values = "BUDGETNAME='" + newBudget.getBudgetName()
					+ "', BUDGETLIMIT=" + newBudget.getBudgetLimit();
			where = "WHERE BUDGETNAME='" + currentBudget.getBudgetName()
					+ "' AND BUDGETLIMIT=" + currentBudget.getBudgetLimit();
			cmdString = "UPDATE BUDGETCATEGORY SET " + values + " " + where;
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			if(updateCount == 1) {
				toReturn = true;
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return toReturn;
	}

	/**
	 * Getter method to get all the credit cards.
	 *
	 * @return creditCards.
	 */
	public List<Card> getCreditCards() {
		List<Card> creditCards = new ArrayList<Card>();
		try {
			String cmdString = "SELECT * FROM CARD";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				Card card;
				if (payDate == 0) {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear);
				} else {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear, payDate);
				}
				if (getAccountsFromDebitCard(card).isEmpty() || payDate != 0) {
					creditCards.add(card);
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return creditCards;
	}

	/**
	 * Getter method to get all the debit cards.
	 *
	 * @return debitCards.
	 */
	@Override
	public List<Card> getDebitCards() {
		List<Card> debitCards = new ArrayList<Card>();
		try {
			String cmdString = "SELECT * FROM CARD";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				Card card;
				if(payDate == 0) {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear);
				}
				else {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear, payDate);
				}
				if (!getAccountsFromDebitCard(card).isEmpty() || payDate == 0) {
					debitCards.add(card);
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return debitCards;
	}

	/**
	 * Getter method to get all the cards.
	 *
	 * @return all the cards.
	 */
	@Override
	public List<Card> getCards() {
		List<Card> cards = new ArrayList<Card>();
		try {
			String cmdString = "SELECT * FROM CARD";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				Card card;
				if (payDate == 0) {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear);
				} else {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear, payDate);
				}
				cards.add(card);
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return cards;
	}

	/**
	 * Getter method for only active cards
	 *
	 * @return active cards
	 */
	@Override
	public List<Card> getActiveCards() {
		List<Card> cards = new ArrayList<Card>();
		try {
			String cmdString = "SELECT * FROM CARD WHERE ISACTIVE <> 0";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				Card card;
				if (payDate == 0) {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear);
				} else {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear, payDate);
				}
				cards.add(card);
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return cards;
	}

	/**
	 * This method updates a card existed in the database
	 *
	 * @param currCard an old card needs to be replaced
	 * @param newCard  a new card will replace the other one
	 * @return true if the old card does exist in the database
	 */
	@Override
	public boolean updateCard(Card currCard, Card newCard) {
		boolean result = false;
		String values, where;
		try {
			values = "CARDNAME='" + newCard.getCardName()
					+ "', CARDNUM='" + newCard.getCardNum()
					+ "', HOLDERNAME='" + newCard.getHolderName()
					+ "', EXPIREMONTH=" + newCard.getExpireMonth()
					+ ", EXPIREYEAR=" + newCard.getExpireYear()
					+ ", PAYDATE=" + newCard.getPayDate();
			where = "CARDNAME='" + currCard.getCardName()
					+ "' AND CARDNUM='" + currCard.getCardNum()
					+ "' AND HOLDERNAME='" + currCard.getHolderName()
					+ "' AND EXPIREMONTH=" + currCard.getExpireMonth()
					+ " AND EXPIREYEAR=" + currCard.getExpireYear()
					+ " AND PAYDATE=" + currCard.getPayDate();
			String cmdString = "UPDATE CARD SET " + values + " WHERE " + where;
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * Mark given card as inactive.
	 *
	 * @param toMark card to mark as inactive
	 */
	@Override
	public boolean markInactive(Card toMark) {
		boolean result = false;
		String values, where;
		try {
			String cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + toMark.getCardName() +
					"' AND CARDNUM='" + toMark.getCardNum() +
					"' AND HOLDERNAME='" + toMark.getHolderName() +
					"' AND EXPIREMONTH=" + toMark.getExpireMonth() +
					" AND EXPIREYEAR=" + toMark.getExpireYear() +
					" AND PAYDATE=" + toMark.getPayDate();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			values = "ISACTIVE=0";
			where = "ID=" + cardID;
			cmdString = "UPDATE CARD SET " + values + " WHERE " + where;
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public boolean markActive(Card toMark) {
		boolean result = false;
		String values, where;
		try {
			String cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + toMark.getCardName() +
					"' AND CARDNUM='" +	toMark.getCardNum() +
					"' AND HOLDERNAME='" + toMark.getHolderName() +
					"' AND EXPIREMONTH=" + toMark.getExpireMonth() +
					" AND EXPIREYEAR=" + toMark.getExpireYear() +
					" AND PAYDATE=" + toMark.getPayDate();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			values = "ACTIVE=1";
			where = "ID=" + cardID;
			cmdString = "UPDATE CARD SET " + values + " WHERE " + where;
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * This method returns the credit card size
	 *
	 * @return size of the credit card.
	 */
	@Override
	public int getCreditCardsSize() {
		return getCreditCards().size();
	}

	/**
	 * This method returns the debit card size
	 *
	 * @return size of the debit card.
	 */
	@Override
	public int getDebitCardsSize() {
		return getDebitCards().size();
	}

	/**
	 * This method returns the card size
	 *
	 * @return size of the card.
	 */
	public int getCardsSize() {
		int count = 0;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM CARD";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				count = results.getInt("CNT");
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return count;
	}

	/**
	 * Getter method to get the transactions.
	 *
	 * @return transactions from database.
	 */
	public List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			String cmdString = "SELECT * FROM TRANSACTION AS T INNER JOIN BUDGETCATEGORY AS BC ON" +
					" T.BUDGETCATEGORYID=BC.ID INNER JOIN CARD AS C ON T.CARDID=C.ID";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				Date time = dateFormat.parse(results.getString("DATE"));
				float amount = (float) results.getDouble("AMOUNT");
				String description = results.getString("DESCRIPTION");
				// Get credit card
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				Card card;
				if (payDate == 0) {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear);
				} else {
					card = new Card(cardName, cardNum, name, expireMonth, expireYear, payDate);
				}
				// Get budget category
				String budgetName = results.getString("BUDGETNAME");
				double budgetLimit = results.getDouble("BUDGETLIMIT");
				BudgetCategory budgetCategory = new BudgetCategory(budgetName, budgetLimit);
				Transaction transaction = new Transaction(time, amount, description, card, budgetCategory);
				transactions.add(transaction);
			}
		} catch (SQLException | ParseException e) {
			System.out.println(e.toString());
		}
		return transactions;
	}

	/**
	 * This method will return the transaction size.
	 *
	 * @return size of transaction
	 */
	public int getTransactionsSize() {
		int count = 0;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM TRANSACTION";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()) {
				count = results.getInt("CNT");
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return count;
	}

	/**
	 * This method will find if a transaction exist or not.
	 *
	 * @param currentTransaction transaction that will be searched
	 * @return True if found, or false if not found
	 */
	public boolean findTransaction(Transaction currentTransaction) {
		boolean found = false;
		try {
			// Get budget category
			String cmdString = "SELECT ID FROM BUDGETCATEGORY WHERE" +
					" BUDGETNAME='" + currentTransaction.getBudgetCategory().getBudgetName() +
					"' AND BUDGETLIMIT=" + currentTransaction.getBudgetCategory().getBudgetLimit();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int budgetID = results.getInt("ID");
			results.close();
			// Get card
			cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + currentTransaction.getCard().getCardName() +
					"' AND CARDNUM='" + currentTransaction.getCard().getCardNum() +
					"' AND HOLDERNAME='" + currentTransaction.getCard().getHolderName() +
					"' AND EXPIREMONTH=" + currentTransaction.getCard().getExpireMonth() +
					" AND EXPIREYEAR=" + currentTransaction.getCard().getExpireYear() +
					" AND PAYDATE=" + currentTransaction.getCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			cmdString = "SELECT COUNT(*) AS CNT FROM TRANSACTION WHERE" +
					" DATE='" + dateFormat.format(currentTransaction.getTime()) +
					"' AND AMOUNT=" + currentTransaction.getAmount() +
					" AND DESCRIPTION='" + currentTransaction.getDescription() +
					"' AND CARDID=" + cardID +
					" AND BUDGETCATEGORYID=" + budgetID;
			stmt = con.createStatement();
			results = stmt.executeQuery(cmdString);
			while (results.next()) {
				int count = results.getInt("CNT");
				if (count == 1) {
					found = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return found;
	}

	/**
	 * This method will insert a new transaction with the ArrayList.
	 *
	 * @param newTransaction transaction that will be inserted
	 * @return true if inserted the transaction properly.
	 */
	public boolean insertTransaction(Transaction newTransaction) {
		boolean result = false;
		try {
			// Get budget category
			String cmdString = "SELECT ID FROM BUDGETCATEGORY WHERE" +
					" BUDGETNAME='" + newTransaction.getBudgetCategory().getBudgetName() +
					"' AND BUDGETLIMIT=" + newTransaction.getBudgetCategory().getBudgetLimit();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int budgetID = results.getInt("ID");
			results.close();
			// Get card
			cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + newTransaction.getCard().getCardName() +
					"' AND CARDNUM='" + newTransaction.getCard().getCardNum() +
					"' AND HOLDERNAME='" + newTransaction.getCard().getHolderName() +
					"' AND EXPIREMONTH=" + newTransaction.getCard().getExpireMonth() +
					" AND EXPIREYEAR=" + newTransaction.getCard().getExpireYear() +
					" AND PAYDATE=" + newTransaction.getCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			String columns = "DATE, AMOUNT, DESCRIPTION, CARDID, BUDGETCATEGORYID";
			String values = "'" + dateFormat.format(newTransaction.getTime()) + "'," +
					newTransaction.getAmount() + ",'" +
					newTransaction.getDescription() + "'," +
					cardID + "," + budgetID;
			cmdString = "INSERT INTO TRANSACTION (" + columns + ") VALUES (" + values + ")";
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * This method will be used to remove a transaction.
	 *
	 * @param currentTransaction transaction that needs to be deleted
	 * @return true if deleted successfully
	 */
	public boolean deleteTransaction(Transaction currentTransaction) {
		boolean result = false;
		try {
			// Get budget category
			String cmdString = "SELECT ID FROM BUDGETCATEGORY WHERE" +
					" BUDGETNAME='" + currentTransaction.getBudgetCategory().getBudgetName() +
					"' AND BUDGETLIMIT=" + currentTransaction.getBudgetCategory().getBudgetLimit();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int budgetID = results.getInt("ID");
			results.close();
			// Get credit card
			cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + currentTransaction.getCard().getCardName() +
					"' AND CARDNUM='" + currentTransaction.getCard().getCardNum() +
					"' AND HOLDERNAME='" + currentTransaction.getCard().getHolderName() +
					"' AND EXPIREMONTH=" + currentTransaction.getCard().getExpireMonth() +
					" AND EXPIREYEAR=" + currentTransaction.getCard().getExpireYear() +
					" AND PAYDATE=" + currentTransaction.getCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			cmdString = "DELETE FROM TRANSACTION WHERE" +
					" DATE='" + dateFormat.format(currentTransaction.getTime()) +
					"' AND AMOUNT=" + currentTransaction.getAmount() +
					" AND DESCRIPTION='" + currentTransaction.getDescription() +
					"' AND CARDID=" + cardID +
					" AND BUDGETCATEGORYID=" + budgetID;
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * This method will be used to update a transaction.
	 *
	 * @param currentTransaction transaction that need to be updated
	 * @param newTransaction     transaction which will be set
	 * @return true if updated successfully.
	 */
	public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction) {
		boolean result = false;
		String values, where;
		try {
			// Get first budget category
			String cmdString = "SELECT ID FROM BUDGETCATEGORY WHERE" +
					" BUDGETNAME='" + newTransaction.getBudgetCategory().getBudgetName() +
					"' AND BUDGETLIMIT=" + newTransaction.getBudgetCategory().getBudgetLimit();
			ResultSet results = stmt.executeQuery(cmdString);
			results.next();
			int newBudgetID = results.getInt("ID");
			results.close();
			// Get first credit card
			cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + newTransaction.getCard().getCardName() +
					"' AND CARDNUM='" + newTransaction.getCard().getCardNum() +
					"' AND HOLDERNAME='" + newTransaction.getCard().getHolderName() +
					"' AND EXPIREMONTH=" + newTransaction.getCard().getExpireMonth() +
					" AND EXPIREYEAR=" + newTransaction.getCard().getExpireYear() +
					" AND PAYDATE=" + newTransaction.getCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int newCardID = results.getInt("ID");
			results.close();
			// Get second budget category
			cmdString = "SELECT ID FROM BUDGETCATEGORY WHERE" +
					" BUDGETNAME='" + currentTransaction.getBudgetCategory().getBudgetName() +
					"' AND BUDGETLIMIT=" + currentTransaction.getBudgetCategory().getBudgetLimit();
			results = stmt.executeQuery(cmdString);
			results.next();
			int currentBudgetID = results.getInt("ID");
			results.close();
			// Get first credit card
			cmdString = "SELECT ID FROM CARD WHERE" +
					" CARDNAME='" + currentTransaction.getCard().getCardName() +
					"' AND CARDNUM='" + currentTransaction.getCard().getCardNum() +
					"' AND HOLDERNAME='" + currentTransaction.getCard().getHolderName() +
					"' AND EXPIREMONTH=" + currentTransaction.getCard().getExpireMonth() +
					" AND EXPIREYEAR=" + currentTransaction.getCard().getExpireYear() +
					" AND PAYDATE=" + currentTransaction.getCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int currentCardID = results.getInt("ID");
			results.close();
			values = "DESCRIPTION='" + newTransaction.getDescription()
					+ "', DATE='" + dateFormat.format(newTransaction.getTime())
					+ "', AMOUNT=" + newTransaction.getAmount()
					+ ", CARDID=" + newCardID
					+ ", BUDGETCATEGORYID=" + newBudgetID;
			where = "WHERE DESCRIPTION='" + currentTransaction.getDescription()
					+ "' AND DATE='" + dateFormat.format(currentTransaction.getTime())
					+ "' AND AMOUNT=" + currentTransaction.getAmount()
					+ " AND CARDID=" + currentCardID
					+ " AND BUDGETCATEGORYID=" + currentBudgetID;
			stmt = con.createStatement();
			cmdString = "UPDATE TRANSACTION SET " + values + " " + where;
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	/**
	 * Getter for username
	 *
	 * @return username
	 */
	public String getUsername() {
		String username = null;
		try {
			String cmdString = "SELECT NAME FROM USERNAME";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			if (results.next()) {
				username = results.getString("NAME");
			} else {
				throw new NullPointerException("No username is set");
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return username;
	}

	/**
	 * setter for username, used when renaming
	 * the username could be anything single line.
	 * this is ensured on presentation side
	 *
	 * @param newUsername String representation of the user's name
	 * @return True if the username was set successfully
	 */
	public boolean setUsername(String newUsername) {
		boolean result = false;
		if (newUsername == null) {
			throw new NullPointerException("Expecting a String!");
		}
		try {
			try {
				String oldUsername = getUsername();
				String cmdString = "UPDATE USERNAME SET NAME='" + newUsername + "' WHERE NAME='" + oldUsername + "'";
				stmt = con.createStatement();
				int updateCount = stmt.executeUpdate(cmdString);
				checkWarning(stmt, updateCount);
				result = true;
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		} catch (NullPointerException npe) {
			try {
				String cmdString = "INSERT INTO USERNAME (NAME) VALUES('" + newUsername + "')";
				stmt = con.createStatement();
				int updateCount = stmt.executeUpdate(cmdString);
				checkWarning(stmt, updateCount);
				result = true;
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		return result;
	}

	/**
	 * Method for checking DB functions
	 *
	 * @param st          statement variable
	 * @param updateCount int variable to update the count
	 */
	public void checkWarning(Statement st, int updateCount) {
		try {
			SQLWarning warning = st.getWarnings();
			if (warning != null) {
				warning.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		if (updateCount < 1) {
			System.out.println("Tuple not inserted correctly.");
		}
	}
}






