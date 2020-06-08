CREATE TABLE Transactions 
                (
                    Description                     TEXT,
                    Amount                          REAL,
                    Date                            DATE,
                    Pending                         BOOL,
                    ForeignCurrencyAmount           REAL,
                    ForeignCurrency                 CHAR[3],
                    Active                          BOOL NOT NULL,
                    CardNumber                      TEXT,
                    BankAccountNumber               TEXT,
                    FOREIGN KEY (CardNumber)        REFERENCES Cards(CardNumber),
                    FOREIGN KEY (BankAccountNumber) REFERENCES BankAccount(BankAccountNumber)
                );

CREATE TABLE BankAccount 
                (
                    BankAccountNumber              TEXT PRIMARY KEY,
                    Balance                        REAL,
                    Currency                       CHAR[3] NOT NULL,
                    Active                         BOOL NOT NULL,
                    BankAccountName                TEXT,
                    InstitutionNumber              TEXT,
                    TransitNumber                  TEXT,
                    Comments                       TEXT,
                    LinkedCardNumber               TEXT,
                    FOREIGN KEY (LinkedCardNumber) REFERENCES Cards(CardNumber)
                );
                
CREATE TABLE Cards 
                (
                    CardType        TEXT,
                    CardNumber      TEXT PRIMARY KEY,
                    CardName        TEXT,
                    ValidThru       DATE,
                    CardholderName  TEXT,
                    CVV             TEXT,
                    Currency        CHAR[3] NOT NULL,
                    Balance         REAL,

                    CreditLimit     REAL,
                    CycleDate       DATE,
                    DueDate         DATE,
                    DueBalance      REAL,
                    Active          BOOL NOT NULL
                );