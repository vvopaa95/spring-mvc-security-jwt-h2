INSERT INTO user
(username, password)
VALUES
('vvopaa', '$2y$12$8HcuW5Z9K8r.uNNxRIRMQe/nzKHdYpRXnIUjZbAmtifwBDSXb6M7G');

INSERT INTO user
(username, password)
VALUES('root', 'root');

INSERT INTO custom_client_details
(client_id, client_secret, auto_approve, scoped, secret_required)
VALUES ('vvopaa', '{noop}123', 0, 0, 0);