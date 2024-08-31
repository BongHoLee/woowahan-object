insert into shops(name, open, min_order_amount, commission_rate, commission) values('오겹돼지', 1, 13000, 0.01, 0);

insert into menus(shop_id, food_name, food_price, food_description) values(1, '삼겹살 1인세트', 12000, '삼겹살 + 야채세트 + 김치찌개 + 공기밥1개');

insert into option_group_specs(menu_id, name, exclusive, basic) values(1, '기본', 1, 1);
insert into option_specs(option_group_spec_id, name, price) values(1, '소(250g)', 12000);
insert into option_specs(option_group_spec_id, name, price) values(1, '중(400g)', 16000);
insert into option_specs(option_group_spec_id, name, price) values(1, '대(600g)', 20000);

insert into option_group_specs(menu_id, name, exclusive, basic) values(1, '맛선택', 0, 0);
insert into option_specs(option_group_spec_id, name, price) values(2, '일반 맛', 0);
insert into option_specs(option_group_spec_id, name, price) values(2, '매콤 맛', 1000);

insert into option_group_specs(menu_id, name, exclusive, basic) values(1, '추가선택', 0, 0);
insert into option_specs(option_group_spec_id, name, price) values(3, '부추재래기 추가', 1000);
insert into option_specs(option_group_spec_id, name, price) values(3, '야채세트 추가', 1000);
insert into option_specs(option_group_spec_id, name, price) values(3, '고기(100g) 추가', 5000);