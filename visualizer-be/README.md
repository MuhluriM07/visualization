Prerequisites
Before running the backend Spring Boot application, make sure you have the following installed:

Java 21 or later
Maven (version >= 3.6)
Steps to Set Up and Run
Clone the repository:

Clone the project repository to your local machine: git clone <repository-url>

Install Dependencies:

Use Maven to install the required dependencies and compile the project: mvn clean install

Run the Microservice:

After the dependencies are installed and the project is compiled, you can start the Spring Boot application using: mvn spring-boot:run

This will start the backend service at http://localhost:8020.

How the Spring Boot Microservice Works
The Spring Boot backend serves as the engine that handles file uploads, data processing, and storing the data in the PostgreSQL database. The backend exposes a set of RESTful APIs for the frontend to interact with.

File Upload: The microservice handles the upload of files (either JSON or CSV format). Once the file is received, it is processed and parsed:

If the file is in JSON format, it is parsed using ObjectMapper from the Jackson library.
If the file is in CSV format, it is processed using Apache POI or OpenCSV.
Data Storage: After parsing the data, the microservice stores it in the PostgreSQL database. The backend uses Spring Data JPA for database operations.

APIs: The backend exposes several REST APIs to allow the frontend to:

Upload data files.
Fetch processed data for visualization.

## Back End Project Development

Muhluri Mhlongo - Full Stack Developer
  