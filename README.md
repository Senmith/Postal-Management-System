# 📦 Post Office Management System

A comprehensive web-based postal management system built with Spring Boot that streamlines parcel tracking, post office management, and logistics operations. The system features QR code generation for parcels, real-time tracking, and a modern, user-friendly dashboard.

## ✨ Features

### Parcel Management
- **Create and Register Parcels** - Add new parcels with sender and receiver details
- **Track Parcels** - Real-time tracking with complete delivery history
- **QR Code Generation** - Automatic QR code creation for each parcel
- **Status Updates** - Track parcels through different delivery stages:
  - Registered
  - At Origin Post Office
  - In Transit
  - At Destination Post Office
  - Out for Delivery
  - Delivered
  - Returned

### Post Office Management
- **Office Registration** - Add and manage multiple post office locations
- **Office Details** - Track office contact information and locations
- **Parcel Assignment** - Assign parcels to specific post offices

### QR Code System
- **QR Scanner Interface** - Scan parcels at checkpoints
- **Automated Status Updates** - Update parcel status via QR scanning
- **History Tracking** - Maintain complete checkpoint history

### Dashboard & Analytics
- **Overview Dashboard** - Real-time statistics and insights
- **Parcel Categories** - Organize by Document, Package, Registered, or EMS
- **Search Functionality** - Quick parcel lookup and filtering
- **Responsive Design** - Works seamlessly on desktop and mobile devices

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.5.6** - Core framework
- **Spring Data JPA** - Database operations
- **Hibernate** - ORM framework
- **MySQL** - Database management

### Frontend
- **Thymeleaf** - Server-side template engine
- **Bootstrap 5.3.2** - Responsive UI framework
- **HTML5/CSS3** - Modern web standards
- **JavaScript** - Interactive features

### Libraries & Tools
- **ZXing (3.5.3)** - QR code generation and scanning
- **Maven** - Dependency management
- **Java 17** - Programming language

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java Development Kit (JDK) 17 or higher**
- **Apache Maven 3.6+**
- **MySQL 8.0+** (or XAMPP with MySQL)
- **Your favorite IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Senmith/Postal-Management-System.git
cd Postal-Management-System/postalapp
```

### 2. Set Up the Database

Create a MySQL database for the application:

```sql
CREATE DATABASE postal;
```

### 3. Configure Database Connection

Open `src/main/resources/application.properties` and update the database credentials if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/postal
spring.datasource.username=root
spring.datasource.password=your_password_here
```

**Note:** If using XAMPP, keep the password empty.

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

Using Maven:
```bash
mvn spring-boot:run
```

Or using the Maven wrapper:
```bash
./mvnw spring-boot:run    # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

### 6. Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```

## 📖 Usage Guide

### Managing Parcels

1. **Add a New Parcel**
   - Navigate to the "Parcels" section
   - Click "Add New Parcel"
   - Fill in sender and receiver details
   - Select category and assign to a post office
   - A QR code will be automatically generated

2. **Track a Parcel**
   - Go to the "Tracking" page
   - Enter the parcel ID
   - View complete delivery history and current status

3. **Update Parcel Status**
   - Use the QR Scanner or manual update
   - Select new status and location
   - History is automatically maintained

### Managing Post Offices

1. **Add a Post Office**
   - Navigate to "Post Offices"
   - Click "Add New Office"
   - Enter office details (ID, name, location, contact)

2. **View All Offices**
   - See a list of all registered post offices
   - View parcels assigned to each office

### Using the QR Scanner

1. Navigate to the "QR Checkpoint" page
2. Scan the parcel's QR code
3. Select the new status and location
4. The system automatically updates the parcel history

## 📁 Project Structure

```
postalapp/
├── src/
│   ├── main/
│   │   ├── java/iit/postalapp/
│   │   │   ├── PostalappApplication.java    # Main application
│   │   │   ├── Parcel.java                  # Parcel entity
│   │   │   ├── ParcelService.java           # Business logic
│   │   │   ├── ParcelController.java        # REST endpoints
│   │   │   ├── ParcelRepo.java              # Data access
│   │   │   ├── ParcelHistory.java           # History tracking
│   │   │   ├── PostOffice.java              # Office entity
│   │   │   ├── PostOfficeService.java       # Office logic
│   │   │   ├── PostOfficeController.java    # Office endpoints
│   │   │   ├── QRCodeService.java           # QR generation
│   │   │   ├── QRScannerController.java     # Scanner endpoints
│   │   │   └── DashboardController.java     # Dashboard logic
│   │   └── resources/
│   │       ├── application.properties       # Configuration
│   │       ├── templates/                   # HTML templates
│   │       │   ├── dashboard.html
│   │       │   ├── parcels.html
│   │       │   ├── post-offices.html
│   │       │   ├── qr-checkpoint.html
│   │       │   └── tracking.html
│   │       └── static/qrcodes/             # Generated QR codes
│   └── test/
│       └── java/iit/postalapp/
│           └── PostalappApplicationTests.java
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

## 🔧 Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/postal
spring.datasource.username=root
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

### Database Schema

The application automatically creates the following tables:
- `parcel_data` - Stores parcel information
- `parcel_history` - Tracks status changes
- `post_office` - Stores post office details

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Known Issues & Future Enhancements

### Known Issues
- QR code scanner requires camera permissions
- Large parcel history may affect page load times

### Planned Features
- [ ] Email notifications for status updates
- [ ] PDF invoice generation
- [ ] Advanced analytics dashboard
- [ ] Mobile application
- [ ] Multi-language support
- [ ] Payment integration
- [ ] User authentication and roles
- [ ] Export data to CSV/Excel

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/Senmith/Postal-Management-System/issues) page
2. Create a new issue with detailed information
3. Contact the maintainer

## 👨‍💻 Author

**Senmith**
- GitHub: [@Senmith](https://github.com/Senmith)

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Bootstrap team for the responsive framework
- ZXing library for QR code functionality
- All contributors who help improve this project

---

Made with ❤️ for efficient postal management
