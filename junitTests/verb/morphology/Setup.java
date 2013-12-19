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

import junit.framework.TestCase;

import org.junit.Before;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.french.XMLLexicon;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.Realiser;

/**
 * This class is the base class for all JUnit test cases in this package.
 * @author R. de Oliveira, University of Aberdeen.
 */
public abstract class Setup extends TestCase {
	
//	long startTime;
//	long endTime;
//	double elapsed;
	
	/** The realiser. */
	Realiser realiser;

	NLGFactory phraseFactory;
	
	Lexicon lexicon;
	
	VPPhraseSpec cantar, vender, partir;
	
	VPPhraseSpec estar, caber, fazer;

	/**
	 * Instantiates a new SimpleNLG test.
	 * 
	 * @param name
	 *            the name
	 */
	public Setup(String name) {
		super(name);
	}

	/**
	 * Set up the variables we'll need for this SimpleNLG test to run (Called
	 * automatically by JUnit)
	 */
	@Override
	@Before
	protected void setUp() {
//		System.out.println("constructing lexicon");
//		startTime = System.nanoTime();
		lexicon = new XMLLexicon();
//		endTime = System.nanoTime();
//		elapsed = (double)(endTime-startTime)/1000000000.0;
//		System.out.println("lexicon constructed");
//		System.out.println("elapsed time: "+elapsed+" seconds\n");
//		System.out.println("constructing NLGFactory");
		this.phraseFactory = new NLGFactory(this.lexicon);
//		System.out.println("NLGFactory constructed\n");
		this.realiser = new Realiser();
		
		this.cantar = this.phraseFactory.createVerbPhrase("cantar");
		this.vender = this.phraseFactory.createVerbPhrase("vender");
		this.partir = this.phraseFactory.createVerbPhrase("partir");
		
		this.estar = this.phraseFactory.createVerbPhrase("estar");
		this.caber = this.phraseFactory.createVerbPhrase("caber");
		this.fazer = this.phraseFactory.createVerbPhrase("fazer");
	}
}
