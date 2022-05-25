/*
 * Jingbo Wang
 */

import java.io.*;
import java.util.*;

public class BasicMaze
{
	final int SIZE = 2; 
	int mazeRow;
	int mazeCol;

	char mazeGame[][];
	int exitedAdrees[] = new int[SIZE];
	int startAdress[] = new int[SIZE];
	static Stack <Integer> markedDirection = new Stack<>();
	
	// constructor
	public BasicMaze(String fileName) throws FileNotFoundException
	{
		toArray(fileName);
		PlayMaze();
	}
	
	public char[][] mazeGameArray(char mazeGame[][])
	{
		mazeGame = new char[mazeRow][mazeCol];
		return mazeGame;
	}
	
	// next stap could go north or not
	public boolean goNorthTest(int[] currentAdress)
	{
		int row = currentAdress[0];
		int col = currentAdress[1];
		row -= 1;
		if(row > -1 && row<mazeRow && col> -1 && col<mazeRow)
		{
			if(mazeGame[row][col] != '#' && mazeGame[row][col] != '^')
			{
				return true;
			}
		}
		return false;
	}
	
	// next stap could go east or not
	public boolean goEastTest(int[] currentAdress)
	{
		int row = currentAdress[0];
		int col = currentAdress[1];
		col += 1;
		
		if(row > -1 && row<mazeRow&& col> -1 && col<mazeRow)
		{
			if(mazeGame[row][col] != '#' && mazeGame[row][col] != '^')
			{
			  return true;	
			}
		}
		return false;
	}
	
	// next stap could go south or not
	public boolean goSouthTest(int[] currentAdress)
	{
		int row = currentAdress[0];
		int col = currentAdress[1];
		row += 1;
		if(row > -1 && row<mazeRow && col> -1 && col<mazeRow)
		{
			if(mazeGame[row][col] != '#' && mazeGame[row][col] != '^')
			{
				return true;	
			}
		}
		return false;
	}
	
	// next stap could go west or not
	public boolean goWestTest(int[] currentAdress)
	{
		int row = currentAdress[0];
		int col = currentAdress[1];
		col -= 1;
		
        if(row > -1 && row<mazeRow && col> -1 && col<mazeRow)
        {
        	if(mazeGame[row][col] != '#' && mazeGame[row][col] != '^')
    		{
    			return true;	
    		}
        }
		return false;
	}
    
	// if fistDrection = 0, then it not move
	public int[] NotMove(int[] currentAdress)
	{
		currentAdress[0]=currentAdress[0];
		currentAdress[1]=currentAdress[1];
		return currentAdress;
	}

	
	public void markedDirectionPush(int[] temp)
	{
		markedDirection.push(temp[0]);
		markedDirection.push(temp[1]);
	}
	
	public void markedDirectionPop(int[] temp)
	{
		temp[1] = markedDirection.pop();
		temp[0]=markedDirection.pop();
	}
	
	public void findoutRoad(int[] currentAdress)
	{
		int[] temp =new int[2];
	    mazeGame[currentAdress[0]][currentAdress[1]] ='#';
		if(goWestTest(currentAdress) || goSouthTest(currentAdress) 
				|| goEastTest(currentAdress) || goNorthTest(currentAdress))
		{
			if(goWestTest(currentAdress))
			{
				temp[0] =currentAdress[0];
				temp[1] =currentAdress[1] - 1;
				markedDirectionPush(temp);
			}
			if(goSouthTest(currentAdress) )
			{
				temp[0] = currentAdress[0] + 1;
				temp[1] = currentAdress[1];
				markedDirectionPush(temp);
			}
			if(goEastTest(currentAdress))
			{
				temp[0] = currentAdress[0];
				temp[1] = currentAdress[1] + 1;
				markedDirectionPush(temp);
				
			}
			if(goNorthTest(currentAdress))
			{
				temp[0] = currentAdress[0] - 1;
				temp[1] = currentAdress[1];
				markedDirectionPush(temp);
			}
		}
		boolean done = false;
		while(!markedDirection.empty() && !done)
		{
			markedDirectionPop(temp);
			pirntOutAdress(temp);
			if(mazeGame[temp[0]][temp[1]]== mazeGame[exitedAdrees[0]][exitedAdrees[1]])
			{
				done = true;
			}
		    mazeGame[temp[0]][temp[1]] ='#';
		    
			int [] tempDirection  = new int[2];
			if((goWestTest(temp) || goEastTest(temp) || goNorthTest(temp) || goSouthTest(temp)) && temp[0]-1 > -1 )
			{
				if(mazeGame[temp[0]][temp[1]] != mazeGame[exitedAdrees[0]][exitedAdrees[1]])
				{
						if(goWestTest(temp))
						{
							//System.out.println("goWest");
							tempDirection[0]=temp[0];
							tempDirection[1]=temp[1]-1;
							markedDirectionPush(tempDirection);
						}
						if(goSouthTest(temp))
						{
							//System.out.println("goSouth");
							tempDirection[0]=temp[0]+1;
							tempDirection[1]=temp[1];
							markedDirectionPush(tempDirection);
						}
						if(goEastTest(temp))
						{
							//System.out.println("goEast");
							tempDirection[0]=temp[0];
							tempDirection[1]=temp[1]+1;
							markedDirectionPush(tempDirection);
						}
						if(goNorthTest(temp))
						{
							//System.out.println("goNorth");
							tempDirection[0]=temp[0]-1;
							tempDirection[1]=temp[1];
							markedDirectionPush(tempDirection);
						}
				}
				else
				{
					done = true;
				}
			}
		}
	}
	

	public boolean isAlphabetic(int[] temp)
	{
		if(Character.isAlphabetic((mazeGame[temp[0]][temp[1]])))
		{
			return true;
		}
		return false;
	}
	public void pirntOutAdress(int[] currentAdress)
	{
	  if(isAlphabetic(currentAdress))
	  {
		System.out.print(" " + mazeGame[currentAdress[0]][currentAdress[1]]);
	  }
	}
	
	public void PlayMaze() 
	{
		foundStartedAdress();
		foundExitedAdress();
		if(isAlphabetic(exitedAdrees))
		{
			findoutRoad(startAdress);
		}
		else
		{
			System.out.println("Could not get out from the maze");
		}
	}
	
	/**
	 * read "maze.txt" file and store it into mazeGame array
	 * @throws FileNotFoundException
	 */
	public void toArray(String fileName) throws FileNotFoundException
    {
		 File inFile = new File(fileName);
		 Scanner in = new Scanner(inFile);
		 storeMazeGame(in);
		 in.close();
		 File secondInFile = new File(fileName);
		 Scanner secondIn = new Scanner(secondInFile);
		 ReadFile(secondIn);
		 secondIn.close();
    }
	
	// found start adress of the maze game 
	public void foundStartedAdress()
	{
		for(int row = 0; row < mazeRow; row++)
		{
			for(int col = 0; col < mazeCol; col++)
			{
				if(mazeGame[row][col] == '^')
				{
					startAdress[0] =row;
					startAdress[1] =col;
				}
			}
		}
	}
	
	// found exit adress of the maze game 
	public void foundExitedAdress()
	{
			int row = 0;
			int col = 0;
			boolean result = false;
			// Check row 0
			for(col = 0; col < mazeCol; col++)
			{
				result = Character.isAlphabetic((mazeGame[row][col]));
				if(result)
				{
					exitedAdrees[0] =row;
					exitedAdrees[1] =col;
				}
			}
			
			// Check row 7
			row = mazeRow - 1;
			for(col = 0; col < mazeCol; col++)
			{
				result = Character.isAlphabetic((mazeGame[row][col]));
				if(result)
				{
					exitedAdrees[0] =row;
					exitedAdrees[1] =col;
				}
			}
			
			// Check col 0
			col = 0;
			for(row = 0; row < mazeCol; row++)
			{
				result = Character.isAlphabetic((mazeGame[row][col]));
				if(result)
				{
					exitedAdrees[0] =row;
					exitedAdrees[1] =col;
				}
			}
			
			// Check col 7
			col = mazeCol - 1;
			for(row = 0; row < mazeCol; row++)
			{
				result = Character.isAlphabetic((mazeGame[row][col]));
				if(result)
				{
					exitedAdrees[0] =row;
					exitedAdrees[1] =col;
				}
			}
	}
	
	/**
	 * initialize the size of maze game
	 * @param in initialize the Scanner format
	 */
	public void storeMazeGame(Scanner in)
	{
		int row = 0;
		String str = null;
		do
		{
		   str = in.nextLine();
		   row++;

		}while(in.hasNext());
		mazeCol = str.length();
		mazeRow = row;
		mazeGame = mazeGameArray(mazeGame);
	}
	
	// to store the maze in the board
	public void ReadFile(Scanner in)
	{
		int row = 0;
		while(in.hasNextLine())	    
		{
			String str = in.nextLine();
			for(int col = 0; col < mazeCol; col++)
			{
				mazeGame[row][col] = str.charAt(col);
			}
			row++;
	    }
	}
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		@SuppressWarnings("unused")
		BasicMaze basicMaze = new BasicMaze("maze.txt");
	}
}
