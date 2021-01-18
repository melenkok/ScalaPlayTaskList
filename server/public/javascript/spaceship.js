const createUser = $("#createUser").val();

// $("#contents").load("login2");
$("#contents").load(createUser);

function create() {
    const userId = $("#userId").val();
    const fullName = $("#fullName").val();
    $("#contents").load("/createSpace?userId=" + userId + "&fullName=" + fullName); //when validate was a GET
//    $.post(validateRoute,
//                        {username, password, csrfToken},
//                        data => { $("#contents").html(data);
//                        });
};