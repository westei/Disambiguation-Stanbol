/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.stanbol.enhancer.engine.disambiguation.mlt;

import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.DC_TYPE;
import static org.apache.stanbol.enhancer.servicesapi.rdf.Properties.ENHANCER_SELECTED_TEXT;

import org.apache.clerezza.rdf.core.NonLiteral;
import org.apache.clerezza.rdf.core.TripleCollection;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.stanbol.enhancer.servicesapi.helper.EnhancementEngineHelper;
import org.apache.stanbol.enhancer.servicesapi.rdf.TechnicalClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SavedEntity {
    private static final Logger log = LoggerFactory.getLogger(SavedEntity.class);
    private final NonLiteral entity;
    private final String name;
    private final UriRef type;
    private String URI;
    private String Context;
    private int start;
    private int end;
    
    private SavedEntity(NonLiteral entity, String name, UriRef type) {
        this.entity = entity;
        this.name = name;
        this.type = type;
        this.URI=null;
        this.Context=null;
        this.start=0;
        this.end=0;
    }
    /**
     * Getter for the Node providing the information about that entity
     * @return the entity
     */
    public final NonLiteral getEntity() {
        return entity;
    }
    /**
     * Getter for the name
     * @return the name
     */
    public final String getName() {
        return name;
    }
    /**
     * Getter for the type
     * @return the type
     */
    public final UriRef getType() {
        return type;
    }
    @Override
    public int hashCode() {
        return entity.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof SavedEntity && entity.equals(((SavedEntity)o).entity);
    }
    @Override
    public String toString() {
        return String.format("SavedEntity %s (name=%s|type=%s)",entity,name,type);
    }
    /**
     * Extracts the information of an {@link SavedEntity} from an
     * {@link TechnicalClasses#ENHANCER_TEXTANNOTATION} instance.
     * @param graph the graph with the information
     * @param textAnnotation the text annotation instance
     * @return the {@link SavedEntity} or <code>null</code> if the parsed
     * text annotation is missing required information.
     */
    public static SavedEntity createFromTextAnnotation(TripleCollection graph, NonLiteral textAnnotation){
        String name = EnhancementEngineHelper.getString(graph, textAnnotation, ENHANCER_SELECTED_TEXT);
        if (name == null) {
            log.debug("Unable to create SavedEntity for TextAnnotation {} "
                    + "because property {} is not present",textAnnotation,ENHANCER_SELECTED_TEXT);
            return null;
        }
        name = name.trim();
        if(name.isEmpty()){
            log.debug("Unable to process TextAnnotation {} because its selects "
            		+ "an empty Stirng !",textAnnotation);
            return null;
        }
        UriRef type = EnhancementEngineHelper.getReference(graph, textAnnotation, DC_TYPE);
        if (type == null) {
            log.warn("Unable to process TextAnnotation {} because property {}"
                     + " is not present!",textAnnotation, DC_TYPE);
            return null;
        }
        // remove punctuation form the search string
        return new SavedEntity(textAnnotation,cleanupKeywords(name),type);
    }        
    /**
     * Removes punctuation form a parsed string
     */
    private static String cleanupKeywords(String keywords) {
        return keywords.replaceAll("\\p{P}", " ").trim();
    }
    
    public void setURI(String a)
    {this.URI=a;}
    
    public void setContext(String a)
    {
    	this.Context=a;
    }
    

    public String getURI()
    {return this.URI;}

    public String getContext()
    {return this.Context;}
    
    public void setIndex(int startIndex,int endIndex)
    {this.start=startIndex;
    this.end=endIndex;}

    public int getStart()
    {return this.start;}

    public int getEnd()
    {return this.end;}
  
}
