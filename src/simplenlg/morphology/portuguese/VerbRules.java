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
 * Source: Bechara, Evanildo. Moderna Gramática Portuguesa. 
 * Nova Fronteira, 2013.
 * 
 * @author R. de Oliveira, University of Aberdeen.
 */
public class VerbRules {
	
	//TODO delete this?
	/**
	 * Adds a radical and a suffix applying phonological rules
	 * Reference : sections 760-761 of Grevisse (1993)
	 * 
	 * @param radical
	 * @param suffix
	 * @return resultant form
	 */
	public static String addSuffix(String radical, String suffix) {
		//TODO
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
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST: case THIRD:
					if(conjugationType == 1) {
						suffix = "aria";
					} else if (conjugationType == 2) {
						suffix = "eria";
					} else if(conjugationType == 3) {
						suffix = "iria";
						}
					break;
				case SECOND:
					if(conjugationType == 1) {
						suffix = "arias";
					} else if (conjugationType == 2) {
						suffix = "erias";
					} else if(conjugationType == 3) {
						suffix = "irias";
						}
					break;
				}
				break;
			case PLURAL:
				switch ( person ) {
				case FIRST:
					if(conjugationType == 1) {
						suffix = "aríamos";
					} else if (conjugationType == 2) {
						suffix = "eríamos";
					} else if(conjugationType == 3) {
						suffix = "iríamos";
						}
					break;
				case SECOND:
					if(conjugationType == 1) {
						suffix = "aríeis";
					} else if (conjugationType == 2) {
						suffix = "eríeis";
					} else if(conjugationType == 3) {
						suffix = "iríeis";
						}
					break;
				case THIRD:
					if(conjugationType == 1) {
						suffix = "ariam";
					} else if (conjugationType == 2) {
						suffix = "eriam";
					} else if(conjugationType == 3) {
						suffix = "iriam";
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
		String radical = getVerbRadical(baseForm, Tense.FUTURE);
		String suffix = "";
			if (conjugationType != 0) {
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						if(conjugationType == 1) {
							suffix = "arei";
						} else if (conjugationType == 2) {
							suffix = "erei";
						} else if(conjugationType == 3) {
							suffix = "irei";
							}
						break;
					case SECOND:
						if(conjugationType == 1) {
							suffix = "arás";
						} else if (conjugationType == 2) {
							suffix = "erás";
						} else if(conjugationType == 3) {
							suffix = "irás";
							}
						break;
					case THIRD:
						if(conjugationType == 1) {
							suffix = "ará";
						} else if (conjugationType == 2) {
							suffix = "erá";
						} else if(conjugationType == 3) {
							suffix = "irá";
							}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						if(conjugationType == 1) {
							suffix = "aremos";
						} else if (conjugationType == 2) {
							suffix = "eremos";
						} else if(conjugationType == 3) {
							suffix = "iremos";
							}
						break;
					case SECOND:
						if(conjugationType == 1) {
							suffix = "areis";
						} else if (conjugationType == 2) {
							suffix = "ereis";
						} else if(conjugationType == 3) {
							suffix = "ireis";
							}
						break;
					case THIRD:
						if(conjugationType == 1) {
							suffix = "arão";
						} else if (conjugationType == 2) {
							suffix = "erão";
						} else if(conjugationType == 3) {
							suffix = "irão";
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
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST: case THIRD: {
					if(conjugationType == 1){
						suffix = "ava";
					} else {
						suffix = "ia";
					}		
				}
				break;
				case SECOND: {
					if(conjugationType == 1){
						suffix = "avas";
					} else {
						suffix = "ias";
					}
				}
				}
				break;
			case PLURAL:
				if(conjugationType == 1){
					switch ( person ) { 
					case FIRST:
						suffix = "ávamos";
						break;
					case SECOND:
						suffix = "áveis";
						break;
					case THIRD:
						suffix = "avam";
						break;
					}
				} else {
					switch ( person ) {
					case FIRST:
						suffix = "íamos";
						break;
					case SECOND:
						suffix = "íeis";
						break;
					case THIRD:
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
					case SECOND:
						suffix = "aste";
						break;
					case THIRD:
						suffix = "ou";
						break;
					}
				} else if(conjugationType == 2){
					switch ( person ) {
					case FIRST:
						suffix = "i";
						break;
					case SECOND:
						suffix = "este";
						break;
					case THIRD:
						suffix = "eu";
						break;
					}
				} else if(conjugationType == 3){
					switch ( person ) {
					case FIRST:
						suffix = "i";
						break;
					case SECOND:
						suffix = "iste";
						break;
					case THIRD:
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
					case SECOND:
						suffix = "astes";
						break;
					case THIRD:
						suffix = "aram";
						break;
					}
				} else if (conjugationType == 2){
					switch ( person ) {
					case FIRST:
						suffix = "emos";
						break;
					case SECOND:
						suffix = "estes";
						break;
					case THIRD:
						suffix = "eram";
						break;
					}
				} else if (conjugationType == 3){
					switch ( person ) {
					case FIRST:
						suffix = "imos";
						break;
					case SECOND:
						suffix = "istes";
						break;
					case THIRD:
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
	 * Builds the pluperfect form for regular verbs. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected static String buildPluperfectRegularVerb(String baseForm,
			NumberAgreement number, Person person) {
		int conjugationType = getConjugationType(baseForm);
		String radical = getVerbRadical(baseForm, Tense.PLUPERFECT);
		String suffix = "";		
		if (conjugationType != 0) {
			switch ( number ) {
			case SINGULAR: case BOTH:
				if(conjugationType == 1){
					switch ( person ) {
					case FIRST: case THIRD:
						suffix = "ara";
						break;
					case SECOND:
						suffix = "aras";
						break;
					}
				} else if(conjugationType == 2){
					switch ( person ) {
					case FIRST: case THIRD:
						suffix = "era";
						break;
					case SECOND:
						suffix = "eras";
						break;
					}
				} else if(conjugationType == 3){
					switch ( person ) {
					case FIRST: case THIRD:
						suffix = "ira";
						break;
					case SECOND:
						suffix = "iras";
						break;
					}
				}
				break;
			case PLURAL:
				if(conjugationType == 1){
					switch ( person ) {
					case FIRST:
						suffix = "áramos";
						break;
					case SECOND:
						suffix = "áreis";
						break;
					case THIRD:
						suffix = "aram";
						break;
					}
				} else if (conjugationType == 2){
					switch ( person ) {
					case FIRST:
						suffix = "êramos";
						break;
					case SECOND:
						suffix = "êreis";
						break;
					case THIRD:
						suffix = "eram";
						break;
					}
				} else if (conjugationType == 3){
					switch ( person ) {
					case FIRST:
						suffix = "íramos";
						break;
					case SECOND:
						suffix = "íreis";
						break;
					case THIRD:
						suffix = "iram";
						break;
					}
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
					case SECOND:
						suffix = "as";
						break;
					case THIRD:
						suffix = "a";
						break;
					}
					break;
				case 2: case 3:
					switch ( person ) {
					case FIRST:
						suffix = "o";
						break;
					case SECOND:
						suffix = "es";
						break;
					case THIRD:
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
					case SECOND:
						suffix = "ais";
						break;
					case THIRD:
						suffix = "am";
						break;
					}
					break;
				case 2:
					switch ( person ) {
					case FIRST:
						suffix = "emos";
						break;
					case SECOND:
						suffix = "eis";
						break;
					case THIRD:
						suffix = "em";
						break;
					}
					break;
				case 3:
					switch ( person ) {
					case FIRST:
						suffix = "imos";
						break;
					case SECOND:
						suffix = "is";
						break;
					case THIRD:
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
		case SINGULAR: case BOTH:
			switch ( person ) {
			case FIRST: case THIRD:
				if (conjugationType == 1) {
					suffix = "ar";
				} else if (conjugationType == 2){
					suffix = "er";
				} else if (conjugationType == 3){
					suffix = "ir";
				}
				break;
			case SECOND:
				if (conjugationType == 1) {
					suffix = "ares";
				} else if (conjugationType == 2){
					suffix = "eres";
				} else if (conjugationType == 3){
					suffix = "ires";
				}
				break;
			}
			break;
		case PLURAL:
			switch ( person ) {
			case FIRST:
				if (conjugationType == 1) {
					suffix = "armos";
				} else if (conjugationType == 2){
					suffix = "ermos";
				} else if (conjugationType == 3){
					suffix = "irmos";
				}
				break;
			case SECOND:
				if (conjugationType == 1) {
					suffix = "ardes";
				} else if (conjugationType == 2){
					suffix = "erdes";
				} else if (conjugationType == 3){
					suffix = "irdes";
				}
				break;
			case THIRD:
				if (conjugationType == 1) {
					suffix = "arem";
				} else if (conjugationType == 2){
					suffix = "erem";
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
		case SINGULAR: case BOTH:
			switch ( person ) {
			case FIRST: case THIRD:
				if (conjugationType == 1) {
					suffix = "asse";
				} else if (conjugationType == 2){
					suffix = "esse";
				} else if (conjugationType == 3){
					suffix = "isse";
				}
				break;
			case SECOND:
				if (conjugationType == 1) {
					suffix = "asses";
				} else if (conjugationType == 2){
					suffix = "esses";
				} else if (conjugationType == 3){
					suffix = "isses";
				}
				break;
			}
			break;
		case PLURAL:
			switch ( person ) {
			case FIRST:
				if (conjugationType == 1) {
					suffix = "ássemos";
				} else if (conjugationType == 2){
					suffix = "êssemos";
				} else if (conjugationType == 3){
					suffix = "íssemos";
				}
				break;
			case SECOND:
				if (conjugationType == 1) {
					suffix = "ásseis";
				} else if (conjugationType == 2){
					suffix = "êsseis";
				} else if (conjugationType == 3){
					suffix = "ísseis";
				}
				break;
			case THIRD:
				if (conjugationType == 1) {
					suffix = "assem";
				} else if (conjugationType == 2){
					suffix = "essem";
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
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST: case THIRD:
					if(conjugationType == 1){
						suffix = "e";
					} else {
						suffix = "a";
					}
					break;
				case SECOND:
					if(conjugationType == 1){
						suffix = "es";
					} else {
						suffix = "as";
					}
					break;
				}
				break;
			case PLURAL:
				if(conjugationType == 1){
					switch ( person ) { 
					case FIRST:
						suffix = "emos";
						break;
					case SECOND:
						suffix = "eis";
						break;
					case THIRD:
						suffix = "em";
						break;
					}
				} else {
					switch ( person ) {
					case FIRST:
						suffix = "amos";
						break;
					case SECOND:
						suffix = "ais";
						break;
					case THIRD:
						suffix = "am";
						break;
					}
					break;
				}
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	//TODO if not debugged in auxiliary creation in VerbPhraseHelper, finish method for
	// "tu and "vos"
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
		
		switch (tense){
		case CONDITIONAL:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "estaria";
					break;
				case SECOND:
					realised = "estarias";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estaríamos";
					break;
				case SECOND:
					realised = "estaríeis";
					break;
				case THIRD:
					realised = "estariam";
					break;
				}
				break;
			}
			break;
		case FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "estarei";
					break;
				case SECOND:
					realised = "estarás";
					break;
				case THIRD:
					realised = "estará";
					break;
				}
			break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estaremos";
					break;
				case SECOND: 
					realised = "estareis";
					break;
				case THIRD:
					realised = "estarão";
					break;
				}
				break;
			}
			break;
		case IMPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "estava";
					break;
				case SECOND:
					realised = "estavas";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estávamos";
					break;
				case SECOND:
					realised = "estáveis";
					break;
				case THIRD:
					realised = "estavam";
					break;
				}
				break;
			}
			break;
		case PAST:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "estive";
					break;
				case SECOND: case THIRD:
					realised = "esteve";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estivemos";
					break;
				case SECOND: case THIRD:
					realised = "estiveram";
					break;
				}
				break;
			}
			break;
		case PERSONAL_INFINITIVE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "estar";
				case SECOND:
					realised = "estares";
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estarmos";
					break;
				case SECOND:
					realised = "estardes";
					break;
				case THIRD:
					realised = "estarem";
					break;
				}
				break;
			}
			break;
		case PLUPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "estivera";
					break;
				case SECOND:
					realised = "estiveras";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estivéramos";
					break;
				case SECOND:
					realised = "estivéreis";
					break;
				case THIRD:
					realised = "estiveram";
					break;
				}
				break;
			}
			break;
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
		case SUBJUNCTIVE_IMPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "estivesse";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estivéssemos";
					break;
				case SECOND: case THIRD:
					realised = "estivessem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "estiver";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estivermos";
					break;
				case SECOND: case THIRD:
					realised = "estiverem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_PRESENT: case IMPERATIVE:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "esteja";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "estejamos";
					break;
				case SECOND: case THIRD:
					realised = "estejam";
					break;
				}
				break;
			}
			break;
		case IMPERSONAL_INFINITIVE:
			break;
		default:
			break;
		}
		
		return realised;		
	}
	
	//TODO if not debugged in auxiliary creation in VerbPhraseHelper, finish method for
		// "tu and "vos"
	/**
	 * Builds the appropriate form for the "ser" verb, in any tense. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @param tense
	 * @return the inflected word.
	 */
	protected static String conjugateSer(NumberAgreement number,
			Person person, Tense tense){
		
		String realised = "ser";
		
		switch (tense){
		case CONDITIONAL:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "seria";
					break;
				case SECOND:
					realised = "serias";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "seríamos";
					break;
				case SECOND:
					realised = "seríeis";
					break;
				case THIRD:
					realised = "seriam";
					break;
				}
				break;
			}
			break;
		case FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "serei";
					break;
				case SECOND:
					realised = "serás";
					break;
				case THIRD:
					realised = "será";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "seremos";
					break;
				case SECOND:
					realised = "sereis";
					break;
				case THIRD:
					realised = "serão";
					break;
				}
				break;
			}
			break;
		case IMPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "era";
					break;
				case SECOND:
					realised = "eras";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "éramos";
					break;
				case SECOND:
					realised = "eréis";
					break;
				case THIRD:
					realised = "eram";
					break;
				}
				break;
			}
			break;
		case PAST:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "fui";
					break;
				case SECOND:
					realised = "foste";
					break;
				case THIRD:
					realised = "foi";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "fomos";
					break;
				case SECOND:
					realised = "fostes";
					break;
				case THIRD:
					realised = "foram";
					break;
				}
				break;
			}
			break;
		case PERSONAL_INFINITIVE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "ser";
					break;
				case SECOND:
					realised = "seres";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "sermos";
					break;
				case SECOND:
					realised = "serdes";
					break;
				case THIRD:
					realised = "serem";
					break;
				}
				break;
			}
			break;
		case PLUPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "fora";
					break;
				case SECOND:
					realised = "foras";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "fôramos";
					break;
				case SECOND:
					realised = "fôreis";
					break;
				case THIRD:
					realised = "foram";
					break;
				}
				break;
			}
			break;
		case PRESENT:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "sou";
					break;
				case SECOND:
					realised = "és";
					break;
				case THIRD:
					realised = "é";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "somos";
					break;
				case SECOND:
					realised = "sois";
					break;
				case THIRD:
					realised = "são";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_IMPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "fosse";
					break;
				case SECOND:
					realised = "fosses";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "fôssemos";
					break;
				case SECOND:
					realised = "fôsseis";
					break;
				case THIRD:
					realised = "fossem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "for";
					break;
				case SECOND:
					realised = "fores";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "formos";
					break;
				case SECOND:
					realised = "fordes";
					break;
				case THIRD:
					realised = "forem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_PRESENT: case IMPERATIVE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST: case THIRD:
					realised = "seja";
					break;
				case SECOND:
					realised = "sejas";
					break;
				}
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "sejamos";
					break;
				case SECOND:
					realised = "sejais";
					break;
				case THIRD:
					realised = "sejam";
					break;
				}
				break;
			}
			break;
		}
		
		return realised;		
	}
	
	//TODO if not debugged in auxiliary creation in VerbPhraseHelper, finish method for
		// "tu and "vos"
	/**
	 * Builds the appropriate form for the "ter" verb, in any tense. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @param tense
	 * @return the inflected word.
	 */
	protected static String conjugateTer(NumberAgreement number,
			Person person, Tense tense){
		
		String realised = "ter";
		
		switch (tense){
		case CONDITIONAL:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "teria";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "teríamos";
					break;
				case SECOND: case THIRD:
					realised = "teriam";
					break;
				}
				break;
			}
			break;
		case FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "terei";
					break;
				case SECOND: case THIRD:
					realised = "terá";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "teremos";
					break;
				case SECOND: case THIRD:
					realised = "terão";
					break;
				}
				break;
			}
			break;
		case IMPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "tinha";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "tínhamos";
					break;
				case SECOND: case THIRD:
					realised = "tinham";
					break;
				}
				break;
			}
			break;
		case PAST:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "tive";
					break;
				case SECOND: case THIRD:
					realised = "teve";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "tivemos";
					break;
				case SECOND: case THIRD:
					realised = "tiveram";
					break;
				}
				break;
			}
			break;
		case PRESENT:
			switch (person){
			case FIRST:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "tenho";
					break;
				case PLURAL:
					realised = "temos";
					break;
				}
			case SECOND: case THIRD:
				switch (number){
				case SINGULAR: case BOTH: case PLURAL:
					realised = "tem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_IMPERFECT: case PERSONAL_INFINITIVE: //TODO fix this, since s-imperfect and p-infinitive of irregulars is not the same
			switch (number){
			case SINGULAR: case BOTH:
				realised = "tivesse";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "tivéssemos";
					break;
				case SECOND: case THIRD:
					realised = "tivessem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "tiver";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "tivermos";
					break;
				case SECOND: case THIRD:
					realised = "tiverem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_PRESENT: case IMPERATIVE:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "tenha";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "tenhamos";
					break;
				case SECOND: case THIRD:
					realised = "tenham";
					break;
				}
				break;
			}
			break;
		}
		
		return realised;		
	}
	
	//TODO if not debugged in auxiliary creation in VerbPhraseHelper, finish method for
		// "tu and "vos"
	/**
	 * Builds the appropriate form for the "ir" verb, in any tense. 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @param tense
	 * @return the inflected word.
	 */
	protected static String conjugateIr(NumberAgreement number,
			Person person, Tense tense){
		
		String realised = "ir";
		
		switch (tense){
		case CONDITIONAL:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "iria";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "iríamos";
					break;
				case SECOND: case THIRD:
					realised = "iriam";
					break;
				}
				break;
			}
			break;
		case FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "irei";
					break;
				case SECOND: case THIRD:
					realised = "irá";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "iremos";
					break;
				case SECOND: case THIRD:
					realised = "irão";
					break;
				}
				break;
			}
			break;
		case IMPERFECT:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "ia";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "íamos";
					break;
				case SECOND: case THIRD:
					realised = "iam";
					break;
				}
				break;
			}
			break;
		case PAST:
			switch (number){
			case SINGULAR: case BOTH:
				switch (person){
				case FIRST:
					realised = "fui";
					break;
				case SECOND: case THIRD:
					realised = "foi";
					break;
				}
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "fomos";
					break;
				case SECOND: case THIRD:
					realised = "foram";
					break;
				}
				break;
			}
			break;
		case PRESENT:
			switch (person){
			case FIRST:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "vou";
					break;
				case PLURAL:
					realised = "vamos";
					break;
				}
			case SECOND: case THIRD:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "vai";
					break;
				case PLURAL:
					realised = "vão";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_IMPERFECT: case PERSONAL_INFINITIVE: //TODO fix this, since s-imperfect and p-infinitive of irregulars is not the same
			switch (number){
			case SINGULAR: case BOTH:
				realised = "fosse";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "fôssemos";
					break;
				case SECOND: case THIRD:
					realised = "fossem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_FUTURE:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "for";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "formos";
					break;
				case SECOND: case THIRD:
					realised = "forem";
					break;
				}
				break;
			}
			break;
		case SUBJUNCTIVE_PRESENT: case IMPERATIVE:
			switch (number){
			case SINGULAR: case BOTH:
				realised = "vá";
				break;
			case PLURAL:
				switch (person){
				case FIRST:
					realised = "vamos";
					break;
				case SECOND: case THIRD:
					realised = "vão";
					break;
				}
				break;
			}
			break;
		}
		
		return realised;		
	}
	
	//TODO if not debugged in auxiliary creation in VerbPhraseHelper, finish method for
			// "tu and "vos"
		/**
		 * Builds the appropriate form for the "ir" verb, in any tense. 
		 *
		 * @param baseForm
		 *            the base form of the word.
		 * @param number
		 * @param person
		 * @param tense
		 * @return the inflected word.
		 */
		protected static String conjugateDar(NumberAgreement number,
				Person person, Tense tense){
			
			String realised = "dar";
			
			switch (tense){
			case CONDITIONAL:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "daria";
					break;
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "daríamos";
						break;
					case SECOND: case THIRD:
						realised = "dariam";
						break;
					}
					break;
				}
				break;
			case FUTURE:
				switch (number){
				case SINGULAR: case BOTH:
					switch (person){
					case FIRST:
						realised = "darei";
						break;
					case SECOND: case THIRD:
						realised = "dará";
						break;
					}
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "daremos";
						break;
					case SECOND: case THIRD:
						realised = "darão";
						break;
					}
					break;
				}
				break;
			case IMPERFECT:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "dava";
					break;
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "dávamos";
						break;
					case SECOND: case THIRD:
						realised = "davam";
						break;
					}
					break;
				}
				break;
			case PAST:
				switch (number){
				case SINGULAR: case BOTH:
					switch (person){
					case FIRST:
						realised = "dei";
						break;
					case SECOND: case THIRD:
						realised = "deu";
						break;
					}
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "demos";
						break;
					case SECOND: case THIRD:
						realised = "deram";
						break;
					}
					break;
				}
				break;
			case PRESENT:
				switch (person){
				case FIRST:
					switch (number){
					case SINGULAR: case BOTH:
						realised = "dou";
						break;
					case PLURAL:
						realised = "damos";
						break;
					}
				case SECOND: case THIRD:
					switch (number){
					case SINGULAR: case BOTH:
						realised = "dá";
						break;
					case PLURAL:
						realised = "dão";
						break;
					}
					break;
				}
				break;
			case SUBJUNCTIVE_IMPERFECT: case PERSONAL_INFINITIVE: //TODO fix this, since s-imperfect and p-infinitive of irregulars is not the same
				switch (number){
				case SINGULAR: case BOTH:
					realised = "desse";
					break;
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "déssemos";
						break;
					case SECOND: case THIRD:
						realised = "dessem";
						break;
					}
					break;
				}
				break;
			case SUBJUNCTIVE_FUTURE:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "der";
					break;
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "dermos";
						break;
					case SECOND: case THIRD:
						realised = "derem";
						break;
					}
					break;
				}
				break;
			case SUBJUNCTIVE_PRESENT: case IMPERATIVE:
				switch (number){
				case SINGULAR: case BOTH:
					realised = "dê";
					break;
				case PLURAL:
					switch (person){
					case FIRST:
						realised = "demos";
						break;
					case SECOND: case THIRD:
						realised = "deem";
						break;
					}
					break;
				}
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
		// TODO This list is far from finished; additional irregular verbs
		// must be added.
		if (baseForm.equals("trazer") && tense == Tense.PAST) {
			radical = "troux";
		} else if (baseForm.equals("reunir") && tense == Tense.PRESENT) {
			radical = "reún";
//		} else if (baseForm.equals("ser")) {
//			radical = baseForm;
		} else {
			radical = baseForm.substring(0,baseForm.length()-2);
		}
		return radical;
	}
}