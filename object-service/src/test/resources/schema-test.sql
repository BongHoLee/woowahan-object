DROP TABLE IF EXISTS shops;
CREATE TABLE shops(
                      shop_id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(150) NOT NULL              COMMENT '가게명',
                      open BIT NOT NULL                       COMMENT '영업중 여부',
                      min_order_amount INT NOT NULL           COMMENT '최소 주문 금액',
                      commission_rate FLOAT NOT NULL          COMMENT '수수료율',
                      commission INT NOT NULL DEFAULT 0       COMMENT '수수료'
);

DROP TABLE IF EXISTS menus;
CREATE TABLE menus(
                      menu_id INT PRIMARY KEY AUTO_INCREMENT,
                      shop_id INT NULL                        COMMENT '메뉴 ID',
                      food_name VARCHAR(200) NOT NULL         COMMENT '음식명',
                      food_description VARCHAR(500) NOT NULL  COMMENT '메뉴 설명',
                      food_price INT NOT NULL                 COMMENT '음식 기본 가격'
);

DROP TABLE IF EXISTS option_group_specs;
CREATE TABLE option_group_specs(
                                   option_group_spec_id INT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(100) NOT NULL              COMMENT '옵션그룹스펙명',
                                   menu_id INT NULL                        COMMENT '몌뉴 ID',
                                   exclusive BIT NOT NULL                  COMMENT '배타선택 여부',
                                   basic BIT NOT NULL                      COMMENT '기본 그룹 여부'
);

DROP TABLE IF EXISTS option_specs;
CREATE TABLE option_specs(
                             option_spec_id INT PRIMARY KEY AUTO_INCREMENT,
                             option_group_spec_id INT NULL           COMMENT '옵션그룹스펙ID',
                             name VARCHAR(100) NOT NULL              COMMENT '옵션스펙명',
                             price INT NOT NULL                      COMMENT '가격'
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
                       order_id INT PRIMARY KEY AUTO_INCREMENT,
                       user_id INT NOT NULL                    COMMENT '사용자ID',
                       shop_id INT NOT NULL                    COMMENT '가게ID',
                       ordered_time TIMESTAMP NOT NULL         COMMENT '주문시간',
                       status VARCHAR(30) NOT NULL             COMMENT '배달상태'
);

DROP TABLE IF EXISTS order_line_items;
CREATE TABLE order_line_items(
                                 order_line_item_id INT PRIMARY KEY AUTO_INCREMENT,
                                 order_id INT NULL                       COMMENT '주문 ID',
                                 menu_id INT NOT NULL                    COMMENT '메뉴ID',
                                 food_name VARCHAR(200) NOT NULL         COMMENT '음식명',
                                 food_count INT NOT NULL                 COMMENT '갯수'
);

DROP TABLE IF EXISTS order_option_groups;
CREATE TABLE order_option_groups(
                                    order_option_group_id INT PRIMARY KEY AUTO_INCREMENT,
                                    order_line_item_id INT NULL             COMMENT '장바구니항목ID',
                                    name  VARCHAR(100) NOT NULL             COMMENT '이름'
);

DROP TABLE IF EXISTS order_options;
CREATE TABLE order_options(
                              order_option_group_id INT NOT NULL      COMMENT '주문옵션그룹ID',
                              name VARCHAR(100) NOT NULL              COMMENT '옵션스펙명',
                              price INT NOT NULL                      COMMENT '가격',
                              CONSTRAINT pk_order_options PRIMARY KEY (order_option_group_id, name, price)
);

DROP TABLE IF EXISTS deliveries;
CREATE TABLE deliveries(
                           delivery_id INT PRIMARY KEY AUTO_INCREMENT,
                           order_id INT NOT NULL                   COMMENT '주문ID',
                           status VARCHAR(30) NOT NULL             COMMENT '배송상태'
);