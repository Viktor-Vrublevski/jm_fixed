window.onload = function() {
    addListenersForTagBarElems('main','tracked')
    populateTagBar('main','tracked')
    addListenersForTagBarElems('main','ignored')
    populateTagBar('main','ignored')
    addListenersForTagBarElems('question-area','tracked')
    populateTagBar('question-area','tracked')
    addListenersForTagBarElems('question-area','ignored')
    populateTagBar('question-area','ignored')
}

// ----------------Functions------------------
function getCoords(elem) {
    let box = elem.getBoundingClientRect();

    return {
        bottom: box.bottom + pageYOffset,
        left: box.left + pageXOffset
    };
}

function sendRequest(requestMethod, url, tagType = null, pageName = null) {
    return fetch(url, {
        method: requestMethod,
        headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': $.cookie("token")
        })
    }).then(response =>{
        if (response.ok) {
            return response.json()
        } else if (response.status == 400) {
            document.getElementById('error-'+tagType+'-tag-'+pageName).style.display = 'block'
            document.getElementById('search-list-'+tagType+'-tag-'+pageName).style.display = 'none'
            document.getElementById('add-'+tagType+'-tag-'+pageName).value = ''
            return response.json()
        } else {
            let error = new Error();
            error.response = response.text();
            throw error;
        }
    }).catch(error => console.log(error.message))
}

function addListenersForTagBarElems(pageName, tagType) {
    const input = document.getElementById('add-'+tagType+'-tag-'+pageName)
    const searchList = document.getElementById('search-list-' + tagType + '-tag-' + pageName)
    const button = document.getElementById('button-add-'+tagType+'-tag-'+pageName)

    input.addEventListener("click",function(){
        searchList.setAttribute("style", "display: block; background: white; position: relative; width: 170px; max-height: 200px; top: 1px; left: 90px; box-shadow: -1px 2px 7px rgba(0,0,0,0.1);")
        let coordsSearchList = getCoords(searchList)
        let coordsInput = getCoords(input)
        searchList.style.top = (coordsInput.bottom - coordsSearchList.bottom+2)+"px"
        searchList.style.left = (coordsInput.left - coordsSearchList.left+91)+"px"

        //Закрытие выпадающего окна с тегами, при клике на область за пределами окна
        document.addEventListener('mouseup', function (){
            if(!searchList.isEqualNode(event.target)
                && !searchList.contains(event.target)
                && (!button.isEqualNode(event.target))) {
                searchList.style.display = "none"
                searchList.innerHTML=""
                input.value=""
                let errors = document.getElementsByClassName("error")
                console.log(errors)
                for (let elem of errors) {
                    if (elem.style.display == 'block') {
                        elem.style.display = 'none'
                    }
                }
            }
        })
    })

    input.oninput = function () {
        sendRequest('GET', "http://localhost:5557/api/tag/name?page=1&size=5&name=" + input.value)
            .then(response=> {
                searchList.innerHTML=""
                searchList.addEventListener("click",function (){input.value = event.target.id})
                response.items.forEach(elem => {
                    searchList.innerHTML += "<option class=\"dropdown-item\" id=\"" + elem.name + "\">" + elem.name + "</option>"
                })
            })
            .catch(err => console.log(err))
    }
    button.addEventListener('click', function (){
        sendRequest('POST', "http://localhost:5557/api/tag/"+tagType+"/add?name=" + input.value, tagType, pageName)
            .then( data => {
                populateTagBar(pageName, tagType)
                input.value=""
                searchList.setAttribute("style", "display: none;")
                searchList.innerHTML = ""
            })
            .catch(err=>console.log(err))
    })
}

function deleteTag(tagType) {
    event.target.parentNode.remove()
    sendRequest('DELETE', 'http://localhost:5557/api/tag/'+tagType+'/delete?tagId=' + event.target.dataset.id)
        .catch(err => console.log(err))
}

function populateTagBar(pageName, tagType) {
    sendRequest('GET', 'http://localhost:5557/api/tag/'+tagType)
        .then(response=> {
            document.getElementById('list-'+tagType+'-tag-'+pageName).innerHTML=""
            response.forEach(elem => {
                document.getElementById('list-'+tagType+'-tag-'+pageName).innerHTML +=
                    "<div class=\"mb-1\">" +
                    elem.name + " " +
                    "   <span class='close' data-id=\"" + elem.id + "\" onclick='deleteTag(\""+tagType+"\")' style='font-size: 100%; margin-left: 5px;'>X</span>\n" +
                    "</div>"
            })
        })
        .catch(err => console.log(err))
}


