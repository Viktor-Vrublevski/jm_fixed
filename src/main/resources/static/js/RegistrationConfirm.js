$(document).ready(function (){
    const registrationConfirm = document.getElementById('registration-confirm')
    let token = (new URL(document.location)).searchParams;
    let query = 'http://localhost:5557/api/auth/reg/confirm?token=' + token.get('token');
    console.log(token)
    fetch(query)
        .then(promise =>  {
            if (promise.status >= 200 && promise.status < 300) {
                registrationConfirm.innerHTML = '<div class="alert alert-success " role="alert">' + 'Учетная запись подтверждена, вы будете перенаправлены на главную страницу сайта</div>'
                document.cookie = 'token=Bearer_' + token.get('token') + '; path=/;'
                setTimeout(function(){
                    location.href = 'http://localhost:5557/site';
                }, 3000);
            } else {
                registrationConfirm.innerHTML = '<div class="alert alert-danger" role="alert">' + 'Ошибка подтверждения учетной записи</div>'
            }
        }).catch( error => error.promise.then(message => console.log(message)));
})