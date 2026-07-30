UPDATE equipment
SET effects = ARRAY_APPEND(effects, 'NECROMANCY_CONDUIT')
    WHERE LOWER(title) ILIKE('%lantern%') AND
    class = 'NECROMANCY';
