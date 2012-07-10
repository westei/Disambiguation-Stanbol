/*
 * Copyright 2012, FORMCEPT [http://www.formcept.com]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.formcept.engine.enhancer;


import static org.apache.commons.lang.StringUtils.getLevenshteinDistance;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_ENTITY_LABEL;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.RDF_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.RDFS_LABEL;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.DC_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_SELECTED_TEXT;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_ENTITY_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_CONFIDENCE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_SELECTION_CONTEXT;
import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.swing.*;

import org.apache.clerezza.rdf.core.LiteralFactory;
import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.NonLiteral;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.core.impl.PlainLiteralImpl;
import org.apache.clerezza.rdf.core.impl.TripleImpl;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.InvalidContentException;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.helper.ContentItemHelper;
import org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.impl.AbstractEnhancementEngine;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.stanbol.enhancer.servicesapi.Blob;
import org.apache.stanbol.enhancer.servicesapi.Chain;
import org.apache.stanbol.enhancer.servicesapi.ChainException;
import org.apache.stanbol.enhancer.servicesapi.ContentItem;
import org.apache.stanbol.enhancer.servicesapi.EngineException;
import org.apache.stanbol.enhancer.servicesapi.EnhancementEngine;
import org.apache.stanbol.enhancer.servicesapi.InvalidContentException;
import org.apache.stanbol.enhancer.servicesapi.ServiceProperties;
import org.apache.stanbol.enhancer.servicesapi.helper.ContentItemHelper;
import org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.rdf.NamespaceEnum;
import org.apache.stanbol.enhancer.servicesapi.rdf.OntologicalClasses;
import org.apache.stanbol.enhancer.servicesapi.rdf.TechnicalClasses;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.stanbol.entityhub.servicesapi.site.ReferencedSite;
import org.apache.stanbol.entityhub.servicesapi.site.ReferencedSiteException;
import org.apache.stanbol.entityhub.servicesapi.site.ReferencedSiteManager;
import org.apache.stanbol.entityhub.servicesapi.query.SimilarityConstraint;
import org.apache.stanbol.entityhub.servicesapi.query.Constraint;
import org.apache.stanbol.entityhub.servicesapi.query.FieldQuery;
import org.apache.stanbol.entityhub.servicesapi.query.QueryResultList;
import org.apache.stanbol.entityhub.servicesapi.query.ReferenceConstraint;
import org.apache.stanbol.entityhub.servicesapi.query.TextConstraint;
import org.apache.stanbol.entityhub.servicesapi.model.Entity;
import org.apache.stanbol.entityhub.servicesapi.model.Representation;
import org.apache.stanbol.entityhub.servicesapi.model.Text;
import org.apache.stanbol.entityhub.servicesapi.model.rdf.RdfResourceEnum;
import org.apache.stanbol.entityhub.servicesapi.Entityhub;
import org.apache.stanbol.entityhub.servicesapi.EntityhubException;
//import org.apache.stanbol.entityhub.servicesapi.defaults;


import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.ReferenceStrategy;
import org.apache.felix.scr.annotations.Service;

/**
 * Simple Enhancer
 * @author Anuj
 */
@Component(immediate = true, metatype = true)
@Service
@Properties(value={
    @Property(name=EnhancementEngine.PROPERTY_NAME,value="formcept-disambiguator")
})
public class FCDEnhancer extends AbstractEnhancementEngine<IOException,RuntimeException>
    implements EnhancementEngine, ServiceProperties {
    
    private static Logger LOG = LoggerFactory.getLogger(FCDEnhancer.class);
    
    @Property(value = "https://www.formcept.com/analyze")
    public static final String FORMCEPT_SERVICE_URL = "org.formcept.engine.enhancer.url";
    
    /**
     * Service URL
     */
    private String serviceURL;

    /**
     * The default value for the Execution of this Engine. Currently set to
     * {@link ServiceProperties#ORDERING_EXTRACTION_ENHANCEMENT} + 17. It should run after Metaxa and LangId.
     */
    public static final Integer defaultOrder = ServiceProperties.ORDERING_EXTRACTION_ENHANCEMENT -10;
    public static final String PLAIN_TEXT_MIMETYPE = "text/plain";

   @Reference
    protected ReferencedSiteManager siteManager;
    /**
     * Contains the only supported mime type {@link #PLAIN_TEXT_MIMETYPE}
     */


    public static final Set<String> SUPPORTED_MIMETYPES = Collections.singleton(PLAIN_TEXT_MIMETYPE);

    
    public Map<String,Object> getServiceProperties() {
        return Collections.unmodifiableMap(Collections.singletonMap(
            ENHANCEMENT_ENGINE_ORDERING, (Object) defaultOrder));
    }

    public int canEnhance(ContentItem ci) throws EngineException {
        // check if content is present
        try {
            if((ContentItemHelper.getText(ci.getBlob()) == null) || 
                    (ContentItemHelper.getText(ci.getBlob()).trim().isEmpty())){
                return CANNOT_ENHANCE;
            }
        } catch (IOException e) {
            LOG.error("Failed to get the text for " +
            		"enhancement of content: " + ci.getUri(), e);
            throw new InvalidContentException(this, ci, e);
        }
        // default enhancement is synchronous enhancement
        return ENHANCE_SYNCHRONOUS;
    }

    public void computeEnhancements(ContentItem ci) throws EngineException {
        // write results (requires a write lock)
        // not required as we are enhancing synchronously
        //ci.getLock().writeLock().lock();
      
       MGraph graph = ci.getMetadata();
       LiteralFactory literalFactory = LiteralFactory.getInstance();
        // Retrieve the existing text annotations (requires read lock)
       
        List<String> AllEntities =new ArrayList<String>();
       
       Map<SavedEntity,List<UriRef>> textAnnotations = new HashMap<SavedEntity,List<UriRef>>();
        //the language extracteid for the parsed content or NULL if not available
       String contentLangauge=null;
      
       UriRef DC_RELATION = new UriRef(NamespaceEnum.dc+ "relation");

	//Triple I1=null,I2=null,I11=null,I22=null;
          //          Triple l1=null,l2=null;int c=0;

       List<Triple> LoseConfidence =new ArrayList<Triple>();
       List<Triple> GainConfidence=new ArrayList<Triple>();
       boolean ChangeConfidence=true;
       ci.getLock().readLock().lock();
        try {
            
             contentLangauge = EnhancementEngineHelper.getLanguage(ci);
             for (Iterator<Triple> it = graph.filter(null, RDF_TYPE, TechnicalClasses.ENHANCER_TEXTANNOTATION); it.hasNext();) 
	      {
                 UriRef uri = (UriRef) it.next().getSubject();
                 //  String name11 = EnhancementEngineHelper.getString(graph, uri, ENHANCER_ENTITY_LABEL);
           	 String name = EnhancementEngineHelper.getString(graph, uri, ENHANCER_SELECTED_TEXT);
          		//if(name==null)
          		//{continue;}
                 if (graph.filter(uri, DC_RELATION, null).hasNext()) 
                    {
                    	// this is not the most specific occurrence of this name: skip
                       	 continue;
                    }
                 SavedEntity savedEntity = SavedEntity.createFromTextAnnotation(graph, uri);
                 if(savedEntity != null)
                    {
                      AllEntities.add(name);
               	      JOptionPane.showMessageDialog(null, "Name="+name);  
                      // This is a first occurrence, collect any subsumed annotations
                      List<UriRef> subsumed = new ArrayList<UriRef>();
                      
                      for (Iterator<Triple> it2 = graph.filter(null, DC_RELATION, uri); it2.hasNext();) 
                    	{
                      	  UriRef uri1 = (UriRef) it2.next().getSubject();
                	  String name11 = EnhancementEngineHelper.getString(graph, uri1, ENHANCER_ENTITY_LABEL);
		          Iterator<Triple> confidenceTriple =graph.filter(uri1,ENHANCER_CONFIDENCE,null);
     			  while(confidenceTriple.hasNext())
    			   {
                             LoseConfidence.add(confidenceTriple.next());
			     //confidenceTriple.remove();//remove the existing confidence value(s)
     		           }
     			  String name1 = EnhancementEngineHelper.getString(graph, uri1, ENHANCER_CONFIDENCE);
			  JOptionPane.showMessageDialog(null, "The Entity  "+name1+" + "+name11);   
                      	  subsumed.add(uri1);
                          textAnnotations.put(savedEntity, subsumed);
	                 }
                     }
                 }
               }  
       catch (Exception e) 
          {
             JOptionPane.showMessageDialog(null, "To Tell The caste");   
             LOG.info("zzzError   "+e.getMessage());   
             JOptionPane.showMessageDialog(null, e.getStackTrace());   
          }
            ci.getLock().readLock().unlock();
               
            
           
         /*

	ci.getLock().readLock().lock();
        try {
             contentLangauge = EnhancementEngineHelper.getLanguage(ci);
            for (Iterator<Triple> it = graph.filter(null, RDF_TYPE, TechnicalClasses.ENHANCER_TEXTANNOTATION); it.hasNext();) 
	        {
           
           	 UriRef uri = (UriRef) it.next().getSubject();
           	 String name = EnhancementEngineHelper.getString(graph, uri, ENHANCER_SELECTED_TEXT);
          	
                if (graph.filter(uri, DC_RELATION, null).hasNext()) {
                    	// this is not the most specific occurrence of this name: skip
                       	 continue;
                    }
          	 SavedEntity savedEntity = SavedEntity.createFromTextAnnotation(graph, uri);
              
                	if(savedEntity != null){
                		AllEntities.add(name);
               				JOptionPane.showMessageDialog(null, "Name="+name);  
          
                    // This is a first occurrence, collect any subsumed annotations
                    
      							
                    for (Iterator<Triple> it2 = graph.filter(null, DC_RELATION, uri); it2.hasNext();) 
                    	{
                      	UriRef uri1 = (UriRef) it2.next().getSubject();
                	   String name11 = EnhancementEngineHelper.getString(graph, uri1, ENHANCER_ENTITY_LABEL);
				 String name1 = EnhancementEngineHelper.getString(graph, uri1, ENHANCER_CONFIDENCE);
			////  graph.remove(l);
 				
                      	JOptionPane.showMessageDialog(null, "The Changes  "+name1+" + "+name11);   
                      	}
                    	
                   
	}}
        } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "My Problem");   
         LOG.info("zzzError   "+e.getMessage());   

         JOptionPane.showMessageDialog(null, e.getStackTrace());   
                      	
            }
            ci.getLock().readLock().unlock();
       */


        ReferencedSite dbpediaReferencedSite=null;
        try{
     // ReferencedSiteManager siteManager=new ReferencedSiteManager();
		 dbpediaReferencedSite =siteManager.getReferencedSite("dbpedia");
         JOptionPane.showMessageDialog(null, "Fired up");   
        
        
        for(Entry<SavedEntity,List<UriRef>> entry : textAnnotations.entrySet())
        {
			
	  SavedEntity savedEntity= entry.getKey();       
          String label= savedEntity.getName();//the selected text of the TextAnnotation to disambiguate
	  Collection<String> types=null; //potential types of entities
   	  String language=contentLangauge; //the language of the analyzed text
    	  List<UriRef> subsumed=entry.getValue();
    		
    	  if(subsumed.size()<=1){continue;}
          String extractionContext=findContext(label,AllEntities); //the surrounding text of the extraction
        
           FieldQuery query =dbpediaReferencedSite.getQueryFactory().createFieldQuery();
	   Constraint labelConstraint;
    	    //TODO: make case sensitivity configurable
           boolean casesensitive = false;
           String SavedEntityLabel = casesensitive ? label : label.toLowerCase();
      	   if(language != null){
            	//search labels in the language and without language
           labelConstraint = new TextConstraint(SavedEntityLabel,casesensitive,language,null);
            } else {
            labelConstraint = new TextConstraint(SavedEntityLabel,casesensitive);
            }
    
    	   query.setConstraint(RDFS_LABEL.getUnicodeString(),labelConstraint);
 	   LOG.info("Init SavedEntityTaggingEngine instance for the Entityhub");
      	   query.setConstraint("http://stanbol.apache.org/ontology/entityhub/query#fullText", new SimilarityConstraint(extractionContext));
	   query.setLimit(Math.max(20,9));
      	   QueryResultList<Entity> results = dbpediaReferencedSite.findEntities(query);
       
         
 	   LOG.info(" - {} results returned by query {}", results.size(), results.getQuery());
           Float maxScore = null;
           Float maxExactScore = null;
        
           JOptionPane.showMessageDialog(null, label+ "  " +results.size());
           List<Suggestion> matches = new ArrayList<Suggestion>(results.size());
        //assumes entities are sorted by score
           for (Iterator<Entity> guesses = results.iterator();guesses.hasNext();) 
            {
              Suggestion match = new Suggestion(guesses.next());
              Representation rep = match.getEntity().getRepresentation();
              Float score = rep.getFirst(RdfResourceEnum.resultScore.getUri(),Float.class);
              if(maxScore == null)
               {
                maxScore = score;
               }
              Iterator<Text> labels = rep.getText(RDFS_LABEL.getUnicodeString());
              while(labels.hasNext() && match.getLevenshtein() < 1.0)
               {
                 Text label1 = labels.next();
                 if(language == null || //if the content language is unknown -> accept all labels
                        label1.getLanguage() == null ||  //accept labels with no language
                        //and labels in the same language as the content
                        (language != null && label1.getLanguage().startsWith(language)))
                          {
                            double actMatch = levenshtein(
                              casesensitive ? label1.getText().toLowerCase() : label1.getText(), 
                                SavedEntityLabel);
                            if(actMatch > match.getLevenshtein())
                             {
                               match.setLevenshtein(actMatch);
                               match.setMatchedLabel(label1);
                             }
                          }
               }
            
              if(match.getMatchedLabel() != null)
               {
                 if(match.getLevenshtein() == 1.0)
                  {
                   if(maxExactScore == null)
                    {
                        maxExactScore = score;
                    }
                    //normalise exact matches against the best exact score
                    match.setScore(score.doubleValue()/maxExactScore.doubleValue());
                  } 
                 else 
                  {
                    //normalise partial matches against the best match and the
                    //Levenshtein similarity with the label
                    match.setScore(score.doubleValue()*match.getLevenshtein()/maxScore.doubleValue());	
                  }
                 matches.add(match);
                 JOptionPane.showMessageDialog(null, match.getMatchedLabel()+"  "+match.getScore());
               } 
              else 
               {
                LOG.info("No value of {} for Entity {}!",RDFS_LABEL.getUnicodeString(),match.getEntity().getId());
               }
             }

        //now sort the results
        Collections.sort(matches);
      //matches=matches.subList(0, Math.min(matches.size(),10));
	    

         ci.getLock().readLock().lock();		
        try
         {	
           if(IntersectionCheck(matches,subsumed,graph,contentLangauge))
            {
		GainConfidence=Intersection(matches,subsumed,graph,GainConfidence,contentLangauge);
            }
            else
            {
                LoseConfidence=RemoveNot(subsumed,graph,LoseConfidence);
            }
          }
             finally
              {    
             ci.getLock().readLock().unlock();		
	      }
		
		
		
			/*UriRef enhancement; //the original enhancement
    		UriRef ENHANCER_CONFIDENCE; //the confidence triple
    		float yourConfidence;

    		Iterator<Triple> confidenceTriple =graph.filter(enhancement,ENHANCER_CONFIDENCE,null);
    		while(confidenceTriple){
       			 it.next()
       			 it.remove();//remove the existing confidence value(s)
    				}

    //add your own confidence

   			 graph.add(new TripleImpl(
        	enhancement;ENHANCER_CONFIDENCE;
        	LiteralFactory.getInstance().createTypedLiteral(yourConfidence));
			*/
		
	  }
	
           ci.getLock().writeLock().lock();
           try
            {	
             RemoveConfidence(graph,LoseConfidence);
             JOptionPane.showMessageDialog(null, "seen");
             AddConfidence(graph,GainConfidence);
            }
          finally
            {    
             ci.getLock().writeLock().unlock();		
	    }
         
	
        
        }
        catch(Exception e){
       JOptionPane.showMessageDialog(null, "Love");  
  JOptionPane.showMessageDialog(null, e.getMessage());  

       JOptionPane.showMessageDialog(null, e.getStackTrace());  

        }
		
    }




protected List<Triple> RemoveNot(List<UriRef> subsumed, MGraph graph,List<Triple> LoseConfidence)
{
	for(int i=0;i<subsumed.size();i++)
	{
		UriRef uri= subsumed.get(i);
		Iterator<Triple> confidenceTriple =graph.filter(uri,ENHANCER_CONFIDENCE,null);
     			  while(confidenceTriple.hasNext())
    			   {
                             LoseConfidence.remove(confidenceTriple.next());
			     //confidenceTriple.remove();//remove the existing confidence value(s)
     		           }
	}
return LoseConfidence;
}

   protected boolean IntersectionCheck( List<Suggestion> matches, List<UriRef> subsumed, MGraph graph,String contentLangauge)
  {
    for(int i=0;i<subsumed.size();i++)
     {
       UriRef uri= subsumed.get(i);
       String Name= EnhancementEngineHelper.getString(graph, uri, ENHANCER_ENTITY_LABEL);
       
	if(Name==null){continue;}
	
       for(int j=0;j<matches.size();j++)
  	 {
  	   Suggestion H=matches.get(j);
  	   String SuggestName=H.getMatchedLabel().toString();
		JOptionPane.showMessageDialog(null, "text="+Name+"v/s"+SuggestName+" compare "+SuggestName.compareToIgnoreCase(Name));  	
	   if(SuggestName.compareToIgnoreCase(Name+"@"+contentLangauge)==0)
  	    {
		JOptionPane.showMessageDialog(null, "macth="+SuggestName);
  	      return true;
  	    }
         }
      } 
  return false;
  }
   
  protected List<Triple> Intersection( List<Suggestion> matches, List<UriRef> subsumed, MGraph graph,List<Triple> GainConfidence,String contentLangauge)
  {
  // List<Triple> GainConfidence=new ArrayList<Triple>();
  
   for(int i=0;i<subsumed.size();i++)
     {
       boolean MatchFound=false;
       UriRef uri= subsumed.get(i);
       String Name= EnhancementEngineHelper.getString(graph, uri, ENHANCER_ENTITY_LABEL);
       int c=0;
       for(int j=0;j<matches.size();j++)
        {
  	  Suggestion H=matches.get(j);
  	  String SuggestName=H.getMatchedLabel().toString();
  	  if(Name==null){continue;}
          if(SuggestName.compareToIgnoreCase(Name+"@"+contentLangauge)==0)
  	   {
 	     Triple I1=new TripleImpl(uri,ENHANCER_CONFIDENCE,LiteralFactory.getInstance().createTypedLiteral(H.getScore()));
	     Triple I11=new TripleImpl((UriRef)I1.getSubject(),new UriRef(NamespaceEnum.dc + "contributor"),LiteralFactory.getInstance().createTypedLiteral(this.getClass().getName()));
  	     GainConfidence.add(I1);
  	     GainConfidence.add(I11);
             MatchFound=true;
           }
  	}
	if(!MatchFound)
         {
            Triple I1=new TripleImpl(uri,ENHANCER_CONFIDENCE,LiteralFactory.getInstance().createTypedLiteral(0.0));
	    Triple I11=new TripleImpl((UriRef)I1.getSubject(),new UriRef(NamespaceEnum.dc + "contributor"),LiteralFactory.getInstance().createTypedLiteral(this.getClass().getName()));
  	    GainConfidence.add(I1);
  	    GainConfidence.add(I11);
	 }
     }
    return GainConfidence; 
  }
   
protected void RemoveConfidence(MGraph graph, List<Triple> LoseConfidence)
{
  for(int i=0;i<LoseConfidence.size();i++)
   {
     Triple a=LoseConfidence.get(i);
     graph.remove(a);
   }

}
 
 
protected void AddConfidence(MGraph graph, List<Triple> GainConfidence)
{
  for(int i=0;i<GainConfidence.size();i++)
   {
     Triple a=GainConfidence.get(i);
     graph.add(a);
   }

}
             

  protected String findContext(String label, List<String> AllEntities){
  		
  	String a="";
  	
  	for(int i=0; i<AllEntities.size();i++)
  	{
  		 
  		if(label.compareToIgnoreCase(AllEntities.get(i))!=0)
  		{a=a+AllEntities.get(i);}
  		// JOptionPane.showMessageDialog(null,"iteration"+i+"="+ a);  
  	}
     return a;
   }
   
   
   
   
    
    /**
     * Activate and read the properties
     * @param ce the {@link ComponentContext}
     */
    @Activate
    protected void activate(ComponentContext ce) throws ConfigurationException {
        try {
            super.activate(ce);
        } catch (IOException e) {
            // log
            LOG.error("Failed to update the configuration", e);
        }
        @SuppressWarnings("unchecked")
        Dictionary<String, Object> properties = ce.getProperties();
        // update the service URL if it is defined
        if(properties.get(FORMCEPT_SERVICE_URL) != null){
            this.serviceURL = (String) properties.get(FORMCEPT_SERVICE_URL);
        }
    }
    
    /**
     * Deactivate
     * @param ce the {@link ComponentContext}
     */
    @Deactivate
    protected void deactivate(ComponentContext ce) {
        super.deactivate(ce);
    }

    /**
     * Gets the Service URL
     * @return
     */
    public String getServiceURL() {
        return serviceURL;
    }

private  static double levenshtein(String s1, String s2) {
        if(s1 == null || s2 == null){
            throw new IllegalArgumentException("NONE of the parsed String MUST BE NULL!");
        }
        s1 = StringUtils.trim(s1);
        s2 = StringUtils.trim(s2);
        return s1.isEmpty() || s2.isEmpty() ? 0 :
            1.0 - (((double)getLevenshteinDistance(s1, s2)) / ((double)(Math.max(s1.length(), s2.length()))));
    }
            
}

