This is a Java Api that can rapidly compare two large text files by computing their jaccard Index
It takes in two user Entered File Paths to documents these documents are broken down into small sections
called shingles using a minHash algorithm we compute two sets of shingles one for each document
and find intersection of both sets  then useing the algorithm AnB / (a + b -AnB) we calculate the jaccard index
and display it to console.

Extra functionality
1.allows user to select amount of words per shingle.

