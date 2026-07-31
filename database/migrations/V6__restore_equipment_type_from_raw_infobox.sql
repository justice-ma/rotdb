BEGIN;

UPDATE equipment
SET type = CASE
    WHEN lower(btrim(raw_infobox->>'type')) = 'tank' THEN 'TANK'
    WHEN lower(btrim(raw_infobox->>'type')) IN ('power', 'power armour') THEN 'POWER'
    WHEN lower(btrim(raw_infobox->>'type')) = 'hybrid' THEN 'HYBRID'
    WHEN lower(btrim(raw_infobox->>'type')) IN ('power hybrid', 'hybrid power') THEN 'POWER_HYBRID'
    WHEN lower(btrim(raw_infobox->>'type')) = 'shield' THEN 'SHIELD'
    WHEN lower(btrim(raw_infobox->>'type')) = 'defender' THEN 'DEFENDER'
    WHEN lower(btrim(raw_infobox->>'type')) = 'rebounder' THEN 'REBOUNDER'
    WHEN lower(btrim(raw_infobox->>'type')) = 'repriser' THEN 'REPRISER'
    WHEN lower(btrim(raw_infobox->>'type')) = 'spear' THEN 'SPEAR'
    WHEN lower(btrim(raw_infobox->>'type')) = 'halberd' THEN 'HALBERD'
    WHEN lower(btrim(raw_infobox->>'type')) = 'spear, halberd' THEN 'SPEAR_HALBERD'
    WHEN lower(btrim(raw_infobox->>'type')) = 'longbow' THEN 'LONGBOW'
    WHEN lower(btrim(raw_infobox->>'type')) = 'chargebow' THEN 'CHARGEBOW'
    WHEN lower(btrim(raw_infobox->>'type')) = 'shieldbow' THEN 'SHIELDBOW'
    WHEN lower(btrim(raw_infobox->>'type')) = 'cosmetic' THEN 'COSMETIC'
    WHEN lower(btrim(raw_infobox->>'type')) = 'prevents attack' THEN 'PREVENTS_ATTACK'
    WHEN lower(btrim(raw_infobox->>'type')) = 'pvp' THEN 'PVP'
    ELSE type
END
WHERE raw_infobox ? 'type';

COMMIT;
