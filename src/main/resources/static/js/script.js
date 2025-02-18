document.addEventListener("DOMContentLoaded", () => {
    loadBooks(); // Fetch and populate books when the page loads
    document.getElementById("editForm").addEventListener("submit", handleSubmit);
});

const apiUrl = "http://127.0.0.1:8081/api/v1/library";
let currentPage = 0;
const pageSize = 10; // Adjust if needed

// ✅ Fetch books from API and populate table
function loadBooks(page = 0) {
    fetch(`${apiUrl}?page=${page}&size=${pageSize}`)
        .then(response => response.json())
        .then(data => {
            if (!data.content) {
                console.error("Unexpected API response:", data);
                return;
            }

            const tableBody = document.querySelector("tbody");
            tableBody.innerHTML = ""; // Clear existing rows

            data.content.forEach(book => {
                const row = document.createElement("tr");
                row.dataset.id = book.isbn; // Store ISBN for reference
                // const row = `<!--<tr data-id="${book.isbn}" onclick="openEditModal(this)">-->
                row.innerHTML = `
                    <td>${book.bookId}</td>
                    <td>${book.title}</td>
                    <td>${book.authors}</td>
                    <td>${book.description}</td>
                    <td>${book.genres}</td>
                    <td>${book.publishedDate}</td>
                    <td>${book.isbn}</td>
                    <td>${book.publishers}</td>
                    <td><button class="edit-btn">Edit</button></td>
                </tr>`;
                tableBody.appendChild(row);
            });

            // ✅ Add event listener to all buttons AFTER they are added to the DOM
            document.querySelectorAll(".edit-btn").forEach(button => {
                button.addEventListener("click", function (event) {
                    event.stopPropagation(); // Prevents the row click event from firing
                    openEditModal(this.closest("tr")); // Pass the parent row
                });
            });
            updatePaginationControls(data);
        })
        .catch(error => console.error("Error loading books:", error));
}

// ✅ Update pagination controls
function updatePaginationControls(data) {
    document.getElementById("pagination").innerHTML = `
        <button ${data.number === 0 ? "disabled" : ""} onclick="loadBooks(${data.number - 1})">Previous</button>
        <span>Page ${data.number + 1} of ${data.totalPages}</span>
        <button ${data.number + 1 >= data.totalPages ? "disabled" : ""} onclick="loadBooks(${data.number + 1})">Next</button>
    `;
}

// ✅ Open the edit modal and populate fields
function openEditModal(row) {
    let modal = document.getElementById("editModal");
    let cells = row.getElementsByTagName("td");

    document.getElementById("id").value = cells[0].innerText // Read-only
    document.getElementById("title").value = cells[1].innerText;
    document.getElementById("authors").value = cells[2].innerText;
    document.getElementById("description").value = cells[3].innerText;
    document.getElementById("genres").value = cells[4].innerText;
    document.getElementById("publishedDate").value = cells[5].innerText;
    document.getElementById("publisher").value = cells[6].innerText;
    document.getElementById("isbn").value = cells[7].innerText; // Read-only

    modal.style.display = "block";
}

// ✅ Close the modal
function closeModal() {
    document.getElementById("editModal").style.display = "none";
}

// ✅ Handle form submission for book updates
function handleSubmit(event) {
    event.preventDefault();

    let isValid = validateForm();
    if (!isValid) return;

    let formData = {
        bookId: document.getElementById("id").value,
        title: document.getElementById("title").value,
        author: document.getElementById("authors").value,
        description: document.getElementById("description").value,
        genres: document.getElementById("genre").value,
        publishedDate: document.getElementById("publishedDate").value,
        publishers: document.getElementById("publishers").value,
        isbn: document.getElementById("isbn").value,
    };

    fetch(`http://127.0.0.1:8081/api/v1/library/book/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
        .then(response => response.json())
        .then(data => {
            alert("Book updated successfully!");
            closeModal();
            loadBooks(); // Reload books after updating
        })
        .catch(error => console.error("Error:", error));
}

// ✅ Validate form inputs
function validateForm() {
    let bookId = document.getElementById("id").value.trim();
    let title = document.getElementById("title").value.trim();
    let authors = document.getElementById("authors").value.trim();
    let description = document.getElementById("description").value.trim();
    let genres = document.getElementById("genre").value.trim();
    let publishedDate = document.getElementById("publishedDate").value.trim();
    let isbn = document.getElementById("isbn").value.trim();
    let publisher = document.getElementById("publisher").value.trim();

    let errors = {};

    if (!bookId) errors.bookId = "Book ID is required";
    if (!title) errors.title = "Title is required.";
    if (!authors) errors.author = "Author is required.";
    if (!description) errors.description = "Description is required.";
    if (!genres) errors.genres = "Genre is required.";
    if (!publishedDate) errors.publishedDate = "PublishedDate is required.";
    if (!isbn.match(/^\d{10}(\d{3})?$/)) errors.isbn = "Invalid ISBN (must be 10 or 13 digits).";
    if (!publisher) errors.publisher = "Publisher is required.";

    document.getElementById("idError").innerText = errors.title || "";
    document.getElementById("titleError").innerText = errors.title || "";
    document.getElementById("authorError").innerText = errors.author || "";
    document.getElementById("descriptionError").innerText = errors.isbn || "";
    document.getElementById("genresError").innerText = errors.publisher || "";
    document.getElementById("publishedDateError").innerText = errors.author || "";
    document.getElementById("isbnError").innerText = errors.isbn || "";
    document.getElementById("publisherError").innerText = errors.publisher || "";

    return Object.keys(errors).length === 0;
}

// ✅ Close modal when clicking outside of it
window.onclick = function(event) {
    let modal = document.getElementById("editModal");
    if (event.target == modal) {
        closeModal();
    }
};