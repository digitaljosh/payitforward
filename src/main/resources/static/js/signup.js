//validation for username field
function handleUsername() {
    let value = this.value;
    if (value.length >= 1 && value.length <= 25) {
        displayValidField("usernameGroup");
        theForm.usernameValid = true;
        checkForm();
        } else {
        displayInvalidField("usernameGroup");
        theForm.usernameValid = false;
        checkForm();
        }
        let g = document.getElementById("errorUsername")
        g.parentNode.removeChild(g)
    }

//validation for password field
function handlePassword() {
    let value = this.value;
    if (value.length >= 3 && value.length <= 15) {
        displayValidField("passwordGroup");
        theForm.passwordValid = true;
        checkForm();
        } else {
        displayInvalidField("passwordGroup");
        theForm.passwordValid = false;
        checkForm();
        }
        let g = document.getElementById("errorPassword")
        g.parentNode.removeChild(g)
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

//check for valid form
function checkForm() {
    if (theForm.usernameValid && theForm.passwordValid) {
        document.getElementById("submitButton").disabled = false;
    } else {
        document.getElementById("submitButton").disabled = true;
    }
}

//only submits form if valid
function submitForm() {
    if (theForm.usernameValid && theForm.passwordValid) {
        document.getElementById("signupForm").submit();
    } else {
        document.getElementById("signupForm").reset();
    }
}
//for form validation
let theForm = {
    usernameValid: false,
    passwordValid: false
};

//wrapper function that gets called when the javascript file is called
(function() {

    let e = document.getElementById("usernameField");
    e.addEventListener("keydown", handleUsername, false);

    e = document.getElementById("passwordField");
    e.addEventListener("keydown", handlePassword, false);

    })()