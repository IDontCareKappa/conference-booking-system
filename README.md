<h1  align='center'>Conference booking system</h1>

## Application launch

I recomend to use IntelliJ IDEA to run the application, here's how you can do it:
```
 1. Download the code
 2. Run IntelliJ IDEA
 3. Select open project
 4. Select the folder with the project
 5. Select "Trust project"
 6. In the IntelliJ project tree go to src/main/java/com.example.conferencebookingsystem/
 7. Right click on ConferenceBookingSystemApplication and select Run 'ConferenceBookingSys...' or open this file and press Ctrl+Shift+F10
```

## Available endpoints

If you run this application on your own machine than main address will be ```localhost:8080```, for example: ```localhost:8080/api/users```

Ther's some dummy data in the main class that allows to test the api

Request type | Description | Route | Example
-------------|-------------|-------|--------
  GET | Conference schedule | /api/schedule | Same as route
  GET | User schedule | /api/user/{UserLogin} | /api/user/mwharmby8
  GET | All registered users | /api/users | Same as route
  GET | Lectures by interest | /api/lectures | Same as route
  GET | Thematic paths by interest | /api/topics | Same as route
  POST | Register user for lecture | /api/reservation?lectureId=&login=&email= | /api/reservation?lectureId=4&login=John&email=john@gmail.com
  PATCH | Update user email | /api/update?login=&newEmail= | /api/update?login=mwharmby8&newEmail=mwharmby8@symantec.com    
  DELETE | Cancel user reservation | /api/cancel?lectureId=&login= | /api/cancel?lectureId=8&login=mwharmby8

I recommend using Postman as a web client to test the api
 
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
  - Java
  - Spring Boot
  - Hibernate
  - H2/JPA
  - Lombok
* Tools
  - IntelliJ IDEA
  - Postman
