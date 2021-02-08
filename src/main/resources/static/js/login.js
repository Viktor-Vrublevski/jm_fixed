const formLogin = document.getElementById('login-page')
const errorLogin = document.getElementById('login-error')
const btn = document.getElementById('show-password')
let email = document.getElementById('email')
let password = document.getElementById('password')
let token = ''


formLogin.addEventListener('submit', (event) => {

    event.preventDefault()
    const data = {
        username: email.value.toString(),
        password: password.value.toString()
    }

    fetch('http://localhost:5557/api/auth/token', {
        method: 'post',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        }
    }).then(promise => {
            if (promise.status >= 200 && promise.status < 300) {
                return promise.json()
            } else {
                errorLogin.innerHTML = ''
                errorLogin.innerHTML = '<div class="alert alert-danger" role="alert">' +
                    'Введён неверный логин или пароль!</div>'
            }
        }
    ).then(response => {
        if (response !== null && response !== {}) {
            token = response.jwtType + '_' + response.jwtToken
            document.cookie = 'token=' + token + ';'
            window.location.href = '/site'
        } else {
            errorLogin.innerHTML = ''
            errorLogin.innerHTML = '<div class="alert alert-danger" role="alert">' +
                'Доступ к системе запрещён!</div>'
        }
    });
})

function showPassword() {
    if (password.type === "password") {
        password.type = "text";
        btn.innerHTML = ''
        btn.innerHTML = '&#x2606;'
    } else {
        password.type = "password";
        btn.innerHTML = ''
        btn.innerHTML = '&#x2605;'
    }
}




