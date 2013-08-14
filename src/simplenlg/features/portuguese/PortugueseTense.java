package simplenlg.features.portuguese;

/**
 * Extension of verb features for Portuguese.
 * 
 * @author de Oliveira
 *
 */
public enum PortugueseTense {
	
	//TODO improve comments based on theory 
	/**
	 * <em>João beija Maria (John kisses Mary)</em>.
	 */
	PRESENT,
	
	/**
	 * The action described by the verb started and finished in the past. For 
	 * example, <em>João beijou Maria (John kissed Mary)</em>.
	 */
	PERFECTIVE_PRETERITE,
	
	/**
	 * <em>João beijava Maria (John used to kiss Mary)</em>.
	 */
	IMPERFECTIVE_PRETERITE,
	
	/**
	 * <em>João beijará Maria (John will kiss Mary)</em>.
	 */
	FUTURE_OF_PRESENT,
	
	/**
	 * <em>João beijaria Maria (John would kiss Mary)</em>.
	 */
	FUTURE_OF_PAST;

}
