Plants vs Zombies:

Regarding group work distribution, in order to keep the workload distributed evenly, each member was assigned a section of the tests (2-3 test classes each), UML, new milestone implementation of save/load and implementing game builder. The fixes that were made from the previous milestone had to do with how the game works.

Introduction:
The goal of this milestone is to create the implementations of the save/load onto the game as well as add a game builder to the game. The team was able to complete the pre-mentioned requirements.

Change Log: 
Implemented the plantsvszombies project using the MVC design pattern (GUI) and implemented new plants and zombie classes aswell as an save/load. This also included creating a test package which retains all the tests for our model side. Moreover we added a higher experience level by adding pictures instead of letters to represent zombies and plants.

Design Decisions/Reflections: Certain design decisions were made and these are mentioned in the class descriptions. A note for users is that there may be some levels that we made challenging (Level: Hard) in order to give the users a fun experience.



Included Files:

--Model Package:
Action.java
CoolDown.java
CoolDownQueue.java
Game.java
Layout.java
Status.java
Weapon.java

--Plants Package:
Chomper.java
Plants.java
PlantStore.java
PotatoMine.java
ShootingPlant.java
Sunflower.java

--Zombie Package:
WalkingZombie.java
Weapon.java
Zombies.java

--View Package:
Intro.java
View.java

--Controller package:
Builder.java
Controller.java
ObjectPersistance.java
TimeLine.java

--Tests Package:
ActionTest.java
AllTests.java
ChomperTest.java
CoolDownQueueTest.java
CoolDownTest.java
GameTest.java
LayoutTest.java
PlantStoreTest.java
PotatoMineTest.java
RugbyZombieTest.java
ShootingPlantTest.java
SprintZombieTest.java
SunflowerTest.java
TimeLineTest.java
WalkingZombieTest.java

- Assets(Contains images for zombies/plants)
- Complete UML diagram (Image File)
- Complete Sequence Diagram (Image File)

Execution/Termination Instructions
Create a project on a desired IDE and import the project. Go to controller.java and select dropdown menu near the run icon. Then choose Run as Java Application and follow the prompts on the console.
After running the java file the user is prompted with a level choosing option in which, for now, they can only select easy, intermediate and hard options which involve either walking zombies for easy, walking zombies and sprint zombies for intermediate or all three for hard. The higher the level of difficulty is the more zombies introduced to the game there are.
After selecting the level of difficulty, the user can then operate the game board on the bottom and the upper is where the user has control over what plants he'd like to purchase and when to simulate the new wave. In order to purchase a plant the user must firstly have enough points for the certain plant unless their purchase button will not function. Once the user has a sufficient amount of sun points for their plant they must select the plant, press purchase and click on the grid below to place the plant. Once the user is ready they may press the simulate wave button in order to start the game.
To undo/redo the user can use the menu bar on the top and click options where they can either choose between undo and redo.
To run the tests, open the java class labeled AllTests.java. Once the file is open, click the drop down arrow beside the green Run button and choose "Run as JUnit test". We have created a test suite that will run all the test cases for you.
 
Known issues that were fixed: Missing documentation was updated and each class was given its own description aswell as method documentation.
