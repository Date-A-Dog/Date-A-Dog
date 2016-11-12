\c dateadog;

INSERT INTO users (id, fname, lname, shelterid) VALUES (
  119889308491710,
  'Sally',
  'Smith',
  'WA214'
) ON CONFLICT DO NOTHING;

INSERT INTO users (id, fname, lname, shelterid) VALUES (
  10154041783618193,
  'Anuraag',
  'Pokhrel',
  'WA214'
) ON CONFLICT DO NOTHING;

INSERT INTO users (id, fname, lname, shelterid) VALUES (
  10155407387966038,
  'Amanda',
  'Loh',
  'WA214'
) ON CONFLICT DO NOTHING;

INSERT INTO users (id, fname, lname, shelterid) VALUES (
  10153811720940946,
  'Amarpal',
  'Singh',
  'WA214'
) ON CONFLICT DO NOTHING;
