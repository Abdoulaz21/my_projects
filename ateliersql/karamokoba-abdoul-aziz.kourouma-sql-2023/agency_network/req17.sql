UPDATE agency
SET ratings = (2 * ratings)
WHERE code <> 101 AND code <> 212 AND code <> 213;
