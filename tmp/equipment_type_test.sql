BEGIN;

UPDATE equipment
SET
    type = 'tank'
WHERE lower(trim(title)) = lower(trim('Cryptbloom boots'));

COMMIT;
