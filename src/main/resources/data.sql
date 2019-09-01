INSERT INTO user
(username, password)
VALUES ('vvopaa', '{bcrypt}$2y$10$DQn7lgMzJ/KjSiGj3Y57ae9IzNUF0NzvCVgOpxwjzE9jNLnPeWg/y'),
('root', '{bcrypt}$2a$10$70YvTnHkcwX6YphCa.zI7O3QAqiqGbR.NHoJWnFFEkpv2lUWyKWxO');

INSERT INTO role
(name)
VALUES ('ROLE_USER'),('ROLE_MODERATOR'),('ROLE_ADMIN');

INSERT INTO user_role
(user_id, role_id)
VALUES (1,1),(1,2),(1,3),(2,1);

INSERT INTO custom_client_details
(client_id, client_secret, auto_approve, scoped, secret_required,
access_token_validity_seconds, refresh_token_validity_seconds)
VALUES
('vvopaa', '{bcrypt}$2a$10$/8GdHFrNB5xUEdXK50ZLzuE6vFEUuHJX4tkiihOuFbbV50oXBKJJi', 0, 0, 0, 20000, 20000);

INSERT INTO CUSTOM_CLIENT_DETAILS_SCOPE
VALUES
(1, 'read'),(1, 'write');

INSERT INTO CUSTOM_CLIENT_DETAILS_AUTHORIZED_GRANT_TYPES
VALUES
(1, 'password'),(1, 'refresh_token');

/*
  root - user pass
  dima - client pass
 */