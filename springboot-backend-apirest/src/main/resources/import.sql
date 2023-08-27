/* Populate tables */
INSERT INTO categories(id,name) VALUES(1,'Processors');
INSERT INTO categories(id,name) VALUES(2,'MotherBoards');

INSERT INTO products(id,available_stock,name,is_available,category_id,price) VALUES(1,18,'Intel Core I5 10400',true,1,1999.00);
INSERT INTO products(id,available_stock,name,is_available,category_id,price) VALUES(2,26,'Intel Core I9 13900KF',true,1,10500.00);
INSERT INTO products(id,available_stock,name,is_available,category_id,price) VALUES(3,5,'Ryzen 5 3600',true,1,2560.00);
INSERT INTO products(id,available_stock,name,is_available,category_id,price) VALUES(4,18,'B460 Mag Mortar',true,2,2456.99);
INSERT INTO products(id,available_stock,name,is_available,category_id,price) VALUES(5,18,'MSI Carbon B450',true,2,3000.99);
INSERT INTO products(id,available_stock,name,is_available,category_id,price) VALUES(6,18,'NZXT Z790',true,2,5555.99);

INSERT INTO specifications(name,product_id) VALUES('6 cores',1),('12 threads',1);
INSERT INTO specifications(name,product_id) VALUES('16 cores',2),('32 threads',2);
INSERT INTO specifications(name,product_id) VALUES('6 cores',3),('12 threads',3);
INSERT INTO specifications(name,product_id) VALUES('LGA1200 socket',4),('Up to 64GB Ram',4);
INSERT INTO specifications(name,product_id) VALUES('AM4 socket',5),('Up to 64GB Ram',5);
INSERT INTO specifications(name,product_id) VALUES('LGA1700 socket',6),('Up to 64GB Ram',6);

INSERT INTO roles(id,name) VALUES(1,'ADMIN'),(2,'USER'),(3,'INVITED');

INSERT INTO carts(id) VALUES(1);
INSERT INTO customers(id,email,first_name,last_name,cart_id) VALUES(1,'yazmin@alumno.ipn.mx','Yazmin Lizbeth','Ledesma Ayala',1);
INSERT INTO users(id,username,password,enabled,email,customer_id) VALUES(1,'Yazmin Lizbeth','$2a$12$X7Rq..hwWb/akcz12nz6MO5GnZMwq3/qAm8AMwCxiKvgIrJMYRTni',false,'yazmin@alumno.ipn.mx',1);
INSERT INTO users_roles(user_id,role_id) VALUES(1,2);
INSERT INTO cart_items(id,quantity,subtotal,product_id,cart_id,unitary_price) VALUES(1,2,112.00,1,1,56.00);
INSERT INTO cart_items(id,quantity,subtotal,product_id,cart_id,unitary_price) VALUES(2,1,634.99,2,1,634.99);

INSERT INTO users(id,username,password,enabled,email,customer_id) VALUES(2,'Angel Yeremi','$2a$12$X7Rq..hwWb/akcz12nz6MO5GnZMwq3/qAm8AMwCxiKvgIrJMYRTni',false,'angel@alumno.ipn.mx',null);
INSERT INTO users_roles(user_id,role_id) VALUES(2,1);
