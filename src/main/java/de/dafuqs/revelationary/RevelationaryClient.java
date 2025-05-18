package de.dafuqs.revelationary;

public class RevelationaryClient {
	
	public static void onInitializeClient() {
		RevelationaryNetworking.registerPacketReceivers();
	}
	
}
