module KnightProblemWarnsdorffsRule4 where
import Graphics.Gloss -- imports gloss

--to run the following code call the main method
--to have the code just return a list of points call the function solveProblem
--this code is abstract and will work for an n number of tiles, change the variable tileLengthOfBoard to change the length of the board

--declares all the necessary variables
tileLengthOfBoard = 9 -- the length of the board measured with the number of tiles
tileLengthOfBoardAsFloat :: Float
tileLengthOfBoardAsFloat = fromIntegral tileLengthOfBoard :: Float
totalTiles :: Int --the total amount of tiles on the board
totalTiles = tileLengthOfBoard*tileLengthOfBoard
--color of the line, which includes the arrowhead at the tip and the circle at the begining
lineColor = makeColor 0.5 0.5 0.5 1.0 

--declares the main function
main :: IO ()
main 
 =	animate (InWindow "Knight Problem" (tileLengthOfBoard*50, tileLengthOfBoard*50) (5, 5))
                (makeColor 1.0 1.0 1.0 1.0)
		frame

--takes a list of pictures and combines them into one
frame :: Float -> Picture
frame timeS
 = Pictures	(getResult timeS)

--returns the midpoint of the tile co-ordinate that is given
getMidpointFromBox :: [Int] ->Int -> Float
getMidpointFromBox (x:y:xs)(0) = a*50 - tileLengthOfBoardAsFloat*25 + 25
  where a = fromIntegral x :: Float
getMidpointFromBox (x:y:xs)(1) = tileLengthOfBoardAsFloat*25 - 25 - b*50
  where b = fromIntegral y :: Float

--this returns the top left co-ordinate of the tile that is given
getPointFromBox :: [Int] -> Point
getPointFromBox (x:y:z) = (a*50 - tileLengthOfBoardAsFloat*25 + 25, tileLengthOfBoardAsFloat*25 - 25 - b*50)
  where a = fromIntegral x :: Float
        b = fromIntegral y :: Float

--returns the pictures of the chess board and the lines
getResult :: Float -> [Picture]
getResult timeS  = makeChessBoard(tileLengthOfBoardAsFloat-1)(tileLengthOfBoardAsFloat-1) ++startDrawingLines(solveProblem)(timeS)

--creates a triangle for the tip of the line
createTriangle = Color(lineColor)(Polygon[(13,0),(-12, 10),(-12, -10),(13,0)])
--this is used to draw the circle at the begining of the line
startDrawingLines (x:xs)(timeS) = [Color (lineColor) (Translate (getMidpointFromBox(x)(0))(getMidpointFromBox(x)(1))(ThickCircle (5)(10)))] ++ getLines(x:xs)(timeS)

--takes in 2 sets of points and returns an angle in degrees
--used for finding the angle for the tip of the arrow
findRotation (x2:y2:rest2)(x1:y1:rest1) 
 = let numerator = y2-y1
       denomenator = x2-x1
	   in if denomenator < 0 then
        -atan(numerator/denomenator)* 180/pi -180	
       else
        -atan(numerator/denomenator)* 180/pi

--makes the arrow on the end of the line
makeArrowHead :: [Int] -> [Int] -> [Picture]
makeArrowHead(x)(y) = [Translate(getMidpointFromBox(x)(0))(getMidpointFromBox(x)(1))(Rotate(findRotation([a,b])([c,d]))(createTriangle))]
  where a = getMidpointFromBox(x)(0)
        b = getMidpointFromBox(x)(1)
        c = getMidpointFromBox(y)(0)
        d = getMidpointFromBox(y)(1)

--creates a set of lines and the arrow 
getLines :: [[Int]] -> Float -> [Picture]
getLines (x:y:xs) (timeS) 
 =	if timeS > 0.5 then 
		[Color (lineColor)(Line[getPointFromBox (x),getPointFromBox (y)])] ++getLines (y:xs) (timeS-0.5)
	else 
		[Color (lineColor)(Line[getPointFromBox (x),getPointFromBox (y)])] ++ makeArrowHead(y)(x)
getLines (x:xs) (timeS) = [Color (lineColor) (Translate (getMidpointFromBox(x)(0))(getMidpointFromBox(x)(1))(ThickCircle (5)(10)))]
{- 
x and y represents co-ordinates in terms of squeares for example  x = 1 y =2 is the first box in the second row
{x E I | 0 <= x <= tileLengthOfBoard}
{y E I | 0 <= y <= tileLengthOfBoard} 0 = bottom-}


--this is just a simple square which is duplicated and transformed to create the board itself
box1 = Polygon[(0, 0),
				(50, 0),
				(50, 50),
				(0, 50)]

--convert the refrence frame to absolute frame
--i made it so my x and y value when 0 is at the bottem left of the screen, this converts the coordinate system i made to the actual coordinate system that haskell uses
--getValueAtPoint
gvap n = n*50 - 50*(tileLengthOfBoardAsFloat)/(2) -- this represents the position to put the block in while making the board
--just draws the board
makeChessBoard :: Float -> Float -> [Picture]
makeChessBoard(1)(y) = if y > 0 then  makeChessBoard(tileLengthOfBoardAsFloat-2+ a)(y-1)++[translate (gvap 1) (gvap y) (box1)] else [translate (gvap 0) (gvap y) (box1)]
 where a = fromIntegral (mod(tileLengthOfBoard)(2)) :: Float 
makeChessBoard(0)(y) = if y > 0 then  makeChessBoard(tileLengthOfBoardAsFloat-1- a)(y-1)++[translate (gvap 0) (gvap y) (box1)] else [translate (gvap 0) (gvap y) (box1)]
 where a = fromIntegral (mod(tileLengthOfBoard)(2)) :: Float 
makeChessBoard x y = makeChessBoard(x-2)(y)++[translate (gvap x) (gvap y) (box1)]   
 
 
 
----------------------------------------This is the part where it actually solves for the knights rout---------------------------------------------------
--an explanation of the following code is at the bottom of the code
--main method
solveProblem = startProblem(tileLengthOfBoard-1)(0)(createPositionBoard(tileLengthOfBoard)(tileLengthOfBoard))

{-
these two functions work together to make a 2d list that represents the 
position of the board. 0 represents the knight has not been there before, and 1 means the knight
has already been there
-}
--creating board functions
createPositionBoard :: Int -> Int -> [[Int]]
createPositionBoard (x)(0) = []
createPositionBoard (x)(y) = [createPositionRow(x)] ++ createPositionBoard(x)(y-1)

createPositionRow :: Int -> [Int]
createPositionRow (0) = []
createPositionRow (x) = [0] ++ createPositionRow(x-1)
--end of creating board functions
	
--this finds a solution starting from the top left corner and moves horizontally to find one
-- if it returns  [[]] then it could not find an answer
startProblem :: Int -> Int -> [[Int]]-> [[Int]]
startProblem (x)(y)(positionBoard) 
 = 						let outcome = nextPosition([[x,y]])(0)(positionBoard)
							in if outcome == [] then -- make it if outcome == [[11]]
								if x < tileLengthOfBoard then
									startProblem (x+1)(y)(positionBoard)
								else if y < tileLengthOfBoard then
									startProblem (0)(y+1)(positionBoard)
								else 
									[[]]--noSolutionString
							else 
								[[x,y]]++outcome
								
-- currentMove:rest = the list of moves
-- n = just make it 0, its the index at which the game would take the element of, needed for recursion
-- positionBoard is just the a 2d list representing the available positions on the board
nextPosition :: [[Int]] -> Int -> [[Int]] -> [[Int]]
nextPosition (currentMove:rest)(n)(positionBoard)
 = 	if (length (currentMove:rest)) == totalTiles then
		[] -- if the amount of steps the the knight has taken is equal to the total amount of tiles ont he board then your done
	else
		--returns a list of all the possible moves the knight can take at this point
		let	movesList = getMovesList(currentMove:rest)(8)([])(positionBoard)
			newPositionBoard = updatePositionBoard(positionBoard)(currentMove)(0)
			in if n >= length movesList then -- if all the positions from this list have already been tried then move just return a list containing a list which has the integer 11 ([[11]])
				[[11]]
			else let nextMove = (getMovesList(currentMove:rest)(8)([])(newPositionBoard))!!n -- figure out the next move
					in let 	outcome = nextPosition (nextMove:currentMove:rest)(0)(newPositionBoard) -- call this function itself
						in if outcome == [[11]] then -- if this position does not have a valid solution
							nextPosition (currentMove:rest)(n+1)(newPositionBoard) -- then choose the next element in the list
						else
							[nextMove]  ++ outcome -- if it does then add up all the items.
							
{-
currentMove:rest = is the list of all the moves
n = is used for recursion
pml = the list which has all the moves that the knight can take in order from least frequency to most frequency
-- positionBoard is just the a 2d list representing the available positions on the board

this function just returns a list of possible positions that player can move based on its current position
-}										
getMovesList :: [[Int]] -> Int -> [[Int]] -> [[Int]] -> [[Int]]
getMovesList(currentMove:rest)(0)(pml)(positionBoard) = pml
getMovesList(currentMove:rest)(n)(pml)(positionBoard) = let	possiblePosition = possibleMove (n)(currentMove)
													in if checkIfValidPosition (currentMove:rest)(possiblePosition)(positionBoard) then
														let moveFrequency = getMoveFrequency (possiblePosition)(8)
															in if pml == [] then
																getMovesList(currentMove:rest)(n-1)([possiblePosition])(positionBoard)
															else
																getMovesList(currentMove:rest)(n-1)(addItemToList(possiblePosition)(pml)(moveFrequency))(positionBoard)-- getMovesList(currentMove:rest)(n-1)(addItemToList(possiblePosition)(pml)(moveFrequency))(updatePositionBoard(positionBoard)(possiblePosition)(0))
													else 
														getMovesList(currentMove:rest)(n-1)(pml)(positionBoard)
{-
-----list functions
these two functions work together to change the value of the 2d list at the given index to 1
pbr = position board row
pbRest = the rest of the possible board
x:y:rest = a list representing the given position of the knight
currentY = start if off at 0. used for recursion
-}
updatePositionBoard :: [[Int]] -> [Int] -> Int -> [[Int]]
updatePositionBoard (pbr:pbRest)(x:y:rest)(currentY)
 = 	if y ==  currentY then
		[updatePositionRow (pbr)(x)(0)] ++ pbRest
	else -- currentY < y
		[pbr] ++ updatePositionBoard(pbRest)([x,y])(currentY+1)
{-
changes the value of a 1d list of at the given index
x:rest = the list you want to change
currentX = the x value you want to change
actualX = make it 0, it is used for recursion
-}
updatePositionRow :: [Int] -> Int -> Int  -> [Int]
updatePositionRow (x:rest)(currentX)(actualX)
 = 	if actualX == currentX then
		[1] ++ rest
	else -- currentX < actualX
		[x] ++ updatePositionRow (rest)(currentX)(actualX+1)
--- end of the list functions
	
-- this function just adds an item to the list sorting it based on that moves frequency
--	item = item you want to add
--  x:xs = the list you want to add it to
-- frequencyOfMove = the frequency of the move
addItemToList :: [Int] -> [[Int]] -> Int -> [[Int]]					
addItemToList (item)(x:xs)(frequencyOfMove) = if frequencyOfMove < getMoveFrequency(x)(8) then
														[item] ++ [x] ++ xs
													else 
														[x] ++ addItemToList(item)(xs)(frequencyOfMove)
addItemToList (item)(emptyList)(frequencyOfMove) = [item]													

{-
posList = the list of all the positions
newPos = the new position the knight want to go to 

this just checks if the position the knight is going to move to is valid, meaning 
it is inside the board and the knight has not already been there
-}
checkIfValidPosition :: [[Int]] -> [Int] -> [[Int]]-> Bool
checkIfValidPosition (posList) (newPos)(positionBoard) 
 =			 								if positionInBound (newPos) then
												if positionAvailable(newPos)(positionBoard) then--if positionInList (posList) (newPos) then
													True
												else
													False
											else
												False
{-
checks if the knight has already been to the given position or not

x:y:rest = the position the players wants to go
positionBoard = a 2d list of available positions
-}
positionAvailable :: [Int] -> [[Int]] -> Bool
positionAvailable(x:y:rest)(positionBoard)
 = 	if positionBoard!!y!!x == 0 then
		True
	else
		False


{-
x:y:z = a list which contains the x and y co-ordinates of the person
n = is used for recursion, needs to start of 8

returns the number of moves the player can take on the given position
-}											
getMoveFrequency :: [Int] -> Int -> Int
getMoveFrequency (x:y:z) (0) = 0
getMoveFrequency (x:y:z) (n) = if positionInBound(possibleMove (n) ([x,y])) then
									1 + getMoveFrequency ([x,y]) (n-1)
								else
									getMoveFrequency ([x,y]) (n-1) 
{-
x:y:rest = a list containing the x and y cordinates
this function just checks if the position is inside the board
-}
positionInBound :: [Int] -> Bool
positionInBound (x:y:rest) = if x >= 0 && x < tileLengthOfBoard then
											if y >= 0 && y < tileLengthOfBoard then
												True
											else
												False
										else
											False
{-
firstNumber = is used for figuring out which position you want
x:y:z = a list containing the x and y coordinates of the current position 

this function returns a possible new position the knight can take based on the index
-}
possibleMove :: Int -> [Int] -> [Int]
possibleMove (0) (x:y:z) = []
possibleMove (1) (x:y:z) = [(x-1), (y+2)]
possibleMove (2) (x:y:z) = [(x+1), (y+2)]
possibleMove (3) (x:y:z) = [(x+2), (y+1)]
possibleMove (4) (x:y:z) = [(x+2), (y-1)]
possibleMove (5) (x:y:z) = [(x+1), (y-2)]
possibleMove (6) (x:y:z) = [(x-1), (y-2)]
possibleMove (7) (x:y:z) = [(x-2), (y-1)]
possibleMove (8) (x:y:z) = [(x-2), (y+1)]

{-
If we try to use brute force to solve for the knight's rout, then it will take an unreasonable amount of time to solve, 
because there are around 4×10^51 possible sequences. I used Warnsdorff's rule. We move the knight so that we 
always proceed to the square from which the knight will have the fewest onward moves. 
-}