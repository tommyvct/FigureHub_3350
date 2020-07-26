package comp3350.pbbs.objects;

public class BankAccount  // TODO: implementation pending
{
    private String accountName;
    private String accountNumber;

    public BankAccount(String accountName, String accountNumber)
    {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
}
