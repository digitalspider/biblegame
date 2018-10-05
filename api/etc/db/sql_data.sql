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
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (5, '',99, 'Keep on loving one another as brothers and ____','sisters','Hebrews 13:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (6, '',99, 'Do not forget to show hospitalty to ____','strangers','Hebrews 13:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (7, '',99, 'Continue to remember those in ____, as if you were together with them','prison','Hebrews 13:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (8, '',99, '____ should be honoured by all, and the bed kept pure','Marriage','Hebrews 13:4');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (9, '',99, 'Keep you lives free from the love of ____ and be content with what you have','money','Hebrews 13:5');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (10, '',99, 'Now there was a Pharisee, a man named ____ who was a member of the Jewish ruling council','Nicodemus','John 3:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (11, '',99, 'He came to Jesus at ____ and said, “Rabbi, we know that you are a teacher who has come from God"','night','John 3:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (12, '',99, 'For no one could perform the ____ you are doing if God were not with him','signs','John 3:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (13, '',99, 'Very truly I tell you, no one can see the kingdom of God unless they are ____ again','born','John 3:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (14, '',99, 'no one can enter the kingdom of God unless they are born of water and the ____','Spirit','John 3:5');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (15, '',99, 'No one has ever gone into heaven except the one who came from ____','heaven','John 3:13');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (16, '',99, 'Just as Moses lifted up the ____ in the wilderness, so the Son of Man must be lifted up','snake','John 3:14');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (17, '',99, 'For God did not send his Son into the world to ____ the world  ','condemn','John 3:17');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (18, '',99, 'Whoever believes in the Son of Man is not ____','condemned','John 3:18');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (19, '',99, 'whoever does not ____ stands condemned already because they have not believed in the name of God’s one and only Son','believe','John 3:18');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (20, '',99, 'Light has come into the world, but people loved ____ instead of light because their deeds were evil','darkness','John 3:19');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (21, '',99, 'But whoever lives by the truth comes into the ____','light','John 3:21');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (22, '',99, 'Now John was at Aenon near Salim, because there was plenty of water, and people were coming and being ____','baptized','John 3:23');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (23, '',99, 'A person can receive only what is given them from ____','heaven','John 3:27');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (24, '',99, 'The ____ belongs to the bridegroom','bride','John 3:29');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (25, '',99, 'He must become ____; I must become less','greater','John 3:30');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (26, '',99, 'The one who comes from ____ is above all; the one who is from the earth belongs to the earth','above','John 3:31');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (27, '',99, 'He testifies to what he has seen and heard, but no one accepts his ____','testimony','John 3:32');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (28, '',99, 'Whoever has accepted it has certified that God is ____','truthful','John 3:33');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (29, '',99, 'For the one whom God has sent ____ the words of God','speaks','John 3:34');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (30, '',99, 'The Father loves the Son and has placed ____ in his hands','everything','John 3:35');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (31, '',99, 'Whoever ____ in the Son has eternal life','believes','John 3:36');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (32, '',99, 'whoever ____ the Son will not see life, for God’s wrath remains on them','rejects','John 3:36');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (33, '',99, 'After Jesus said this, he looked toward heaven and ____','prayed','John 17:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (34, '',99, 'Father, the hour has come. ____ your Son, that your Son may glorify you','Glorify','John 17:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (35, '',99, 'For you granted Jesus ____ over all people','authority','John 17:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (36, '',99, 'that Jesus might give ____ life to all those you have given him','eternal','John 17:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (37, '',99, 'Now this is eternal life: that they ____ you, the only true God','know','John 17:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (38, '',99, 'that they know you, the only true God, and Jesus Christ, whom you have ____','sent','John 17:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (39, '',99, 'I have brought you glory on earth by finishing the ____ you gave me to do','work','John 17:4');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (40, '',99, 'And now, Father, glorify me in your presence with the ____ I had with you before the world began','glory','John 17:5');
select * from biblegame.question;

/** TABLE: Friends */
insert into biblegame.friends (user_id, friend_id) VALUES (1, 2);
insert into biblegame.friends (user_id, friend_id) VALUES (2, 1);
select * from biblegame.friends;

/** TABLE: Message */
insert into biblegame.message (id, name, from_id, user_id, team_id, message) VALUES (1, 'test', 1, 2, null, 'Testing message');
select * from biblegame.message;
