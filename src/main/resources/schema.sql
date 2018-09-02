CREATE TABLE IF NOT EXISTS users (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  account VARCHAR(50) NOT NULL,       -- account
  password VARCHAR(50) BINARY NOT NULL, -- password
  email VARCHAR(50) BINARY NOT NULL,  -- email address
  name VARCHAR(50) NOT NULL,          -- name
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id),
  UNIQUE KEY account (account)
);

CREATE TABLE IF NOT EXISTS member_statuses (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  status VARCHAR(50),
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS members (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL DEFAULT 0,
  nick_name VARCHAR(50),
  is_admin BOOL DEFAULT 0,
  member_status_id INT UNSIGNED NOT NULL DEFAULT 1,
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id, user_id)
);

CREATE TABLE IF NOT EXISTS registers (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  email VARCHAR(50) BINARY NOT NULL,  -- email address
  name VARCHAR(50) BINARY NOT NULL,
  member_id INT UNSIGNED NOT NULL,
  user_id INT UNSIGNED NOT NULL DEFAULT 0, -- who issued
  passcode VARCHAR(50) NOT NULL,
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id, passcode)
);

CREATE TABLE IF NOT EXISTS events (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  date DATETIME NOT NULL,
  location VARCHAR(50) NOT NULL,
  ground VARCHAR(10) DEFAULT NULL,
  type VARCHAR(50) DEFAULT NULL,
  opponent VARCHAR(50) DEFAULT NULL,
  status INT UNSIGNED NOT NULL DEFAULT 0, -- 0: planned, 1: closed, 2: done, 3: canceled
  note TEXT,
  expense BOOL DEFAULT 0,
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS attendees (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id INT UNSIGNED NOT NULL,
  member_id INT UNSIGNED NOT NULL,
  status INT UNSIGNED NOT NULL DEFAULT 0, -- 1: attend, 2: can not attend, 3: may be attend, 4: canceled
  PRIMARY KEY(id, event_id)
);

CREATE TABLE IF NOT EXISTS games (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id INT UNSIGNED NOT NULL,
  result INT,
  batFirst BOOL NOT NULL,
  scoreA VARCHAR(255) DEFAULT NULL,
  scoreB VARCHAR(255) DEFAULT NULL,
  pointGot INT,
  pointLost INT,
  PRIMARY KEY(id, event_id)
);

CREATE TABLE IF NOT EXISTS scoreboards (
--  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  game_id INT UNSIGNED NOT NULL,
  inning INT UNSIGNED NOT NULL,
  scoreA INT UNSIGNED, -- omote
  scoreB INT UNSIGNED, -- ura
  PRIMARY KEY(game_id)
);

CREATE TABLE IF NOT EXISTS batters (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id INT UNSIGNED NOT NULL,          -- イベント ID
  member_id INT UNSIGNED NOT NULL,         -- 選手 ID
  bOrder INT UNSIGNED NOT NULL,            -- 打順
  daida INT UNSIGNED NOT NULL DEFAULT 0,   -- 代打
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS battings (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  batter_id INT UNSIGNED NOT NULL,
  inning INT UNSIGNED NOT NULL,     -- イニング
  number INT UNSIGNED NOT NULL,     -- 打席番号
  result INT UNSIGNED NOT NULL,     -- 結果 定義はbattings_controller
  dasu INT UNSIGNED NOT NULL,       -- 打数 0:四死球、犠打など 1:打数としてカウント
  dakyu INT UNSIGNED,               -- 打球 0:ゴロ 1:フライ 2:ライナー 3:オーバー
  direction INT UNSIGNED,           -- 打球方向
  rbi INT DEFAULT 0,                -- 打点
  point INT DEFAULT 0,              -- 得点
  steal INT DEFAULT 0,              -- 盗塁
  hits INT DEFAULT 0,               -- 塁打
  hit1 INT DEFAULT 0,               -- 単打
  hit2 INT DEFAULT 0,               -- 二塁打
  hit3 INT DEFAULT 0,               -- 三塁打
  homerun INT DEFAULT 0,            -- 本塁打
  sout INT DEFAULT 0,               -- 三振
  fball INT DEFAULT 0,              -- 四球
  dball INT DEFAULT 0,              -- 死球
  note TEXT DEFAULT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS helpers (
  event_id INT UNSIGNED NOT NULL,
  name VARCHAR(50) BINARY NOT NULL
);

CREATE TABLE IF NOT EXISTS infos (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(50) BINARY NOT NULL,
  comment TEXT NOT NULL,
  target_date DATETIME NOT NULL,   -- 有効期限  その後表示されなくなります。
  user_id INT UNSIGNED NOT NULL, -- 作成者
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id)
);

-- Editable pages
CREATE TABLE IF NOT EXISTS pages (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(50) BINARY NOT NULL,
  page TEXT NOT NULL,
  user_id INT UNSIGNED NOT NULL, -- 最後に編集した人
  page_id INT UNSIGNED NOT NULL DEFAULT 0, -- 編集元のページ
  created DATETIME DEFAULT NULL,
  modified DATETIME DEFAULT NULL,
  PRIMARY KEY(id, title)
);

