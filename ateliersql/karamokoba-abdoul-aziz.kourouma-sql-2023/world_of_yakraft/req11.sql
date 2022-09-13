SELECT quest.title, creature_template.name
FROM (quest LEFT JOIN creature
ON quest.creature_start = creature.id) LEFT JOIN creature_template
ON creature.gid = creature_template.id
ORDER BY quest.title, creature_template.name;
