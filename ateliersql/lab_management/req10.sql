UPDATE assistant
SET credit = 10.00;

UPDATE assistant
SET credit = credit + 40.00
WHERE login = 'el_hal_a';

UPDATE assistant
SET credit = credit + 20.00
WHERE login = 'claire.billy';

UPDATE assistant
SET credit = credit + 30.00
WHERE login = 'ma_9';
