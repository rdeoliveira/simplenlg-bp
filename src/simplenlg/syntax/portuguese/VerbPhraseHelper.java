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
package simplenlg.syntax.portuguese;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import simplenlg.features.DiscourseFunction;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.InternalFeature;
import simplenlg.features.InterrogativeType;
import simplenlg.features.LexicalFeature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.portuguese.PortugueseFeature;
import simplenlg.features.portuguese.PortugueseLexicalFeature;
import simplenlg.features.portuguese.PronounType;
import simplenlg.features.portuguese.PortugueseInternalFeature;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.ListElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.framework.StringElement;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.portuguese.XMLLexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;

/**
 * This class contains static methods to help the syntax processor realise verb
 * phrases for Portuguese.
 * 
 * @author R. de Oliveira, University of Aberdeen.
 */
public class VerbPhraseHelper extends simplenlg.syntax.english.nonstatic.VerbPhraseHelper {
	
	// the following lists are NOT exhaustive; they shall grow as required
	List<String> aModals = Arrays.asList("começar","continuar","voltar");
	List<String> deModals = Arrays.asList("acabar","chegar","deixar","gostar",
			"gostar","parar","ter");
	List<String> paraModals = Arrays.asList("dar");
	
	/**
	 * The main method for realising verb phrases.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> to be realised.
	 * @return the realised <code>NLGElement</code>.
	 */
	@Override
	public NLGElement realise(PhraseElement phrase) {
		ListElement realisedElement = null;
		Stack<NLGElement> vgComponents = null;
		Stack<NLGElement> mainVerbRealisation = new Stack<NLGElement>();
		Stack<NLGElement> auxiliaryRealisation = new Stack<NLGElement>();

		if (phrase != null) {
			vgComponents = createVerbGroup(phrase);
			splitVerbGroup(vgComponents, mainVerbRealisation,
					auxiliaryRealisation);

			// vaudrypl added phrase argument to ListElement constructor
			// to copy all features from the PhraseElement
//			realisedElement = new ListElement(phrase);
			realisedElement = new ListElement();
			// TODO: double check that setFactory does not do anything else
			realisedElement.setFactory(phrase.getFactory());

			if ((!phrase.hasFeature(InternalFeature.REALISE_AUXILIARY)
					|| phrase.getFeatureAsBoolean(InternalFeature.REALISE_AUXILIARY))
							&& !auxiliaryRealisation.isEmpty()) {

				realiseAuxiliaries(realisedElement,
						auxiliaryRealisation);

				NLGElement verb = mainVerbRealisation.peek();
				Object verbForm = verb.getFeature(Feature.FORM);
				if (verbForm == Form.INFINITIVE) {
					realiseMainVerb(phrase, mainVerbRealisation,
							realisedElement);
					phrase.getPhraseHelper().realiseList(realisedElement, phrase
							.getPreModifiers(), DiscourseFunction.PRE_MODIFIER);
				} else {
					phrase.getPhraseHelper().realiseList(realisedElement, phrase
							.getPreModifiers(), DiscourseFunction.PRE_MODIFIER);
					realiseMainVerb(phrase, mainVerbRealisation,
							realisedElement);
				}
			} else {
				realiseMainVerb(phrase, mainVerbRealisation,
						realisedElement);
				phrase.getPhraseHelper().realiseList(realisedElement, phrase
						.getPreModifiers(), DiscourseFunction.PRE_MODIFIER);
			
			}
			realiseComplements(phrase, realisedElement);
			phrase.getPhraseHelper().realiseList(realisedElement, phrase
					.getPostModifiers(), DiscourseFunction.POST_MODIFIER);
		}
		
		return realisedElement;
	}

	/**
	 * Checks to see if the base form of the word is copular.
	 * 
	 * @param element
	 *            the element to be checked
	 * @return <code>true</code> if the element is copular.
	 */
	@Override
	public boolean isCopular(NLGElement element) {
		if (element != null) {
			return element.getFeatureAsBoolean(PortugueseLexicalFeature.COPULAR);
		} else return true;
	}

	/**
	 * Splits the stack of verb components into two sections. One being the verb
	 * associated with the main verb group, the other being associated with the
	 * auxiliary verb group.
	 * 
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @param mainVerbRealisation
	 *            the main group of verbs.
	 * @param auxiliaryRealisation
	 *            the auxiliary group of verbs.
	 */
	@Override
	protected void splitVerbGroup(Stack<NLGElement> vgComponents,
			Stack<NLGElement> mainVerbRealisation,
			Stack<NLGElement> auxiliaryRealisation) {

		boolean mainVerbSeen = false;
		boolean cliticsSeen = false;

		for (NLGElement word : vgComponents) {
			if (!mainVerbSeen) {
				mainVerbRealisation.push(word);
//				if (!word.equals("pas") &&
				if (!word.isA(LexicalCategory.ADVERB) &&
						!word.getFeatureAsBoolean(PortugueseInternalFeature.CLITIC)) {
					mainVerbSeen = true;
				}
			} else if (!cliticsSeen) {
//				if (!word.equals("ne") &&
				if (!"ne".equals(word.getFeatureAsString(LexicalFeature.BASE_FORM)) &&
						!word.getFeatureAsBoolean(PortugueseInternalFeature.CLITIC)) {
					cliticsSeen = true;
					auxiliaryRealisation.push(word);
				} else {
					mainVerbRealisation.push(word);
				}
			} else {
				auxiliaryRealisation.push(word);
			}
		}

	}

	/**
	 * Creates a stack of verbs for the verb phrase. Additional auxiliary verbs
	 * are added as required based on the features of the verb phrase. The final
	 * verb order should be: ("ir" +)(modal +)("ter" +)("estar" +)main. 
	 * 
	 * Based on English method of the same name.
	 * 
	 * Source: Bechara, Evanildo. Moderna Gramática Portuguesa, pp. 230-233. 
	 * Nova Fronteira, 2009. 
	 * 
	 * @author R. de Oliveira, University of Aberdeen.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this verb phrase.
	 * @return the verb group as a <code>Stack</code> of <code>NLGElement</code>
	 *         s.
	 */
	@Override
	protected Stack<NLGElement> createVerbGroup(PhraseElement phrase) {
		
		// constructs the verb group as a stack
		Stack<NLGElement> vgComponents = new Stack<NLGElement>();
		ArrayList<NLGElement> vgComponentsArray = new ArrayList<NLGElement>();
		
		// constructs lexicon, so morphological rules are set for Portuguese
		Lexicon ptLexicon = phrase.getLexicon();
		// get all verb phrase features
		Object tenseValue = phrase.getFeature(Feature.TENSE);
		Object personValue = phrase.getFeature(Feature.PERSON);
		Object numberValue = phrase.getFeature(Feature.NUMBER);
		String progressiveString = phrase.getFeatureAsString(Feature.PROGRESSIVE);
		boolean perfect = phrase.getFeatureAsBoolean(Feature.PERFECT);
		boolean prospective = phrase.getFeatureAsBoolean(Feature.PROSPECTIVE);
		String modal = phrase.getFeatureAsString(Feature.MODAL);
		boolean negated = phrase.getFeatureAsBoolean(Feature.NEGATED);
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		Object gender = phrase.getFeature(LexicalFeature.GENDER);

		// gets head verb and adds it as first element in the array
		NLGElement headVerb = phrase.getHead();
		if (headVerb != null) {
			vgComponentsArray.add(headVerb);
		} else {
			return vgComponents;
		}
		
		// if the entire verb phrase has passive feature...
		if (passive) {
			// creates auxiliary "ser"...
			WordElement auxWord = new WordElement("ser", LexicalCategory.VERB, 
					ptLexicon);
			InflectedWordElement aux = new InflectedWordElement(auxWord);
			// adds it to the end of the array...
			vgComponentsArray.add(aux);
			// and sets previous verb as past participle with according number
			// and gender.
			vgComponentsArray.get(vgComponentsArray.indexOf(aux)-1).
				setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
			vgComponentsArray.get(vgComponentsArray.indexOf(aux)-1).
			setFeature(Feature.NUMBER, numberValue);
		}
		
		// if the entire verb phrase has progressive feature...
		if (!progressiveString.equals("false")) {
			// grabs the string associated with the progressive feature, if any...
			String auxString = phrase.getFeatureAsString(Feature.PROGRESSIVE);
			WordElement auxWord;
			// if there was no string...
			if (progressiveString.equals("true")){
				// creates auxiliary "estar" as deafult...
				auxWord = new WordElement("estar", LexicalCategory.VERB, 
					ptLexicon);
			// but if there was an specific string -- even if "estar"...
			} else {
				// creates auxiliary using that string 
				auxWord = new WordElement(auxString, LexicalCategory.VERB, 
					ptLexicon);
			}
			// finishes creation of auxiliary TODO really necessary?
			InflectedWordElement aux = new InflectedWordElement(auxWord);
			// adds it to the end of the array...
			vgComponentsArray.add(aux);
			// and sets previous verb as present participle (-ndo).
			vgComponentsArray.get(vgComponentsArray.indexOf(aux)-1).
				setFeature(Feature.FORM, Form.PRESENT_PARTICIPLE);
		}
		
		// if the entire verb phrase has perfect(ive) feature...
		if (perfect) {
			// creates auxiliary "ter"...
			WordElement auxWord = new WordElement("ter", LexicalCategory.VERB, 
					ptLexicon);
			InflectedWordElement aux = new InflectedWordElement(auxWord);
			// adds it to the end of the array...
			vgComponentsArray.add(aux);
			// and sets previous verb as past participle (-do).
			vgComponentsArray.get(vgComponentsArray.indexOf(aux)-1).
				setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
		}
		
		// if the entire verb phrase has a modal (which should be a string)...
		if (modal!=null) {
			// creates modal with grabbed modal string...
			WordElement auxWord = new WordElement(modal, LexicalCategory.VERB, 
					ptLexicon);
			InflectedWordElement aux = new InflectedWordElement(auxWord);
			// checks if the modal requires preposition "a"
			if (aModals.contains(modal)){
				// creates preposition "a"
				WordElement aPrepWord = new WordElement("a", LexicalCategory.PREPOSITION, 
						ptLexicon);
				InflectedWordElement aPrep = new InflectedWordElement(aPrepWord);
				// adds preposition to array
				vgComponentsArray.add(aPrep);
				// adds modal to the end of the array...
				vgComponentsArray.add(aux);
				// and sets previous verb as infinitive.
				vgComponentsArray.get(vgComponentsArray.indexOf(aux)-2).
					setFeature(Feature.FORM, Form.BARE_INFINITIVE);
			// checks if the modal requires preposition "de"
			} else if (deModals.contains(modal)){
					// creates preposition "de"
					WordElement dePrepWord = new WordElement("de", LexicalCategory.PREPOSITION, 
							ptLexicon);
					InflectedWordElement dePrep = new InflectedWordElement(dePrepWord);
					// adds preposition to array
					vgComponentsArray.add(dePrep);
					// adds modal to the end of the array...
					vgComponentsArray.add(aux);
					// and sets previous verb as infinitive.
					vgComponentsArray.get(vgComponentsArray.indexOf(aux)-2).
						setFeature(Feature.FORM, Form.BARE_INFINITIVE);
					// checks if the modal requires preposition "para"
			} else if (paraModals.contains(modal)){
				// creates preposition "para"
				WordElement paraPrepWord = new WordElement("para", LexicalCategory.PREPOSITION, 
						ptLexicon);
				InflectedWordElement paraPrep = new InflectedWordElement(paraPrepWord);
				// adds preposition to array
				vgComponentsArray.add(paraPrep);
				// adds modal to the end of the array...
				vgComponentsArray.add(aux);
				// and sets previous verb as infinitive.
				vgComponentsArray.get(vgComponentsArray.indexOf(aux)-2).
					setFeature(Feature.FORM, Form.BARE_INFINITIVE);
		// in case the modal doesn't require a preposition
			} else {
				// adds the modal to the end of the array...
				vgComponentsArray.add(aux);
				// and sets previous verb as infinitive.
				vgComponentsArray.get(vgComponentsArray.indexOf(aux)-1).
					setFeature(Feature.FORM, Form.BARE_INFINITIVE);
			}
		}
	
		if (prospective) {
			// creates auxiliary "ir"...
			WordElement auxWord = new WordElement("ir", LexicalCategory.VERB, 
					ptLexicon);
			InflectedWordElement aux = new InflectedWordElement(auxWord);
			// adds it to the end of the array...
			vgComponentsArray.add(aux);
			// and sets previous verb as infinitive.
			vgComponentsArray.get(vgComponentsArray.indexOf(aux)-1).
				setFeature(Feature.FORM, Form.BARE_INFINITIVE);
		}
		
		if (negated) {
			// creates particle "não"...
			WordElement notWord = new WordElement("não", LexicalCategory.ANY, 
					ptLexicon);
			InflectedWordElement not = new InflectedWordElement(notWord);
			// adds it to the end of the array...
			vgComponentsArray.add(not);
		}
		
		// passing features to agreement verb (last verb in array, topmost in stack)
		NLGElement lastVerb;
		// if there is "não" in the VP, the second last element is last verb 
		if (negated) {
			lastVerb = vgComponentsArray.get(vgComponentsArray.size()-2);
		// if there isn't a "não" the last element is the last verb
		} else {
			lastVerb = vgComponentsArray.get(vgComponentsArray.size()-1);
		}
		// now passing features
		lastVerb.setFeature(Feature.TENSE, tenseValue);
		lastVerb.setFeature(Feature.PERSON, personValue);
		lastVerb.setFeature(Feature.NUMBER, numberValue);
		lastVerb.setFeature(Feature.FORM, Form.NORMAL);
		
		if (passive) {
			NLGElement firstVerb = vgComponentsArray.get(0);
			firstVerb.setFeature(LexicalFeature.GENDER, gender);
		}
		
//		// printing verb phrase...
//		System.out.println("Verb Phrase: "+
//				"head: "+phrase.getHead()+
//				"; progressive: "+phrase.getFeatureAsString(Feature.PROGRESSIVE)+
//				"; perfect: "+phrase.getFeatureAsString(Feature.PERFECT));		
		
		// stack population = last in array becomes top of stack
		for (NLGElement verb : vgComponentsArray){
			
//			// printing out each verb in array; remember: first is last!
//			int position = vgComponentsArray.indexOf(verb);
//			String baseWord = verb.getFeatureAsString(InternalFeature.BASE_WORD);
//			String person = verb.getFeatureAsString(Feature.PERSON);
//			String number = verb.getFeatureAsString(Feature.NUMBER);
//			String tense = verb.getFeatureAsString(Feature.TENSE);
//			String form = verb.getFeatureAsString(Feature.FORM);
//			System.out.println(
//				"verb "+(position+1)+": "+baseWord+"; "+
//				person+"; "+number+"; "+tense+"; "+form);
			
			// placing array item (from left to right) on top of the stack 
			vgComponents.push(verb);
		}
		
//		System.out.println("  * * * * *  ");
		
		// return stack
		return vgComponents;
	}

	/**
	 * Determine which pronominal complements are clitics and inserts
	 * them in the verb group components.
	 * Reference : section 657 of Grevisse (1993)
	 * 
	 * @param phrase
	 * @param vgComponents
	 */
	protected NLGElement insertCliticComplementPronouns(PhraseElement phrase,
			Stack<NLGElement> vgComponents) {
		List<NLGElement> complements =
			phrase.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		NLGElement pronounEn = null, pronounY = null,
					directObject = null, indirectObject = null;

		// identify clitic candidates
		for (NLGElement complement : complements) {
			if (complement != null && !complement.getFeatureAsBoolean(Feature.ELIDED)) {
				Object discourseValue = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);
				if (!(discourseValue instanceof DiscourseFunction)) {
					discourseValue = DiscourseFunction.COMPLEMENT;
				}
				// Realise complement only if it is not the relative phrase of
				// the parent clause and not a phrase with the same function in case
				// of a direct or indirect object.
				NLGElement parent = phrase.getParent();
				if ( parent == null ||
						(complement != parent.getFeatureAsElement(PortugueseFeature.RELATIVE_PHRASE) &&
							(discourseValue == DiscourseFunction.COMPLEMENT ||
								!parent.hasRelativePhrase((DiscourseFunction) discourseValue)))) {
					NLGElement head = null;
					Object type = null;
					
					// if a complement is or contains a pronoun, or will be pronominalised
					if (complement.isA(LexicalCategory.PRONOUN)) {
						head = complement;
					} else if (complement instanceof NPPhraseSpec
							&& ((NPPhraseSpec)complement).getHead() != null
							&& ((NPPhraseSpec)complement).getHead().isA(LexicalCategory.PRONOUN)) {
						head = ((NPPhraseSpec)complement).getHead();
					}
					else if (complement.getFeatureAsBoolean(Feature.PRONOMINAL)) {
						type = PronounType.PERSONAL;
					}
					
					if (head != null) {
						type = head.getFeature(PortugueseLexicalFeature.PRONOUN_TYPE);
					}
					
					if (type != null) {
						complement.setFeature(PortugueseInternalFeature.CLITIC, false);
						if (type == PronounType.SPECIAL_PERSONAL) {
							String baseForm = ((WordElement)head).getBaseForm();
							if (baseForm.equals("en")) {
								pronounEn = complement;
							}
							else if (baseForm.equals("y")) {
								pronounY = complement;
							}
						} else if (type == PronounType.PERSONAL) {
							Object discourseFunction = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);
							if (discourseFunction == DiscourseFunction.OBJECT && !passive) {
								directObject = complement;
							} else if (discourseFunction == DiscourseFunction.INDIRECT_OBJECT) {
								indirectObject = complement;
							}
						}
					}
				}
			}
		}
		
		// place clitics in order :
		// (indirect object) (direct object) y en
		
		if (pronounEn != null) {
			pronounEn.setFeature(PortugueseInternalFeature.CLITIC, true);
			vgComponents.push(pronounEn);
		}
		
		if (pronounY != null) {
			pronounY.setFeature(PortugueseInternalFeature.CLITIC, true);
			vgComponents.push(pronounY);
		}
		
		if (directObject != null) {
			directObject.setFeature(PortugueseInternalFeature.CLITIC, true);
			vgComponents.push(directObject);
		}
		
		// the indirect object is clitic if there's no direct object
		// or if it is third person and not reflexive
		if ( indirectObject != null && (directObject == null || 
				((directObject.getFeature(Feature.PERSON) == Person.THIRD
						|| directObject.getFeature(Feature.PERSON) == null)
					&& !directObject.getFeatureAsBoolean(LexicalFeature.REFLEXIVE) )) ) {
			
			indirectObject.setFeature(PortugueseInternalFeature.CLITIC, true);

			Object person = indirectObject.getFeature(Feature.PERSON);
			boolean luiLeurPronoun = (person == null || person == Person.THIRD);
			// place indirect object after direct object if indirect object is "lui" or "leur"
			if (directObject != null && luiLeurPronoun) vgComponents.pop();
			vgComponents.push(indirectObject);
			if (directObject != null && luiLeurPronoun) {
				vgComponents.push(directObject);
			}
		}
		
		// return the direct object for use with past participle agreement with auxiliary "avoir"
		return directObject;
	}

	/**
	 * Checks to see if the phrase is in infinitive form. If it is then
	 * no morphology is done on the main verb.
	 * 
	 * Based on English method checkImperativeInfinitive(...)
	 * 
	 * @param formValue
	 *            the <code>Form</code> of the phrase.
	 * @param frontVG
	 *            the first verb in the verb group.
	 */
	protected void checkInfinitive(Object formValue,
			NLGElement frontVG) {

		if ((Form.INFINITIVE.equals(formValue) || Form.BARE_INFINITIVE.equals(formValue))
				&& frontVG != null) {
			frontVG.setFeature(InternalFeature.NON_MORPH, true);
		}
	}

	/**
	 * Adds the passive auxiliary verb to the front of the group.
	 * 
	 * Based on English method addBe(...)
	 * 
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @return the new element for the front of the group.
	 */
	protected NLGElement addPassiveAuxiliary(NLGElement frontVG,
			Stack<NLGElement> vgComponents, PhraseElement phrase) {

		// adds the current front verb in pas participle form
		// with aggreement with the subject (auxiliary "être")
		if (frontVG != null) {
			frontVG.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
			Object number = phrase.getFeature(Feature.NUMBER);
			frontVG.setFeature(Feature.NUMBER, number);
			Object gender = phrase.getFeature(LexicalFeature.GENDER);
			frontVG.setFeature(LexicalFeature.GENDER, gender);
			vgComponents.push(frontVG);
		}
		// adds auxiliary "être"
		WordElement passiveAuxiliary = (WordElement)
			frontVG.getLexicon().lookupWord("être", LexicalCategory.VERB); //$NON-NLS-1$
		return new InflectedWordElement(passiveAuxiliary);
	}
	
	/**
	 * Says if the verb phrase has a reflexive object (direct or indirect)
	 * 
	 * @param phrase	the verb phrase
	 * @return			true if the verb phrase has a reflexive object (direct or indirect)
	 */
	protected boolean hasReflexiveObject(PhraseElement phrase) {
		boolean reflexiveObjectFound = false;
		List<NLGElement> complements =
			phrase.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		Object subjectPerson = phrase.getFeature(Feature.PERSON);
		Object subjectNumber = phrase.getFeature(Feature.NUMBER);
		if (subjectNumber != NumberAgreement.PLURAL) {
			subjectNumber = NumberAgreement.SINGULAR;
		}
		
		for (NLGElement complement : complements) {
			if (complement != null && !complement.getFeatureAsBoolean(Feature.ELIDED)) {
				
				Object function = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);
				boolean reflexive = complement.getFeatureAsBoolean(LexicalFeature.REFLEXIVE);
				Object person = complement.getFeature(Feature.PERSON);
				Object number = complement.getFeature(Feature.NUMBER);
				if (number != NumberAgreement.PLURAL) {
					number = NumberAgreement.SINGULAR;
				}
				
				// if the complement is a direct or indirect object
				if ( (function == DiscourseFunction.INDIRECT_OBJECT
						|| (!passive && function == DiscourseFunction.OBJECT))
					// and if it is reflexive, or the same as the subject if not third person
					&& ( reflexive ||
						((person == Person.FIRST || person == Person.SECOND)
								&& person == subjectPerson && number == subjectNumber) )) {
					reflexiveObjectFound = true;
					break;
				}
			}
		}
		
		return reflexiveObjectFound;
	}

	/**
	 * Adds <em>pas</em> to the stack if the phrase is negated.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param hasModal
	 *            the phrase has a modal
	 * @return the new element for the front of the group.
	 */
	protected void createPas(PhraseElement phrase,
			Stack<NLGElement> vgComponents, NLGElement frontVG, boolean hasModal) {
		boolean pasForbiddenByArgument = phrase.checkIfNeOnlyNegation();
		if (phrase.getFeatureAsBoolean(Feature.NEGATED)) {
			// first get negation auxiliary; if not specified, it is "pas" by default
			WordElement negation = null;
			Lexicon lexicon = phrase.getLexicon();
			
			Object negationObject = phrase.getFeature(PortugueseFeature.NEGATION_AUXILIARY);
			if (negationObject instanceof WordElement) {
				negation = (WordElement) negationObject;
			} else if (negationObject != null) {
				String negationString;
				if (negationObject instanceof StringElement) {
					negationString = ((StringElement)negationObject).getRealisation();
				} else {
					negationString = negationObject.toString();
				}
				negation = lexicon.lookupWord(negationString);
			}
			
			if (negation == null) {
				negation = lexicon.lookupWord("pas", LexicalCategory.ADVERB);
			}
			// push negation auxiliary if it's not forbidden by arguments that provoke
			// "ne" only negation or if the auxiliary is "plus"
			WordElement plus = lexicon.lookupWord("plus", LexicalCategory.ADVERB);
			if (!pasForbiddenByArgument || plus.equals(negation)) {
				InflectedWordElement inflNegation = new InflectedWordElement( negation ); //$NON-NLS-1$
				vgComponents.push(inflNegation);
			}
		}
	}

	/**
	 * Adds <em>ne</em> to the stack if the phrase is negated or if
	 * it has a suject or complement that provokes "ne" only negation.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 */
	protected void createNe(PhraseElement phrase, Stack<NLGElement> vgComponents) {
		
		boolean neRequiredByArgument = phrase.checkIfNeOnlyNegation();

		if (phrase.getFeatureAsBoolean(Feature.NEGATED) || neRequiredByArgument) {
			InflectedWordElement ne = new InflectedWordElement( (WordElement)
				phrase.getFactory().createWord("ne", LexicalCategory.ADVERB) ); //$NON-NLS-1$
	
			 vgComponents.push(ne);
		}
	}
	
	/**
	 * Determines the number agreement for the phrase.
	 * 
	 * @param parent
	 *            the parent element of the phrase.
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @return the <code>NumberAgreement</code> to be used for the phrase.
	 */
	@Override
	protected NumberAgreement determineNumber(NLGElement parent,
			PhraseElement phrase) {
		Object numberValue = phrase.getFeature(Feature.NUMBER);
		NumberAgreement number = null;
		
		if (numberValue instanceof NumberAgreement) {
			number = (NumberAgreement) numberValue;
		} else {
			number = NumberAgreement.SINGULAR;
		}
		
		return number;
	}

	/**
	 * Add a modifier to a verb phrase. Use heuristics to decide where it goes.
	 * Based on method of the same name in English verb phrase helper.
	 * Reference : section 935 of Grevisse (1993)
	 * 
	 * @param verbPhrase
	 * @param modifier
	 * 
	 * @author vaudrypl
	 */
	@Override
	public void addModifier(VPPhraseSpec verbPhrase, Object modifier) {
		// Everything is postModifier

		if (modifier != null) {
		
			// get modifier as NLGElement if possible
			NLGElement modifierElement = null;
			if (modifier instanceof NLGElement)
				modifierElement = (NLGElement) modifier;
			else if (modifier instanceof String) {
				String modifierString = (String) modifier;
				if (modifierString.length() > 0 && !modifierString.contains(" "))
					modifierElement = verbPhrase.getFactory().createWord(modifier,
							LexicalCategory.ADVERB);
			}
		
			// if no modifier element, must be a complex string
			if (modifierElement == null) {
				verbPhrase.addPostModifier((String) modifier);
			} else {
				// default case
				verbPhrase.addPostModifier(modifierElement);
			}
		}
	}
		
	/**
	 * Realises the complements of this phrase.
	 * Based on English method of the same name.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	@Override
	protected void realiseComplements(PhraseElement phrase,
			ListElement realisedElement) {

		ListElement indirects = new ListElement();
		ListElement directs = new ListElement();
		ListElement unknowns = new ListElement();
		Object discourseValue = null;
		NLGElement currentElement = null;

		for (NLGElement complement : phrase
				.getFeatureAsElementList(InternalFeature.COMPLEMENTS)) {
			if (!complement.getFeatureAsBoolean(PortugueseInternalFeature.CLITIC)) {
				
				discourseValue = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);

				if (!(discourseValue instanceof DiscourseFunction)) {
					discourseValue = DiscourseFunction.COMPLEMENT;
				}

				// Realise complement only if it is not the relative phrase of
				// the parent clause and not a phrase with the same function in case
				// of a direct or indirect object.
				NLGElement parent = phrase.getParent();
				if ( parent == null ||
						(!complement.getFeatureAsBoolean(PortugueseInternalFeature.RELATIVISED) &&
							complement != parent.getFeatureAsElement(PortugueseFeature.RELATIVE_PHRASE) &&
							(discourseValue == DiscourseFunction.COMPLEMENT ||
								!parent.hasRelativePhrase((DiscourseFunction) discourseValue)))) {
					
					if (DiscourseFunction.INDIRECT_OBJECT.equals(discourseValue)) {
						complement = checkIndirectObject(complement);
					}
					
					currentElement = complement.realiseSyntax();
	
					if (currentElement != null) {
						currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
								discourseValue);
	
						if (DiscourseFunction.INDIRECT_OBJECT.equals(discourseValue)) {
							indirects.addComponent(currentElement);
						} else if (DiscourseFunction.OBJECT.equals(discourseValue)) {
							directs.addComponent(currentElement);
						} else {
							unknowns.addComponent(currentElement);
						}
					}
				} else {
				// Reset relativised feature if the complement was a relative phrase.
					complement.removeFeature(PortugueseInternalFeature.RELATIVISED);
				}
			}
			// Reset the clitic selection feature after use.
			complement.removeFeature(PortugueseInternalFeature.CLITIC);
		}
		
		
		// Reference : section 657 of Grevisse (1993)
		// normal order, when complements are all of the same length :
		// direct objects + indirect objects + other complements
		// when objects are longer than others, they are placed after them
		int numberOfWordDirects = NLGElement.countWords(directs.getChildren());
		int numberOfWordIndirects = NLGElement.countWords(indirects.getChildren());
		int numberOfWordUnknowns = NLGElement.countWords(unknowns.getChildren());
		// there are 3*2*1 = 6 orders possible
		if (numberOfWordDirects <= numberOfWordIndirects) {
			if (numberOfWordIndirects <= numberOfWordUnknowns) {
				// normal order
				addDirectObjects(directs, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
			} else if (numberOfWordDirects <= numberOfWordUnknowns) {
				addDirectObjects(directs, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
			} else {
				addUnknownComplements(unknowns, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
			}
		} else {
			if (numberOfWordDirects <= numberOfWordUnknowns) {
				addIndirectObjects(indirects, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
			} else if (numberOfWordIndirects <= numberOfWordUnknowns) {
				addIndirectObjects(indirects, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
			} else {
				addUnknownComplements(unknowns, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
			}
		}
	}

	/**
	 * Adds realised direct objects to the complements realisation
	 * @param directs			realised direct objects
	 * @param phrase			the verb phrase to wich belongs those complements
	 * @param realisedElement	complements realisation
	 */
	protected void addDirectObjects(ListElement directs, PhraseElement phrase,
			ListElement realisedElement) {
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		if (!passive && !InterrogativeType.isObject(phrase
					.getFeature(Feature.INTERROGATIVE_TYPE))) {
			realisedElement.addComponents(directs.getChildren());
		}
	}

	/**
	 * Adds realised indirect objects to the complements realisation
	 * @param indirects			realised indirect objects
	 * @param phrase			the verb phrase to wich belongs those complements
	 * @param realisedElement	complements realisation
	 */
	protected void addIndirectObjects(ListElement indirects, PhraseElement phrase,
			ListElement realisedElement) {
		if (!InterrogativeType.isIndirectObject(phrase
				.getFeature(Feature.INTERROGATIVE_TYPE))) {
			realisedElement.addComponents(indirects.getChildren());
		}
	}

	/**
	 * Adds unknown complements to the complements realisation
	 * @param unknowns			unknown complements
	 * @param phrase			the verb phrase to wich belongs those complements
	 * @param realisedElement	complements realisation
	 */
	protected void addUnknownComplements(ListElement unknowns, PhraseElement phrase,
			ListElement realisedElement) {
		if (!phrase.getFeatureAsBoolean(Feature.PASSIVE)) {
			realisedElement.addComponents(unknowns.getChildren());
		}
	}

	/**
	 * Adds a default preposition to all indirect object noun phrases.
	 * Checks also inside coordinated phrases.
	 * 
	 * @param nounPhrase
	 * @return the new complement
	 * 
	 * @vaudrypl
	 */
	@SuppressWarnings("unchecked")
	protected NLGElement checkIndirectObject(NLGElement element) {
		if (element instanceof NPPhraseSpec) {
			NLGFactory factory = element.getFactory();
			NPPhraseSpec elementCopy = new NPPhraseSpec((NPPhraseSpec) element);
			PPPhraseSpec newElement = factory.createPrepositionPhrase("à", elementCopy);
			element.setFeature(InternalFeature.DISCOURSE_FUNCTION, DiscourseFunction.INDIRECT_OBJECT);
			element = newElement;
		} else if (element instanceof CoordinatedPhraseElement) {
			element = new CoordinatedPhraseElement( (CoordinatedPhraseElement) element );
			Object coordinates = element.getFeature(InternalFeature.COORDINATES);
			if (coordinates instanceof List) {
				List<NLGElement> list = (List<NLGElement>) coordinates;
				for (int index = 0; index < list.size(); ++index) {
					list.set(index, checkIndirectObject(list.get(index)));
				}
			}
		}
		
		return element;
	}

}
