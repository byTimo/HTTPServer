function sentPOST() {
    var content = document.getElementById("content").value;
    var xmlhttp = new XMLHttpRequest;
    xmlhttp.open('POST', '/task.html', true);
    xmlhttp.send("content=" + content);
    xmlhttp.onreadystatechange = function() {
      if (xmlhttp.readyState == 4) {
        if(xmlhttp.status == 200) {
          document.getElementById("sent").innerHTML = xmlhttp.responseText;
        }
      }
    };
  }