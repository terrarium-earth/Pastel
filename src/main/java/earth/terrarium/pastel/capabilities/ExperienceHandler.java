package earth.terrarium.pastel.capabilities;

public interface ExperienceHandler {
	
	/**
	 * Returns the amount of experience stored
	 *
	 * @return The amount of stored experience
	 */
	int getStoredAmount();
	
	/**
	 * Inserts an amount of experience.
	 * Return the amount of experience that could not be stored
	 *
	 * @param amount    The amount of experience to store
	 * @return The overflow amount that could not be stored
	 */
	int insert(int amount, boolean simulate);
	
	/**
	 * Removes amount experience from an ExperienceProviderItem stack.
	 * If there is not enough experience that could be removed do nothing and return false
	 *
	 * @param amount    The amount of experience to remove
	 * @return If there was enough experience that could be removed
	 */
	boolean extractOrFail(int amount);

	/**
	 * Extracts an amount of experience.
	 * Returns how much could be actually extracted
	 *
	 * @param amount    The amount of experience to remove
	 * @return The amount of experience that was able to be extracted
	 */
	int extract(int amount, boolean simulate);
	
	int getCapacity();
	
}
