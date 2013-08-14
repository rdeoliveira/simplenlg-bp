/**
 * 
 */
package simplenlg.morphology.portuguese;

import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.Tense;

/**
 * Conjugates the irregular verb "estar".
 * @author de Oliveira
 */
public class VerbRules {
	
	//TODO finish up conjugation; done: present 
	protected static String conjugateEstar(NumberAgreement number,
			Person person, Tense tense){
		
		String realised = "estar";
		
		switch (tense){
		case PRESENT: case CONDITIONAL: case FUTURE: case IMPERFECTIVE_PAST: case PAST:
			switch (person){
			case FIRST:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "estou";
					break;
				case PLURAL:
					realised = "estamos";
					break;
				}
			case SECOND: case THIRD:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "está";
					break;
				case PLURAL:
					realised = "estão";
					break;
				}
				break;
			}
			break;
		}
		
		return realised;		
	}
	
	/**
	 * Builds the present form for regular verbs. 
	 * Reference : Celso & Cunha (1984)
	 *
	 * @param baseForm the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 * @author edited by de Oliveira
	 */
	protected static String buildPresentRegularVerb(String baseForm, 
			NumberAgreement number, Person person) {
		
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.PRESENT);
		String suffix = "";		
		
		if (conjugationType != 0) {
			switch ( number ) {
			case SINGULAR: case BOTH:
				switch (conjugationType) {
				case 1:
					switch ( person ) {
					case FIRST:
						suffix = "o";
						break;
					case SECOND: case THIRD:
						suffix = "a";
						break;
					}
					break;
				case 2: case 3:
					switch ( person ) {
					case FIRST:
						suffix = "o";
						break;
					case SECOND: case THIRD:
						suffix = "e";
						break;
					}
					break;
				}
				break;
			case PLURAL:
				switch (conjugationType) {
				case 1:
					switch ( person ) {
					case FIRST:
						suffix = "amos";
						break;
					case SECOND: case THIRD:
						suffix = "am";
						break;
					}
					break;
				case 2:
					switch ( person ) {
					case FIRST:
						suffix = "emos";
						break;
					case SECOND: case THIRD:
						suffix = "em";
						break;
					}
				case 3:
					switch ( person ) {
					case FIRST:
						suffix = "imos";
						break;
					case SECOND: case THIRD:
						suffix = "em";
						break;
					}
					break;
				}
				break;
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	/**
	 * Builds the perfective preterite form for regular verbs. 
	 * Reference : Cunha & Cintra (1984)
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 * @author de Oliveira
	 */
	protected static String buildPerfectivePreteriteRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
//		System.out.println(baseForm+" "+number+" "+person);
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.PAST);
		String suffix = "";		
		if (conjugationType != 0) {
			switch ( number ) {
			case SINGULAR: case BOTH:
				if(conjugationType == 1){
					switch ( person ) {
					case FIRST:
						suffix = "ei";
						break;
					case SECOND: case THIRD:
						suffix = "ou";
						break;
					}
				} else if(conjugationType == 2){
					switch ( person ) {
					case FIRST:
						suffix = "i";
						break;
					case SECOND: case THIRD:
						suffix = "ou";
						break;
					}
				} else if(conjugationType == 3){
					switch ( person ) {
					case FIRST:
						suffix = "i";
						break;
					case SECOND: case THIRD:
						suffix = "iu";
						break;
					}
				}
				break;
			case PLURAL:
				if(conjugationType == 1){
					switch ( person ) {
					case FIRST:
						suffix = "amos";
						break;
					case SECOND: case THIRD:
						suffix = "aram";
						break;
					}
				} else if (conjugationType == 2){
					switch ( person ) {
					case FIRST:
						suffix = "emos";
						break;
					case SECOND: case THIRD:
						suffix = "eram";
						break;
					}
				} else if (conjugationType == 3){
					switch ( person ) {
					case FIRST:
						suffix = "imos";
						break;
					case SECOND: case THIRD:
						suffix = "iram";
						break;
					}
					break;
				}
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	// gets conjugation type between -ar, -er or -ir verbs 
	protected static int getConjugationType(String baseForm){
		int conjugationType = 0;
		
		if (baseForm.endsWith("ar") ) {
			conjugationType = 1;
		} else if (baseForm.endsWith("er") ) {
			conjugationType = 2;
		} else if (baseForm.endsWith("ir") ) {
			conjugationType = 3;
		}
		return conjugationType;
	}
	
	// gets verb radical
	// very simplified rule, and will need improvement
	protected static String getVerbRadical(String baseForm, Tense tense){
		String radical = "";
//		System.out.println(baseForm);
		if (baseForm.equals("trazer") && tense == Tense.PAST) {
			radical = "troux";
		} else if (baseForm.equals("reunir") && tense == Tense.PRESENT) {
			radical = "reún";
		} else if (baseForm.equals("ser")) {
			radical = baseForm;
		} else {
			radical = baseForm.substring(0,baseForm.length()-2);
		}
		return radical;
	}

	/**
	 * Adds a radical and a suffix applying phonological rules
	 * Reference : sections 760-761 of Grevisse (1993)
	 * 
	 * @param radical
	 * @param suffix
	 * @return resultant form
	 */
	public static String addSuffix(String radical, String suffix) {
//		int length = radical.length();
//		// change "c" to "ç" and "g" to "ge" before "a" and "o";
//		if (suffix.matches("a")) {
//			if (radical.endsWith("c")) {
//				radical = radical.substring(0, length-1) + "ç";
//			} else if (radical.endsWith("g")) {
//				radical += "e";
//			}
//		}
//		// if suffix begins with mute "e"
//		if (!suffix.equals("ez") && suffix.startsWith("e")) {
//			// change "y" to "i" if not in front of "e"
//			if (!radical.endsWith("ey") && radical.endsWith("y")) {
//				radical = radical.substring(0,length-1) + "i";
//			}
//			// change "e" and "é" to "è" in last sillable of radical
//			char penultimate = radical.charAt(length-2);
//			if (penultimate == 'e' || penultimate == 'é') {
//				radical = radical.substring(0,length-2) + "è"
//						+ radical.substring(length-1);
//			}
//		}
		return radical + suffix;
	}
	
}