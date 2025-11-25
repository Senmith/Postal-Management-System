# Database Setup Guide

This guide will help you set up the MySQL database for the Post Office Management System.

## Prerequisites

- MySQL 8.0 or higher installed
- MySQL Workbench (optional, but recommended)
- OR XAMPP with MySQL

## Option 1: Using MySQL Command Line

### Step 1: Access MySQL

Open your terminal/command prompt and access MySQL:

```bash
mysql -u root -p
```

Enter your MySQL root password when prompted (leave empty if no password is set).

### Step 2: Create the Database

```sql
CREATE DATABASE postal;
```

### Step 3: Verify Database Creation

```sql
SHOW DATABASES;
```

You should see `postal` in the list.

### Step 4: Exit MySQL

```sql
EXIT;
```

## Option 2: Using MySQL Workbench

### Step 1: Open MySQL Workbench

Launch MySQL Workbench and connect to your local MySQL server.

### Step 2: Create the Database

1. Click on the "Create a new schema" button (cylinder with a plus icon)
2. Enter `postal` as the schema name
3. Click "Apply"
4. Review the SQL script and click "Apply" again
5. Click "Finish"

## Option 3: Using XAMPP

### Step 1: Start MySQL

1. Open XAMPP Control Panel
2. Click "Start" next to MySQL

### Step 2: Access phpMyAdmin

1. Click "Admin" next to MySQL in XAMPP Control Panel
2. Or navigate to `http://localhost/phpmyadmin/` in your browser

### Step 3: Create the Database

1. Click on "New" in the left sidebar
2. Enter `postal` as the database name
3. Select `utf8mb4_general_ci` as collation
4. Click "Create"

## Application Configuration

After creating the database, configure the application:

### Step 1: Open application.properties

Navigate to: `src/main/resources/application.properties`

### Step 2: Update Database Credentials

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/postal
spring.datasource.username=root
spring.datasource.password=your_password_here
```

**Important Notes:**
- Replace `your_password_here` with your actual MySQL password
- If using XAMPP with default settings, leave password empty
- If your MySQL is running on a different port, update `3306` accordingly

### Step 3: Verify Hibernate Settings

Ensure these settings are present (they should be by default):

```properties
# Hibernate will automatically create tables
spring.jpa.hibernate.ddl-auto=update

# Show SQL queries in console (useful for debugging)
spring.jpa.show-sql=true

# Format SQL for better readability
spring.jpa.properties.hibernate.format_sql=true
```

## Database Tables

The application will automatically create the following tables when you first run it:

### 1. parcel_data
Stores parcel information:
- `parcel_id` (Primary Key)
- `sender_name`
- `sender_address`
- `receiver_name`
- `receiver_address`
- `weight`
- `category`
- `current_status`
- `created_date`
- `qr_code_path`
- `office_id` (Foreign Key)

### 2. parcel_history
Tracks status changes:
- `id` (Primary Key, Auto-generated)
- `parcel_id` (Foreign Key)
- `status`
- `update_time`
- `location`

### 3. post_office
Stores post office details:
- `office_id` (Primary Key)
- `office_name`
- `location`
- `contact_number`

## Testing the Database Connection

### Step 1: Run the Application

```bash
mvn spring-boot:run
```

### Step 2: Check Console Output

Look for successful connection messages like:
```
Hibernate: create table parcel_data ...
Hibernate: create table parcel_history ...
Hibernate: create table post_office ...
```

### Step 3: Verify in Database

Access your database and verify tables were created:

**Using MySQL Command Line:**
```sql
USE postal;
SHOW TABLES;
```

**Using MySQL Workbench:**
1. Refresh the schemas
2. Expand the `postal` database
3. Check the Tables folder

**Using phpMyAdmin:**
1. Click on `postal` database in the left sidebar
2. View the list of tables

## Troubleshooting

### Connection Refused Error

**Error:** `com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure`

**Solutions:**
1. Verify MySQL service is running
2. Check if MySQL is listening on port 3306
3. Verify firewall isn't blocking the connection

### Access Denied Error

**Error:** `Access denied for user 'root'@'localhost'`

**Solutions:**
1. Verify username and password in `application.properties`
2. Reset MySQL root password if forgotten
3. Create a new MySQL user with appropriate privileges:

```sql
CREATE USER 'postaluser'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON postal.* TO 'postaluser'@'localhost';
FLUSH PRIVILEGES;
```

Then update `application.properties`:
```properties
spring.datasource.username=postaluser
spring.datasource.password=secure_password
```

### Database Not Found Error

**Error:** `Unknown database 'postal'`

**Solution:**
The database wasn't created. Follow the creation steps above again.

### Table Already Exists Error

**Error:** `Table 'parcel_data' already exists`

**Solution:**
This is usually harmless. If you want to start fresh:

```sql
DROP DATABASE postal;
CREATE DATABASE postal;
```

**Warning:** This will delete all existing data!

## Database Backup and Restore

### Creating a Backup

**Using mysqldump:**
```bash
mysqldump -u root -p postal > postal_backup.sql
```

**Using MySQL Workbench:**
1. Server → Data Export
2. Select `postal` schema
3. Choose export location
4. Click "Start Export"

### Restoring from Backup

**Using MySQL Command Line:**
```bash
mysql -u root -p postal < postal_backup.sql
```

**Using MySQL Workbench:**
1. Server → Data Import
2. Select the backup file
3. Click "Start Import"

## Security Best Practices

1. **Never commit database passwords to Git**
   - The `.gitignore` file already excludes sensitive configs
   
2. **Use environment variables for production**
   ```properties
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   ```

3. **Create a separate user for the application**
   - Don't use the root account in production
   
4. **Use strong passwords**
   - Minimum 12 characters
   - Mix of letters, numbers, and symbols

5. **Regular backups**
   - Schedule daily backups
   - Store backups securely
   - Test restore procedures

## Need Help?

If you encounter issues:
1. Check the console logs for detailed error messages
2. Verify MySQL service is running
3. Test database connection using a MySQL client
4. Review the `application.properties` configuration
5. Check the [troubleshooting section](#troubleshooting) above
6. Open an issue on GitHub with:
   - Error message
   - Your MySQL version
   - Operating system
   - Steps you've already tried

---

**Database Setup Complete!** You're now ready to run the Post Office Management System. 🎉
