# Spring Reddit Clone
This is a Spring Boot application that clones the popular social media platform Reddit. It provides basic functionalities such as user registration, creating posts, upvoting and downvoting posts, and commenting on posts.

## Table of Contents

[Installation](#installation)

[Usage](#usage)

[Technologies used](#technologies-used)

[Contributing](#contributing)

[Acknowledgements](#acknowledgements)

## Installation
To run this application locally, you need to have Java and Maven installed on your machine. You can download Java from here and Maven from here.

Once you have Java and Maven installed, follow these steps:

1. Clone the repository
```bash
git clone https://github.com/DaddaAdam/Spring-Reddit.git
```

2. Navigate to the project directory
```bash
cd Spring-Reddit
```

3. For app to work locally services present in docker-compose.yml file must be running
```bash
# Docker required
docker-compose up -d
# Or
docker compose up -d
```

4. Run the application using Maven
```bash
mvn spring-boot:run
```

The application should now be running on http://localhost:8080.

## Usage
1. To use the application, you can access it through a web browser at http://localhost:8080.
2. Register a new user account by clicking on the "Sign Up" button on the top right corner of the page.
3. Once you have registered, you can log in using your username and password.
4. You can create a new post by clicking on the "Create Post" button on the top right corner of the page.
5. You can upvote or downvote a post by clicking on the upvote or downvote buttons next to the post.
6. You can comment on a post by clicking on the "Comment" button below the post and entering your comment.

## Technologies Used
This application was built using the following technologies:

>Spring Boot

>Spring Data JPA

>React

>MySQL

## Contributing
We welcome contributions from anyone who is interested in helping to improve this project. If you would like to contribute to this project, here are the steps to contribute.
1. Check the [issues section](https://github.com/DaddaAdam/Spring-Reddit/issues)
2. Fork the repository.
3. Create a new branch for your feature or bug fix.
4. Make your changes.
5. Test your changes.
6. Submit a pull request.

## Acknowledgements
This project was inspired by [Reddit](https://www.reddit.com/)

Thank you to the [Spring](https://spring.io/) community for their amazing documentation and resources.
