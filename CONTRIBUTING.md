# Contributing to Post Office Management System

First off, thank you for considering contributing to the Post Office Management System! It's people like you that make this project such a great tool for managing postal operations.

## 🎯 Code of Conduct

By participating in this project, you are expected to uphold our Code of Conduct:

- Be respectful and inclusive
- Welcome newcomers and help them learn
- Focus on what is best for the community
- Show empathy towards other community members

## 🤔 How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues to avoid duplicates. When you create a bug report, include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples** to demonstrate the steps
- **Describe the behavior you observed** and what you expected to see
- **Include screenshots** if applicable
- **Note your environment**: OS, Java version, MySQL version

**Bug Report Template:**
```markdown
## Bug Description
A clear description of what the bug is.

## Steps to Reproduce
1. Go to '...'
2. Click on '...'
3. Scroll down to '...'
4. See error

## Expected Behavior
What you expected to happen.

## Actual Behavior
What actually happened.

## Environment
- OS: [e.g., Windows 11]
- Java Version: [e.g., JDK 17]
- MySQL Version: [e.g., 8.0.35]
- Browser: [e.g., Chrome 120]

## Screenshots
If applicable, add screenshots.
```

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion:

- **Use a clear and descriptive title**
- **Provide a detailed description** of the suggested enhancement
- **Explain why this enhancement would be useful**
- **List any alternative solutions** you've considered

**Feature Request Template:**
```markdown
## Feature Description
A clear description of the feature.

## Use Case
Explain the problem this feature would solve.

## Proposed Solution
Describe how you envision this feature working.

## Alternatives Considered
Any alternative solutions or features you've considered.

## Additional Context
Add any other context or screenshots about the feature request.
```

### Pull Requests

We love pull requests! Here's a quick guide:

1. **Fork the repository** and create your branch from `main`
2. **Make your changes** following our coding standards
3. **Test your changes** thoroughly
4. **Update documentation** if needed
5. **Write clear commit messages**
6. **Submit a pull request**

## 💻 Development Setup

### Prerequisites
- JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Your favorite IDE

### Setting Up Your Development Environment

1. **Fork and clone the repository**
   ```bash
   git clone https://github.com/YOUR-USERNAME/Postal-Management-System.git
   cd Postal-Management-System/postalapp
   ```

2. **Set up the database**
   ```sql
   CREATE DATABASE postal;
   ```

3. **Configure application.properties**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/postal
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## 📝 Coding Standards

### Java Code Style

- **Follow Java naming conventions**
  - Classes: `PascalCase`
  - Methods and variables: `camelCase`
  - Constants: `UPPER_SNAKE_CASE`
  
- **Use meaningful names**
  ```java
  // Good
  String senderAddress = parcel.getSenderAddress();
  
  // Avoid
  String s = parcel.getS();
  ```

- **Add comments for complex logic**
  ```java
  // Calculate delivery time based on parcel category and distance
  private int calculateDeliveryTime(Parcel parcel) {
      // implementation
  }
  ```

- **Keep methods focused and small**
  - Each method should do one thing well
  - Aim for methods under 50 lines

### Spring Boot Best Practices

- Use `@Service` for business logic
- Use `@Repository` for data access
- Use `@Controller` for web endpoints
- Inject dependencies via constructor
  ```java
  @Service
  public class ParcelService {
      private final ParcelRepo parcelRepo;
      
      @Autowired
      public ParcelService(ParcelRepo parcelRepo) {
          this.parcelRepo = parcelRepo;
      }
  }
  ```

### Database Practices

- Always use parameterized queries
- Add proper indexes for frequently queried fields
- Use transactions for operations that modify multiple tables
- Keep entity relationships clear and well-documented

### Frontend Code

- Use semantic HTML
- Keep JavaScript modular and organized
- Ensure responsive design (mobile-friendly)
- Test across different browsers

## 🧪 Testing

- Write unit tests for new features
- Ensure existing tests pass before submitting PR
- Aim for meaningful test coverage
- Test edge cases and error conditions

```java
@Test
public void testAddParcel_ValidParcel_ReturnsParcel() {
    // Arrange
    Parcel parcel = new Parcel();
    parcel.setParcelId("TEST001");
    
    // Act
    Parcel result = parcelService.addParcel(parcel);
    
    // Assert
    assertNotNull(result);
    assertEquals("TEST001", result.getParcelId());
}
```

## 📋 Pull Request Process

1. **Update the README.md** with details of changes if applicable
2. **Update documentation** for any new features
3. **Follow the pull request template**
4. **Link any related issues**
5. **Request review** from maintainers
6. **Address review comments** promptly

**Pull Request Template:**
```markdown
## Description
Brief description of what this PR does.

## Related Issue
Fixes #(issue number)

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## How Has This Been Tested?
Describe the tests you ran and the results.

## Checklist
- [ ] My code follows the style guidelines of this project
- [ ] I have performed a self-review of my own code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes

## Screenshots (if applicable)
Add screenshots to help explain your changes.
```

## 🌟 Recognition

Contributors will be recognized in:
- The project README
- Release notes
- Our contributors page

## 📬 Questions?

Feel free to:
- Open an issue with the `question` label
- Reach out to the maintainers
- Check existing documentation

## 🙏 Thank You!

Your contributions make this project better for everyone. We appreciate your time and effort!

---

**Happy Coding!** 🚀
