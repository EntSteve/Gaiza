package com.github.cainKaltenbaugh.gaiza;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.*;
import listener.*;
import management.Token;

import java.io.*;

public class gaiza 
{
	static DiscordApi startUp() throws FileNotFoundException
	{
		String token = "";
		Token gaizaToken = new Token();
		
		token = gaizaToken.getToken();

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		
		return api;
	}

	public static void main(String[] args) throws FileNotFoundException 
	{
		DiscordApi startUpApi = startUp();
		
		commandInit(startUpApi);
				
		//System.out.println("invite" + api.createBotInvite());
	}
	
	static void commandInit(DiscordApi initApi)
	{
		DiscordApi myApi = initApi;
		
		System.out.println("Bot command files loading... \n");
		
		Ping pingInit = new Ping(initApi);
		Invite inviteInit = new Invite(initApi);
		Avatar avatarInit = new Avatar(initApi);
		
		System.out.println("\nCommands finished loading!");
		System.out.println("--------------------------------");
		System.out.println("Bot listener files loading... \n");
		
		BoredResponse boredInit = new BoredResponse(initApi);
		
		System.out.println("\nBot listener files loaded! \n");
	}
}
