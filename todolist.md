Catalog

Clase initale: student, profesor, grupe, course

Clase actuale:
- [x] abstract Account implements GetNameInterface, SetAccountInterface
    - [x] name
    - [x] gender
    - [x] date of birth

- [x] Student extends Account
    - [x] Map<Course, Integer> courses -- courses and grades

- [x] Profesor extends Account
    - [x] List / Array / Colectie<Course
    - [x] method mark(Student, Course, nota)

- [x] Course
    - [x]  name
    - [x]  professor

- [x] Group
    - [x]  name / identifier
    - [x]  List / array / colectie Student
    - [x]  List / array / colectie Profesor
    - [x]  Set Courses

- [x] static Util
    - [x]  arraylistToString
    - [x]  setToString
    - [x]  getRandomNumber
    - [x]  genderStringValidation
    - [x]  stringToDate  

- [x] static Generator
    - [x] private static final List de String coursesNames 
    - [x] create random name
        - un array de nume, iei random 2 din ele si bam ala e numele
    - [x]  create random date of birth
    - [x]  studentGenerator (test purposes)
    - [x]  professorGenerator (test purposes)
    - [x]  groupGenerator (test purposes)
    - [x]  courseGenerator (test purposes)
    - [x]  randomGenderGenerator (test purposes to be able to generate students and professors)

- [x] interface GetNameInterface -> pentru functiile generice setToString si arrayListToString din clasa Util -> pentru printarea mai usoara a acestora 
    - [x] getName()
- [x] inteface SetAccountInteface ->  pentru functia generica createStudentOrProfessor
    - [x] setName()
    - [x] setGender()
    - [x] setDateOfBirth()

- [x] class Meniu
    - [x] print meniu principal
    - [x] optiuni meniu principal
    - [x] print meniu student
    - [x] optiuni meniu student
    - [x] print meniu profesor
    - [x] optiuni meniu profesor
    - [x] create student/profesor -> implementeaza interfata SetAccountInterface
    - [x] add si remove profesor/group/student/course -> comentate pentru ca nu sunt folosite
    - [x] getteri si setteri de liste



- [x] FUNCTII MENIU INTERACTIV
    - [x] initializeze datele de inceput random din functiile din extras.Util // sa se creeze -> am trecut pe creat acum
     - [x]  sa printeze un meniu
       - [x]  creare grupa
       - [x]  creare student -> nu se poate fara o grupa existenta
       - [x]  creare profesor 
       - [x]  creare materie -> se poate alege un profesor existent sau se creeaza unul nou
        - [x] selectezi un user
            - [x] student
                - [x] print materii 
                - [x] print materii si note
                - [x] print nr grupei
                - [x] print grupa si colegi
                - [x] print failed classes
                - [x] go to previous menu
            - [x] prof
                - [x] mark note
                - [x] print materii la care preda
                - [x] print grupe la care preda
                - [x] go to previous menu

Lista undeva in cod cu nume de materii -> generator

- [x] Create group
   - [x]  enter number of students:
        30
    for loop 0 30 face 30 de studenti
- [x]  Create materii
- [x] Create profs
- [x] asign materie to grupa 
- [x] la mark note la prof
    - [x] mark(student, materie, nota)
        - [x] verificare ca proful preda studentului

ETAPA 2:
Raporturi in fisiere de tip '.csv':
- [x] professors that teach to a specific group
- [x] groups enrolled in a specific course
- [x] passed students 
- [x] failed students
- [x] enrolled students in a specific course
- [x] students with courses not graded

ETAPA 3:
- [x] setup MYSQL as DB 
- CRUD = create retrieve update delete
