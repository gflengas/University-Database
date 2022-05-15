# University-Database
The database will contain all the data related to a university department and its operation. For this database, the ER diagram, PostgreSQL relational implementation, and initial data were provided. 

![explanation image](https://github.com/gflengas/University-Database/blob/main/pics/db1.png)

The main tasks of this assignment were resolving around introducing information inside tables( new students, grades on particular courses, etc), retrieving information based on particular criteria (name, id, course participation, etc), triggers and views design. 


An external application for the export of indicative functionality and communication with the PostgreSQL database management system using JDBC protocol was also build.

## Example of a request
Study the following request: Find students who have a surname in the alphanumeric space
from ‘MA to’ MO ’:

![explanation image1](https://github.com/gflengas/University-Database/blob/main/pics/1.png)

Using index: 

![explanation image2](https://github.com/gflengas/University-Database/blob/main/pics/2.png)
![explanation image3](https://github.com/gflengas/University-Database/blob/main/pics/3.png)

We notice that the hash index application in Surname improves the performance even slightly.

Then we applied clustering with the following results:

![explanation image4](https://github.com/gflengas/University-Database/blob/main/pics/4.png)
![explanation image5](https://github.com/gflengas/University-Database/blob/main/pics/5.png)

Clustering leads to a slightly better execution time than the hash index, but at the same time to worse planning time. For this reason the use of a hash index is preferable.
