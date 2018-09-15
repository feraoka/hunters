ALTER TABLE attendees CHANGE COLUMN id id INT UNSIGNED NOT NULL;
ALTER TABLE attendees DROP PRIMARY KEY;
ALTER TABLE attendees ADD PRIMARY KEY (member_id, event_id);

SELECT status, COUNT(status) FROM attendees GROUP BY status;
ALTER TABLE attendees DROP status;
