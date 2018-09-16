CREATE TABLE IF NOT EXISTS users (
  account  VARCHAR(16)         NOT NULL, -- account
  password VARCHAR(128) BINARY NOT NULL, -- password
  email    VARCHAR(64) BINARY  NOT NULL, -- email address
  PRIMARY KEY (account)
);

CREATE TABLE IF NOT EXISTS members (
  id        INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nick_name VARCHAR(50)  NOT NULL,
  status    INT UNSIGNED NOT NULL DEFAULT 0, -- 0: normal, 1: helper
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
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
  INDEX (date, status, type)
);

CREATE TABLE IF NOT EXISTS attendees (
  event_id  INT UNSIGNED NOT NULL,
  member_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (event_id, member_id),
  FOREIGN KEY (event_id) REFERENCES events (id),
  FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE IF NOT EXISTS games (
  event_id   INT UNSIGNED NOT NULL,
  result     INT,
  bat_first  BOOL         NOT NULL,
  score_a    VARCHAR(255)          DEFAULT NULL,
  score_b    VARCHAR(255)          DEFAULT NULL,
  point_got  INT,
  point_lost INT,
  PRIMARY KEY (event_id),
  FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS batters (
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

CREATE TABLE IF NOT EXISTS battings (
  id        INT UNSIGNED NOT NULL AUTO_INCREMENT,
  batter_id INT UNSIGNED NOT NULL,
  inning    INT UNSIGNED NOT NULL, -- イニング
  number    INT UNSIGNED NOT NULL, -- 打席番号
  result    INT UNSIGNED NOT NULL, -- 結果 定義はbattings_controller
  dasu      INT UNSIGNED NOT NULL, -- 打数 0:四死球、犠打など 1:打数としてカウント
  dakyu     INT UNSIGNED, -- 打球 0:ゴロ 1:フライ 2:ライナー 3:オーバー
  direction INT UNSIGNED, -- 打球方向
  rbi       INT                   DEFAULT 0, -- 打点
  point     INT                   DEFAULT 0, -- 得点
  steal     INT                   DEFAULT 0, -- 盗塁
  hits      INT                   DEFAULT 0, -- 塁打
  hit1      INT                   DEFAULT 0, -- 単打
  hit2      INT                   DEFAULT 0, -- 二塁打
  hit3      INT                   DEFAULT 0, -- 三塁打
  homerun   INT                   DEFAULT 0, -- 本塁打
  sout      INT                   DEFAULT 0, -- 三振
  fball     INT                   DEFAULT 0, -- 四球
  dball     INT                   DEFAULT 0, -- 死球
  note      TEXT                  DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX (batter_id),
  FOREIGN KEY (batter_id) REFERENCES batters (id)
);

