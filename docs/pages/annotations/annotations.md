# Extending Annotations

Annotations are a form of metadata, which are used to provide data about a program that is not part of the program itself. They are a very generic tool and can be used for anything that processes the code before its run. 

The Annotation system in Java is extensible, as you can create your own annotations with their own meanings. You can also tell the compiler where these annotations go. 

## Examples

There are several annotations that are provided by JUnit. Some examples are:

- **@Test** : this labels a method as a test, so JUnit goes through all of the compiled code looking for anything that has the same annotation and runs it.
- **@BeforeAll** : this is used to show that the method should be excuted before all tests in the current test class.
- **@Override** : this is used to indicate to the compiler that the subclasss method is overriding the superclass method.

These examples have been included in the documentation to provide an insight into the use and effects of annotations.

## Jupiter

Jupiter adds a whole extra level of functionality to annotations because when it looks at a class, it does extra work to help users better comprehend what is happening. Jupiter looks at the annotations used and considers any relevant annotations used on them as if they were on the same class.

## Annotations Within Testify

Testify provides some annotations, two of which are:

- **@ConfigurePartRunner**
    
    >The `@ConfigurePartRunner` annotation allows you to get a part runner in your setup and your test methods. Having the `@ConfigurePartRunner` annotation is simply a shorthand for including `@ExtendWith(PartRunnerExtension.class)` without making that class available. This essentially tells JUnit to load up the `PartRunnerExtension` upon running the class, and to use any extension points that are in this class in the course of running any tests. The `@ConfigurePartRunner `annotation is further explained in the [ConfigurePartRunner](/pages/annotations/part-runner) section.

- **@RetriedTest**
    
    >The `@RetriedTest` annotation labels the method as a test which can be run multiple times until at least one failure occurs. In other words, if at first you do succeed, try until you fail. This allows for tests that are programmatically aborted (e.g. by a failing assumption). If the total number of runs is reached without any successes or failures occurring, no further tests will be run. If there are any successess or failures, the total number of allowed aborted tests increases significantly.

By creating your own annotations in accordance with the [JUnit5 Extension Model](https://junit.org/junit5/docs/current/user-guide/#extensions), Testify can be extended. If you are finding that you are doing the same things over and over again in your tests, create your own annotation.