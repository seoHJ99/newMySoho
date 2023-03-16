
USE soho_db;


DROP TABLE IF EXISTS member;
CREATE TABLE member (
	member_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 회원 번호
	member_ID VARCHAR(30) NOT NULL,						-- 회원 아이디
	member_PW VARCHAR(30) NOT NULL,						-- 회원 비밀번호
	member_NAME VARCHAR(20) NOT NULL,					-- 회원 이름
	member_MAIL VARCHAR(20) NOT NULL,	        -- 회원 이메일 ** 추가
	member_ADDRESS VARCHAR(100) NOT NULL,				-- 회원 주소
	member_POST VARCHAR(100) NOT NULL,					-- 회원 우편번호
	member_PHONE VARCHAR(20) NOT NULL,					-- 회원 전화번호
	member_SIGNUP DATE NOT NULL,						-- 회원 가입일
	member_POINT INT,									-- 회원 마일리지
	member_DROP CHAR(2) DEFAULT 0	-- 0:기본 1:탈퇴 2:정지			-- 회원 탈퇴여부 ***** 정지 추가
);

INSERT INTO member VALUES(null, 'qwe123', '123456', '이천수', 'qwe123@naver.com', '서울시 마포구 대흥동 123-10', '23154', '010-1234-1234', '2023-01-01', '300', '활동');
INSERT INTO member VALUES(null, 'qwe345', '123456', '이을용', 'qwe123@naver.com', '서울시 서대문구 북아현동 13-1', '23214', '010-1234-5678', '2023-01-03', '700', '활동');
INSERT INTO member VALUES(null, 'qwe676', '123456', '김남일', '서울시 마포구 대흥동 20-3', '65154', '010-4141-2325', '2023-01-05', '1997-03-24', '1200', '정지');
INSERT INTO member VALUES(null, 'qwe3534', '123456', '안정환', '서울시 마포구 아현동 102-10', '13154', '010-8705-3123', '2023-01-03', '1997-06-14', '100', '활동');
INSERT INTO member VALUES(null, 'qwe12312', '123456', '조원희', '서울시 마포구 대흥동 16-10', '22154', '010-3456-2342', '2023-01-02', '1997-02-22', '50', '탈퇴');

DROP TABLE IF EXISTS nonmember;
CREATE TABLE nonmember (
	nonmember_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 비회원 번호
	nonmember_NAME VARCHAR(20) NOT NULL,					-- 비회원 이름
	nonmember_PHONE VARCHAR(20) NOT NULL					-- 비회원 전화번호
);

INSERT INTO nonmember VALUES(null, '박지성', '010-8705-3123');
INSERT INTO nonmember VALUES(null, '박주영', '010-9999-3123');


DROP TABLE IF EXISTS item;
CREATE TABLE item (
	item_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 상품 번호
	item_CATEGORY1 VARCHAR(20) NOT NULL,      -- 상품 카테고리1  ** 추가
	item_CATEGORY2 VARCHAR(20) NOT NULL,      -- 상품 카테고리2  ** 추가
	item_NAME VARCHAR(20) NOT NULL,							-- 상품 이름
	item_PRICE INT NOT NULL,								-- 상품 가격
	item_DISCOUNT INT NOT NULL DEFAULT 0,					-- 상품 할인율
	item_ORIGINAL VARCHAR(10) NOT NULL,						-- 상품 색상  ** 상품 색상 => 원본 상품으로 변경
	item_AMOUNT INT NOT NULL,								-- 상품 재고량
	item_IMAGE TEXT NOT NULL,								-- 상품 사진
	item_DETAIL TEXT NOT NULL,								-- 상품 상세
	item_REGDATE DATE NOT NULL,								-- 상품 등록일
	item_SELL INT NOT NULL DEFAULT 0						-- 상품 판매량
);

INSERT INTO item VALUES (NULL, '원피스', '할인', '베이지원피스', '28000', '10', '베이지', '5', 'http://www.nate.com', '베이지원피스입니다~', '2023-01-03', DEFAULT);
INSERT INTO item VALUES (NULL, '하의', '행사', '아크네데님연청', '35000', '0', '블루', '2', 'http://www.naver.com', '연청바지입니다~', '2023-01-03', 10);

DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
	cart_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 장바구니 번호
	member_IDX INT,											-- 회원 번호(FK)
	item_IDX INT NOT NULL, -- 상품 번호(FK)
	nonmember_IDX INT,										-- 비회원 번호(FK)
	cart_QTY INT NOT NULL DEFAULT 1							-- 구매수량
);

INSERT INTO cart VALUES (NULL, '1', '1', NULL, 5);
INSERT INTO cart VALUES (NULL, null, '1', '1', 2);
SELECT * FROM cart;

DROP TABLE IF EXISTS coupon;
CREATE TABLE coupon (
	coupon_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 쿠폰 번호
	coupon_NAME VARCHAR(10),								-- 쿠폰 이름
	coupon_DISCOUNT INT DEFAULT '0',									-- 쿠폰 할인율
	coupon_PRICE INT DEFAULT '0',										-- 쿠폰 할인금액
	coupon_REGDATE DATE,									-- 쿠폰 등록일
	coupon_ENDDATE INT,										-- 쿠폰 기간
	coupon_USED CHAR(3) NOT NULL DEFAULT '미사용',      -- 쿠폰 사용여부
	member_IDX INT NOT NULL,								-- 회원 번호(FK)
	item_IDX INT NOT NULL									-- 상품 번호(FK)
);

INSERT INTO coupon VALUES (NULL, '생일축하쿠폰', '10', default, '2023-01-13', 30, DEFAULT, '1', '1');
SELECT * FROM coupon;


DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
	orders_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,			-- 주문 번호
	member_IDX VARCHAR(10),												-- 회원 번호(FK)
	nonmember_IDX INT,											-- 비회원 번호(FK)
   using_MILEAGE INT,                                  -- 사용 마일리지
   accumulate_MILEAGE INT,                              -- 적립 마일리지
   coupon_IDX INT,                                     -- 쿠폰
	orders_DATE DATE,											-- 주문 날짜
	senders_NAME VARCHAR(20) NOT NULL,							-- 보낸사람
	senders_PHONE VARCHAR(20) NOT NULL,							-- 보낸사람 전화번호
	orders_NAME VARCHAR(20) NOT NULL,							-- 수령자
	orders_PHONE VARCHAR(20) NOT NULL,							-- 수령자 전화번호
	orders_ADDRESS VARCHAR(100) NOT NULL,						-- 수령자 주소
	orders_POST VARCHAR(10) NOT NULL,							-- 수령자 우편번호
	orders_AMOUNT INT NOT NULL,									-- 주문 총 금액
	orders_PAYMENT VARCHAR(6) NOT NULL,  -- 0.무통장 1.카드 2.전화결제		-- 결제방식(예시입니다. 변동가능)
   orders_STATUS VARCHAR(4) NOT NULL DEFAULT '배송준비',	  -- 0.입금대기 1.배송준비 2.배송중 3.배송완료	-- 주문 상태
	orders_DELIVERYPAY int NOT NULL,  -- 0.무료 1.일반 2.특수			-- 배송비
   orders_DELIVERY_PS VARCHAR(50) DEFAULT '없음' -- 배달 요청사항
);
SELECT * FROM orders;

INSERT INTO orders VALUES (NULL, null, '1', 0, 0, 1, '2023-01-14', "박주영","010-0000-0000", "이천수", '010-1234-1234', '서울시 마포구 대흥동 123-10', '23154', 40000, '전화결제', '배송대기', 3000, default);
SELECT * FROM orders;

DROP TABLE IF EXISTS orders_detail;
CREATE TABLE orders_detail (
	odetail_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 주문상세 번호
	orders_IDX INT NOT NULL,									-- 주문 번호(FK)
	item_IDX INT NOT NULL,										-- 상품 번호(FK)
	odetail_QTY INT NOT NULL,									-- 상품별 구매수량
   odetail_STATUS VARCHAR(2) NOT NULL DEFAULT '기본'                 -- 물품 상태. 기본, 교환, 환불
);

INSERT INTO orders_detail VALUES (NULL, '1', '1', '2', default);
INSERT INTO orders_detail VALUES (NULL, '1', '2', '1', '교환');
SELECT * FROM orders_detail;

DROP TABLE review;
CREATE TABLE review (
	review_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,			-- 리뷰 번호
	orders_IDX INT NOT NULL,									-- 주문 번호(FK)
	item_IDX INT NOT NULL,										-- 상품 번호(FK)
	review_SCORE INT DEFAULT 0,								-- 리뷰 별점
	review_WRITER VARCHAR(30),									-- 리뷰 작성자
	review_TITLE VARCHAR(50) NOT NULL,							-- 리뷰 제목
	review_CONTENT TEXT NOT NULL,								-- 리뷰 내용
	review_IMAGE TEXT DEFAULT '이미지 없음',											-- 리뷰 사진
	review_REGDATE DATE NOT NULL,								-- 리뷰 등록날짜
	review_REPLY VARCHAR(500),                          -- 리뷰 답변
	review_STATUS CHAR(3)                                -- 리뷰 상태 공개, 비공개
);

INSERT INTO review VALUES (null, '3', '1', '3', null, '1234', '좋아요', '옷이 이쁘고 배송이 빨리와서 좋았어요', DEFAULT, '2023-01-14', NULL, '공개');
SELECT * FROM review;

DROP TABLE qna;
CREATE TABLE qna (
	qna_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,		-- 문의글 번호
	item_IDX INT,											-- 상품 번호(FK)
	qna_CATE CHAR(4) NOT NULL,		-- 0.상품 1.회원정보 			-- 문의글 카테고리
   qna_SORT VARCHAR(4) NOT NULL,                   -- 배송, 기타, 교환, 환불, 등등
	qna_WRITER VARCHAR(30),									-- 문의글 작성자0
	qna_PW VARCHAR(30) NOT NULL,							-- 작성자 비밀번호
	qna_TITLE VARCHAR(50) NOT NULL,							-- 문의글 제목
	qna_CONTENT TEXT NOT NULL,								-- 문의글 내용
   qna_REACT TEXT,                              -- 답변 내용
	qna_REGDATE DATE NOT NULL,								-- 문의글 등록일
      qna_ANSWERED VARCHAR(3) NOT NULL DEFAULT '미답변', -- 답변 여부
	qna_SECRET INT NOT NULL		-- 0.공개 1.비밀				-- 문의글 공개여부
); -- 기존 사이트가 이상해서 나중에

INSERT INTO qna VALUES (null, '1', '상품', '교환', '무야호', '1234', '교환문의', '사이즈가 너무 커서 바꾸고싶어요', NULL, '2023-01-16', DEFAULT, 1);
SELECT * FROM qna;

DROP TABLE admin;
CREATE TABLE admin (
	admin_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,	-- 관리자 번호
	admin_ID VARCHAR(30) NOT NULL,						-- 관리자 아이디
	admin_PW VARCHAR(30) NOT NULL,						-- 관리자 비밀번호
	admin_NAME VARCHAR(20) NOT NULL,					-- 관리자 이름
	admin_POW INT NOT NULL DEFAULT 1,					-- 관리자 권한
	admin_REGDATE DATE NOT NULL							-- 관리자 등록일
);

INSERT INTO admin VALUES (null, 'hong2002', '1234', '홍명보', 1, '2023-02-06');

DROP TABLE notice;
CREATE TABLE notice (
	notice_IDX INT NOT NULL PRIMARY KEY AUTO_INCREMENT,			-- 공지글 번호soho_db
	notice_CATE CHAR(3) NOT NULL,		-- 0.일반 1.이벤트 			-- 공지글 카테고리
	notice_WRITER CHAR(3) DEFAULT '운영자',                -- 작성자
	notice_TITLE VARCHAR(50) NOT NULL,							-- 공지글 제목
	notice_CONTENT TEXT NOT NULL,								-- 공지글 내용
	notice_FILE TEXT,											-- 공지글 첨부
   notice_REGTYPE CHAR(2) NOT NULL,               -- 공지 예약 여부
	notice_REGDATE DATETIME NOT NULL								-- 공지글 등록날짜	
); 

INSERT INTO notice VALUES (0, '일반', DEFAULT, '나다 운영자.', '안녕하냐', '첨부파일', '일반', '2023-02-06 00:00'  );


-- 일단 추가는 했으나 여러군데 손봐야할것이 있을것임. 알아두기