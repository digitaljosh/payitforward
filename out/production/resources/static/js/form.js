//validation for name field
function handleName() {
    let value = this.value;
    if (value.length > 1) {
        displayValidField("nameGroup");
        } else {
        displayInvalidField("nameGroup");
        }
    }

//validation for email field
function handleEmail() {
    let value = this.value;
    if (isValidEmail(value)) {
    displayValidField("emailGroup");
    } else {
    displayInvalidField("emailGroup");
    }
}

function handleSubmit() {
    let

}

//changes field to green if correct
function displayValidField(id) {

     let e = document.getElementById(id);
     e.className = "has-success";
 }

//changes field to red if incorrect
function displayInvalidField(id) {

    let e = document.getElementById(id);
    e.className = "has-error";

    /*let elt = document.createElement("div");
    elt.className = "alert alert-danger";
    let text = document.createTextNode("Something went wrong!");
    elt.appendChild(text);
    e.appendChild(elt);*/

}

//validates email
function isValidEmail(str){
    let emailRegex = /\S+\@\S+\.\S+/;
    return emailRegex.test(str);
}

//wrapper function that gets called when the javascript file is called
(function() {

    let e = document.getElementById("nameField");
    e.addEventListener("change", handleName, false);

    e = document.getElementById("emailField");
    e.addEventListener("change", handleEmail, false);

    e.document,getElementById("submit");
    e.addEventListener("submit", handleSubmit, false);

})()