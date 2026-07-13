# 基于 Spring Boot 的迷你电商后台管理系统设计与实现

## 课程名称：Java EE 高级技术
## 题 目：基于 Spring Boot 的迷你电商后台管理系统设计与实现
## 小组成员：学号-姓名，学号-姓名，学号-姓名
## 指导教师：罗辉黄
## 二级学院：大数据与计算机学院
## 提交日期：2026年6月28日

---

## 1. 系统功能概述

本系统是一个基于 Spring Boot 的企业级迷你电商后台管理系统，主要实现电商业务的后台管理功能。系统采用微服务架构思想的模块化设计，将整体业务划分为多个独立模块，便于开发和维护。

### 1.1 系统主要功能

系统主要包含以下核心功能：

- **用户认证与授权**：实现基于 Spring Security 的用户登录认证、权限管理、角色分配等功能
- **系统管理**：包括用户管理、角色管理、菜单管理、操作日志管理等系统级功能
- **商品管理**：实现商品的增删改查、分类管理、库存管理等功能
- **订单管理**：实现订单的创建、支付、发货、取消等全流程管理

### 1.2 系统模块划分

系统按功能划分为以下四个核心模块：

| 模块名称 | 功能描述 |
| :--- | :--- |
| **mini-shop-common** | 公共模块，提供通用实体、工具类、异常处理、返回结果封装等 |
| **mini-shop-system** | 系统模块，实现用户认证、权限管理、操作日志等系统功能 |
| **mini-shop-product** | 商品模块，实现商品管理和分类管理功能 |
| **mini-shop-order** | 订单模块，实现订单创建、支付、发货等业务逻辑 |
| **mini-shop-web** | 启动模块，整合所有子模块，配置数据源和视图解析 |

---

## 2. 知识点的应用

本系统综合应用了 Java EE 高级技术课程中的多个核心知识点：

### 2.1 Spring Boot 框架应用

- **自动配置机制**：利用 Spring Boot 的自动配置特性，简化数据源、MyBatis、Thymeleaf 等组件的配置
- **依赖注入**：使用 `@Autowired`、`@RequiredArgsConstructor` 等注解实现依赖注入
- **注解驱动开发**：通过 `@Controller`、`@Service`、`@Mapper` 等注解定义组件
- **全局异常处理**：使用 `@RestControllerAdvice` 和 `@ExceptionHandler` 实现统一异常处理

### 2.2 MyBatis 数据持久化

- **Mapper 接口映射**：定义 Mapper 接口，通过 XML 文件实现 SQL 映射
- **动态 SQL**：使用 `<if>`、`<where>`、`<set>` 等标签实现动态查询和更新
- **关联查询**：通过 JOIN 语句实现商品与分类的关联查询
- **逻辑删除**：通过 `del_flag` 字段实现数据的软删除

### 2.3 Spring Security 安全框架

- **认证流程**：实现 `UserDetailsService` 接口，从数据库加载用户信息
- **密码加密**：使用 `BCryptPasswordEncoder` 对密码进行加密存储
- **角色权限控制**：基于角色（Role）和权限（Authority）的细粒度访问控制
- **安全配置**：通过 `SecurityFilterChain` 配置认证规则和权限策略

### 2.4 AOP 面向切面编程

- **自定义注解**：定义 `@OperationLog` 注解标记需要记录日志的方法
- **切面设计**：使用 `@Aspect`、`@Before`、`@AfterReturning`、`@AfterThrowing` 实现操作日志记录
- **环绕通知**：通过 ThreadLocal 记录方法执行耗时

### 2.5 其他技术应用

- **分页查询**：集成 PageHelper 实现分页功能
- **事务管理**：使用 `@Transactional` 注解管理数据库事务
- **Thymeleaf 模板**：服务器端渲染页面视图
- **Lombok**：简化实体类代码，自动生成 getter/setter 方法

---

## 3. 项目结构

### 3.1 项目整体目录结构

```
javaee/
├── pom.xml                           # 父工程 Maven 配置
├── mini-shop-common/                 # 公共模块
│   └── src/main/java/com/minishop/common/
│       ├── dto/                      # 数据传输对象
│       │   └── LoginDTO.java
│       ├── entity/                   # 基础实体类
│       │   └── BaseEntity.java
│       ├── exception/                # 异常处理
│       │   └── BusinessException.java
│       ├── result/                   # 返回结果封装
│       │   ├── PageResult.java
│       │   └── Result.java
│       ├── util/                     # 工具类
│       │   ├── CurrentUser.java
│       │   └── SecurityUtils.java
│       ├── vo/                       # 视图对象
│       │   └── UserVO.java
│       └── aop/annotation/           # AOP 注解
│           └── OperationLog.java
├── mini-shop-system/                 # 系统模块
│   └── src/main/java/com/minishop/system/
│       ├── aop/                      # 切面实现
│       │   └── OperationLogAspect.java
│       ├── config/                   # 配置类
│       │   ├── DataInitializer.java
│       │   └── SecurityConfig.java
│       ├── controller/               # 控制器
│       │   ├── AuthController.java
│       │   ├── MenuController.java
│       │   ├── OperLogController.java
│       │   ├── RoleController.java
│       │   └── UserController.java
│       ├── domain/                   # 领域实体
│       │   ├── LoginUser.java
│       │   ├── SysMenu.java
│       │   ├── SysOperLog.java
│       │   ├── SysRole.java
│       │   ├── SysRoleMenu.java
│       │   ├── SysUser.java
│       │   └── SysUserRole.java
│       ├── mapper/                   # 数据访问层
│       │   ├── SysMenuMapper.java
│       │   ├── SysOperLogMapper.java
│       │   ├── SysRoleMapper.java
│       │   └── SysUserMapper.java
│       └── service/                  # 业务逻辑层
│           ├── impl/
│           │   └── UserDetailsServiceImpl.java
│           ├── MenuService.java
│           ├── OperLogService.java
│           ├── RoleService.java
│           └── UserService.java
├── mini-shop-product/                # 商品模块
│   └── src/main/java/com/minishop/product/
│       ├── controller/
│       │   ├── ProductCategoryController.java
│       │   └── ProductController.java
│       ├── domain/
│       │   ├── Product.java
│       │   └── ProductCategory.java
│       ├── mapper/
│       │   ├── ProductCategoryMapper.java
│       │   └── ProductMapper.java
│       ├── service/
│       │   ├── ProductCategoryService.java
│       │   └── ProductService.java
│       └── vo/
│           ├── CategoryWithProductsVO.java
│           └── ProductVO.java
├── mini-shop-order/                  # 订单模块
│   └── src/main/java/com/minishop/order/
│       ├── controller/
│       │   └── ShopOrderController.java
│       ├── domain/
│       │   ├── OrderItem.java
│       │   └── ShopOrder.java
│       ├── mapper/
│       │   ├── OrderItemMapper.java
│       │   └── ShopOrderMapper.java
│       ├── service/
│       │   └── ShopOrderService.java
│       └── vo/
│           ├── CreateOrderVO.java
│           └── OrderVO.java
└── mini-shop-web/                    # 启动模块
    └── src/main/
        ├── java/com/minishop/web/
        │   ├── config/
        │   │   └── WebMvcConfig.java
        │   ├── exception/
        │   │   └── GlobalExceptionHandler.java
        │   └── MiniShopApplication.java
        └── resources/
            ├── db/
            │   ├── schema.sql
            │   └── data.sql
            ├── static/
            │   ├── css/
            │   └── js/
            ├── templates/
            │   ├── login.html
            │   ├── order/list.html
            │   ├── product/list.html
            │   └── system/user.html
            └── application.yml
```

### 3.2 Maven 多模块依赖关系

```
mini-shop-web (启动模块)
    ├── mini-shop-common (公共模块)
    ├── mini-shop-system (系统模块)
    ├── mini-shop-product (商品模块)
    └── mini-shop-order (订单模块)
```

---

## 4. 系统需求

### 4.1 功能需求

#### 4.1.1 用户认证与授权

| 需求编号 | 需求描述 | 优先级 |
| :--- | :--- | :--- |
| REQ-AUTH-001 | 用户登录功能，支持用户名密码验证 | 高 |
| REQ-AUTH-002 | 用户退出功能，清除登录状态 | 高 |
| REQ-AUTH-003 | 获取当前登录用户信息 | 高 |
| REQ-AUTH-004 | 基于角色的访问控制 | 高 |
| REQ-AUTH-005 | 基于权限的细粒度控制 | 中 |

#### 4.1.2 系统管理

| 需求编号 | 需求描述 | 优先级 |
| :--- | :--- | :--- |
| REQ-SYS-001 | 用户列表查询，支持分页和条件筛选 | 高 |
| REQ-SYS-002 | 用户新增、修改、删除（逻辑删除） | 高 |
| REQ-SYS-003 | 角色管理（新增、修改、删除） | 中 |
| REQ-SYS-004 | 菜单管理（新增、修改、删除） | 中 |
| REQ-SYS-005 | 操作日志查询 | 中 |

#### 4.1.3 商品管理

| 需求编号 | 需求描述 | 优先级 |
| :--- | :--- | :--- |
| REQ-PROD-001 | 商品列表查询，支持分页和条件筛选 | 高 |
| REQ-PROD-002 | 商品新增、修改、删除（逻辑删除） | 高 |
| REQ-PROD-003 | 商品分类管理 | 高 |
| REQ-PROD-004 | 商品与分类关联查询 | 中 |

#### 4.1.4 订单管理

| 需求编号 | 需求描述 | 优先级 |
| :--- | :--- | :--- |
| REQ-ORDER-001 | 订单列表查询，支持分页 | 高 |
| REQ-ORDER-002 | 创建订单，包含商品明细 | 高 |
| REQ-ORDER-003 | 订单支付功能 | 高 |
| REQ-ORDER-004 | 订单状态更新（发货、完成、取消） | 中 |

### 4.2 非功能需求

| 需求类型 | 需求描述 |
| :--- | :--- |
| **性能需求** | 系统响应时间不超过2秒，支持并发用户数≥100 |
| **安全需求** | 密码加密存储，防止SQL注入，防止XSS攻击 |
| **可扩展性** | 采用模块化设计，支持模块独立部署和扩展 |
| **可维护性** | 代码结构清晰，遵循分层架构，便于后续维护和升级 |
| **可用性** | 系统7×24小时可用，数据库支持自动初始化 |

---

## 5. 系统模块设计

### 5.1 模块架构设计

系统采用经典的三层架构模式，分为表现层（Controller）、业务层（Service）和数据访问层（Mapper）。

```
┌─────────────────────────────────────────────────────────────┐
│                    表现层 (Controller)                       │
│  AuthController | UserController | ProductController       │
│  OrderController | MenuController | RoleController          │
├─────────────────────────────────────────────────────────────┤
│                    业务层 (Service)                          │
│  UserService | RoleService | MenuService | OperLogService   │
│  ProductService | ProductCategoryService | ShopOrderService │
├─────────────────────────────────────────────────────────────┤
│                    数据访问层 (Mapper)                       │
│  SysUserMapper | SysRoleMapper | SysMenuMapper              │
│  SysOperLogMapper | ProductMapper | ShopOrderMapper         │
├─────────────────────────────────────────────────────────────┤
│                      数据库 (MySQL)                          │
└─────────────────────────────────────────────────────────────┘
```

### 5.2 核心模块详细设计

#### 5.2.1 用户认证模块

**认证流程**：

```
用户登录请求 → AuthController.login()
    → AuthenticationManager.authenticate()
        → UserDetailsServiceImpl.loadUserByUsername()
            → 查询用户信息
            → 查询用户角色
            → 查询用户权限
            → 返回 LoginUser 对象
        → 密码验证 (BCryptPasswordEncoder)
    → 设置 SecurityContext
    → 返回登录成功
```

**关键类说明**：

| 类名 | 职责 | 核心方法 |
| :--- | :--- | :--- |
| `AuthController` | 处理认证请求 | `login()`、`info()`、`logout()` |
| `UserDetailsServiceImpl` | 加载用户详情 | `loadUserByUsername()` |
| `SecurityConfig` | 安全配置 | `filterChain()`、`passwordEncoder()` |
| `LoginUser` | 登录用户信息 | `getAuthorities()`、`getUserId()` |

#### 5.2.2 操作日志模块（AOP）

**切面设计**：

```
@OperationLog 注解标记 → OperationLogAspect 切面拦截
    → @Before：记录方法开始时间（ThreadLocal）
    → @AfterReturning：记录正常返回结果
    → @AfterThrowing：记录异常信息
    → handleLog()：组装日志数据并持久化
```

**关键类说明**：

| 类名 | 职责 | 核心方法 |
| :--- | :--- | :--- |
| `OperationLog` | 自定义注解 | 定义模块、操作、类型属性 |
| `OperationLogAspect` | 日志切面 | `doBefore()`、`doAfterReturning()`、`handleLog()` |

#### 5.2.3 商品管理模块

**业务流程**：

```
商品列表查询 → ProductController.list()
    → PageHelper.startPage()
    → ProductService.list()
    → ProductMapper.selectList()
    → 返回分页结果

商品新增 → ProductController.add()
    → ProductService.add()
        → 设置创建人、创建时间
        → ProductMapper.insert()
    → 返回成功
```

**关键类说明**：

| 类名 | 职责 | 核心方法 |
| :--- | :--- | :--- |
| `ProductController` | 商品管理API | `list()`、`add()`、`update()`、`delete()` |
| `ProductService` | 商品业务逻辑 | `list()`、`add()`、`update()`、`delete()` |
| `ProductMapper` | 商品数据访问 | `selectList()`、`insert()`、`update()`、`deleteById()` |

#### 5.2.4 订单管理模块

**业务流程**：

```
创建订单 → ShopOrderService.create()
    → 参数校验（商品列表非空）
    → 遍历商品：查询商品信息、校验库存
    → 计算订单总金额
    → 生成订单编号（UUID）
    → 插入订单主表
    → 批量插入订单明细
```

**关键类说明**：

| 类名 | 职责 | 核心方法 |
| :--- | :--- | :--- |
| `ShopOrderController` | 订单管理API | `list()`、`create()`、`pay()`、`delete()` |
| `ShopOrderService` | 订单业务逻辑 | `create()`、`pay()`、`update()`、`delete()` |
| `ShopOrderMapper` | 订单数据访问 | `selectList()`、`insert()`、`update()` |
| `OrderItemMapper` | 订单明细访问 | `batchInsert()`、`deleteByOrderId()` |

---

## 6. 数据库设计

### 6.1 数据库表结构

#### 表 6-1 sys_user（系统用户表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| user_id | BIGINT | 是 | 用户ID（主键，自增） |
| username | VARCHAR(50) | 是 | 用户名（唯一） |
| password | VARCHAR(100) | 是 | 密码（BCrypt加密） |
| nickname | VARCHAR(50) | 否 | 昵称 |
| avatar | VARCHAR(200) | 否 | 头像 |
| status | TINYINT | 否 | 状态（0正常，1停用） |
| create_by | BIGINT | 否 | 创建者 |
| create_time | DATETIME | 否 | 创建时间 |
| update_by | BIGINT | 否 | 更新者 |
| update_time | DATETIME | 否 | 更新时间 |
| del_flag | TINYINT | 否 | 删除标志（0正常，1删除） |

#### 表 6-2 sys_role（角色表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| role_id | BIGINT | 是 | 角色ID（主键，自增） |
| role_name | VARCHAR(50) | 是 | 角色名称 |
| role_key | VARCHAR(50) | 是 | 角色权限字符串 |
| role_sort | INT | 否 | 显示顺序 |
| status | TINYINT | 否 | 状态（0正常，1停用） |
| create_time | DATETIME | 否 | 创建时间 |

#### 表 6-3 sys_menu（菜单表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| menu_id | BIGINT | 是 | 菜单ID（主键，自增） |
| menu_name | VARCHAR(50) | 是 | 菜单名称 |
| parent_id | BIGINT | 否 | 父菜单ID |
| order_num | INT | 否 | 显示顺序 |
| path | VARCHAR(200) | 否 | 路由地址 |
| component | VARCHAR(255) | 否 | 组件路径 |
| perms | VARCHAR(100) | 否 | 权限标识 |
| icon | VARCHAR(100) | 否 | 菜单图标 |
| menu_type | CHAR(1) | 否 | 菜单类型（M目录，C菜单，F按钮） |
| status | TINYINT | 否 | 状态（0正常，1停用） |
| create_time | DATETIME | 否 | 创建时间 |

#### 表 6-4 sys_user_role（用户角色关联表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| user_id | BIGINT | 是 | 用户ID（联合主键） |
| role_id | BIGINT | 是 | 角色ID（联合主键） |

#### 表 6-5 sys_role_menu（角色菜单关联表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| role_id | BIGINT | 是 | 角色ID（联合主键） |
| menu_id | BIGINT | 是 | 菜单ID（联合主键） |

#### 表 6-6 sys_oper_log（操作日志表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| oper_id | BIGINT | 是 | 日志ID（主键，自增） |
| title | VARCHAR(100) | 否 | 操作模块 |
| oper_type | TINYINT | 否 | 操作类型（0其它，1新增，2修改，3删除） |
| method | VARCHAR(500) | 否 | 请求方法 |
| request_method | VARCHAR(10) | 否 | 请求方式 |
| oper_name | VARCHAR(50) | 否 | 操作人员 |
| oper_url | VARCHAR(500) | 否 | 请求URL |
| oper_ip | VARCHAR(128) | 否 | 操作IP |
| oper_param | TEXT | 否 | 请求参数 |
| json_result | TEXT | 否 | 返回结果 |
| status | TINYINT | 否 | 操作状态（0正常，1异常） |
| error_msg | VARCHAR(2000) | 否 | 错误消息 |
| cost_time | BIGINT | 否 | 执行耗时 |
| create_time | DATETIME | 否 | 操作时间 |

#### 表 6-7 product_category（商品分类表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| category_id | BIGINT | 是 | 分类ID（主键，自增） |
| category_name | VARCHAR(100) | 是 | 分类名称 |
| parent_id | BIGINT | 否 | 父分类ID |
| sort | INT | 否 | 显示顺序 |
| status | TINYINT | 否 | 状态（0正常，1停用） |
| create_by | BIGINT | 否 | 创建者 |
| create_time | DATETIME | 否 | 创建时间 |
| update_by | BIGINT | 否 | 更新者 |
| update_time | DATETIME | 否 | 更新时间 |
| del_flag | TINYINT | 否 | 删除标志（0正常，1删除） |

#### 表 6-8 product（商品表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| product_id | BIGINT | 是 | 商品ID（主键，自增） |
| category_id | BIGINT | 是 | 分类ID |
| product_name | VARCHAR(200) | 是 | 商品名称 |
| price | DECIMAL(10,2) | 是 | 价格 |
| stock | INT | 是 | 库存 |
| image | VARCHAR(500) | 否 | 商品图片 |
| description | TEXT | 否 | 商品描述 |
| status | TINYINT | 否 | 状态（0上架，1下架） |
| create_by | BIGINT | 否 | 创建者 |
| create_time | DATETIME | 否 | 创建时间 |
| update_by | BIGINT | 否 | 更新者 |
| update_time | DATETIME | 否 | 更新时间 |
| del_flag | TINYINT | 否 | 删除标志（0正常，1删除） |

#### 表 6-9 shop_order（订单表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| order_id | BIGINT | 是 | 订单ID（主键，自增） |
| order_no | VARCHAR(64) | 是 | 订单编号（唯一） |
| user_id | BIGINT | 是 | 用户ID |
| total_amount | DECIMAL(12,2) | 是 | 订单总金额 |
| status | TINYINT | 否 | 状态（0待支付，1已支付，2已发货，3已完成，4已取消） |
| pay_type | TINYINT | 否 | 支付方式（1余额，2微信，3支付宝） |
| address | VARCHAR(500) | 否 | 收货地址 |
| remark | VARCHAR(500) | 否 | 备注 |
| create_time | DATETIME | 否 | 创建时间 |
| pay_time | DATETIME | 否 | 支付时间 |

#### 表 6-10 order_item（订单明细表）

| 字段英文名称 | 类型 | 是否必填 | 中文说明 |
| :--- | :--- | :--- | :--- |
| item_id | BIGINT | 是 | 明细ID（主键，自增） |
| order_id | BIGINT | 是 | 订单ID |
| product_id | BIGINT | 是 | 商品ID |
| product_name | VARCHAR(200) | 是 | 商品名称 |
| product_image | VARCHAR(500) | 否 | 商品图片 |
| price | DECIMAL(10,2) | 是 | 单价 |
| quantity | INT | 是 | 数量 |
| total_amount | DECIMAL(12,2) | 是 | 小计金额 |

### 6.2 实体关系图（ER图）

```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│   sys_user   │────▶│sys_user_role │◀────│   sys_role   │
│  (用户表)     │1:N  │  (用户角色)   │N:1   │  (角色表)     │
└──────────────┘     └──────────────┘     └──────────────┘
                                              │
                                              ▼
                                       ┌──────────────┐
                                       │sys_role_menu │
                                       │  (角色菜单)   │
                                       └──────────────┘
                                              │
                                              ▼
                                       ┌──────────────┐
                                       │  sys_menu    │
                                       │  (菜单表)     │
                                       └──────────────┘

┌──────────────┐     ┌──────────────┐
│product_cat   │────▶│   product    │
│ (商品分类)    │1:N  │   (商品表)    │
└──────────────┘     └──────────────┘
                          │
                          ▼
                   ┌──────────────┐     ┌──────────────┐
                   │ shop_order   │────▶│ order_item   │
                   │   (订单表)     │1:N  │  (订单明细)   │
                   └──────────────┘     └──────────────┘
                          │
                          ▲
                   ┌──────────────┐
                   │   sys_user   │
                   │  (用户表)     │
                   └──────────────┘
```

---

## 7. 实现效果

### 7.1 登录页面

登录页面是系统的入口，提供用户名和密码输入框，以及登录按钮。系统预置了两个用户：
- 管理员：用户名 `admin`，密码 `123456`
- 普通用户：用户名 `test`，密码 `123456`

### 7.2 商品管理

商品管理模块实现了以下功能：
- **商品列表查询**：支持分页、按商品名称搜索、按分类筛选、按状态筛选
- **商品详情查看**：查看商品的完整信息，包括分类名称
- **商品新增**：填写商品名称、分类、价格、库存、描述等信息
- **商品修改**：修改已有商品的信息
- **商品删除**：逻辑删除商品，保留数据完整性

### 7.3 订单管理

订单管理模块实现了以下功能：
- **订单列表查询**：查看所有订单，支持分页
- **订单创建**：选择商品、填写收货地址、创建订单
- **订单支付**：将订单状态从"待支付"更新为"已支付"
- **订单详情**：查看订单的商品明细

### 7.4 用户管理

用户管理模块实现了以下功能：
- **用户列表查询**：支持按用户名、昵称搜索，按状态筛选
- **用户新增**：创建新用户，密码自动加密存储
- **用户修改**：修改用户昵称、头像、状态等信息
- **用户删除**：逻辑删除用户

### 7.5 操作日志

操作日志模块记录了所有标记了 `@OperationLog` 注解的操作，包括：
- 操作模块和操作名称
- 请求方法和URL
- 操作人员和IP地址
- 请求参数和返回结果
- 操作状态和执行耗时

---

## 8. 总结

### 8.1 设计与实现过程总结

本系统基于 Spring Boot 框架，采用 Maven 多模块管理，实现了一个功能完整的迷你电商后台管理系统。在设计与实现过程中，我们遵循了以下原则：

**架构设计方面**：
- 采用经典的三层架构（Controller-Service-Mapper），职责清晰
- 使用模块化设计，将系统拆分为公共模块、系统模块、商品模块、订单模块和启动模块
- 每个模块独立开发、独立测试，便于团队协作和后续维护

**技术实现方面**：
- 使用 Spring Security 实现认证和授权，密码采用 BCrypt 加密
- 使用 MyBatis 实现数据持久化，支持动态 SQL 和关联查询
- 使用 AOP 实现操作日志记录，通过自定义注解标记需要记录的方法
- 使用 PageHelper 实现分页查询，提升用户体验

**代码质量方面**：
- 使用 Lombok 简化代码，减少样板代码
- 使用统一的返回结果封装（Result），便于前端处理
- 使用全局异常处理，统一异常响应格式

### 8.2 遇到的问题及解决方案

**问题1：Spring Security 认证流程配置复杂**

在配置 Spring Security 时，遇到了认证流程不清晰、权限配置不准确的问题。通过深入学习 Spring Security 的认证机制，理解了 `UserDetailsService`、`AuthenticationManager`、`PasswordEncoder` 等核心组件的作用，最终成功实现了基于数据库的用户认证和基于角色/权限的访问控制。

**问题2：AOP 切面获取请求参数困难**

在实现操作日志切面时，需要获取 Controller 方法的请求参数。通过使用 `JoinPoint.getArgs()` 获取方法参数，并结合 `ObjectMapper` 将参数序列化为 JSON 字符串，成功解决了参数获取问题。同时，使用 `ThreadLocal` 存储方法开始时间，准确计算方法执行耗时。

**问题3：订单创建的事务一致性**

订单创建涉及订单主表和订单明细表的插入，需要保证事务一致性。通过使用 `@Transactional(rollbackFor = Exception.class)` 注解，确保在任何步骤失败时都能回滚事务，保证数据完整性。同时，在创建订单前进行库存校验，避免超卖问题。

**问题4：逻辑删除的实现**

为了保留数据历史记录，系统采用逻辑删除策略。通过在实体类中添加 `del_flag` 字段，在所有查询中添加 `del_flag = 0` 条件，删除操作改为更新 `del_flag = 1`，成功实现了逻辑删除功能。

### 8.3 系统不足与改进方向

**系统不足**：
- 前端页面采用 Thymeleaf 模板，交互体验相对简单
- 缺乏缓存机制，频繁查询数据库影响性能
- 没有实现商品图片上传功能
- 缺乏数据导出功能

**改进方向**：
1. **前端升级**：使用 Vue.js 等现代前端框架，实现更丰富的交互效果
2. **缓存优化**：引入 Redis 缓存热点数据，减少数据库压力
3. **图片上传**：集成文件上传组件，实现商品图片的上传和管理
4. **数据导出**：支持 Excel 导出功能，方便数据统计和分析
5. **接口文档**：集成 Swagger 自动生成 API 文档
6. **单元测试**：增加单元测试和集成测试，提升代码质量

### 8.4 学习收获

通过本项目的开发，我们深入理解了 Spring Boot 框架的核心技术，掌握了 Spring Security、MyBatis、AOP 等关键技术的应用。同时，在团队协作过程中，我们学会了如何进行模块化开发、代码规范管理和问题排查。这些经验将对我们今后的软件开发工作产生积极影响。
