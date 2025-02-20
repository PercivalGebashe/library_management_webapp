export class LoginHandler {
    constructor() {
        this.loginForm = document.getElementById("loginForm");

        if (this.loginForm) {
            this.loginForm.addEventListener("submit", (event) => this.handleLogin(event));
        }
    }

    handleLogin(event) {
        event.preventDefault(); // Prevent default form submission

        const formData = {
            username: document.getElementById("username").value,
            password: document.getElementById("password").value
        };

        fetch(`/api/v1/auth/authenticate`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        })
            .then(response => {
                console.log(response);
                if (!response.ok) {
                    throw new Error("Login failed");
                }
                fetch("home") // Parse JSON if needed
            })
            .then(data => {
                console.log("Login successful", data);
                window.location.href = "/home"; // Redirect after successful login
            })
            .catch(error => {
                console.error("Error:", error);
                document.getElementById("error-msg").style.display = "block"; // Show error message
            });
    }
}

new LoginHandler();

