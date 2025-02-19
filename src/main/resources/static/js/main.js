import BookManager from "./updateTableView";
import BookSearch from "./searchBooks";
import Pagination from "./pagination";
import LoginHandler from "./loginForm";
import EditModal from "./editModal";
import Authors from "./authors";
import Filter from "./filter";

const apiUrl = "http://127.0.0.1:8081/api/v1/library";
let currentPage = 0;
const pageSize = 10;