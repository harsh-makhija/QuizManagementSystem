CSC1035 Project 2 (Team Project)
================================
About this project:
-------------------
This program was created as part of a team project for module 1035
at Newcastle University. The aim of this task was to create a quiz
program with a range of functionalities including reading from and writing
to a database.

How to Run:
----------
Before running this program, please ensure you are connected to our team database
and have updated the file "hibernate.cfg.xml" such that the connection url is
up-to-date.

To use this program, make use of the command line interface (found in Main.java)
which has an extensive menu for making use of the functionalities this
program has to offer. This should be relatively clear to use but here are
some assumptions you may need to be aware of:
- When creating a multiple choice question, the correct answer must be inputted as the answer number only and not the answer itself.
- if you enter a menu you did not wish to access (ie. quiz menu) you can return to the main menu by entering anything other than the options listed.
- Many of the options are not case sensitive but when searching by a quiz or question name, I would recommend entering this as seen within the database itself.
- In regards to queries, seach for quizzes by Quiz Name and search for questions by the question itself.
- Please input numbers in integer format and not word format. For example enter 8 instead of eight.
- The command line interface is contained within a while loop, to exit the program please return to the main menu then input 4.
- When making connection to the database make sure to update the connection url within hibernate.cfg.xml.

"Use case" example of this quiz application:
-------------------------------------------

When running this program (Main.java), you should be greeted with a menu that looks something like this:

Choose an option:
1) Quiz
2) Questions
3) Reporting Stats
4) exit

For this menu, input 1, 2, 3 or 4. Any other value should just return you back to this menu.

If the Quiz option is chosen you should be presented with the following choices (input the number of the option as seen with the main menu):

1) Take a quiz - This allows the user to take a quiz and will display their results on completion.


2) Create a quiz - This allows the user to create a generated quiz of a given length, type, topic and name.


3) Delete a quiz - Deletes a quiz from the database with a given name.

If the Questions option is chosen, you will be presented with the following menu (input in the same format as the main menu):

1) Add a question - Allows the user to add a question (MCQ/SAQ) to the db.


2) Update a question - Allows the user to update a value of an existing question.


3) Delete a question - Allows the user to delete a question by typing in the question itself. When you delete a question, be sure to type in the entire question. For example "What is 5+5? 1) 3 2) 56 3) 10" for a MCQ or "What is 7+1?".


4) List questions - Presents the user with an additional menu to choose the how they wish to list questions (ie MCQs only)

Choosing Reporting stats will allow the user to view results of a specific quiz.

Choosing exit will exit the program entirely.

