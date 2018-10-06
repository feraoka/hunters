CREATE TABLE users (
  account  VARCHAR(16)         NOT NULL, -- account
  password VARCHAR(128) BINARY NOT NULL, -- password
  email    VARCHAR(64) BINARY  NOT NULL, -- email address
  PRIMARY KEY (account)
);

CREATE TABLE members (
  id        INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nick_name VARCHAR(50)  NOT NULL,
  status    INT UNSIGNED NOT NULL DEFAULT 0, -- 0: normal, 1: helper
  PRIMARY KEY (id)
);

CREATE TABLE events (
  id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
  date     DATETIME     NOT NULL,
  location VARCHAR(50)  NOT NULL,
  ground   VARCHAR(10)           DEFAULT NULL,
  type     VARCHAR(50)           DEFAULT NULL,
  opponent VARCHAR(50)           DEFAULT NULL,
  status   INT UNSIGNED NOT NULL DEFAULT 0, -- 0: planned, 1: closed, 2: done, 3: canceled
  note     TEXT,
  expense  BOOL                  DEFAULT 0,
  PRIMARY KEY (id),
  INDEX (date, status, type),
  INDEX (opponent, status)
);

CREATE TABLE attendees (
  event_id  INT UNSIGNED NOT NULL,
  member_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (event_id, member_id),
  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE games (
  event_id   INT UNSIGNED NOT NULL,
  bat_first  BOOL         NOT NULL,
  score_a    VARCHAR(255)          DEFAULT NULL,
  score_b    VARCHAR(255)          DEFAULT NULL,
  result     INT,
  point_got  INT,
  point_lost INT,
  PRIMARY KEY (event_id),
  FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE batters (
  id        INT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id  INT UNSIGNED NOT NULL, -- イベント ID
  member_id INT UNSIGNED NOT NULL, -- 選手 ID
  bat_order INT UNSIGNED NOT NULL, -- 打順
  daida     INT UNSIGNED NOT NULL DEFAULT 0, -- 代打
  PRIMARY KEY (id),
  INDEX (event_id, member_id),
  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE battings (
  id        INT UNSIGNED NOT NULL AUTO_INCREMENT,
  batter_id INT UNSIGNED NOT NULL,
  inning    INT UNSIGNED NOT NULL, -- イニング
  number    INT UNSIGNED NOT NULL, -- 打席番号
  result    INT UNSIGNED NOT NULL, -- 結果 定義はbattings_controller
  direction INT UNSIGNED, -- 打球方向
  rbi       INT                   DEFAULT 0, -- 打点
  point     INT                   DEFAULT 0, -- 得点
  steal     INT                   DEFAULT 0, -- 盗塁
  PRIMARY KEY (id),
  INDEX (batter_id),
  FOREIGN KEY (batter_id) REFERENCES batters (id)
);

