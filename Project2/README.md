### Project2

In this project, it attempts to illustrate one important nonfunctional characteristic of distributed systems – security. Security is a major concern of many distributed system designers.

#### Task1

This task builds a secure system using symmetric key cryptography. Alice and Bob have a shared secret and use it to encrypt and decrypt communications. It encrypts data before writing it to TCP sockets. The Tiny Encryption Algorithm (TEA) will provide the encryption and decryption. User authentication with user ID’s and passwords is done in the application layer – just above the encryption layer. This is a common theme in modern systems. User names and passwords are passed over an encrypted, but otherwise insecure, channel. 

The clients and the server all are fitted with the same symmetric key. This would normally require that all parties agree beforehand on the value of the symmetric key.

#### Task2

This task extends the work in Task1. It uses asymmetric key cryptography to establish the shared secret. Then, with the symmetric keys in hand, it uses symmetric key cryptography as before. It uses RSA for the asymmetric key cryptography. 

