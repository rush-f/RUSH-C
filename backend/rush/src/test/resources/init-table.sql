set foreign_key_checks = 0;  -- foreign key check off

delete from article_group;
delete from user_group;
delete from article_like;
delete from comment_like;
delete from comment;
delete from article;
delete from user;
delete from group_table;

alter table article_group
    alter column id restart with 1;
alter table user_group
    alter column id restart with 1;
alter table article_like
    alter column id restart with 1;
alter table comment_like
    alter column id restart with 1;
alter table comment
    alter column id restart with 1;
alter table article
    alter column id restart with 1;
alter table user
    alter column id restart with 1;
alter table group_table
    alter column id restart with 1;

set foreign_key_checks = 0;  -- foreign key check on