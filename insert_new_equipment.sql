BEGIN;

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Argonite guard', 'Argonite guard', 'necromancy', 'WEAPON', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}", "class": "necromancy", "slot": "weapon", "tier": "60", "damage": "894.0", "accuracy": "1132", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 1132.0, NULL, NULL, NULL, NULL, NULL, 894.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Argonite guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Argonite lantern', 'Argonite lantern', 'necromancy', 'OFF_HAND_WEAPON', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "60", "damage": "447.0", "accuracy": "1132", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 1132.0, NULL, NULL, NULL, NULL, NULL, 447.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Argonite lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Bathus guard', 'Bathus guard', 'necromancy', 'WEAPON', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}", "class": "necromancy", "slot": "weapon", "tier": "10", "damage": "149.0", "accuracy": "202", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 202.0, NULL, NULL, NULL, NULL, NULL, 149.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Bathus guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Bathus lantern', 'Bathus lantern', 'necromancy', 'OFF_HAND_WEAPON', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "10", "damage": "74.5", "accuracy": "202", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 202.0, NULL, NULL, NULL, NULL, NULL, 74.5, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Bathus lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Chaotic grimoire', 'Chaotic grimoire', 'none', 'POCKET', NULL, NULL, 'NONE', NULL, NULL, 10.9, 10.9, 10.9, 10.9, NULL, 'None', NULL, '{"requirements": "None", "class": "none", "slot": "pocket", "tier": "n/a", "strength": "10.9", "ranged": "10.9", "magic": "10.9", "necromancy": "10.9"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'None', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Chaotic grimoire'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Chaotic guard', 'Chaotic guard', 'necromancy', 'WEAPON', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}} {{sc|dungeoneering|70}}', NULL, '{"smwname": "new", "requirements": "{{sc|necromancy|80}} {{sc|dungeoneering|70}}", "class": "necromancy", "slot": "weapon", "tier": "80", "degrades": "Yes", "repair_cost": "2000000", "damage": "1192.0", "accuracy": "1924", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 1924.0, NULL, NULL, NULL, NULL, NULL, 1192.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}} {{sc|dungeoneering|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Chaotic guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Chaotic lantern', 'Chaotic lantern', 'necromancy', 'OFF_HAND_WEAPON', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}} {{sc|dungeoneering|70}}', NULL, '{"smwname": "new", "requirements": "{{sc|necromancy|80}} {{sc|dungeoneering|70}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "80", "degrades": "Yes", "repair_cost": "1000000", "damage": "596.0", "accuracy": "1924", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 1924.0, NULL, NULL, NULL, NULL, NULL, 596.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}} {{sc|dungeoneering|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Chaotic lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbell gloves', 'Deathbell gloves', 'necromancy', 'GLOVES', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}, {{sc|defence|60}}", "class": "necromancy", "slot": "hands", "tier": "60", "type": "tank", "armour": "56.6", "life": "300"}'::jsonb, NULL, NULL, NULL, 56.6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 300.0, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbell gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbell hood', 'Deathbell hood', 'necromancy', 'HEAD', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}, {{sc|defence|60}}", "class": "necromancy", "slot": "head", "tier": "60", "type": "tank", "armour": "226.4", "life": "600"}'::jsonb, NULL, NULL, NULL, 226.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 600.0, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbell hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbell robe bottom', 'Deathbell robe bottom', 'necromancy', 'LEGS', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}, {{sc|defence|60}}", "class": "necromancy", "slot": "legs", "tier": "60", "type": "tank", "armour": "249.0", "life": "900"}'::jsonb, NULL, NULL, NULL, 249.0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 900.0, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbell robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbell robe top', 'Deathbell robe top', 'necromancy', 'BODY', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}, {{sc|defence|60}}", "class": "necromancy", "slot": "body", "tier": "60", "type": "tank", "armour": "260.3", "life": "900"}'::jsonb, NULL, NULL, NULL, 260.3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 900.0, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbell robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbell shoes', 'Deathbell shoes', 'necromancy', 'BOOTS', 60, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, '{"requirements": "{{sc|necromancy|60}}, {{sc|defence|60}}", "class": "necromancy", "slot": "feet", "tier": "60", "type": "tank", "armour": "56.6", "life": "300"}'::jsonb, NULL, NULL, NULL, 56.6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 300.0, NULL, NULL, NULL, '{{sc|necromancy|60}}, {{sc|defence|60}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbell shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbloom gloves', 'Deathbloom gloves', 'necromancy', 'GLOVES', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, '{"image": "Deathbloom gloves equipped.png", "requirements": "{{sc|necromancy|90}}, {{sc|defence|90}}", "class": "necromancy", "slot": "hands", "tier": "90", "type": "tank", "armour": "122.9", "life": "450"}'::jsonb, NULL, NULL, NULL, 122.9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathbloom gloves equipped.png', NULL, NULL, 450.0, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbloom gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbloom hood', 'Deathbloom hood', 'necromancy', 'HEAD', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, '{"requirements": "{{sc|necromancy|90}}, {{sc|defence|90}}", "class": "necromancy", "slot": "head", "tier": "90", "type": "tank", "armour": "491.6", "life": "900"}'::jsonb, NULL, NULL, NULL, 491.6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 900.0, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbloom hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbloom robe bottom', 'Deathbloom robe bottom', 'necromancy', 'LEGS', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, '{"requirements": "{{sc|necromancy|90}}, {{sc|defence|90}}", "class": "necromancy", "slot": "legs", "tier": "90", "type": "tank", "armour": "540.7", "life": "1350"}'::jsonb, NULL, NULL, NULL, 540.7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 1350.0, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbloom robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbloom robe top', 'Deathbloom robe top', 'necromancy', 'BODY', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, '{"image": "Deathbloom robe top equipped.png", "requirements": "{{sc|necromancy|90}}, {{sc|defence|90}}", "class": "necromancy", "slot": "body", "tier": "90", "type": "tank", "armour": "565.3", "life": "1350"}'::jsonb, NULL, NULL, NULL, 565.3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathbloom robe top equipped.png', NULL, NULL, 1350.0, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbloom robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathbloom shoes', 'Deathbloom shoes', 'necromancy', 'BOOTS', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, '{"requirements": "{{sc|necromancy|90}}, {{sc|defence|90}}", "class": "necromancy", "slot": "feet", "tier": "90", "type": "tank", "armour": "122.9", "life": "450"}'::jsonb, NULL, NULL, NULL, 122.9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 450.0, NULL, NULL, NULL, '{{sc|necromancy|90}}, {{sc|defence|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathbloom shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathcress gloves', 'Deathcress gloves', 'necromancy', 'GLOVES', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}, {{sc|defence|10}}", "class": "necromancy", "slot": "hands", "tier": "10", "type": "tank", "armour": "10.1", "life": "50", "image": "Deathcress gloves equipped.png"}'::jsonb, NULL, NULL, NULL, 10.1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathcress gloves equipped.png', NULL, NULL, 50.0, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathcress gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathcress hood', 'Deathcress hood', 'necromancy', 'HEAD', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}, {{sc|defence|10}}", "class": "necromancy", "slot": "head", "tier": "10", "type": "tank", "armour": "40.4", "life": "100", "image": "Deathcress hood equipped.png"}'::jsonb, NULL, NULL, NULL, 40.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathcress hood equipped.png', NULL, NULL, 100.0, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathcress hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathcress robe bottom', 'Deathcress robe bottom', 'necromancy', 'LEGS', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}, {{sc|defence|10}}", "class": "necromancy", "slot": "legs", "tier": "10", "type": "tank", "armour": "44.4", "life": "150", "image": "Deathcress armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 44.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathcress armour equipped (male).png', NULL, NULL, 150.0, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathcress robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathcress robe top', 'Deathcress robe top', 'necromancy', 'BODY', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}, {{sc|defence|10}}", "class": "necromancy", "slot": "body", "tier": "10", "type": "tank", "armour": "46.4", "life": "150", "image": "Deathcress armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 46.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathcress armour equipped (male).png', NULL, NULL, 150.0, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathcress robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathcress shoes', 'Deathcress shoes', 'necromancy', 'BOOTS', 10, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, '{"requirements": "{{sc|necromancy|10}}, {{sc|defence|10}}", "class": "necromancy", "slot": "feet", "tier": "10", "type": "tank", "armour": "10.1", "life": "50", "image": "Deathcress armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 10.1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathcress armour equipped (male).png', NULL, NULL, 50.0, NULL, NULL, NULL, '{{sc|necromancy|10}}, {{sc|defence|10}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathcress shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathleaf gloves', 'Deathleaf gloves', 'necromancy', 'GLOVES', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}, {{sc|defence|20}}", "class": "necromancy", "slot": "hands", "tier": "20", "type": "tank", "armour": "15.8", "life": "100", "image": "Deathleaf armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 15.8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathleaf armour equipped (male).png', NULL, NULL, 100.0, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathleaf gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathleaf hood', 'Deathleaf hood', 'necromancy', 'HEAD', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}, {{sc|defence|20}}", "class": "necromancy", "slot": "head", "tier": "20", "type": "tank", "armour": "63.2", "life": "200", "image": "Deathleaf armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 63.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathleaf armour equipped (male).png', NULL, NULL, 200.0, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathleaf hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathleaf robe bottom', 'Deathleaf robe bottom', 'necromancy', 'LEGS', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}, {{sc|defence|20}}", "class": "necromancy", "slot": "legs", "tier": "20", "type": "tank", "armour": "69.5", "life": "300", "image": "Deathleaf armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 69.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathleaf armour equipped (male).png', NULL, NULL, 300.0, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathleaf robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathleaf robe top', 'Deathleaf robe top', 'necromancy', 'BODY', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}, {{sc|defence|20}}", "class": "necromancy", "slot": "body", "tier": "20", "type": "tank", "armour": "72.6", "life": "300", "image": "Deathleaf armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 72.6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathleaf armour equipped (male).png', NULL, NULL, 300.0, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathleaf robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathleaf shoes', 'Deathleaf shoes', 'necromancy', 'BOOTS', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}, {{sc|defence|20}}", "class": "necromancy", "slot": "feet", "tier": "20", "type": "tank", "armour": "15.8", "life": "100", "image": "Deathleaf armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 15.8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathleaf armour equipped (male).png', NULL, NULL, 100.0, NULL, NULL, NULL, '{{sc|necromancy|20}}, {{sc|defence|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathleaf shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathsalve gloves', 'Deathsalve gloves', 'necromancy', 'GLOVES', 1, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}, {{sc|defence|1}}", "class": "necromancy", "slot": "hands", "tier": "1", "type": "tank", "armour": "5.5", "life": "5", "image": "Deathsalve gloves equipped.png"}'::jsonb, NULL, NULL, NULL, 5.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathsalve gloves equipped.png', NULL, NULL, 5.0, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathsalve gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathsalve hood', 'Deathsalve hood', 'necromancy', 'HEAD', 1, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}, {{sc|defence|1}}", "class": "necromancy", "slot": "head", "tier": "1", "type": "tank", "armour": "22.0", "life": "10", "image": "Deathsalve hood equipped.png"}'::jsonb, NULL, NULL, NULL, 22.0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathsalve hood equipped.png', NULL, NULL, 10.0, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathsalve hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathsalve robe bottom', 'Deathsalve robe bottom', 'necromancy', 'LEGS', 1, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}, {{sc|defence|1}}", "class": "necromancy", "slot": "legs", "tier": "1", "type": "tank", "armour": "24.2", "life": "15", "image": "Deathsalve robe bottom equipped.png"}'::jsonb, NULL, NULL, NULL, 24.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathsalve robe bottom equipped.png', NULL, NULL, 15.0, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathsalve robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathsalve robe top', 'Deathsalve robe top', 'necromancy', 'BODY', 1, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}, {{sc|defence|1}}", "class": "necromancy", "slot": "body", "tier": "1", "type": "tank", "armour": "25.3", "life": "15", "image": "Deathsalve robe top equipped.png"}'::jsonb, NULL, NULL, NULL, 25.3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathsalve robe top equipped.png', NULL, NULL, 15.0, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathsalve robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathsalve shoes', 'Deathsalve shoes', 'necromancy', 'BOOTS', 1, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}, {{sc|defence|1}}", "class": "necromancy", "slot": "feet", "tier": "1", "type": "tank", "armour": "5.5", "life": "5", "image": "Deathsalve shoes equipped.png"}'::jsonb, NULL, NULL, NULL, 5.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Deathsalve shoes equipped.png', NULL, NULL, 5.0, NULL, NULL, NULL, '{{sc|necromancy|1}}, {{sc|defence|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathsalve shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathweed gloves', 'Deathweed gloves', 'necromancy', 'GLOVES', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}, {{sc|defence|50}}", "class": "necromancy", "slot": "hands", "tier": "50", "type": "tank", "armour": "42.5", "life": "250"}'::jsonb, NULL, NULL, NULL, 42.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 250.0, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathweed gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathweed hood', 'Deathweed hood', 'necromancy', 'HEAD', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}, {{sc|defence|50}}", "class": "necromancy", "slot": "head", "tier": "50", "type": "tank", "armour": "170.0", "life": "500"}'::jsonb, NULL, NULL, NULL, 170.0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 500.0, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathweed hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathweed robe bottom', 'Deathweed robe bottom', 'necromancy', 'LEGS', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}, {{sc|defence|50}}", "class": "necromancy", "slot": "legs", "tier": "50", "type": "tank", "armour": "187.0", "life": "750"}'::jsonb, NULL, NULL, NULL, 187.0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 750.0, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathweed robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathweed robe top', 'Deathweed robe top', 'necromancy', 'BODY', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}, {{sc|defence|50}}", "class": "necromancy", "slot": "body", "tier": "50", "type": "tank", "armour": "195.5", "life": "750"}'::jsonb, NULL, NULL, NULL, 195.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 750.0, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathweed robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Deathweed shoes', 'Deathweed shoes', 'necromancy', 'BOOTS', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}, {{sc|defence|50}}", "class": "necromancy", "slot": "feet", "tier": "50", "type": "tank", "armour": "42.5", "life": "250"}'::jsonb, NULL, NULL, NULL, 42.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 250.0, NULL, NULL, NULL, '{{sc|necromancy|50}}, {{sc|defence|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Deathweed shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic gloves', 'Entropic gloves', 'necromancy', 'GLOVES', 99, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, '{"image": "Entropic gloves equipped.png", "requirements": "{{sc|necromancy|99}}, {{sc|defence|99}}", "class": "necromancy", "slot": "hands", "tier": "99", "type": "tank", "armour": "151.5", "life": "495"}'::jsonb, NULL, NULL, NULL, 151.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Entropic gloves equipped.png', NULL, NULL, 495.0, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic guard', 'Entropic guard', 'necromancy', 'WEAPON', 99, NULL, 'NONE', 100, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}', NULL, '{"image": "Entropic guard equipped.png", "requirements": "{{sc|necromancy|99}}", "class": "necromancy", "slot": "weapon", "tier": "99", "damagetier": "100", "accuracytier": "100", "damage": "1490.0", "accuracy": "3100", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 1490.0, NULL, 100, NULL, TRUE, NULL, 'Entropic guard equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic hood', 'Entropic hood', 'necromancy', 'HEAD', 99, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, '{"image": "Entropic hood equipped.png", "requirements": "{{sc|necromancy|99}}, {{sc|defence|99}}", "class": "necromancy", "slot": "head", "tier": "99", "type": "tank", "armour": "606.2", "life": "990"}'::jsonb, NULL, NULL, NULL, 606.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Entropic hood equipped.png', NULL, NULL, 990.0, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic lantern', 'Entropic lantern', 'necromancy', 'OFF_HAND_WEAPON', 99, NULL, 'NONE', 100, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}', NULL, '{"image": "Entropic lantern equipped.png", "requirements": "{{sc|necromancy|99}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "99", "damagetier": "100", "accuracytier": "100", "damage": "745.0", "accuracy": "3100", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 745.0, NULL, 100, NULL, TRUE, NULL, 'Entropic lantern equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic robe bottom', 'Entropic robe bottom', 'necromancy', 'LEGS', 99, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, '{"image": "Entropic robe bottom equipped.png", "requirements": "{{sc|necromancy|99}}, {{sc|defence|99}}", "class": "necromancy", "slot": "legs", "tier": "99", "type": "tank", "armour": "666.8", "life": "1485"}'::jsonb, NULL, NULL, NULL, 666.8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Entropic robe bottom equipped.png', NULL, NULL, 1485.0, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic robe top', 'Entropic robe top', 'necromancy', 'BODY', 99, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, '{"image": "Entropic robe top equipped.png", "requirements": "{{sc|necromancy|99}}, {{sc|defence|99}}", "class": "necromancy", "slot": "body", "tier": "99", "type": "tank", "armour": "697.1", "life": "1485"}'::jsonb, NULL, NULL, NULL, 697.1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Entropic robe top equipped.png', NULL, NULL, 1485.0, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Entropic shoes', 'Entropic shoes', 'necromancy', 'BOOTS', 99, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, '{"image": "Entropic shoes equipped.png", "requirements": "{{sc|necromancy|99}}, {{sc|defence|99}}", "class": "necromancy", "slot": "feet", "tier": "99", "type": "tank", "armour": "151.5", "life": "495"}'::jsonb, NULL, NULL, NULL, 151.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Entropic shoes equipped.png', NULL, NULL, 495.0, NULL, NULL, NULL, '{{sc|necromancy|99}}, {{sc|defence|99}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Entropic shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Fractite lantern', 'Fractite lantern', 'necromancy', 'OFF_HAND_WEAPON', 40, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}', NULL, '{"requirements": "{{sc|necromancy|40}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "40", "damage": "298.0", "accuracy": "628", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 628.0, NULL, NULL, NULL, NULL, NULL, 298.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Fractite lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Gorgonite guard', 'Gorgonite guard', 'necromancy', 'WEAPON', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}", "class": "necromancy", "slot": "weapon", "tier": "80", "damage": "1192.0", "accuracy": "1924", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 1924.0, NULL, NULL, NULL, NULL, NULL, 1192.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Gorgonite guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Gorgonite lantern', 'Gorgonite lantern', 'necromancy', 'OFF_HAND_WEAPON', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "80", "damage": "596.0", "accuracy": "1924", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 1924.0, NULL, NULL, NULL, NULL, NULL, 596.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Gorgonite lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Gravite guard', 'Gravite guard', 'necromancy', 'WEAPON', 55, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|55}} {{sc|dungeoneering|55}}', NULL, '{"smwname": "new", "requirements": "{{sc|necromancy|55}} {{sc|dungeoneering|55}}", "class": "necromancy", "slot": "weapon", "tier": "55", "degrades": "Yes", "repair_cost": "1000000", "damage": "819.5", "accuracy": "983", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 983.0, NULL, NULL, NULL, NULL, NULL, 819.5, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|55}} {{sc|dungeoneering|55}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Gravite guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Gravite lantern', 'Gravite lantern', 'necromancy', 'OFF_HAND_WEAPON', 55, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|55}} {{sc|dungeoneering|55}}', NULL, '{"smwname": "new", "requirements": "{{sc|necromancy|55}} {{sc|dungeoneering|55}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "55", "degrades": "Yes", "repair_cost": "500000", "damage": "409.7", "accuracy": "983", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 983.0, NULL, NULL, NULL, NULL, NULL, 409.7, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|55}} {{sc|dungeoneering|55}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Gravite lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Katagon guard', 'Katagon guard', 'necromancy', 'WEAPON', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}", "class": "necromancy", "slot": "weapon", "tier": "70", "damage": "1043.0", "accuracy": "1486", "attack_range": "6", "speed": "average", "image": "Katagon guard equipped.png"}'::jsonb, NULL, 1486.0, NULL, NULL, NULL, NULL, NULL, 1043.0, NULL, NULL, NULL, TRUE, NULL, 'Katagon guard equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Katagon guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Katagon lantern', 'Katagon lantern', 'necromancy', 'OFF_HAND_WEAPON', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "70", "damage": "521.5", "accuracy": "1486", "attack_range": "6", "speed": "average", "image": "Katagon lantern equipped.png"}'::jsonb, NULL, 1486.0, NULL, NULL, NULL, NULL, NULL, 521.5, NULL, NULL, NULL, TRUE, NULL, 'Katagon lantern equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Katagon lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Kratonite guard', 'Kratonite guard', 'necromancy', 'WEAPON', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}", "class": "necromancy", "slot": "weapon", "tier": "30", "damage": "447.0", "accuracy": "454", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 454.0, NULL, NULL, NULL, NULL, NULL, 447.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Kratonite guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Kratonite lantern', 'Kratonite lantern', 'necromancy', 'OFF_HAND_WEAPON', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "30", "damage": "223.5", "accuracy": "454", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 454.0, NULL, NULL, NULL, NULL, NULL, 223.5, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Kratonite lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Marmaros guard', 'Marmaros guard', 'necromancy', 'WEAPON', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}", "class": "necromancy", "slot": "weapon", "tier": "20", "damage": "298.0", "accuracy": "316", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 316.0, NULL, NULL, NULL, NULL, NULL, 298.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Marmaros guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Marmaros lantern', 'Marmaros lantern', 'necromancy', 'OFF_HAND_WEAPON', 20, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}', NULL, '{"requirements": "{{sc|necromancy|20}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "20", "damage": "149.0", "accuracy": "316", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 316.0, NULL, NULL, NULL, NULL, NULL, 149.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|20}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Marmaros lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nectrogloves', 'Nectrogloves', 'necromancy', 'GLOVES', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}, {{sc|defence|70}}", "class": "necromancy", "slot": "hands", "tier": "70", "type": "tank", "armour": "74.3", "life": "350", "image": "Nectrogloves equipped.png"}'::jsonb, NULL, NULL, NULL, 74.3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Nectrogloves equipped.png', NULL, NULL, 350.0, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nectrogloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nectrohood', 'Nectrohood', 'necromancy', 'HEAD', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}, {{sc|defence|70}}", "class": "necromancy", "slot": "head", "tier": "70", "type": "tank", "armour": "297.2", "life": "700", "image": "Nectrohood equipped.png"}'::jsonb, NULL, NULL, NULL, 297.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Nectrohood equipped.png', NULL, NULL, 700.0, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nectrohood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nectrorobe bottom', 'Nectrorobe bottom', 'necromancy', 'LEGS', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}, {{sc|defence|70}}", "class": "necromancy", "slot": "legs", "tier": "70", "type": "tank", "armour": "326.9", "life": "1050", "image": "Nectrorobe bottom equipped.png"}'::jsonb, NULL, NULL, NULL, 326.9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Nectrorobe bottom equipped.png', NULL, NULL, 1050.0, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nectrorobe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nectrorobe top', 'Nectrorobe top', 'necromancy', 'BODY', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}, {{sc|defence|70}}", "class": "necromancy", "slot": "body", "tier": "70", "type": "tank", "armour": "341.7", "life": "1050", "image": "Nectrorobe top equipped.png"}'::jsonb, NULL, NULL, NULL, 341.7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Nectrorobe top equipped.png', NULL, NULL, 1050.0, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nectrorobe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Nectroshoes', 'Nectroshoes', 'necromancy', 'BOOTS', 70, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}, {{sc|defence|70}}", "class": "necromancy", "slot": "feet", "tier": "70", "type": "tank", "armour": "74.3", "life": "350", "image": "Nectroshoes equipped.png"}'::jsonb, NULL, NULL, NULL, 74.3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Nectroshoes equipped.png', NULL, NULL, 350.0, NULL, NULL, NULL, '{{sc|necromancy|70}}, {{sc|defence|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Nectroshoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Novite guard', 'Novite guard', 'necromancy', 'WEAPON', 5, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}", "class": "necromancy", "slot": "weapon", "tier": "5", "damage": "74.5", "accuracy": "150", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 150.0, NULL, NULL, NULL, NULL, NULL, 74.5, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Novite guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Novite lantern', 'Novite lantern', 'necromancy', 'OFF_HAND_WEAPON', 5, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}', NULL, '{"requirements": "{{sc|necromancy|1}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "5", "damage": "37.2", "accuracy": "150", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 150.0, NULL, NULL, NULL, NULL, NULL, 37.2, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|1}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Novite lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Occultist''s blood necklace', 'Occultist''s blood necklace', 'none', 'NECK', NULL, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, 46.0, NULL, '{{sc|necromancy|80}}', NULL, '{"bucketname": "new", "requirements": "{{sc|necromancy|80}}", "class": "none", "slot": "neck", "tier": "n/a", "armourtier": "n/a", "armourdamagetier": "80", "degrades": "60000", "repair_cost": "1000000", "necromancy": "46.0", "necromancyaccuracy": "84"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Occultist''s blood necklace'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Occultist''s hex necklace', 'Occultist''s hex necklace', 'Necromancy', 'NECK', NULL, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, 46.0, NULL, '{{sc|necromancy|80}} {{sc|dungeoneering|70}}', NULL, '{"requirements": "{{sc|necromancy|80}} {{sc|dungeoneering|70}}", "class": "Necromancy", "slot": "neck", "tier": "n/a", "armourtier": "n/a", "armourdamagetier": "80", "necromancyaccuracy": "84.0", "necromancy": "46.0"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}} {{sc|dungeoneering|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Occultist''s hex necklace'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Occultist''s revival necklace', 'Occultist''s revival necklace', 'none', 'NECK', NULL, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, 31.6, NULL, '{{sc|necromancy|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}", "class": "none", "slot": "neck", "tier": "", "necromancy": "31.6", "necromancyaccuracy": "40"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Occultist''s revival necklace'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Occultist''s undead necklace', 'Occultist''s undead necklace', 'none', 'NECK', NULL, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, 17.2, NULL, '{{sc|necromancy|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}", "class": "none", "slot": "neck", "tier": "", "necromancy": "17.2"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Occultist''s undead necklace'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Off-hand ruinous crossbow', 'Off-hand ruinous crossbow', 'ranged', 'OFF_HAND_WEAPON', 90, 'BOLT', 'ARROW', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|ranged|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|ranged|90}}", "class": "ranged", "slot": "off-hand weapon", "style": "bolt", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "2500000", "damage": "0.0", "accuracy": "3100", "attack_range": "7", "speed": "fastest", "image": "Offhand Ruinous Crossbow Equipped.png"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 0.0, NULL, 90, NULL, TRUE, NULL, 'Offhand Ruinous Crossbow Equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|ranged|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Off-hand ruinous crossbow'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Off-hand ruinous rapier', 'Off-hand ruinous rapier', 'melee', 'OFF_HAND_WEAPON', 90, 'STAB', 'STAB', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|attack|90}}", "class": "melee", "slot": "off-hand weapon", "style": "stab", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "2500000", "damage": "551.2", "accuracy": "3100", "attack_range": "1", "speed": "fast"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 551.2, NULL, 90, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Off-hand ruinous rapier'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Promethium guard', 'Promethium guard', 'necromancy', 'WEAPON', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, '{"requirements": "{{sc|necromancy|90}}", "class": "necromancy", "slot": "weapon", "tier": "90", "damage": "1341.0", "accuracy": "2458", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 2458.0, NULL, NULL, NULL, NULL, NULL, 1341.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Promethium guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Promethium lantern', 'Promethium lantern', 'necromancy', 'OFF_HAND_WEAPON', 90, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, '{"requirements": "{{sc|necromancy|90}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "90", "damage": "670.5", "accuracy": "2458", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 2458.0, NULL, NULL, NULL, NULL, NULL, 670.5, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Promethium lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Ruinous crossbow', 'Ruinous crossbow', 'ranged', 'WEAPON', 90, 'BOLT', 'ARROW', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|ranged|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|ranged|90}}", "class": "ranged", "style": "bolt", "slot": "weapon", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "5000000", "damage": "0.0", "accuracy": "3100", "attack_range": "7", "speed": "fastest", "image": "Ruinous Crossbow Equipped.png"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 0.0, NULL, 90, NULL, TRUE, NULL, 'Ruinous Crossbow Equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|ranged|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Ruinous crossbow'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Ruinous guard', 'Ruinous guard', 'necromancy', 'WEAPON', 90, NULL, 'NONE', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|necromancy|90}}", "class": "necromancy", "slot": "weapon", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "5000000", "damage": "1341.0", "accuracy": "3100", "attack_range": "6", "speed": "average", "image": "Ruinous guard equipped.png"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 1341.0, NULL, 90, NULL, TRUE, NULL, 'Ruinous guard equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Ruinous guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Ruinous lantern', 'Ruinous lantern', 'necromancy', 'OFF_HAND_WEAPON', 90, NULL, 'NONE', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|necromancy|90}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "2500000", "damage": "670.5", "accuracy": "3100", "attack_range": "6", "speed": "average", "image": "Ruinous lantern equipped.png"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 670.5, NULL, 90, NULL, TRUE, NULL, 'Ruinous lantern equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Ruinous lantern'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Ruinous maul', 'Ruinous maul', 'melee', 'TWOHANDED', 90, 'CRUSH', 'CRUSH', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|attack|90}}", "class": "melee", "slot": "2h", "style": "crush", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "5000000", "damage": "2011.5", "accuracy": "3100", "attack_range": "1", "speed": "average"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 2011.5, NULL, 90, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Ruinous maul'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Ruinous rapier', 'Ruinous rapier', 'melee', 'WEAPON', 90, 'STAB', 'STAB', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|attack|90}}", "class": "melee", "style": "stab", "slot": "weapon", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "5000000", "damage": "1102.5", "accuracy": "3100", "attack_range": "1", "speed": "fast", "image": "Ruinous Rapier Equipped.png"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 1102.5, NULL, 90, NULL, TRUE, NULL, 'Ruinous Rapier Equipped.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|attack|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Ruinous rapier'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Ruinous staff', 'Ruinous staff', 'magic', 'TWOHANDED', 90, 'MAGIC', 'SPELL', 90, 100, NULL, NULL, NULL, NULL, NULL, '{{sc|magic|90}}', NULL, '{"smwname": "new", "requirements": "{{sc|magic|90}}", "class": "magic", "slot": "2h", "tier": "90", "accuracytier": "100", "damagetier": "90", "degrades": "Yes", "repair_cost": "5000000", "damage": "0.0", "accuracy": "3100", "style": "spell", "attack_range": "8", "speed": "average", "image": "RuinousStaff.png"}'::jsonb, NULL, 3100.0, 100, NULL, NULL, NULL, NULL, 0.0, NULL, 90, NULL, TRUE, NULL, 'RuinousStaff.png', NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|magic|90}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Ruinous staff'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Saradomin''s hum', 'Saradomin''s hum', 'none', 'NECK', NULL, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, 40.2, 2.0, '{{sc|necromancy|70}}', NULL, '{"requirements": "{{sc|necromancy|70}}", "class": "none", "slot": "neck", "tier": "n/a", "armourtier": "n/a", "armourdamagetier": "70", "prayer": "2", "necromancy": "40.2"}'::jsonb, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|70}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Saradomin''s hum'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowblood gloves', 'Shadowblood gloves', 'necromancy', 'GLOVES', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}, {{sc|defence|30}}", "class": "necromancy", "slot": "hands", "tier": "30", "type": "tank", "armour": "22.7", "life": "150", "image": "Shadowblood armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 22.7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowblood armour equipped (male).png', NULL, NULL, 150.0, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowblood gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowblood hood', 'Shadowblood hood', 'necromancy', 'HEAD', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}, {{sc|defence|30}}", "class": "necromancy", "slot": "head", "tier": "30", "type": "tank", "armour": "90.8", "life": "300", "image": "Shadowblood armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 90.8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowblood armour equipped (male).png', NULL, NULL, 300.0, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowblood hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowblood robe bottom', 'Shadowblood robe bottom', 'necromancy', 'LEGS', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}, {{sc|defence|30}}", "class": "necromancy", "slot": "legs", "tier": "30", "type": "tank", "armour": "99.8", "life": "450", "image": "Shadowblood armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 99.8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowblood armour equipped (male).png', NULL, NULL, 450.0, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowblood robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowblood robe top', 'Shadowblood robe top', 'necromancy', 'BODY', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}, {{sc|defence|30}}", "class": "necromancy", "slot": "body", "tier": "30", "type": "tank", "armour": "104.4", "life": "450", "image": "Shadowblood armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 104.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowblood armour equipped (male).png', NULL, NULL, 450.0, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowblood robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowblood shoes', 'Shadowblood shoes', 'necromancy', 'BOOTS', 30, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, '{"requirements": "{{sc|necromancy|30}}, {{sc|defence|30}}", "class": "necromancy", "slot": "feet", "tier": "30", "type": "tank", "armour": "22.7", "life": "150", "image": "Shadowblood armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 22.7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowblood armour equipped (male).png', NULL, NULL, 150.0, NULL, NULL, NULL, '{{sc|necromancy|30}}, {{sc|defence|30}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowblood shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowbryll gloves', 'Shadowbryll gloves', 'necromancy', 'GLOVES', 40, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, '{"requirements": "{{sc|necromancy|40}}, {{sc|defence|40}}", "class": "necromancy", "slot": "hands", "tier": "40", "type": "tank", "armour": "31.4", "life": "200", "image": "Shadowbryll gloves equipped.png"}'::jsonb, NULL, NULL, NULL, 31.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowbryll gloves equipped.png', NULL, NULL, 200.0, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowbryll gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowbryll hood', 'Shadowbryll hood', 'necromancy', 'HEAD', 40, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, '{"requirements": "{{sc|necromancy|40}}, {{sc|defence|40}}", "class": "necromancy", "slot": "head", "tier": "40", "type": "tank", "armour": "125.6", "life": "400", "image": "Shadowbryll armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 125.6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowbryll armour equipped (male).png', NULL, NULL, 400.0, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowbryll hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowbryll robe bottom', 'Shadowbryll robe bottom', 'necromancy', 'LEGS', 40, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, '{"requirements": "{{sc|necromancy|40}}, {{sc|defence|40}}", "class": "necromancy", "slot": "legs", "tier": "40", "type": "tank", "armour": "138.1", "life": "600", "image": "Shadowbryll armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 138.1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowbryll armour equipped (male).png', NULL, NULL, 600.0, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowbryll robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowbryll robe top', 'Shadowbryll robe top', 'necromancy', 'BODY', 40, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, '{"requirements": "{{sc|necromancy|40}}, {{sc|defence|40}}", "class": "necromancy", "slot": "body", "tier": "40", "type": "tank", "armour": "144.4", "life": "600", "image": "Shadowbryll armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 144.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowbryll armour equipped (male).png', NULL, NULL, 600.0, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowbryll robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Shadowbryll shoes', 'Shadowbryll shoes', 'necromancy', 'BOOTS', 40, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, '{"requirements": "{{sc|necromancy|40}}, {{sc|defence|40}}", "class": "necromancy", "slot": "feet", "tier": "40", "type": "tank", "armour": "31.4", "life": "200", "image": "Shadowbryll armour equipped (male).png"}'::jsonb, NULL, NULL, NULL, 31.4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, 'Shadowbryll armour equipped (male).png', NULL, NULL, 200.0, NULL, NULL, NULL, '{{sc|necromancy|40}}, {{sc|defence|40}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Shadowbryll shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Soulrune gloves', 'Soulrune gloves', 'necromancy', 'GLOVES', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}, {{sc|defence|80}}", "class": "necromancy", "slot": "hands", "tier": "80", "type": "tank", "armour": "96.2", "life": "400"}'::jsonb, NULL, NULL, NULL, 96.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 400.0, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Soulrune gloves'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Soulrune hood', 'Soulrune hood', 'necromancy', 'HEAD', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}, {{sc|defence|80}}", "class": "necromancy", "slot": "head", "tier": "80", "type": "tank", "armour": "384.8", "life": "800"}'::jsonb, NULL, NULL, NULL, 384.8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 800.0, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Soulrune hood'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Soulrune robe bottom', 'Soulrune robe bottom', 'necromancy', 'LEGS', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}, {{sc|defence|80}}", "class": "necromancy", "slot": "legs", "tier": "80", "type": "tank", "armour": "423.2", "life": "1200"}'::jsonb, NULL, NULL, NULL, 423.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 1200.0, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Soulrune robe bottom'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Soulrune robe top', 'Soulrune robe top', 'necromancy', 'BODY', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}, {{sc|defence|80}}", "class": "necromancy", "slot": "body", "tier": "80", "type": "tank", "armour": "442.5", "life": "1200"}'::jsonb, NULL, NULL, NULL, 442.5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 1200.0, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Soulrune robe top'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Soulrune shoes', 'Soulrune shoes', 'necromancy', 'BOOTS', 80, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, '{"requirements": "{{sc|necromancy|80}}, {{sc|defence|80}}", "class": "necromancy", "slot": "feet", "tier": "80", "type": "tank", "armour": "96.2", "life": "400"}'::jsonb, NULL, NULL, NULL, 96.2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, 400.0, NULL, NULL, NULL, '{{sc|necromancy|80}}, {{sc|defence|80}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Soulrune shoes'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Zephyrium guard', 'Zephyrium guard', 'necromancy', 'WEAPON', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}", "class": "necromancy", "slot": "weapon", "tier": "50", "damage": "745.0", "accuracy": "850", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 850.0, NULL, NULL, NULL, NULL, NULL, 745.0, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Zephyrium guard'))
);

INSERT INTO equipment (title, name, class, slot, tier, style, type, damagetier, accuracytier, strength, ranged, magic, necromancy, prayer, requirements_raw, req_strength, raw_infobox, effects, accuracy, accuracy_tier, armour, armour_damage_tier, armour_tier, attack_range, damage, damage_bonus, damage_tier, defensive_bonus, equippable, ids, images, invtier, level_requirement, life, members, pvm_reduction, pvp_reduction, requirements, speed, versions)
SELECT 'Zephyrium lantern', 'Zephyrium lantern', 'necromancy', 'OFF_HAND_WEAPON', 50, NULL, 'NONE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}', NULL, '{"requirements": "{{sc|necromancy|50}}", "class": "necromancy", "slot": "off-hand weapon", "tier": "50", "damage": "372.5", "accuracy": "850", "attack_range": "6", "speed": "average"}'::jsonb, NULL, 850.0, NULL, NULL, NULL, NULL, NULL, 372.5, NULL, NULL, NULL, TRUE, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '{{sc|necromancy|50}}', NULL, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM equipment
    WHERE lower(trim(title)) = lower(trim('Zephyrium lantern'))
);

COMMIT;
