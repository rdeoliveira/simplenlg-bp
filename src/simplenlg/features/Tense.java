/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is "Simplenlg".
 *
 * The Initial Developer of the Original Code is Ehud Reiter, Albert Gatt and Dave Westwater.
 * Portions created by Ehud Reiter, Albert Gatt and Dave Westwater are Copyright (C) 2010-11 The University of Aberdeen. All Rights Reserved.
 *
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell, Pierre-Luc Vaudry.
 */

package simplenlg.features;

/**
 * <p>
 * An enumeration representing the different types of temporal sense that can be
 * applied to a verb. The tense is recorded in the {@code Feature.TENSE} feature
 * and applies to verbs and their associated phrases.
 * </p>
 * 
 * Verb features for Portuguese, differently from verb features for English, 
 * should be all necessary features to conjugate verbs in finite position in 
 * Portuguese. English realization may also use other features in Features.java
 * or Form.java.
 * Both added and recycled features follow traditional prescriptive grammars, 
 * which combine time and mood into one feature. Aspect is normally given in 
 * Portuguese in periphrastic verb groups, i.e. those composed of main and 
 * auxiliary verbs. 
 * 
 * Reference for Portuguese tenses:
 * 
 * Cunha, Celso & Cintra, Lindley (1984). Nova Gramática do Português 
 * Contemporâneo. Edições João de Sá da Costa, Lisboa.
 * 
 * Nomenclature has been adapted to better correlate to the nomenclature of
 * English grammars. 
 * 
 * @author de Oliveira 
 * @author A. Gatt and D. Westwater, University of Aberdeen.
 * @version 4.0
 * 
 */

public enum Tense {

	/**
	 * Example: <em>João beija Maria (John kisses Mary)</em>.
	 */
	PRESENT,
	
	/**
	 * Example: <em>João beijou Maria (John kissed Mary)</em>.
	 */
	PAST,
	
	/**
	 * Example: <em>João beijava Maria (John used to kiss Mary)</em>.
	 */
	IMPERFECT,
	
	// Not used, since modern Portuguese hardly ever uses it.
//	/**
//	 * Example: <em>João beijara Maria (John had kissed Mary)</em>.
//	 */
//	PLUPERFECT,
	
	/**
	 * Example: <em>João beijará Maria (John will kiss Mary)</em>.
	 */
	FUTURE,
	
	/**
	 * /**
	 * Example: <em>João beijaria Maria (John would kiss Mary)</em>.
	 */
	CONDITIONAL,
	
	/**
	 * Example: <em>Que João beije Maria (That John kiss Mary)</em>.
	 */
	SUBJUNCTIVE_PRESENT,
	
	/**
	 * Example: <em>Se João beijasse Maria (If John kissed Mary)</em>.
	 */
	SUBJUNCTIVE_IMPERFECT,
	
	/**
	 * Example: <em>Quando João beijar Maria (When John kiss Mary)</em>.
	 */
	SUBJUNCTIVE_FUTURE,
	
	/**
	 * Example: <em>João, beije Maria (John, kiss Mary)</em>.
	 * This feature should exist just for flexibility of association, since
	 * its morphological impact on the verb is the same as "subjunctive present".
	 */
	IMPERATIVE,
	
	/**
	 * Example: <em>Para João beijar Maria (For John to kiss Mary)</em>.
	 * This feature should exist just for flexibility of association, since
	 * its morphological impact on the verb is the same as "subjunctive future".
	 */
	PERSONAL_INFINITIVE;
}
