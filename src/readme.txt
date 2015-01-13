This file explains what each class's purpose is and generally where it fits in

RunMe - The class with the main. Sets everything up, has the grid and listener created on it.

Grid - The primary class where almost every function is. Knows everything and shows everything.



Drawable - Objects on the foreground arraylist of the grid
	Plant - Extends Drawable
		Native - Extends Plant
			CommonMilkweed - One of the two native plants
			Sasafras - the other type of native plant
		Invasive - Extends plant
			JapaneseMaple - One type of invasive
			ButterflyBush - Other type of invasive

Menu - Contains tools
	Tool - Different from tiles, held by menu
		Planting_Tool - doesn't really do anything itself, was from original outline. 
			ButterflyBushTool
			CommonMilkweedTool - types of tools with different attributes but same methods
			JapaneseMapleTool
			SasafrasTool 
		Lawnmower - lawnmower tool

AePlayWave - Straight from labs, used to play the sounds


descriptionScreen - intro screen

endScreen - used to show different endings

Grid_Type - objects on background array of the grid
	Grass_Tile
	Forest_Tile - different types of grid_type
	Meadow_Tile
	Roof_Tile
	Queue_Of_Care

			

