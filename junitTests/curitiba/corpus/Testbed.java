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

package curitiba.corpus;

import junit.framework.Assert;

import org.junit.Test;

import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

/**
 * This class generates instances of the test-bed explored in the MA thesis
 * on spatial language by Rodrigo de Oliveira on 24 January 2013. It
 * uses a trilingual (En, Fr and Pt) adaptation of SimpleNLG 4.0.
 * The testbed is available on: https://docs.google.com/spreadsheet/ccc?key=0AjjU8ITs-OqudDE1MkZoS19IQWJ2Tks0NE5ONFhrZEE&usp=sharing 
 */
public class Testbed extends Setup {

	public Testbed(String name) {
		super(name);
	}
	
	// they brought the recipes
	@Test
	public void testEx01() {
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
	
	// who likes it should visit the chapel
	@Test
	public void testEx04() {
		// TODO it should be possible to pack the subject "quem gosta" within 
		// a VP instead of within an NP, as it is. Something in the French
		// grammar messes with the syntax if one does this.
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
	
	// The dwellers need to cross the city
	@Test
	public void testEx05() {
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
	
	// lies five blocks [away] from the tower
	@Test
	public void testEx06() {
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("de");
		pp.addPreModifier("a cinco quadras");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("torre");
		np.setSpecifier("a");
		pp.addComplement(np);

		SPhraseSpec s = this.phraseFactory.createClause(null,"ficar",pp);
		
		Assert.assertEquals(
				"fica a cinco quadras da torre", this.realiser
						.realise(s).getRealisation());
	}
	
	// turning right
	@Test
	public void testEx07() {
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("a");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("direita");
		np.setSpecifier("a");
		pp.addComplement(np);
		
		SPhraseSpec s = this.phraseFactory.createClause(null,"virar",pp);
		s.setFeature(Feature.TENSE, Tense.IMPERATIVE);		
		
		Assert.assertEquals(
				"vire à direita", this.realiser
						.realise(s).getRealisation());
	}
	
	// several plates on the table
	@Test
	public void testEx08() {
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("prato");
		np.setSpecifier("vários");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("a");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("mesa");
		np2.setSpecifier("a");
		pp.addComplement(np2);
		np.addComplement(pp);
		
		Assert.assertEquals(
				"vários pratos à mesa", this.realiser
						.realise(np).getRealisation());
	}
	
	// who likes [it] can cross the bridge
	@Test
	public void testEx09() {
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("quem gosta");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("ponte");
		np2.setSpecifier("a");

		SPhraseSpec s = this.phraseFactory.createClause(np,"cruzar", np2);
		s.setFeature(Feature.MODAL, "poder");

		Assert.assertEquals(
				"quem gosta pode cruzar a ponte", this.realiser
						.realise(s).getRealisation());
	}
	
	// the proximity with nature
	@Test
	public void testEx10() {
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("proximidade");
		np.setSpecifier("a");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("com");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("natureza");
		np2.setSpecifier("a");
		pp.addComplement(np2);
		np.addComplement(pp);
		
		Assert.assertEquals(
				"a proximidade com a natureza", this.realiser
						.realise(np).getRealisation());
	}
	
	// go down the hill
	@Test
	public void testEx11() {
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("descer");
		vp.setFeature(Feature.FORM, Form.BARE_INFINITIVE);
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("serra");
		np.setSpecifier("a");
		vp.addComplement(np);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("depois de");
		pp.addComplement(vp);

		Assert.assertEquals(
				"depois de descer a serra", this.realiser
						.realise(pp).getRealisation());
	}
	
	// the children to follow the track
	@Test
	public void testEx12() {
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("as crianças");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		SPhraseSpec s = this.phraseFactory.createClause(np,"seguir","a trilha");
		s.setFeature(Feature.TENSE, Tense.PERSONAL_INFINITIVE);
		
		Assert.assertEquals(
				"as crianças seguirem a trilha", this.realiser
						.realise(s).getRealisation());
	}

}
