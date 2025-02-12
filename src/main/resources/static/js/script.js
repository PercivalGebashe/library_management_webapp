document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("editForm").addEventListener("submit", handleSubmit);
});

function openEditModal(row) {
    let modal = document.getElementById("editModal");
    let cells = row.getElementsByTagName("td");

    document.getElementById("title").value = cells[0].innerText;
    document.getElementById("author").value = cells[1].innerText;
    document.getElementById("isbn").value = cells[2].innerText; // Read-only
    document.getElementById("publisher").value = cells[3].innerText;

    modal.style.display = "block";
}

function closeModal() {
    document.getElementById("editModal").style.display = "none";
}

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
            location.reload(); // Refresh table
        })
        .catch(error => console.error("Error:", error));
}

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

// Close modal when clicking outside
window.onclick = function(event) {
    let modal = document.getElementById("editModal");
    if (event.target == modal) {
        closeModal();
    }
};