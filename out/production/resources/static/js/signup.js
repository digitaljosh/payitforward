//validation for username field
function handleUsername() {
    let value = this.value;
    if (value.length > 2 && value.length < 16) {
        displayValidField("usernameGroup");
        } else {
        displayInvalidField("usernameGroup");
        }
        let g = document.getElementById("errorUsername")
        g.parentNode.removeChild(g)
    }

//validation for password field
function handlePassword() {
    let value = this.value;
    if (value.length > 2 && value.length < 16) {
        displayValidField("passwordGroup");
        } else {
        displayInvalidField("passwordGroup");
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

//wrapper function that gets called when the javascript file is called
(function() {

    let e = document.getElementById("usernameField");
    e.addEventListener("change", handleUsername, false);

    e = document.getElementById("passwordField");
    e.addEventListener("change", handlePassword, false);

    e.document,getElementById("submit");
    e.addEventListener("submit", handleSubmit, false);

    })()