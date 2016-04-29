@echo off
java -jar grader.jar compile
java -jar grader.jar run
echo IF YOU NEED TO PUT SOME FILE THAT THE EXECUTABLE WILL READ
echo THEN YOU NEED TO PUT THAT FILE MANUALLY TO ALL FOLDERS
echo please push any key to continue if you have done that.
pause
call somefile.bat
java -jar grader.jar grade
pause
