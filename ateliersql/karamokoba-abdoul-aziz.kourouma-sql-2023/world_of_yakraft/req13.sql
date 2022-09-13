SELECT creature_template.name
FROM creature RIGHT JOIN creature_template
ON creature.gid = creature_template.id
WHERE creature.id IS NULL
ORDER BY creature_template.name;
