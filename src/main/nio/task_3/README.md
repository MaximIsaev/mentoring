Task 3 - FastFileMover

Cost: 30 points.

Write several versions of the FastFileMover utility, which moves a file from one directory to another directory. 
It takes both file paths as a command line parameters. All exceptions must be handled correctly.

Required functionality:

(5 points) It uses FileStreams
(5 points) It uses FileStreams with buffer 100 kb
(5 points) It uses FileChannel
(5 points) It uses NIO 2 File API
After that prepare a performance report based on next requirements.

Measure the time for copying, run on several reference files of different sizes 
(1 Kb, 100 Kb, 10 Mb, 1 GB). On each file, run 1000 times, get the average time.