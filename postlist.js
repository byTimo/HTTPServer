window.onload = function postlist(){
    var xmlhttp = new XMLHttpRequest;
    xmlhttp.open('POST', '/table.html', true);
    xmlhttp.send("size");
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                var size = xmlhttp.responseText;
                if(size >0){
                    var xmlhttp2 = new XMLHttpRequest;
                    xmlhttp2.open('POST', '/table.html', true);
                    xmlhttp2.send("list");
                    xmlhttp2.onreadystatechange = function() {
                        if (xmlhttp2.readyState == 4) {
                            if(xmlhttp2.status == 200) {
                                var list = xmlhttp2.responseText.split(";");
                                for(var i=0;i<size;i++)
                                {
                                    var line = list[i].split("$");
                                    var body = document.getElementById('table').getElementsByTagName('TBODY')[0];
                                    var row = document.createElement("TR");
                                    body.appendChild(row);
                                    var td0 = document.createElement("TD");
                                    td0.innerHTML = line[0];
                                    var td1 = document.createElement("TD");
                                    td1.innerHTML = line[1];
                                    var td2 = document.createElement("TD");
                                    td2.innerHTML = line[2];
                                    var td3 = document.createElement("TD");
                                    td3.innerHTML = line[3];
                                    var td4firstbutton = document.createElement("button");
                                    td4firstbutton.onclick = function() {deleteline(this);}
                                    td4firstbutton.innerHTML = "Удалить";
                                    var td4secondbutton = document.createElement("button");
                                    td4secondbutton.onclick = function() {checkline(this);}
                                    td4secondbutton.innerHTML = "Изменить";
                                    row.appendChild(td0);
                                    row.appendChild(td1);
                                    row.appendChild(td2);
                                    row.appendChild(td3);
                                    row.appendChild(td4firstbutton);
                                    row.appendChild(td4secondbutton);
                                }
                            }
                        }
                    }
                }
            }
        }
    };
}