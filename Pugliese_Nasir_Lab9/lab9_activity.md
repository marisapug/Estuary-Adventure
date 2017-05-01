Git Intro Activity
==================

A. Form teams
-------------

Form a 2-person team. Try to find someone who uses the same platform as you (e.g., Windows, Linux, etc.). If you can't, that's fine. If you are the odd-person-out, join a team of 2.

Complete the exercises below, and fill in your answers in the spaces provided below each question.
Once you have completed all the exercises and filled in this file with your answers, 
add this file to your Lab 9 directory (which you will create in section G) and push it to your team repo.
This file is the deliverable for this lab, so make sure you do not forget to push it at the end!

1.  List your team members below.
    Marisa Pugliese
    Maaz Nasir



D. Getting help
---------------

Run the following commands.

    git help
    git help -ag
    git help init

1.  What does `git help` do?

    Offers a list of common git commands and a brief description of its use

2.  What does `-ag` cause `git help` to do?

    Offers a list of available subcommands

G. Basic commands
-----------------

Open the terminal. Navigate to your team’s repository directory.

Create a directory inside of your team repository which will act as your submission directory for this lab.
The directory should be named according to the last names of your team members following the format below.

    <member1LastName>_<member2LastName>_Lab9

Use a plain text editor to create `names.txt` inside the directory you just created.
Put the names of your team in the file. Save and exit.

Run `git status` before and after each of these commands.

    git add names.txt
    git commit –m “Add our names.”
    git log

1.  What kind of information does `git status` report?

    git status reports all modified files

2.  What does `git add names.txt` do?

    adds the file to the directory

3.  What does `git commit -m "Add our names."` do?

    commits the file and adds the message "Add our names."

Use a plain text editor to create the following files:

-   `birthdays.txt` - Put your birthdays in this file. (you are not obligated to use real information here)

-   `movies.txt` - Put the last movie each of you watched in alphabetical order.

Run `git status` before and after each of these commands.

    git add .
    git commit		Note:  Commit will open the vim editor; write a multi-line commit
						   message, save and quit (press esc and then type :wq).
    git log

4.  What does `git add .` do? What do you think `.` means?

    adds/stages all new and modified files to the current directory (not deleted)
    includes files whose names begin with .

5.  What does `git commit` (without -m) do?

    records changes to the LOCAL repository -- will direct you to write a message

6.  If you want to write a more detailed commit message (which is
    good practice) what command would you use?

    git commit
    ```

7.  What does `git log do`?

    shows recent pushes in the repository


H. Stage/Cache/Index
--------------------

Do the following:

-   Modify `names.txt` so that names are listed in *Last, First* format,
    one per line.

-   Modify `movies.txt` so they are in reverse alphabetical order
    by title.

-   Create a new file `foods.txt` that contains your favorite foods (one
    for each team member).

Run the following commands:

    git add names.txt
    git status

1.  Below write each file name under the state that its changes are
    currently in. Compose a definition for each state.

    **Staged**

    names.txt
    add the file to the queue of files that will be committed



    ```

    **Unstaged**

    movies.txt
    changes were made but not added to the repository



    ```

    **Untracked**

    foods.txt
    file created locally


    ```

1.  If you run `git commit` what changes will be committed (***DON’T DO IT***)?

    ```
    names.txt will be committed *nothing else*


    ```

2.  What command do you run to stage changes?

    ```
    git add


    ```

3.  What command do you run to unstage changes?

    ```
    git reset 


    ```

Run the following commands:

    git diff
    git diff --cached

1.  What does `git diff` display?
    tells you the changes you have made

2.  What does `git diff --cached` display?
    displays changes that are staged to be committed

3.  Formulate a sequence of commands to unstage changes to `names.txt`,
    and stage the changes to `movies.txt`. Execute your commands and
    confirm they worked.

    git add movies.txt
    git reset names.txt


4.  Edit `movies.txt`, change any one of the movies, and save it. Then
    run `git status`. What do you observe? Explain what you think is
    going on.
    movies.txt is staged and unstaged.
    the original version has been staged for commit but the changed version has not

5.  Delete `names.txt`. Then run `git status`. What do you observe?
    Explain what you think is going on.
    names.txt is unstaged
    The file was deleted locally, and git knows that a change was made

6.  Rename `movies.txt` to `last-movies`. Run `git status`. Observe
    and explain.
    last-movies is untracked because it is considered a "new" file

7.  Formulate a sequence of commands to stage all changes including the
    untracked file and commit (with any reasonable message you like).
    Execute them.
    git add .
    git commit

8.  In git vernacular, `index`, `cache`, and `stage` all refer to the
    same thing. What does it hold?
    it is the queue that holds files to be committed
    ```

9.  Why have a `stage`? Why not just commit all changes since the last
    commit?
    gives the programmer more control

I. Undo
-------

Run the following commands:

    git log
    git status
    git reset --soft "HEAD^"
    git log
    git status

1.  What does `git reset --soft ``"HEAD^" `do?
    "undo" button

Run the following commands:

    git commit –m "Redo."
    git log
    git status
    git reset --hard "HEAD^"
    git log
    git status

1.  What does `git reset --hard ``"HEAD^"`` `do?
    undoes commit --> updates local files to match the repository on github

2.  What is the difference between `--hard` and `--soft`?
    --hard deletes changes, --soft does not

3.  What do you think `HEAD` means?
    current branch

4.  What do you think `HEAD^` means?
    first parent of the tip of the current branch

J. Helpful resources
--------------------

-   <https://git-scm.com/doc>

-   <https://www.atlassian.com/git/tutorials/>

-   <https://training.github.com/kit/downloads/github-git-cheat-sheet.pdf>

K. Copyright and Licensing
--------------------------

Copyright 2016, Darci Burdge and Stoney Jackson SOME RIGHTS RESERVED

This work is licensed under the Creative Commons Attribution-ShareAlike
4.0 International License. To view a copy of this license, visit
<http://creativecommons.org/licenses/by-sa/4.0/> .
