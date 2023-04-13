package com.example.mobile_cinema_lab1.usecases

class ValidateAuthDataUseCase {


    operator fun invoke(
        email: String,
        password: String,
        name: String? = null,
        surname: String? = null,
        passwordConfirmation: String? = null
    ): String {
        var validationAnswer = ""

        if (email.isBlank()) {
            validationAnswer += "Email не может быть пустым\n"
        }

        if (password.isBlank()) {
            validationAnswer += "Пароль не может быть пустым\n"
        }

        if (!email.isEmailValid()) {
            validationAnswer += "Неверный Email\n"
        }

        name?.let {  // means validation for register form  -->  name, surname, password exists
            if (it.isBlank()) {
                validationAnswer += "Имя не может быть пустым\n"
            }

            if (surname!!.isBlank()) {
                validationAnswer += "Фамилия не может быть пустой\n"
            }

            if (password != passwordConfirmation) {
                validationAnswer += "Пароли не совпадают\n"
            }
        }



        return validationAnswer
    }

    private fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches();
    }
}