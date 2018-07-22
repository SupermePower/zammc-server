create table user_info
(
   user_id        	 varchar(50) not null comment '主键',
   nick_name      	 varchar(50) comment '昵称',
   avatar_url        varchar(200)  comment '头像',
   city       		 varchar(50)  comment '市/区',
   country           varchar(50)  comment '国家',
   gender            tinyint  comment '性别',
   language         varchar(50)  comment '语言',
   data_status       tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   province			 varchar(50)  comment '省',
   version           int not null comment '版本',
   primary key (user_id)
);

create table recharge_order
(
   order_id        	 bigint(20) not null comment '主键',
   user_id      	    varchar(50) not null comment '用户主键',
   recharge_money        decimal(10,2) not null comment '充值金额',
   pay_money       		 decimal(10,2) not null comment '支付金额',
   create_time           timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time           timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version               int not null comment '版本',
   data_status           tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   order_status			tinyint not null default 0 comment '数据状态:0-待支付；1-已支付',
   primary key (order_id)
);

create table recharge_package
(
   package_id        	 bigint(20) not null comment '主键',
   package_name      	 varchar(11) not null comment '套餐名称',
   recharge_money        decimal(10,2) not null comment '充值金额',
   pay_money       		 decimal(10,2) not null comment '支付金额',
   create_time           timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time           timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version               int not null comment '版本',
   data_status           tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   primary key (package_id)
);

create table restaurant
(
   restaurant_id         bigint(20) not null comment '主键',
   restaurant_name       varchar(11) not null comment '商店名称',
   restaurant_img        varchar(100) not null comment '商店图片',
   restaurant_code       varchar(20) not null comment '商店编码',
   create_time           timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time           timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version               int not null comment '版本',
   data_status           tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   summary				 varchar(200) comment '备注信息',
   primary key (restaurant_id)
);


create table reserve
(
   reserve_id            bigint(20) not null comment '主键',
   mobile                varchar(11) not null comment '联系电话',
   reserve_time          varchar(20) not null comment '预约时间',
   reserve_numbers       int not null comment '预约人数',
   real_name             varchar(10) not null comment '姓名',
   create_time           timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time           timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version               int not null comment '版本',
   data_status           tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   summary				 varchar(200) not null comment '备注信息',
   primary key (reserve_id)
);



create table banner
(
   banner_id            bigint(20) not null comment '主键',
   banner_name          varchar(50) not null comment 'banner名称',
   banner_url           varchar(100) not null comment 'banner图片地址',
   banner_status        tinyint not null default 0  comment 'banner状态：0-禁用；1-启用',
   banner_sort          int not null comment '显示顺序',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              int not null comment '版本',
   data_status          tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   primary key (banner_id)
);

create table notice
(
   notice_id            bigint(20) not null comment '主键',
   notice_name          varchar(50) not null comment 'notice名称',
   notice_url           varchar(100) not null comment 'notice图片地址',
   notice_status        tinyint not null default 0  comment 'notice状态：0-禁用；1-启用',
   notice_sort          int not null comment '显示顺序',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              int not null comment '版本',
   data_status          tinyint not null default 1 comment '数据状态:0-删除；1-正常',
   primary key (notice_id)
);

drop table if exists home_config;

/*==============================================================*/
/* Table: home_config                                           */
/*==============================================================*/
create table home_config
(
   id                   bigint(20) not null comment '主键',
   area_name            varchar(50) not null comment '区域名',
   area_tag             varchar(10) not null comment '区域标识',
   area_hight           int not null comment '区域高度',
   area_sort            int not null comment '区域顺序',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);


drop table if exists home_msg;

/*==============================================================*/
/* Table: home_msg                                              */
/*==============================================================*/
create table home_msg
(
   id                   bigint(20) not null comment '主键',
   name                 varchar(50) not null comment '区域名',
   img_url              varchar(200) not null comment '区域标识',
   context              varchar(500) not null comment '区域高度',
   sort                 int not null comment '区域顺序',
   area_tag             varchar(10) not null comment '顺序',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);

drop table if exists order_item;

/*==============================================================*/
/* Table: order_item                                            */
/*==============================================================*/
create table order_item
(
   item_id              bigint not null comment '主键',
   order_id             bigint not null comment '订单id',
   goods_id             bigint not null comment '商品id',
   goods_type           varchar(50) not null comment '商品类型（例如：中份，微辣）',
   goods_name           varchar(100) not null comment '商品名称',
   goods_num            int not null comment '商品数量',
   goods_price          decimal(10,2) not null comment '商品价格',
   real_price           decimal(10,2) not null comment '实际价格',
   subtotal             decimal(10,2) not null comment '小计',
   order_type           tinyint not null default 0 comment '订单类型（1预约，0店中，2外卖）',
   pay_time             timestamp comment '支付时间',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (item_id)
);


drop table if exists order_info;

/*==============================================================*/
/* Table: order_info                                            */
/*==============================================================*/
create table order_info
(
   order_id             bigint not null comment '主键',
   user_id              VARCHAR(50) comment '用户微信id',
   totalprice           decimal(6,2) comment '总计',
   item_id              bigint comment '订单项id',
   pay_status           tinyint comment '支付状态（0-未支付;1-已支付;2-已取消）',
   pay_way              varchar(100) comment '支付方式（全付，代金卷，微信+优惠卷）',
   pay_time             timestamp comment '支付时间',
   table_num            int comment '桌号',
   memo                 varchar(500) comment '备注',
   create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint comment '版本',
   data_status          tinyint comment '数据状态',
   primary key (order_id)
);

drop table if exists goods;

/*==============================================================*/
/* Table: goods                                                 */
/*==============================================================*/
create table goods
(
   goods_id             bigint not null comment '主键',
   goods_name           varchar(100) not null comment '商品名称',
   goods_img            varchar(200) not null comment '商品图片',
   goods_cate           bigint not null comment '商品分类',
   goods_type           varchar(50) not null default '0' comment '商品类型（0 单品，1 规格）',
   goods_price          decimal(4,2) not null default 0.0 comment '商品价格',
   goods_discount       decimal(4,2) not null default 0.0 comment '商品折扣',
   goods_status         tinyint not null default 0 comment '商品状态（0 在售  1下架）',
   goods_msg            varchar(500) comment '商品介绍',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null comment '版本',
   data_status          tinyint not null comment '数据状态',
   primary key (goods_id)
);

drop table if exists goods_cate;

/*==============================================================*/
/* Table: goods_cate                                            */
/*==============================================================*/
create table goods_cate
(
   cate_id              bigint not null comment '分类id',
   cate_icon            varchar(200) comment '图标',
   cate_name            varchar(100) comment '名称',
   sort                 int comment '顺序',
   status               tinyint default 0 comment '使用状态（0 禁用 1启用）',
   create_time          timestamp default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint comment '版本',
   data_status          tinyint comment '数据状态',
   primary key (cate_id)
);

drop table if exists goods_property;

/*==============================================================*/
/* Table: goods_property                                        */
/*==============================================================*/
create table goods_property
(
   id                   bigint not null comment '主键',
   goods_id             bigint not null comment '商品主键',
   property_name        varchar(100) not null comment '属性名称（规格，大小，温度等）',
   property_msg         varchar(100) not null comment '属性内容（微辣，小份，加冰）',
   property_status      tinyint not null default 0 comment '属性状态(0 可用，1不可用)',
   property_img         varchar(200) comment '属性图',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);

drop table if exists account_water;

/*==============================================================*/
/* Table: account_water                                         */
/*==============================================================*/
create table account_water
(
   water_id             bigint not null comment '流水id',
   user_id              VARCHAR(50) not null comment '用户',
   order_id             bigint,
   water_type           tinyint not null default 0 comment '流水类型(1 充值, 0消费,2代金券)',
   water_num            decimal(5,2) not null comment '流水数量',
   wanter_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '流水时间',
   memo                 varchar(100) comment '备注',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (water_id)
);

drop table if exists account;

/*==============================================================*/
/* Table: account                                               */
/*==============================================================*/
create table account
(
   id                   bigint not null comment '主键',
   userId                 bigint not null comment '用户',
   account_num          decimal(5,2) not null default 0.00 comment '账户余额',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);


drop table if exists coupon;

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   id                   bigint not null comment '主键',
   coupon_num           int not null comment '优惠券数量',
   coupon_type          tinyint not null default 0 comment '券类型（0优惠券 1代金券）',
   coupon_img           varchar(200) comment '卷图片',
   coupon_name           varchar(200) comment '卷名称',
   coupon_size          decimal(4,2) not null comment '优惠额度',
   coupon_condition     decimal(4,2) not null comment '优惠条件',
   coupon_msg           varchar(500) not null comment '优惠说明',
   coupon_time          timestamp comment '优惠截止时间',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);
drop table if exists coupon_user;

/*==============================================================*/
/* Table: coupon_user                                           */
/*==============================================================*/
create table coupon_user
(
   id                   bigint not null comment '主键',
   coupon_id            bigint not null comment '券id',
   user_id              VARCHAR(50) not null comment '用户主键',
   page_type            tinyint default 0 comment '券类型（0 优惠卷 1代金券）',
   use_time             timestamp comment '有效时间',
   used                 tinyint not null default 0 comment '是否可用',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);

drop table if exists discount;

/*==============================================================*/
/* Table: discount                                              */
/*==============================================================*/
create table discount
(
   id                   bigint not null comment '主键',
   discount_type        tinyint not null default 0 comment '类型（0 充值 1 付款）',
   discount_condition   decimal(4,2) not null default 0.00 comment '条件',
   discount_size        decimal(4,2) not null default 0.00 comment '额度',
   memo                 varchar(100),
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);


drop table if exists restaurant_property;

/*==============================================================*/
/* Table: restaurant_property                                   */
/*==============================================================*/
create table restaurant_property
(
   id                   bigint not null comment '主键',
   restaurant_id        bigint not null comment '餐馆主键',
   restaurant_property  varchar(200) not null default '0' comment '餐馆属性',
   property_img         varchar(200) not null comment '属性图片',
   property_msg         varchar(2000) not null comment '属性说明',
   sort                 int comment '顺序',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   version              tinyint not null default 0 comment '版本',
   data_status          tinyint not null default 0 comment '数据状态',
   primary key (id)
);


