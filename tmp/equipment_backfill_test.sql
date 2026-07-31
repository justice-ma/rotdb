BEGIN;

UPDATE equipment
SET
    name = 'Cryptbloom boots',
    class = 'magic',
    slot = 'BOOTS',
    tier = 90,
    type = 'NONE',
    requirements_raw = '{{sc|defence|90}}',
    raw_infobox = '{"requirements": "{{sc|defence|90}}", "class": "magic", "slot": "feet", "tier": "90", "type": "tank", "degrades": "100000", "repair cost": "-1", "armour": "122.9", "life": "450", "attack range": "1", "image": "Cryptbloom boots equipped (male).png", "altimage": "Cryptbloom boots equipped (female).png"}'::jsonb,
    armour = 122.9,
    attack_range = 1.0,
    equippable = TRUE,
    images = 'Cryptbloom boots equipped (male).png',
    life = 450.0,
    requirements = '{{sc|defence|90}}'
WHERE lower(trim(title)) = lower(trim('Cryptbloom boots'));

COMMIT;
