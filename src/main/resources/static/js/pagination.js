export default class Pagination {
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
