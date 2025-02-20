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