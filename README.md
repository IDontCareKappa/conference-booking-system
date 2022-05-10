<h1  align='center'>Conference booking system</h1>
 
## Key features
* The user can view the conference schedule,
* After entering your login, you can view the lectures you have subscribed to,
* The user can make a reservation,
* The user can cancel the reservation,
* You may update your e-mail address,
* The system can display a list of registered users with their e-mail addresses,
* The system enables the display of lectures according to the interest of users,
* The system enables the display of thematic paths according to users interests,
* After the reservation has been made, the system sends a notification to the user

## Technologies used to develop application
* Backend
  - Java 11
  - Spring Boot 2.6.7
  - Hibernate
  - H2/JPA
  - Lombok
* Tools
  - IntelliJ IDEA
  - Postman

## Application launch

I recomend to use IntelliJ IDEA to run the application, here's how you can do it:
```
 1. Download the code
 2. Run IntelliJ IDEA
 3. Select open project
 4. Select the folder with the project
 5. Select "Trust authors"
 6. In the IntelliJ project tree go to src/main/java/com.example.conferencebookingsystem/
 7. Right click on ConferenceBookingSystemApplication and select Run 'ConferenceBookingSys...' or press Ctrl+Shift+F10
```

## Available endpoints

If you run this application on your own machine than main address will be ```localhost:8080```, for example: ```localhost:8080/api/users```

Request type | Description | Route | Example
-------------|-------------|-------|--------
  GET | Conference schedule | /api/schedule | Same as route
  GET | User schedule | /api/{UserLogin} | /api/John
  GET | All registered users | /api/users | Same as route
  GET | Lectures by interest | /api/lectures | Same as route
  GET | Thematic paths by interest | /api/topics | Same as route
  POST | Register user for lecture | /api/reservation?lectureId=&login=&email= | /api/reservation?lectureId=4&login=John&email=john@gmail.com
  PATCH | Update user email | /api/update?login=&newEmail= | /api/update?login=John&newEmail=john25@gmail.com    
  DELETE | Cancel user reservation | /api/cancel?lectureId=&login= | /api/cancel?lectureId=4&login=John

I recommend using Postman as a web client to test the api
