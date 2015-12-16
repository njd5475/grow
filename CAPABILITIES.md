# Capabilities

List of missing capabilities

* Coder should have the capability to create new rendering methods for cells depending on their surroundings.
* Life cells need the capability to build up energy and to build up the energy of neighboring cells.
* Player has the capability to achieve goals and see that they have achieved the goal as well as get rewarded for achieving the goal.
* Player has the capability to choose when they want to start playing the game as well as being able to pause the game at any time.
* The user has the capability to watch the grid expand like darkness being pealed away by light, where the void is a mess of black and deep dark purple dots and the edges of the grid are wavy solid curves like a cloud that is smoothly moving outward.

List of current capabilities

* The game has the capability to place and update any GameObject 
* The game has the capability to place CellTypes in the grid by dynamically instantiating the GrowCell instance passing the x and y coordinates of the cell.
* The code has the capability to add new GrowCell types as a subclass of GrowCell.
* The code has the capability to update any type of GrowCell on the grid in the updater class.
* The user has the capability to rotate directions by pressing the spacebar.
* The user has the capability of using the current item in their inventory by pressing enter.
* The render and update loops have the capability to iterator over all cells in a grid simultaneously
* The NecroMonger cells have the capability to spawn if the player does not create life in the first 60 seconds of the game.