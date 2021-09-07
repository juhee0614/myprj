drop table member;
drop sequence member_id_seq;
create table member(
  id        number(8),
  email     varchar2(40),
  pw        varchar2(10) constraint member_pw_nn not null,
  tel       varchar2(13),
  nickname  varchar2(30),--한글 3byte
  gender    char(3),
  region    varchar2(30),
  birth     date,
  letter    char(1),
  fid       number(10),
  cdate     timestamp default systimestamp,
  udate     timestamp,
  constraint member_id_pk primary key(id),
  constraint member_email_uk unique (email),
  --constraint member_pw_nn not null ,
  constraint member_gender_ck check (gender in ('남','녀')),
  constraint member_letter_ck check (letter in ('1','0'))
);
create sequence member_id_seq;


drop table code;
create table code(
  code VARCHAR2(11),		--코드
  decode varchar2(30),		--코드명
  descript clob,			--코드설명
  pcode varchar2(11),		--상위코드
  use_yn char(1),			--사용여부
  cdate timestamp default systimestamp,
  udate timestamp,
  constraint code_code_pk primary key(code),
  constraint code_pcode_fk foreign key(code) references code,
  constraint code_decode_uk unique (decode),
  constraint code_use_yn_ck check(use_yn in('Y','N'))
);

insert into code(code,decode,pcode,use_yn) values ('A01','취미',NULL,'Y'); 
insert into code(code,decode,pcode,use_yn) values ('A02','성별',NULL,'Y');
insert into code(code,decode,pcode,use_yn) values ('A03','지역',NULL,'Y');
insert into code(code,decode,pcode,use_yn) values ('A04','첨부파일',NULL,'Y');
insert into code(code,decode,pcode,use_yn) values ('A05','게시판',NULL,'Y');
insert into code(code,decode,pcode,use_yn) values ('A0101','수영','A01','Y');
insert into code(code,decode,pcode,use_yn) values ('A0102','골프','A01','Y');
insert into code(code,decode,pcode,use_yn) values ('A0103','영화','A01','Y');
insert into code(code,decode,pcode,use_yn) values ('A0201','남','A02','Y');
insert into code(code,decode,pcode,use_yn) values ('A0202','여','A02','Y');
insert into code(code,decode,pcode,use_yn) values ('A0301','서울','A03','Y');
insert into code(code,decode,pcode,use_yn) values ('A0302','부산','A03','Y');
insert into code(code,decode,pcode,use_yn) values ('A0303','울산','A03','Y');
insert into code(code,decode,pcode,use_yn) values ('A0401','회원이미지','A04','Y');
insert into code(code,decode,pcode,use_yn) values ('A0402','상품이미지','A04','Y');
insert into code(code,decode,pcode,use_yn) values ('A0501','Spring','A05','Y');
insert into code(code,decode,pcode,use_yn) values ('A0502','DATABASE','A05','Y');
insert into code(code,decode,pcode,use_yn) values ('A0503','WEB Standard','A05','Y');
insert into code(code,decode,pcode,use_yn) values ('A0504','Q&A','A05','Y');

drop table hobby;
create table hobby (
  member_id number(8),
  code_code varchar2(11),
  constraint hobby_pk primary key(member_id,code_code)
);

drop table board;
create table board(
bnum          number(10),
bcategory     varchar2(11),
btitle        varchar2(150),
bid           number(8),
bemail        varchar2(40),
bnickname     varchar2(30),
bhit          number(5),
bcontent      clob,
pbnum         number(10),
bgroup        number(10),
bstep         number(3),
bindent       number(3),
status        char(1),
bcdate        timestamp default systimestamp,
budate        timestamp,
constraint board_bnum_pk primary key(bnum),
constraint board_bcategory_fk foreign key(bcategory) references code(code),
constraint board_bid_fk foreign key(bid) references member(id),
constraint board_bemail_fk foreign key(bemail) references member(email)
);

drop sequence board_bnum_seq;
create sequence board_bnum_seq
increment by 1
start with 1
maxvalue 999999999
nocycle;

drop table uploadfile;
create table uploadfile(
  fid number(10),
  rid varchar2(10) not null,
  code varchar2(11) not null,
  store_fname varchar2(50) not null,
  upload_fname varchar2(50) not null,
  fsize varchar2(45) not null,
  ftype varchar2(100) not null,
  cdate timestamp default systimestamp,
  udate timestamp,
  constraint uploadfile_fid_pk primary key(fid),
  --constraint uploadfile_rid_fk1 FOREIGN key(rid) references product,  
  constraint uploadfile_pid_fk2 FOREIGN key(code) references code
);
drop sequence uploadfile_fid_seq;
create sequence uploadfile_fid_seq
increment by 1 --증감치
start with 1 --시작값
maxvalue 9999999999 --최대값
nocycle;  --순환하지않음

commit;