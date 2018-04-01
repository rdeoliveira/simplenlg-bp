package verb.form;

/**
 * Created by thiagocastroferreira on 27/03/18.
 */

import junit.framework.TestCase;
import org.junit.Before;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.portuguese.XMLLexicon;
import simplenlg.realiser.Realiser;

/**
 * This class is the base class for all JUnit simplenlg.test cases for
 * SimpleNLG.
 * @author R. de Oliveira, University of Aberdeen.
 */
public abstract class Setup extends TestCase {

    Realiser realiser;
    NLGFactory phraseFactory;
    Lexicon lexicon;

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
     * Set up the variables we'll need for this simplenlg.test to run (Called
     * automatically by JUnit)
     */
    @Override
    @Before
    protected void setUp() {
        lexicon = new XMLLexicon();
        this.phraseFactory = new NLGFactory(this.lexicon);
        this.realiser = new Realiser();
    }
}
