insert ignore into user(created, email, enabled, password, username) values (current_time(), 'admin@reddittest.com', 1, '$2a$10$UaWwzwAutpXl.dEMUHR64.vvT8xcR8Uha8Q/W4ZXHGFb6QRWNBMqW', 'admin');
insert ignore into user(created, email, enabled, password, username) values (current_time(), 'user@reddittest.com', 1, '$2a$10$UaWwzwAutpXl.dEMUHR64.vvT8xcR8Uha8Q/W4ZXHGFb6QRWNBMqW', 'user');
insert ignore into user(created, email, enabled, password, username) values (current_time(), 'user2@reddittest.com', 1, '$2a$10$UaWwzwAutpXl.dEMUHR64.vvT8xcR8Uha8Q/W4ZXHGFb6QRWNBMqW', 'user2');

insert ignore into subreddit(created_date, name, description, user_id) values (current_time(), 'java', 'Java subreddit', 1);
insert ignore into subreddit(created_date, name, description, user_id) values (current_time(), 'python', 'Python subreddit', 1);
insert ignore into subreddit(created_date, name, description, user_id) values (current_time(), 'javascript', 'Javascript subreddit', 1);

insert ignore into post(created_date, description, post_name, url, vote_count, subreddit_id, user_id) values (current_time(), 'Java is a programming language', 'Java', 'https://www.java.com', 1, 1, 1);
insert ignore into post(created_date, description, post_name, url, vote_count, subreddit_id, user_id) values (current_time(), 'Python is a programming language', 'Python', 'https://www.python.org', 1, 2, 1);
insert ignore into post(created_date, description, post_name, url, vote_count, subreddit_id, user_id) values (current_time(), 'Javascript is a programming language', 'Javascript', 'https://www.javascript.com', 1, 3, 1);
insert ignore into post(created_date, description, post_name, url, vote_count, subreddit_id, user_id) values (current_time(), 'Java is a programming language', 'Java', 'https://www.java.com', 1, 1, 2);

insert ignore into vote(vote_type, post_id, user_id) values (1, 1, 1);
insert ignore into vote(vote_type, post_id, user_id) values (1, 1, 2);
insert ignore into vote(vote_type, post_id, user_id) values (-1, 1, 3);
insert ignore into vote(vote_type, post_id, user_id) values (1, 2, 2);
insert ignore into vote(vote_type, post_id, user_id) values (1, 3, 1);
insert ignore into vote(vote_type, post_id, user_id) values (1, 4, 1);
