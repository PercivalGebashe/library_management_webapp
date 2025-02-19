document.addEventListener("DOMContentLoaded", () => {
    loadBooks(); // Fetch and populate books when the page loads
    document.getElementById("editForm").addEventListener("submit", handleSubmit);
});

function loadBooks(page = 0) {
    fetch(`${apiUrl}?page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(data => {
            if (!data.content) {
                console.error("Unexpected API response:", data);
                return;
            }

            const tableBody = document.querySelector("tbody");
            tableBody.innerHTML = "";

            data.content.forEach(book => {
                const row = document.createElement("tr");
                row.dataset.id = book.isbn;
                row.innerHTML = `
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.authors}</td>
                    <td>${book.description}</td>
                    <td>${book.genres}</td>
                    <td>${book.publishedDate}</td>
                    <td>${book.publishers}</td>
                    <td>${book.isbn}</td>
                    <td><button class="edit-btn admin-only">Edit</button></td>
                </tr>`;
                tableBody.appendChild(row);
            });

            document.querySelectorAll(".edit-btn").forEach(button => {
                button.addEventListener("click", function (event) {
                    event.stopPropagation();
                    openEditModal(this.closest("tr"));
                });
            });
            updatePaginationControls(data);
        })
        .catch(error => console.error("Error loading books:", error));
}