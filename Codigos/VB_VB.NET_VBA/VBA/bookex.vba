Option Compare Database
Option Explicit

'*******************************************
' Stephen J. Hegner, 14 November 1997
' This VBA program creates the running database schema of the text of
' ElMasri and Navathe, and inserts the tuples of the running example as
' well.  For the most part, SQL-like statements are used.  However, there
' are a few minor modification which were made to get things to work.
'*******************************************

Dim wrkdefault As Workspace
Dim dbs As Database
Dim path As String

Sub DoItAll()

' This is the top-level procedure which calls the schema-definition
' procedure and the tuple-insertion procedure.

'*********************************************
' Change this path to point to the place at which you want to create
' the database. The database (.mdb file) should NOT exist beforehand.
path = "f:\Courses\dbtk\sql\book1.mdb"
'*********************************************

Set wrkdefault = DBEngine.Workspaces(0)

    CreateSchemaX
    InsertTuplesIntoDatabaseX

End Sub

Sub CreateSchemaX()

' This subroutine contains the SQL "Create Table" commands which define
' the overall schema. There are a few modifications:
' 1. The "Not Null" constraint on the "Hours" attribute of the
'    "Works_On" relation is omitted, since the example database
'    includes a null there!
' 2. The foreign key constraint on relation "Employee" which declares
'    "DNumber" of relation "Department" to be a foreign key is imposed
'    in subroutine InsertTuplesIntoDatabaseX, after the tuples are
'    inserted.  This is necessary to avoid a chicken-and-egg deadlock
'    during database intialization.
' 3. Salary is represented by an integer, since the decimal type is not
'    supported in Access.
' 4. Hours in "Works_On" is represented in "deci-hours," since decimal
'    type is not supported.


    If Dir(path) <> "" _
    Then
        Set dbs = OpenDatabase(path)
    Else
       Set dbs = wrkdefault.CreateDatabase _
         (path, dbLangGeneral)
    End If

dbs.Execute "CREATE TABLE Employee " _
        & "(FName   Varchar(15)     not null," _
        & " MInit    Char," _
        & " LName    Varchar(15)     not null," _
        & " SSN      Char(9)         not null," _
        & " BDate    Date," _
        & " Address  Varchar(30)," _
        & " Sex      Char," _
        & " Salary   Int," _
        & " Superssn Char(9)," _
        & " DNo      Int             not null," _
        & " Constraint pkey_emp primary key (SSN)," _
        & " Constraint fkey_emp1 foreign key (SuperSSN)" _
        & "                      references Employee (SSN)); " _

dbs.Execute "CREATE TABLE Department " _
        & "(DName   Varchar(15)     not null," _
        & " DNumber  Int             not null," _
        & " MgrSSN   Char(9)         not null," _
        & " MgrStartDate Date," _
        & " Constraint pkey_dept primary key (DNumber)," _
        & " Constraint ckey_dept unique (DName)," _
        & " Constraint fkey_dept foreign key (mgrssn) " _
        & "                      references Employee (SSN)); " _
  
dbs.Execute "CREATE TABLE Dept_Locations  " _
    & "(Dnumber     Int     not null," _
    & " Dlocation   varchar(15) not null," _
    & " Constraint pkey_dep_loc primary key (DNumber, DLocation)," _
    & " Constraint fkey_dep_loc foreign key (Dnumber)" _
    & " references Department (DNumber));"

dbs.Execute "CREATE TABLE Project " _
    & "(PName   Varchar(15) not null," _
    & " PNumber Int     not null," _
    & " PLocation   Varchar(15)," _
    & " DNum        Int     not null," _
    & " Constraint pkey_proj primary key (PNumber)," _
    & " Constraint ckey_proj unique (PName)," _
    & " Constraint fkey_proj_1 foreign key (DNum)" _
    & "                        references Department (DNumber));"

dbs.Execute "CREATE TABLE Works_on " _
    & "(ESSN    Char(9)     not null," _
    & " PNo     Int     not null," _
    & " DeciHours   Int," _
    & " Constraint pkey_works_on primary key (ESSN, PNo)," _
    & " Constraint fkey_works_on_1 foreign key (ESSN)" _
    & "                            references Employee (SSN)," _
    & " Constraint fkey_works_on_2 foreign key (PNo)" _
    & "                            references Project (PNumber));"


dbs.Execute "CREATE TABLE Dependent " _
    & "(ESSN    Char(9)     not null," _
    & " Dependent_Name Varchar(15)  not null," _
    & " Sex     Char," _
    & " BDate   Date," _
    & " Relationship Varchar(8)," _
    & " Constraint pkey_depe primary key (ESSN, Dependent_Name)," _
    & " Constraint fkey_depe foreign key (ESSN)" _
    & "                      references Employee (SSN));"

    dbs.Close

End Sub

Sub InsertTuplesIntoDatabaseX()

' This subroutine inserts the tuples from Figure 6.6 into the database.
' Afterwards the foreign key constraint on relation  "Employee" is imposed.

Set wrkdefault = DBEngine.Workspaces(0)

    If Dir(path) <> "" _
    Then
        Set dbs = OpenDatabase(path)
    Else

    End If

  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('James','E','Borg',888665555,#11/11/27#," _
  & " '450 Stone, Houston, TX','M',55000,Null,1);"

  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('Franklin','T','Wong',333445555,#12/8/45#," _
  & " '638 Voss, Houston, TX','M',40000,888665555,5);"
        
  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('John','B','Smith',123456789,#1/9/55#," _
  & " '731 Fondren, Houston, TX','M',30000,333445555,5);"
         
  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('Jennifer','S','Wallace',987654321,#6/20/31#," _
  & " '291 Berry, Bellaire, TX','F',43000,888665555,4);"
        
  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('Alicia','J','Zelaya',999887777,#7/19/58#," _
  & " '3321 Castle, Spring, TX','F',25000,987654321,4);"

  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('Ramesh','K','Narayan',666884444,#9/15/52#," _
  & " '975 Fire Oak, Humble, TX','M',38000,333445555,5);"
        
  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('Joyce','A','English',453453453,#7/31/62#," _
  & " '5631 Rice, Houston, TX','F',25000,333445555,5);"
        
  dbs.Execute "INSERT INTO Employee VALUES" _
  & "('Ahmad','V','Jabbar',987987987,#3/29/59#," _
  & " '980 Dallas Houston, TX','M',25000,987654321,4);"

  dbs.Execute "INSERT INTO Department VALUES" _
  & "('Research',5,333445555,#5/22/78#);"

  dbs.Execute "INSERT INTO Department VALUES" _
  & "('Administration',4,987654321,#1/1/85#);"

  dbs.Execute "INSERT INTO Department VALUES" _
  & "('Headquarters',1,888665555,#6/19/71#);"

dbs.Execute "ALTER TABLE Employee " _
        & "Add Constraint fkey_emp2 foreign key (DNo)" _
        & "                         references Department (DNumber);"
        
  dbs.Execute "INSERT INTO Dept_Locations VALUES" _
  & "(1,'Houston');"

  dbs.Execute "INSERT INTO Dept_Locations VALUES" _
  & "(4,'Stafford');"
    
  dbs.Execute "INSERT INTO Dept_Locations VALUES" _
  & "(5,'Bellaire');"
    
  dbs.Execute "INSERT INTO Dept_Locations VALUES" _
  & "(5,'Sugarland');"
    
  dbs.Execute "INSERT INTO Dept_Locations VALUES" _
  & "(5,'Houston');"
    
  dbs.Execute "INSERT INTO Project VALUES" _
  & "('ProductX',1,'Bellaire',5);"
    
  dbs.Execute "INSERT INTO Project VALUES" _
  & "('ProductY',2,'Sugarland',5);"
       
  dbs.Execute "INSERT INTO Project VALUES" _
  & "('ProductZ',3,'Houston',5);"
    
  dbs.Execute "INSERT INTO Project VALUES" _
  & "('Computerization',10,'Stafford',4);"
    
  dbs.Execute "INSERT INTO Project VALUES" _
  & "('Reorganization',20,'Houston',1);"
              
  dbs.Execute "INSERT INTO Project VALUES" _
  & "('NewBenefits',30,'Stafford',4);"

  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(123456789,1,325);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(123456789,2,75);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(666884444,3,400);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(453453453,1,200);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(453453453,2,200);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(333445555,2,100);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(333445555,3,100);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(333445555,10,100);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(333445555,20,100);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(999887777,30,300);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(999887777,10,100);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(987987987,10,350);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(987987987,30,50);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(987654321,30,200);"
    
  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(987654321,20,150);"

  dbs.Execute "INSERT INTO Works_On VALUES" _
  & "(888665555,20,Null);"

  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(333445555,'Alice','F',#4/5/76#,'Daughter');"
    
  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(333445555,'Theodore','M',#10/22/73#,'Son');"
                    
  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(333445555,'Joy','F',#5/3/48#,'Spouse');"
    
  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(987654321,'Abner','M',#2/29/32#,'Spouse');"
    
  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(123456789,'Michael','M',#1/1/78#,'Son');"
    
  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(123456789,'Alice','F',#12/31/78#,'Daughter');"
    
  dbs.Execute "INSERT INTO Dependent VALUES" _
  & "(123456789,'Elizabeth','F',#5/5/57#,'Spouse');"
    
dbs.Close
   
End Sub