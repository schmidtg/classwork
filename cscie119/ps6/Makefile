#### Make script to submit the unit 6 problem set for CSCI E-119 
####
#### File:        Makefile
#### Assignment:  ps6 (grad project)
####    DO NOT MODIFY THIS FILE

COURSE = cscie119 

ASST = 6

JAVAC = /nfs/home/l/i/libs1/pub/local/i386/jdk1.6.0_14/bin/javac 

SUBMIT = /nfs/home/c/s/cscie119/pub/bin/submit119

default:
	$(JAVAC) *.java

submit:
	@ $(SUBMIT) $(COURSE) $(ASST) `whoami`

check:
	@ /usr/local/bin/submit contents $(COURSE) $(ASST) 

clean:
	@ rm -f *.class *~ core

