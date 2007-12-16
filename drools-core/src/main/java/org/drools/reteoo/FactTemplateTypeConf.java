/*
 * Copyright 2007 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created on Nov 26, 2007
 */
package org.drools.reteoo;

import java.io.Serializable;

import org.drools.RuntimeDroolsException;
import org.drools.common.InternalRuleBase;
import org.drools.facttemplates.FactTemplate;
import org.drools.facttemplates.FactTemplateObjectType;
import org.drools.reteoo.builder.BuildContext;
import org.drools.reteoo.builder.PatternBuilder;
import org.drools.rule.EntryPoint;
import org.drools.spi.ObjectType;

public class FactTemplateTypeConf
    implements
    ObjectTypeConf,
    Serializable {
    private InternalRuleBase ruleBase;
    private FactTemplate     factTemplate;
    private ObjectTypeNode   concreteObjectTypeNode;
    private ObjectTypeNode[] cache;
    private EntryPoint       entryPoint;

    public FactTemplateTypeConf(final EntryPoint entryPoint,
                                final FactTemplate factTemplate,
                                final InternalRuleBase ruleBase) {
        this.ruleBase = ruleBase;
        this.factTemplate = factTemplate;
        this.entryPoint = entryPoint;

        ObjectType objectType = new FactTemplateObjectType( factTemplate );
        this.concreteObjectTypeNode = (ObjectTypeNode) ruleBase.getRete().getObjectTypeNodes( entryPoint ).get( objectType );
        if ( this.concreteObjectTypeNode == null ) {
            BuildContext context = new BuildContext( ruleBase,
                                                     ((ReteooRuleBase) ruleBase.getRete().getRuleBase()).getReteooBuilder().getIdGenerator() );
            if ( context.getRuleBase().getConfiguration().isSequential() ) {
                // We are in sequential mode, so no nodes should have memory
                context.setTupleMemoryEnabled( false );
                context.setObjectTypeNodeMemoryEnabled( false );
                context.setTerminalNodeMemoryEnabled( false );
            } else {
                context.setTupleMemoryEnabled( true );
                context.setObjectTypeNodeMemoryEnabled( true );
                context.setTerminalNodeMemoryEnabled( true );
            }
            // there must exist an ObjectTypeNode for this concrete class                
            this.concreteObjectTypeNode = PatternBuilder.attachObjectTypeNode( context,
                                                                               objectType );
        }
        this.cache = new ObjectTypeNode[]{this.concreteObjectTypeNode};
    }

    public ObjectTypeNode getConcreteObjectTypeNode() {
        return this.concreteObjectTypeNode;
    }

    public ObjectTypeNode[] getObjectTypeNodes() {
        if ( this.cache == null ) {
            this.cache = new ObjectTypeNode[]{this.concreteObjectTypeNode};
        }
        return this.cache;
    }

    public Object getShadow(Object fact) throws RuntimeDroolsException {
        return null;
    }

    public boolean isShadowEnabled() {
        return false;
    }

    public boolean isAssignableFrom(Object object) {
        return this.factTemplate.equals( object );
    }

    public void resetCache() {
        this.cache = null;
    }

    public boolean isActive() {
        return true;
    }

    public boolean isEvent() {
        return false;
    }

}