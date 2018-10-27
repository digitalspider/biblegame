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
-- truncate biblegame.user_question;
-- delete from biblegame.question;
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (1, 'GodsLove',1, 'For God so loved the world','loved','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (2, 'GodsLove',2, 'that he sent his only begotten Son','Son','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (3, 'GodsLove',3, 'that whoever believes in him','believes','John 3:16');
insert into biblegame.question (id, category, sort, name,answer,reference) VALUES (4, 'GodsLove',4, 'shall not perish but have everlasting life','life','John 3:16');
insert into biblegame.question (name,answer,reference) VALUES ('Keep on loving one another as brothers and sisters','sisters','Hebrews 13:1');
insert into biblegame.question (name,answer,reference) VALUES ('Do not forget to show hospitalty to strangers','strangers','Hebrews 13:2');
insert into biblegame.question (name,answer,reference) VALUES ('Continue to remember those in prison, as if you were together with them','prison','Hebrews 13:3');
insert into biblegame.question (name,answer,reference) VALUES ('Marriage should be honoured by all, and the bed kept pure','Marriage','Hebrews 13:4');
insert into biblegame.question (name,answer,reference) VALUES ('Keep you lives free from the love of money and be content with what you have','money','Hebrews 13:5');
insert into biblegame.question (name,answer,reference) VALUES ('Now there was a Pharisee, a man named Nicodemus who was a member of the Jewish ruling council','Nicodemus','John 3:1');
insert into biblegame.question (name,answer,reference) VALUES ('He came to Jesus at night and said, “Rabbi, we know that you are a teacher who has come from God"','night','John 3:2');
insert into biblegame.question (name,answer,reference) VALUES ('For no one could perform the signs you are doing if God were not with him','signs','John 3:2');
insert into biblegame.question (name,answer,reference) VALUES ('Very truly I tell you, no one can see the kingdom of God unless they are born again','born','John 3:3');
insert into biblegame.question (name,answer,reference) VALUES ('no one can enter the kingdom of God unless they are born of water and the Spirit','Spirit','John 3:5');
insert into biblegame.question (name,answer,reference) VALUES ('No one has ever gone into heaven except the one who came from heaven','heaven','John 3:13');
insert into biblegame.question (name,answer,reference) VALUES ('Just as Moses lifted up the snake in the wilderness, so the Son of Man must be lifted up','snake','John 3:14');
insert into biblegame.question (name,answer,reference) VALUES ('For God did not send his Son into the world to condemn the world  ','condemn','John 3:17');
insert into biblegame.question (name,answer,reference) VALUES ('Whoever believes in the Son of Man is not condemned','condemned','John 3:18');
insert into biblegame.question (name,answer,reference) VALUES ('whoever does not believe stands condemned already because they have not believed in the name of God’s one and only Son','believe','John 3:18');
insert into biblegame.question (name,answer,reference) VALUES ('Light has come into the world, but people loved darkness instead of light because their deeds were evil','darkness','John 3:19');
insert into biblegame.question (name,answer,reference) VALUES ('But whoever lives by the truth comes into the light','light','John 3:21');
insert into biblegame.question (name,answer,reference) VALUES ('Now John was at Aenon near Salim, because there was plenty of water, and people were coming and being baptized','baptized','John 3:23');
insert into biblegame.question (name,answer,reference) VALUES ('A person can receive only what is given them from heaven','heaven','John 3:27');
insert into biblegame.question (name,answer,reference) VALUES ('The bride belongs to the bridegroom','bride','John 3:29');
insert into biblegame.question (name,answer,reference) VALUES ('He must become greater; I must become less','greater','John 3:30');
insert into biblegame.question (name,answer,reference) VALUES ('The one who comes from above is above all; the one who is from the earth belongs to the earth','above','John 3:31');
insert into biblegame.question (name,answer,reference) VALUES ('He testifies to what he has seen and heard, but no one accepts his testimony','testimony','John 3:32');
insert into biblegame.question (name,answer,reference) VALUES ('Whoever has accepted it has certified that God is truthful','truthful','John 3:33');
insert into biblegame.question (name,answer,reference) VALUES ('For the one whom God has sent speaks the words of God','speaks','John 3:34');
insert into biblegame.question (name,answer,reference) VALUES ('The Father loves the Son and has placed everything in his hands','everything','John 3:35');
insert into biblegame.question (name,answer,reference) VALUES ('Whoever believes in the Son has eternal life','believes','John 3:36');
insert into biblegame.question (name,answer,reference) VALUES ('whoever rejects the Son will not see life, for God’s wrath remains on them','rejects','John 3:36');
insert into biblegame.question (name,answer,reference) VALUES ('After Jesus said this, he looked toward heaven and prayed','prayed','John 17:1');
insert into biblegame.question (name,answer,reference) VALUES ('Father, the hour has come. Glorify your Son, that your Son may glorify you','Glorify','John 17:1');
insert into biblegame.question (name,answer,reference) VALUES ('For you granted Jesus authority over all people','authority','John 17:2');
insert into biblegame.question (name,answer,reference) VALUES ('that Jesus might give eternal life to all those you have given him','eternal','John 17:2');
insert into biblegame.question (name,answer,reference) VALUES ('Now this is eternal life: that they know you, the only true God','know','John 17:3');
insert into biblegame.question (name,answer,reference) VALUES ('that they know you, the only true God, and Jesus Christ, whom you have sent','sent','John 17:3');
insert into biblegame.question (name,answer,reference) VALUES ('I have brought you glory on earth by finishing the work you gave me to do','work','John 17:4');
insert into biblegame.question (name,answer,reference) VALUES ('And now, Father, glorify me in your presence with the glory I had with you before the world began','glory','John 17:5');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:1','ancestors','In the past God spoke to our ancestors through the prophets at many times and in various ways,');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:2','Son','but in these last days he has spoken to us by his Son, whom he appointed heir of all things, and through whom also he made the universe.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:3','representation','The Son is the radiance of God’s glory and the exact representation of his being, sustaining all things by his powerful word. After he had provided purification for sins, he sat down at the right hand of the Majesty in heaven.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:4','superior','So he became as much superior to the angels as the name he has inherited is superior to theirs.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:5','angels','For to which of the angels did God ever say, “You are my Son; today I have become your Father”? Or again, “I will be his Father, and he will be my Son”?');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:6','firstborn','And again, when God brings his firstborn into the world, he says, “Let all God’s angels worship him.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:7','angels','In speaking of the angels he says, “He makes his angels spirits, and his servants flames of fire.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:8','justice','But about the Son he says, “Your throne, O God, will last for ever and ever; a scepter of justice will be the scepter of your kingdom.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:9','righteousness','You have loved righteousness and hated wickedness; therefore God, your God, has set you above your companions by anointing you with the oil of joy.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:10','foundations','He also says, “In the beginning, Lord, you laid the foundations of the earth, and the heavens are the work of your hands.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:11','garment','They will perish, but you remain; they will all wear out like a garment.');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:12','never','You will roll them up like a robe; like a garment they will be changed. But you remain the same, and your years will never end.”');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:13','footstoll','To which of the angels did God ever say, “Sit at my right hand until I make your enemies a footstool for your feet”?');
insert into biblegame.question (reference,answer,name) VALUES ('Hebrews 1:14','serve','Are not all angels ministering spirits sent to serve those who will inherit salvation?');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:1','Thesselonians','To the church of the Thessalonians in God the Father and the Lord Jesus Christ: Grace and peace to you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:2','continually','We always thank God for all of you and continually mention you in our prayers.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:3','remember','We remember before our God and Father your work produced by faith, your labor prompted by love, and your endurance inspired by hope in our Lord Jesus Christ.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:4','chosen','For we know, brothers and sisters loved by God, that he has chosen you,');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:5','power','because our gospel came to you not simply with words but also with power, with the Holy Spirit and deep conviction. You know how we lived among you for your sake.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:6','imitators','You became imitators of us and of the Lord, for you welcomed the message in the midst of severe suffering with the joy given by the Holy Spirit.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:7','believers','And so you became a model to all the believers in Macedonia and Achaia.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:8','everywhere','The Lord’s message rang out from you not only in Macedonia and Achaia—your faith in God has become known everywhere. Therefore we do not need to say anything about it,');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:9','idols','for they themselves report what kind of reception you gave us. They tell how you turned to God from idols to serve the living and true God,');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 1:10','rescues','and to wait for his Son from heaven, whom he raised from the dead—Jesus, who rescues us from the coming wrath.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:1','visit','You know, brothers and sisters, that our visit to you was not without results.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:2','suffered','We had previously suffered and been treated outrageously in Philippi, as you know, but with the help of our God we dared to tell you his gospel in the face of strong opposition.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:3','trick','For the appeal we make does not spring from error or impure motives, nor are we trying to trick you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:4','approved','On the contrary, we speak as those approved by God to be entrusted with the gospel. We are not trying to please people but God, who tests our hearts.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:5','flattery','You know we never used flattery, nor did we put on a mask to cover up greed—God is our witness.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:6','praise','We were not looking for praise from people, not from you or anyone else, even though as apostles of Christ we could have asserted our authority.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:7','children','Instead, we were like young children among you. Just as a nursing mother cares for her children,');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:8','loved','so we cared for you. Because we loved you so much, we were delighted to share with you not only the gospel of God but our lives as well.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:9','hardship','Surely you remember, brothers and sisters, our toil and hardship; we worked night and day in order not to be a burden to anyone while we preached the gospel of God to you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:10','witnesses','You are witnesses, and so is God, of how holy, righteous and blameless we were among you who believed.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:11','father','For you know that we dealt with each of you as a father deals with his own children,');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:12','worthy','encouraging, comforting and urging you to live lives worthy of God, who calls you into his kingdom and glory.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:13','continually','And we also thank God continually because, when you received the word of God, which you heard from us, you accepted it not as a human word, but as it actually is, the word of God, which is indeed at work in you who believe.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:14','imitators','For you, brothers and sisters, became imitators of God’s churches in Judea, which are in Christ Jesus: You suffered from your own people the same things those churches suffered from the Jews');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:15','hostile','who killed the Lord Jesus and the prophets and also drove us out. They displease God and are hostile to everyone');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:16','saved','in their effort to keep us from speaking to the Gentiles so that they may be saved. In this way they always heap up their sins to the limit. The wrath of God has come upon them at last.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:17','separated','But, brothers and sisters, when we were orphaned by being separated from you for a short time (in person, not in thought), out of our intense longing we made every effort to see you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:18','blocked','For we wanted to come to you—certainly I, Paul, did, again and again—but Satan blocked our way.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:19','presence','For what is our hope, our joy, or the crown in which we will glory in the presence of our Lord Jesus when he comes? Is it not you?');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 2:20','joy','Indeed, you are our glory and joy.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:1','Athens','So when we could stand it no longer, we thought it best to be left by ourselves in Athens.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:2','Timothy','We sent Timothy, who is our brother and co-worker in God’s service in spreading the gospel of Christ, to strengthen and encourage you in your faith,');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:3','destined','so that no one would be unsettled by these trials. For you know quite well that we are destined for them.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:4','persecuted','In fact, when we were with you, we kept telling you that we would be persecuted. And it turned out that way, as you well know.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:5','faith','For this reason, when I could stand it no longer, I sent to find out about your faith. I was afraid that in some way the tempter had tempted you and that our labors might have been in vain.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:6','news','But Timothy has just now come to us from you and has brought good news about your faith and love. He has told us that you always have pleasant memories of us and that you long to see us, just as we also long to see you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:7','encouraged','Therefore, brothers and sisters, in all our distress and persecution we were encouraged about you because of your faith.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:8','Lord','For now we really live, since you are standing firm in the Lord.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:9','God','How can we thank God enough for you in return for all the joy we have in the presence of our God because of you?');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:10','pray','Night and day we pray most earnestly that we may see you again and supply what is lacking in your faith.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:11','Jesus','Now may our God and Father himself and our Lord Jesus clear the way for us to come to you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:12','increase','May the Lord make your love increase and overflow for each other and for everyone else, just as ours does for you.');
insert into biblegame.question (reference,answer,name) VALUES ('1 Thessalonians 3:13','holy','May he strengthen your hearts so that you will be blameless and holy in the presence of our God and Father when our Lord Jesus comes with all his holy ones.');
select * from biblegame.question;

/** TABLE: Friends */
insert into biblegame.friends (user_id, friend_id) VALUES (1, 2);
insert into biblegame.friends (user_id, friend_id) VALUES (2, 1);
select * from biblegame.friends;

/** TABLE: Message */
insert into biblegame.message (id, name, from_id, user_id, team_id, message) VALUES (1, 'test', 1, 2, null, 'Testing message');
select * from biblegame.message;
