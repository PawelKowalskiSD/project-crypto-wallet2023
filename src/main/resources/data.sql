INSERT INTO role (role_id, role_name) values (1, 'ADMIN');
INSERT INTO role (role_id, role_name) values (2, 'USER');


INSERT INTO user (user_id, is_enabled, mail_addressee, password, username) values (1, true, 'admin@123.com', sha1('admin123'), 'admin');
INSERT INTO user (user_id, is_enabled, mail_addressee, password, username) values (2, true, 'jan@123.com', sha1('jan123'), 'jan');

INSERT INTO join_user_role(user_id, role_id) values (1,1), (2,2);

insert into wallet(wallet_id, wallet_name, user_id) VALUES (1, 'demo wallet', 2);

insert into coin(coin_id, average_purchase_price, average_sale_price, coin_name,
                 current_price, quantity, symbol, total_value_of_coins_sold,
                 total_value_purchase_coin, wallet_id)
values (1, 21000, null , 'bitcoin', 32000, 10, 'btc', null, 210000, 1);
