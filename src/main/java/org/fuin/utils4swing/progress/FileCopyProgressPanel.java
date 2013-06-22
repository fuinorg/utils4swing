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
package org.fuin.utils4swing.progress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.fuin.utils4j.Cancelable;
import org.fuin.utils4swing.common.ScreenCenterPositioner;
import org.fuin.utils4swing.common.Utils4Swing;

/**
 * A progress panel with two progress bars: One for the number of processed
 * files and a second for the number of transferred bytes for the current file.
 */
public class FileCopyProgressPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_TRANSFER_TEXT = "Transferring file $N of $M...";

    private static final String DEFAULT_TITLE = "File Copy Progress";

    private JPanel panelTitle = null;

    private JPanel panelButtons = null;

    private JPanel panelCenter = null;

    private JLabel labelNofM = null;

    private JProgressBar progressBarNofM = null;

    private JLabel labelSpacer = null;

    private JLabel labelDestFile = null;

    private JProgressBar progressBarFile = null;

    private JLabel labelTitle = null;

    private JButton buttonCancel = null;

    private JLabel labelSourceFile = null;

    private String transferText = DEFAULT_TRANSFER_TEXT;

    private String currentTransferText = transferText;

    private int currentFile = 0;

    private Cancelable cancelable = null;

    private JLabel labelSource = null;

    private JLabel labelDest = null;

    /**
     * No arguments Constructor.
     */
    public FileCopyProgressPanel() {
        super();
        initialize();
    }

    private void initialize() {
        final BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(0);
        borderLayout.setVgap(5);
        this.setLayout(borderLayout);
        this.setSize(600, 320);
        this.setMaximumSize(new Dimension(1600, 400));
        this.setMinimumSize(new Dimension(400, 320));
        this.setPreferredSize(new Dimension(600, 320));
        this.add(getTitlePanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
        this.add(getPanelCenter(), BorderLayout.CENTER);
    }

    private JLabel getLabelTitle() {
        if (labelTitle == null) {
            labelTitle = new JLabel();
            labelTitle.setText(DEFAULT_TITLE);
            labelTitle.setFont(new Font("Dialog", Font.BOLD, 18));
            labelTitle.setName("labelTitle");
        }
        return labelTitle;
    }

    private JPanel getTitlePanel() {
        if (panelTitle == null) {
            final FlowLayout flowLayout1 = new FlowLayout();
            flowLayout1.setVgap(10);
            panelTitle = new JPanel();
            panelTitle.setLayout(flowLayout1);
            panelTitle.setPreferredSize(new Dimension(100, 50));
            panelTitle.setName("panelTitle");
            panelTitle.add(getLabelTitle(), null);
        }
        return panelTitle;
    }

    private JPanel getButtonPanel() {
        if (panelButtons == null) {
            final FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(10);
            flowLayout.setVgap(10);
            panelButtons = new JPanel();
            panelButtons.setLayout(flowLayout);
            panelButtons.setPreferredSize(new Dimension(100, 50));
            panelButtons.setName("buttonPanel");
            panelButtons.add(getButtonCancel(), null);
        }
        return panelButtons;
    }

    private JLabel getLabelSourceFile() {
        if (labelSourceFile == null) {
            labelSourceFile = new JLabel();
            labelSourceFile.setText("http://www.fuin.org/kickstart4j/jnlp/data/demo.jar");
            labelSourceFile.setName("labelSourceFile");
        }
        return labelSourceFile;
    }

    private JLabel getLabelDestFile() {
        if (labelDestFile == null) {
            labelDestFile = new JLabel();
            labelDestFile.setText("C:\\Program Files\\Demo Application\\demo.jar");
            labelDestFile.setName("labelDestFile");
        }
        return labelDestFile;
    }

    private JLabel getLabelSpacer() {
        if (labelSpacer == null) {
            labelSpacer = new JLabel();
            labelSpacer.setText("");
            labelSpacer.setName("labelSpacer");
            labelSpacer.setPreferredSize(new Dimension(38, 16));
        }
        return labelSpacer;
    }

    private JLabel getLabelNofM() {
        if (labelNofM == null) {
            labelNofM = new JLabel();
            labelNofM.setText(transferText);
            labelNofM.setName("labelNofM");
        }
        return labelNofM;
    }

    private JLabel getLabelDest() {
        if (labelDest == null) {
            labelDest = new JLabel();
            labelDest.setText("Destination:");
            labelDest.setName("labelDest");
        }
        return labelDest;
    }

    private JLabel getLabelSource() {
        if (labelSource == null) {
            labelSource = new JLabel();
            labelSource.setText("Source:");
            labelSource.setName("labelSource");
        }
        return labelSource;
    }

    private JPanel getPanelCenter() {
        if (panelCenter == null) {
            final GridLayout gridLayout = new GridLayout();
            gridLayout.setRows(8);
            gridLayout.setHgap(5);
            gridLayout.setVgap(5);
            gridLayout.setColumns(1);
            panelCenter = new JPanel();
            panelCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelCenter.setLayout(gridLayout);
            panelCenter.setName("panelCenter");
            panelCenter.add(getLabelNofM(), null);
            panelCenter.add(getProgressBarNofM(), null);
            panelCenter.add(getLabelSpacer(), null);
            panelCenter.add(getLabelSource(), null);
            panelCenter.add(getLabelSourceFile(), null);
            panelCenter.add(getLabelDest(), null);
            panelCenter.add(getLabelDestFile(), null);
            panelCenter.add(getProgressBarFile(), null);
        }
        return panelCenter;
    }

    private JProgressBar getProgressBarNofM() {
        if (progressBarNofM == null) {
            progressBarNofM = new JProgressBar();
            progressBarNofM.setValue(50);
            progressBarNofM.setName("progressBarNofM");
        }
        return progressBarNofM;
    }

    private JProgressBar getProgressBarFile() {
        if (progressBarFile == null) {
            progressBarFile = new JProgressBar();
            progressBarFile.setName("progressBarFile");
            progressBarFile.setValue(80);
        }
        return progressBarFile;
    }

    private JButton getButtonCancel() {
        if (buttonCancel == null) {
            buttonCancel = new JButton();
            buttonCancel.setName("buttonCancel");
            buttonCancel.setText("Cancel");
            buttonCancel.setMnemonic(KeyEvent.VK_C);
            buttonCancel.setEnabled(false);
            buttonCancel.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    getButtonCancel().setEnabled(false);
                    if (cancelable != null) {
                        cancelable.cancel();
                    }
                }
            });
        }
        return buttonCancel;
    }

    private static String replace(final String src, final String var, final String val) {
        if (src == null) {
            return src;
        }
        if (var == null) {
            return src;
        }
        final int p = src.indexOf(var);
        if (p > -1) {
            return src.substring(0, p) + val + src.substring(p + var.length());
        }
        return src;
    }

    private void updateTransferText() {
        final String text = replace(currentTransferText, "$N", "" + currentFile);
        getLabelNofM().setText(text);
    }

    private void setCurrentFileIntern(final int n) {
        this.currentFile = n;
        updateTransferText();
        getProgressBarNofM().setValue(n);
    }

    private void setMaxFileIntern(final int m) {
        currentTransferText = replace(transferText, "$M", "" + m);
        updateTransferText();
        getProgressBarNofM().setMaximum(m);
    }

    private void setDestFileIntern(final String destFile) {
        getLabelDestFile().setText(destFile);
    }

    private void setSourceFileIntern(final String sourceFile) {
        getLabelSourceFile().setText(sourceFile);
    }

    private void setTitleIntern(final String title) {
        if (title == null) {
            getLabelTitle().setText(DEFAULT_TITLE);
        } else {
            getLabelTitle().setText(title);
        }
    }

    private void setCurrentByteIntern(final int n) {
        getProgressBarFile().setValue(n);
    }

    private void setMaxByteIntern(final int m) {
        getProgressBarFile().setMaximum(m);
    }

    private void setTransferTextIntern(final String transferText) {
        if (transferText == null) {
            this.transferText = DEFAULT_TRANSFER_TEXT;
        } else {
            this.transferText = transferText;
        }
        this.currentTransferText = transferText;
    }

    private void updateFileIntern(final String sourceFile, final String destFile,
            final int currentFile, final int maxByte) {
        setSourceFileIntern(sourceFile);
        setDestFileIntern(destFile);
        setCurrentFileIntern(currentFile);
        setMaxByteIntern(maxByte);
    }

    private void setCancelableIntern(final Cancelable cancelable) {
        this.cancelable = cancelable;
        getButtonCancel().setEnabled(cancelable != null);
    }

    private void setDestTextIntern(final String text) {
        getLabelDest().setText(text);
    }

    private void setSourceTextIntern(final String text) {
        getLabelSource().setText(text);
    }

    /**
     * Set the number of the current file. If called outside the EDT this method
     * will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param n
     *            The N value in "N of M".
     */
    public final void setCurrentFile(final int n) {
        if (SwingUtilities.isEventDispatchThread()) {
            setCurrentFileIntern(n);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setCurrentFileIntern(n);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the number of the max file.If called outside the EDT this method will
     * switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param m
     *            The M value in "N of M".
     */
    public final void setMaxFile(final int m) {
        if (SwingUtilities.isEventDispatchThread()) {
            setMaxFileIntern(m);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setMaxFileIntern(m);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the name of the destination file currently copied.If called outside
     * the EDT this method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param destFile
     *            Filename with full path.
     */
    public final void setDestFile(final String destFile) {
        if (SwingUtilities.isEventDispatchThread()) {
            setDestFileIntern(destFile);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setDestFileIntern(destFile);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Sets the name of the source file currently copied.If called outside the
     * EDT this method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param srcFile
     *            Filename with full path.
     */
    public final void setSourceFile(final String srcFile) {
        if (SwingUtilities.isEventDispatchThread()) {
            setSourceFileIntern(srcFile);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setSourceFileIntern(srcFile);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Sets the title inside the panel. If called outside the EDT this method
     * will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param title
     *            Title.
     */
    public final void setTitle(final String title) {
        if (SwingUtilities.isEventDispatchThread()) {
            setTitleIntern(title);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setTitleIntern(title);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the number of the current byte transferred. If called outside the EDT
     * this method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param n
     *            The N value in "N of M".
     */
    public final void setCurrentByte(final int n) {
        if (SwingUtilities.isEventDispatchThread()) {
            setCurrentByteIntern(n);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setCurrentByteIntern(n);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the number of max bytes transferred.If called outside the EDT this
     * method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param m
     *            The M value in "N of M".
     */
    public final void setMaxByte(final int m) {
        if (SwingUtilities.isEventDispatchThread()) {
            setMaxByteIntern(m);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setMaxByteIntern(m);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the transfer text. Can contain two possible variables: $N = Current
     * file number, $M = Max file count. If called outside the EDT this method
     * will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param transferText
     *            Text to display for file number "N of M".
     */
    public final void setTransferText(final String transferText) {
        if (SwingUtilities.isEventDispatchThread()) {
            setTransferTextIntern(transferText);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setTransferTextIntern(transferText);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the file information for the currently transferred file. If called
     * outside the EDT this method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param sourceFile
     *            Source filename with full path.
     * @param destFile
     *            Destination filename with full path.
     * @param fileNo
     *            Number of the current file.
     * @param maxByte
     *            File size.
     */
    public final void updateFile(final String sourceFile, final String destFile,
            final int fileNo, final int maxByte) {
        if (SwingUtilities.isEventDispatchThread()) {
            updateFileIntern(sourceFile, destFile, fileNo, maxByte);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateFileIntern(sourceFile, destFile, fileNo, maxByte);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Sets the listener to inform about the user cancelling the current
     * transfer.
     * 
     * @param cancelable
     *            Listener or <code>null</code> if cancel is disabled.
     */
    public final void setCancelable(final Cancelable cancelable) {
        if (SwingUtilities.isEventDispatchThread()) {
            setCancelableIntern(cancelable);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setCancelableIntern(cancelable);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the text for the "Destination" label. If called outside the EDT this
     * method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param text
     *            Text for the label.
     */
    public final void setDestText(final String text) {
        if (SwingUtilities.isEventDispatchThread()) {
            setDestTextIntern(text);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setDestTextIntern(text);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    /**
     * Set the text for the "Source" label. If called outside the EDT this
     * method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param text
     *            Text for the label.
     */
    public final void setSourceText(final String text) {
        if (SwingUtilities.isEventDispatchThread()) {
            setSourceTextIntern(text);
        } else {
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setSourceTextIntern(text);
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    private static void ignore() {
        // Dummy method to satisfy Checkstyle's empty block check...
    }
    
    /**
     * Main method to test the panel. Only for testing purposes.
     * 
     * @param args
     *            Not used.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Utils4Swing.initSystemLookAndFeel();
                Utils4Swing.createShowAndPosition("Test Progress Dialog",
                        new FileCopyProgressPanel(), true, new ScreenCenterPositioner());
            }
        });
    }

} // @jve:decl-index=0:visual-constraint="10,10"
