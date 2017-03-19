### Project4

- Developed a currency converter including user interactive features using a third party API and deployed on Heroku to test.
- Made an HTTP request to the web service; received and parsed an JSON formatted reply from the web service.
- Recorded the app visiting data in MongoDB and presented them in a web interface dashboard.

#### Task1

1. Implemented a currency converter andriod app in the name of Project4Android and achieved the following features:
   - Use TextView, EditText, Button and Spinner in the app UI.
   - The application does an HTTP GET request in GetonverterResult.java.
   - Receives and parses a JSON formatted reply from the web service.
   - The user can type in another search term and hit Submit.
2. Implement a web application, deployed to Heroku.
   - Use MVC in my project: Model: ConverterModel.java; View: login.jsp, result.jsp; Controller: MyAppServlet.java.
   - Receives an HTTP request from the native Android application.
   - Replies to the Android application with a JSON formatted response.

#### Task2

This task implements a web application to provide data analytics dashboard for visiting logs of the app storing in MongoDB with a login interface.

- The log data contains information such as search amount, search from currency, search to currency, rates for from and to currency based on Euro, visited model type, time.
- Store the log information in MongoDB and query the data using Java API.
- The dashboard displays at 5 data analysis results with updated figures.



