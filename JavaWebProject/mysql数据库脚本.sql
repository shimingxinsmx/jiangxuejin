-- phpMyAdmin SQL Dump
-- version 3.0.1.1
-- http://www.phpmyadmin.net
--
-- �������汾: 5.1.29
-- PHP �汾: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- --------------------------------------------------------

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a'); 

CREATE TABLE IF NOT EXISTS `t_userInfo` (
  `user_name` varchar(30)  NOT NULL COMMENT 'user_name',
  `password` varchar(30)  NOT NULL COMMENT '��¼����',
  `colleageObj` int(11) NOT NULL COMMENT '����ѧԺ',
  `name` varchar(20)  NOT NULL COMMENT '����',
  `gender` varchar(4)  NOT NULL COMMENT '�Ա�',
  `birthDate` varchar(20)  NULL COMMENT '��������',
  `userPhoto` varchar(60)  NOT NULL COMMENT 'ѧ����Ƭ',
  `telephone` varchar(20)  NOT NULL COMMENT '��ϵ�绰',
  `email` varchar(50)  NOT NULL COMMENT '����',
  `address` varchar(80)  NULL COMMENT '��ͥ��ַ',
  `regTime` varchar(20)  NULL COMMENT 'ע��ʱ��',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `t_colleage` (
  `collleageId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ѧԺid',
  `colleageName` varchar(20)  NOT NULL COMMENT 'ѧԺ����',
  `colleageMemo` varchar(800)  NULL COMMENT 'ѧԺ��ע',
  PRIMARY KEY (`collleageId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_fdy` (
  `fdyUserName` varchar(30)  NOT NULL COMMENT 'fdyUserName',
  `password` varchar(30)  NOT NULL COMMENT '��¼����',
  `name` varchar(20)  NOT NULL COMMENT '����',
  `gender` varchar(4)  NOT NULL COMMENT '�Ա�',
  `birthDate` varchar(20)  NULL COMMENT '��������',
  `telephone` varchar(20)  NOT NULL COMMENT '��ϵ�绰',
  `email` varchar(50)  NOT NULL COMMENT '����',
  `fdyMemo` varchar(800)  NULL COMMENT '����Ա��ע',
  PRIMARY KEY (`fdyUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `t_jxjType` (
  `typeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '���id',
  `typeName` varchar(20)  NOT NULL COMMENT '�������',
  `jxjMoney` float NOT NULL COMMENT '��ѧ����',
  `pdbz` varchar(800)  NOT NULL COMMENT '������׼',
  `addTime` varchar(20)  NULL COMMENT '���ʱ��',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_score` (
  `scoreId` int(11) NOT NULL AUTO_INCREMENT COMMENT '�ɼ�id',
  `termObj` int(11) NOT NULL COMMENT '����ѧ��',
  `userObj` varchar(30)  NOT NULL COMMENT 'ѧ��',
  `colleageObj` int(11) NOT NULL COMMENT '����ѧԺ',
  `zhcj` float NOT NULL COMMENT '�ۺϳɼ�',
  `scoreDesc` varchar(8000)  NOT NULL COMMENT '��ϸ�ɼ�',
  `scoreMemo` varchar(800)  NULL COMMENT '�ɼ���ע',
  PRIMARY KEY (`scoreId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_termInfo` (
  `termId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ѧ��id',
  `termName` varchar(20)  NOT NULL COMMENT 'ѧ������',
  PRIMARY KEY (`termId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_family` (
  `familyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '��¼id',
  `userObj` varchar(30)  NOT NULL COMMENT 'ѧ��',
  `familyDesc` varchar(8000)  NOT NULL COMMENT '��ͥ���',
  `updateTime` varchar(20)  NULL COMMENT '����ʱ��',
  PRIMARY KEY (`familyId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_jxj` (
  `jxjId` int(11) NOT NULL AUTO_INCREMENT COMMENT '��¼id',
  `jxjTypeObj` int(11) NOT NULL COMMENT '��ѧ������',
  `title` varchar(80)  NOT NULL COMMENT '�������',
  `content` varchar(800)  NOT NULL COMMENT '��������',
  `sqcl` varchar(60)  NOT NULL COMMENT '�������',
  `userObj` varchar(30)  NOT NULL COMMENT '����ѧ��',
  `fdyState` varchar(20)  NOT NULL COMMENT '����Ա���״̬',
  `fdyUserName` varchar(20)  NOT NULL COMMENT '��˵ĸ���Ա',
  `glState` varchar(20)  NOT NULL COMMENT '����Ա���״̬',
  `glResult` varchar(500)  NOT NULL COMMENT '����Ա��˽��',
  PRIMARY KEY (`jxjId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

ALTER TABLE t_userInfo ADD CONSTRAINT FOREIGN KEY (colleageObj) REFERENCES t_colleage(collleageId);
ALTER TABLE t_score ADD CONSTRAINT FOREIGN KEY (termObj) REFERENCES t_termInfo(termId);
ALTER TABLE t_score ADD CONSTRAINT FOREIGN KEY (userObj) REFERENCES t_userInfo(user_name);
ALTER TABLE t_score ADD CONSTRAINT FOREIGN KEY (colleageObj) REFERENCES t_colleage(collleageId);
ALTER TABLE t_family ADD CONSTRAINT FOREIGN KEY (userObj) REFERENCES t_userInfo(user_name);
ALTER TABLE t_jxj ADD CONSTRAINT FOREIGN KEY (jxjTypeObj) REFERENCES t_jxjType(typeId);
ALTER TABLE t_jxj ADD CONSTRAINT FOREIGN KEY (userObj) REFERENCES t_userInfo(user_name);


