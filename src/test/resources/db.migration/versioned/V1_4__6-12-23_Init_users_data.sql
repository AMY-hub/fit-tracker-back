INSERT INTO user_details (initial_weight, target_weight, height, gender)
    VALUES (
               100.0,
                80.0,
               'MALE',
                '186'
           );

INSERT INTO users (email,
                  firstname,
                  lastname,
                  password,
                  role_id,
                  created_at,
                  detail_id)
VALUES (
        'tester3@gmail.com',
        'Tester3',
        'Tester3',
        '$2a$10$uG8twZ/TJbG0HpDp4WGAmOUOzoHdjFi5M.O5RsspaHpTfAumnw44i',
        1,
        '2023-10-29 13:51:51.265646+03',
        1);