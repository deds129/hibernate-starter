INSERT INTO public.company (id, name) VALUES (DEFAULT, 'Amazon');
INSERT INTO public.users (id, username, firstname, lastname, birth_date, role, company_id) VALUES (DEFAULT, 'nchudinov', 'nick', 'chudinov', '2022-08-09', 'ADMIN', 1);
INSERT INTO public.profile (id, user_id, street, language) VALUES (DEFAULT, 1, 'Central', 'ru');
INSERT INTO public.chat (id, name) VALUES (DEFAULT, 'devs');
INSERT INTO public.users_chat (id, user_id, chat_id, created_at, created_by) VALUES (DEFAULT, 1, 1, '2022-08-07 13:27:20.000000', 'Nick');