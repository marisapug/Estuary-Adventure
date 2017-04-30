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

    ```
	Rachel Butler
	Logan Muir
	Natalie Ayling

    ```


D. Getting help
---------------

Run the following commands.

    git help
    git help -ag
    git help init

1.  What does `git help` do?

    ```
	“git help” shows you common git commands and their purposes/how to use them.

    ```

2.  What does `-ag` cause `git help` to do?

    ```

	The “-ag”, when typed at the end of “git help”, shows a list of the git commands available for use (a) and a list of th common git guides with their purpose (g).

    ```

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

    ```
	“git status” reports whether or not your branch is up-to-date with the


    ```

2.  What does `git add names.txt` do?

    ```

	“git add names.txt” adds names.txt to the files/changes to be committed.

    ```

3.  What does `git commit -m "Add our names."` do?

    ```
	“git commit -, “Add our names.”” “commits” all the files added to prepare them to be pushed to the repository (in this case, just names.txt). The “-m “Add out names.”” adds a message for everyone in the group to see, often used to explain the changes that the user made.


    ```

Use a plain text editor to create the following files:

-   `birthdays.txt` - Put your birthdays in this file. (you are not obligated to use real information here)

-   `movies.txt` - Put the last movie each of you watched in alphabetical order.

Run `git status` before and after each of these commands.

    git add .
    git commit		Note:  Commit will open the vim editor; write a multi-line commit
						   message, save and quit (press esc and then type :wq).
    git log

4.  What does `git add .` do? What do you think `.` means?

    ```
	“git add .” adds all of the files that you’ve been working on in that folder to the changes to be committed, the “.” is what makes all of the files to be added.


    ```

5.  What does `git commit` (without -m) do?

    ```
	Without the “-m”, “git commit” takes you to an editor which allows you to enter a message as to why you’re committing the changes that you are; putting “-m” at the end along with a message acts as a shortcut to avoid this screen.


    ```

6.  If you want to write a more detailed commit message (which is
    good practice) what command would you use?

    ```
	
	You would use the regular “git commit” command, without the “-m” at the end, to allow for a more complex message.

    ```

7.  What does `git log do`?

    ```
	It shows you the most recent commits that every member of your team (including yourself) has done.


    ```


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

    ```
	names.txt

	Definition —> You have added this file that has been modified to the group that will be committed next.


    ```

    **Unstaged**

    ```

	movies.txt

	Definition —> You have modified this file, but have not added it to the group that will be committed next.

    ```

    **Untracked**

    ```
	foods.txt

	Definition —> You have created this file locally, but haven’t added it to the group that will be committed next, which means that it is only local and not on the git server.


    ```

1.  If you run `git commit` what changes will be committed (***DON’T DO IT***)?

    ```
	If we run “git commit” now, only names.txt will be committed.

    ```

2.  What command do you run to stage changes?

    ```
	“git add <filename>”

    ```

3.  What command do you run to unstage changes?

    ```
	“git reset HEAD <filename>”


    ```

Run the following commands:

    git diff
    git diff --cached

1.  What does `git diff` display?

    ```

	“git diff” displays the changes you made relative to the index (staging area for the next commit).

    ```

2.  What does `git diff --cached` display?

    ```
	 “git diff —cached” displays the changes you staged for the next commit relative to the named <commit>. 


    ```

3.  Formulate a sequence of commands to unstage changes to `names.txt`,
    and stage the changes to `movies.txt`. Execute your commands and
    confirm they worked.

    ```
	git reset HEAD names.txt
	git add movies.txt


    ```

4.  Edit `movies.txt`, change any one of the movies, and save it. Then
    run `git status`. What do you observe? Explain what you think is
    going on.

    ```
	When I do this, movies.txt is both staged and unstaged. What I think is going on is that the version of the file that I staged (the version without the changed movie) is still staged, but the version that is unstated is the version with the changed movie. 


    ```

5.  Delete `names.txt`. Then run `git status`. What do you observe?
    Explain what you think is going on.

    ```
	When I do this, names.txt is still unstaged, even though the file should not be there. I think that since it has been pushed before, and the file is still in the remote repository, that it just assumes that changes have been made, not knowing that the file was deleted.


    ```

6.  Rename `movies.txt` to `last-movies`. Run `git status`. Observe
    and explain.

    ```
	When I renamed it, the staged file is movies.txt (assuming it’s the one we staged before the changes), the unstaged files were movies.txt (the one before the rename, but after the changed movie) and names.txt (before it was deleted), and the untracked files are foods.txt and last-movies.txt (the renamed movies.txt). I think it’s in the untracked files because, since it has a new name, it is not recognized as being movies.txt.


    ```

7.  Formulate a sequence of commands to stage all changes including the
    untracked file and commit (with any reasonable message you like).
    Execute them.

    ```
	git add .
	git commit -m “for lab - staging all files”


    ```

8.  In git vernacular, `index`, `cache`, and `stage` all refer to the
    same thing. What does it hold?

    ```
	
	The index/cache/stage holds the files that have been staged for commit.

    ```

9.  Why have a `stage`? Why not just commit all changes since the last
    commit?

    ```
	A ‘stage’ is important because it allows you to control what you commit. For example, if you’re working on multiple things at once and only one of them is working, then you can commit just the working one and not mess everything up by pushing projects with bugs.


    ```

I. Undo
-------

Run the following commands:

    git log
    git status
    git reset --soft "HEAD^"
    git log
    git status

1.  What does `git reset --soft ``"HEAD^" `do?

    ```

	“git reset —soft “HEAD^”” undoes the your recent commit.

    ```

Run the following commands:

    git commit –m "Redo."
    git log
    git status
    git reset --hard "HEAD^"
    git log
    git status

1.  What does `git reset --hard ``"HEAD^"`` `do?

    ```
	“git reset —hard “HEAD^” undoes your most recent commit, and also gets rid of all the changes that you made/updates your local files to those on the remote repository.


    ```

2.  What is the difference between `--hard` and `--soft`?

    ```
	“—hard” erases the changes that you’ve made/staged, and “—soft” keeps them.


    ```

3.  What do you think `HEAD` means?

    ```
	I think HEAD is a reference to the main branch that you are currently on.

    ```

4.  What do you think `HEAD^` means?

    ```
	I think “HEAD^” is first parent of the tip of the current branch.

    ```

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
