package com.googlecode.generatefluentinterface;

import com.intellij.codeInsight.generation.PsiFieldMember;
import com.intellij.ide.util.MemberChooser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

/**
 * <p>
 * <p> Date: 6/15/11 Time: 4:00 PM </p>
 *
 * @author Felix.ZHU
 * @since v
 */
public class GenerateFluentInterfaceMemberChooser extends MemberChooser<PsiFieldMember> {
// ------------------------------ FIELDS ------------------------------

    private AdvanceOptionPanel advanceOptionPanel;

// --------------------------- CONSTRUCTORS ---------------------------

    public GenerateFluentInterfaceMemberChooser(PsiFieldMember[] elements,
                                                @org.jetbrains.annotations.NotNull
                                                Project project) {
        super(elements, false, true, project);
        setCopyJavadocVisible(false);

    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected JComponent createSouthPanel() {
        advanceOptionPanel = new AdvanceOptionPanel();
        advanceOptionPanel.add(super.createSouthPanel(),
                new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(10, 0, 0, 0), 0, 0));
        setOKActionEnabled(true);
        return advanceOptionPanel;
    }

    public String getSetterPrefix() {
        return this.advanceOptionPanel.getSetterPrefix();
    }

    public boolean generateGetters() {
        return this.advanceOptionPanel.generateGetters();
    }

    public boolean useSetters() {
        return this.advanceOptionPanel.useSetters();
    }

// -------------------------- INNER CLASSES --------------------------

    protected class AdvanceOptionPanel extends JPanel {
        private final JTextField setterPrefix;
        private final JCheckBox generateGetters;
        private final JCheckBox invokeExistingSetters;

        public String getSetterPrefix() {
            return setterPrefix.getText().replaceAll("\\s", "");
        }

        public boolean generateGetters() {
            return this.generateGetters.isSelected();
        }

        public boolean useSetters() {
            return this.invokeExistingSetters.isSelected();
        }

        public AdvanceOptionPanel() {
            super(new GridBagLayout());

            GenerateFluentInterfaceApplicationComponent applicationComponent
                    = ApplicationManager.getApplication()
                    .getComponent(GenerateFluentInterfaceApplicationComponent.class);

            setterPrefix = new JTextField();

            JLabel label = new JLabel("setters prefix:");
            label.setDisplayedMnemonic(KeyEvent.VK_P);
            label.setLabelFor(setterPrefix);
            setterPrefix.setText(applicationComponent.getSetterPrefix());
            setterPrefix.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(final FocusEvent e) {
                    setterPrefix.selectAll();
                }

                @Override
                public void focusLost(final FocusEvent e) {
                }
            });

            generateGetters = new JCheckBox("generate getters");
            generateGetters.setMnemonic(KeyEvent.VK_G);
            generateGetters.setSelected(applicationComponent.isGeneratingGetters());

            invokeExistingSetters = new JCheckBox("invoke existing setters");
            invokeExistingSetters.setMnemonic(KeyEvent.VK_S);
            invokeExistingSetters.setSelected(applicationComponent.isInvokeExistingSetters());

            final Insets insets = new Insets(5, 0, 0, 0);
            add(generateGetters, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 0.1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    insets, 0, 0));
            add(label, new GridBagConstraints(1, 0, 1, 1, 0.1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    insets, 0, 0));
            add(setterPrefix, new GridBagConstraints(2, 0, 1, 1, 0.8, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    insets, 20, 0));
            add(invokeExistingSetters, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 0.1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    insets, 0, 0));
        }
    }
}
