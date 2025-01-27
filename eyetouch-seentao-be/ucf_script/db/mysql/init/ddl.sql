
drop table if EXISTS post;
CREATE TABLE `post` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `content` VARCHAR(8000) DEFAULT NULL COMMENT '帖子内容',
    `title` VARCHAR(64) DEFAULT NULL COMMENT '帖子标题',
    `type` int DEFAULT NULL COMMENT '帖子类型',
    `style` int DEFAULT NULL COMMENT '帖子风格',
    `uid` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
    `fpid` VARCHAR(64) DEFAULT NULL COMMENT '转发的原帖id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS pimage;
CREATE TABLE `pimage` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `pid` VARCHAR(64) DEFAULT NULL COMMENT '帖子的id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS pvideo;
CREATE TABLE `pvideo` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `pid` VARCHAR(64) DEFAULT NULL COMMENT '帖子id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS commodity;
CREATE TABLE `commodity` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `price` VARCHAR(64) DEFAULT NULL COMMENT '商品价格',
    `content` VARCHAR(64) DEFAULT NULL COMMENT '商品内容',
    `effacicy` VARCHAR(64) DEFAULT NULL COMMENT '商品功效',
    `name` VARCHAR(64) DEFAULT NULL COMMENT '商品名称',
    `brand` VARCHAR(64) DEFAULT NULL COMMENT '商品品牌',
    `type` VARCHAR(64) DEFAULT NULL COMMENT '商品类型',
    `link` VARCHAR(64) DEFAULT NULL COMMENT '商品链接',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS cimage;
CREATE TABLE `cimage` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `cid` VARCHAR(64) DEFAULT NULL COMMENT '商品id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS cbrand;
CREATE TABLE `cbrand` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `name` VARCHAR(64) DEFAULT NULL COMMENT '品牌名称',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS effacicy;
CREATE TABLE `effacicy` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `name` VARCHAR(64) DEFAULT NULL COMMENT '商品功效名称',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS ctype;
CREATE TABLE `ctype` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `name` VARCHAR(64) DEFAULT NULL COMMENT '商品类型名称',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS myuser;
CREATE TABLE `myuser` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `avatar` VARCHAR(64) DEFAULT NULL COMMENT '头像id',
    `password` VARCHAR(64) DEFAULT NULL COMMENT '密码',
    `sex` VARCHAR(64) DEFAULT NULL COMMENT '性别',
    `nickname` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    `register_date` VARCHAR(64) DEFAULT NULL COMMENT '注册日期',
    `birthday` VARCHAR(64) DEFAULT NULL COMMENT '生日',
    `phone_number` VARCHAR(64) DEFAULT NULL COMMENT '电话号码',
    `login_name` VARCHAR(64) DEFAULT NULL COMMENT '登录名',
    `profile` VARCHAR(64) DEFAULT NULL COMMENT '简介',
    `mailbox` VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS relation;
CREATE TABLE `relation` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `follows` VARCHAR(64) DEFAULT NULL COMMENT '关注人id',
    `fans` VARCHAR(64) DEFAULT NULL COMMENT '粉丝id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;


drop table if EXISTS plikes;
CREATE TABLE `plikes` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `pid` VARCHAR(64) DEFAULT NULL COMMENT '帖子id',
    `uid` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS pfavorites;
CREATE TABLE `pfavorites` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `pid` VARCHAR(64) DEFAULT NULL COMMENT '帖子id',
    `uid` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS pcomments;
CREATE TABLE `pcomments` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `auid` VARCHAR(64) DEFAULT NULL COMMENT 'a用户id',
    `buid` VARCHAR(64) DEFAULT NULL COMMENT 'b用户id',
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `pid` VARCHAR(64) DEFAULT NULL COMMENT '帖子id',
    `content` VARCHAR(64) DEFAULT NULL COMMENT '评论内容',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS ccomments;
CREATE TABLE `ccomments` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `cid` VARCHAR(64) DEFAULT NULL COMMENT '商品id',
    `uid` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
    `content` VARCHAR(64) DEFAULT NULL COMMENT '评论内容',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS clikes;
CREATE TABLE `clikes` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `cid` VARCHAR(64) DEFAULT NULL COMMENT '商品id',
    `uid` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS cfavorites;
CREATE TABLE `cfavorites` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `time` VARCHAR(64) DEFAULT NULL COMMENT '发表时间',
    `cid` VARCHAR(64) DEFAULT NULL COMMENT '商品id',
    `uid` VARCHAR(64) DEFAULT NULL COMMENT '用户id',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

drop table if EXISTS myimg;
CREATE TABLE `myimg` (
`ID` VARCHAR(64) NOT NULL COMMENT '主键',
    PRIMARY KEY (`ID`),
    `course` VARCHAR(8000) DEFAULT NULL COMMENT '教程',
        `TENANT_ID` varchar(64) DEFAULT NULL,
        `DR` int(11) DEFAULT NULL COMMENT '是否删除',
        `TS` varchar(64) DEFAULT NULL COMMENT '时间戳',
        `LAST_MODIFIED` varchar(64) DEFAULT NULL COMMENT '修改时间',
        `LAST_MODIFY_USER` varchar(64) DEFAULT NULL COMMENT '修改人',
        `CREATE_TIME` varchar(64) DEFAULT NULL COMMENT '创建时间',
        `CREATE_USER` varchar(64) DEFAULT NULL COMMENT '创建人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

alter table pimage add constraint FK_Reference_1 foreign key (pid)
      references post (ID) on delete restrict on update restrict;

alter table pvideo add constraint FK_Reference_2 foreign key (pid)
      references post (ID) on delete restrict on update restrict;

alter table cimage add constraint FK_Reference_3 foreign key (cid)
      references commodity (ID) on delete restrict on update restrict;

alter table commodity add constraint FK_Reference_4 foreign key (brand)
      references cbrand (ID) on delete restrict on update restrict;

alter table commodity add constraint FK_Reference_5 foreign key (effacicy)
      references effacicy (ID) on delete restrict on update restrict;

alter table commodity add constraint FK_Reference_6 foreign key (type)
      references ctype (ID) on delete restrict on update restrict;

alter table post add constraint FK_Reference_7 foreign key (uid)
      references myuser (ID) on delete restrict on update restrict;

alter table relation add constraint FK_Reference_8 foreign key (fans)
      references myuser (ID) on delete restrict on update restrict;

alter table relation add constraint FK_Reference_9 foreign key (follows)
      references myuser (ID) on delete restrict on update restrict;

alter table plikes add constraint FK_Reference_10 foreign key (uid)
      references myuser (ID) on delete restrict on update restrict;

alter table plikes add constraint FK_Reference_11 foreign key (pid)
      references post (ID) on delete restrict on update restrict;

alter table pfavorites add constraint FK_Reference_12 foreign key (uid)
      references myuser (ID) on delete restrict on update restrict;

alter table pfavorites add constraint FK_Reference_13 foreign key (pid)
      references post (ID) on delete restrict on update restrict;

alter table pcomments add constraint FK_Reference_14 foreign key (auid)
      references myuser (ID) on delete restrict on update restrict;

alter table pcomments add constraint FK_Reference_15 foreign key (buid)
      references myuser (ID) on delete restrict on update restrict;

alter table pcomments add constraint FK_Reference_16 foreign key (pid)
      references post (ID) on delete restrict on update restrict;

alter table ccomments add constraint FK_Reference_17 foreign key (uid)
      references myuser (ID) on delete restrict on update restrict;

alter table ccomments add constraint FK_Reference_18 foreign key (cid)
      references commodity (ID) on delete restrict on update restrict;

alter table clikes add constraint FK_Reference_19 foreign key (uid)
      references myuser (ID) on delete restrict on update restrict;

alter table clikes add constraint FK_Reference_20 foreign key (cid)
      references commodity (ID) on delete restrict on update restrict;

alter table cfavorites add constraint FK_Reference_21 foreign key (uid)
      references myuser (ID) on delete restrict on update restrict;

alter table cfavorites add constraint FK_Reference_22 foreign key (cid)
      references commodity (ID) on delete restrict on update restrict;