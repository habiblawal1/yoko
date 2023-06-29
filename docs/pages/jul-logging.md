# Enabling J.U.L Logging

>A Logger object is used to log messages for a particular system or application component. In other words, it is a means of tracking events that happen when some software runs by capturing and persisting important data and making it available for analysis at any point in time. 

## Why is Logging Used?

To see what is happening within a system, we must use `@Logging `to allow tracing, which can be found in the `@Logging` annotation, in the jupiter package. When a class or test method has the `@Logging` annotation, the `specified java.util.logging `is enabled for it.

Since other logging frameworks (Log4J, SLF4J) are j.u.l-compatible, these should work for them, but have not been tested. 
 
Upon turning on logging and running tests, all logging is collected and printed out - logging up to where the test began and after the test is run. Testify captures any enabled logging into a buffer, and flushes the buffer to the console - before and after a test. 

## Formatting

There are various different formatting options that are used for logging. Testify tells us how many threads are involved in logging while your tests are running.

>Testify prints a key to threads and gives them easily distinguishable nicknames (AAA, BBB, etc) - each with their own unique ID’s.

Instead of timestamps with each logging statement, Testify computes and prints elapsed time which tells us how long into the test we are. This is simply because it is the most helpful when comparing the behaviour between two tests to see which test takes longer to reach a certain point, easing the diagnosis of a potential problem.

Looking at the thread key, we can see that there is only one thread involved on the client. Testify does not have remote part logging implemented yet, so you will not get any trace from the server. The eventual idea is that we collect trace from the server.