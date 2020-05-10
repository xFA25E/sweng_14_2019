-- -*- sql-product: sqlite; -*-

-- This is a TABLE with raw event data. Use a VIEW to retrieve nicely formatted
-- data.
CREATE TABLE IF NOT EXISTS raw_event (
  source_id INTEGER NOT NULL,
  event_id INTEGER NOT NULL,
  -- Integers are used for CAPs instead of strings because the nature of data is
  -- known. Strings would involve complicated regular expressions (like
  -- /[0-9]{5}/) to ensure the integrity of database, which are not supported by
  -- default in SQLite. Since Italian CAPs are all numeric it is much more
  -- easier to check for [0, 99999] interval and add zeroes right before
  -- selecting data. Also, it uses less memory, but it's not a priority.
  cap INTEGER NOT NULL CHECK(`cap` BETWEEN 0 AND 99999),
  message TEXT DEFAULT NULL CHECK(LENGTH(`message`) BETWEEN 1 AND 255),
  expected_at INTEGER NOT NULL CHECK(
    -- The insertion of DATETIME(..) works for this column (which inserts TEXT
    -- type), so this column should be checked for integer type. Maybe it's a
    -- SQLite bug.

    -- Unix Epoch is used as a time instant, because it is easier to validate
    -- data. See `event_id` comment for more explanations.
    TYPEOF(`expected_at`) == 'integer' AND 0 <= `expected_at`
  ),
  severity INTEGER NOT NULL CHECK(`severity` BETWEEN 0 AND 10),
  -- It is possible to adopt INTEGERs as a type here, by having a complicated
  -- conversion in a CASE statement or by having a separate TABLE with all the
  -- possible values. Which would also involve the usage of ASSERTs or TRIGGERs.
  -- Considering various tradeoffs, TEXT type is much easier to implement and
  -- maintain.
  status TEXT NOT NULL CHECK(
    `status` == 'expected'
    OR `status` == 'ongoing'
    OR `status` == 'occured'
    OR `status` == 'canceled'
  ),
  -- Event KINDs are not known in advance.
  kind TEXT NOT NULL CHECK(LENGTH(`kind`) <> 0),
  PRIMARY KEY (`source_id`, `event_id`)
);

-- This VIEW is used to retrieve data. It automatically inserts the necessary
-- number of zeroes at the beginning of a `cap` and converts Unix Epoch of
-- `expected_at` to a valid DATETIME string.
CREATE VIEW IF NOT EXISTS event AS
  SELECT source_id,
         event_id,
         printf('%05d', cap) AS cap,
         message,
         DATETIME(expected_at, 'unixepoch') AS expected_at,
         severity,
         status,
         kind
    FROM raw_event;
