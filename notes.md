# Spring Data JPA

- Is an abstraction layer.
- Implements the repository pattern.
- Mainly for SQL not for non-SQL
- some noSQL have been implemented as part of Data JPA

### Not good for
- Batch operations
- 

#### Hibernate
- Hibernate is an implementation of JPA
- Hibernate ORM - Data Mapper
- mapping from Objects to Tables and vice versa.
- Hibernate stull uses JDBC to communicate with the database.
- Hibernate creates all the SQL expressions to create the tables.

#### SQL
- ANSI SQL is the standard for the SQL language.
- Implementation of the standard also extend the standard with their own features.

#### JDBC
- Java DataBase Connectivity
- JDBC is the API for connecting to databases.
- Implemntations of JDBC are called Drivers.
- Each DB has its own driver/implementation.
- Each Driver implements the JDBC API + the DB provider extensions.

#### Leakage
  - one paradigm leaking into the other.
  - ID primary key in DB's leaks into the Object paradigm of java.



- Spring Data JPA -> flush operations + batch operations
- @NoRepositoryBean -> Don't create a repository for this Class. CredRepository has this.
  
- We created a constructor that only take the object fields - not the ID
- We created a equals/hashcode method that uses the ID as the only field.


#### SQL Logging
```prop
# Implementation Generic
spring.jpa.show-sql=true
```
```log
Hibernate: drop table if exists book cascade 
Hibernate: drop sequence if exists book_seq
Hibernate: create sequence book_seq start with 1 increment by 50
Hibernate: create table book (id bigint not null, isbn varchar(255), publisher varchar(255), title varchar(255), primary key (id))

SIA ID before saving: null
Hibernate: select next value for book_seq
Hibernate: insert into book (isbn,publisher,title,id) values (?,?,?,?)
Hibernate: select next value for book_seq
Hibernate: insert into book (isbn,publisher,title,id) values (?,?,?,?)
SIA ID after saving: 2
Hibernate: select b1_0.id,b1_0.isbn,b1_0.publisher,b1_0.title from book b1_0
ID: 1, Title: Domain Driven Design
ID: 2, Title: Spring In action
```

#### DBMS - Computer programs for Databases
- define the types of data
- CRUD data
- handle sys admin of databases - users/roles/etc.

#### NoSQL
- key-value store
- document based
- column based

#### Surrogate key
- Type of primary key
- has no business value - only used for ID.

#### Natural Key
- bad practice - as business data can change
- Type of primary key
- Uses one or more data columns e.g. f_name + l_name

#### Constraints
- not null
- unique
- primary key
- foreign key
- check constraint - min, max - user defined - operates on data


#### DDL
- Data Definition Language
- Sets data about data (Metadata) - like data types, constraints, etc.

#### DML
- Data Manipulation Language
- CRUD - INSERT, SELECT, UPDATE, DELETE


#### ACID
- Atomicity
  - All statements must be able to complete
- Consistency
  - Changes don't violate constraints
- Isolation
  - References to the data inside the transaction show the new data
  - References to the data outside the transaction show the original data
- Durability
  - once the transaction completes, the data is committed to DB
  - old data is never read again

#### Problems with ACID
- Lost updates
  - updates at the same time may cause one update to be lost

#### RDBMS
- Relational Database Mgmt System
- Support ANSI SQL
- Have their own commands also

#### Users
- Application should not use the root database account
- App should use an account that can only perform DML commands(not DDL commands).
- Accounts used by an application is called a service account.

#### DB History Facts 
- mariaDb identical API to mysql
- mysql features
  - stored procs
  - triggers
  - cursors
  - updated views
  - query cache
  - sub-selects

#### DB Architectures
- Local non-dedicated server - macbook
- LAMP stack
- Client-Server
  - scaled client-server
    - app servers communicating with db server
    - db server communicates with storage method
  - cluster of dbs
    - higher performance
    - higher cross-talk - keeping the db's aligned
    - redundancy - data is duplicated across cluster

#### Multiple Datatypes across layers
- ANSI SQL datatype
- DB provider - MySQL datatype
- Java Types
- Hibernate datatype

#### Installation
```
brew install mysql

Data directory: /opt/homebrew/var/mysql

```
#### Executing
```
To connect run:
mysql -u root

To restart mysql after an upgrade:
brew services restart mysql

Or, if you don't want/need a background service you can just run:
  /opt/homebrew/opt/mysql/bin/mysqld_safe --datadir\=/opt/homebrew/var/mysql
```

#### Annotations
- @DataJpaTest - Brings up hibernate, h2-db & spring data JPA  
can autowire & invoke BookRepository.  
CommandLineRunner not invoked.

#### Transactional Context in Spring Tests
Single Test -> Single Transactional Context 
When a test is executed the code relating to the DB is in a "transactional context".  
When the statements in this context are completed the whole transactional context is rolled back.
You can cancel this behaviour -> Unadvised -> The sql code in test 1 will influence the sql code in test 2.
```java
// Cancels the transactional context rollback for a specific test.
// OR commit the transaction in the transaction context. 
@Rollback(value = false) OR @Commit

// Can force an explicit test execution order by adding this class level annotation & the method level annotations:
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class
  @Order(1)
  <TEST>
  
  @Order(2)
  <TEST>
```

