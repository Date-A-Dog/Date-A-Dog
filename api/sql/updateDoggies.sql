\c dateadog;

UPDATE doggies
SET dog=dog || '{"name":"Bobby"}'
WHERE id = '22406048';

UPDATE doggies
SET dog=dog || '{"name":"Lio"}'
WHERE id = '32883666';

UPDATE doggies
SET dog=dog || '{"name":"Cujo"}'
WHERE id = '31945123';
