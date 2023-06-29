# Using the Bus across Threads and Sub Processes

## SimpleBus

SimpleBus is the simple base interface for the bus that most processes that use the bus are based on.

| Return Type |	Method Name | Parameters | Description |
| ----------- | ----------- | ---------- | ----------- |
| SimpleBus | put | (String key, String value) | This is similar to how put works on a Map. It puts a value on the bus for the matching key. After it has been put on the bus other threads and processes can access the value. |
| String | get | (String key) | Allows a thread or process to wait for a value to be put on the bus that matches the inputted key. Once it arrives on the bus the value is returned. |
| SimpleBus | onMsg | (String key, Consumer<String\> action) | Performs the action inputted as a parameter once the value matching the inputted key arrives on the bus. You would use onMsg over get when you want to do something asynchronous with a value from the bus. |
| boolean | hasKey | (String key) | Returns true if the key inputted currently has a value on the bus. |
| Bus | forUser | (String user) | This is used if you only want a specific user to look at the value placed on the bus. |
| String | peek | (String key) | It returns the value from the key inputted if it currently exists on the bus. If there is no value then null is returned. Unlike `get` it does not wait for a value to be put on the bus. |


## UserBus

UserBus has a similar interface to SimpleBus The main difference is that on the UserBus, the message you put and get from the bus is specific to a particular user. Within the UserBus interface, there is a `String user()` method which returns the name given to the user of the bus.

## EventBus

EventBus is a more generic version of the SimpleBus, allowing us to no longer be restricted to just using strings on the bus. Oher then having a generic object type for the method, the rest of the interface is the same as SimpleBus. It also improves the SimpleBus by allowing you to have type safe key-value pairs.