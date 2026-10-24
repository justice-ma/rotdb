-- Game item IDs (ObjType.id) for equipment rows. One row per ID, so the primary key
-- guarantees every ID resolves to exactly one equipment row.
CREATE TABLE equipment_item_id (
    item_id        integer PRIMARY KEY,
    equipment_id   bigint NOT NULL REFERENCES equipment (id),
    wiki_page      varchar(255),
    version_anchor varchar(255),
    item_name      varchar(255),
    source         varchar(16) NOT NULL DEFAULT 'wiki' CHECK (source IN ('wiki', 'manual')),
    last_seen_at   timestamptz
);

CREATE INDEX equipment_item_id_equipment_id_idx ON equipment_item_id (equipment_id);

-- Natural key of the wiki stat record a row was scraped from: the page title, or
-- "Page#version" when a page's versions have different bonuses.
ALTER TABLE equipment ADD COLUMN wiki_key varchar(512);
ALTER TABLE equipment ADD CONSTRAINT equipment_wiki_key_key UNIQUE (wiki_key);

-- Family link for dyed, augmented, ornament and upgraded variants. Points at the root item.
ALTER TABLE equipment ADD COLUMN variant_of_id bigint REFERENCES equipment (id);
CREATE INDEX equipment_variant_of_id_idx ON equipment (variant_of_id);
