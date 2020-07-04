package comp3350.pbbs.business;

import comp3350.pbbs.objects.CreditCard;
import comp3350.pbbs.application.Main;
import comp3350.pbbs.application.Services;
import comp3350.pbbs.persistence.StubDatabase;

/*
 * Class AccessCreditCard
 * Created by Hao Zheng
 */
public class AccessCreditCard
{
	private StubDatabase db;	//create an object of the database

	public AccessCreditCard() {
		db = Services.getDataAccess(Main.dbName);
	}

	/* method: find a credit card exist or not in database */
	public boolean findCreditCard(CreditCard currCard) {
		return db.findCreditCard(currCard);
	}

	/* method: add a credit card into database */
	public boolean insertCreditCard(CreditCard newCard) {
		if (!findCreditCard(newCard)) {
			db.insertCreditCard(newCard);
			return true;
		}
		return false;
	}

	/* method: delete a credit card from database */
	public boolean deleteCreditCard(CreditCard currCard) {
		if (findCreditCard(currCard)) {
			db.deleteCreditCard(currCard);
			return true;
		}
		return false;
	}

	/* method: update a credit card existed in database */
	public boolean updateCreditCard(CreditCard currCard, CreditCard newCard) {
		return db.updateCreditCard(currCard, newCard);
	}
}