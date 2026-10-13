UPDATE equipment SET effects = array_append(effects, 'HAVENSILVER') WHERE LOWER(title) ilike'%havensilver%';

