#create database
create database `bank_question`;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `role` enum('Quản trị viên','Người kiểm duyệt','Người tạo đề thi','Người dùng') NOT NULL,
  `status` enum('Kích hoạt','Chưa kích hoạt') NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` enum('Nam','Nữ') NOT NULL,
  `birthday` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `questions` (
  `id` bigint(20) NOT NULL,
  `answer_a` varchar(255) DEFAULT NULL,
  `answer_b` varchar(255) DEFAULT NULL,
  `answer_c` varchar(255) DEFAULT NULL,
  `answer_d` varchar(255) DEFAULT NULL,
  `answer_e` varchar(255) DEFAULT NULL,
  `answer_f` varchar(255) DEFAULT NULL,
  `answer_g` varchar(255) DEFAULT NULL,
  `answer_h` varchar(255) DEFAULT NULL,
  `answer_i` varchar(255) DEFAULT NULL,
  `answer_j` varchar(255) DEFAULT NULL,
  `answer_permutation` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `question_type` varchar(255) DEFAULT NULL,
  `right_answer` varchar(255) DEFAULT NULL,
  `suggest` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `question_group` (
  `question_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `question_position` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `test_feedbacks` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `test_question_question_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `test_question` (
  `question_id` bigint(20) NOT NULL,
  `test_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
  CREATE TABLE `header_templates` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `source_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `header_templates`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKegyenvpeungft7o74vflbrv4s` (`user_id`);

ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjoo8hp6d3gfwctr68dl2iaemj` (`user_id`);

ALTER TABLE `question_group`
  ADD PRIMARY KEY (`question_id`,`group_id`);

ALTER TABLE `test_feedbacks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd12iily7getg25bm0eg15vjj9` (`test_question_question_id`),
  ADD KEY `FK4g4mc84qwrwrhycx6rn0px8y` (`user_id`);

ALTER TABLE `test_question`
  ADD PRIMARY KEY (`question_id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_username` (`username`);

ALTER TABLE `header_templates`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE `questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `test_feedbacks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `test_question`
  MODIFY `question_id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `header_templates`
  ADD CONSTRAINT `FKegyenvpeungft7o74vflbrv4s` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `questions`
  ADD CONSTRAINT `FKjoo8hp6d3gfwctr68dl2iaemj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `test_feedbacks`
  ADD CONSTRAINT `FK4g4mc84qwrwrhycx6rn0px8y` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKd12iily7getg25bm0eg15vjj9` FOREIGN KEY (`test_question_question_id`) REFERENCES `test_question` (`question_id`);
  
INSERT INTO `users` (`id`, `username`, `password`, `first_name`, `last_name`, `create_time`, `email`, `phone_number`, `role`, `status`, `gender`, `birthday`) VALUES
(4, 'administration', '$2a$10$Lg5spqzVZuXvmV/44UXLb.SsktOcQqPmiBIzQBGUCdHA6tgNvWPNa', 'admin', 'admin', '2021-05-31 17:25:27', 'admin@gmail.com', NULL, 'Quản trị viên', 'Kích hoạt', 'Nam', '2000-01-31');

# Username: administration, password: 123456 

