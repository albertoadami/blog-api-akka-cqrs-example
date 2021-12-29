package it.adami.blog.http.json

case class CreateUserRequest(
    username: String,
    firstname: String,
    lastname: String,
    email: String,
    password: String,
    dateOfBirth: String,
    gender: String
)
