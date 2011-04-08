JFAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	DataServer.java \
	DataClient.java \
	Data.java


default: classes

classes: $(CLASSES:.java=.class)

server:
	java DataServer

client:
	java DataClient

clean:
	$(RM) *.class
