## Advanced Object Orientated Programming Project

# Grade Book

The main focus of this project is the creation and usage of a `console-app` gradebook that keeps track of the grades given by professors in a local database.

## Table of Contents
[1. REQUIREMENTS](#requirements)

[2. PROJECT STRUCTURE](#project-structure)

[3. DATABASE IMPLEMENTATION](#database-implementation)

[4. EXAMPLE CODE SNIPPLETS AND IMAGES](#code-snippets-and-images)  
<sub>*Update with code snippets and DB screenshots coming soon<sup>TM</sup>*</sub>



## [1. REQUIREMENTS](#requirements)

The projects requirements were devided into three stages:

#### [STAGE ONE](#stage-one)
    
Long story short, we neded to have a few classes saved in the local memory that will be used in a console menu with the purpose of achieving different scenarios while using the `CRUD operations` (`CREATE`, `RETRIEVE`, `UPDATE`, `DELETE`).

While working on the project we had to keep in mind and use different elements or concepts:

- [x] polymorphism   
- [x] arrays   
- [x] sorting   
- [x] java 8+ elements   
- [x] abstract classes   
- [x] interfaces   
- [x] excetions and collections + lambda expressions   
- [x] comparators  
- [x] files reading / writing   
- [x] generic methods  

The stage one version is at [this location](https://github.com/ralucafritz/aoop-project/tree/b9507660804292f38c785dca7a83d2db31ff4737) and after a few days I discovered a small bug in the menu of the application because of some issues with `scanner.next()` and `scanner.nextInt` that needed a `scanner.nextLine()` in order for the next read with the help of a scanner to be preformed properly, which was fixed at [this location](https://github.com/ralucafritz/aoop-project/tree/e740b064ba55a2e4c797b670a7d2db6d52d6fb7e).

#### [STAGE TWO](#stage-two)

Generate 2-4 reports that follow the pattern of the scenarios from stage one.

The stage two version is at [this location](https://github.com/ralucafritz/aoop-project/tree/99ff7abca04e9ceaaad7b9feabc2eb2300b30af5) after a few bug fixes and updates.

#### [STAGE THREE](#stage-three)

All the `CRUD operations` from [STAGE ONE](#stage-one) shall be made on objects found in the DATABASE.

The stage three version is also the last version, available at [this location](https://github.com/ralucafritz/aoop-project).



## [2. PROJECT STRUCTURE](#project-structure)

When I started working on this project I have tried building a UML diagram that can be found at [this location](aoop-project.xml).  
The structure of the project went through multiple changes since the initial plan, but for the first 2 stages I have used the [todolist.md](todolist.md) file in order to keep track of the different concepts and ideas I wanted to implement. 

Along the way I had to delete, change or even add new elements to that list. All the changes can be found in the commits section. Word of caution, due to my inexperience the commits comments are not consistent and also not very accurate for all the changes made.

#### CLASSES 

Main classes:

- `Account` = abstract class used as template for the `Student` and `Professor` classes
- `Student` = extends the class Account 
- `Professor` = extends the class Acount
- `Group` 
   Because in Romania we are divided groups of 20-30 students in order to attend laboratories or seminars, I used it as a template for this `Grade Book`

- `Course` 

  Special connection between the main clases:  
  - a Student is part of a group  
  - a Group is enrolled in a Course  
  - a Professor is teaching a course and by association, the group that is enrolled in that course  
  - classes attended by Students, tought by Professors  

Extra classes:

- `Interfaces`  
  - `GetNameInterface`  
  - `SetAccountInterface`  
  - Both of the interfaces lack in methods but their main goal was to help with    the creation of generic methods in order to reduce the chance of duplicated    code  

- `Gender`  
    Enum class used for the gender of a person(`M`,`F`,`UNKNOWN`) - lacks in  the overall possible choices
- `GradesOptions`  
  Enum class used for the grades status(`PASSED`,`FAILED`,`NOT GRADED`)
- `Generator`  
  This class was made solely for the purpose of generating different objects: students, professors, groups, courses, dates of birth, names, retrieve a random gender. This class was used mainly in the early stage of the project when pre-made data was needed to test different methods. 
- `Util`   
  This class is used for most of the useful methods used across the project in order to optimize different aspects or to reduce the risk of duplicated code ++ methods for data validation and data formatting.
- `CsvReports`  
  Used for the creation of `.csv` files and the read/write process of the reports
- `Repositories`  
  - `StudentRepository`  
  - `ProfessorRepository`  
  - `CourseRepository`  
  - `GroupRepository`  
  - `UtilRepository`  - the `Util` class's version of the `Repositories`  
  - Singleton classes used mainly for the connection between the Database and the local data with CRUD operations
- `Menu`  
  A singleton class that holds most of the information while the application runs. It is also the place where the data from the DB is being loaded into, the data is loaded into specific lists. 
  This class went through multiple changes along the stages.

#### MENUS

- `Main Menu`
  - create/remove a Group/Student/Professor/Course
  - enter `Student Menu`
  - enter `Professor Menu`
  - enter `Reports Menu (stage 2+)`
  - end program  
    
- `Student Menu`
  - print enrolled courses
  - print enroled courses along with their grades
  - print the group number
  - print the group's students list
  - print failed classes(if any)
  - previous menu  

- `Professor Menu`
  - print the courses that the selected proffesor teaches
  - mark grades for a specific course
  - print the groups enrolled in a specific course
  - previous menu  

- `Reports Menu (stage 2+)` - all of the following options are being saved in a specific `report.csv` file
  -  return the professors names for a specific group
  -  return the group that are enrolled in a specific course
  -  return all the students that passed a course and the name of the course
  -  return all the students that failed a course and the name of the course 
  -  return all the students enrolled in a specific course
  -  return all the students without grades
  -  previous menu

## [3. DATABASE IMPLEMENTATION](#database-implementation)

In the last stage I had to do a lot of changes and adapt to different situations because I have realised that the plan and structure I had set up were not 100% compatible with the way a database is implemented. 

More on this can be found at the bottome of the `README.md` file in a [note](#note).


## [4. EXAMPLE CODE SNIPPLETS AND IMAGES](#code-snippets-and-images)
<sub>*Update with code snippets and DB screenshots coming soon<sup>TM</sup>*</sub>


<sub>**KNOWN BUG** - found in the stage three: it was intended that when a course is added, it can only be added to only one group, therefore when it gets deleted, the professor should be checked if he also teaches other courses to the current group when deleting the course, in order to not have a possible error afterwards. Tried fixing this issue, but my solution involved multiple `for` blocks or lamdba expressions that would in the end give a `ConcurrentModificationException`</sub>

---

*[NOTE:](#note) Because of some initial planning issues with the classes and relations between them, I had to improvise when implementing the database, more details at [repository\README.md](https://github.com/ralucafritz/aoop-project/blob/main/src/main/java/repositories/README.md)*

<!-- ![alt text](https://github.com/[username]/[reponame]/blob/[branch]/image.jpg?raw=true)
 -->