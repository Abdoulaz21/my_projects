SELECT creature_template.name
FROM (creature_template LEFT JOIN loots
ON creature_template.id = loots.creature_gid) LEFT JOIN inventory
ON inventory.item_id = loots.item_id
WHERE inventory.character_id = (SELECT id FROM character WHERE name = 'Tilon')
ORDER BY creature_template.name;
