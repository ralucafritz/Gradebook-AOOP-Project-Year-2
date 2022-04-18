Catalog

- [X]  student, profesor, grupe, course

- [x] abstract Account
    - [x] name
    - [x] gender
    - [x] date of birth

- [x] Student extends Account
    - [x] Map<Course, Integer> courses -- courses and grades
    <!-- - [x] Group -->

- [x] Profesor extends Account
    <!-- - [x] List / Array / Colectie <Group> -->
    - [x] List / Array / Colectie <Course>
    - [x] method mark(Student, Course, nota)

- [x] Course
    - [x]  name
    - [x]  credits
    - [x]  professor

- [x] Group
    - [x]  name / identifier
    - [x]  List / array / colectie <Student>
    - [x]  List / array / colectie <Profesor>
    - [ ]  List / array / colectie <Courses>

- [x] static Util
    - [x]  arraylistToString
    - [x]  setToString
    - [x]  getRandomNumber
    - [x]  genderStringValidation
    - [x]  stringToDate  

- [x] static Generator
    - [x] private static final List<String> coursesNames 
    - [x] create random name
        - un array de nume, iei random 2 din ele si bam ala e numele
    - [x]  create random date of birth
    - [x]  studentGenerator (test purposes)
    - [x]  professorGenerator (test purposes)
    - [x]  groupGenerator (test purposes)
    - [x]  courseGenerator (test purposes)
    - [x]  randomGenderGenerator (test purposes to be able to generate students and professors)

<!-- - [ ] TBD if scripts
    - [ ] fisier pentru studenti
    - [ ] fisier pentru profesori
    - [ ] fisier pentru grupe
    - [ ] fisier pentru materii -->

- [x] class Meniu
    - [ ] init data =  initializeze datele de inceput 
    - [x] method option()
    - [x] method print options() // le ia pe toate de mai sus
SCRISE DAR TREBUIE DEBUGGING:
- [ ] main function => tot in menu class
    - [x] initializeze datele de inceput random din functiile din Extras.Util // sa se creeze -> am trecut pe creat acum
     - [x]  sa printeze un meniu
        - [x] selectezi un user
            - [x] student
                - [ ] print materii si note
                - [ ] print grupa si colegi
                - [ ] "am picat la X?"
            - [ ] prof
               - [ ] mark note
                - [ ] print grupe
                - [ ] print studentii unei grupe
                - [ ] print notele unui student
        - [ ] selectezi o materie
            - [ ] print prof
            - [ ] print grupe
            - [ ] print studenti picati
            - [ ] sau alte chestii procente de nr studenti trecuti

Lista undeva in cod cu nume de materii

- [x] Create group
   - [x]  enter number of students:
        30
    for loop 0 30 face 30 de studenti
    
- [x]  Create materii

- [x] Create profs
    - [ ] number of profs
        la fiecare prof creat se asigneaza o materie random

- [ ] asign prof to grupa
    random din lista de grupe se asigneaza random o materie
 s  
 
- [x] la mark note la prof
    - [x] mark(student, materie, nota)
        - [x] verificare ca proful preda studentului


