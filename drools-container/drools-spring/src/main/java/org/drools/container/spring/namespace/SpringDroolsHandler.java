package org.drools.container.spring.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SpringDroolsHandler extends NamespaceHandlerSupport {

    public void init() {

        registerBeanDefinitionParser( "resource",
                                      new ResourceDefinitionParser() );

        registerBeanDefinitionParser( "resource-ref",
                                      new ResourceRefDefinitionParser() );

        registerBeanDefinitionParser( "resource-change-scanner",
                					  new ResourceChangeScannerDefinitionParser() );        

        registerBeanDefinitionParser( "model",
                                      new ResourceDefinitionParser() );

        registerBeanDefinitionParser( "kbase",
                                      new KnowledgeBaseDefinitionParser() );
        
        registerBeanDefinitionParser( "kagent",
                                      new KnowledgeAgentDefinitionParser() );        
        
        registerBeanDefinitionParser( "kstore",
                                      new KnowledgeStoreDefinitionParser() );        

        registerBeanDefinitionParser( "ksession",
                                      new KnowledgeSessionDefinitionParser() );

        registerBeanDefinitionParser( "connection",
                                      new ConnectionDefinitionParser() );

        registerBeanDefinitionParser( "execution-node",
                                      new ExecutionNodeDefinitionParser() );
    }

}