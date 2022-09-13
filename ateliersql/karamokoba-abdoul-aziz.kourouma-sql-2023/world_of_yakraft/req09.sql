SELECT creature_template.name AS name
FROM creature_template LEFT JOIN creature
ON creature_template.id = creature.gid
WHERE creature.id = 6;
