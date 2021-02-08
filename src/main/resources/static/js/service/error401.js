$( window ).bind('load',function() {
    var cookie = $.cookie("token");
    console.log(cookie)
    fetch('http://localhost:5557/api/auth/authenticated',{
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': cookie
        })
    })
        .then(response =>  {
            if (response.status === 401 && filterUrl() === false && window.location.href !== "http://localhost:5557/registration/**") {
                window.location.href = '/'
            } else if (response.status === 200 && filterUrl() === true) {
                window.location.href = '/site'
            }
        }).catch( error => error.response.then(message => console.log(message)));
})

function filterUrl(){
    if(window.location.href === "http://localhost:5557" || window.location.href === "http://localhost:5557/"
        || window.location.href === "http://localhost:5557/registration"){
        return true
    }
    return false;
}