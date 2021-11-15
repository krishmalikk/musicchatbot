/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hello, let's talk about music. "+ generateRandomMusicQuestions();
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
		}

		else if (findKeyword(statement, "Who is the best artist") >=0)
		{
			response = "Kanye West" + generateRandomMusicQuestions();
		}
		else if (findKeyword(statement, "Sure") >=0  || findKeyword(statement, "Yeah") >=0 || findKeyword(statement, "Yes") >=0 )
		{
			response = generateRandomMusicQuestions();

		}
		else if (findKeyword(statement, "artist") >=0 )
		{
			response = "That's great. My favorite artist is Kanye. " + generateRandomMusicQuestions();
		}
		else if (findKeyword(statement, "band") >=0 )
		{
			response = "Sounds like a great band. My favorite band is Coldplay. " + generateRandomMusicQuestions();
		}

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

		else if (findKeyword(statement, "Do you like", 0) >= 0)
		{
			response = artistStatements(statement);
		}
		else if (findKeyword(statement, "Do you like the song", 0) >= 0)
		{
			response = songStatements(statement);
		}
		else if (findKeyword(statement, "Do you like the artist", 0) >= 0 )
		{
			response = artistStatements(statement);
		}
		else if (findKeyword(statement, "Do you like the genre", 0) >= 0 )
		{
			genreStatements(statement);
		}

		else if (findKeyword(statement, "Soundcloud", 0) >= 0 || findKeyword(statement, "Spotify") >=0 )
		{
			response = "That's a great platform. Personally I like Spotify. " + generateRandomMusicQuestions();
		}

		else if (findKeyword(statement, "genre", 0) >= 0 )
		{
			response = "I like many songs from that genre. I like indie music. " + generateRandomMusicQuestions();
		}
		else if (findKeyword(statement, "minutes", 0) >= 0 || findKeyword(statement, "hours", 0) >=0 || findKeyword(statement, "days", 0) >=0 )
		{
			response = "Sounds like a reasonable amount of time. I listen to music 24/7. " + generateRandomMusicQuestions();
		}
		else if (findKeyword(statement, "song", 0) >= 0)
		{
			response = "I love that song too. My favorite song is Allstar by Smash Mouth. " + generateRandomMusicQuestions();
		}

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse();
			}
		}
		return response;
	}

	
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}


	private String artistStatements(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("?"))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "Do you like", 0);
		String restOfStatement = statement.substring(psn + 11).trim();
		return "I haven't heard of them, but I'll listen to " + restOfStatement + " later. " + generateRandomMusicQuestions();
	}
	private String songStatements(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("?"))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "Do you like the song", 0);
		String restOfStatement = statement.substring(psn + 26).trim();
		return "I like " + restOfStatement + " too" + "I like My favorite song is All Star. Thanks for asking. If you want, you can ask me another question";
	}
	private String genreStatements(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("?"))
		{
			statement = statement.substring(0, statement.length() - 1);
		}
		int psn = findKeyword(statement, "Do you like the genre", 0);
		String restOfStatement = statement.substring(psn + 27).trim();
		return restOfStatement + " is cool" + "My favorite genre is Indie, sometimes Rap music. Depends on the mood. Ask me more questions.";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more.";
		}
		else if (whichResponse == 1)
		{
			response = "I'm sorry. Could you say that again?";
		}
		else if (whichResponse == 2)
		{
			response = "I'm not sure.";
		}
		else if (whichResponse == 3)
		{
			response = "You don't say.";
		}

		return response;
	}

	private String generateRandomMusicQuestions() 
	{
		final int number_of_questions = 6;
		double randomNumber = Math.random();
		int indexOfResponse = (int)(randomNumber * number_of_questions);
		String response = "";
		if (indexOfResponse  == 0)
		{
			response = "Who's your favorite artist?";
		}
		else if (indexOfResponse  == 1)
		{
			response = "What's your favorite genre of music?";
		}
		else if (indexOfResponse  == 2)
		{
			response = "What's your favorite song?";
		}
		else if (indexOfResponse  == 3)
		{
			response = "What do you normally listen to your music on?";
		}
		else if (indexOfResponse == 4)
		{
			response = "How often do you listen to music?";
		}
		return response;
	}

}
