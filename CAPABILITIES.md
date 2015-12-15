# Capabilities

List of missing capabilities

* The game does not have any other life cells
* NecroMonger cells do not have the capability to exist
* The render and update loops do not have the capability to iterate over all
  the cells in the grid simultaneously. **This capability has been added**
* The user has the capability to watch the grid expand like darkness being pealed away by light,
	where the void is a mess of black and deep dark purple dots and the edges of the grid are 
	wavy solid curves like a cloud that is smoothly moving outward.

List of current capabilities

* The game has the capability to place CellTypes in the grid by dynamically instantiating the GrowCell instance passing the x and y coordinates of the cell.
* The code has the capability to add new GrowCell types as a subclass of GrowCell.
* The code has the capability to update any type of GrowCell on the grid in the updater class.
* The user has the capability to rotate directions by pressing the spacebar.
* The user has the capability of using the current item in their inventory by pressing enter.
** The render and update loops have the capability to iterator over all cells in a grid simultaneously **