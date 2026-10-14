BEGIN;

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Aragnya''s Verity', 'Aragnya''s Verity', 'none', 'OFFHAND', NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "Off-hand", "tier": "n/a", "type": "", "damage": "0.0", "accuracy": "0", "image": "Aragnya''s Verity equipped.png"}'::jsonb, NULL, 0.0, NULL, NULL, NULL, NULL, NULL, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Aragnya''s Verity equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Aragnya''s Verity'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Bogolin sogtorch', 'Bogolin sogtorch', 'none', 'OFFHAND', NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "Off-hand", "tier": "n/a", "type": "", "degrades": "No", "damage": "0.0", "accuracy": "0", "style": "n/a", "attack range": "1", "speed": "4", "image": "Bogolin sogtorch equipped.png"}'::jsonb, NULL, 0.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Bogolin sogtorch equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 4.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Bogolin sogtorch'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Brooch of the Spirits', 'Brooch of the Spirits', 'none', 'POCKET', NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 4.0, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "pocket", "tier": "no", "prayer": "4"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Brooch of the Spirits'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deities'' Transmuter', 'Deities'' Transmuter', 'None', 'POCKET', NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "None", "slot": "Pocket", "tier": "No", "armour": "", "image": "No"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'No', NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deities'' Transmuter'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Earthborne staff', 'Earthborne staff', 'Magic', 'TWOHANDED', 65, 'SPELL', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|magic|65}}', NULL, '{"requirements": "{{sc|magic|65}}", "class": "Magic", "slot": "2h", "tier": "65", "damage": "0.0", "accuracy": "1299", "style": "spells", "attack range": "8", "speed": "6", "image": "Earthborne staff equipped.png"}'::jsonb, NULL, 1299.0, NULL, NULL, NULL, NULL, 8.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Earthborne staff equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|magic|65}}', 6.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Earthborne staff'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium adamant trophy', 'Equilibrium adamant trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium adamant trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium adamant trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium adamant trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium bronze trophy', 'Equilibrium bronze trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium bronze trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium bronze trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium bronze trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium dragon trophy', 'Equilibrium dragon trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium dragon trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium dragon trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium dragon trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium iron trophy', 'Equilibrium iron trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium iron trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium iron trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium iron trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium mithril trophy', 'Equilibrium mithril trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium mithril trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium mithril trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium mithril trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium rune trophy', 'Equilibrium rune trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium rune trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium rune trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium rune trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Equilibrium steel trophy', 'Equilibrium steel trophy', 'none', 'MAINHAND', 1, 'CRUSH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "main-hand", "tier": "1", "style": "Crushing", "accuracy": "110", "attack range": "1", "speed": "3", "damage": "0.0", "image": "Equilibrium steel trophy equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Equilibrium steel trophy equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 3.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Equilibrium steel trophy'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Fenshell knife', 'Fenshell knife', 'none', 'WEAPON', NULL, 'THROWN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "weapon", "tier": "n/a", "type": "", "degrades": "No", "damage": "0.0", "accuracy": "0", "style": "n/a", "attack range": "1", "speed": "4", "image": "Fenshell knife equipped.png"}'::jsonb, NULL, 0.0, NULL, NULL, NULL, NULL, 1.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Fenshell knife equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 4.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Fenshell knife'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Harpoon of the pious', 'Harpoon of the pious', 'ranged', 'WEAPON', 5, 'THROWN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "ranged", "slot": "weapon", "tier": "5", "damage": "61.3", "accuracy": "150", "style": "Thrown", "attack range": "5", "speed": "5", "image": "Harpoon of the pious equipped.png"}'::jsonb, NULL, 150.0, NULL, NULL, NULL, NULL, 5.0, 61.3, NULL, NULL, NULL, TRUE, NULL, 'Harpoon of the pious equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 5.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Harpoon of the pious'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Hatchet of Devotion', 'Hatchet of Devotion', 'melee', 'WEAPON', 5, 'SLASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "melee", "slot": "weapon", "tier": "5", "damage": "61.3", "accuracy": "150", "style": "Slash", "attack range": "1", "speed": "5", "image": "Hatchet of Devotion equipped.png"}'::jsonb, NULL, 150.0, NULL, NULL, NULL, NULL, 1.0, 61.3, NULL, NULL, NULL, TRUE, NULL, 'Hatchet of Devotion equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 5.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Hatchet of Devotion'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Havensilver halberd', 'Havensilver halberd', 'melee', 'TWOHANDED', 5, 'SLASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|1}}', NULL, '{"requirements": "{{sc|attack|1}}", "class": "melee", "slot": "2h", "tier": "5", "damage": "111.8", "accuracy": "150", "attack range": "2", "speed": "average", "style": "slash", "image": "Havensilver halberd equipped.png"}'::jsonb, ARRAY['HAVENSILVER'], 150.0, NULL, NULL, NULL, NULL, 2.0, 111.8, NULL, NULL, NULL, TRUE, NULL, 'Havensilver halberd equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|1}}', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Havensilver halberd'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Havensilver halberd +1', 'Havensilver halberd +1', 'melee', 'TWOHANDED', 30, 'SLASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|30}}', NULL, '{"requirements": "{{sc|attack|30}}", "class": "melee", "slot": "2h", "tier": "30", "damage": "670.5", "accuracy": "454", "attack range": "2", "speed": "average", "style": "slash", "image": "Havensilver halberd +1 equipped.png"}'::jsonb, ARRAY['HAVENSILVER'], 454.0, NULL, NULL, NULL, NULL, 2.0, 670.5, NULL, NULL, NULL, TRUE, NULL, 'Havensilver halberd +1 equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|30}}', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Havensilver halberd +1'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Havensilver halberd +2', 'Havensilver halberd +2', 'melee', 'TWOHANDED', 60, 'SLASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|60}}', NULL, '{"requirements": "{{sc|attack|60}}", "class": "melee", "slot": "2h", "tier": "60", "damage": "1341", "accuracy": "1132", "attack range": "2", "speed": "average", "style": "slash", "image": "Havensilver halberd +2 equipped.png"}'::jsonb, ARRAY['HAVENSILVER'], 1132.0, NULL, NULL, NULL, NULL, 2.0, 1341.0, NULL, NULL, NULL, TRUE, NULL, 'Havensilver halberd +2 equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|60}}', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Havensilver halberd +2'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Hawthorn''s hatchet', 'Hawthorn''s hatchet', 'melee', 'WEAPON', 1, 'SLASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|1}}', NULL, '{"requirements": "{{sc|attack|1}}", "class": "melee", "slot": "weapon", "tier": "1", "damage": "12", "accuracy": "110", "style": "Slashing", "speed": "5", "attack range": "1", "image": "Hawthorn''s hatchet equipped.png"}'::jsonb, NULL, 110.0, NULL, NULL, NULL, NULL, 1.0, 12.0, NULL, NULL, NULL, TRUE, NULL, 'Hawthorn''s hatchet equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|1}}', 5.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Hawthorn''s hatchet'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Malachi''s claw', 'Malachi''s claw', 'ranged', 'OFF_HAND_WEAPON', 95, 'BOLT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|Ranged|95}}', NULL, '{"version1": "Normal", "version2": "Innate Mastery", "damagetier1": "95", "damagetier2": "100", "accuracytier1": "95", "accuracytier2": "100", "accuracy1": "2765", "accuracy2": "3100", "requirements": "{{sc|Ranged|95}}", "class": "ranged", "slot": "off-hand weapon", "tier": "95", "damage": "0.0", "style": "bolt", "attack range": "7", "speed": "", "image": "Malachi''s claw equipped.png"}'::jsonb, ARRAY['SHARDABLE', 'MALACHI'], NULL, NULL, NULL, NULL, NULL, 7.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Malachi''s claw equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|Ranged|95}}', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Malachi''s claw'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Malachi''s tooth', 'Malachi''s tooth', 'Ranged', 'WEAPON', 95, 'BOLT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|Ranged|95}}', NULL, '{"version1": "Normal", "version2": "Innate Mastery", "damagetier1": "95", "damagetier2": "100", "accuracytier1": "95", "accuracytier2": "100", "accuracy1": "2765", "accuracy2": "3100", "requirements": "{{sc|Ranged|95}}", "class": "Ranged", "slot": "weapon", "tier": "95", "damage": "0.0", "style": "bolt", "attack range": "7", "speed": "", "image": "Malchi''s tooth equipped.png"}'::jsonb, ARRAY['SHARDABLE', 'MALACHI'], NULL, NULL, NULL, NULL, NULL, 7.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Malchi''s tooth equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|Ranged|95}}', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Malachi''s tooth'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Mudborne staff', 'Mudborne staff', 'Magic', 'TWOHANDED', 78, 'SPELL', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|magic|78}}', NULL, '{"requirements": "{{sc|magic|78}}", "class": "Magic", "slot": "2h", "tier": "78", "damage": "0", "accuracy": "1829", "style": "spells", "attack range": "8", "speed": "6", "image": "Mudborne staff equipped.png"}'::jsonb, NULL, 1829.0, NULL, NULL, NULL, NULL, 8.0, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Mudborne staff equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|magic|78}}', 6.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Mudborne staff'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nature''s rune pouch', 'Nature''s rune pouch', 'Magic', 'AMMO', NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "Magic", "slot": "Ammo", "tier": "No", "damage": "", "style": "None"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nature''s rune pouch'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nightblood ring', 'Nightblood ring', 'ranged', 'RING', NULL, 'NONE', NULL, NULL, NULL, NULL, 37.5, NULL, NULL, 2.0, 'None', NULL, '{"requirements": "None", "class": "ranged", "slot": "ring", "tier": "n/a", "armourtier": "n/a", "armourdamagetier": "100", "damage": "0", "accuracy": "0", "ranged": "37.5", "prayer": "2", "image": "no"}'::jsonb, NULL, 0.0, NULL, NULL, NULL, NULL, NULL, 0.0, NULL, NULL, NULL, TRUE, NULL, 'no', NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nightblood ring'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Pickaxe of the Faithful', 'Pickaxe of the Faithful', 'melee', 'WEAPON', 5, 'SLASH', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "melee", "slot": "weapon", "tier": "5", "damage": "61.3", "accuracy": "150", "style": "Slash", "attack range": "1", "speed": "4", "image": "Pickaxe of the Faithful equipped.png"}'::jsonb, NULL, 150.0, NULL, NULL, NULL, NULL, 1.0, 61.3, NULL, NULL, NULL, TRUE, NULL, 'Pickaxe of the Faithful equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', 4.0, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Pickaxe of the Faithful'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Pink hallowe''en mask', 'Pink hallowe''en mask', 'none', 'HEAD', 0, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "head", "tier": "0", "image": ""}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Pink hallowe''en mask'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Seren''s Crystal Tiara', 'Seren''s Crystal Tiara', 'None', 'HEAD', 0, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "None", "slot": "Head", "tier": "0", "image": "Seren''s Crystal Tiara equipped.png"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Seren''s Crystal Tiara equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Seren''s Crystal Tiara'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Stonevein amulet', 'Stonevein amulet', 'none', 'NECK', NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "neck", "tier": "n/a", "type": "", "damage": "0.0", "accuracy": "0", "image": "Stonevein amulet (equipped).png"}'::jsonb, NULL, 0.0, NULL, NULL, NULL, NULL, NULL, 0.0, NULL, NULL, NULL, TRUE, NULL, 'Stonevein amulet (equipped).png', NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Stonevein amulet'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Uri''s briefcase', 'Uri''s briefcase', 'None', 'POCKET', 0, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, '{"requirements": "None", "class": "None", "slot": "Pocket", "tier": "0", "image": ""}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
    WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Uri''s briefcase'))
);

COMMIT;
