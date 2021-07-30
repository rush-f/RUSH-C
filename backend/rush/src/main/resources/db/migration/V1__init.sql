create table article (
    id bigint not null auto_increment,
    content longtext,
    create_date datetime(6),
    latitude double precision,
    longitude double precision,
    private_map bit not null,
    public_map bit not null,
    title varchar(50) not null,
    user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table article_group (
    id bigint not null auto_increment,
    create_date datetime(6),
    article_id bigint not null,
    group_id bigint not null,
primary key (id)
) engine=InnoDB;

create table article_like (
    id bigint not null auto_increment,
    article_id bigint not null,
    user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table comment (
    id bigint not null auto_increment,
    content longtext,
    create_date datetime(6),
    article_id bigint not null,
    user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table group_table (
    id bigint not null auto_increment,
    create_date datetime(6),
    invitation_code varchar(255),
    name varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table user (
    id bigint not null auto_increment,
    email varchar(100) not null,
    image_url varchar(255),
    join_date datetime(6),
    nick_name varchar(100) not null,
    password varchar(255),
    provider varchar(255),
    provider_id varchar(255),
    visit_date datetime(6),
    primary key (id)
) engine=InnoDB;

create table user_group (
    id bigint not null auto_increment,
    create_date datetime(6),
    group_id bigint not null,
    user_id bigint not null,
    primary key (id)
) engine=InnoDB;

-- 제약조건 네이밍 규칙 : 테이블명_컬럼명_제약조건 --
alter table group_table
    add constraint group_table_invitation_code_unique unique (invitation_code);

alter table user
    add constraint user_email_unique unique (email);

alter table user_group
    add constraint user_group_user_id_group_id_unique unique (user_id, group_id);

alter table article
    add constraint article_user_id_foreign_key
        foreign key (user_id)
            references user (id);

alter table article_group
    add constraint article_group_article_id_foreign_key
        foreign key (article_id)
            references article (id);

alter table article_group
    add constraint article_group_group_id_foreign_key
        foreign key (group_id)
            references group_table (id);

alter table article_like
    add constraint article_like_article_id_foreign_key
        foreign key (article_id)
            references article (id);

alter table article_like
    add constraint article_like_user_id_foreign_key
        foreign key (user_id)
            references user (id);

alter table comment
    add constraint comment_article_id_foreign_key
        foreign key (article_id)
            references article (id);

alter table comment
    add constraint comment_user_id_foreign_key
        foreign key (user_id)
            references user (id);

alter table user_group
    add constraint user_group_group_id_foreign_key
        foreign key (group_id)
            references group_table (id);

alter table user_group
    add constraint user_group_user_id_foreign_key
        foreign key (user_id)
            references user (id);
