class BookManager {
    constructor(apiUrl, pageSize = 10) {
        this.apiUrl = apiUrl;
        this.pageSize = pageSize;

        document.addEventListener("DOMContentLoaded", () => {
            this.loadBooks();
        });

        // Close modals when clicking outside
        window.onclick = (event) => {
            let editModal = document.getElementById("editModal");
            let bookDetailsModal = document.getElementById("bookDetailsModal");
            if (event.target === editModal) {
                this.closeModal(editModal);
            }
            if (event.target === bookDetailsModal) {
                this.closeModal(bookDetailsModal);
            }
        };
    }

    loadBooks(page = 0) {
        fetch(`${this.apiUrl}?page=${page}&size=${this.pageSize}`)
            .then(response => response.json())
            .then(data => {
                if (!data.content) {
                    console.error("Unexpected API response:", data);
                    return;
                }
                const booksContainer = document.getElementById("booksContainer");
                booksContainer.innerHTML = "";

                data.content.forEach(book => {
                    const row = document.createElement("div");
                    row.className = "grid-row";
                    row.dataset.id = book.isbn;

                    row.innerHTML = `
                    <div>${book.id}</div>
                    <div>${book.title}</div>
                    <div>${book.authors}</div>
                    <div>${book.description}</div>
                    <div>${book.genres}</div>
                    <div>${book.publishedDate}</div>
                    <div>${book.publishers}</div>
                    <div>${book.isbn}</div>
                    <div><button id="editButton" class="edit-btn">Edit</button></div>
                `;

                    // Row click handler for book details
                    row.addEventListener("click", (event) => {
                        event.stopPropagation();
                        this.openBookDetailsModal(book);
                    });

                    // Edit button click handler
                    const editBtn = row.querySelector(".edit-btn");
                    editBtn.addEventListener("click", (event) => {
                        event.stopPropagation();
                        this.openEditModal(book);
                    });

                    booksContainer.appendChild(row);
                });

                this.updateControls(data);
            })
            .catch(error => console.error("Error loading books:", error));
    }

    openBookDetailsModal(book) {
        document.getElementById("bDModalTitle").textContent = book.title;
        document.getElementById("bDModalAuthors").textContent = book.authors;
        document.getElementById("bDModalDescription").textContent = book.description;
        document.getElementById("bDModalGenres").textContent = book.genres;
        document.getElementById("bDModalPublishedDate").textContent = book.publishedDate;
        document.getElementById("bDModalPublishers").textContent = book.publishers;
        document.getElementById("bDModalIsbn").textContent = book.isbn;
        document.getElementById("bDModalImage").src = "https://picsum.photos/200/300/?blur"

        document.getElementById("bookDetailsModal").style.display = "block";
    }

    openEditModal(book) {
        document.getElementById("editForm").addEventListener("submit",
            (event) => {this.handleEditSubmit(event)})

        document.getElementById("editModalId").value = book.id;
        document.getElementById("editModalTitle").value = book.title;
        document.getElementById("editModalAuthors").value = book.authors;
        document.getElementById("editModalDescription").value = book.description;
        document.getElementById("editModalGenres").value = book.genres;
        document.getElementById("editModalPublishedDate").value = book.publishedDate;
        document.getElementById("editModalPublishers").value = book.publishers;
        document.getElementById("editModalIsbn").value = book.isbn;

        document.getElementById("editModal").style.display = "block";
    }

    handleEditSubmit(event) {
        event.preventDefault();
        let formData = {
            id: document.getElementById("editModalId").value,
            title: document.getElementById("editModalTitle").value,
            authors: document.getElementById("editModalAuthors").values,
            description: document.getElementById("editModalDescription").value,
            genres: document.getElementById("editModalGenres").value,
            publishedDate: document.getElementById("editModalPublishedDate").value,
            publishers: document.getElementById("editModalPublishers").value,
            isbn: document.getElementById("editModalIsbn").value,
        };

        fetch(`${this.apiUrl}/book/update`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(() => {
                alert("Book updated successfully!");
                document.getElementById("editModal").style.display = "none";
                this.loadBooks();
            })
            .catch(error => console.error("Error updating book:", error));
    }

    closeModal(modal) {
        modal.style.display = "none";
    }

    updateControls(data) {
        document.getElementById("pagination").innerHTML = `
            <button id="prevPage" ${data.number === 0 ? "disabled" : ""}>Previous</button>
            <span>Page ${data.number + 1} of ${data.totalPages}</span>
            <button id="nextPage" ${data.number + 1 >= data.totalPages ? "disabled" : ""}>Next</button>
        `;

        document.getElementById("prevPage").addEventListener("click", () => this.loadBooks(data.number - 1));
        document.getElementById("nextPage").addEventListener("click", () => this.loadBooks(data.number + 1));
    }

    handleSearch(event) {
        event.preventDefault();
        const searchType = document.getElementById("searchType").value;
        const searchQuery = document.getElementById("searchInput").value.trim();
        if (!searchQuery) return;

        fetch(`/api/v1/books/search?${searchType}=${encodeURIComponent(searchQuery)}`)
            .then(response => response.json())
            .then(books => this.updateTable(books))
            .catch(error => console.error("Error searching books:", error));
    }

    applyFilters() {
        const title = document.getElementById("filterTitle").value.trim();
        const author = document.getElementById("filterAuthor").value;
        const genre = document.getElementById("filterGenre").value.trim();
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;

        let url = `/api/v1/books/filter?`;
        if (title) url += `title=${encodeURIComponent(title)}&`;
        if (author) url += `authorId=${author}&`;
        if (genre) url += `genre=${encodeURIComponent(genre)}&`;
        if (startDate) url += `startDate=${startDate}&`;
        if (endDate) url += `endDate=${endDate}&`;

        fetch(url)
            .then(response => response.json())
            .then(books => bookManager.updateTable(books))
            .catch(error => console.error("Error applying filters:", error));
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
                    option.setAttribute("data-bio", author.bio);
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

// Initialize the app
class Main {
    constructor() {
        document.addEventListener("DOMContentLoaded", () => {
            this.run();
        });
    }

    run() {
        const apiUrl = "http://127.0.0.1:8081/api/v1/library";
        window.bookManager = new BookManager(apiUrl);
        bookManager.loadBooks();
    }
}

new Main();