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

package simplenlg.morphology.portuguese;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.PhraseCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.StringElement;
import simplenlg.framework.WordElement;
import simplenlg.features.*;
import simplenlg.features.french.*;
import simplenlg.features.Tense;
import simplenlg.lexicon.Lexicon;
import simplenlg.morphology.MorphologyRulesInterface;

/**
 * Morphology rules for Portuguese.
 *  
 * Reference:
 * 
 * Cunha, Celso & Cintra, Lindley (1984). Nova Gramática do Português 
 * Contemporâneo. Edições João de Sá da Costa, Lisboa.
 * 
 * @author R. de Oliveira, University of Aberdeen.
 *
 */
public class MorphologyRules extends simplenlg.morphology.english.NonStaticMorphologyRules
		implements MorphologyRulesInterface {
	
	/**
	 * This method performs the morphology for determiners.
	 * It returns a StringElement made from the baseform, or
	 * the plural or feminine singular form of the determiner
	 * if it applies.
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 */
	@Override
	public NLGElement doDeterminerMorphology(InflectedWordElement element) {
		String inflectedForm;
		// Get gender from parent, or from self if there is no parent
		NLGElement parent = element.getParent();
		Object gender = null;
		
		if (parent != null) gender = parent.getFeature(LexicalFeature.GENDER);
		else gender = element.getFeature(LexicalFeature.GENDER);
		
		boolean feminine = Gender.FEMININE.equals( gender );
		
		// plural form
		if (element.isPlural() && 
			element.hasFeature(LexicalFeature.PLURAL)) {
			inflectedForm = element.getFeatureAsString(LexicalFeature.PLURAL);
			
			if (feminine && element.hasFeature(FrenchLexicalFeature.FEMININE_PLURAL)) {
				inflectedForm = element.getFeatureAsString(FrenchLexicalFeature.FEMININE_PLURAL);
			}
			
			// "des" -> "de" in front of noun premodifiers
			if (parent != null && "des".equals(inflectedForm)) {
				List<NLGElement> preModifiers = parent.getFeatureAsElementList(InternalFeature.PREMODIFIERS);
				if (!preModifiers.isEmpty()) {
					inflectedForm = "de";
				}
			}
			
		// feminine singular form
		} else if (feminine	&& element.hasFeature(FrenchLexicalFeature.FEMININE_SINGULAR)) {
			inflectedForm = element.getFeatureAsString(FrenchLexicalFeature.FEMININE_SINGULAR);
		// masculine singular form
		} else {
			inflectedForm = element.getBaseForm();
			// remove particle if the determiner has one
			String particle = getParticle(element);
			inflectedForm = inflectedForm.replaceFirst(particle, "");
			inflectedForm = inflectedForm.trim();
		}
		
		StringElement realisedElement = new StringElement(inflectedForm, element);
		return realisedElement;
	}
	
	/**
	 * This method performs the morphology for adjectives.
	 * Based in part on the same method in the english rules
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	@Override
	public NLGElement doAdjectiveMorphology(
			InflectedWordElement element, WordElement baseWord) {

		String realised = null;

		// base form from baseWord if it exists, otherwise from element
		String baseForm = getBaseForm(element, baseWord);

		// Comparatives and superlatives are mainly treated by syntax
		// in French. Only exceptions, provided by the lexicon, are
		// treated by morphology.
		if (element.getFeatureAsBoolean(Feature.IS_COMPARATIVE).booleanValue()) {
			realised = element.getFeatureAsString(LexicalFeature.COMPARATIVE);

			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.COMPARATIVE);
			}
			if (realised == null) realised = baseForm;
		} else {
			realised = baseForm;
		}
		
		// Get gender from parent or "grandparent" or self, in that order
		NLGElement parent = element.getParent();
		Object function = element.getFeature(InternalFeature.DISCOURSE_FUNCTION);
		boolean feminine = false;
		if (parent != null) {
			if (function == DiscourseFunction.HEAD) {
				function = parent.getFeature(InternalFeature.DISCOURSE_FUNCTION);
			}

			if (!parent.hasFeature(LexicalFeature.GENDER) && parent.getParent() != null) {
				parent = parent.getParent();
			}
		} else {
			parent = element;
		}
		// if parent or grandparent is a verb phrase and the adjective is a modifier,
		// assume it's a direct object attribute if there is one
		if (parent.isA(PhraseCategory.VERB_PHRASE) && (function == DiscourseFunction.FRONT_MODIFIER
				|| function == DiscourseFunction.PRE_MODIFIER
				|| function == DiscourseFunction.POST_MODIFIER)) {
			List<NLGElement> complements = parent.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
			NLGElement directObject = null;
			for (NLGElement complement: complements) {
				if (complement.getFeature(InternalFeature.DISCOURSE_FUNCTION) == DiscourseFunction.OBJECT) {
					directObject = complement;
				}
			}
			if (directObject != null) parent = directObject;
		}
		feminine = Gender.FEMININE.equals( parent.getFeature(LexicalFeature.GENDER) );

		// Feminine
		// The rules used here apply to the most general cases.
		// Exceptions are meant to be specified in the lexicon or by the user
		// by means of the FrenchLexicalFeature.FEMININE_SINGULAR feature.
		// Reference : sections 528-534 of Grevisse (1993)
		if ( feminine ) {
			if (element.hasFeature(FrenchLexicalFeature.FEMININE_SINGULAR)) {
				realised = element.getFeatureAsString(FrenchLexicalFeature.FEMININE_SINGULAR);
			}
			else if (realised.endsWith("e")) {
				// do nothing
			}
			else if ( realised.endsWith("el") || realised.endsWith("eil") ) {
				realised += "le";
			}
			else if (realised.endsWith("en") || realised.endsWith("on")) {
				realised += "ne";
			}
			else if (realised.endsWith("et")) {
				realised += "te";
			}
			else if (realised.endsWith("eux")) {
				// replace 'x' by 's'
				realised = realised.substring(0, realised.length()-1) + "se";
			}
			else if (realised.endsWith("er")) {
				realised = realised.substring(0, realised.length()-2) + "ère";
			}
			else if (realised.endsWith("eau")) {
				realised = realised.substring(0, realised.length()-3) + "elle";
			}
			else if (realised.endsWith("gu")) {
				realised += "ë";
			}
			else if (realised.endsWith("g")) {
				realised += "ue";
			}
			else if (realised.endsWith("eur")
					// if replacing -eur by -ant gives a valid present participle
					&& element.getLexicon().hasWordFromVariant(
							realised.substring(0, realised.length()-3)+"ant")) {
				// replace 'r' by 's'
				realised = realised.substring(0, realised.length()-1) + "se";
			}
			else if (realised.endsWith("teur")) {
				realised = realised.substring(0, realised.length()-4) + "trice";
			}
			else if (realised.endsWith("if")) {
				realised = realised.substring(0, realised.length()-1) + "ve";
			}
			else {
				realised += "e";
			}
		}

		// Plural
		// The rules used here apply to the most general cases.
		// Exceptions are meant to be specified in the lexicon or by the user
		// by means of the LexicalFeature.PLURAL and
		// FrenchLexicalFeature.FEMININE_PLURAL features.
		// Reference : sections 538-539 of Grevisse (1993)
		if (parent.isPlural()) {
			if (feminine) {
				if (element.hasFeature(FrenchLexicalFeature.FEMININE_PLURAL)) {
					realised = element.getFeatureAsString(FrenchLexicalFeature.FEMININE_PLURAL);
				}
				else {
					realised += "s";
				}
			}
			else if (element.hasFeature(LexicalFeature.PLURAL)) {
				realised = element.getFeatureAsString(LexicalFeature.PLURAL);
			}
			else {
				realised = buildRegularPlural(realised);
			}
		}
		
		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
		return realisedElement;
	}

	/**
	 * Return an empty string if the element doesn't have a particle.
	 * If it has a non empty one, it returns it prepended by a dash.
	 * 
	 * @param element
	 * @return	the String to be appended to the element's realisation
	 */
	protected String getParticle(InflectedWordElement element) {
		String particle = element.getFeatureAsString(Feature.PARTICLE);
		
		if (particle == null) particle = "";
		else if (!particle.isEmpty()) particle = "-" + particle;
		
		return particle;
	}

	/**
	 * Builds the plural form of a noun or adjective following regular rules.
	 * 
	 * Source: Bechara, Evanildo. Moderna Gramática Portuguesa, pp. 117-123. 
	 * Nova Fronteira, 2009.
	 * 
	 * @author R. de Oliveira, University of Aberdeen.
	 * @param form form being realised on which to apply the plural morphology
	 * @return the plural form
	 */
	public String buildRegularPlural(String form) {
		
//		System.out.println("building regular plural");
		//TODO this method is being called several times for a single sentence 		
		
		// TODO improve rule for "ão";  too generic rule as it is
		//if form ends in "ão", delete "ão" and add "ões"
		if(form.endsWith("ão")){
			form = form.substring(0, form.length()-2) + "ões";
			
		//if form ends in "s"...
		} else if(form.endsWith("s")){
			// and vowel is stressed "a", unstress "a" and add "es"
			if(form.substring(form.length()-2, form.length()-1).matches("[áãâ]")){
				form = form.substring(0, form.length()-2) + "ases";
			}
			// and vowel is stressed "e" or "i", just add "es"
			if(form.substring(form.length()-2, form.length()-1).matches("[éêí]")){
				form = form + "es";
			}
			// and vowel is stressed "o", unstress "o" and add "es"
			if(form.substring(form.length()-2, form.length()-1).matches("[óõô]")){
				form = form.substring(0, form.length()-2) + "oses";
			}
			// and vowel is stressed "u", unstress "u" and add "es"
			if(form.substring(form.length()-2, form.length()-1).matches("[ú]")){
				form = form.substring(0, form.length()-2) + "uses";
			// but if vowels are not stressed
			} else {
				// do nothing, since plural form equals singular form
			}

		// if ending is "l", delete "l" and add "is" (too generic rule)
	    // TODO improve rule for "l" 
		} else if (form.endsWith("l")) {
				form = form.substring(0, form.length()-1) + "is";
		// if ending is "x"...
		} else if (form.endsWith("x")) {
				// do nothing, since plural form equals singular form 
		// if ending is "m", delete "m" and add "ns"
		} else if (form.endsWith("m")) {
			form = form.substring(0, form.length()-1) + "ns";
		// now same rule for multiple endings...
		} else {
		
			// get the last character of form as ending
			String ending = form.substring(form.length()-1);
			
			// if ending is -n or any vowel and the word is not 'que', add "s"
			if (ending.matches("[aáãeéiíoóõuún]") && !form.equals("que")) {
				form += "s";
			// if ending is "r" or "z", add "es"
			} else if (ending.matches("[rz]")) {
				form += "es";
			}		
		}
		return form;
	}

	/**
	 * This method performs the morphology for nouns.
	 * Based in part on the same method in the english rules
	 * Reference : sections 504-505 of Grevisse (1993)
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	@Override
	public StringElement doNounMorphology(
			InflectedWordElement element, WordElement baseWord) {
		String realised = "";

		// Check if the noun's gender needs to be changed
		// and change base form and baseWord accordingly
		if (baseWord != null) {
			Object elementGender = element.getFeature(LexicalFeature.GENDER);
			Object baseWordGender = baseWord.getFeature(LexicalFeature.GENDER);
			// The gender of the inflected word is opposite to the base word 
			if ((Gender.MASCULINE.equals(baseWordGender) &&	Gender.FEMININE.equals(elementGender))
				|| (Gender.FEMININE.equals(baseWordGender) && Gender.MASCULINE.equals(elementGender))) {
				
				String oppositeGenderForm = baseWord.getFeatureAsString(FrenchLexicalFeature.OPPOSITE_GENDER);
				
				if (oppositeGenderForm == null) {
					// build opposite gender form if possible
					if (Gender.MASCULINE.equals(baseWordGender)) {
						// the base word is masculine and the feminine must be build
						// (to be completed if necessary)
					}
					else {
						// the base word is feminine and the masculine must be build
						// (to be completed if necessary)
					}
				}
				// if oppositeGenderForm is specified or has been built
				if (oppositeGenderForm != null) {
					// change base form and base word
					element.setFeature(LexicalFeature.BASE_FORM, oppositeGenderForm);
					baseWord = baseWord.getLexicon().lookupWord(oppositeGenderForm, LexicalCategory.NOUN);
					element.setBaseWord(baseWord);
				}
			}
		}
		
		// base form from element if it exists, otherwise from baseWord 
		String baseForm = getBaseForm(element, baseWord);
		
		if (element.isPlural()
				&& !element.getFeatureAsBoolean(LexicalFeature.PROPER)) {

			String pluralForm = null;

			pluralForm = element.getFeatureAsString(LexicalFeature.PLURAL);

			if (pluralForm == null && baseWord != null) {
				pluralForm = baseWord.getFeatureAsString(LexicalFeature.PLURAL);
			}
			
			if (pluralForm == null) {
				pluralForm = buildRegularPlural(baseForm);
			}
			realised = pluralForm;
		} else {
			realised = baseForm;
		}
		
		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised.toString(), element);
		return realisedElement;
	}

	/**
	 * This method performs the morphology for verbs.
	 * Imperative forms are the same as subjunctive present forms and personal
	 * infinitive forms are the same as subjunctive future forms. 
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	@Override
	public NLGElement doVerbMorphology(InflectedWordElement element,
			WordElement baseWord) {

		String realised = null;
		
		Object tenseValue = element.getFeature(Feature.TENSE);
		String t = null;
		// default tense is PRESENT
		Tense tense = Tense.PRESENT;
		if (tenseValue instanceof Tense) {
			tense = (Tense) tenseValue;
			t = tense.name().toLowerCase();
		}
		
		Object personValue = element.getFeature(Feature.PERSON);
		String p = null;
		// default person is THIRD
		Person person = Person.THIRD;
		if (personValue instanceof Person) {
			person = (Person) personValue;
			p = person.name();
			if (p.equals("FIRST")){
				p = "1";
			} else if (p.equals("SECOND")){
				p = "2";
			} else if (p.equals("THIRD")){
				p = "3";
			}
		}
		
		Object numberValue = element.getFeature(Feature.NUMBER);
		String n = null;
		// default number is SINGULAR
		NumberAgreement number = NumberAgreement.SINGULAR;
		if (numberValue instanceof NumberAgreement) {
			number = (NumberAgreement) numberValue;
			n = number.name();
			if (n.equals("SINGULAR")){
				n = "s";
			} else if (n.equals("PLURAL")){
				n = "p";
				}
		}
		
		Object genderValue = element.getFeature(LexicalFeature.GENDER);
		// default gender is MASCULINE
		Gender gender = Gender.MASCULINE;
		if (genderValue instanceof Gender) {
			gender = (Gender) genderValue;
		}
		
		// if verb form (e.g. infinitive) is previously given, get it
		Object formValue = element.getFeature(Feature.FORM);
		
		// base form from baseWord if it exists, otherwise from element
		String baseForm = getBaseForm(element, baseWord);

		String realisedFromLexicon = baseWord.getFeatureAsString(t+p+n);
		
		if (Form.BARE_INFINITIVE.equals(formValue)) {
			realised = baseForm;
		// TODO make this method neater and move it to verb rules
		} else if (Form.PRESENT_PARTICIPLE.equals(formValue) || Form.GERUND.equals(formValue)){
			String radical = VerbRules.getVerbRadical(baseForm, tense);
			String thematicVowel = "";
			int conjugationType = VerbRules.getConjugationType(baseForm);
			switch (conjugationType){
			case 1:
				thematicVowel = "a";
				break;
			case 2:
				thematicVowel = "e";
				break;
			case 3:
				thematicVowel = "i";
				break;
			}
			realised = radical+thematicVowel+"ndo";
		// TODO make this method neater and move it to verb rules
		// TODO include gender
		} else if (Form.PAST_PARTICIPLE.equals(formValue)){
			String radical = VerbRules.getVerbRadical(baseForm, tense);
			String thematicVowel = "";
			String suffix = "";
			int conjugationType = VerbRules.getConjugationType(baseForm);
			switch (conjugationType){
			case 1:
				thematicVowel = "a";
				break;
			case 2: case 3:
				thematicVowel = "i";
				break;
			}
			switch (number){
			case SINGULAR: case BOTH:
				switch (gender){
				case MASCULINE: case NEUTER:
					suffix = "o";
					break;
				case FEMININE:
					suffix = "a";
					break;
				}
				break;
			case PLURAL:
				switch (gender){
				case MASCULINE: case NEUTER:
					suffix = "os";
					break;
				case FEMININE:
					suffix = "as";
					break;
				}
				break;
			}
			realised = radical+thematicVowel+"d"+suffix;
		//TODO once debugged auxiliary creation in VerbPhraseHelper, delete these (and
		// called methods
		// here begins a list of irregular verbs, that is far from complete
		}
		// if inflected form based on tense, person and number exists in lexicon file,
		// get that form and finish method here; otherwise apply rules below
		else if (realisedFromLexicon != null) {
			realised = realisedFromLexicon;
			StringElement realisedElement = new StringElement(realised, element);
			return realisedElement;
		}else if(baseForm.equals("estar")){
			realised = VerbRules.conjugateEstar(number, person, tense);
		} else if(baseForm.equals("ser")){
			realised = VerbRules.conjugateSer(number, person, tense);
		} else if(baseForm.equals("ter")){
			realised = VerbRules.conjugateTer(number, person, tense);
		} else if(baseForm.equals("ir")){
			realised = VerbRules.conjugateIr(number, person, tense);
		} else if(baseForm.equals("dar")){
			realised = VerbRules.conjugateDar(number, person, tense);
		// here begins the set of regular verb conjugations
		} else {
			switch (tense) {
			case CONDITIONAL:
				realised = VerbRules.buildConditionalRegularVerb(
						baseForm, number, person);
				break;
			case FUTURE:
				realised = VerbRules.buildFutureRegularVerb(
						baseForm, number, person);
				break;
			// same mechanism as subjunctive present 
			case IMPERATIVE:
				realised = VerbRules.buildSubjunctivePresentRegularVerb(
						baseForm, number, person);
				break;
			case IMPERFECT:
				realised = VerbRules.buildImperfectRegularVerb(
						baseForm, number, person);
				break;
			// no morphological mechanism required
			case IMPERSONAL_INFINITIVE:
				realised = baseForm;
				break;
			case PAST:
				realised = VerbRules.buildPastRegularVerb(
						baseForm, number, person);
				break;
			// same mechanism as subjunctive future
			case PERSONAL_INFINITIVE:
				realised = VerbRules.buildSubjunctiveFutureRegularVerb(
						baseForm, number, person);
				break;
			case PLUPERFECT:
				realised = VerbRules.buildPluperfectRegularVerb(
						baseForm, number, person);
				break;
			case PRESENT:
				realised = VerbRules.buildPresentRegularVerb(
						baseForm, number, person);
				break;
			case SUBJUNCTIVE_FUTURE:
				realised = VerbRules.buildSubjunctiveFutureRegularVerb(
						baseForm, number, person);
				break;
			case SUBJUNCTIVE_IMPERFECT:
				realised = VerbRules.buildSubjunctiveImperfectRegularVerb(
						baseForm, number, person);
				break;
			case SUBJUNCTIVE_PRESENT:
				realised = VerbRules.buildSubjunctivePresentRegularVerb(
						baseForm, number, person);
				break;			
			}
//			if(tense == Tense.PRESENT) {
////				System.out.println(baseForm+" tense is present");
//				realised = VerbRules.buildPresentRegularVerb(baseForm, number, 
//						person);
//			} else if (tense == Tense.PAST) {
////				System.out.println(baseForm+" "+number+" "+person);
//				realised = VerbRules.buildPastRegularVerb(
//						baseForm, number, person);
//			} else if (tense == Tense.IMPERATIVE) {
//				realised = VerbRules.buildImperativeRegularVerb(
//					baseForm, number, person);
//			} else if (tense == Tense.PERSONAL_INFINITIVE) {
//				realised = VerbRules.buildPersonalInfinitiveVerb(
//					baseForm, number, person);
//			}
		}
		
		// may never need this...
//		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
		return realisedElement;
		
	}

	/**
	 * This method performs the morphology for adverbs.
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	public NLGElement doAdverbMorphology(InflectedWordElement element,
			WordElement baseWord) {

		String realised = null;

		// base form from baseWord if it exists, otherwise from element
		String baseForm = getBaseForm(element, baseWord);

		// Comparatives and superlatives are mainly treated by syntax
		// in French. Only exceptions, provided by the lexicon, are
		// treated by morphology.
		if (element.getFeatureAsBoolean(Feature.IS_COMPARATIVE).booleanValue()) {
			realised = element.getFeatureAsString(LexicalFeature.COMPARATIVE);

			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.COMPARATIVE);
			}
			if (realised == null) realised = baseForm;
		} else {
			realised = baseForm;
		}

		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
		return realisedElement;
	}

	/**
	 * This method performs the morphology for pronouns.
	 * Reference : sections 633-634 of Grevisse (1993)
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	public NLGElement doPronounMorphology(InflectedWordElement element) {
		String realised = element.getBaseForm();
		
		Object type = element.getFeature(FrenchLexicalFeature.PRONOUN_TYPE);
		// inflect only personal pronouns, exluding complement pronouns ("y" and "en")
		if (type == PronounType.PERSONAL
			&& element.getFeature(InternalFeature.DISCOURSE_FUNCTION)
				!= DiscourseFunction.COMPLEMENT) {
			
			// this will contain the features we want the pronoun to have
			Map<String, Object> pronounFeatures = new HashMap<String, Object>();
	
			pronounFeatures.put(FrenchLexicalFeature.PRONOUN_TYPE, type);
			
			boolean passive = element.getFeatureAsBoolean(Feature.PASSIVE);
			boolean reflexive = element.getFeatureAsBoolean(LexicalFeature.REFLEXIVE);
			boolean detached = isDetachedPronoun(element);
			NLGElement parent = element.getParent();
			
			Object gender = element.getFeature(LexicalFeature.GENDER);
			if (!(gender instanceof Gender) || gender == Gender.NEUTER) gender = Gender.MASCULINE;
			
			Object person = element.getFeature(Feature.PERSON);
			Object number = element.getFeature(Feature.NUMBER);			
			// agree the reflexive pronoun with the subject
			if (reflexive && parent != null) {
				NLGElement grandParent =  parent.getParent();
				if (grandParent != null && grandParent.getCategory().equalTo(PhraseCategory.VERB_PHRASE)) {
					person = grandParent.getFeature(Feature.PERSON);
					number = grandParent.getFeature(Feature.NUMBER);
					
					// If the verb phrase is in imperative form,
					// the reflexive pronoun can only be in 2S, 1P or 2P.
					if (grandParent.getFeature(Feature.FORM) == Form.IMPERATIVE) {
						if (number == NumberAgreement.PLURAL) {
							if (person != Person.FIRST && person != Person.SECOND) {
								person = Person.SECOND;
							}
						} else {
							person = Person.SECOND;
						}
					}
				}
			}
			if (!(person instanceof Person)) person = Person.THIRD;
			if (!(number instanceof NumberAgreement)) number = NumberAgreement.SINGULAR;
			
			Object function = element.getFeature(InternalFeature.DISCOURSE_FUNCTION);
			// If the pronoun is the head of a noun phrase,
			// take the discourse function of this noun phrase
			if (function == DiscourseFunction.SUBJECT && parent != null
					&& parent.isA(PhraseCategory.NOUN_PHRASE)) {
				function = parent.getFeature(InternalFeature.DISCOURSE_FUNCTION);
			}
			if (!detached && !(function instanceof DiscourseFunction)) function = DiscourseFunction.SUBJECT;
			if (passive) {
				if (function == DiscourseFunction.SUBJECT) function = DiscourseFunction.OBJECT;
				else if (function == DiscourseFunction.OBJECT) function = DiscourseFunction.SUBJECT;
			}
			
			if (function != DiscourseFunction.OBJECT && function != DiscourseFunction.INDIRECT_OBJECT
					&& !detached) {
				reflexive = false;
			}
			
			pronounFeatures.put(Feature.PERSON, person);
			
			// select wich features to include in search depending on pronoun features,
			// syntactic function and wether the pronoun is detached from the verb
			if (person == Person.THIRD) {
				pronounFeatures.put(LexicalFeature.REFLEXIVE, reflexive);
				pronounFeatures.put(FrenchLexicalFeature.DETACHED, detached);
				if (!reflexive) {
					pronounFeatures.put(Feature.NUMBER, number);
					if (!detached) {
						pronounFeatures.put(InternalFeature.DISCOURSE_FUNCTION, function);
						if ((number != NumberAgreement.PLURAL && function != DiscourseFunction.INDIRECT_OBJECT)
								|| function == DiscourseFunction.SUBJECT) {
							pronounFeatures.put(LexicalFeature.GENDER, gender);
						}
					} else {
						pronounFeatures.put(LexicalFeature.GENDER, gender);
					}
				}
			} else {
				pronounFeatures.put(Feature.NUMBER, number);
				if (!element.isPlural()) {
					pronounFeatures.put(FrenchLexicalFeature.DETACHED, detached);
					if (!detached) {
						if (function != DiscourseFunction.SUBJECT) function = null;
						pronounFeatures.put(InternalFeature.DISCOURSE_FUNCTION, function);
					}
				}
			}
	
			Lexicon lexicon = element.getLexicon();
			// search the lexicon for the right pronoun
			WordElement proElement =
				lexicon.getWord(LexicalCategory.PRONOUN, pronounFeatures);
			
			// if the right pronoun is not found in the lexicon,
			// leave the original pronoun
			if (proElement != null) {
				element = new InflectedWordElement(proElement);
				realised = proElement.getBaseForm();
			}
			
		// Agreement of relative pronouns with parent noun phrase.
		} else if (type == PronounType.RELATIVE) {
			// Get parent clause.
			NLGElement antecedent = element.getParent();
			while (antecedent != null
					&& !antecedent.isA(PhraseCategory.CLAUSE)) {
				antecedent = antecedent.getParent();
			}
/*			// Skip parent noun phrase if necessary.
			if (antecedent != null && antecedent.isA(PhraseCategory.NOUN_PHRASE)) {
				antecedent = antecedent.getParent();
			}
			
			// Skip parent prepositional phrase if necessary.
			if (antecedent != null && antecedent.isA(PhraseCategory.PREPOSITIONAL_PHRASE)) {
				antecedent = antecedent.getParent();
			}
*/
			
			if (antecedent != null) {
				// Get parent noun phrase of parent clause.
				antecedent = antecedent.getParent();
				if (antecedent != null) {
					boolean feminine = antecedent.getFeature(LexicalFeature.GENDER)
							== Gender.FEMININE;
					boolean plural = antecedent.getFeature(Feature.NUMBER)
							== NumberAgreement.PLURAL;
					
					// Lookup lexical entry for appropriate form.
					// If the corresponding form is not found :
					// Feminine plural defaults to masculine plural.
					// Feminine singular and masculine plural default
					// to masculine singular.
					String feature = null;
					if (feminine && plural) {
						feature = element.getFeatureAsString(
								FrenchLexicalFeature.FEMININE_PLURAL);
					} else if (feminine) {
						feature = element.getFeatureAsString(
								FrenchLexicalFeature.FEMININE_SINGULAR);
					}
					
					if (plural && feature == null ) {
						feature = element.getFeatureAsString(
								LexicalFeature.PLURAL);
					}
					
					if (feature != null) realised = feature;
				}
			}
		}
	
		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);

		return realisedElement;
	}

	/**
	 * Determine if the pronoun is detached ("disjoint") from the verb.
	 * 
	 * @param element the pronoun
	 * @return true if element is a pronoun and is detached from the web
	 */
	public boolean isDetachedPronoun(InflectedWordElement element) {
		boolean detached = false;
		
		if (element.getCategory().equalTo(LexicalCategory.PRONOUN)) {
			NLGElement parent = element.getParent();
			Object function;
			
			if (parent != null) {
				function = parent.getFeature(InternalFeature.DISCOURSE_FUNCTION);
				// If the pronoun isn't a subject or an object, it is detached.
				
				if (!(function == DiscourseFunction.SUBJECT
						|| function == DiscourseFunction.OBJECT
						|| function == DiscourseFunction.INDIRECT_OBJECT)) {
					detached = true;
				} else {
					NLGElement grandParent = parent.getParent();
					// If the pronoun is in a prepositional phrase,
					// or it is 1rst or 2nd person and the verb is in imperative form
					// but not negated, it is detached.
					Object person = element.getFeature(Feature.PERSON);
					boolean reflexive = element.getFeatureAsBoolean(LexicalFeature.REFLEXIVE);
					boolean person1or2 = (person == Person.FIRST || person == Person.SECOND);
					if ( PhraseCategory.PREPOSITIONAL_PHRASE.equalTo(parent.getCategory())
						|| ((person1or2 || reflexive)
								&& parent.getFeature(Feature.FORM) == Form.IMPERATIVE
								&& !parent.getFeatureAsBoolean(Feature.NEGATED)) ||
							parent instanceof CoordinatedPhraseElement ||
						(grandParent != null &&
							(PhraseCategory.PREPOSITIONAL_PHRASE.equalTo(grandParent.getCategory())
								|| ((person1or2 || reflexive)
										&& grandParent.getFeature(Feature.FORM) == Form.IMPERATIVE
										&& !grandParent.getFeatureAsBoolean(Feature.NEGATED))
								|| grandParent instanceof CoordinatedPhraseElement))) {
						detached = true;
					}
				}
			// if there's no parent
			} else detached = true;
		}
		
		return detached;
	}

}
