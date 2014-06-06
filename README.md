### Simple Java Bingo
==========
This is a simple Bingo game built by Java with socket API that allows two players to play with each other.

Requirements:

1. Java Runtime Environment 7 and above
2. Both computers contains this copy
3. Local network connection

#### PLATFORM
- Programming language used to develop this program: Java SE7
- Platform used to develop and run the program: Ubuntu 13.10
- Port number of the socket tested on this program: port 4000


#### HOW TO COMPILE AND RUN THE PROGRAM
This home directory consists of two folders:
- cls: the class files generated by the compiler
- src: the source code of the Bingo game

1. To compile the game, simply compile the game in this home directory by the command `javac -d cls src/*.java`. All generated classes will be store or replaced in /cls folder.

2. To start and run the server, direct to `/cls` folder by the command `cd cls`, and then type the command `java BingoServer <port number>`, where <port number> is the port number used by the socket initialized by the server.

3. The game will only be started when two players are connected to the server. To start the game client, the command will be `java BingoClient <ip address> <port number>`, where <ip address> is the ip address of the server, and the port number is the socket port number initialized by the server.