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
- Hibernate still uses JDBC to communicate with the database.
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

#### Hibernate DDL Schema Generation tool
- Create DDL statements & execute DDL statements to create tables from JPA annotated classes.
- property: hbm2ddl.auto
- spring boot autoconfigures this property - to automatically generate database tables.
- can set the table names, column names, types etc.
- For PROD dbs use Validate or None
  - validate - startup will fail is db schema is wrong
  - without errors could occur at runtime

- FlyWay & Liquid base can be used for schema mgmt 


#### H2 MySql compatibility mode
- Can enable H2 to behave like mysql.

#### V1__init_db.sql
- file in classpath(resources) will be executed to setup db.
- hibernate can initialise the db using a V1__init_db.sql file in the classpath - hibernate executes the script

#### liquibase
- migration - moving code from one system to another
- db migration
- Need to be able to do them: accurately & repeatably
- terminology
  - change set - a set of changes to be applied to db
  - change - a single change
  - changelog - lists change sets to be applied
  - change log params - params that can be changed at runtime
  - preconditions - condition to apply or not
  - context - expression - Run script if...
- best practice - 1 change per change log - easier to handle rollbacks.
- changes should be additive
- spring boot runs liquidbase to apply up to latest changesets
- Way to migrate manual db to liquid base 
- Liquibase maps the XML changeset file to sql - it interprets the xml to generate valid SQL based on db provider.

#### Liquibase errors
 
- ERROR: could not read a hi value - you need to populate the table: book_seq  
  EXPLANATION: This occurs when the sequence table book_seq doesn't have a starting value  
  FIX: Insert a value into the sequence normally 0 for long sequences.

- ERROR: Book table already exists  
  EXPLANATION: I changed the liquibase script file names which created the book table.  
               Liquibase saw these new files in the master and tried to run them.  
  FIX: Update the DB with the new changelogs names.  
  ```sql
  SET SQL_SAFE_UPDATES = 0;
  UPDATE bookdb.databasechangelog
  SET FILENAME = 'db/changelog/changelog-v2-init-hibernate.xml'
  WHERE FILENAME = 'db/changelog/init-hibernate.xml';
  SET SQL_SAFE_UPDATES = 1;
  ```

#### Flyway 
- org.flywaydb.flyway-core 
- baseline command is used to migrate an existing db to flyway.
- config:
  - spring.flyway.user
  - spring.flyway.password
- resources/db/migration/V1__<description>.sql, V2__<description>.sql, V3__<description>.sql

#### Spring Web
- @RunWith(SpringRunner.class)  
  @SpringBootTest  
  Brings up spring context therefore it's an Integration test not a unit test. 
- @ResponseCode()
- etc.

#### Dependency Injection
- Field DI:
  - Don't make Service field in the controller private, as you won't be able to unit test it.
- Constructor DI:
  - Best
  - Can pass in mocked object(service) to the controller for testing.
  - can make parameter to constructor private & final.
  - So that the dependency cannot be changed.
- Setter based DI:
  - Has its downsides as you can instantiate the class without setting the service dependency:  
    Leading to an invalid state.
  - Can also set the service at runtime which could be problematic.

Should program service against an interface in order to write test implementation which may be a mock or something else.


#### Primary Key generation
- UUID 
- Long
- @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
  - SEQUENCE
  - TABLE
  - AUTO - let hibernate decide
  - IDENTITY - rely on DB to increment id value - efficient
- Using IDENTITY for mysql dbs better than using sequence as the sequence table needs to be locked which could slow db additions to the book table.
```xml
<!--Liquibase table definition-->
<column name="id" type="BIGINT" autoIncrement="true">
  <constraints nullable="false" primaryKey="true"/>
</column>

<!--Or you can alter an existing table to be auto_increment-->
<changeSet author="William Walsh" id="8">
    <sql>alter table book change id id BIGINT auto_increment</sql>
</changeSet>
```

#### Run config profile
This will take the property config that is specified in the application-XXXXX.properties file.  
-Dspring.profiles.active=local,clean  
application-local.properties - local config  
application-clean.properties - cleans the schema  


