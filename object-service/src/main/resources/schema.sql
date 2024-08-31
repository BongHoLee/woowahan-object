DROP TABLE IF EXISTS food.SHOPS;
CREATE TABLE food.SHOPS(
                      SHOP_ID INT PRIMARY KEY AUTO_INCREMENT,
                      NAME VARCHAR(150) NOT NULL              COMMENT '가게명',
                      OPEN BIT NOT NULL                       COMMENT '영업중 여부',
                      MIN_ORDER_AMOUNT INT NOT NULL           COMMENT '최소 주문 금액',
                      COMMISSION_RATE FLOAT NOT NULL          COMMENT '수수료율',
                      COMMISSION INT NOT NULL DEFAULT 0       COMMENT '수수료'
);


DROP TABLE IF EXISTS food.MENUS;
CREATE TABLE food.MENUS(
                      MENU_ID INT PRIMARY KEY AUTO_INCREMENT,
                      SHOP_ID INT NULL                        COMMENT '메뉴 ID',
                      FOOD_NAME VARCHAR(200) NOT NULL         COMMENT '음식명',
                      FOOD_DESCRIPTION VARCHAR(500) NOT NULL  COMMENT '메뉴 설명',
                      FOOD_PRICE INT NOT NULL                 COMMENT '음식 기본 가격'
);

DROP TABLE IF EXISTS food.OPTION_GROUP_SPECS;
CREATE TABLE food.OPTION_GROUP_SPECS(
                                   OPTION_GROUP_SPEC_ID INT PRIMARY KEY AUTO_INCREMENT,
                                   NAME VARCHAR(100) NOT NULL              COMMENT '옵션그룹스펙명',
                                   MENU_ID INT NULL                        COMMENT '몌뉴 ID',
                                   EXCLUSIVE BIT NOT NULL                  COMMENT '배타선택 여부',
                                   BASIC BIT NOT NULL                      COMMENT '기본 그룹 여부'
);


DROP TABLE IF EXISTS food.OPTION_SPECS;
CREATE TABLE food.OPTION_SPECS(
                             OPTION_SPEC_ID INT PRIMARY KEY AUTO_INCREMENT,
                             OPTION_GROUP_SPEC_ID INT NULL           COMMENT '옵션그룹스펙ID',
                             NAME VARCHAR(100) NOT NULL              COMMENT '옵션스펙명',
                             PRICE INT NOT NULL                      COMMENT '가격'
);


DROP TABLE IF EXISTS food.ORDERS;
CREATE TABLE food.ORDERS(
                       ORDER_ID INT PRIMARY KEY AUTO_INCREMENT,
                       USER_ID INT NOT NULL                    COMMENT '사용자ID',
                       SHOP_ID INT NOT NULL                    COMMENT '가게ID',
                       ORDERED_TIME TIMESTAMP NOT NULL         COMMENT '주문시간',
                       STATUS VARCHAR(30) NOT NULL             COMMENT '배달상태'
);

DROP TABLE IF EXISTS food.ORDER_LINE_ITEMS;
CREATE TABLE food.ORDER_LINE_ITEMS(
                                 ORDER_LINE_ITEM_ID INT PRIMARY KEY AUTO_INCREMENT,
                                 ORDER_ID INT NULL                       COMMENT '주문 ID',
                                 MENU_ID INT NOT NULL                    COMMENT '메뉴ID',
                                 FOOD_NAME VARCHAR(200) NOT NULL         COMMENT '음식명',
                                 FOOD_COUNT INT NOT NULL                 COMMENT '갯수'
);


DROP TABLE IF EXISTS food.ORDER_OPTION_GROUPS;
CREATE TABLE food.ORDER_OPTION_GROUPS(
                                    ORDER_OPTION_GROUP_ID INT PRIMARY KEY AUTO_INCREMENT,
                                    ORDER_LINE_ITEM_ID INT NULL             COMMENT '장바구니항목ID',
                                    NAME  VARCHAR(100) NOT NULL             COMMENT '이름'
);


DROP TABLE IF EXISTS food.ORDER_OPTIONS;
CREATE TABLE food.ORDER_OPTIONS(
                              ORDER_OPTION_GROUP_ID INT NOT NULL      COMMENT '주문옵션그룹ID',
                              NAME VARCHAR(100) NOT NULL              COMMENT '옵션스펙명',
                              PRICE INT NOT NULL                      COMMENT '가격',
                              CONSTRAINT PK_ORDER_OPTIONS PRIMARY KEY (ORDER_OPTION_GROUP_ID, NAME, PRICE)
);


DROP TABLE IF EXISTS food.DELIVERIES;
CREATE TABLE food.DELIVERIES(
                           DELIVERY_ID INT PRIMARY KEY AUTO_INCREMENT,
                           ORDER_ID INT NOT NULL                   COMMENT '주문ID',
                           STATUS VARCHAR(30) NOT NULL             COMMENT '배송상태'
);
