/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/5/29 10:32:13                           */
/*==============================================================*/


drop index idx_create_uid on activity;

drop table if exists activity;

drop index idx_user_name on admin_user;

drop table if exists admin_user;

drop index idx_activity_id on audit;

drop table if exists audit;

drop index idx_audit_id on audit_record;

drop table if exists audit_record;

drop index idx_create_uid on comment;

drop index idx_activity_id on comment;

drop table if exists comment;

drop index idx_activity_id on debate;

drop table if exists debate;

drop index idx_activity_id on discuss;

drop table if exists discuss;

drop index idx_activity_user_id on "order";

drop table if exists "order";

drop index idx_activity_user_id on order_record;

drop table if exists order_record;

drop index idx_email on user;

drop index idx_user_name on user;

drop table if exists user;

drop index idx_activity_user_id on user_favorite;

drop table if exists user_favorite;

drop index idx_activity_id on vote;

drop table if exists vote;

drop index idx_vote_id on vote_option;

drop table if exists vote_option;

/*==============================================================*/
/* Table: activity                                              */
/*==============================================================*/
create table activity
(
   id                   int unsigned not null auto_increment comment '主键',
   title                varchar(100) not null default '' comment '标题、名称',
   cover_img            varchar(200) not null default '' comment '封面图片URL',
   description          text not null default '' comment '描述',
   status               tinyint unsigned not null default 0 comment '活动状态(0：新建，1：审核驳回，2：审核通过，3：进行中，4：已失效，5：删除)',
   create_uid           int unsigned not null default 0 comment '创建人',
   type                 tinyint unsigned not null default 0 comment '活动类型(0:投票，1：辩论，2：探讨）',
   join_num             int unsigned not null default 0 comment '参与人数',
   view_num             int unsigned not null default 0 comment '浏览人数',
   level                tinyint unsigned not null default 0 comment '展示等级(0:普通，1：高级+1，2：高级+2）',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table activity comment '活动表';

/*==============================================================*/
/* Index: idx_create_uid                                        */
/*==============================================================*/
create index idx_create_uid on activity
(
   create_uid
);

/*==============================================================*/
/* Table: admin_user                                            */
/*==============================================================*/
create table admin_user
(
   id                   int unsigned not null auto_increment comment '主键',
   user_name            varchar(50) not null default '' comment '用户名',
   password             varchar(100) not null default '' comment '密码',
   email                varchar(50) not null default '' comment '邮箱',
   mobile               char(11) not null default '' comment '手机号',
   salt                 varchar(100) not null default '' comment '盐',
   status               tinyint unsigned not null default 0 comment '状态（0：正常，1：禁用，2：删除)',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   ip                   varchar(20) not null default '' comment 'IP地址',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table admin_user comment '后台用户表';

/*==============================================================*/
/* Index: idx_user_name                                         */
/*==============================================================*/
create index idx_user_name on admin_user
(
   user_name
);

/*==============================================================*/
/* Table: audit                                                 */
/*==============================================================*/
create table audit
(
   id                   int unsigned not null auto_increment comment '主键',
   activity_id          int unsigned not null default 0 comment '活动ID',
   status               tinyint unsigned not null default 0 comment '审核状态 （0:待审核，1：审核通过，2：审核驳回）',
   remark               varchar(200) not null default '' comment '审核备注',
   audit_uid            int unsigned not null default 0 comment '审核人',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table audit comment '审核表';

/*==============================================================*/
/* Index: idx_activity_id                                       */
/*==============================================================*/
create index idx_activity_id on audit
(
   activity_id
);

/*==============================================================*/
/* Table: audit_record                                          */
/*==============================================================*/
create table audit_record
(
   id                   int unsigned not null auto_increment comment '主键',
   audit_id             int unsigned not null default 0 comment '审核ID',
   status               tinyint unsigned not null default 0 comment '审核状态 （0:待审核，1：审核通过，2：审核驳回）',
   remark               varchar(200) not null default '' comment '审核备注',
   audit_uid            int unsigned not null default 0 comment '审核人',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table audit_record comment '审核记录表';

/*==============================================================*/
/* Index: idx_audit_id                                          */
/*==============================================================*/
create index idx_audit_id on audit_record
(
   audit_id
);

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   id                   int unsigned not null auto_increment comment '主键',
   activity_id          int unsigned not null default 0 comment '活动ID',
   status               tinyint unsigned not null default 0 comment '评论（0：待审核，1：审核驳回，2：展示，3：删除）',
   content              text not null default '' comment '评论内容',
   create_uid           int unsigned not null default 0 comment '评论人',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '发表时间',
   primary key (id)
)
ENGINE=MyISAM CHARSET=utf8;

alter table comment comment '评论';

/*==============================================================*/
/* Index: idx_activity_id                                       */
/*==============================================================*/
create index idx_activity_id on comment
(
   activity_id
);

/*==============================================================*/
/* Index: idx_create_uid                                        */
/*==============================================================*/
create index idx_create_uid on comment
(
   create_uid
);

/*==============================================================*/
/* Table: debate                                                */
/*==============================================================*/
create table debate
(
   id                   int unsigned not null auto_increment comment '主键',
   activity_id          int unsigned not null default 0 comment '活动ID',
   pros_opinion         varchar(200) not null default '' comment '正方观点',
   cons_opinion         varchar(200) not null default '' comment '反方观点',
   pros_support_num     int unsigned not null default 0 comment '正方支持人数',
   cons_support_num     int unsigned not null default 0 comment '反方支持人数',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table debate comment '辩论';

/*==============================================================*/
/* Index: idx_activity_id                                       */
/*==============================================================*/
create index idx_activity_id on debate
(
   activity_id
);

/*==============================================================*/
/* Table: discuss                                               */
/*==============================================================*/
create table discuss
(
   id                   int unsigned not null auto_increment comment '主键',
   activity_id          int unsigned not null default 0 comment '活动ID',
   content              text not null default '' comment '探讨问题',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table discuss comment '探讨';

/*==============================================================*/
/* Index: idx_activity_id                                       */
/*==============================================================*/
create index idx_activity_id on discuss
(
   activity_id
);

/*==============================================================*/
/* Table: "order"                                               */
/*==============================================================*/
create table "order"
(
   id                   int unsigned not null auto_increment comment '主键',
   user_id              int unsigned not null default 0 comment '用户ID',
   activity_id          int unsigned not null default 0 comment '活动ID',
   amount               int unsigned not null default 0 comment '订单金额',
   status               tinyint unsigned not null default 0 comment '订单状态(0:新增，1:待付款，2：成功，3：退款，4：取消，4：删除)',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table "order" comment '用户订单';

/*==============================================================*/
/* Index: idx_activity_user_id                                  */
/*==============================================================*/
create unique index idx_activity_user_id on "order"
(
   user_id,
   activity_id
);

/*==============================================================*/
/* Table: order_record                                          */
/*==============================================================*/
create table order_record
(
   id                   int unsigned not null auto_increment comment '主键',
   user_id              int unsigned not null default 0 comment '用户ID',
   status               tinyint unsigned not null default 0 comment '订单状态(0:新增，1:待付款，2：成功，3：退款，4：取消，4：删除)',
   operator_id          int unsigned not null default 0 comment '操作人',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table order_record comment '用户订单记录表';

/*==============================================================*/
/* Index: idx_activity_user_id                                  */
/*==============================================================*/
create unique index idx_activity_user_id on order_record
(
   user_id
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   int unsigned not null auto_increment comment '主键',
   user_name            varchar(50) not null default '' comment '用户名',
   password             varchar(100) not null default '' comment '密码',
   email                varchar(50) not null default '' comment '邮箱',
   mobile               char(11) not null default '' comment '手机号',
   salt                 varchar(100) not null default '' comment '盐',
   status               tinyint unsigned not null default 0 comment '状态（0：正常，1：禁用，2：删除)',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   ip                   varchar(20) not null default '' comment 'IP地址',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table user comment '用户表';

/*==============================================================*/
/* Index: idx_user_name                                         */
/*==============================================================*/
create index idx_user_name on user
(
   user_name
);

/*==============================================================*/
/* Index: idx_email                                             */
/*==============================================================*/
create index idx_email on user
(
   email
);

/*==============================================================*/
/* Table: user_favorite                                         */
/*==============================================================*/
create table user_favorite
(
   id                   int unsigned not null auto_increment comment '主键',
   user_id              int unsigned not null default 0 comment '用户ID',
   activity_id          int unsigned not null default 0 comment '活动ID',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default '0000-00-00 00:00:00' comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table user_favorite comment '用户收藏';

/*==============================================================*/
/* Index: idx_activity_user_id                                  */
/*==============================================================*/
create unique index idx_activity_user_id on user_favorite
(
   user_id,
   activity_id
);

/*==============================================================*/
/* Table: vote                                                  */
/*==============================================================*/
create table vote
(
   id                   int unsigned not null auto_increment comment '主键',
   activity_id          int unsigned not null default 0 comment '活动ID',
   type                 tinyint unsigned not null default 0 comment '活动类型(0:多选,1:单选）',
   start_time           timestamp not null default '0000-00-00 00:00-00' comment '投票开始时间',
   end_time             timestamp not null default '0000-00-00 00:00-00' comment '投票结束时间',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table vote comment '投票';

/*==============================================================*/
/* Index: idx_activity_id                                       */
/*==============================================================*/
create index idx_activity_id on vote
(
   activity_id
);

/*==============================================================*/
/* Table: vote_option                                           */
/*==============================================================*/
create table vote_option
(
   id                   int unsigned not null auto_increment comment '主键',
   vote_id              int unsigned not null default 0 comment '投票ID',
   content              varchar(500) not null default '' comment '选项内容',
   select_num           int unsigned not null default 0 comment '选择人数',
   rate                 tinyint unsigned not null default 0 comment '所占比率（投票结束后设置）',
   primary key (id)
)
ENGINE=InnoDB CHARSET=utf8;

alter table vote_option comment '投票选项';

/*==============================================================*/
/* Index: idx_vote_id                                           */
/*==============================================================*/
create index idx_vote_id on vote_option
(
   vote_id
);

