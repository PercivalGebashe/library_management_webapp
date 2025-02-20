class BookManager {
    constructor(apiUrl, pageSize = 10) {
        this.apiUrl = apiUrl;
        this.pageSize = pageSize;

        document.addEventListener("DOMContentLoaded", () => {
            this.loadBooks();
            document.getElementById("editForm").addEventListener(
                "submit", (event) => this.handleSubmit(event)
            );
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

                pagination.updateControls(data);
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

    updateTable(books) {
        const tableBody = document.querySelector("tbody");
        tableBody.innerHTML = "";

        books.forEach(book => {
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
            `;
            tableBody.appendChild(row);
        });
    }
}

class BookSearch {
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

class Pagination {
    constructor(containerId, loadBooksCallback) {
        this.container = document.getElementById(containerId);
        this.loadBooks = loadBooksCallback;
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

class Filter {
    constructor() {
        document.getElementById("applyFiltersBtn").addEventListener("click", () => this.applyFilters());
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
}

class EditModal {
    constructor(apiUrl, loadBooks) {
        this.apiUrl = apiUrl;
        this.loadBooks = loadBooks;

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

        document.getElementById("id").value = cells[0].innerText;
        document.getElementById("title").value = cells[1].innerText;
        document.getElementById("authors").value = cells[2].innerText;
        document.getElementById("description").value = cells[3].innerText;
        document.getElementById("genres").value = cells[4].innerText;
        document.getElementById("publishedDate").value = cells[5].innerText;
        document.getElementById("publishers").value = cells[6].innerText;
        document.getElementById("isbn").value = cells[7].innerText;

        modal.style.display = "block";
    }

    closeModal() {
        document.getElementById("editModal").style.display = "none";
    }
}

class Authors {
    constructor() {
        document.addEventListener("DOMContentLoaded", () => {
            this.fetchAuthors();
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

class Main {
    constructor() {
        document.addEventListener("DOMContentLoaded", () => {
            this.run();
        });
    }

    run() {
        const apiUrl = "http://127.0.0.1:8081/api/v1/library";
        window.bookManager = new BookManager(apiUrl);
        window.pagination = new Pagination("pagination", (page) => bookManager.loadBooks(page));
        window.bookSearch = new BookSearch("searchForm", (books) => bookManager.updateTable(books));
        window.filter = new Filter();
        window.editModal = new EditModal(apiUrl, () => bookManager.loadBooks());
        window.authors = new Authors();

        bookManager.loadBooks();
    }
}

// Initialize the app
new Main();
