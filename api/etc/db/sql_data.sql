/** TABLE: User */
insert into biblegame.user (id, name, email, password. stamina) VALUES (1, 'test', 'test@test.com', '$2a$10$RHAk2V6CQH8Ehz/6Cjisoe61Du44fFVqLGEyxtElI4EWOsilrd4oW', 6);
insert into biblegame.user (id, name, email, password, stamina, team_id) VALUES (2, 'team', 'team@test.com', '$2a$10$KG4XgrOddRS9jCoMxmGTR.NjocRaT3UYPYEctjwx3YtMci8iDXJaq', 6, 1);
select * from biblegame.user;

/** TABLE: Team */
insert into biblegame.team (id, name, tag, owner_id) VALUES (1, 'CreatorTeam', 'GOD', 1);
select * from biblegame.team;

/** TABLE: Scroll */
insert into biblegame.scroll (id, name,description) VALUES (1, 'TestScroll','Test scroll');
select * from biblegame.scroll;

/** TABLE: User-Scroll */
insert into biblegame.user_scroll (user_id, scroll_id) VALUES (1, 1);
select * from biblegame.user_scroll;

/** TABLE: Question */
insert into biblegame.question (id, category, sort, name,answer,book,chapter,verse) VALUES (1, 'John3:16',1, 'For God so _____ the world','loved','John',3,16);
insert into biblegame.question (id, category, sort, name,answer,book,chapter,verse) VALUES (1, 'John3:16',2, 'that he sent his only begotten ____','Son','John',3,16);
insert into biblegame.question (id, category, sort, name,answer,book,chapter,verse) VALUES (1, 'John3:16',3, 'that whoever ____ in him','believes','John',3,16);
insert into biblegame.question (id, category, sort, name,answer,book,chapter,verse) VALUES (1, 'John3:16',4, 'shall not perish but have everlasting ____','Son','John',3,16);
select * from biblegame.question;

/** TABLE: Friends */
insert into biblegame.friends (user_id, friend_id) VALUES (1, 2);
insert into biblegame.friends (user_id, friend_id) VALUES (2, 1);
select * from biblegame.friends;

/** TABLE: Message */
insert into biblegame.message (id, name, from_id, user_id, team_id, message) VALUES (1, 'test', 1, 2, null, 'Testing message');
select * from biblegame.message;
