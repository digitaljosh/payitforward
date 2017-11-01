//validation for displayName field
function handleDisplayName() {
    let value = this.value;
    if (value.length >= 2 && value.length <= 25) {
        displayValidField("displayNameGroup");
        } else {
        displayInvalidField("displayNameGroup");
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

//changes field to green if correct
function displayValidField(id) {

     let e = document.getElementById(id);
     e.className = "has-success";
 }

//changes field to red if incorrect
function displayInvalidField(id) {

    let e = document.getElementById(id);
    e.className = "has-error";
}

//validates email
function isValidEmail(str){
    let emailRegex = /\S+\@\S+\.\S+/;
    return emailRegex.test(str);
}

//wrapper function that gets called when the javascript file is called
(function() {

    e = document.getElementById("emailField");
    e.addEventListener("keydown", handleEmail, false);

    e = document.getElementById("displayNameField");
    e.addEventListener("keydown", handleDisplayName, false);

    e.document,getElementById("submit");
    e.addEventListener("submit", handleSubmit, false);

})()