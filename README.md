# Library Management Web Application

Welcome to the Library Management Web Application! This project is designed to streamline the management of library resources, including books, authors, genres, and publishers. It offers a user-friendly interface for both librarians and members to efficiently manage and explore library collections.

## 🚀 Features
- **User Authentication**: Secure login and registration for librarians and members.
- **Role-Based Access Control**: Different functionalities accessible based on user roles (Admin, User).
- **Book Management**: Add, update, delete, and view books in the library.
- **Author and Genre Management**: Manage authors and genres associated with books.
- **Publisher Management**: Keep track of publishers and their publications.
- **Borrowing System**: Members can borrow and return books; librarians can manage borrowing records.
- **Search and Filter**: Advanced search and filtering options to easily find books and authors.
- **Responsive Design**: Accessible on various devices, including desktops, tablets, and mobile phones.

## 🛠️ Technologies Used
- **Backend**: Java, Spring Boot, Spring Security, JPA/Hibernate
- **Frontend**: HTML, CSS, JavaScript, Thymeleaf
- **Database**: MySQL
- **Build Tool**: Maven
- **Version Control**: Git

## 🗂️ Project Structure
```
src/
├── main/
│   ├── java/com/github/percivalgebashe/librarymanagement/
│   │   ├── controller/    # REST Controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── entity/        # JPA Entities
│   │   ├── exception/     # Custom Exception Handling
│   │   ├── repository/    # Data Repositories
│   │   ├── security/      # Security Configurations
│   │   ├── service/       # Business Logic Services
│   │   └── LibraryManagementApplication.java
│   └── resources/
│       ├── templates/     # Thymeleaf Templates
│       ├── static/        # Static Resources (CSS, JS)
│       └── application.properties
└── test/
    └── java/com/github/percivalgebashe/librarymanagement/
        └── LibraryManagementApplicationTests.java
```

## ⚙️ Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 8.0 or higher

### Steps

#### Clone the Repository
```bash
git clone https://github.com/PercivalGebashe/library_management_webapp.git
cd library_management_webapp
```

#### Configure the Database

Create a MySQL database named `library_db`.

Update the `src/main/resources/application.properties` file with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### Build the Project
```bash
./mvnw clean install
```

#### Run the Application
```bash
./mvnw spring-boot:run
```
The application will start on [http://localhost:8080](http://localhost:8080).

## 🔒 Security
The application implements role-based authentication and authorization using Spring Security. There are two primary roles:
- **ADMIN**: Full access to all management features.
- **USER**: Access to view and borrow books.

Authentication is handled via JWT (JSON Web Tokens) to ensure secure and stateless communication between the client and server.

## 📄 API Documentation
API documentation is available via Swagger UI. Once the application is running, you can access it at:
```bash
http://localhost:8080/swagger-ui.html
```
This interface provides a comprehensive overview of all available endpoints, their request and response structures, and allows for interactive API exploration.

## 🤝 Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a new branch: `git checkout -b feature/your-feature-name`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/your-feature-name`.
5. Create a pull request.

Please ensure your code adheres to the project's coding standards and includes appropriate tests.

## 📝 License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## 📧 Contact
For questions or support, please contact *++++++++++++ +++++++++* at `++++++++++++++++++++++++`.

Thank you for using the Library Management Web Application! We hope it meets your library's needs effectively.
