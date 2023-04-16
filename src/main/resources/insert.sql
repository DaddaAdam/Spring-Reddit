insert ignore into user(created, email, enabled, password, username) values (current_time(), 'admin@reddittest.com', 1, '$2a$10$UaWwzwAutpXl.dEMUHR64.vvT8xcR8Uha8Q/W4ZXHGFb6QRWNBMqW', 'admin');
insert ignore into user(created, email, enabled, password, username) values (current_time(), 'user@reddittest.com', 1, '$2a$10$UaWwzwAutpXl.dEMUHR64.vvT8xcR8Uha8Q/W4ZXHGFb6QRWNBMqW', 'user');
insert ignore into user(created, email, enabled, password, username) values (current_time(), 'user2@reddittest.com', 1, '$2a$10$UaWwzwAutpXl.dEMUHR64.vvT8xcR8Uha8Q/W4ZXHGFb6QRWNBMqW', 'user2');

insert ignore into subreddit(created_date, name, description, user_id) values (current_time(), 'java', 'Java subreddit', 1);
insert ignore into subreddit(created_date, name, description, user_id) values (current_time(), 'python', 'Python subreddit', 1);
insert ignore into subreddit(created_date, name, description, user_id) values (current_time(), 'javascript', 'Javascript subreddit', 1);