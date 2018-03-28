package thiago;

import inlg2014.*;
import junit.framework.Assert;
import org.junit.Test;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;
import simplenlg.phrasespec.*;

/**
 * Created by thiagocastroferreira on 27/03/18.
 */
public class Testbed extends Setup {

    public Testbed(String name) {
        super(name);
    }

    private String getResult(NLGElement example, String number) {
        //		this.realiser.setDebugMode(true);
        String realisation = this.realiser.realiseSentence(example).replace(" ,", ",");
        System.out.println(number + ". " + realisation);
        return realisation;
    }

    /**
     * This example shows:
     * past participle: registrar + form = registrados
     * Sentence: O Índice de Preços ao Consumidor (IPCA) encerrou o ano de 2017 com 2,94% de variação,
     * 3.34% menor dos 6.28% registrados em 2016
     */
    @Test
    public void testEx01() {
        NPPhraseSpec subject = this.phraseFactory.createNounPhrase("Índice de Preços ao Consumidor (IPCA)");
        subject.setSpecifier("o");
//        subject.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        VPPhraseSpec verb = this.phraseFactory.createVerbPhrase("encerrar");

        NPPhraseSpec object = this.phraseFactory.createNounPhrase("o", "ano");
        PPPhraseSpec ppMod = this.phraseFactory.createPrepositionPhrase("de", "2017");
        object.addPostModifier(ppMod);

        PPPhraseSpec indirectObject = this.phraseFactory.createPrepositionPhrase("com", "2.94% de variação");

        NLGElement comma = this.phraseFactory.createNLGElement(",");

        AdjPhraseSpec complement = this.phraseFactory.createAdjectivePhrase("menor");
        NPPhraseSpec premod = this.phraseFactory.createNounPhrase("3.34%");
        complement.addPreModifier(premod);

        PPPhraseSpec postmod = this.phraseFactory.createPrepositionPhrase("de");
        NPPhraseSpec head = this.phraseFactory.createNounPhrase("os", "6.28%");
        VPPhraseSpec vpmod = this.phraseFactory.createVerbPhrase("registrar");
        vpmod.addComplement(this.phraseFactory.createPrepositionPhrase("em", "2016"));
        vpmod.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
        vpmod.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        head.addPostModifier(vpmod);
        postmod.setComplement(head);

        complement.addComplement(postmod);

        SPhraseSpec s = this.phraseFactory.createClause();
        s.setSubject(subject);
        s.setVerb(verb);
        s.setObject(object);
        s.setIndirectObject(indirectObject);
        s.addComplement(comma);
        s.addComplement(complement);

        s.setFeature(Feature.TENSE, Tense.PAST);

        String real = "O Índice de Preços ao Consumidor (IPCA) encerrou o ano de 2017 com 2.94% de variação, 3.34% menor dos 6.28% registrados em 2016.";
        Assert.assertEquals(real, getResult(s, "01"));
    }
}
