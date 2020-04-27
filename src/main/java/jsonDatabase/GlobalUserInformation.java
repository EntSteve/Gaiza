package jsonDatabase;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.DiscordApi;
import org.json.simple.JSONObject;

import management.BotInfo;

public class GlobalUserInformation 
{
	private Map <String , String> userIDList = new HashMap<>();
	private static String filePath = "C:\\Users\\Cain\\Documents\\javaDocs\\gaiza\\bin\\Storage\\Users\\uwuData\\";
	private String uwuAmount = "Fine Amount";
	
	public GlobalUserInformation(DiscordApi getApi)
	{
		DiscordApi userApi = getApi;
		
		getUsersList(userApi);
		checkForNewUsers();
		
	}
	
	@SuppressWarnings("unchecked")
	public void blankFileInit(String getUserFilePath)
	{
		JSONObject initData = new JSONObject();
		String filePath = getUserFilePath;
		
		initData.put(uwuAmount, "0");
		
		try
		{
			Files.write(Paths.get(filePath), initData.toJSONString().getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void checkForNewUsers()
	{
		File createNewFile;
		boolean fileExists;
		String checkFilePath = "";
		int i;
		
		for (Map.Entry<String, String> mapElement : userIDList.entrySet())
		{
			checkFilePath = mapElement.getValue();
			fileExists = new File(checkFilePath).exists();
			
			if (!fileExists)
			{
				try
				{
					createNewFile = new File(checkFilePath);
					createNewFile.createNewFile();
					blankFileInit(checkFilePath);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void getUsersList(DiscordApi getApi)
	{
		DiscordApi userInitApi = getApi;
		ArrayList<String> generalUserInfo = new ArrayList<String>();
		DatabaseLL getServersLL = InitDatabase.getCurrLL();
		
		int usersInServer = 0;
		int i, j;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			for (j = 0; j < userInitApi.getServerById(getServersLL.getCurrServerID(getServersLL, i)).get().getMemberCount(); ++j)
			{
				generalUserInfo.add(userInitApi.getServerById(getServersLL.getCurrServerID(getServersLL, i)).get().getMembers().toArray()[j].toString());
				usersInServer += userInitApi.getServerById(getServersLL.getCurrServerID(getServersLL, i)).get().getMemberCount();
			}
		}
		
		BotInfo.setUserCount(usersInServer);
		
		splitUserID(generalUserInfo);
	}
	
	public void splitUserID(ArrayList<String> userInfo)
	{
		ArrayList<String> userInfoSplit = userInfo;
		String splitString[] = null;
		String userID = "";
		String userIDPath = "";
		
		final int IDINDEX = 2;
		int i;
		
		for (i = 0; i < userInfoSplit.size(); ++i)
		{
			splitString = userInfoSplit.get(i).split(" ");
			userID = splitString[IDINDEX].replaceAll(",", "");
			userIDPath = filePath + userID + ".json";
			
			userIDList.put(userID, userIDPath);
		}
	}	
}