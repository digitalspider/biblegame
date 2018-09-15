/** DROP Tables  */
DROP TABLE IF EXISTS biblegame.user;
DROP TABLE IF EXISTS biblegame.team;
DROP TABLE IF EXISTS biblegame.scroll;
DROP TABLE IF EXISTS biblegame.user_scroll;
DROP TABLE IF EXISTS biblegame.friends;
DROP TABLE IF EXISTS biblegame.message;

/** TABLE: User */
DROP TABLE IF EXISTS biblegame.user;
CREATE TABLE IF NOT EXISTS biblegame.user (
    id                   serial PRIMARY KEY,
    name                 varchar(16) not null,
	email                varchar(256) not null,
	password             varchar(256) not null,
	displayName          varchar(16) null,
	level                int not null default 0,
	stamina              int not null default 0,
	knowledge            int not null default 0,
	love                 int not null default 0,
	riches               int not null default 0,
	character            int not null default 0,
	slaves               int not null default 0,
	tools                int not null default 0,
	locks                int not null default 0,
	team_id              bigint null,
	location_id          varchar(20) not null default 'HOME',
	roles                varchar(20) null,
	enabled              boolean not null default true,
	created_at           date not null default CURRENT_DATE
);

/** TABLE: Team */
DROP TABLE IF EXISTS biblegame.team;
CREATE TABLE IF NOT EXISTS biblegame.team (
    id                   serial PRIMARY KEY,
    name                 varchar(256) not null,
    tag                  varchar(6) not null,
    owner_id             bigint not null,
    enabled              boolean not null default true,
    CONSTRAINT FK_team_owner_id FOREIGN KEY (owner_id) REFERENCES biblegame.user (id)
);

/** TABLE: Scroll */
DROP TABLE IF EXISTS biblegame.scroll;
CREATE TABLE IF NOT EXISTS biblegame.scroll (
    id                   serial PRIMARY KEY,
    name                 varchar(256) not null
);

/** TABLE: Scroll = ManyToMany links User to Scroll */
DROP TABLE IF EXISTS biblegame.user_scroll;
CREATE TABLE IF NOT EXISTS biblegame.user_scroll (
    user_id                   bigint not null,
    scroll_id                 bigint not null,
    PRIMARY KEY(user_id,scroll_id),
	CONSTRAINT FK_user_scroll_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_user_scroll_scroll_id FOREIGN KEY (scroll_id) REFERENCES biblegame.scroll (id)
);

/** TABLE: Friends = ManyToMany links User to User */
DROP TABLE IF EXISTS biblegame.friends;
CREATE TABLE IF NOT EXISTS biblegame.friends (
    user_id                   bigint not null,
    friend_id                 bigint not null,
    accepted                  boolean not null default false,
    PRIMARY KEY(user_id,friend_id),
	CONSTRAINT FK_friends_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_friends_friend_id FOREIGN KEY (friend_id) REFERENCES biblegame.user (id)
);

/** TABLE: Message = ManyToMany links User to User or Team */
DROP TABLE IF EXISTS biblegame.message;
CREATE TABLE IF NOT EXISTS biblegame.message (
    id                   serial PRIMARY KEY,
    name                 varchar(256) not null,
	from_id              bigint NOT NULL,
	user_id              bigint NULL,
	team_id				 bigint NULL,
    message              varchar(1024),
    read                 boolean not null default false, 	
	CONSTRAINT FK_message_from_id FOREIGN KEY (from_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_message_user_id FOREIGN KEY (user_id) REFERENCES biblegame.user (id),
    CONSTRAINT FK_message_team_id FOREIGN KEY (team_id) REFERENCES biblegame.team (id)
);
