
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();
const csrfToken = $("#csrfToken").val();
const createRoute = $("#createRoute").val();
const addRoute = $("#addRoute").val();
const deleteRoute = $("#deleteRoute").val();
// $("#contents").load("login2");
$("#contents").load(loginRoute);

function login() {
    const username = $("#loginName").val();
    const password = $("#loginPass").val();
    // $("#contents").load("/validate2?username=" + username + "&password=" + password); //when validate was a GET
    $.post(validateRoute,
                        {username, password, csrfToken},
                        data => { $("#contents").html(data);
                        });
};

function createUser() {
    const username = $("#createName").val();
    const password = $("#createPass").val();
//    $("#contents").load("/create2?username=" + username + "&password=" + password);
    $.post(createRoute,
                            {username, password, csrfToken},
                            data => { $("#contents").html(data);
                            });
}

//function deleteTask(index) {
//    $("#contents").load("/deleteTask2?index=" + index );

function deleteTask(index) {
    $.post(deleteRoute,
                                {index, csrfToken},
                                data => { $("#contents").html(data);
                                });
}

function addTask() {
    const task = $("#newTask").val();
    $("#contents").load("/addTask2?task=" + encodeURIComponent(task))
}