/*
Service provider for the WorldService
 */

package nl.hu.v1wac.template.model;

public class ServiceProvider {
	private static WorldService worldService = new WorldService();

	public static WorldService getWorldService() {
		return worldService;
	}
}