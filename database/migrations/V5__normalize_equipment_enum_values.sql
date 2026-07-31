BEGIN;

UPDATE equipment
SET "class" = CASE
    WHEN "class" IS NULL OR btrim("class"::text) = '' THEN NULL
    WHEN lower(btrim("class"::text)) IN ('none', 'n/a', 'null') THEN NULL
    WHEN lower(btrim("class"::text)) IN ('hybrid', 'all') THEN 'ALL'
    WHEN upper(btrim("class"::text)) IN ('MELEE', 'MAGIC', 'RANGED', 'NECROMANCY') THEN upper(btrim("class"::text))
    ELSE NULL
END;

UPDATE equipment
SET slot = CASE
    WHEN slot IS NULL OR btrim(slot::text) = '' THEN NULL
    WHEN lower(btrim(slot::text)) IN ('head', 'helmet', 'helm') THEN 'HEAD'
    WHEN lower(btrim(slot::text)) IN ('body', 'torso') THEN 'BODY'
    WHEN lower(btrim(slot::text)) IN ('gloves', 'hands') THEN 'GLOVES'
    WHEN lower(btrim(slot::text)) = 'legs' THEN 'LEGS'
    WHEN lower(btrim(slot::text)) IN ('boots', 'feet') THEN 'BOOTS'
    WHEN lower(btrim(slot::text)) = 'pocket' THEN 'POCKET'
    WHEN lower(btrim(slot::text)) IN ('offhand', 'off_hand_weapon', 'off-hand weapon', 'off hand weapon') THEN 'OFFHAND'
    WHEN lower(btrim(slot::text)) IN ('mainhand', 'weapon', 'main hand weapon', 'main-hand weapon') THEN 'MAINHAND'
    WHEN lower(btrim(slot::text)) IN ('twohanded', 'two-handed', 'two handed', '2h', '2h weapon') THEN 'TWOHANDED'
    WHEN lower(btrim(slot::text)) IN ('ammo', 'ammunition') THEN 'AMMO'
    WHEN lower(btrim(slot::text)) = 'ring' THEN 'RING'
    WHEN lower(btrim(slot::text)) IN ('neck', 'necklace') THEN 'NECK'
    WHEN lower(btrim(slot::text)) IN ('cape', 'back') THEN 'CAPE'
    WHEN lower(btrim(slot::text)) = 'quiver' THEN 'QUIVER'
    ELSE NULL
END;

UPDATE equipment
SET style = CASE
    WHEN style IS NULL OR btrim(style::text) = '' THEN 'NONE'
    WHEN lower(btrim(style::text)) IN ('none', 'n/a', 'null') THEN 'NONE'
    WHEN lower(btrim(style::text)) IN ('slash', 'slashing') THEN 'SLASH'
    WHEN lower(btrim(style::text)) IN ('arrow', 'arrows') THEN 'ARROW'
    WHEN lower(btrim(style::text)) IN ('bolt', 'bolts') THEN 'BOLT'
    WHEN lower(btrim(style::text)) IN ('crush', 'crushing') THEN 'CRUSH'
    WHEN lower(btrim(style::text)) IN ('spell', 'magic') THEN 'SPELL'
    WHEN lower(btrim(style::text)) IN ('stab', 'stabbing') THEN 'STAB'
    WHEN lower(btrim(style::text)) IN ('thrown', 'throwing') THEN 'THROWN'
    ELSE 'NONE'
END;

UPDATE equipment
SET type = CASE
    WHEN type IS NULL OR btrim(type::text) = '' THEN 'NONE'
    WHEN lower(btrim(type::text)) IN ('none', 'n/a', 'null') THEN 'NONE'
    WHEN lower(btrim(type::text)) = 'tank' THEN 'TANK'
    WHEN lower(btrim(type::text)) IN ('power', 'power armour') THEN 'POWER'
    WHEN lower(btrim(type::text)) = 'hybrid' THEN 'HYBRID'
    WHEN lower(btrim(type::text)) IN ('power hybrid', 'hybrid power', 'power_hybrid') THEN 'POWER_HYBRID'
    WHEN lower(btrim(type::text)) = 'shield' THEN 'SHIELD'
    WHEN lower(btrim(type::text)) = 'defender' THEN 'DEFENDER'
    WHEN lower(btrim(type::text)) = 'rebounder' THEN 'REBOUNDER'
    WHEN lower(btrim(type::text)) = 'repriser' THEN 'REPRISER'
    WHEN lower(btrim(type::text)) = 'spear' THEN 'SPEAR'
    WHEN lower(btrim(type::text)) = 'halberd' THEN 'HALBERD'
    WHEN lower(btrim(type::text)) IN ('spear, halberd', 'spear_halberd') THEN 'SPEAR_HALBERD'
    WHEN lower(btrim(type::text)) = 'longbow' THEN 'LONGBOW'
    WHEN lower(btrim(type::text)) = 'chargebow' THEN 'CHARGEBOW'
    WHEN lower(btrim(type::text)) = 'shieldbow' THEN 'SHIELDBOW'
    WHEN lower(btrim(type::text)) = 'cosmetic' THEN 'COSMETIC'
    WHEN lower(btrim(type::text)) = 'prevents attack' THEN 'PREVENTS_ATTACK'
    WHEN lower(btrim(type::text)) = 'pvp' THEN 'PVP'
    ELSE 'NONE'
END;

UPDATE targets
SET primarystyle = CASE
    WHEN primarystyle IS NULL OR btrim(primarystyle::text) = '' THEN NULL
    WHEN lower(btrim(primarystyle::text)) IN ('none', 'n/a', 'null') THEN NULL
    WHEN lower(btrim(primarystyle::text)) IN ('hybrid', 'all') THEN 'ALL'
    WHEN upper(btrim(primarystyle::text)) IN ('MELEE', 'MAGIC', 'RANGED', 'NECROMANCY') THEN upper(btrim(primarystyle::text))
    ELSE NULL
END;

COMMIT;
