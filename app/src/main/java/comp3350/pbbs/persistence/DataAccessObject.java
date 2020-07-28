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

import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.objects.BudgetCategory;

public class DataAccessObject implements DataAccess {
	private Connection con;	// for DB switch
	private Statement stmt; // statement
	private String dbName;	// name of DB
	private String dbType;	// type of DB
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * Constructor of DB
	 * @param dbName the DB name
	 */
	public DataAccessObject(String dbName) {
		this.dbName = dbName;
	}

	public void open(String dbPath) {
		String url;
		try {
			dbType = "HSQL";
			url = "jdbc:hsqldb:file:" + dbPath;
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			con = DriverManager.getConnection(url, "PBBS", "");
			System.out.println("Opened " + dbType + " database " + dbPath);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

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
	 * Methods for BudgetCategories
	 */
	public List<BudgetCategory> getBudgets() {
		List<BudgetCategory> toReturn = new ArrayList<BudgetCategory>();
		try {
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM BUDGETCATEGORY");
			while(results.next()) {
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

	public boolean addBudgetCategories(List<BudgetCategory> budgetList) {
		boolean result = false;
		for(BudgetCategory budgetCategory : budgetList) {
			result = insertBudgetCategory(budgetCategory);
			if(!result)
				break;
		}
		return result;
	}

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
				if(count == 1) {
					result = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

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

	public boolean deleteBudgetCategory(BudgetCategory currentBudget) {
		boolean toReturn = false;
		try {
			String cmdString = "DELETE FROM BUDGETCATEGORY WHERE BUDGETNAME='" +
					currentBudget.getBudgetName() + "' AND BUDGETLIMIT=" + currentBudget.getBudgetLimit();
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			toReturn = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return toReturn;
	}

	public int getBudgetsSize() {
		int count = 0;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM BUDGETCATEGORY";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while(results.next()) {
				count = results.getInt("CNT");
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return count;
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
	 * Methods for CreditCards
	 */
	public List<CreditCard> getCreditCards() {
		List<CreditCard> creditCards = new ArrayList<CreditCard>();
		try {
			String cmdString = "SELECT * FROM CREDITCARD";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while(results.next()) {
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				CreditCard card = new CreditCard(cardName, cardNum, name, expireMonth, expireYear, payDate);
				creditCards.add(card);
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return creditCards;
	}

	public int getCardsSize() {
		int count = 0;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM CREDITCARD";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()){
				count = results.getInt("CNT");
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return count;
	}

	public boolean addAllCreditCards(List<CreditCard> cardList) {
		boolean result = false;
		for(CreditCard creditCard : cardList) {
			result = insertCreditCard(creditCard);
			if(!result)
				break;
		}
		return result;
	}

	public boolean findCreditCard(CreditCard currCard) {
		boolean result = false;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM CREDITCARD WHERE" +
					" CARDNAME='" + currCard.getCardName() +
					"' AND CARDNUM='" +	currCard.getCardNum() +
					"' AND HOLDERNAME='" + currCard.getHolderName() +
					"' AND EXPIREMONTH=" + currCard.getExpireMonth() +
					" AND EXPIREYEAR=" + currCard.getExpireYear() +
					" AND PAYDATE=" + currCard.getPayDate();
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()){
				int count = results.getInt("CNT");
				if(count == 1) {
					result = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return result;
	}

	public boolean insertCreditCard(CreditCard newCard) {
		String values;
		boolean toReturn = false;
		if(!findCreditCard(newCard)) {
			try {
				values = "'" + newCard.getCardName()
						+ "', '" + newCard.getCardNum()
						+ "', '" + newCard.getHolderName()
						+ "', " + newCard.getExpireMonth()
						+ ", " + newCard.getExpireYear()
						+ ", " + newCard.getPayDate();
				String cmdString = "INSERT INTO CREDITCARD (CARDNAME, CARDNUM, HOLDERNAME," +
						" EXPIREMONTH, EXPIREYEAR, PAYDATE) VALUES(" + values + ")";
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

	public boolean deleteCreditCard(CreditCard currCard) {
		boolean toReturn = false;
		try {
			String cmdString = "DELETE FROM CREDITCARD WHERE" +
					" CARDNAME='" + currCard.getCardName() +
					"' AND CARDNUM='" +	currCard.getCardNum() +
					"' AND HOLDERNAME='" + currCard.getHolderName() +
					"' AND EXPIREMONTH=" + currCard.getExpireMonth() +
					" AND EXPIREYEAR=" + currCard.getExpireYear() +
					" AND PAYDATE=" + currCard.getPayDate();
			stmt = con.createStatement();
			int updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			toReturn = true;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return toReturn;
	}

	public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
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
			String cmdString = "UPDATE CREDITCARD SET " + values + " WHERE " + where;
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
	 * Methods for Transactions
	 */
	public List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			String cmdString = "SELECT * FROM TRANSACTION AS T INNER JOIN BUDGETCATEGORY AS BC ON" +
					" T.BUDGETCATEGORYID=BC.ID INNER JOIN CREDITCARD AS C ON T.CREDITCARDID=C.ID";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while(results.next()) {
				Date time = dateFormat.parse(results.getString("DATE"));
				float amount = (float)results.getDouble("AMOUNT");
				String description = results.getString("DESCRIPTION");
				// Get credit card
				String cardName = results.getString("CARDNAME");
				String cardNum = results.getString("CARDNUM");
				String name = results.getString("HOLDERNAME");
				int expireMonth = results.getInt("EXPIREMONTH");
				int expireYear = results.getInt("EXPIREYEAR");
				int payDate = results.getInt("PAYDATE");
				CreditCard card = new CreditCard(cardName, cardNum, name, expireMonth, expireYear, payDate);
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

	public int getTransactionsSize() {
		int count = 0;
		try {
			String cmdString = "SELECT COUNT(*) AS CNT FROM TRANSACTION";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			while (results.next()){
				count = results.getInt("CNT");
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return count;
	}

	public boolean addTransactions(List<Transaction> transactionsList) {
		boolean result = false;
		for(Transaction transaction : transactionsList) {
			result = insertTransaction(transaction);
			if(!result)
				break;
		}
		return result;
	}

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
			// Get credit card
			cmdString = "SELECT ID FROM CREDITCARD WHERE" +
					" CARDNAME='" + currentTransaction.getCard().getCardName() +
					"' AND CARDNUM='" +	currentTransaction.getCard().getCardNum() +
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
					"' AND CREDITCARDID=" + cardID +
					" AND BUDGETCATEGORYID=" + budgetID;
			stmt = con.createStatement();
			results = stmt.executeQuery(cmdString);
			while (results.next()){
				int count = results.getInt("CNT");
				if(count == 1) {
					found = true;
				}
			}
			results.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return found;
	}

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
			// Get credit card
			cmdString = "SELECT ID FROM CREDITCARD WHERE" +
					" CARDNAME='" + newTransaction.getCard().getCardName() +
					"' AND CARDNUM='" +	newTransaction.getCard().getCardNum() +
					"' AND HOLDERNAME='" + newTransaction.getCard().getHolderName() +
					"' AND EXPIREMONTH=" + newTransaction.getCard().getExpireMonth() +
					" AND EXPIREYEAR=" + newTransaction.getCard().getExpireYear() +
					" AND PAYDATE=" + newTransaction.getCard().getPayDate();
			results = stmt.executeQuery(cmdString);
			results.next();
			int cardID = results.getInt("ID");
			results.close();
			String columns = "DATE, AMOUNT, DESCRIPTION, CREDITCARDID, BUDGETCATEGORYID";
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
			cmdString = "SELECT ID FROM CREDITCARD WHERE" +
					" CARDNAME='" + currentTransaction.getCard().getCardName() +
					"' AND CARDNUM='" +	currentTransaction.getCard().getCardNum() +
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
					"' AND CREDITCARDID=" + cardID +
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
			cmdString = "SELECT ID FROM CREDITCARD WHERE" +
					" CARDNAME='" + newTransaction.getCard().getCardName() +
					"' AND CARDNUM='" +	newTransaction.getCard().getCardNum() +
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
			cmdString = "SELECT ID FROM CREDITCARD WHERE" +
					" CARDNAME='" + currentTransaction.getCard().getCardName() +
					"' AND CARDNUM='" +	currentTransaction.getCard().getCardNum() +
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
					+ ", CREDITCARDID=" + newCardID
					+ ", BUDGETCATEGORYID=" + newBudgetID;
			where = "WHERE DESCRIPTION='" + currentTransaction.getDescription()
					+ "' AND DATE='" + dateFormat.format(currentTransaction.getTime())
					+ "' AND AMOUNT=" + currentTransaction.getAmount()
					+ " AND CREDITCARDID=" + currentCardID
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
	 * Methods for Users
	 */
	public String getUsername() {
		String username = null;
		try {
			String cmdString = "SELECT NAME FROM USERNAME";
			stmt = con.createStatement();
			ResultSet results = stmt.executeQuery(cmdString);
			if(results.next()) {
				username = results.getString("NAME");
			}
			else {
				throw new NullPointerException("No username is set");
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return username;
	}

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
			} catch(SQLException e) {
				System.out.println(e.toString());
			}
		} catch (NullPointerException npe) {
			try {
				String cmdString = "INSERT INTO USERNAME (NAME) VALUES('" + newUsername + "')";
				stmt = con.createStatement();
				int updateCount = stmt.executeUpdate(cmdString);
				checkWarning(stmt, updateCount);
				result = true;
			} catch(SQLException e) {
				System.out.println(e.toString());
			}
		}
		return result;
	}

	/**
	 * Methods for DB functions
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






