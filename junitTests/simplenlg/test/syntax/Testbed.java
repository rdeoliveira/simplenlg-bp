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
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell.
 */

package simplenlg.test.syntax;

import junit.framework.Assert;

//import org.junit.Before;
import org.junit.Test;

//import simplenlg.features.ClauseStatus;
//import simplenlg.features.DiscourseFunction;
import simplenlg.features.Feature;
import simplenlg.features.Form;
//import simplenlg.features.Form;
//import simplenlg.features.InternalFeature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
//import simplenlg.framework.CoordinatedPhraseElement;
//import simplenlg.framework.LexicalCategory;
//import simplenlg.framework.NLGElement;
//import simplenlg.framework.PhraseCategory;
//import simplenlg.framework.PhraseElement;
//import simplenlg.phrasespec.AdjPhraseSpec;
//import simplenlg.phrasespec.AdvPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
//import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
//import simplenlg.phrasespec.VPPhraseSpec;

/**
 * The Class STest.
 */
public class Testbed extends SimpleNLG4Test {

	/**
	 * This class generates instances of the test-bed explored in the MA thesis
	 * on spatial language by Rodrigo de Oliveira on 24 January 2013. It
	 * uses a trilingual (En, Fr and Pt) adaptation of SimpleNLG 4.0.
	 * The testbed is available on: https://docs.google.com/spreadsheet/ccc?key=0AjjU8ITs-OqudDE1MkZoS19IQWJ2Tks0NE5ONFhrZEE&usp=sharing 
	 */
	public Testbed(String name) {
		super(name);
	}

	@Test
	public void testEx01() {
		// they brought the recipes
		NPPhraseSpec subject = this.phraseFactory.createNounPhrase("ele");
		subject.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		NPPhraseSpec object = this.phraseFactory.createNounPhrase("receita");
		object.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		object.setSpecifier("as");

		SPhraseSpec s = this.phraseFactory.createClause(subject,
				"trazer", object);
		s.setFeature(Feature.TENSE, Tense.PAST);

		Assert.assertEquals(
				"eles trouxeram as receitas", this.realiser
						.realise(s).getRealisation());
	}
	
	@Test
	public void testEx04() {
		// who likes it should visit the chapel
		NPPhraseSpec subject = this.phraseFactory.createNounPhrase("quem gosta");
		NPPhraseSpec object = this.phraseFactory.createNounPhrase("capela");
		object.setSpecifier("a");

		SPhraseSpec s = this.phraseFactory.createClause(subject,
				"visitar", object);
	    s.setFeature(Feature.MODAL, "dever");

		Assert.assertEquals(
				"quem gosta deve visitar a capela", this.realiser
						.realise(s).getRealisation());
	}
	
	@Test
	public void testEx05() {
		// The dwellers need to cross the city
		NPPhraseSpec subject = this.phraseFactory.createNounPhrase("morador");
		subject.setSpecifier("os");
		subject.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		NPPhraseSpec object = this.phraseFactory.createNounPhrase("cidade");
		object.setSpecifier("a");

		SPhraseSpec s = this.phraseFactory.createClause(subject,
				"atravessar", object);
		s.setFeature(Feature.MODAL, "precisar");

		Assert.assertEquals(
				"os moradores precisam atravessar a cidade", this.realiser
						.realise(s).getRealisation());
	}

}
