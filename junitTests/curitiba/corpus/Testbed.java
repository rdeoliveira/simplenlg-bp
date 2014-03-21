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
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.phrasespec.AdvPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

/**
 * This class generates instances of the test-bed explored in the MA thesis
 * on spatial language by Rodrigo de Oliveira on 24 January 2013. It
 * uses a trilingual (En, Fr and Pt) adaptation of SimpleNLG 4.2.
 * The testbed is available on: https://docs.google.com/spreadsheet/ccc?key=0AjjU8ITs-OqudDE1MkZoS19IQWJ2Tks0NE5ONFhrZEE&usp=sharing 
 */
public class Testbed extends Setup {

	public Testbed(String name) {
		super(name);
	}
	
	// 01. they brought the recipes
	@Test
	public void testEx01() {
//		System.out.print("01. ");
		NPPhraseSpec subject = this.phraseFactory.createNounPhrase("eles");
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
	
	// 04. who likes it should visit the chapel
	@Test
	public void testEx04() {
//		System.out.print("04. ");
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
	
	// 05. The dwellers need to cross the city
	@Test
	public void testEx05() {
//		System.out.print("05. ");
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
	
	// 06. [it] lies five blocks [away] from the tower
	@Test
	public void testEx06() {
//		System.out.print("06. ");
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
	
	// 07. turning right
	@Test
	public void testEx07() {
//		System.out.print("07. ");
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
	
	// 08. several plates on the table
	@Test
	public void testEx08() {
//		System.out.print("08. ");
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
	
	// 09. who likes [it] can cross the bridge
	@Test
	public void testEx09() {
//		System.out.print("09. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("quem gosta");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("ponte");
		np2.setSpecifier("a");

		SPhraseSpec s = this.phraseFactory.createClause(np,"cruzar", np2);
		s.setFeature(Feature.MODAL, "poder");

		Assert.assertEquals(
				"quem gosta pode cruzar a ponte", this.realiser
						.realise(s).getRealisation());
	}
	
	// 10. the proximity with nature
	@Test
	public void testEx10() {
//		System.out.print("10. ");
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
	
	// 11. go down the hill
	@Test
	public void testEx11() {
//		System.out.print("11. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("descer");
		vp.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("serra");
		np.setSpecifier("a");
		vp.addComplement(np);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("depois de");
		pp.addComplement(vp);

		Assert.assertEquals(
				"depois de descer a serra", this.realiser
						.realise(pp).getRealisation());
	}
	
	// 12. the children to follow the track
	@Test
	public void testEx12() {
//		System.out.print("12. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("as crianças");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		SPhraseSpec s = this.phraseFactory.createClause(np,"seguir","a trilha");
		s.setFeature(Feature.TENSE, Tense.PERSONAL_INFINITIVE);
		
		Assert.assertEquals(
				"as crianças seguirem a trilha", this.realiser
						.realise(s).getRealisation());
	}
	
	// 13. the pedestrian zone begins there
	@Test
	public void testEx13() {
//		System.out.print("13. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("calçadão");
		np.setSpecifier("o");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("começar");
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("ali");
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,ap);
		
		Assert.assertEquals(
				"o calçadão começa ali", this.realiser
						.realise(s).getRealisation());
	}
	
	// 14. the theatre lies there
	@Test
	public void testEx14() {
//		System.out.print("14. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("teatro");
		np.setSpecifier("o");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ficar");
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("ali");
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,ap);
		
		Assert.assertEquals(
				"o teatro fica ali", this.realiser
						.realise(s).getRealisation());
	}
	
	// 15. take passengers to the airport
	@Test
	public void testEx15() {
//		System.out.print("15. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("levar");
		vp.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("passageiros");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("aeroporto");
		np2.setSpecifier("o");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("a");
		pp.addComplement(np2);
		SPhraseSpec s = this.phraseFactory.createClause(vp,np,pp);
		
		Assert.assertEquals(
				"levar passageiros ao aeroporto", this.realiser
						.realise(s).getRealisation());
	}
	
	// 16. see on the side
	@Test
	public void testEx16() {
//		System.out.print("16. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ver");
		vp.setFeature(Feature.TENSE, Tense.IMPERATIVE);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("ao lado");
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp);
		
		Assert.assertEquals(
				"veja ao lado", this.realiser
						.realise(s).getRealisation());
	}
	
	// 17. which were installed along the pedestrian zone
	@Test
	public void testEx17() {
//		System.out.print("17. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("que");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("instalar");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		vp.setFeature(Feature.TENSE, Tense.PAST);
		vp.setFeature(Feature.PASSIVE, true);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("ao longo de");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("calçadão");
		np2.setSpecifier("o");
		pp.addComplement(np2);
		vp.addPostModifier(pp);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp);
		
		Assert.assertEquals(
				"que foram instalados ao longo do calçadão", this.realiser
						.realise(s).getRealisation());
	}
	
	// 17b. which had been installed along the pedestrian zone
	@Test
	public void testEx17b() {
//		System.out.print("17b. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("que");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("instalar");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		vp.setFeature(Feature.TENSE, Tense.IMPERFECT);
		vp.setFeature(Feature.PERFECT, true);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("ao longo de");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("calçadão");
		np2.setSpecifier("o");
		pp.addComplement(np2);
		vp.addPostModifier(pp);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp);
		
		Assert.assertEquals(
				"que tinham instalado ao longo do calçadão", this.realiser
						.realise(s).getRealisation());
	}
	
	// 17c. which were installing along the pedestrian zone
	@Test
	public void testEx17c() {
//		System.out.print("17c. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("que");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("instalar");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		vp.setFeature(Feature.TENSE, Tense.PAST);
		vp.setFeature(Feature.PROGRESSIVE, true);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("ao longo de");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("calçadão");
		np2.setSpecifier("o");
		pp.addComplement(np2);
		vp.addPostModifier(pp);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp);
		
		Assert.assertEquals(
				"que estiveram instalando ao longo do calçadão", this.realiser
						.realise(s).getRealisation());
	}
	
	// 18. the streets around the square
	@Test
	public void testEx18() {
//		System.out.print("18. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("rua");
		np.setSpecifier("as");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("ao redor de");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("praça");
		np2.setSpecifier("a");
		pp.addComplement(np2);
		np.addComplement(pp);
		
		Assert.assertEquals(
				"as ruas ao redor da praça", this.realiser
						.realise(np).getRealisation());
	}
	
	// 19. it will be here
	@Test
	public void testEx19() {
//		System.out.print("19. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("ele");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("estar");
		vp.setFeature(Feature.TENSE, Tense.FUTURE);
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("aqui");
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,ap);
		
		Assert.assertEquals(
				"ele estará aqui", this.realiser
						.realise(s).getRealisation());
	}
	
	// 20. the streets around the square
	@Test
	public void testEx20() {
//		System.out.print("20. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("lugar");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("aqui perto");
		np.addComplement(ap);
		
		Assert.assertEquals(
				"lugares aqui perto", this.realiser
						.realise(np).getRealisation());
	}
		
	// 21. which goes until Boca
	@Test
	public void testEx21() {
//		System.out.print("21. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("que");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ir");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("até");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("Boca");
		pp.addComplement(np2);
		vp.addPostModifier(pp);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp);
		
		Assert.assertEquals(
				"que vai até Boca", this.realiser
						.realise(s).getRealisation());
	}
	
	// 22. you come.SUBJUNCTIVE (until) here
	@Test
	public void testEx22() {
//		System.out.print("22. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("você");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("vir");
		vp.setFeature(Feature.TENSE, Tense.SUBJUNCTIVE_FUTURE);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("até");
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("aqui");
		pp.addComplement(ap);
		vp.addComplement(pp);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp);
		
		Assert.assertEquals(
				"você vier até aqui", this.realiser
						.realise(s).getRealisation());
	}
	
	// 23. there is a construction behind the society
	@Test
	public void testEx23() {
//		System.out.print("23. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("haver");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("uma","construção");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("atrás de");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("a","sociedade");
		pp.addComplement(np2);
		SPhraseSpec s = this.phraseFactory.createClause(vp,np,pp);
		
		Assert.assertEquals(
				"há uma construção atrás da sociedade", this.realiser
						.realise(s).getRealisation());
	}
	
	// 24. the churches share space with the house
	@Test
	public void testEx24() {
//		System.out.print("24. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("as","igreja");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("dividir");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("espaço");
		vp.addComplement(np2);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("com");
		NPPhraseSpec np3 = this.phraseFactory.createNounPhrase("a","casa");
		pp.addComplement(np3);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp);
		
		Assert.assertEquals(
				"as igrejas dividem espaço com a casa", this.realiser
						.realise(s).getRealisation());
	}
	
//	25. Target: “passear da Boca”
//	Gloss: “promenade from Boca”
	@Test
	public void testEx25() {
//		System.out.print("25. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("passear");
		vp.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("de");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("a","boca");
		pp.addComplement(np);
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp);
		
		Assert.assertEquals(
				"passear da boca", this.realiser
						.realise(s).getRealisation());
	}
	
//	26. Target: “chegar dali”
//	Gloss: “arrive from there”
	public void testEx26() {
//		System.out.print("26. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("chegar");
		vp.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("de");
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("ali");
		pp.addComplement(ap);
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp);
		
		Assert.assertEquals(
				"chegar dali", this.realiser
						.realise(s).getRealisation());
	}
	
//	27. Target: “dá para ir de bar em bar”
//	Gloss: “[one] can go from bar to bar”
	public void testEx27() {
//		System.out.print("27. ");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("dar");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("para");
		VPPhraseSpec vp2 = this.phraseFactory.createVerbPhrase("ir");
		vp2.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		PPPhraseSpec pp2 = this.phraseFactory.createPrepositionPhrase("de");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("bar");
		pp2.addComplement(np);
		PPPhraseSpec pp3 = this.phraseFactory.createPrepositionPhrase("em");
		pp3.addComplement(np);
		vp2.addComplement(pp2);
		vp2.addComplement(pp3);
		pp.addComplement(vp2);
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp);
		
		Assert.assertEquals(
				"dá para ir de bar em bar", this.realiser
						.realise(s).getRealisation());
	}
	
//	28. Target: “o andar de cima”
//	Gloss: “the top floor”
	public void testEx28() {
//		System.out.print("28. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("o","andar");
		// traditional grammars label 'de cima' as an 'adverbial complex',
		// even though in this case it modifies a noun. 
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("de cima");
		np.addComplement(ap);
		
		Assert.assertEquals(
				"o andar de cima", this.realiser
						.realise(np).getRealisation());
	}
	
//	29. Target: “dá para observar a geografia de um dos pontos”
//	Gloss: “[one] can observe the landscape from one of the points”
	public void testEx29() {
//		System.out.print("29. ");
		
		NPPhraseSpec np3 = this.phraseFactory.createNounPhrase("os","pontos"); //os pontos
		PPPhraseSpec pp2 = this.phraseFactory.createPrepositionPhrase("de"); //de		
		pp2.addComplement(np3); //d + os pontos
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("um"); //um
		np2.addPostModifier(pp2); //um + dos pontos
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("de"); //de
		ap.addComplement(np2); //de + um dos pontos
		VPPhraseSpec vp2 = this.phraseFactory.createVerbPhrase("observar"); //observar
		vp2.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("a","geografia"); //a + geografia
		vp2.setObject(np); //observar + a geografia
		vp2.addComplement(ap); //observar a geografia + de um dos pontos
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("para"); //para
		pp.addComplement(vp2); //para + observar a geografia de um dos pontos
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("dar"); //da
					
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp); // da + para observar a geografia de um dos pontos 
		
		Assert.assertEquals(
				"dá para observar a geografia de um dos pontos", this.realiser
						.realise(s).getRealisation());
	}
	
//	30. Target: “a beira do lago”
//	Gloss: “the shore of the river”
	public void testEx30() {
//		System.out.print("30. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("o","lago");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("de");
		pp.addComplement(np);
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("a","beira");
		np2.addComplement(pp);
		
		Assert.assertEquals(
				"a beira do lago", this.realiser
						.realise(np2).getRealisation());
	}
	
//	31. Target: “a entrada fica do outro lado da praça”
//	Gloss: “the entrance lies on the other side of the square”
	public void testEx31() {
//		System.out.print("31. ");		
		NPPhraseSpec np3 = this.phraseFactory.createNounPhrase("a","praça"); //a praça
		PPPhraseSpec pp2 = this.phraseFactory.createPrepositionPhrase("de"); //de
		pp2.addComplement(np3); //d + a praça
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("de"); //de
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("o","outro lado"); //o + outro lado
		pp.addComplement(np2); //d + o outro lado
		pp.addPostModifier(pp2); //do outro lado + da praça
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ficar"); //fica
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("a","entrada"); //a + entrada
					
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp); // a entrada + fica + do outro lado da praça  
		
		Assert.assertEquals(
				"a entrada fica do outro lado da praça", this.realiser
						.realise(s).getRealisation());
	}
	
//	32. Target: “dá para chegar bem perto das quedas em botes”
//	Gloss: “[one] can come very close to the falls in boats”
	public void testEx32() {
//		System.out.print("32. ");		
		
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("chegar"); //chegar
		vp.setFeature(Feature.MODAL, "dar"); //da para chegar
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("perto de"); //perto de
		pp.addPreModifier("bem"); //bem perto de
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("as","quedas"); //as quedas
		pp.addComplement(np); //bem perto das quedas
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("em"); //em
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("botes"); //botes
		ap.addComplement(np2); //em botes
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp,ap);  
		
		Assert.assertEquals(
				"dá para chegar bem perto das quedas em botes", this.realiser
						.realise(s).getRealisation());
	}
	
//	33. Target: “o edifício fica em frente”
//	Gloss: “the building lies in front”
	public void testEx33() {
//		System.out.print("33. ");		
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("o","edifício");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ficar");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("em frente");
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp);  
		
		Assert.assertEquals(
				"o edifício fica em frente", this.realiser
						.realise(s).getRealisation());
	}
	
//	34. Target: “ela entra em taxis”
//	Gloss: “she enters [in] taxis”
	public void testEx34() {
//		System.out.print("34. ");		
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("ela");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("entrar");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("em");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("taxis");
		pp.addComplement(np2);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp);  
		
		Assert.assertEquals(
				"ela entra em taxis", this.realiser
						.realise(s).getRealisation());
	}
	
//	35. Target: “você vai passar pelo bairro entre as paradas 3 e 4”
//	Gloss: “you will pass by/through the neighborhood between the stops 3 and 4”
	public void testEx35() {
//		System.out.print("35. ");		
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("você");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("passar");
		vp.setFeature(Feature.PROSPECTIVE, true);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("por");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("o","bairro");
		PPPhraseSpec pp2 = this.phraseFactory.createPrepositionPhrase("entre");
		NPPhraseSpec np3 = this.phraseFactory.createNounPhrase("as","parada");
		np3.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		np3.addPostModifier("3 e 4");
		pp2.addComplement(np3);
		np2.addComplement(pp2);
		pp.addComplement(np2);
		vp.addComplement(pp);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp);  
		
		Assert.assertEquals(
				"você vai passar pelo bairro entre as paradas 3 e 4", this.realiser
						.realise(s).getRealisation());
	}
	
//	36. Target: “fica entre Perneta e Franca”
//	Gloss: “lies between Perneta and France”
	public void testEx36() {
//		System.out.print("36. ");		
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ficar");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("entre");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("Perneta");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("Franca");
		pp.addComplement(np);
		pp.addComplement(np2);
		SPhraseSpec s = this.phraseFactory.createClause(vp,pp);  
		
		Assert.assertEquals(
				"fica entre Perneta e Franca", this.realiser
						.realise(s).getRealisation());
	}
	
//	37. Target: “um microônibus circula entre pontos e o Aeroporto”
//	Gloss: “a minibus circulates between spots and the Airport”
	public void testEx37() {
//		System.out.print("37. ");		
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("um","microônibus");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("circular");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("entre");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("pontos");
		NPPhraseSpec np3 = this.phraseFactory.createNounPhrase("o","aeroporto");
		pp.addComplement(np2);
		pp.addComplement(np3);
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp);  
		
		Assert.assertEquals(
				"um microônibus circula entre pontos e o aeroporto", this.realiser
						.realise(s).getRealisation());
	}
	
//	38. Target: “Banca esquina com Augusto”
//	Gloss: “Newsstand corner with Augusto [street]”
	public void testEx38() {
//		System.out.print("38. ");		
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("banca");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("esquina com");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("Augusto");
		pp.addComplement(np2);
		SPhraseSpec s = this.phraseFactory.createClause(np,pp);  
		
		Assert.assertEquals(
				"banca esquina com Augusto", this.realiser
						.realise(s).getRealisation());
	}
	
//	39. Target: “para jogar fora”
//	Gloss: “to throw away”
	public void testEx39() {
//		System.out.print("39. ");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("para");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("jogar");
		vp.setFeature(Feature.TENSE, Tense.IMPERSONAL_INFINITIVE);
		PPPhraseSpec pp2 = this.phraseFactory.createPrepositionPhrase("fora");
		vp.addComplement(pp2);
		pp.addComplement(vp);  
		
		Assert.assertEquals(
				"para jogar fora", this.realiser
						.realise(pp).getRealisation());
	}
	
//	40. Target: “que caiba na bolsa”
//	Gloss: “which fits in the bag”
	public void testEx40() {
//		System.out.print("40. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("que");
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("caber");
		vp.setFeature(Feature.TENSE, Tense.SUBJUNCTIVE_PRESENT);
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("em");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("a","bolsa");
		pp.addComplement(np2); 
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp);
		
		Assert.assertEquals(
				"que caiba na bolsa", this.realiser
						.realise(s).getRealisation());
	}
	
//	48. Target: “as famílias vão para a beira”
//	Gloss: “the families go to the shore”
	public void testEx48() {
//		System.out.print("48. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("as","família");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("ir");
		PPPhraseSpec pp = this.phraseFactory.createPrepositionPhrase("para");
		NPPhraseSpec np2 = this.phraseFactory.createNounPhrase("a","beira");
		pp.addComplement(np2); 
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,pp);
		
		Assert.assertEquals(
				"as famílias vão para a beira", this.realiser
						.realise(s).getRealisation());
	}
	
//	58. Target: “os locais estão um pouco espalhados”
//	Gloss: “the places are a little scattered”
	public void testEx58() {
//		System.out.print("58. ");
		NPPhraseSpec np = this.phraseFactory.createNounPhrase("o","local");
		np.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		VPPhraseSpec vp = this.phraseFactory.createVerbPhrase("estar");
		AdvPhraseSpec ap = this.phraseFactory.createAdverbPhrase("espalhado");
		ap.addPreModifier("um pouco");
		SPhraseSpec s = this.phraseFactory.createClause(np,vp,ap);
		
		//TODO this example generates an ill-formed clause but, in the way
		// the grammar currently is, neither (a) determiner-noun agreement
		// happens, nor (b) subject-predicate agreement of copula clauses.
		// commented out is the realisation one wishes to get.
		Assert.assertEquals(
//				"os locais estão um pouco espalhados",
				"o locais estão um pouco espalhado",
				this.realiser.realise(s).getRealisation());
	}
	
}
