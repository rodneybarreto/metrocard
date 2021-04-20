-- accounts
INSERT INTO accounts(owner,balance) VALUES('Bob',26.0);
INSERT INTO accounts(owner,balance) VALUES('Teddy',126.0);
-- cards
INSERT INTO cards(account_id,zone_type,acquire_date) VALUES(1,'A',CURDATE());

