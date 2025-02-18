document.addEventListener("DOMContentLoaded", () => {
    loadBooks(); // Fetch and populate books when the page loads
    document.getElementById("editForm").addEventListener("submit", handleSubmit);
});

// ✅ Fetch books from the REST API and populate the table dynamically
function loadBooks() {
    fetch("http://127.0.0.1:8081/api/v1/library") // Calls the REST API we set up
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("tbody");
            tableBody.innerHTML = ""; // Clear old rows

            data.forEach(book => {
                const row = `<tr data-id="${book.isbn}" onclick="openEditModal(this)">
                    <td>${book.title}</td>
                    <td>${book.authors}</td>
                    <td>${book.isbn}</td>
                    <td>${book.publishers}</td>
                    <td><button>Edit</button></td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error loading books:", error));
}

// ✅ Open the edit modal and populate fields
function openEditModal(row) {
    let modal = document.getElementById("editModal");
    let cells = row.getElementsByTagName("td");

    document.getElementById("title").value = cells[0].innerText;
    document.getElementById("author").value = cells[1].innerText;
    document.getElementById("isbn").value = cells[2].innerText; // Read-only
    document.getElementById("publisher").value = cells[3].innerText;

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
        title: document.getElementById("title").value,
        author: document.getElementById("author").value,
        isbn: document.getElementById("isbn").value,
        publisher: document.getElementById("publisher").value
    };

    fetch(`/api/books/${formData.isbn}`, {
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
    let title = document.getElementById("title").value.trim();
    let author = document.getElementById("author").value.trim();
    let isbn = document.getElementById("isbn").value.trim();
    let publisher = document.getElementById("publisher").value.trim();

    let errors = {};

    if (!title) errors.title = "Title is required.";
    if (!author) errors.author = "Author is required.";
    if (!isbn.match(/^\d{10}(\d{3})?$/)) errors.isbn = "Invalid ISBN (must be 10 or 13 digits).";
    if (!publisher) errors.publisher = "Publisher is required.";

    document.getElementById("titleError").innerText = errors.title || "";
    document.getElementById("authorError").innerText = errors.author || "";
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