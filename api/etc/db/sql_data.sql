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
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (1, 'GodsLove',1, 'For God so _____ the world','loved','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (2, 'GodsLove',2, 'that he sent his only begotten ____','Son','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (3, 'GodsLove',3, 'that whoever ____ in him','believes','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (4, 'GodsLove',4, 'shall not perish but have everlasting ____','life','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (5, '',99, 'Keep on lovig one another as brothers and ____','sisters','Hebrews 13:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (6, '',99, 'Do not forget to show hospitalty to ____','strangers','Hebrews 13:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (7, '',99, 'Continue to remember those in ____, as if you were together with them','prison','Hebrews 13:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (8, '',99, '____ shoud be honoured by all, and the bed kept pure','Marriage','Hebrews 13:4');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (9, '',99, 'Keep you lives free from the love of ____ and be content with what you have','money','Hebrews 13:5');
select * from biblegame.question;

/** TABLE: Friends */
insert into biblegame.friends (user_id, friend_id) VALUES (1, 2);
insert into biblegame.friends (user_id, friend_id) VALUES (2, 1);
select * from biblegame.friends;

/** TABLE: Message */
insert into biblegame.message (id, name, from_id, user_id, team_id, message) VALUES (1, 'test', 1, 2, null, 'Testing message');
select * from biblegame.message;
