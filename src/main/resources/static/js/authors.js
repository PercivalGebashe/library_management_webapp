export default class Authors {
    constructor() {
        document.addEventListener("DOMContentLoaded", () => {
            this.fetchAuthors(); // Fetch authors dynamically

            // Hide admin-only features if the user is not an admin
            const userRole = document.body.getAttribute("data-role"); // Better than template literals
            if (userRole !== "ADMIN") {
                document.querySelectorAll(".admin-only").forEach(el => el.style.display = "none");
            }
        });
    }

    fetchAuthors() {
        fetch('/api/v1/authors')
            .then(response => response.json())
            .then(authors => {
                const authorDropdown = document.getElementById("filterAuthor");
                if (!authorDropdown) return;

                authorDropdown.innerHTML = `<option value="">Select an Author</option>`;
                authors.forEach(author => {
                    const option = document.createElement("option");
                    option.value = author.id;
                    option.textContent = author.name;
                    option.setAttribute("data-bio", author.bio); // Store bio for details view
                    authorDropdown.appendChild(option);
                });

                authorDropdown.addEventListener("change", function () {
                    const selectedAuthor = this.options[this.selectedIndex];
                    const authorBio = selectedAuthor.getAttribute("data-bio");
                    document.getElementById("authorDetails").innerHTML = authorBio ? `<p>${authorBio}</p>` : "";
                });
            })
            .catch(error => console.error("Error fetching authors:", error));
    }
}