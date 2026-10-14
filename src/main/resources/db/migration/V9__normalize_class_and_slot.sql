UPDATE equipment SET class='RANGED' WHERE LOWER(class)='ranged';
UPDATE equipment SET class='MELEE' WHERE LOWER(class)='melee';
UPDATE equipment SET class='MAGIC' WHERE LOWER(class)='magic';
UPDATE equipment SET class='NECROMANCY' WHERE LOWER(class)='necromancy';
UPDATE equipment SET class='ALL' WHERE LOWER(class)='none';

UPDATE equipment SET slot='MAINHAND' WHERE LOWER(slot)='weapon';
UPDATE equipment SET slot='OFFHAND' WHERE LOWER(slot)='off_hand_weapon';
