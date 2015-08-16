CREATE TABLE event (
  event_id VARCHAR(50) NOT NULL,
  event_description VARCHAR(100),
  start_date DATE,
  end_date DATE,
  insert_date DATE,
  deleted BIT(1) DEFAULT 0,
  PRIMARY KEY(event_id)
);

CREATE TABLE user (
  user_id varchar(255) NOT NULL,
  enabled bit(1) NOT NULL,
  password varchar(255) NOT NULL,
  user_name varchar(255) NOT NULL,
  insert_date datetime DEFAULT NULL,
  deleted bit(1) NOT NULL,
  PRIMARY KEY (user_id)
);


INSERT INTO event(event_id,event_description,start_date,end_date,insert_date,deleted)
VALUES("1", "(Responsive) UI Testing mit Galen", "2015-08-18", NULL, "2015-07-01", false);

INSERT INTO event(event_id,event_description,start_date,end_date,insert_date,deleted)
VALUES("2", "Ein großes Event für Groß und Klein, damit auch jeder was davon hat!", "2015-08-01", "2015-08-21", "2015-07-01", false);

INSERT INTO event(event_id,event_description,start_date,end_date,insert_date,deleted)
VALUES("3", "Clean Code Session", "2015-08-03", "2015-08-05", "2015-07-01", false);

INSERT INTO event(event_id,event_description,start_date,end_date,insert_date,deleted)
VALUES("4", "Spieleabend", "2015-08-01", NULL, "2015-07-01", false);

INSERT INTO user(user_id,user_name,password,insert_date,enabled,deleted)
VALUES ("1", "user", "$2a$10$o2C6NPSNsq45fV.qArHXiep0OGb4YNCODGQNFpKWQ7TX7jZuiCKYq", "2015-07-01", true, false);