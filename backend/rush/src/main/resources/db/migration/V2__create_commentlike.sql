create table comment_like (
      id bigint not null auto_increment,
      comment_id bigint not null,
      user_id bigint not null,
      primary key (id)
) engine=InnoDB;

alter table comment_like
    add constraint comment_like_comment_id_foreign_key
        foreign key (comment_id)
            references comment (id);

alter table comment_like
    add constraint comment_like_user_id_foreign_key
        foreign key (user_id)
            references user (id);