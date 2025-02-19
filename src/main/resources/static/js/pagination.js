function updatePaginationControls(data) {
    document.getElementById("pagination").innerHTML = `
        <button ${data.number === 0 ? "disabled" : ""} onclick="loadBooks(${data.number - 1})">Previous</button>
        <span>Page ${data.number + 1} of ${data.totalPages}</span>
        <button ${data.number + 1 >= data.totalPages ? "disabled" : ""} onclick="loadBooks(${data.number + 1})">Next</button>
    `;
}