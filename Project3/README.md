### Project3

In this project, it attempts to illustrate several important nonfunctional characteristics of distributed systems. 

- Use digital signatures to authenticate messages. 
-  Use web services to enhance interoperability. 
- Consider various styles of API design.

The idea is to simulate a sensor that sends messages to a service whenever measured temperatures become too hot or too cold. These messages will be sent using HTTP.

Task1 and task2 use SOAP over HTTP. Task 3 uses HTTP alone. In all tasks, the sensor keeps readings (along with time stamps and units of measure and sensor numbers) on the server so that they may be retrieved with two query methods. The sensor messages are signed by the client and authenticated by the server. The query methods are left unsigned. Anyone may read, only sensors may write.

#### Task1 RPC Style API Design

In this Task I write a JAX-WS client that makes calls to a web service.

1. It will call the highTemperature method for sensor 1 – passing data and a signature – reporting on a high temperature sensed at our simulated sensor 1. 
2. It will call on the lowTemperature method for sensor 2 – passing data and a signature – reporting on a low temperature sensed at our simulated sensor 2. 
3. It will again call the highTemperature method for sensor 1 – passing data as before. 
4. It will call the highTemperature method for sensor 1, but with an invalid signature.

In the first three calls, a success message will be returned by the web service. In the last call, an “invalid signature method” will be returned. Your web service client will display the message returned after each of these calls.

After the four calls above, the client will call the getTemperatures method. The client will show the three temperatures recorded on the server. In this call - a simple query - there is no need to transmit a signature. Finally, on the last call, your client will request the last temperature recorded for sensor 1. The return value will be the last sensor reading to arrive at the server (for sensor 1).

#### Task2 API Design Single Message Style

Built a system with the same functional characteristics but using a message style design. That is, pass a single message (containing a single String holding an XML message) to a single JAX-WS service operation. The service will examine the message and if it is a report from a sensor, it will check the signature and, if valid, will store the temperature report. If the message is a query message (get all temperatures or retrieve a particular sensor’s last record) then no signature needs to be checked.

#### Task 3 API Design REST Style

Built a system with the same functional characteristics as above but using a REST style design. That is, make good use of HTTP verbs, status codes and URL’s. Messages coming from the client are signed. The signatures are verified on the server.