package net.ddp.chief;

import java.io.InputStream;

import net.ddp.chief.know.lang.ParseException;
import net.ddp.chief.know.lang.java.visitor.JavaSyntaxVisitor;
import net.ddp.chief.know.ont.OntModelFactory;
import net.ddp.chief.know.ont.OntologyHelper;
import net.ddp.chief.know.ont.scro.SCROClasses;
import net.ddp.chief.know.ont.source.SourceProperties;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

/**
 * Hello world!
 *
 */
public class App extends Ugga implements Bugga, Tugga, Wugga
{
    public static void main( String[] args ) throws ParseException
    {
       	Model model = ModelFactory.createDefaultModel();
		OntModelFactory factory = new OntModelFactory("http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#");
		
		OntModel theSCROModel = factory.getOntologyModel();
		OntologyHelper helper = new OntologyHelper(theSCROModel);

       	Individual root = helper.makeIndividual(SCROClasses.COMPILATION_UNIT, "SyntaxVisitor.java");
       	Property prop = model.getProperty(SourceProperties.FILENAME.getURI());
       	model.add(model.createStatement(root, prop, model.createLiteral("SyntaxVisitor.java")));
       	InputStream sampleFile = App.class.getClassLoader().getResourceAsStream("SyntaxVisitor.java");
       	
       	JavaSyntaxVisitor visitor = new JavaSyntaxVisitor();
       	
       	visitor.process(model, root, sampleFile);
       	model.write(System.out);
    }
}
