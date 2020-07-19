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

public class DataAccessObject
{
	private Connection c1;	// for DB switch
	private Statement st1;	// 1 statement running at a time
	private ResultSet rs1, rs2, rs3, rs4; // for DB switch and 3 objects
	private String dbName;	// name of DB
	private String dbType;	// type of DB
	private String cmdString;	// store SQL codes
	private int updateCount;

	private ArrayList<BudgetCategory> budgetCategories;
	private ArrayList<CreditCard> creditCards;
	private ArrayList<Transaction> transactions;

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
			c1 = DriverManager.getConnection(url, "SA", "");
			st1 = c1.createStatement();
		} catch (Exception e) {
			processSQLError(e);
		}
		System.out.println("Opened " + dbType + " database " + dbPath);
	}

	/**
	 * This method commits all changes to the DB then terminate it
	 */
	public void close() {
		try {
			cmdString = "shutdown compact";
			rs1 = st1.executeQuery(cmdString);
			c1.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		System.out.println("Closed " + dbType + " database " + dbName);
	}

	/**
	 * Methods for BudgetCategories
	 */
	public ArrayList<BudgetCategory> getBudgets() {
		try {
			cmdString = "Select * from BudgetCategories";
			rs2 = st1.executeQuery(cmdString);
		} catch (Exception e) {
			processSQLError(e);
		}
		return budgetCategories;
	}

	public boolean addAllBudgetCategories(List<BudgetCategory> budgetList) {
		BudgetCategory budgetCategory;
		String myBudgetName;
		double myBudgetLimit;
		boolean result = false;
		try {
			cmdString = "Select * from BudgetCategories";
			rs2 = st1.executeQuery(cmdString);
			while (rs2.next()) {
				myBudgetName = rs2.getString("budgetName");
				myBudgetLimit = rs2.getDouble("budgetLimit");
				budgetCategory = new BudgetCategory(myBudgetName, myBudgetLimit);
				budgetList.add(budgetCategory);
				result = true;
			}
			rs2.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean findBudgetCategory(BudgetCategory currBudget) {
		BudgetCategory budgetCategory;
		String myBudgetName;
		double myBudgetLimit;
		boolean result = false;
		try {
			cmdString = "Select * from BudgetCategories where budgetName="
					+ currBudget.getBudgetName();
			rs2 = st1.executeQuery(cmdString);
			while (rs2.next()) {
				myBudgetName = rs2.getString("budgetName");
				myBudgetLimit = rs2.getDouble("budgetLimit");
				budgetCategory = new BudgetCategory(myBudgetName, myBudgetLimit);
				budgetCategories.add(budgetCategory);
				result = true;
			}
			rs2.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean insertBudgetCategory(BudgetCategory newBudget) {
		boolean result = false;
		String values;
		try {
			values = "'" + newBudget.getBudgetName() + "', " + newBudget.getBudgetLimit();
			cmdString = "Insert into BudgetCategories " + " Values(" + values + ")";
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean deleteBudgetCategory(BudgetCategory currBudget) {
		boolean result = false;
		String values;
		try {
			values = "'" + currBudget.getBudgetName() + "'";
			cmdString = "Delete from BudgetCategory where budgetName=" + values;
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean updateBudgetCategory(BudgetCategory newBudget) {
		boolean result = false;
		String values, where;
		try {
			values = "budgetLimit=" + newBudget.getBudgetLimit();
			where = "where budgetName='" + newBudget.getBudgetName() + "'";
			cmdString = "Update BudgetCategories " + " Set " + values + " " + where;
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	/**
	 * Methods for CreditCards
	 */

	public ArrayList<CreditCard> getCreditCards() {
		try {
			cmdString = "Select * from CreditCards";
			rs3 = st1.executeQuery(cmdString);
		} catch (Exception e) {
			processSQLError(e);
		}
		return creditCards;
	}

	public boolean addAllCreditCards(List<CreditCard> cardList) {
		CreditCard creditCard;
		String myCardName, myCardNum, myHolderName;
		int myExpireMonth, myExpireYear, myPayDate;
		boolean result = false;
		try {
			cmdString = "Select * from CreditCards";
			rs3 = st1.executeQuery(cmdString);
			while (rs3.next()) {
				myCardName = rs3.getString("cardName");
				myCardNum = rs3.getString("cardNum");
				myHolderName = rs3.getString("holderName");
				myExpireMonth = rs3.getInt("expireMonth");
				myExpireYear = rs3.getInt("expireYear");
				myPayDate = rs3.getInt("payDate");
				creditCard = new CreditCard(myCardName, myCardNum, myHolderName,
						myExpireMonth, myExpireYear, myPayDate);
				cardList.add(creditCard);
				result = true;
			}
			rs3.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean findCreditCard(CreditCard currCard) {
		CreditCard creditCard;
		String myCardName, myCardNum, myHolderName;
		int myExpireMonth, myExpireYear, myPayDate;
		boolean result = false;
		try {
			cmdString = "Select * from CreditCards where cardNum=" + currCard.getCardNum();
			rs3 = st1.executeQuery(cmdString);
			while (rs3.next()) {
				myCardName = rs3.getString("cardName");
				myCardNum = rs3.getString("cardNum");
				myHolderName = rs3.getString("holderName");
				myExpireMonth = rs3.getInt("expireMonth");
				myExpireYear = rs3.getInt("expireYear");
				myPayDate = rs3.getInt("payDate");
				creditCard = new CreditCard(myCardName, myCardNum, myHolderName,
						myExpireMonth, myExpireYear, myPayDate);
				creditCards.add(creditCard);
				result = true;
			}
			rs3.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean insertCreditCard(CreditCard newCard) {
		boolean result = false;
		String values;
		try {
			values = "'" + newCard.getCardName() + "', " + newCard.getCardNum() + "', "
					+ newCard.getHolderName() + "', " + newCard.getExpireMonth() + ", "
					+ newCard.getExpireYear() + ", " + newCard.getPayDate();
			cmdString = "Insert into CreditCards " + " Values(" + values + ")";
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean deleteCreditCard(CreditCard currCard) {
		boolean result = false;
		String values;
		try {
			values = "'" + currCard.getCardNum() + "'";
			cmdString = "Delete from CreditCards where cardNum=" + values;
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean updateCreditCard(CreditCard newCard) {
		boolean result = false;
		String values, where;
		try {
			values = "cardName='" + newCard.getCardName() +
					"', holderName='" + newCard.getHolderName() +
					"', expireMonth=" + newCard.getExpireMonth() +
					", expireYear=" + newCard.getExpireYear() +
					", payDate=" + newCard.getPayDate();
			where = "where cardNum='" + newCard.getCardNum() + "'";
			cmdString = "Update CreditCards " + " Set " + values + " " + where;
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	/**
	 * Methods for Transactions
	 */
	public ArrayList<Transaction> getTransactions() {
		try {
			cmdString = "Select * from Transactions";
			rs4 = st1.executeQuery(cmdString);
		} catch (Exception e) {
			processSQLError(e);
		}
		return transactions;
	}

	public boolean addAllTransactions(List<Transaction> transactionsList) {
		Transaction transaction;
		Date myDate;
		float myAmount;
		String myDescription;
		CreditCard myCreditCard;
		BudgetCategory myBudgetCategory;
		boolean result = false;
		try {
			cmdString = "Select * from Transactions";
			rs4 = st1.executeQuery(cmdString);
			while (rs4.next()) {
				myDate = rs4.getDate("time");
				myAmount = rs4.getFloat("amount");
				myDescription = rs4.getString("description");
				myCreditCard = (CreditCard)rs4.getObject("card");
				myBudgetCategory = (BudgetCategory)rs4.getObject("budgetCategory");
				transaction = new Transaction(myDate, myAmount, myDescription,
						myCreditCard, myBudgetCategory);
				transactionsList.add(transaction);
				result = true;
			}
			rs4.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean findTransaction(Transaction currTransaction) {
		Transaction transaction;
		Date myDate;
		float myAmount;
		String myDescription;
		CreditCard myCreditCard;
		BudgetCategory myBudgetCategory;
		boolean result = false;
		try {
			cmdString = "Select * from Transactions where creditCard=" +
					currTransaction.getDescription();
			rs4 = st1.executeQuery(cmdString);
			while (rs4.next()) {
				myDate = rs4.getDate("time");
				myAmount = rs4.getFloat("amount");
				myDescription = rs4.getString("description");
				myCreditCard = (CreditCard)rs4.getObject("card");
				myBudgetCategory = (BudgetCategory)rs4.getObject("budgetCategory");
				transaction = new Transaction(myDate, myAmount, myDescription,
						myCreditCard, myBudgetCategory);
				transactions.add(transaction);
				result = true;
			}
			rs4.close();
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean insertTransaction(Transaction newTransaction) {
		boolean result = false;
		String values;
		try {
			values = "'" + newTransaction.getDescription() + "', " +
					newTransaction.getAmount() + ", '" + newTransaction.getTime()
					+ "', '" + newTransaction.getCard() + "', '"
					+ newTransaction.getBudgetCategory() + "'";
			cmdString = "Insert into Transactions " + " Values(" + values + ")";
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean deleteTransaction(Transaction currTransaction) {
		boolean result = false;
		String values;
		try {
			values = "'" + currTransaction.getDescription() + "'";
			cmdString = "Delete from Transactions where description=" + values;
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public boolean updateTransaction(Transaction newTransaction) {
		boolean result = false;
		String values, where;
		try {
			values = "time='" + newTransaction.getTime() +
					"', amount=" + newTransaction.getAmount() +
					", card='" + newTransaction.getCard() +
					"', budgetCategory='" + newTransaction.getBudgetCategory() + "'";
			where = "where description='" + newTransaction.getDescription() + "'";
			cmdString = "Update Transactions " + " Set " + values + " " + where;
			updateCount = st1.executeUpdate(cmdString);
			checkWarning(st1, updateCount);
			result = true;
		} catch (Exception e) {
			processSQLError(e);
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
			processSQLError(e);
		}
		if (updateCount != 1) {
			System.out.println("Tuple not inserted correctly.");
		}
	}

	public void processSQLError(Exception e) {
		e.printStackTrace();
	}
}






