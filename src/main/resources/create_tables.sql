CREATE SEQUENCE user_sequence;

CREATE TABLE users (
	user_id BIGINT PRIMARY KEY DEFAULT nextval('user_sequence'),
	user_name VARCHAR(20) NOT NULL UNIQUE, 
	user_pass VARCHAR(100) NOT NULL,
	user_avatar VARCHAR(100) NOT NULL,
	user_role VARCHAR(20) NOT NULL
);

CREATE SEQUENCE category_sequence;

CREATE TABLE categories (
	category_id BIGINT PRIMARY KEY DEFAULT nextval('category_sequence'),
	category_name VARCHAR(100) NOT NULL,
	category_super_id BIGINT NULL REFERENCES categories (category_id),
	UNIQUE (category_name, category_super_id)
);

CREATE SEQUENCE good_sequence;

CREATE TABLE goods (
	good_id BIGINT PRIMARY KEY DEFAULT nextval('good_sequence'),
	good_title VARCHAR(100) NOT NULL,
	good_description TEXT NOT NULL, 
	good_category_id BIGINT NOT NULL REFERENCES categories (category_id),
	UNIQUE (good_title, good_category_id)
);

CREATE SEQUENCE offer_sequence;

CREATE TABLE offers (
	offer_id BIGINT PRIMARY KEY DEFAULT nextval('offer_sequence'),
	offer_description TEXT NOT NULL, 
	offer_buy_now REAL NOT NULL, 
	offer_start_price REAL NOT NULL,
	offer_status VARCHAR(20) NOT NULL,
	offer_user_id BIGINT NOT NULL REFERENCES users (user_id),
	offer_good_id BIGINT NOT NULL REFERENCES goods (good_id)
);

CREATE SEQUENCE bid_sequence;

CREATE TABLE bids (
	bid_id BIGINT PRIMARY KEY DEFAULT nextval('bid_sequence'),
	bid_date TIMESTAMP NOT NULL,
	bid_value REAL NOT NULL,
	bid_offer_id BIGINT NOT NULL REFERENCES offers (offer_id),
	bid_user_id BIGINT NOT NULL REFERENCES users (user_id)
);

CREATE SEQUENCE activity_sequence;

CREATE TABLE activities (
	activity_id BIGINT PRIMARY KEY DEFAULT nextval('activity_sequence'),
	activity_type VARCHAR(20) NOT NULL,
	activity_date TIMESTAMP NOT NULL,
	activity_old_status VARCHAR(20) NULL,
	activity_offer_id BIGINT NOT NULL REFERENCES offers (offer_id),
	activity_bid_id BIGINT NULL REFERENCES bids (bid_id)
);