--
--
-- DO
-- $$
--     DECLARE
--         grade_id INT;
--         student_id INT;
--         student_identifier VARCHAR(30);
--         grade_level INT;
--     BEGIN
--         FOR grade_id IN 1..15 LOOP
--                 FOR student_id IN 1..30 LOOP
--                         student_identifier := 'student_' || grade_id || '_' || student_id;
--
--                         -- Determine the grade level based on the grade_id
--                         CASE
--                             WHEN grade_id BETWEEN 1 AND 3 THEN grade_level := 1;
--                             WHEN grade_id BETWEEN 4 AND 6 THEN grade_level := 2;
--                             WHEN grade_id BETWEEN 7 AND 9 THEN grade_level := 3;
--                             WHEN grade_id BETWEEN 10 AND 12 THEN grade_level := 4;
--                             WHEN grade_id BETWEEN 13 AND 15 THEN grade_level := 5;
--                             END CASE;
--
--                         INSERT INTO users (idUser, identifier, firstname, lastname, email, password, role, idgrade)
--                         VALUES (nextval('users_seq'), student_identifier, 'Firstname' || student_id, 'Lastname' || student_id,
--                                 student_identifier || '@example.com', '$2a$10$LmVlsr1CXT/G63tqruIQMejcmIsyn05igAmZo/ioSebHwZEohgjHG', 'STUDENT', grade_level)
--                         ON CONFLICT (identifier) DO NOTHING;
--                     END LOOP;
--             END LOOP;
--     END
-- $$;
--

-- DO
-- $$
--     DECLARE
--         grade_id INT;
--         student_id INT;
--         student_identifier VARCHAR(30);
--     BEGIN
--         FOR grade_id IN 1..15 LOOP
--                 FOR student_id IN 1..30 LOOP
--                         student_identifier := 'student_' || grade_id || '_' || student_id;
--                         INSERT INTO users (idUser, identifier, firstname, lastname, email, password, role, idgrade)
--                         VALUES (nextval('users_seq'), student_identifier, 'Firstname' || student_id, 'Lastname' || student_id,
--                                 student_identifier || '@example.com', '$2a$10$LmVlsr1CXT/G63tqruIQMejcmIsyn05igAmZo/ioSebHwZEohgjHG', 'student', grade_id)
--                         ON CONFLICT (identifier) DO NOTHING;
--                     END LOOP;
--             END LOOP;
--     END
-- $$;

-- Insert professors if not exists
-- INSERT INTO users (idUser, identifier, firstname, lastname, email, password, role, idgrade)
-- VALUES (100, 'prof1', 'ProfFirst1', 'ProfLast1', 'prof1@example.com', 'password', 'professor', NULL),
--        (101, 'prof2', 'ProfFirst2', 'ProfLast2', 'prof2@example.com', 'password', 'professor', NULL),
--        (102, 'prof3', 'ProfFirst3', 'ProfLast3', 'prof3@example.com', 'password', 'professor', NULL)
-- ON CONFLICT (identifier) DO NOTHING;



update users
    set  role='STUDENT'
where role='student';












































