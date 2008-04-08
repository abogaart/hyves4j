function handlePost(formId, returnId) {
    var x = "#" + returnId;
    jQuery.get("console", $('#' + formId).serialize(), function(data) {
        $(x).html(data);        
    });
}