$(document).ready(function () {
    getPrincipal()
    getData();
});

var allRoles
var allUsers
var principal


//получение залогиненного пользователя
async function getPrincipal() {
    let jsonPrincipal = await fetch('/api/user');

    if (jsonPrincipal.ok) {
        principal = await jsonPrincipal.json();
    } else {
        alert("Ошибка HTTP: " + jsonPrincipal.status);
    }

    fillNavbar()
}

//оформление навбара
function fillNavbar() {
    $('#principal-username').append(principal.username)

    $('#principal-roles').append(principal.stringRoles)
}

//получение всех пользователей и всех ролей
async function getData(){
    let roles = await fetch('/api/roles');

    if (roles.ok) {
        allRoles = await roles.json();
    } else {
        alert("Ошибка HTTP: " + roles.status);
    }


    let users = await fetch('/api/users');

    if (users.ok) {
        allUsers = await users.json();
    } else {
        alert("Ошибка HTTP: " + users.status);
    }


    fillTable()
}

//заполнение таблицы админа

function fillTable() {
    $('.user-table-body').empty();

    $.each(allUsers, function(i, user) {
        $('<tr>').append(
            $('<td>').text(user.id),
            $('<td>').text(user.name),
            $('<td>').text(user.lastname),
            $('<td>').text(user.age),
            $('<td>').text(user.username),
            $('<td>').text((user.roles).map(role => role.shortRole).join(', ')),
            $('<td>').append($('<button>').text("Edit").attr({
                "type": "button",
                "id": "buttonEdit",
                "class": "btn btn-info",
                "data-bs-toggle": "modal",
                "data-bs-target": "#editModal",
            }).data("user", user)),
            $('<td>').append($('<button>').text("Delete").attr({
                "type": "button",
                "id": "buttonDelete",
                "class": "btn btn-danger",
                "data-bs-toggle": "modal",
                "data-bs-target": "#deleteModal",
            }).data("user", user))
        ).appendTo('.user-table-body')
    })
}


//конструирование модалки изменения пользователя

$(document).on('click', '#buttonEdit', function () {
    $(`#rolesToEdit`).empty()

    let user = $(this).data("user");
    $('#idToEdit').val(user.id)
    $('#nameToEdit').val(user.name)
    $('#lastNameToEdit').val(user.lastname)
    $('#ageToEdit').val(user.age)
    $('#usernameToEdit').val(user.username)
    $('#passwordToEdit').val(user.password)

    let selected = false

    $.each(allRoles, function(i, role) {

        $.each(user.roles, function (i, userRole) {
            if (userRole.role === role.role)
                selected = true
        })

        $(`#rolesToEdit`).append(
            $('<option>').text(role.shortRole).attr({
                "id": role.id,
                "value": role.role,
                "selected": selected,
            })
        )
        selected = false
    })
})

//запрос на изменение пользователя
$(document).on('click', '#submit-edit', async function () {

    let roles = [];
    $('#rolesToEdit option:selected').each(function(index, value) {
        roles[index] = {
            id: value.id,
            role: value.value
        }
    });

    const user = {
        id: $('#idToEdit').val(),
        name: $('#nameToEdit').val(),
        lastname: $('#lastNameToEdit').val(),
        age: $('#ageToEdit').val(),
        username: $('#usernameToEdit').val(),
        password: $('#passwordToEdit').val(),
        roles: roles
    };

    try {
        const response = await fetch('/api/change', {
            method: 'PATCH',
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        $('#button-close-edit-modal').click();
        await getData();

        const json = await response.json();
        console.log('Успех:', JSON.stringify(json));
    } catch (error) {
        console.error('Ошибка:', error);
    }
})



// конструирование модалки удаления пользователя

$(document).on('click', '#buttonDelete', function () {
    $(`#rolesToDelete`).empty()

    let user = $(this).data("user");
    $('#idToDelete').val(user.id)
    $('#nameToDelete').val(user.name)
    $('#lastNameToDelete').val(user.lastname)
    $('#ageToDelete').val(user.age)
    $('#usernameToDelete').val(user.username)


    $.each(user.roles, function (i, userRole) {

        $(`#rolesToDelete`).append(
            $('<option>').text(userRole.shortRole).attr({
                "id": userRole.id,
                "value": userRole.role,
                "selected": "true",
            })
        )
    })
})

//запрос на удаление пользователя
$(document).on('click', '#submit-delete', async function () {

    try {
        const response = await fetch('/api/delete/' + $('#idToDelete').val(), {
            method: 'DELETE',
        });

        $('#button-close-delete-modal').click();
        await getData();

        const json = await response.json();
        console.log('Успех:', JSON.stringify(json));
    } catch (error) {
        console.error('Ошибка:', error);
    }
})



//конструирование формы создания НОВОГО ЮЗЕРА

$(document).on('click', '#new-user-tab', function () {
    $(`#newUserRoles`).empty()
    $.each(allRoles, function(i, role) {
        $(`#newUserRoles`).append(
            $('<option>').text(role.shortRole).attr({
                "id": role.id,
                "value": role.role,
            })
        )
    })
})

//запрос на создание нового юзера
$(document).on('click', '#create-new-user', async function () {
    let roles = [];
    $('#newUserRoles option:selected').each(function(index, value) {
        roles[index] = {
            id: value.id,
            role: value.value
        }
    });

    const user = {
        name: $('#name').val(),
        lastname: $('#lastname').val(),
        age: $('#age').val(),
        username: $('#username').val(),
        password: $('#password').val(),
        roles: roles
    };

    try {
        const response = await fetch('/api/new', {
            method: 'POST',
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        $('#users-table-tab').click();
        await getData();


        const json = await response.json();
        console.log('Успех:', JSON.stringify(json));
    } catch (error) {
        console.error('Ошибка:', error);
    }
})


//таб панель с информацией о залогиненом пользователе
$(document).on('click', '#v-pills-profile-tab', function () {
    $('<tr>').append(
        $('<td>').text(principal.id),
        $('<td>').text(principal.name),
        $('<td>').text(principal.lastname),
        $('<td>').text(principal.age),
        $('<td>').text(principal.username),
        $('<td>').text((principal.roles).map(role => role.shortRole).join(', ')),
    ).appendTo('.principal-table-body')
})









