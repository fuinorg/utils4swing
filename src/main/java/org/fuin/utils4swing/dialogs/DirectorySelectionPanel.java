/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.utils4swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.fuin.utils4j.Utils4J;

/**
 * Panel for directory selection.
 */
public class DirectorySelectionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel panelTitle = null;

    private JPanel panelButtons = null;

    private JPanel panelDirectory = null;

    private JLabel labelTitle = null;

    private JButton buttonOK = null;

    private JButton buttonCancel = null;

    private JTextField textFieldDirectory = null;

    private JButton buttonDirectory = null;

    private DirectorySelector destDirSelector = null;

    private JCheckBox checkBoxIncludeSubdirs = null;

    /**
     * This is the constructor with parent.
     * 
     * @param destDirSelector
     *            Controller.
     */
    public DirectorySelectionPanel(final DirectorySelector destDirSelector) {
        super();
        initialize();
        Utils4J.checkNotNull("destDirSelector", destDirSelector);
        this.destDirSelector = destDirSelector;
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(450, 130);
        this.setPreferredSize(new Dimension(450, 130));
        this.add(getPanelTitle(), BorderLayout.NORTH);
        this.add(getPanelButtons(), BorderLayout.SOUTH);
        this.add(getPanelDirectory(), BorderLayout.CENTER);
    }

    private JLabel getLabelTitle() {
        if (labelTitle == null) {
            labelTitle = new JLabel();
            labelTitle.setText("Please select the destination directory:");
        }
        return labelTitle;
    }

    private JPanel getPanelTitle() {
        if (panelTitle == null) {
            final FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(10);
            flowLayout.setAlignment(FlowLayout.LEFT);
            flowLayout.setVgap(10);
            panelTitle = new JPanel();
            panelTitle.setLayout(flowLayout);
            panelTitle.setPreferredSize(new Dimension(40, 30));
            panelTitle.add(getLabelTitle(), null);
        }
        return panelTitle;
    }

    private JPanel getPanelButtons() {
        if (panelButtons == null) {
            final FlowLayout flowLayout2 = new FlowLayout();
            flowLayout2.setAlignment(FlowLayout.RIGHT);
            flowLayout2.setVgap(10);
            flowLayout2.setHgap(10);
            panelButtons = new JPanel();
            panelButtons.setLayout(flowLayout2);
            panelButtons.setPreferredSize(new Dimension(200, 45));
            panelButtons.add(getCheckBoxIncludeSubdirs(), null);
            panelButtons.add(getButtonCancel(), null);
            panelButtons.add(getButtonOK(), null);
        }
        return panelButtons;
    }

    private JPanel getPanelDirectory() {
        if (panelDirectory == null) {
            final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.insets = new Insets(0, 0, 0, 5);
            gridBagConstraints1.gridy = 0;
            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints.gridx = 0;
            panelDirectory = new JPanel();
            panelDirectory.setLayout(new GridBagLayout());
            panelDirectory.add(getTextFieldDirectory(), gridBagConstraints);
            panelDirectory.add(getButtonDirectory(), gridBagConstraints1);
        }
        return panelDirectory;
    }

    /**
     * Returns the 'OK' button for usage as default button.
     * 
     * @return Button.
     */
    protected final JButton getButtonOK() {
        if (buttonOK == null) {
            buttonOK = new JButton();
            buttonOK.setText("OK");
            buttonOK.setPreferredSize(new Dimension(80, 26));
            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    if (destDirSelector != null) {
                        destDirSelector.ok();
                    }
                }
            });
        }
        return buttonOK;
    }

    /**
     * Returns the 'Cancel' button for usage as default button.
     * 
     * @return Button.
     */
    protected final JButton getButtonCancel() {
        if (buttonCancel == null) {
            buttonCancel = new JButton();
            buttonCancel.setText("Cancel");
            buttonCancel.setPreferredSize(new Dimension(80, 26));
            buttonCancel.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    if (destDirSelector != null) {
                        destDirSelector.cancel();
                    }
                }
            });
        }
        return buttonCancel;
    }

    private JTextField getTextFieldDirectory() {
        if (textFieldDirectory == null) {
            textFieldDirectory = new JTextField();
        }
        return textFieldDirectory;
    }

    private JButton getButtonDirectory() {
        if (buttonDirectory == null) {
            buttonDirectory = new JButton();
            buttonDirectory.setPreferredSize(new Dimension(40, 20));
            buttonDirectory.setText("...");
            buttonDirectory.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    selectDirectory();
                }
            });
        }
        return buttonDirectory;
    }

    private JCheckBox getCheckBoxIncludeSubdirs() {
        if (checkBoxIncludeSubdirs == null) {
            checkBoxIncludeSubdirs = new JCheckBox();
            checkBoxIncludeSubdirs.setText("Include Subdirectories");
            checkBoxIncludeSubdirs.setPreferredSize(new Dimension(250, 24));
            checkBoxIncludeSubdirs.setVisible(true);
        }
        return checkBoxIncludeSubdirs;
    }

    private void selectDirectory() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(getTextFieldDirectory().getText()));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                setDirectoryIntern(chooser.getSelectedFile().getCanonicalPath());
            } catch (final IOException e) {
                setDirectoryIntern(chooser.getSelectedFile().toString());
            }
        }
    }

    private void setDirectoryIntern(final String directory) {
        getTextFieldDirectory().setText(directory);
    }

    /**
     * Set the directory. If called outside the EDT this method will switch to
     * the UI thread using <code>SwingUtilities.invokeAndWait(Runnable)</code>.
     * 
     * @param directory
     *            Path for the text field.
     */
    public final void setDirectory(final String directory) {
        if (SwingUtilities.isEventDispatchThread()) {
            setDirectoryIntern(directory);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        setDirectoryIntern(directory);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    private void setTitleIntern(final String title) {
        getLabelTitle().setText(title);
    }

    /**
     * Set the title. If called outside the EDT this method will switch to the
     * UI thread using <code>SwingUtilities.invokeAndWait(Runnable)</code>.
     * 
     * @param title
     *            Text for the label.
     */
    public final void setTitle(final String title) {
        if (SwingUtilities.isEventDispatchThread()) {
            setTitleIntern(title);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        setTitleIntern(title);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    private void setIncludeSubdirsVisibleIntern(final boolean b) {
        getCheckBoxIncludeSubdirs().setVisible(b);
    }

    /**
     * Set the "Include subdirectories" checkbox visible. If called outside the
     * EDT this method will switch to the UI thread using
     * <code>SwingUtilities.invokeAndWait(Runnable)</code>.
     * 
     * @param b
     *            If visible <code>true</code> else <code>false</code>.
     */
    public final void setIncludeSubdirsVisible(final boolean b) {
        if (SwingUtilities.isEventDispatchThread()) {
            setIncludeSubdirsVisibleIntern(b);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        setIncludeSubdirsVisibleIntern(b);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Returns the currently selected directory.
     * 
     * @return Text of the 'directory' text field.
     */
    public final String getDirectory() {
        return getTextFieldDirectory().getText().trim();
    }

    /**
     * Returns if sub directories should be included.
     * 
     * @return If sub directories are included <code>true</code> else
     *         <code>false</code>.
     */
    public final boolean isIncludeSubdirs() {
        return getCheckBoxIncludeSubdirs().isSelected();
    }

    private static void ignore() {
        // Dummy method to satisfy Checkstyle's empty block check...
    }

} // @jve:decl-index=0:visual-constraint="10,10"
