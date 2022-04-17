Catalog

- [X]  student, profesor, grupe, course

- [x] abstract Account
    - [x] name
    - [x]gender
    - [x]date of birth


- [x] Student extends Account
    - [x] List / Array / Colectie <Course>
    - [x] Group

- [x] Profesor extends Account
    - [x] List / Array / Colectie <Group>
    - [x] List / Array / Colectie <Course>
    - [ ] private method mark(Student, Course, nota)

- [x] Course
    - [x]  name
    - [x]  credits

- [x] Group
    - [x]  name / identifier
    - [x]  List / array / colectie <Student>
    - [x]  List / array / colectie <Profesor>

- [x] static Utils
    - TBD
    - [x]  create random name
        - un array de nume, iei random 2 din ele si bam ala e numele
    - [x]  create random date of birth

- [ ] TBD if scripts
    - [ ] fisier pentru studenti
    - [ ] fisier pentru profesori
    - [ ] fisier pentru grupe
    - [ ] fisier pentru materii

- [ ] class Meniu
    - [ ]  init data initializeze datele de inceput din fisiere
    - [ ]  method option()
    - [ ] method print options() // le ia pe toate de mai sus

- [ ] main function
    - [ ] initializeze datele de inceput random din functiile din Util
     - [ ]  sa printeze un meniu
        - [ ] selectezi un user
            - [ ] student
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

- [ ] Create group
   - [ ]  enter number of students:
        30
    for loop 0 30 face 30 de studenti
    
- [ ]  Create materii

- [ ] Create profs
    - [ ] number of profs
        la fiecare prof creat se asigneaza o materie random

- [ ] asign prof to grupa
    random din lista de grupe se asigneaza random o materie


- [ ] la mark note la prof
    - [ ] mark(student, materie, nota)
        - [ ] verificare ca proful preda studentului

