/** DROP Tables  */
DROP TABLE IF EXISTS biblegame.friends;
DROP TABLE IF EXISTS biblegame.message;
DROP TABLE IF EXISTS biblegame.team;
DROP TABLE IF EXISTS biblegame.user_scroll;
DROP TABLE IF EXISTS biblegame.scroll;
DROP TABLE IF EXISTS biblegame.user_question;
DROP TABLE IF EXISTS biblegame.question;
DROP TABLE IF EXISTS biblegame.user;

/** TABLE: User */
DROP TABLE IF EXISTS biblegame.user;
CREATE TABLE IF NOT EXISTS biblegame.user (
    id                   serial PRIMARY KEY,
    name                 varchar(16) not null,
	email                varchar(256) not null,
	password             varchar(64) not null,
	token                varchar(512) null,
	display_name         varchar(16) null,
	level                int not null default 0,
	stamina              int not null default 0,
	knowledge            int not null default 0,
	faith                int not null default 0,
	love                 int not null default 0,
	riches               int not null default 0,
	`character`          int not null default 0,
	slaves               int not null default 0,
	tools                int not null default 0,
	locks                int not null default 0,
	books                int not null default 0,
	team_id              bigint null,
	location_id          varchar(20) not null default 'HOME',
	roles                varchar(20) null,
	enabled              boolean not null default true,
	created_at           timestamp not null default NOW(),
	last_login_at        timestamp not null default NOW()
);

/** TABLE: Team */
DROP TABLE IF EXISTS biblegame.team;
CREATE TABLE IF NOT EXISTS biblegame.team (
    id                   serial PRIMARY KEY,
    name                 varchar(256) not null,
    tag                  varchar(6) not null,
    owner_id             bigint(20) unsigned not null,
    enabled              boolean not null default true,
    CONSTRAINT FK_team_owner_id FOREIGN KEY (owner_id) REFERENCES biblegame.user (id)
);

/** TABLE: Scroll */
DROP TABLE IF EXISTS biblegame.scroll;
CREATE TABLE IF NOT EXISTS biblegame.scroll (
    id                   serial PRIMARY KEY,
    name                 varchar(256) not null,
    description          varchar(512) not null
);

/** TABLE: UserScroll = ManyToMany links User to Scroll */
DROP TABLE IF EXISTS biblegame.user_scroll;
CREATE TABLE IF NOT EXISTS biblegame.user_scroll (
    user_id                   bigint(20) unsigned not null,
    scroll_id                 bigint(20) unsigned not null,
    completed_at              timestamp null,
    attempts                  int not null default 0,
    created_at                timestamp not null default NOW(),
    PRIMARY KEY(user_id,scroll_id),
	CONSTRAINT FK_user_scroll_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_user_scroll_scroll_id FOREIGN KEY (scroll_id) REFERENCES biblegame.scroll (id)
);

/** TABLE: Question */
DROP TABLE IF EXISTS biblegame.question;
CREATE TABLE IF NOT EXISTS biblegame.question (
    id                   serial PRIMARY KEY,
    name                 varchar(512) not null,
    answer               varchar(256) not null,
    enabled              boolean not null default true,
    level                int not null default 0,
    category             varchar(256) null,
    sort                 int not null default 99,
    reference            varchar(256) null,
    correct              int not null default 0,
    wrong                int not null default 0,
    created_at           timestamp not null default NOW()
);

/** View: ViewQuestion with chapter and book
CREATE VIEW biblegame.vquestion AS 
SELECT *, 
substr(reference,0,position(':' in reference)) as chapter, 
substr(reference,0,position(' ' in substr(reference,3))+3) as book 
from biblegame.question;

/** TABLE: UserQuestion = ManyToMany links User to Question */
DROP TABLE IF EXISTS biblegame.user_question;
CREATE TABLE IF NOT EXISTS biblegame.user_question (
    user_id                   bigint(20) unsigned not null,
    question_id               bigint(20) unsigned not null,
    answered_at               timestamp not null default NOW(),
    correct                   int not null default 0,
    wrong                     int not null default 0,
    PRIMARY KEY(user_id,question_id),
	CONSTRAINT FK_user_question_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_user_question_question_id FOREIGN KEY (question_id) REFERENCES biblegame.question (id)
);

/** TABLE: Friends = ManyToMany links User to User */
DROP TABLE IF EXISTS biblegame.friends;
CREATE TABLE IF NOT EXISTS biblegame.friends (
    user_id                   bigint(20) unsigned not null,
    friend_id                 bigint(20) unsigned not null,
    accepted_at               timestamp null,
    created_at                timestamp not null default NOW(),
    PRIMARY KEY(user_id,friend_id),
	CONSTRAINT FK_friends_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_friends_friend_id FOREIGN KEY (friend_id) REFERENCES biblegame.user (id)
);

/** TABLE: Message = ManyToMany links User to User or Team */
DROP TABLE IF EXISTS biblegame.message;
CREATE TABLE IF NOT EXISTS biblegame.message (
    id                   serial PRIMARY KEY,
    name                 varchar(256) not null,
	from_id              bigint(20) unsigned NOT NULL,
	user_id              bigint(20) unsigned NULL,
	team_id				 bigint(20) unsigned NULL,
    message              varchar(1024),
    viewed               boolean not null default false,
    created_at           timestamp not null default NOW(), 	
	CONSTRAINT FK_message_from_id FOREIGN KEY (from_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_message_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_message_team_id FOREIGN KEY (team_id) REFERENCES biblegame.team (id)
);
