/**
 * Copyright 2010 JBoss Inc
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
 */

package org.drools.guvnor.client.qa;

/**
 * Created by IntelliJ IDEA.
 * User: nheron
 * Date: 8 nov. 2009
 * Time: 19:12:24
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;
import java.util.Map;

import org.drools.guvnor.client.common.DirtyableComposite;
import org.drools.guvnor.client.common.DropDownValueChanged;
import org.drools.guvnor.client.common.FormStylePopup;
import org.drools.guvnor.client.common.InfoPopup;
import org.drools.guvnor.client.common.SmallLabel;
import org.drools.guvnor.client.common.ValueChanged;
import org.drools.guvnor.client.messages.Constants;
import org.drools.guvnor.client.modeldriven.ui.ActionValueEditor;
import org.drools.guvnor.client.modeldriven.ui.DatePickerTextBox;
import org.drools.guvnor.client.modeldriven.ui.EnumDropDown;
import org.drools.ide.common.client.modeldriven.DropDownData;
import org.drools.ide.common.client.modeldriven.SuggestionCompletionEngine;
import org.drools.ide.common.client.modeldriven.testing.ExecutionTrace;
import org.drools.ide.common.client.modeldriven.testing.FactData;
import org.drools.ide.common.client.modeldriven.testing.FieldData;
import org.drools.ide.common.client.modeldriven.testing.Scenario;
import org.drools.ide.common.client.modeldriven.testing.VerifyField;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.util.Format;

/**
 * Constraint editor for the VerifyField of the expect part
 *
 * @author Nicolas Heron
 */
public class VerifyFieldConstraintEditor extends DirtyableComposite {
    private String                     factType;
    private VerifyField                field;
    private final Panel                panel;
    private Scenario                   scenario;
    private SuggestionCompletionEngine sce;
    private ValueChanged               callback;
    private Constants                  constants = ((Constants) GWT.create( Constants.class ));
    private ExecutionTrace             executionTrace;

    public VerifyFieldConstraintEditor(String factType,
                                       ValueChanged callback,
                                       VerifyField field,
                                       SuggestionCompletionEngine sce,
                                       Scenario scenario,
                                       ExecutionTrace executionTrace) {
        this.field = field;
        this.sce = sce;
        this.factType = factType;
        this.callback = callback;
        this.scenario = scenario;
        this.executionTrace = executionTrace;
        panel = new SimplePanel();
        refreshEditor();
        initWidget( panel );
    }

    private void refreshEditor() {
        String key = factType + "." + field.fieldName;
        String flType = sce.getFieldType( key );
        panel.clear();
        if ( flType.equals( SuggestionCompletionEngine.TYPE_NUMERIC ) ) {
            final TextBox box = editableTextBox( callback,
                                                 field.fieldName,
                                                 field.expected );
            box.addKeyboardListener( ActionValueEditor.getNumericFilter( box ) );
            panel.add( box );
        } else if ( flType.equals( SuggestionCompletionEngine.TYPE_BOOLEAN ) ) {
            String[] c = new String[]{"true", "false"};
            panel.add( new EnumDropDown( field.expected,
                                         new DropDownValueChanged() {
                                             public void valueChanged(String newText,
                                                                      String newValue) {
                                                 callback.valueChanged( newValue );
                                             }
                                         },
                                         DropDownData.create( c ) ) );
        } else if (flType != null && flType.equals(SuggestionCompletionEngine.TYPE_DATE)) {           
            final DatePickerTextBox datePicker = new DatePickerTextBox( field.expected );
            String m = Format.format(((Constants) GWT.create(Constants.class)).ValueFor0(),
            		field.fieldName);
            datePicker.setTitle(m);
            datePicker.addValueChanged(new ValueChanged() {
                public void valueChanged(String newValue) {
                	field.expected = newValue;
                }
            });

             panel.add(datePicker);
        } else {
            String[] enums = sce.getDataEnumList( key );
            if ( enums != null ) {
                panel.add( new EnumDropDown( field.expected,
                                             new DropDownValueChanged() {
                                                 public void valueChanged(String newText,
                                                                          String newValue) {
                                                     callback.valueChanged( newValue );
                                                 }
                                             },
                                             DropDownData.create( enums ) ) );

            } else {
                if ( field.expected != null && field.expected.length() > 0 && field.nature == FieldData.TYPE_UNDEFINED ) {
                    //  GUVNOR-337
                    if ( field.expected.charAt( 0 ) == '=' ) {
                        field.nature = FieldData.TYPE_VARIABLE;
                    } else {
                        field.nature = FieldData.TYPE_LITERAL;
                    }
                }
                if ( field.nature == FieldData.TYPE_UNDEFINED && isThereABoundVariableToSet() == true ) {
                    Image clickme = new Image( "images/edit.gif" ); // NON-NLS
                    clickme.addClickListener( new ClickListener() {
                        public void onClick(Widget w) {
                            showTypeChoice( w,
                                            field );
                        }
                    } );
                    panel.add( clickme );
                } else if ( field.nature == FieldData.TYPE_VARIABLE ) {
                    panel.add( variableEditor() );
                } else {
                    panel.add( editableTextBox( callback,
                                                field.fieldName,
                                                field.expected ) );
                }

            }
        }
    }

    private Widget variableEditor() {
        // sce.
        List vars = this.scenario.getFactNamesInScope( this.executionTrace,
                                                       true );

        final ListBox box = new ListBox();

        if ( this.field.expected == null ) {
            box.addItem( constants.Choose() );
        }
        int j = 0;
        for ( int i = 0; i < vars.size(); i++ ) {
            String var = (String) vars.get( i );
            Map m = this.scenario.getVariableTypes();
            FactData f = (FactData) scenario.getFactTypes().get( var );
            String fieldType = sce.getFieldType( this.factType,
                                                 field.fieldName );
            if ( f.type.equals( fieldType ) ) {
                if ( box.getItemCount() == 0 ) {
                    box.addItem( "..." );
                    j++;
                }
                box.addItem( "=" + var );
                if ( this.field.expected != null && this.field.expected.equals( "=" + var ) ) {
                    box.setSelectedIndex( j );
                }
                j++;
            }
        }

        box.addChangeListener( new ChangeListener() {
            public void onChange(Widget w) {
                field.expected = box.getItemText( box.getSelectedIndex() );
            }
        } );

        return box;
    }

    private static TextBox editableTextBox(final ValueChanged changed,
                                           String fieldName,
                                           String initialValue) {
        // Fixme nheron
        final TextBox tb = new TextBox();
        tb.setText( initialValue );
        String m = Format.format( ((Constants) GWT.create( Constants.class )).ValueFor0(),
                                  fieldName );
        tb.setTitle( m );
        tb.addChangeListener( new ChangeListener() {
            public void onChange(Widget w) {
                changed.valueChanged( tb.getText() );
            }
        } );

        return tb;
    }

    private void showTypeChoice(Widget w,
                                final VerifyField con) {
        final FormStylePopup form = new FormStylePopup( "images/newex_wiz.gif",
                                                        constants.FieldValue() );

        Button lit = new Button( constants.LiteralValue() );
        lit.addClickListener( new ClickListener() {
            public void onClick(Widget w) {
                con.nature = FieldData.TYPE_LITERAL;
                doTypeChosen( form );
            }

        } );
        form.addAttribute( constants.LiteralValue() + ":",
                           widgets( lit,
                                    new InfoPopup( constants.LiteralValue(),
                                                   constants.LiteralValTip() ) ) );

        form.addRow( new HTML( "<hr/>" ) );
        form.addRow( new SmallLabel( constants.AdvancedOptions() ) );

        // If we are here, then there must be a bound variable compatible with
        // me

        Button variable = new Button( constants.BoundVariable() );
        variable.addClickListener( new ClickListener() {
            public void onClick(Widget w) {
                con.nature = FieldData.TYPE_VARIABLE;
                doTypeChosen( form );
            }
        } );
        form.addAttribute( constants.AVariable(),
                           widgets( variable,
                                    new InfoPopup( constants.ABoundVariable(),
                                                   constants.BoundVariableTip() ) ) );

        form.show();
    }

    private boolean isThereABoundVariableToSet() {
        boolean retour = false;
        List vars = this.scenario.getFactNamesInScope( executionTrace,
                                                       true );
        if ( vars.size() > 0 ) {
            for ( int i = 0; i < vars.size(); i++ ) {
                String var = (String) vars.get( i );
                Map m = this.scenario.getVariableTypes();
                FactData f = (FactData) scenario.getFactTypes().get( var );
                String fieldType = sce.getFieldType( this.factType,
                                                     field.fieldName );
                if ( f.type.equals( fieldType ) ) {
                    retour = true;
                    break;
                }
            }
        }
        return retour;
    }

    private void doTypeChosen(final FormStylePopup form) {
        refreshEditor();
        form.hide();
    }

    private Panel widgets(Widget left,
                          Widget right) {
        HorizontalPanel panel = new HorizontalPanel();
        panel.add( left );
        panel.add( right );
        panel.setWidth( "100%" );
        return panel;
    }

}
