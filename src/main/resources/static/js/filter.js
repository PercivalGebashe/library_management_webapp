function applyFilters() {
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
        .then(books => updateTable(books))
        .catch(error => console.error("Error applying filters:", error));
}