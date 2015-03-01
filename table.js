function createline() {
    var name = prompt("Введите название сноуборда:", "");
    var brand = prompt("Введите название бренда сноуборда:", "");
    var app = prompt("Введите стиль катания", "");

    var body = document.getElementById('table').getElementsByTagName('TBODY')[0];
    var line = document.createElement("TR");
    body.appendChild(line);

    var td1 = document.createElement("TD");
    td1.innerHTML = name;
    var td2 = document.createElement("TD");
    td2.innerHTML = brand;
    var td3 = document.createElement("TD");
    td3.innerHTML = app;
    var td4firstbutton = document.createElement("button");
    td4firstbutton.onclick = function () {
        deleteline(this);
    }
    td4firstbutton.innerHTML = "Удалить";
    var td4secondbutton = document.createElement("button");
    td4secondbutton.onclick = function () {
        checkline(this);
    }
    td4secondbutton.innerHTML = "Изменить";

    var xmlhttp = new XMLHttpRequest;
    xmlhttp.open('POST', '/table.html', true);
    xmlhttp.send("create;" + name + ";" + brand + ";" + app);
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4) {
            if (xmlhttp.status == 200) {
                var id = xmlhttp.responseText;
                var td0 = document.createElement("TD");
                td0.innerHTML = id;
                line.appendChild(td0);
                line.appendChild(td1);
                line.appendChild(td2);
                line.appendChild(td3);
                line.appendChild(td4firstbutton);
                line.appendChild(td4secondbutton);
            }
        }

    }
}
function deleteline(line){
        var i = line.parentNode.rowIndex;

    var row = document.getElementById('table').rows[i].cells;
    var xmlhttp = new XMLHttpRequest;
    xmlhttp.open('POST', '/table.html', true);
    xmlhttp.send("delete;"+row[0].innerHTML+";"+row[1].innerHTML+";"+row[2].innerHTML+";"+row[3].innerHTML);
    document.getElementById('table').deleteRow(i);
}
function checkline(line){
    var i = line.parentNode.rowIndex;
    var oldrow = document.getElementById('table').rows[i].cells;

    var name = prompt("Введите название сноуборда:", oldrow[1].innerHTML);
    var brand = prompt("Введите название бренда сноуборда:",oldrow[2].innerHTML);
    var app = prompt("Введите стиль катания",oldrow[3].innerHTML);
    if(name != null && brand != null && app != null){

        var id = oldrow[0].innerHTML;
        var td4firstbutton = document.createElement("button");
        td4firstbutton.onclick = function() {deleteline(this);}
        td4firstbutton.innerHTML = "Удалить";
        var td4secondbutton = document.createElement("button");
        td4secondbutton.onclick = function() {checkline(this);}
        td4secondbutton.innerHTML = "Изменить";


        document.getElementById('table').deleteRow(i);
        var newrow = document.getElementById('table').insertRow(i);
        newrow.insertCell(0).innerHTML = id;
        newrow.insertCell(1).innerHTML = name;
        newrow.insertCell(2).innerHTML = brand;
        newrow.insertCell(3).innerHTML = app;
        newrow.appendChild(td4firstbutton);
        newrow.appendChild(td4secondbutton);

        var xmlhttp = new XMLHttpRequest;
        xmlhttp.open('POST', '/table.html', true);
        xmlhttp.send("check;"+id+";"+name+";"+brand+";"+app);
    }
}