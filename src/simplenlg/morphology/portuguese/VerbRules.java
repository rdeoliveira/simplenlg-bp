/**
 * 
 */
package simplenlg.morphology.portuguese;

import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.Tense;

/**
 * This class is used to apply morphological changes to the finite verb, based
 * solely on tense choice, which in turn combines time and mood, but not aspect.
 * Note that 'finite' may be the main or the auxiliary verb, whichever agrees
 * in number and person with the subject. 
 * 
 * Reference:
 * 
 * Cunha, Celso & Cintra, Lindley (1984). Nova Gramática do Português 
 * Contemporâneo. Edições João de Sá da Costa, Lisboa.
 * 
 * @author de Oliveira
 */
public class VerbRules {
	
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
	
	/**
	 * Builds the indicative conditional form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildConditionalRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.CONDITIONAL);
		String suffix = "";
		if (conjugationType != 0) {
			switch ( number ) {
			// if singular (regardless of person)
			case SINGULAR: case BOTH:
				// if -ar verb
				if(conjugationType == 1){
					suffix = "aria";
				// if -er verb
				} else if(conjugationType == 2){
					suffix = "eria";
				// if -ir verb
				} else if(conjugationType == 3){
					suffix = "iria";
				}
				break;
			// if plural
			case PLURAL:
				// if -ar verb
				if(conjugationType == 1){
					switch ( person ) {
					// if 1st person 
					case FIRST:
						suffix = "aríamos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "ariam";
						break;
					}
				// if -er verb
				} else if(conjugationType == 2){
					switch ( person ) {
					// if first person
					case FIRST:
						suffix = "eríamos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "eriam";
						break;
					}
					break;
				// if -ir verb
				} else if(conjugationType == 3){
					switch ( person ) {
					// if first person
					case FIRST:
						suffix = "iríamos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "iriam";
						break;
					}
					break;
				}
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	/**
	 * Builds the indicative future form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildFutureRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.CONDITIONAL);
		String suffix = "";
		if (conjugationType != 0) {
			switch ( number ) {
			// if singular
			case SINGULAR: case BOTH:
				switch ( person ) {
				// if 1st person
				case FIRST:
					// if -ar verb
					if(conjugationType == 1){
						suffix = "arei";
					// if -er verb
					} else if(conjugationType == 2){
						suffix = "erei";
					// if -ir verb
					} else if(conjugationType == 3){
						suffix = "irei";
					}
					break;
				case SECOND: case THIRD:
					// if -ar verb
					if(conjugationType == 1){
						suffix = "ará";
					// if -er verb
					} else if(conjugationType == 2){
						suffix = "erá";
					// if -ir verb
					} else if(conjugationType == 3){
						suffix = "irá";
					}
					break;				
				}
				break;
			// if plural
			case PLURAL:
				// if -ar verb
				if(conjugationType == 1){
					switch ( person ) {
					// if 1st person 
					case FIRST:
						suffix = "aremos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "arão";
						break;
					}
				// if -er verb
				} else if(conjugationType == 2){
					switch ( person ) {
					// if first person
					case FIRST:
						suffix = "eremos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "erão";
						break;
					}
					break;
				// if -ir verb
				} else if(conjugationType == 3){
					switch ( person ) {
					// if first person
					case FIRST:
						suffix = "iremos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "irão";
						break;
					}
					break;
				}
			}
		}
		
		return addSuffix(radical, suffix);
	}
		
	/**	 
	 * Builds the indicative imperfect (imperfect preterite) form for regular 
	 * verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildImperfectRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.IMPERFECT);
		String suffix = "";		
		if (conjugationType != 0) {
			switch ( number ) {
			// if singular (regardless of person)
			case SINGULAR: case BOTH:
				// if -ar verb 
				if(conjugationType == 1){
					suffix = "ava";
				// if -er or -ir verb
				} else {
					suffix = "ia";
				}
				break;
			// if plural
			case PLURAL:
				// if -ar verb
				if(conjugationType == 1){
					switch ( person ) {
					// if 1st person 
					case FIRST:
						suffix = "ávamos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "avam";
						break;
					}
				// if -er or -ir verb
				} else {
					switch ( person ) {
					// if first person
					case FIRST:
						suffix = "íamos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "iam";
						break;
					}
					break;
				}
			}
		}
		
		return addSuffix(radical, suffix);
	}
		
	/**
	 * Builds the indicative past (preterite) form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildPastRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
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
						suffix = "eu";
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
	
	/**
	 * Builds the indicative present form for regular verbs. 
	 *
	 * @param baseForm the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
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
					break;
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
	 * Builds the subjunctive future form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildSubjunctiveFutureRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.SUBJUNCTIVE_FUTURE);
		String suffix = "";
		switch ( number ) {
		// if singular
		case SINGULAR: case BOTH:
			switch ( person ) {
			// regardless of person
			case FIRST: case SECOND: case THIRD:
				// if -ar verb
				if (conjugationType == 1) {
					suffix = "ar";
				// if -er verb
				} else if (conjugationType == 2){
					suffix = "er";
				// if -ir verb
				} else if (conjugationType == 3){
					suffix = "ir";
				}
				break;
			}
			break;
		// if plural
		case PLURAL:
			switch ( person ) {
			// if 1st person
			case FIRST:
				// if -ar verb
				if (conjugationType == 1) {
					suffix = "armos";
				// if -er verb
				} else if (conjugationType == 2){
					suffix = "ermos";
				// if -ir verb
				} else if (conjugationType == 3){
					suffix = "irmos";
				}
				break;
			// if 2nd or 3rd person
			case SECOND: case THIRD:
				// if -ar verb
				if (conjugationType == 1) {
					suffix = "arem";
				// if -er verb
				} else if (conjugationType == 2){
					suffix = "erem";
				// if -ir verb
				} else if (conjugationType == 3){
					suffix = "irem";
				}
				break;
			}
			break;
		
		}
		return addSuffix(radical, suffix);
	}
	
	/**
	 * Builds the subjunctive imperfect form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildSubjunctiveImperfectRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.SUBJUNCTIVE_IMPERFECT);
		String suffix = "";
		switch ( number ) {
		// if singular
		case SINGULAR: case BOTH:
			switch ( person ) {
			// regardless of person
			case FIRST: case SECOND: case THIRD:
				// if -ar verb
				if (conjugationType == 1) {
					suffix = "asse";
				// if -er verb
				} else if (conjugationType == 2){
					suffix = "esse";
				// if -ir verb
				} else if (conjugationType == 3){
					suffix = "isse";
				}
				break;
			}
			break;
		// if plural
		case PLURAL:
			switch ( person ) {
			// if 1st person
			case FIRST:
				// if -ar verb
				if (conjugationType == 1) {
					suffix = "ássemos";
				// if -er verb
				} else if (conjugationType == 2){
					suffix = "êssemos";
				// if -ir verb
				} else if (conjugationType == 3){
					suffix = "íssemos";
				}
				break;
			// if 2nd or 3rd person
			case SECOND: case THIRD:
				// if -ar verb
				if (conjugationType == 1) {
					suffix = "assem";
				// if -er verb
				} else if (conjugationType == 2){
					suffix = "essem";
				// if -ir verb
				} else if (conjugationType == 3){
					suffix = "issem";
				}
				break;
			}
			break;
		
		}
		return addSuffix(radical, suffix);
	}
		
	/**
	 * Builds the subjunctive present or imperative form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildSubjunctivePresentRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.SUBJUNCTIVE_PRESENT);
		String suffix = "";		
		if (conjugationType != 0) {
			switch ( number ) {
			// if singular (regardless of person)
			case SINGULAR: case BOTH:
				// if -ar verb 
				if(conjugationType == 1){
					suffix = "e";
				// if -er or -ir verb
				} else {
					suffix = "a";
				}
				break;
			// if plural
			case PLURAL:
				// if -ar verb
				if(conjugationType == 1){
					switch ( person ) {
					// if 1st person 
					case FIRST:
						suffix = "emos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "em";
						break;
					}
				// if -er or -ir verb
				} else {
					switch ( person ) {
					// if first person
					case FIRST:
						suffix = "amos";
						break;
					// if 2nd or 3rd
					case SECOND: case THIRD:
						suffix = "am";
						break;
					}
					break;
				}
			}
		}
		
		return addSuffix(radical, suffix);
	}

	/**
	 * Builds the appropriate form for the "estar" verb, in any tense. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @param tense
	 * @return the inflected word.
	 */
	protected static String conjugateEstar(NumberAgreement number,
			Person person, Tense tense){
		
		String realised = "estar";
		
		// TODO finish up conjugation; done: present 
		switch (tense){
		case PRESENT:
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
		case CONDITIONAL:
			break;
		case FUTURE:
			break;
		case IMPERATIVE:
			break;
		case IMPERFECT:
			break;
		case PAST:
			break;
		case PERSONAL_INFINITIVE:
			break;
		case SUBJUNCTIVE_FUTURE:
			break;
		case SUBJUNCTIVE_IMPERFECT:
			break;
		case SUBJUNCTIVE_PRESENT:
			break;
		default:
			break;
		}
		
		return realised;		
	}
	
	/**
	 * Returns the conjugation type (pattern) of the finite verb, which may be:
	 *  -ar (1st type), -er (2nd type) or -ir (3rd type).
	 *
	 * @param baseForm
	 *            the base form of the word.
	 */
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

	/**
	 * Returns the radical of the finite verb to be conjugated. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param tense
	 */
	protected static String getVerbRadical(String baseForm, Tense tense){
		String radical = "";
		// TODO Very simplified rule, and will need improvement.
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
}