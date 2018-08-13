CREATE DATABASE IF NOT EXISTS `crawl_jobs`;

USE `crawl_jobs`;

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int not null primary key auto_increment,
  `jobname` varchar(255) DEFAULT NULL,
  `process` varchar(255) DEFAULT NULL,
  `arguments` varchar(255) default null,
  `active` bit default true,
  `timeout_millis` int DEFAULT 0,
  `pause_millis` int DEFAULT 0,
  `update_on` timestamp null on update current_timestamp,
  `created_on` timestamp not null
);

CREATE TABLE IF NOT EXISTS `job_history` (
  `id` int not null primary key auto_increment,
  `jobid` int not null,
  `crawl_state` int default 0,
  `message` text default null,
  `update_on` timestamp null on update current_timestamp,
  `start_time` timestamp,
  `end_time` timestamp,
  `created_on` timestamp
);

ALTER TABLE `job_history`
ADD FOREIGN KEY (`jobid`) REFERENCES jobs(id);

-- insert into jobs(`jobname`, `process`, `arguments`, `timeout_millis`,`created_on`) values('NoException', 'java', 'NoException', 1000, now());
-- insert into jobs(`jobname`, `process`, `arguments`, `timeout_millis`, `created_on`) values('WithException', 'java', 'WithException', 1000, now());
-- insert into jobs(`jobname`, `process`, `arguments`, `timeout_millis`, `created_on`) values('WithTimeout', 'java', 'WithTimeout', 1000, now());
-- insert into jobs(`jobname`, `process`, `arguments`, `timeout_millis`, `created_on`) values('NoExceptionWithArgs', 'java', 'WithTimeout;', 1000, now());
