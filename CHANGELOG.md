# Changelog

All notable changes to the Post Office Management System will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned Features
- User authentication and authorization system
- Email notifications for parcel status updates
- PDF invoice generation
- Advanced analytics dashboard
- Mobile responsive improvements
- Export functionality (CSV, Excel, PDF)
- Multi-language support
- Payment gateway integration
- RESTful API enhancements
- Real-time notifications via WebSocket

## [0.0.1-SNAPSHOT] - 2025-11-25

### Added
- Initial release of Post Office Management System
- Parcel management functionality
  - Create, read, update, and delete parcels
  - Support for different parcel categories (Document, Package, Registered, EMS)
  - Automatic parcel ID generation
- Post office management
  - Register and manage multiple post office locations
  - Assign parcels to specific post offices
- QR code system
  - Automatic QR code generation for each parcel
  - QR scanner interface for checkpoint updates
  - QR code storage in static resources
- Parcel tracking system
  - Complete delivery history tracking
  - Multiple status stages support
  - Timeline view of parcel journey
- Dashboard with statistics and overview
- Modern, responsive UI using Bootstrap 5
- Database integration with MySQL
- Spring Boot 3.5.6 backend
- Thymeleaf template engine for views
- Comprehensive documentation
  - README with setup instructions
  - API documentation
  - Database setup guide
  - Contributing guidelines
  - Security policy
  - Code of conduct

### Status Support
- REGISTERED - Parcel registered in system
- AT_ORIGIN_POST_OFFICE - At sender's post office
- IN_TRANSIT - Moving between locations
- AT_DESTINATION_POST_OFFICE - Arrived at receiver's post office
- OUT_FOR_DELIVERY - Out for final delivery
- DELIVERED - Successfully delivered
- RETURNED - Returned to sender

### Technical Stack
- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- Hibernate ORM
- MySQL Database
- Thymeleaf
- Bootstrap 5.3.2
- ZXing 3.5.3 (QR Code Library)
- Maven

### Database Schema
- `parcel_data` table for parcel information
- `parcel_history` table for status tracking
- `post_office` table for office details
- Automatic schema generation via Hibernate

### Known Issues
- No user authentication (planned for next release)
- No HTTPS support by default
- QR scanner requires camera permissions
- Limited mobile optimization for some views

### Security Notes
- Database credentials stored in application.properties (development only)
- No authentication/authorization implemented
- Should not be used in production without security enhancements

---

## Version History

### Version Number Format
```
MAJOR.MINOR.PATCH-SNAPSHOT

MAJOR: Incompatible API changes
MINOR: New functionality (backwards-compatible)
PATCH: Bug fixes (backwards-compatible)
SNAPSHOT: Development version
```

### Release Notes

This is the initial development version. Future releases will focus on:
1. Security enhancements
2. User management
3. Advanced features
4. Performance optimization
5. Production readiness

---

## How to Use This Changelog

### For Contributors
When adding new features or fixing bugs:
1. Add your changes under the `[Unreleased]` section
2. Use appropriate subsections (Added, Changed, Deprecated, Removed, Fixed, Security)
3. Keep entries concise and user-focused
4. Link to relevant issues or PRs when possible

### For Users
- Check `[Unreleased]` to see what's coming next
- Review version history to understand what's changed
- Read security notes for important security information

---

**Legend:**
- `Added` - New features
- `Changed` - Changes to existing functionality
- `Deprecated` - Soon-to-be removed features
- `Removed` - Removed features
- `Fixed` - Bug fixes
- `Security` - Security-related changes

[Unreleased]: https://github.com/Senmith/Postal-Management-System/compare/v0.0.1...HEAD
[0.0.1-SNAPSHOT]: https://github.com/Senmith/Postal-Management-System/releases/tag/v0.0.1
