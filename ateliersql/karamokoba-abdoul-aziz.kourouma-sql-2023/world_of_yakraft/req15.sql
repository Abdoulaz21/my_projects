SELECT quest.title, character.name
FROM (quest LEFT JOIN character_quests
ON quest.id = character_quests.quest_id) LEFT JOIN character
ON character.id = character_quests.character_id
WHERE character_quests.complete = 0
ORDER BY quest.title, character.name;
