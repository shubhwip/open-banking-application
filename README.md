# Open Banking Application

## Run
```shell
docker compose up -d
```

### Connect to MySQL
```shell
docker exec -it mysqldb sh
mysql -u root
```

## Learnings
## JPA No Arg Constructor Requirement
Certainly! Let's dive deep into the concept of no-args constructors in JPA entities with some detailed examples to clarify why they're necessary and how they're used.

### Basic Example: Student Entity

Let's start with a simple `Student` entity:

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    // No-args constructor
    public Student() {}

    // Parameterized constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
}
```

Now, let's explore different scenarios where the no-args constructor is crucial:

### Scenario 1: JPA Entity Creation

When JPA needs to create a new instance of `Student`, it uses the no-args constructor:

```java
EntityManager em = // ... obtain EntityManager
Student student = em.find(Student.class, 1L);
```

Here's what happens behind the scenes:
1. JPA creates an empty `Student` object using the no-args constructor.
2. It then populates the fields with data from the database.

Without a no-args constructor, JPA wouldn't know how to create the initial object.

### Scenario 2: Lazy Loading

Consider a `Course` entity with a lazy-loaded collection of students:

```java
@Entity
public class Course {
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Student> students;

    // No-args constructor
    public Course() {}

    // Other constructors, getters, setters...
}
```

When you access the `students` collection:

```java
Course course = em.find(Course.class, 1L);
List<Student> students = course.getStudents(); // Lazy loading occurs here
```

Hibernate creates proxy objects for each student. These proxies are subclasses of `Student` and require the no-args constructor to be instantiated.

### Scenario 3: JPQL Queries

Consider a JPQL query that returns `Student` objects:

```java
TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.age > 18", Student.class);
List<Student> adultStudents = query.getResultList();
```

For each result row, JPA:
1. Creates a `Student` instance using the no-args constructor.
2. Populates the fields with data from the query result.

## Scenario 4: Deserialization

When deserializing a `Student` object (e.g., from cache):

```java
// Assuming we have a serialized Student object
byte[] serializedStudent = // ... serialized data
ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedStudent));
Student deserializedStudent = (Student) ois.readObject();
```

The deserialization process uses the no-args constructor to create the initial object before populating its state.

### Scenario 5: Framework Integration

Many frameworks that work with JPA entities rely on the presence of a no-args constructor. For example, Spring Data JPA's repository methods:

```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeGreaterThan(int age);
}

// Usage
List<Student> adultStudents = studentRepository.findByAgeGreaterThan(18);
```

Spring Data JPA uses the no-args constructor to create `Student` instances when populating the result list.

### Why Not Use Reflection to Set Fields Directly?

You might wonder why JPA doesn't just use reflection to set fields directly without a constructor. While this is technically possible, using a no-args constructor provides several benefits:
1. It's more performant than using reflection for every field.
2. It allows entities to perform any necessary initialization logic.
3. It's consistent with Java beans conventions, making entities more interoperable with other frameworks and libraries.

By understanding these scenarios, you can see how the no-args constructor, despite being empty, plays a crucial role in the JPA ecosystem, enabling flexible and efficient object creation and manipulation across various use cases.

## Repository Pattern
The Repository Design Pattern is a structural pattern that acts as an intermediary layer between an application's business logic and data storage[1]. It provides a centralized way to manage data operations, abstracting the underlying details of data storage technologies[1].

## Key Characteristics

- Abstracts data access, separating the data layer from business logic[1]
- Provides a uniform method for querying data, regardless of the data source type or location[2]
- Acts as a mediator between data and business logic, managing data sources and business rules independently[2]

## Advantages

- Enhances code maintainability, testability, and flexibility[1]
- Simplifies data access code and improves readability[2]
- Allows for easier switching between different data sources without affecting business logic[2]
- Enables performance optimization through caching, batching, or lazy loading of data[2]

## Implementation

The pattern typically involves creating a repository interface or abstract class with methods for common data operations such as create, read, update, delete, and query[2]. This interface can be implemented for specific business objects or as a generic repository for multiple types[3].

## Use Cases

- Web applications for managing database interactions
- APIs and microservices for organizing data access
- Large systems to keep data access logic organized
- Testing environments for creating mock repositories
- Data migration scenarios for smooth transitions between databases[1]

The Repository Pattern is particularly useful in applications where there's a need to work with multiple data sources or where the data access logic needs to be decoupled from the business logic for better maintainability and testability.

Citations:
[1] https://www.geeksforgeeks.org/repository-design-pattern/
[2] https://www.linkedin.com/advice/1/what-repository-pattern-how-can-you-use-your-ozw0f
[3] https://deviq.com/design-patterns/repository-pattern/

Certainly! Let's go through a simple example of the Repository pattern using JPA, and then I'll explain how it works internally.

## Simple Example

Consider a `User` entity:

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    // Constructors, getters, and setters
}
```

Now, let's create a repository interface:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
```

In a Spring Boot application, you can use this repository like this:

```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
```

## How It Works Internally in JPA

1. **Interface Proxy Creation**:
   When your application starts, Spring creates a proxy implementation of your `UserRepository` interface.

2. **Method Invocation**:
   When you call `userRepository.findByEmail(email)`, the proxy intercepts this call.

3. **Query Generation**:
   The proxy analyzes the method name and generates a JPQL query. For `findByEmail`, it might generate something like:
   ```sql
   SELECT u FROM User u WHERE u.email = :email
   ```

4. **Parameter Binding**:
   The method parameters are bound to the query. In this case, the `email` parameter is bound to the `:email` placeholder.

5. **Query Execution**:
   The JPA provider (e.g., Hibernate) executes this query against the database.

6. **Result Mapping**:
   The database results are mapped back to `User` objects using JPA's entity mapping.

7. **Result Return**:
   The mapped `User` object is returned to your service method.

For the `save` method:

1. JPA checks if the entity is new (no ID) or existing.
2. For a new entity, it generates an INSERT SQL statement.
3. For an existing entity, it generates an UPDATE SQL statement.
4. The SQL is executed against the database.
5. The saved entity (with updated ID for new entities) is returned.

This abstraction allows you to work with Java objects and methods, while JPA handles the complex tasks of SQL generation, execution, and object-relational mapping behind the scenes.