# Crypto Wallet
Application for building a cryptocurrency wallet

## Table of Contents
* [Introduction](#introduction)
* [Features](#features)
* [Technologies](#technologies)
* [Setup](#setup)

## Introduction
This project presents an application that allows you to create your cryptocurrency portfolio completely offline without connecting external wallets.
Connecting external wallets is dangerous and users often keep statistics of their assets in Excel.
Thanks to the application, we can easily build portfolios and view statistics.

When creating this application, I wanted to consolidate the technologies learned during the bootcamp.
Additionally, for my own application, I learned the basics of Spring Boot Security and JWT.
## Features
* User registration and login with JWT authentication
* When registering, the user is sent an e-mail that needs to be confirmed
* Password encryption using BCrypt
* Downloading external api
* Cryptocurrency search
* Creation of wallets
* Buying coins for wallets and selling them
* Logout mechanism

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Token (JWT)
* BCrypt
* Gradle
* MySQL database
* Hibernate
* Thymeleaf
* Lombok
* Mockito
* JUnit
* Swagger

## Setup
To get started with this project, you will need to have the fallowing installed on your local machine:
* JDK 17
* Gradle 8.2.1

To build and run project, fallow these steps:
* Clone the repository: 'git clone https://github.com/PawelKowalskiSD/project-crypto-wallet2023.git'
* Navigate to the project directory: cd project-crypto-wallet2023
* Add database "crypto_wallet_project" to MySQL
* Build the project: gradle build
* Run the project: ./gradlew bootRun

 The application will be available at: http://localhost:8080/swagger-ui/index.html#/