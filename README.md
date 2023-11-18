# Bus Lines Challenge

## Overview
This project is a solution to the Bus Lines Challenge, involving the creation of a Spring Boot application that displays the top 10 bus lines with the most bus stops on their route. The application also shows the names of every bus stop for each of the bus lines in a nicely formatted way in a web browser. The data is automatically gathered from Trafiklab’s open API.

## Table of Contents
- [Requirements](#requirements)
- [Backend (Java)](#backend-java)
- [Frontend (JavaScript)](#frontend-javascript)
- [Additional Guidelines](#additional-guidelines)
- [Getting Started](#getting-started)
    - [Backend (Spring Boot)](#backend-spring-boot-1)
    - [Frontend (JavaScript)](#frontend-javascript-1)
- [License](#license)

## Requirements

### Backend (Java)
- **Language:** Java 17
- **Build Tool:** Maven 4.0.0
- **API:** Utilize Trafiklab’s open API for fetching bus lines and bus stops data. Documentation can be found [here](https://www.trafiklab.se/api/sl-hallplatser-och-linjer-2).
- **Task:** Implement a Spring Boot application that fetches data from the API, analyzes it to find the top 10 bus lines with the most bus stops, and retrieves the names of every bus stop for each of these lines.
- **Delivery:** Share the Java source code along with clear instructions on how to run the application on MacOS/Unix or Windows.

### Frontend (JavaScript)
- **Language:** JavaScript
- **Task:** Implement a frontend application that visually presents the results obtained from the backend. Display the top 10 bus lines and their associated bus stops in a well-formatted manner.
- **Delivery:** Share the JavaScript source code for the frontend. Provide instructions on how to integrate it with the backend and run the complete application.
- **Link to the Frontend repository:** [topbuslines_frontend](https://github.com/Yommievata/topbuslines_frontend)

## Additional Guidelines
- **API Access:** Register your own account at Trafiklab to obtain API access.
- **External Libraries:** You are free to use external libraries for both the frontend and backend if they enhance the functionality or improve the user interface.

## Getting Started
Follow these instructions to set up and run the Bus Lines Challenge application.

### Backend (Spring Boot)
1. Clone the repository: `git clone https://github.com/your-username/topbuslines_backend.git`
2. Navigate to the backend directory: `cd topbuslines_backend`
3. Open the project in your preferred Java IDE or use the command line.
4. Configure your Trafiklab API credentials in the application.
5. Run the application: `mvn spring-boot:run`.

### Frontend (JavaScript)
1. Clone the frontend repository: `git clone https://github.com/Yommievata/topbuslines_frontend.git`
2. Navigate to the frontend directory: `cd topbuslines_frontend`
3. Open the `index.html` file in your web browser or set up a local server.

## License
This project is licensed under the MIT License. See the [MIT License](LICENSE) for more details.

