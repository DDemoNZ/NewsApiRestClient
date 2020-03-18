Stack:
-
*  Maven
* SpringBoot
* RestTemplate
* JUnit5

Additional stack:
-
* Lombok
* Jackson LIB

###API - [News API](https://newsapi.org/) site.

Task
-
Create a client that connects to the News API and retrieve the 5 latest Top-Headlines from the API.

Create an application (e.g. a command line program or a JUnit test) that uses the result of the
 API to print out the "title", the "author", the "date" the headline was published to STDOUT
 (console).
 
Try ty handle errors from the API in the client and to test these error using a Unit-Test. Can
  use the credentials below to authenticate your client against the News API.
  
  ```
    API-Key: fd868cb7d74b41d59cb8f6dc708c521c
    URL: http://newsapi.org/
  ```

#Request parameters:
  In request can change country: 
-
  |||||||||||||||
  |:---:|:---:|:---:|:---: |:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
  |ae|ar|at|au|be|bg|br|ca|ch|cn|co|cu|cz|de|
  |eg|fr|gb|gr|hk|hu|id|ie|il|in|it|jp|kr|lt|
  |lv|ma|mx|my|ng|nl|no|nz|ph|pl|pt|ro|rs|ru|
  |sa|se|sg|si|sk|th|tr|tw|ua|us|ve|za|||

  category:
  -
  ||||||||
  |:---:|:---:|:---:|:---:|:---:|:---:|:---:|
  |business|entertainment|general|health|science|sport|technology|
 
  sources:
  -
  In this param you can mix categories or countries with comma separator.
  
  pageSize 
  -
  ||
  |:---:|
  |int number between 0 and 100|
  |20 is the default value|
  
  apiKey
  -
  ||
  |:---:|
  |your API key which you can take on the site|
 
 #Response parameters
 ||
 |:---:|
 |status (String)|
 |totalResults (int)|
 |articles (array[article])|
 |source (object)|
 |author (String)|
 |title (String)|
 |description (String)|
 |url (String)|
 |urlToImage (String)|
 |publishedAt (String) UTC +000|
 |content (String)|
 