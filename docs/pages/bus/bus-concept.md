# Bus Concept

## What is the Bus Concept?

---

The bus concept is used within Testify to help write JUnit tests for a test that is not single-threaded or on a single processor. The bus ensures effective communication between the different threads and processes for a specific test.

### Bus vs Java Concurrency Synchronisation Utilities

---

The reason why the bus was created instead of using one of the many utilities that Java has for concurrency synchronisation is that the utilities Java has usually only works with multiple threads. However, Testify needs to be capable of working with multiple processes as well as multiple threads. This is why the bus concept was implemented within Testify.