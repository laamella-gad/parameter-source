# parameter-source

## What problem does this solve?

Reading configuration parameters during application startup is often a little messy, leading to unnecessary long debugging times.
This tiny library wants to make it a solid experience.

Perceived problems are:
- sloppy null handling leading to confusing null pointer exceptions down the road
- sloppy type conversions (string to int, for example) leading to undescriptive errors
- sloppy file handling when we are dealing with a configuration file leading to undescriptive errors
- messy API's, different for every source of parameters
- arcane implementations making unit testing difficult

Offered solutions are:
- an API that has methods that will either fail when a required parameter is missing, or will deliver a Java 8 Optional when the parameter is not required
- file handling and type conversion work as tight as possible.
If a parameter is correct, it will be delivered, otherwise it will throw an exception so that an application does not start in an undefined state.
- one approach to API naming and much reuse, so that parameter sources will look alike as much as possible.
Extending a class to define your own source is easy.
