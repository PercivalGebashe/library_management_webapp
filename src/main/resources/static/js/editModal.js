export default class EditModal {
    constructor(apiUrl, loadBooks) {
        this.apiUrl = apiUrl;
        this.loadBooks = loadBooks;

        // Close modal when clicking outside
        window.onclick = (event) => {
            let modal = document.getElementById("editModal");
            if (event.target === modal) {
                this.closeModal();
            }
        };
    }

    openEditModal(row) {
        let modal = document.getElementById("editModal");
        let cells = row.getElementsByTagName("td");

        document.getElementById("id").value = cells[0].innerText; // Read-only
        document.getElementById("title").value = cells[1].innerText;
        document.getElementById("authors").value = cells[2].innerText;
        document.getElementById("description").value = cells[3].innerText;
        document.getElementById("genres").value = cells[4].innerText;
        document.getElementById("publishedDate").value = cells[5].innerText;
        document.getElementById("publishers").value = cells[6].innerText;
        document.getElementById("isbn").value = cells[7].innerText; // Read-only

        modal.style.display = "block";
    }

    closeModal() {
        document.getElementById("editModal").style.display = "none";
    }

    handleSubmit(event) {
        event.preventDefault();

        let isValid = this.validateForm();
        if (!isValid) return;

        let formData = {
            id: document.getElementById("id").value,
            title: document.getElementById("title").value,
            author: document.getElementById("authors").value,
            description: document.getElementById("description").value,
            genres: document.getElementById("genres").value,
            publishedDate: document.getElementById("publishedDate").value,
            publishers: document.getElementById("publishers").value,
            isbn: document.getElementById("isbn").value,
        };

        fetch(`${this.apiUrl}/book/update`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                alert("Book updated successfully!");
                this.closeModal();
                this.loadBooks(); // Reload books after updating
            })
            .catch(error => console.error("Error:", error));
    }

    validateForm() {
        let id = document.getElementById("id").value.trim();
        let title = document.getElementById("title").value.trim();
        let authors = document.getElementById("authors").value.trim();
        let description = document.getElementById("description").value.trim();
        let genres = document.getElementById("genres").value.trim();
        let publishedDate = document.getElementById("publishedDate").value.trim();
        let publishers = document.getElementById("publishers").value.trim();
        let isbn = document.getElementById("isbn").value.trim();

        let errors = {};

        if (!id) errors.id = "Book ID is required";
        if (!title) errors.title = "Title is required.";
        if (!authors) errors.authors = "Author is required.";
        if (!description) errors.description = "Description is required.";
        if (!genres) errors.genres = "Genre is required.";
        if (!publishedDate) errors.publishedDate = "Published Date is required.";
        if (!publishers) errors.publishers = "Publisher is required.";
        if (!isbn.match(/^\d{10}(\d{3})?$/)) errors.isbn = "Invalid ISBN (must be 10 or 13 digits).";

        document.getElementById("idError").innerText = errors.id || "";
        document.getElementById("titleError").innerText = errors.title || "";
        document.getElementById("authorError").innerText = errors.authors || "";
        document.getElementById("descriptionError").innerText = errors.description || "";
        document.getElementById("genresError").innerText = errors.genres || "";
        document.getElementById("publishedDateError").innerText = errors.publishedDate || "";
        document.getElementById("publisherError").innerText = errors.publishers || "";
        document.getElementById("isbnError").innerText = errors.isbn || "";

        return Object.keys(errors).length === 0;
    }
}