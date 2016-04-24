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