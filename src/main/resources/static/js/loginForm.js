document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission

    const formData = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    fetch("/api/v1/auth/authenticate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData)
    })
        .then(response => {
            console.log(response)
            if (!response.ok) {
                throw new Error("Login failed");
            }
            // return response.json();
        })
        .then(data => {
            console.log(data)
            console.log("Login successful", data);
            window.location.href = "/home";
            // Redirect or handle login success
        })
        .catch(error => {
            console.error("Error:", error);
            document.getElementById("error-msg").style.display = "block";
        });
});