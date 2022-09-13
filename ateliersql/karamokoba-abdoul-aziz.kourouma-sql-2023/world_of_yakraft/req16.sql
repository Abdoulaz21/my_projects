DELETE FROM character
WHERE now() - lastconn > interval '1 year';
