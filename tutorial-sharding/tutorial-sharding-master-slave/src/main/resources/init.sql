-- ----------------------------------------------------------------- master 库 -----------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `tutorial-sharding-master-slave-master`
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

use `tutorial-sharding-master-slave-master`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` VALUES ('1', '主库-张三');


-- ----------------------------------------------------------------- slave_0 库 -------------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS `tutorial-sharding-master-slave-slave_0`
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

use `tutorial-sharding-master-slave-slave_0`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` VALUES ('1', '从库0-张三');


-- ----------------------------------------------------------------- slave_1 库 -------------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS `tutorial-sharding-master-slave-slave_1`
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

use `tutorial-sharding-master-slave-slave_1`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` VALUES ('1', '从库1-张三');