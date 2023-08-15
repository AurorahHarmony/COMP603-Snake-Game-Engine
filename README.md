# Console Snake Game
This project is a console based snake game, written in Java.

# Class Name Ideas
- Main: Creates an instance of Engine and starts it.
- Engine: Handles the game loop and stands up the game.
- InputManager: Broadcasts messages when different types of input are recieved.
- GameState: Contains all stateful information such as the score.
- Snake: Contains information such as length, and previous positions, direction etc.
- Food: Represents a piece of food. Notifies the score and snake when it is eaten.
- Vector2: Structure and helper functions for 2 vectors.
- MessageBus: A singleton which lets other classes subscribe to be notified of messages, or send messages
    - IMessageHandler: Intereface defining the characteristics of any class that wishes to recieve message notifications.
    - Message: A wrapper containing information about a message.
    - MessageSubscriptionNode: Groups a message and a handler before it is added to the processing queue.
- GameSave: Stores information about a current save
- ISaveLoader: Defines methods that all save loaders should have
    - JSON: save / read files from a json file.
       

