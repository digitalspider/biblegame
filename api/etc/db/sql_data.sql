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
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (1, 'GodsLove',1, 'For God so loved the world','loved','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (2, 'GodsLove',2, 'that he sent his only begotten Son','Son','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (3, 'GodsLove',3, 'that whoever believes in him','believes','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (4, 'GodsLove',4, 'shall not perish but have everlasting life','life','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (5, '',99, 'Keep on loving one another as brothers and sisters','sisters','Hebrews 13:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (6, '',99, 'Do not forget to show hospitalty to strangers','strangers','Hebrews 13:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (7, '',99, 'Continue to remember those in prison, as if you were together with them','prison','Hebrews 13:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (8, '',99, 'Marriage should be honoured by all, and the bed kept pure','Marriage','Hebrews 13:4');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (9, '',99, 'Keep you lives free from the love of money and be content with what you have','money','Hebrews 13:5');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (10, '',99, 'Now there was a Pharisee, a man named Nicodemus who was a member of the Jewish ruling council','Nicodemus','John 3:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (11, '',99, 'He came to Jesus at night and said, “Rabbi, we know that you are a teacher who has come from God"','night','John 3:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (12, '',99, 'For no one could perform the signs you are doing if God were not with him','signs','John 3:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (13, '',99, 'Very truly I tell you, no one can see the kingdom of God unless they are born again','born','John 3:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (14, '',99, 'no one can enter the kingdom of God unless they are born of water and the Spirit','Spirit','John 3:5');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (15, '',99, 'No one has ever gone into heaven except the one who came from heaven','heaven','John 3:13');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (16, '',99, 'Just as Moses lifted up the snake in the wilderness, so the Son of Man must be lifted up','snake','John 3:14');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (17, '',99, 'For God did not send his Son into the world to condemn the world  ','condemn','John 3:17');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (18, '',99, 'Whoever believes in the Son of Man is not condemned','condemned','John 3:18');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (19, '',99, 'whoever does not believe stands condemned already because they have not believed in the name of God’s one and only Son','believe','John 3:18');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (20, '',99, 'Light has come into the world, but people loved darkness instead of light because their deeds were evil','darkness','John 3:19');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (21, '',99, 'But whoever lives by the truth comes into the light','light','John 3:21');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (22, '',99, 'Now John was at Aenon near Salim, because there was plenty of water, and people were coming and being baptized','baptized','John 3:23');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (23, '',99, 'A person can receive only what is given them from heaven','heaven','John 3:27');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (24, '',99, 'The bride belongs to the bridegroom','bride','John 3:29');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (25, '',99, 'He must become greater; I must become less','greater','John 3:30');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (26, '',99, 'The one who comes from above is above all; the one who is from the earth belongs to the earth','above','John 3:31');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (27, '',99, 'He testifies to what he has seen and heard, but no one accepts his testimony','testimony','John 3:32');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (28, '',99, 'Whoever has accepted it has certified that God is truthful','truthful','John 3:33');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (29, '',99, 'For the one whom God has sent speaks the words of God','speaks','John 3:34');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (30, '',99, 'The Father loves the Son and has placed everything in his hands','everything','John 3:35');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (31, '',99, 'Whoever believes in the Son has eternal life','believes','John 3:36');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (32, '',99, 'whoever rejects the Son will not see life, for God’s wrath remains on them','rejects','John 3:36');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (33, '',99, 'After Jesus said this, he looked toward heaven and prayed','prayed','John 17:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (34, '',99, 'Father, the hour has come. Glorify your Son, that your Son may glorify you','Glorify','John 17:1');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (35, '',99, 'For you granted Jesus authority over all people','authority','John 17:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (36, '',99, 'that Jesus might give eternal life to all those you have given him','eternal','John 17:2');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (37, '',99, 'Now this is eternal life: that they know you, the only true God','know','John 17:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (38, '',99, 'that they know you, the only true God, and Jesus Christ, whom you have sent','sent','John 17:3');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (39, '',99, 'I have brought you glory on earth by finishing the work you gave me to do','work','John 17:4');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (40, '',99, 'And now, Father, glorify me in your presence with the glory I had with you before the world began','glory','John 17:5');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:1','ancestors','In the past God spoke to our ancestors through the prophets at many times and in various ways,');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:2','Son','but in these last days he has spoken to us by his Son, whom he appointed heir of all things, and through whom also he made the universe.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:3','','The Son is the radiance of God’s glory and the exact representation of his being, sustaining all things by his powerful word. After he had provided purification for sins, he sat down at the right hand of the Majesty in heaven.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:4','','So he became as much superior to the angels as the name he has inherited is superior to theirs.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:5','','For to which of the angels did God ever say, “You are my Son; today I have become your Father”? Or again, “I will be his Father, and he will be my Son”?');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:6','','And again, when God brings his firstborn into the world, he says, “Let all God’s angels worship him.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:7','','In speaking of the angels he says, “He makes his angels spirits, and his servants flames of fire.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:8','','But about the Son he says, “Your throne, O God, will last for ever and ever; a scepter of justice will be the scepter of your kingdom.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:9','','You have loved righteousness and hated wickedness; therefore God, your God, has set you above your companions by anointing you with the oil of joy.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:10','','He also says, “In the beginning, Lord, you laid the foundations of the earth, and the heavens are the work of your hands.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:11','','They will perish, but you remain; they will all wear out like a garment.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:12','','You will roll them up like a robe; like a garment they will be changed. But you remain the same, and your years will never end.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:13','','To which of the angels did God ever say, “Sit at my right hand until I make your enemies a footstool for your feet”?');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:14','','Are not all angels ministering spirits sent to serve those who will inherit salvation?');
select * from biblegame.question;

/** TABLE: Friends */
insert into biblegame.friends (user_id, friend_id) VALUES (1, 2);
insert into biblegame.friends (user_id, friend_id) VALUES (2, 1);
select * from biblegame.friends;

/** TABLE: Message */
insert into biblegame.message (id, name, from_id, user_id, team_id, message) VALUES (1, 'test', 1, 2, null, 'Testing message');
select * from biblegame.message;
