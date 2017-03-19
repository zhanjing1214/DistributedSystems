### Project5

Implement the Chandy Lamport Snapshot Algorithm. This algorithm can obtain a global state of a distributed system. The construction also requires the setting up of JMS resources.

The distributed system has five players. Each player has a set of commodities which it trades with the other players. Trades are more like gifts than exchanges. When each player receives a commodity from someone, it gives one of its commodities to another player. It might be the same player, or it may be another player. It picks who to give the Trade to randomly. The trading action therefore is a fast series of accepting commodities from the others and giving commodities to others. 

Each of the 5 players is modeled as a Message Driven Bean. Each of the players instantiates a PITPlayerModel which does all the business (game) logic for the simulation. 

All communication between the players is done by JMS Message Queues. Each player has its own Queue that it listens to. Other players can communicate with the player by sending a message to its Queue.

The servlet allows the system to be initialized (PITsnapshot.java). This servlet will send a series of two messages to each Player's Queue. First it sends a Reset message to each Player and awaits its acknowledgement response. Once all five Players have been reset, it sends a NewHand message to each of the Players with a set of commodities. In this way, each Player is assigned its own initial set of commodities. These commodities are also known as cards. As soon as each Player receives its NewHand, it begins trading.

Trading continues until the maxTrades threshold is hit. This can be adjusted in the PITPlayerModel so the trading does not go on forever.

A new round of trading can then be started by using the PITsnapshot servlet again.

