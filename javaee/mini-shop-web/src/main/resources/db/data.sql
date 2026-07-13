-- 初始化用户（密码：123456，BCrypt加密）
REPLACE INTO sys_user (user_id, username, password, nickname, status, del_flag, create_time) VALUES
(1, 'admin', '$2a$10$7JB720yubVSofvwd/GZK3.PmsrV72mivYDuGCEy7iOhiCFSdBRqcu', '超级管理员', 0, 0, NOW()),
(2, 'test', '$2a$10$7JB720yubVSofvwd/GZK3.PmsrV72mivYDuGCEy7iOhiCFSdBRqcu', '测试用户', 0, 0, NOW());

-- 初始化角色
REPLACE INTO sys_role (role_id, role_name, role_key, role_sort, status, create_time) VALUES
(1, '超级管理员', 'admin', 1, 0, NOW()),
(2, '普通用户', 'user', 2, 0, NOW());

-- 初始化菜单
REPLACE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, perms, icon, menu_type, status, create_time) VALUES
(1, '系统管理', 0, 1, '/system', NULL, NULL, 'system', 'M', 0, NOW()),
(2, '用户管理', 1, 1, '/system/user', 'system/user/index', 'system:user:list', 'user', 'C', 0, NOW()),
(5, '商品管理', 0, 2, '/product', NULL, 'product:manage', 'shopping', 'M', 0, NOW()),
(6, '商品列表', 5, 1, '/product/list', 'product/index', 'product:list', 'edit', 'C', 0, NOW()),
(7, '分类管理', 5, 2, '/product/category', 'product/category', 'category:list', 'tree', 'C', 0, NOW()),
(8, '订单管理', 0, 3, '/order', NULL, 'order:manage', 'order', 'M', 0, NOW()),
(9, '订单列表', 8, 1, '/order/list', 'order/index', 'order:list', 'list', 'C', 0, NOW());

-- 用户角色关联
DELETE FROM sys_user_role WHERE user_id IN (1, 2);
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1), (2, 2);

-- 角色菜单关联（admin拥有所有菜单）
DELETE FROM sys_role_menu WHERE role_id IN (1, 2);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9),
(2, 5), (2, 6), (2, 7), (2, 8), (2, 9);

-- 初始化商品分类
REPLACE INTO product_category (category_id, category_name, parent_id, sort, status, del_flag, create_time) VALUES
(1, '数码电器', 0, 1, 0, 0, NOW()),
(2, '手机', 1, 1, 0, 0, NOW()),
(3, '电脑', 1, 2, 0, 0, NOW()),
(4, '家居生活', 0, 2, 0, 0, NOW()),
(5, '食品饮料', 0, 3, 0, 0, NOW());

-- 初始化商品
REPLACE INTO product (product_id, category_id, product_name, price, stock, image, description, status, del_flag, create_time) VALUES
(1, 2, 'iPhone 15 Pro', 7999.00, 100, '', 'Apple iPhone 15 Pro 256GB', 0, 0, NOW()),
(2, 2, '小米14', 3999.00, 200, '', '小米14 徕卡光学镜头', 0, 0, NOW()),
(3, 3, 'MacBook Pro 14', 14999.00, 50, '', 'Apple MacBook Pro 14英寸', 0, 0, NOW()),
(4, 4, '小米空气净化器', 899.00, 150, '', '小米空气净化器4 Pro', 0, 0, NOW()),
(5, 5, '可口可乐 330ml*24', 59.90, 500, '', '可口可乐整箱装', 0, 0, NOW());

-- 初始化订单
REPLACE INTO shop_order (order_id, order_no, user_id, total_amount, status, pay_type, address, remark, create_time, pay_time) VALUES
(1, 'ORD20260628001', 2, 7999.00, 1, 2, '北京市朝阳区xxx街道xxx号', '尽快发货', NOW(), NOW()),
(2, 'ORD20260628002', 2, 3999.00, 0, 1, '上海市浦东新区xxx路xxx号', '', NOW(), NULL),
(3, 'ORD20260628003', 2, 899.00, 2, 3, '广州市天河区xxx大道xxx号', '周末送货', NOW(), NOW()),
(4, 'ORD20260628004', 1, 14999.00, 3, 2, '深圳市南山区xxx科技园', '已签收', NOW(), NOW()),
(5, 'ORD20260628005', 2, 59.90, 1, 1, '杭州市西湖区xxx路xxx号', '', NOW(), NOW());

-- 初始化订单明细
REPLACE INTO order_item (item_id, order_id, product_id, product_name, product_image, price, quantity, total_amount) VALUES
(1, 1, 1, 'iPhone 15 Pro', '', 7999.00, 1, 7999.00),
(2, 2, 2, '小米14', '', 3999.00, 1, 3999.00),
(3, 3, 4, '小米空气净化器', '', 899.00, 1, 899.00),
(4, 4, 3, 'MacBook Pro 14', '', 14999.00, 1, 14999.00),
(5, 5, 5, '可口可乐 330ml*24', '', 59.90, 1, 59.90);
