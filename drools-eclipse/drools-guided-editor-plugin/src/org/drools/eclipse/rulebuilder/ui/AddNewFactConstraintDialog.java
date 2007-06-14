package org.drools.eclipse.rulebuilder.ui;

import org.drools.brms.client.modeldriven.SuggestionCompletionEngine;
import org.drools.brms.client.modeldriven.brxml.CompositeFactPattern;
import org.drools.brms.client.modeldriven.brxml.FactPattern;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Adds new fact pattern to a composite fact
 * 
 * @author Anton Arhipov
 * @author Ahti Kitsik
 */
public class AddNewFactConstraintDialog extends RuleDialog {

    private final FormToolkit    toolkit;

    private RuleModeller         modeller;

    private CompositeFactPattern pattern;

    public AddNewFactConstraintDialog(Shell parent,
                                      FormToolkit toolkit,
                                      RuleModeller modeller,
                                      CompositeFactPattern pattern) {
        super( parent,
               "New fact pattern",
               "Pick the value from combobox." );
        this.toolkit = toolkit;
        this.modeller = modeller;
        this.pattern = pattern;
    }

    protected Control createDialogArea(final Composite parent) {
        Composite composite = (Composite) super.createDialogArea( parent );

        //System.out.println( "Creating new fact constraint" );

        toolkit.createLabel( composite,
                             "Choose fact type" );

        String[] fieldCompletitions = getCompletion().getFactTypes();
        final Combo factsCombo = new Combo( composite,
                                            SWT.READ_ONLY );
        factsCombo.add( "Choose fact type..." );
        for ( int i = 0; i < fieldCompletitions.length; i++ ) {
            factsCombo.add( fieldCompletitions[i] );
        }
        factsCombo.select( 0 );

        factsCombo.addListener( SWT.Selection,
                                new Listener() {
                                    public void handleEvent(Event event) {

                                        //System.out.println( "HERE! event " + event );

                                        if ( factsCombo.getSelectionIndex() == 0 ) {
                                            return;
                                        }

                                        FactPattern factPattern = new FactPattern();
                                        factPattern.factType = factsCombo.getText();
                                        pattern.addFactPattern( factPattern );

                                        modeller.setDirty( true );
                                        modeller.reloadLhs();
                                        close();
                                    }
                                } );

        return composite;
    }

    private SuggestionCompletionEngine getCompletion() {
        return modeller.getSuggestionCompletionEngine();
    }

}
