export class BookManager {
    constructor(apiUrl, pageSize = 10) {
        this.apiUrl = apiUrl;
        this.pageSize = pageSize;

        document.addEventListener("DOMContentLoaded", () => {
            this.loadBooks();
            document.getElementById("editForm").addEventListener("submit", (event) => this.handleSubmit(event));
        });
    }

    loadBooks(page = 0) {
        fetch(`${this.apiUrl}?page=${page}&size=${this.pageSize}`)
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
                    button.addEventListener("click", (event) => {
                        event.stopPropagation();
                        this.openEditModal(event.target.closest("tr"));
                    });
                });

                updatePaginationControls(data);
            })
            .catch(error => console.error("Error loading books:", error));
    }

    handleSubmit(event) {
        event.preventDefault();
        console.log("Form submitted");
    }

    openEditModal(row) {
        console.log("Opening modal for:", row.dataset.id);
    }
}