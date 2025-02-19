function searchBooks() {
    const searchType = document.getElementById("searchType").value;
    const searchQuery = document.getElementById("searchInput").value.trim();
    if (!searchQuery) return;

    fetch(`/api/v1/books/search?${searchType}=${encodeURIComponent(searchQuery)}`)
        .then(response => response.json())
        .then(books => updateTable(books))
        .catch(error => console.error("Error searching books:", error));
}