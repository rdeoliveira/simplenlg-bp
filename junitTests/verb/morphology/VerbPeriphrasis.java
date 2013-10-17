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

package verb.morphology;

import junit.framework.Assert;

import org.junit.Test;

import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.Tense;

/**
 * TODO
 */
public class VerbPeriphrasis extends Setup {
	
	public VerbPeriphrasis(String name) {
		super(name);
	}
	
	@Test
	public void testProgressive(){
		
		cantar.setFeature(Feature.TENSE, Tense.FUTURE);
		cantar.setFeature(Feature.PERSON, Person.SECOND);
		cantar.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		cantar.setFeature(Feature.PROGRESSIVE, true);
		Assert.assertEquals(
				"estarão cantando", realiser.realise(cantar).getRealisation());
	}
	
	@Test
	public void testPerfective(){
		
		cantar.setFeature(Feature.TENSE, Tense.IMPERFECT);
		cantar.setFeature(Feature.PERSON, Person.FIRST);
		cantar.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);
		cantar.setFeature(Feature.PERFECT, true);
		Assert.assertEquals(
				"tinha cantado", realiser.realise(cantar).getRealisation());
	}
	
	@Test
	public void testProspective(){
		
		cantar.setFeature(Feature.TENSE, Tense.PRESENT);
		cantar.setFeature(Feature.PERSON, Person.THIRD);
		cantar.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);
		cantar.setFeature(Feature.PROSPECTIVE, true);
		Assert.assertEquals(
				"vai cantar", realiser.realise(cantar).getRealisation());
	}
	
	@Test
	public void testProgressiveProspective(){
		
		cantar.setFeature(Feature.TENSE, Tense.PRESENT);
		cantar.setFeature(Feature.PERSON, Person.THIRD);
		cantar.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);
		cantar.setFeature(Feature.PROGRESSIVE, true);
		cantar.setFeature(Feature.PROSPECTIVE, true);
		Assert.assertEquals(
				"vai estar cantando", realiser.realise(cantar).getRealisation());
	}
	
	@Test
	public void testPerfectProspective(){
		
		cantar.setFeature(Feature.TENSE, Tense.CONDITIONAL);
		cantar.setFeature(Feature.PERSON, Person.FIRST);
		cantar.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
		cantar.setFeature(Feature.PERFECT, true);
		cantar.setFeature(Feature.PROSPECTIVE, true);
		Assert.assertEquals(
				"iríamos ter cantado", realiser.realise(cantar).getRealisation());
	}
	
}