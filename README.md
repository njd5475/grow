# GrowGridGrow
Simple Cell Based Growing Game

# To Run
Compile and run GrowGridGrow class

# Development
Development will be done in phases each phase consisting of the following

* Identity defects and enhancements design new game mechanics ** ID Phase **
* Work the defects ** BUG SQUASH PHASE **
* Work on enhancements (this includes new content) ** BUILD PHASE **
* Work on new game mechanics ** BRAINSTORM PHASE **
* Polish graphics and interfaces ** SPIT SHINE PHASE **
* Rinse, Release, Repeat ** RELEASE PHASE **

# Design

The grid of empty cells expands at a set rate this expansion creates more and more empty cells. Only empty cells can have life placed on them. You can place life as it is the item you start with in your inventory. You inventory contains a single item. You have goals to achieve in the game the first goal being to place life.

If you do not place life in the first 60 seconds NecroMonger's will start spawning and find and devour you.

# Current Phase
* ** BUG SQUASH PHASE **

Bugs:
* NecroMonger cells do not have the capability to exist ** This capability has been added **
* The render and update loops do not have the capability to iterate over all
  the cells in the grid simultaneously. **This capability has been added**