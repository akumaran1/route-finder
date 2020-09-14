## Route Finder
#### Problem:
Write a program which determines if two cities are connected.
List of roads are available in a file.  The file contains a list of city pair(One pair per line, comma separated), which indicates that there's a road between those cities.


```
Boston, New York
Philadelphia, Newark
Newark, Boston
Trenton, Albany
```

### Build from source
```bash
    gradle build
```
### Run the application

Using maven Spring Boot plugin 
``` 
    gradle bootRun 
```
Using Java command line 
```
    java -jar build/libs/route-finder.jar
```


### Execution sample

Example `Boston` and `Albany` are not connected:

[http://localhost:8080/connected?origin=Boston&destination=Albany](http://localhost:8080/connected?origin=Boston&destination=Albany) (result **false**)

Example `Boston` and `Philadelphia` are connected:

[http://localhost:8080/connected?origin=Boston&destination=Philadelphia](http://localhost:8080/connected?origin=Boston&destination=Philadelphia) (result **true**)


### Swagger

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   


