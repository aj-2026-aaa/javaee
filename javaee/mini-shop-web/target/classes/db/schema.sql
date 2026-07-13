-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    avatar VARCHAR(200) DEFAULT NULL COMMENT '头像',
    status TINYINT DEFAULT 0 COMMENT '状态 0正常 1停用',
    create_by BIGINT DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by BIGINT DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志 0正常 1删除',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_key VARCHAR(50) NOT NULL COMMENT '角色权限字符串',
    role_sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态 0正常 1停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    menu_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    path VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    component VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    perms VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    icon VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    menu_type CHAR(1) DEFAULT '' COMMENT '菜单类型 M目录 C菜单 F按钮',
    status TINYINT DEFAULT 0 COMMENT '状态 0正常 1停用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS sys_oper_log (
    oper_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    title VARCHAR(100) DEFAULT '' COMMENT '操作模块',
    oper_type TINYINT DEFAULT 0 COMMENT '操作类型 0其它 1新增 2修改 3删除',
    method VARCHAR(500) DEFAULT '' COMMENT '请求方法',
    request_method VARCHAR(10) DEFAULT '' COMMENT '请求方式',
    oper_name VARCHAR(50) DEFAULT '' COMMENT '操作人员',
    oper_url VARCHAR(500) DEFAULT '' COMMENT '请求URL',
    oper_ip VARCHAR(128) DEFAULT '' COMMENT '操作IP',
    oper_param TEXT COMMENT '请求参数',
    json_result TEXT COMMENT '返回结果',
    status TINYINT DEFAULT 0 COMMENT '操作状态 0正常 1异常',
    error_msg VARCHAR(2000) DEFAULT '' COMMENT '错误消息',
    cost_time BIGINT DEFAULT 0 COMMENT '执行耗时',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 商品分类表
CREATE TABLE IF NOT EXISTS product_category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态 0正常 1停用',
    create_by BIGINT DEFAULT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT DEFAULT NULL,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    del_flag TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 商品表
CREATE TABLE IF NOT EXISTS product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    stock INT NOT NULL DEFAULT 0 COMMENT '库存',
    image VARCHAR(500) DEFAULT NULL COMMENT '商品图片',
    description TEXT COMMENT '商品描述',
    status TINYINT DEFAULT 0 COMMENT '状态 0上架 1下架',
    create_by BIGINT DEFAULT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_by BIGINT DEFAULT NULL,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    del_flag TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 订单表
CREATE TABLE IF NOT EXISTS shop_order (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    order_no VARCHAR(64) NOT NULL COMMENT '订单编号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(12,2) NOT NULL COMMENT '订单总金额',
    status TINYINT DEFAULT 0 COMMENT '状态 0待支付 1已支付 2已发货 3已完成 4已取消',
    pay_type TINYINT DEFAULT 1 COMMENT '支付方式 1余额 2微信 3支付宝',
    address VARCHAR(500) DEFAULT '' COMMENT '收货地址',
    remark VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    pay_time DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单明细表
CREATE TABLE IF NOT EXISTS order_item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '明细ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    product_image VARCHAR(500) DEFAULT NULL COMMENT '商品图片',
    price DECIMAL(10,2) NOT NULL COMMENT '单价',
    quantity INT NOT NULL COMMENT '数量',
    total_amount DECIMAL(12,2) NOT NULL COMMENT '小计金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';
