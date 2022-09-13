SELECT quest.title
FROM (quest LEFT JOIN character_quests
ON quest.id = character_quests.quest_id) LEFT JOIN character
ON character.id = character_quests.character_id
WHERE character.name = 'Kuro' AND character_quests.complete = 1
ORDER BY quest.title;
