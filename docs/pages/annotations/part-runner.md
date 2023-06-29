# How to use @ConfigurePartRunner

We can use PartRunner as a parameter in test methods. The runner allows to us to fork methods onto another thread or JVM (Java Virtual Machine). 

PartRunner also allows us to use the bus to communicate with the parts of the program that have been forked. This allows for a lot more detail to be passed from the remote process to the test cases.

## How to use PartRunner

- Use a new JVM when forking:

        runner.useNewJVMWhenForking

- Use a new thread when forking

        runner.fork()

Below is a code example of using the `@ConfigurePartRunner` annotation:

        @ConfigurePartRunner
        public class InitialTest {
            static int port;
            private static String lookupURL;

            @BeforeAll
            static void setup(PartRunner runner) {
                // When forking use new Java Virtual Machine
                runner.useNewJVMWhenForking();
                // Create a new part called HelloServer
                runner.fork("HelloServer",
                        // Tell Testify how to start this new part
                        InitialTest::runServer,
                        // Tell Testify how to stop this part
                        bus -> bus
                                // Send a stp request
                                .put(STOP_REQUESTED, 0)
                                // Wait for stopped server response
                                .get(SERVER_STOPPED));
                // Wait for the server to start
                port = runner.bus("HelloServer").get(SERVER_STARTED);
                lookupURL = "//localhost:" + port + "/MessengerService";
                System.out.println(lookupURL);
            }
        }