-- --------------------------------------------------------
--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `gender` enum('Nam','Nữ') CHARACTER SET utf8 NOT NULL,
  `phone_number` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('Quản trị viên','Người kiểm duyệt','Người tạo đề thi','Người dùng') CHARACTER SET utf8 NOT NULL,
  `status` enum('Kích hoạt','Chưa kích hoạt') CHARACTER SET utf8 NOT NULL,
  `create_time` datetime NOT NULL,
  `birthday` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `verification_codes`
--

CREATE TABLE `verification_codes` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `token` varchar(45) NOT NULL,
  `type` varchar(30) NOT NULL,
  `expire_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `persistent_logins`
--

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `header_templates`
--

CREATE TABLE `header_templates` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `source_path` varchar(255) DEFAULT NULL,
  `create_date` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `questions`
--

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
  `content` longtext DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `question_type` varchar(255) DEFAULT NULL,
  `right_answer` varchar(255) DEFAULT NULL,
  `suggest` longtext DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `question_group`
--

CREATE TABLE `question_group` (
  `question_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `question_position` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test`
--

CREATE TABLE `test` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `num_of_question` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_date` date NOT NULL DEFAULT current_timestamp(),
  `header_template_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_question`
--

CREATE TABLE `test_question` (
  `question_id` bigint(20) NOT NULL,
  `test_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_feedbacks`
--

CREATE TABLE `test_feedbacks` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `test_question_question_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `test_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_request`
--

CREATE TABLE `user_request` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator_id` bigint(20) DEFAULT NULL,
  `view_person_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_username` (`username`);

--
-- Chỉ mục cho bảng `verification_codes`
--
ALTER TABLE `verification_codes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FR-USER_ID` (`user_id`);

--
-- Chỉ mục cho bảng `persistent_logins`
--
ALTER TABLE `persistent_logins`
  ADD PRIMARY KEY (`series`);

--
-- Chỉ mục cho bảng `header_templates`
--
ALTER TABLE `header_templates`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjoo8hp6d3gfwctr68dl2iaemj` (`user_id`);

--
-- Chỉ mục cho bảng `question_group`
--
ALTER TABLE `question_group`
  ADD PRIMARY KEY (`question_id`,`group_id`);

--
-- Chỉ mục cho bảng `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_users` (`user_id`) USING BTREE,
  ADD KEY `FK_header_templates` (`header_template_id`);

--
-- Chỉ mục cho bảng `test_question`
--
ALTER TABLE `test_question`
  ADD PRIMARY KEY (`question_id`),
  ADD KEY `FKk2sfq1wyx19uvwn7pkgk1bc9n` (`test_id`);

--
-- Chỉ mục cho bảng `test_feedbacks`
--
ALTER TABLE `test_feedbacks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd12iily7getg25bm0eg15vjj9` (`test_question_question_id`),
  ADD KEY `FK4g4mc84qwrwrhycx6rn0px8y` (`user_id`),
  ADD KEY `FKm1403l9frujfxb56708ts01xn` (`test_id`);

--
-- Chỉ mục cho bảng `user_request`
--
ALTER TABLE `user_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9adqmi2tb3jpsq90glf0y0e4k` (`user_id`),
  ADD KEY `FKe5srg7awgxo2e9f24mwewa3jq` (`creator_id`),
  ADD KEY `FKk6dciqmn868c7dk3l2gre795l` (`view_person_id`);

-- --------------------------------------------------------

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=411;

--
-- AUTO_INCREMENT cho bảng `verification_codes`
--
ALTER TABLE `verification_codes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `header_templates`
--
ALTER TABLE `header_templates`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `questions`
--
ALTER TABLE `questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `test`
--
ALTER TABLE `test`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `test_question`
--
ALTER TABLE `test_question`
  MODIFY `question_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `test_feedbacks`
--
ALTER TABLE `test_feedbacks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user_request`
--
ALTER TABLE `user_request`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

-- --------------------------------------------------------

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `verification_codes`
--
ALTER TABLE `verification_codes`
  ADD CONSTRAINT `verification_codes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `header_templates`
--
ALTER TABLE `header_templates`
  ADD CONSTRAINT `FKegyenvpeungft7o74vflbrv4s` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
--
-- Các ràng buộc cho bảng `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FKjoo8hp6d3gfwctr68dl2iaemj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `FK_header_templates` FOREIGN KEY (`header_template_id`) REFERENCES `header_templates` (`id`),
  ADD CONSTRAINT `FKdug99c3uufjv7wedmlt9euews` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `test_question`
--
ALTER TABLE `test_question`
  ADD CONSTRAINT `FK6mo59htonr4bwc0nqpeuts9x4` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  ADD CONSTRAINT `FKk2sfq1wyx19uvwn7pkgk1bc9n` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

--
-- Các ràng buộc cho bảng `test_feedbacks`
--
ALTER TABLE `test_feedbacks`
  ADD CONSTRAINT `FK4g4mc84qwrwrhycx6rn0px8y` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKd12iily7getg25bm0eg15vjj9` FOREIGN KEY (`test_question_question_id`) REFERENCES `test_question` (`question_id`),
  ADD CONSTRAINT `FKm1403l9frujfxb56708ts01xn` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

--
-- Các ràng buộc cho bảng `user_request`
--
ALTER TABLE `user_request`
  ADD CONSTRAINT `FK9adqmi2tb3jpsq90glf0y0e4k` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKe5srg7awgxo2e9f24mwewa3jq` FOREIGN KEY (`creator_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKk6dciqmn868c7dk3l2gre795l` FOREIGN KEY (`view_person_id`) REFERENCES `users` (`id`);
