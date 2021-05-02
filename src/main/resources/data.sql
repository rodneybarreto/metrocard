-- accounts
INSERT INTO accounts(owner,balance) VALUES('Bob',26.0);
INSERT INTO accounts(owner,balance) VALUES('Teddy',126.0);
-- users
INSERT INTO users(username,password,account_id) VALUES('Bob','1234',1);
INSERT INTO users(username,password,account_id) VALUES('Teddy','5678',2);
-- cards
INSERT INTO cards(account_id,zone_type,acquire_date) VALUES(1,'A',CURDATE());

