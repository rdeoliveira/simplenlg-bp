package singleTests;

import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

import simplenlg.lexicon.english.XMLLexicon;

public class SingleExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Lexicon lexicon = Lexicon.getDefaultLexicon();
		Lexicon lexicon = new XMLLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser();
//        realiser.setDebugMode(true);
        SPhraseSpec ex = nlgFactory.createClause();
        
        NPPhraseSpec locatum = nlgFactory.createNounPhrase("edifício");
//        System.out.println(ex+"\n");
        locatum.setSpecifier("o");
//        System.out.println(ex+"\n");
        ex.setSubject(locatum);
        ex.setVerb("sucker-punches");
        PPPhraseSpec generalizedLocation = nlgFactory.createPrepositionPhrase();
        NPPhraseSpec relatum = nlgFactory.createNounPhrase("praça");
        relatum.setSpecifier("a");
        generalizedLocation.addComplement(relatum);
        generalizedLocation.setPreposition("em");
//        System.out.println(ex+"\n");
        ex.addComplement(generalizedLocation);
//        System.out.println(ex+"\n");
//        System.out.println(locatum+"\n");
//        System.out.println(generalizedLocation+"\n");
        
        String target = "O edifício fica na praça.";
        String gloss = "The building lies at the square.";
        String output = realiser.realiseSentence(ex);
        if(!output.equals(target)){
        	System.out.println("* "+output+" TARGET: "+target);
    		} else {
			System.out.println(output + " (" + gloss + ")");
		}
    }
}