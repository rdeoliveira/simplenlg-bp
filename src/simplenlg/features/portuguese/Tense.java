//package simplenlg.features.portuguese;
//
///**
// * Verb features for Portuguese. Differently from SimpleNLG-English, which
// * lists verb features in the Feature and Tense classes, these should be 
// * all necessary features to conjugate verbs in finite position in Portuguese.
// * The features listed follow traditional prescriptive grammars, which combine
// * time and mood into one feature. Aspect is normally given in Portuguese in
// * periphrastic verb groups, i.e. those composed of main and auxiliary verbs. 
// * 
// * Reference:
// * 
// * Cunha, Celso & Cintra, Lindley (1984). Nova Gramática do Português 
// * Contemporâneo. Edições João de Sá da Costa, Lisboa.
// * 
// * Nomenclature has been adapted to better correlate to the nomenclature of
// * English grammars. 
// * 
// * @author R. de Oliveira, University of Aberdeen.
// *
// */
//public enum Tense {
//
//	/**
//	 * Example: <em>João beija Maria (John kisses Mary)</em>.
//	 */
//	PRESENT,
//	
//	/**
//	 * Example: <em>João beijou Maria (John kissed Mary)</em>.
//	 */
//	PAST,
//	
//	/**
//	 * Example: <em>João beijava Maria (John used to kiss Mary)</em>.
//	 */
//	IMPERFECT,
//	
//	/**
//	 * Example: <em>João beijara Maria (John had kissed Mary)</em>.
//	 */
//	PLUPERFECT,
//	
//	/**
//	 * Example: <em>João beijará Maria (John will kiss Mary)</em>.
//	 */
//	FUTURE,
//	
//	/**
//	 * /**
//	 * Example: <em>João beijaria Maria (John would kiss Mary)</em>.
//	 */
//	CONDITIONAL,
//	
//	/**
//	 * Example: <em>Que João beije Maria (That John kiss Mary)</em>.
//	 */
//	SUBJUNCTIVE_PRESENT,
//	
//	/**
//	 * Example: <em>Se João beijasse Maria (If John kissed Mary)</em>.
//	 */
//	SUBJUNCTIVE_IMPERFECT,
//	
//	/**
//	 * Example: <em>Quando João beijar Maria (When John kiss Mary)</em>.
//	 */
//	SUBJUNCTIVE_FUTURE,
//	
//	/**
//	 * Example: <em>João, beije Maria (John, kiss Mary)</em>.
//	 * This feature should exist just for flexibility of association, since
//	 * its morphological impact on the verb is the same as "subjunctive present".
//	 */
//	IMPERATIVE,
//	
//	/**
//	 * Example: <em>Para João beijar Maria (For John to kiss Mary)</em>.
//	 * This feature should exist just for flexibility of association, since
//	 * its morphological impact on the verb is the same as "subjunctive future".
//	 */
//	PERSONAL_INFINITIVE;
//	
//}
