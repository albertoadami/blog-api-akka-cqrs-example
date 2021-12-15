package it.adami.blog.http.json

case class CreateUserRequest(
    name: String,
    surname: String,
    email: String,
    password: String,
    gender: String
)
