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

package simplenlg.morphophonology.portuguese;

import simplenlg.features.portuguese.PortugueseLexicalFeature;
import simplenlg.features.portuguese.PronounType;
import simplenlg.framework.ElementCategory;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.StringElement;
import simplenlg.morphophonology.MorphophonologyRulesInterface;

/**
 * This contains the Portuguese morphophonology rules.
 * 
 * Reference:
 * 
 * Cunha, Celso & Cintra, Lindley (1984). Nova Gramática do Português 
 * Contemporâneo. Edições João de Sá da Costa, Lisboa.
 * 
 * @author R. de Oliveira, University of Aberdeen.
 */
public class MorphophonologyRules implements MorphophonologyRulesInterface {
	
	//TODO: demonstrative pronouns, which are not prepositions, are not yet
	//entirely implemented; generation should succeed nonetheless if one defines
	//things like "este" or "aquele" as the specifier of the NP inside the PP.
	/**
	 * This method performs the morphophonology on two StringElements. General
	 * PPs with preposition that undergo elision. These are "a", "de"
	 * 	 and "por" or prepositional complexes formed with those such as "próximo
	 * 	 a" or "longe de". Note that "desde", a preposition, ends with -de but
	 * 	 should not undergo elision. The same applies for "contra" and "para";
	 * 	 both end in -a but should not undergo elision.
	 */
	 public void doMorphophonology(StringElement leftWord, StringElement rightWord) {		
		ElementCategory leftCategory = leftWord.getCategory();
		ElementCategory rightCategory = rightWord.getCategory();
		String leftRealisation = leftWord.getRealisation();
		String rightRealisation = rightWord.getRealisation();
		
		if (leftRealisation != null && rightRealisation != null) {
		
			if (LexicalCategory.PREPOSITION.equalTo(leftCategory)
					&& (LexicalCategory.DETERMINER.equalTo(rightCategory)
							|| rightWord.getFeature(PortugueseLexicalFeature.PRONOUN_TYPE)
								== PronounType.RELATIVE)) {
				// edited by de Oliveira
				// if the preposition is "de" or ends with " de"
				if (leftRealisation.matches("(.+ |)de\\z")) {
					String temp = leftRealisation.substring(0,
							leftRealisation.length()-1);
					leftWord.setRealisation(temp + rightRealisation);
					rightWord.setRealisation(null);
				}
				// if the preposition is "em", transform to "n
				if (leftRealisation.matches("em")) {
					leftWord.setRealisation("n" + rightRealisation);
					rightWord.setRealisation(null);
				}
				// if the preposition is "a" or ends with " a"
				if (leftRealisation.matches("(.+ |)a\\z")) {
					// "a" + beginning with "a" = "à"
					if (rightRealisation.startsWith("a")) {
						String temp = leftRealisation.substring(0,
								leftRealisation.length()-1);
						leftWord.setRealisation(temp + "à");
					}
					// "a" + beginning with "o" = "ao"
					if (rightRealisation.startsWith("o")) {
						String temp = leftRealisation.substring(0,
								leftRealisation.length()-1);
						leftWord.setRealisation(temp + "ao");
					}
					rightWord.setRealisation(null);
				}
				// if the preposition is "por"
				if (leftRealisation.matches("por")) {
					// "por" + "a" = "pela"
					if (rightRealisation.equals("a")) {
						leftWord.setRealisation("pela");
					}
					// "por" + "o" = "pelo"
					if (rightRealisation.equals("o")) {
						leftWord.setRealisation("pelo");
					}
					rightWord.setRealisation(null);
				}
			}
		}
	}
}