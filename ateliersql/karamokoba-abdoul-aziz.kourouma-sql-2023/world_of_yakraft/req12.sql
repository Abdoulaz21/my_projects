SELECT DISTINCT creature_template.name
FROM (quest LEFT JOIN creature
ON quest.creature_start = creature.id) LEFT JOIN creature_template
ON creature.gid = creature_template.id
WHERE quest.creature_start = quest.creature_end
ORDER BY creature_template.name;
