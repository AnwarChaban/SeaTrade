// Anwar und Miro haben diese JS gemacht 
function getInformation() {
    getCompany();
    getships();
}
function getCompany() {
    fetchAsync("http://localhost:8082/getCompany")
        .then(data => {
            const dataArray = data.split(';');
            let tbody = document.querySelector('#company-table tbody');
            Array.from(tbody.querySelectorAll('tr:not(:first-child)')).forEach(tr => tr.remove());

            // Create the tr and td elements
            dataArray.forEach(item => {
                let itemArray = item.split(','); 
                let tr = document.createElement('tr');

                itemArray.forEach((subItem)  => {
                    let td = document.createElement('td');
                    td.innerText = subItem; 
                    tr.appendChild(td);
                });

                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error(error));
}
 function getships() {
    fetchAsync("http://localhost:8082/getShips")
        .then(data => {
            const dataArray = data.split(';');
            let tbody = document.querySelector('#ship-table tbody');
            Array.from(tbody.querySelectorAll('tr:not(:first-child)')).forEach(tr => tr.remove());

            // Create the tr and td elements
            dataArray.forEach(item => {
                let itemArray = item.split(','); 
                let tr = document.createElement('tr');

                itemArray.forEach((subItem)  => {
                    let td = document.createElement('td');
                    td.innerText = subItem; 
                    tr.appendChild(td);
                });

                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error(error));
}
function fetchAsync(url) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", url);
        xhr.send();
        xhr.onload = () => {
            if (xhr.readyState == 4 && xhr.status == 200) {
                const data = xhr.response;
                resolve(data);
            } else {
                reject(`Error: ${xhr.status}`);
            }
        };
    });
}