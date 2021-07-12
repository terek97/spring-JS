async function getResponse() {
    let res = await fetch('http://localhost:8080/api/user')
    let content = await res.json()


    let list = document.querySelector('.user-table-body')
    let roles = "";
    for (let role in content.roles) {
        roles += `${content.roles[role].shortRole} `
    }

    list.innerHTML += `
        <td>${content.id}</td>
        <td>${content.name}</td>
        <td>${content.lastname}</td>
        <td>${content.age}</td>
        <td>${content.username}</td>
        <td>${roles}</td>
`
}

getResponse()