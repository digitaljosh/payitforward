//validation for displayName field
function handleDisplayName() {
    let value = this.value;
    if (value.length >= 1 && value.length <= 25) {
        displayValidField("displayNameGroup");
        theForm.displayNameValid = true;
        checkForm();
    } else {
        displayInvalidField("displayNameGroup");
        theForm.displayNameValid = false;
        checkForm();
    }
    let g = document.getElementById("errorDisplayName");
    g.parentNode.removeChild(g);
}


//validation for email field
function handleEmail() {
    let value = this.value;
    if (isValidEmail(value)) {
        displayValidField("emailGroup");
        theForm.emailValid = true;
        checkForm();
    } else {
        displayInvalidField("emailGroup");
        theForm.emailValid = false;
        checkForm();
    }
    let g = document.getElementById("errorEmail");
    g.parentNode.removeChild(g);
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

//check for valid form
function checkForm() {
    if (theForm.displayNameValid && theForm.emailValid) {
        document.getElementById("submitButton").disabled = false;
    } else {
        document.getElementById("submitButton").disabled = true;
    }
}

//only submits form if valid
function submitForm() {
    if (theForm.displayNameValid && theForm.emailValid) {
        document.getElementById("editForm").submit();
    } else {
        document.getElementById("editForm").reset();
    }
}

//for form validation
let theForm = {
    displayNameValid: false,
    emailValid: false
};

//wrapper function that gets called when the javascript file is called
(function() {

    let e = document.getElementById("emailField");
    e.addEventListener("keydown", handleEmail);

    e = document.getElementById("displayNameField");
    e.addEventListener("keydown", handleDisplayName);

})();