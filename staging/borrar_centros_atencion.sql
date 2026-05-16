-- Resetea las tablas y reinicia los IDs a 1
TRUNCATE TABLE centro_atencion, point, especialidad, consultorio, medico, centro_especialidad RESTART IDENTITY CASCADE;