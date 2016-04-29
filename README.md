# graderC
Grader software

GraderC software v1.0
Salih Safa BACANLI
Ph.D. Student at UCF
Written for C programming course
04/29/2016

HOW TO RUN THIS?

put all the .c files in the same directory with grader.jar, config.txt and grader.bat
put all the input and correct output files in the same directory with grader.jar, config.txt and grader.bat

change the config.txt as follows:

Considering that the input files are 

>checkone.in, checkone2.in checkone3.in

and the output files for these inputs are

>checkone.out, checkone2.out checkone3.out

the config file will be 

>inputfilename:checkone.in, checkone2.in checkone3.in

>outputfilename:checkone.out, checkone2.out checkone3.out

>checkfilename:checkone.out, checkone2.out checkone3.out

after these steps run the somefile.bat (double click)

You need to watch the run of that program.Probably till call of somefile.bat you won't face any issue.
For call of somefile.bat, some runs may not stop. In that case you need to stop the execution window.
Edit the somefile.bat and either remove that line that makes the execution not stop or bypass it by goto command.
Continue running the remaining runs.

The last part is for grading. At the end of the run you will see a text file called grades.txt. report.txt file will also be created.

grades.txt will give you enough information if a file is not compiled,empty or too big.
There is a maximmum file size metric. If the output file of one of the runs is greater than 100 MB, the file will not be processed.

Each read line which matches the correct output for each student, the student will get 1 point.

The grader program is not forgiving. Please keep in mind that if the outputs seem to be correct but there are some unexpected lines in the output, the grader will not give score for these.
##Some notes
The grader will only compile 1 C file. you need to have gcc.exe in your system path environment. To learn this write gcc.exe in the command line. If you see something like "command not recognized as internal command" it means, it is not enabled. Please check internet for that.

If the compile file need to read some file (from the same directory) then in that case the job is yours to put that file in each folder.
