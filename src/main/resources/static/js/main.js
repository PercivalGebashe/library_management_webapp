// Table Update class
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

                // const pagination = new Pagination(apiUrl, pageSize);
                // pagination.updatePaginationControls(data);
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

// Search Filter class
export class BookSearch {
    constructor(searchFormId, updateTableCallback) {
        this.form = document.getElementById(searchFormId);
        this.updateTable = updateTableCallback;
        this.form.addEventListener("submit", (event) => this.handleSearch(event));
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
}

// pagination class
export class Pagination {

    constructor(containerId, loadBooksCallback) {
        this.container = document.getElementById(containerId);
        this.loadBooks = loadBooksCallback; // Function to load books
    }

    updateControls(data) {
        this.container.innerHTML = `
            <button id="prevPage" ${data.number === 0 ? "disabled" : ""}>Previous</button>
            <span>Page ${data.number + 1} of ${data.totalPages}</span>
            <button id="nextPage" ${data.number + 1 >= data.totalPages ? "disabled" : ""}>Next</button>
        `;

        document.getElementById("prevPage").addEventListener("click", () => this.loadBooks(data.number - 1));
        document.getElementById("nextPage").addEventListener("click", () => this.loadBooks(data.number + 1));
    }
}

// filter class
export class Filter {
    constructor() {
        const filterBtn = document.getElementById("applyFiltersBtn");
        if (filterBtn) {
            filterBtn.addEventListener("click", () => this.applyFilters());
        }
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
            .then(books => updateTable(books)) // Use imported function
            .catch(error => console.error("Error applying filters:", error));
    }
}

//book edit modal class
export class EditModal {
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

//author class
export class Authors {
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

class Main{
    constructor() {
        document.addEventListener("DOMContentLoaded", () => {
            this.run();
        });
    }

    run() {
        const apiUrl = "http://127.0.0.1:8081/api/v1/library";
        const bookManager = new BookManager(apiUrl);
        // // const pagination = new Pagination("paginationControls", (page) => bookManager.loadBooks(page));
        // const bookSearch = new BookSearch("searchForm", (books) => this.updateTable(books));
        // const filter = new Filter();
        // const editModal = new EditModal(apiUrl, () => bookManager.loadBooks());
        // const authors = new Authors();

        bookManager.loadBooks();
    }
}

new Main();