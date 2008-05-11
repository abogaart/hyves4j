/**
 * Helper function that does an ajax-form-get and writes result in DOM
 */ 
function handlePost(formId, returnId) {
    var formData = serialize(document.getElementById(formId));
    get('console', formData, function(data) {
       document.getElementById(returnId).innerHTML = data;
    });
}

/**
 * Create cross-browser XmlHttpRequest
 */
function createXHR() 
{
    try {
        return new ActiveXObject('Msxml2.XMLHTTP');
    } catch (e) {}
    try {
        return new ActiveXObject('Microsoft.XMLHTTP');
    } catch (e) {}
    try {
        return new XMLHttpRequest();
    }
    catch (e) {} 

    return null;
}

/**
 * Do a get on specified url with data as request string, call func with result
 */
function get(url, data, func)
{
    var xhr = createXHR();
    xhr.onreadystatechange=function()
    { 
        if(xhr.readyState == 4)
        {
            if(xhr.status == 200)
            {
                if(func != null) func(xhr.responseText);
            }
        } 
    }; 

    xhr.open("GET", url + '?' + data , true);
    xhr.send(null); 
}

/**
 * Serialize a form into key=value&key2=value2 (etc.) format
 */
function serialize(formObj) {
    var returnStr = "";
    for (i=0; i<formObj.elements.length; i++) {
        var el = formObj.elements[i];
        switch(el.type) { 
            case "text": 
            case "hidden": 
            case "password": 
            case "textarea":
                returnStr += el.name + "=" + encodeURI(el.value) + "&";  
                break;
            case "checkbox":  
            case "radio":  
                if(el.checked) returnStr += el.name + "=" + encodeURI(el.value) + "&";  
                break;  
            case "select":  
                returnStr += el.name + "=" +  
                el.options[el.selectedIndex].value + "&";  
                break;      
        }             
    }
    return returnStr;
}