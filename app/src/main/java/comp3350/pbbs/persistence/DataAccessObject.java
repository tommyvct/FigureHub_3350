package comp3350.pbbs.persistence;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.objects.Transaction;
import comp3350.pbbs.objects.BudgetCategory;

public class DataAccessObject implements DataAccess {
	private Connection con;	// for DB switch
	private Statement stmt;	// 1 statement running at a time
	private ResultSet rs1, rs2, rs3, rs4, rs5; // for DB switch and 4 accesses
	private String dbName;	// name of DB
	private String dbType;	// type of DB
	private String cmdString;	// store SQL codes
	private int updateCount;

	private ArrayList<BudgetCategory> budgetCategories;
	private ArrayList<CreditCard> creditCards;
	private ArrayList<Transaction> transactions;
	private String username;

	/**
	 * Constructor of DB
	 * @param dbName the DB name
	 */
	public DataAccessObject(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * This method contains hsql setup and allows DB to run
	 * @param dbPath directory of the project DB
	 */
	public void open(String dbPath) {
		String url;
		try {
			dbType = "HSQL";
			url = "jdbc:hsqldb:file:" + dbPath;
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			con = DriverManager.getConnection(url, "SA", "");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println("Opened " + dbType + " database " + dbPath);
	}

	/**
	 * This method commits all changes to the DB then terminate it
	 */
	public void close() {
		try {
			stmt = con.createStatement();
			stmt.execute("SHUTDOWN");
			con.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println("Closed " + dbType + " database " + dbName);
	}

	/**
	 * Methods for BudgetCategories
	 */
	public ArrayList<BudgetCategory> getBudgets() {
		try {
			rs2 = stmt.executeQuery("SELECT * FROM BUDGETCATEGORIES");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return budgetCategories;
	}

	public int getBudgetsSize() {
		int count = 0;
		try {
			cmdString = "SELECT Count(*) from BUDGETCATEGORIES";
			rs2 = stmt.executeQuery(cmdString);
			rs2.next();
			count = rs2.getInt(1);
			rs2.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return count;
	}

	public boolean addBudgetCategories(List<BudgetCategory> budgetList) {
		BudgetCategory budgetCategory;
		String myBudgetName;
		double myBudgetLimit;
		boolean result = false;
		try {
			cmdString = "SELECT * from BUDGETCATEGORIES";
			rs2 = stmt.executeQuery(cmdString);
			while (rs2.next()) {
				myBudgetName = rs2.getString("BUDGETNAME");
				myBudgetLimit = rs2.getDouble("BUDGETLIMIT");
				budgetCategory = new BudgetCategory(myBudgetName, myBudgetLimit);
				budgetList.add(budgetCategory);
				result = true;
			}
			rs2.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public BudgetCategory findBudgetCategory(BudgetCategory currentBudget) {
		BudgetCategory budgetCategory = null;
		String myBudgetName;
		double myBudgetLimit;
		try {
			cmdString = "SELECT * from BUDGETCATEGORIES where BUDGETNAME="
					+ currentBudget.getBudgetName();
			rs2 = stmt.executeQuery(cmdString);
			while (rs2.next()) {
				myBudgetName = rs2.getString("BUDGETNAME");
				myBudgetLimit = rs2.getDouble("BUDGETLIMIT");
				budgetCategory = new BudgetCategory(myBudgetName, myBudgetLimit);
				budgetCategories.add(budgetCategory);
			}
			rs2.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return budgetCategory;
	}

	public boolean insertBudgetCategory(BudgetCategory newBudget) {
		boolean result = false;
		String values;
		try {
			values = "'" + newBudget.getBudgetName() + "', " + newBudget.getBudgetLimit();
			cmdString = "Insert into BUDGETCATEGORIES " + " Values(" + values + ")";
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public BudgetCategory deleteBudgetCategory(BudgetCategory currentBudget) {
		String values;
		try {
			values = "'" + currentBudget.getBudgetName() + "'";
			cmdString = "Delete from BUDGETCATEGORIES where BUDGETNAME=" + values;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return currentBudget;
	}

	public BudgetCategory updateBudgetCategory(BudgetCategory currentBudget, BudgetCategory newBudget) {
		String values, where;
		try {
			values = "BUDGETNAME='" + newBudget.getBudgetName()
					+ "', BUDGETLIMIT=" + newBudget.getBudgetLimit();
			where = "where BUDGETNAME='" + currentBudget.getBudgetName()
					+ "', BUDGETLIMIT=" + currentBudget.getBudgetLimit();	// primary key?
			cmdString = "Update BUDGETCATEGORIES " + " Set " + values + " " + where;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return newBudget;
	}

	/**
	 * Methods for CreditCards
	 */
	public ArrayList<CreditCard> getCreditCards() {
		try {
			cmdString = "Select * from CREDITCARDS";
			rs3 = stmt.executeQuery(cmdString);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return creditCards;
	}

	public int getCardsSize() {
		int count = 0;
		try {
			cmdString = "Select Count(*) from CREDITCARDS";
			rs3 = stmt.executeQuery(cmdString);
			rs3.next();
			count = rs3.getInt(1);
			rs3.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return count;
	}

	public boolean addAllCreditCards(List<CreditCard> cardList) {
		CreditCard creditCard;
		String myCardName, myCardNum, myHolderName;
		int myExpireMonth, myExpireYear, myPayDate;
		boolean result = false;
		try {
			cmdString = "Select * from CREDITCARDS";
			rs3 = stmt.executeQuery(cmdString);
			while (rs3.next()) {
				myCardName = rs3.getString("CARDNAME");
				myCardNum = rs3.getString("CARDNUM");
				myHolderName = rs3.getString("HOLDERNAME");
				myExpireMonth = rs3.getInt("EXPIREMONTH");
				myExpireYear = rs3.getInt("EXPIREYEAR");
				myPayDate = rs3.getInt("PAYDATE");
				creditCard = new CreditCard(myCardName, myCardNum, myHolderName,
						myExpireMonth, myExpireYear, myPayDate);
				cardList.add(creditCard);
				result = true;
			}
			rs3.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public boolean findCreditCard(CreditCard currCard) {
		CreditCard creditCard = null;
		String myCardName, myCardNum, myHolderName;
		int myExpireMonth, myExpireYear, myPayDate;
		boolean result = false;
		try {
			cmdString = "Select * from CREDITCARDS where CARDNUM=" + currCard.getCardNum();
			rs3 = stmt.executeQuery(cmdString);
			while (rs3.next()) {
				myCardName = rs3.getString("CARDNAME");
				myCardNum = rs3.getString("CARDNUM");
				myHolderName = rs3.getString("HOLDERNAME");
				myExpireMonth = rs3.getInt("EXPIREMONTH");
				myExpireYear = rs3.getInt("EXPIREYEAR");
				myPayDate = rs3.getInt("PAYDATE");
				creditCard = new CreditCard(myCardName, myCardNum, myHolderName,
						myExpireMonth, myExpireYear, myPayDate);
				creditCards.add(creditCard);
				result = true;
			}
			rs3.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public void insertCreditCard(CreditCard newCard) {
		String values;
		try {
			values = newCard.getCardNum()
					+ ", '" + newCard.getCardName()
					+ "', '" + newCard.getHolderName()
					+ "', " + newCard.getExpireMonth()
					+ ", " + newCard.getExpireYear()
					+ ", " + newCard.getPayDate();
			cmdString = "Insert into CREDITCARDS " + " Values(" + values + ")";
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public void deleteCreditCard(CreditCard currCard) {
		String values;
		try {
			values = "'" + currCard.getCardNum() + "'";
			cmdString = "Delete from CREDITCARDS where CARDNUM=" + values;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
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
			where = "where CARDNAMR='" + newCard.getCardName()
					+ "', CARDNUM='" + newCard.getCardNum()
					+ "', HOLDERNAME='" + newCard.getHolderName()
					+ "', EXPIREMONTH=" + newCard.getExpireMonth()
					+ ", EXPIREYEAR=" + newCard.getExpireYear()
					+ ", PAYDATE=" + newCard.getPayDate();
			cmdString = "Update CREDITCARDS " + " Set " + values + " " + where;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	/**
	 * Methods for Transactions
	 */
	public ArrayList<Transaction> getTransactions() {
		try {
			cmdString = "Select * from TRANSACTIONS";
			rs4 = stmt.executeQuery(cmdString);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return transactions;
	}

	public int getTransactionsSize() {
		int count = 0;
		try {
			cmdString = "Select Count(*) from TRANSACTIONS";
			rs4 = stmt.executeQuery(cmdString);
			rs4.next();
			count = rs4.getInt(1);
			rs4.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return count;
	}

	public boolean addTransactions(List<Transaction> transactionsList) {
		Transaction transaction;
		Date myDate;
		float myAmount;
		String myDescription;
		CreditCard myCreditCard;
		BudgetCategory myBudgetCategory;
		boolean result = false;
		try {
			cmdString = "Select * from TRANSACTIONS";
			rs4 = stmt.executeQuery(cmdString);
			while (rs4.next()) {
				myDate = rs4.getDate("TIME");
				myAmount = rs4.getFloat("AMOUNT");
				myDescription = rs4.getString("DESCRIPTION");
				myCreditCard = (CreditCard)rs4.getObject("CARD");
				myBudgetCategory = (BudgetCategory)rs4.getObject("BUDGETCATEGORY");
				transaction = new Transaction(myDate, myAmount, myDescription,
						myCreditCard, myBudgetCategory);
				transactionsList.add(transaction);
				result = true;
			}
			rs4.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public Transaction findTransaction(Transaction currentTransaction) {
		Transaction transaction = null;
		Date myDate;
		float myAmount;
		String myDescription;
		CreditCard myCreditCard;
		BudgetCategory myBudgetCategory;
		try {
			cmdString = "Select * from TRANSACTIONS where CREDITCARD=" +
					currentTransaction.getDescription();
			rs4 = stmt.executeQuery(cmdString);
			while (rs4.next()) {
				myDate = rs4.getDate("TIME");
				myAmount = rs4.getFloat("AMOUNT");
				myDescription = rs4.getString("DESCRIPTION");
				myCreditCard = (CreditCard)rs4.getObject("CARD");
				myBudgetCategory = (BudgetCategory)rs4.getObject("BUDGETCATEGORY");
				transaction = new Transaction(myDate, myAmount, myDescription,
						myCreditCard, myBudgetCategory);
				transactions.add(transaction);
			}
			rs4.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return transaction;
	}

	public boolean insertTransaction(Transaction newTransaction) {
		boolean result = false;
		String values;
		try {
			values = "'" + newTransaction.getDescription()
					+ "', " + newTransaction.getAmount()
					+ ", '" + newTransaction.getTime()
					+ "', '" + newTransaction.getCard() + "', '"
					+ newTransaction.getBudgetCategory() + "'";
			cmdString = "Insert into TRANSACTIONS " + " Values(" + values + ")";
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public boolean deleteTransaction(Transaction currentTransaction) {
		boolean result = false;
		String values;
		try {
			values = "'" + currentTransaction.getDescription() + "'";
			cmdString = "Delete from TRANSACTIONS where DESCRIPTION=" + values;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	public boolean updateTransaction(Transaction currentTransaction, Transaction newTransaction) {
		boolean result = false;
		String values, where;
		try {
			values = "DESCRIPTION='" + newTransaction.getDescription()
					+ "', TIME='" + newTransaction.getTime()
					+ "', AMOUNT=" + newTransaction.getAmount()
					+ ", CARD='" + newTransaction.getCard()
					+ "', BUDGETCATEGORY='" + newTransaction.getBudgetCategory() + "'";
			where = "where DESCRIPTION='" + currentTransaction.getDescription()
					+ "', TIME='" + currentTransaction.getTime()
					+ "', AMOUNT=" + currentTransaction.getAmount()
					+ ", CARD='" + currentTransaction.getCard()
					+ "', BUDGETCATEGORY='" + currentTransaction.getBudgetCategory() + "'";	// primary key?
			cmdString = "Update TRANSACTIONS " + " Set " + values + " " + where;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
			result = true;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return result;
	}

	/**
	 * Methods for Users
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String newUsername) {
		String values, where;
		try {
			values = "USERNAME='" + newUsername;
			where = "where USERNAME='" + username;
			cmdString = "Update USERNAME " + " Set " + values + " " + where;
			updateCount = stmt.executeUpdate(cmdString);
			checkWarning(stmt, updateCount);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
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
		if (updateCount != 1) {
			System.out.println("Tuple not inserted correctly.");
		}
	}
}






