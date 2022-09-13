SELECT CASE WHEN substring(acronym from '.$') = '1' THEN REPLACE(acronym, '1', '01')
            WHEN substring(acronym from '.$') = '1' THEN REPLACE(acronym, '1', '01')
            WHEN substring(acronym from '.$') = '2' THEN REPLACE(acronym, '2', '02')
            WHEN substring(acronym from '.$') = '3' THEN REPLACE(acronym, '3', '03')
            WHEN substring(acronym from '.$') = '4' THEN REPLACE(acronym, '4', '04')
            WHEN substring(acronym from '.$') = '5' THEN REPLACE(acronym, '5', '05')
            WHEN substring(acronym from '.$') = '6' THEN REPLACE(acronym, '6', '06')
            WHEN substring(acronym from '.$') = '7' THEN REPLACE(acronym, '7', '07')
            WHEN substring(acronym from '.$') = '8' THEN REPLACE(acronym, '8', '08')
            WHEN substring(acronym from '.$') = '9' THEN REPLACE(acronym, '9', '09')
            WHEN length(acronym) = 4 THEN acronym
    END AS acronym
FROM destination
ORDER BY acronym;
