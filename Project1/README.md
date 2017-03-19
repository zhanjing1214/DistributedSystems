### Project 1 

This project has five objectives:

- Introduced to GlassFish. GlassFish is an open source application server that implements the latest JEE specification. This tool is used throughout the course. The NetBeans integrated development environment is used to build source code and interact with GlassFish.


- Build first set of distributed systems. These are three small web applications using Servlets and Java Server Pages.
- Introduced to simple mobile device awareness and adapting content to be suitable for either desktop or mobile devices.
- Introduced to the MVC pattern.
- Reflected on the functional and non-functional characteristics (e.g. security, scalability, failure handling, interoperability) of your solutions. There will be questions on the midterm and final exam concerning these characteristics.

#### Task1

Write an index.jsp page that asks the user to enter a string of text data, and to make a choice of two hash functions using radio buttons. The hash function choices should be MD5 and SHA-1, with MD5 being the default. When the submit button is pressed a servlet is executed. The servlet is named ComputeHashes.java. The servlet will compute the appropriate cryptographic hash value from the text transmitted by the browser. The original text will be echoed back to the browser along with the name of the hash, and the hash value. The hash values sent back to the browser should be displayed in two forms: as hexadecimal text and as base 64 notation. 

#### Task2

Implemented a web application that implements a simple desktop and mobile “clicker” for class. The app allows users to submit answers to questions posed in class, and provides a separate URL end point for getting the results of the submitted responses. 

When the user makes a choice and hits “submit”, their answer is stored in your MVC model. The user also has the ability to submit another answer. The web app also works with a mobile browser.

#### Task3

Built an application that displays the flags of countries around the world, and gives a short description about the flag. 

1. The user is presented with a screen with instructions: "Choose a country to display their flag".
2. Upon Submit, the web application  uses screen scraping to find the flag URL and flag description.
3. The response page allows the user to choose another country to display their flag.
4. If a flag description cannot be found, or if a request to cia.gov fails, then an appropriate graceful error message is provided, as well as the ability to " Choose another country…".

Scrape source: https://www.cia.gov/library/publications/the-world-factbook/



