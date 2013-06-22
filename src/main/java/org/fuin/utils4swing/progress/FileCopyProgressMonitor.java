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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.fuin.utils4j.Cancelable;
import org.fuin.utils4j.CancelableVolatile;
import org.fuin.utils4swing.common.ScreenCenterPositioner;
import org.fuin.utils4swing.common.Utils4Swing;

/**
 * A class to monitor the progress of a copying a list of files using frame.
 */
public final class FileCopyProgressMonitor implements FileCopyProgressListener {

    private static final String DEFAULT_TRANSFER_TEXT = "Transferring file $N of $M...";

    private static final String DEFAULT_TITLE = "File Copy Progress";

    private static final String DEFAULT_SOURCE_LABEL = "Source:";

    private static final String DEFAULT_DESTINATION_LABEL = "Destination:";

    private final Cancelable cancelable;

    private final String title;

    private final int fileMax;

    private final String transferText;

    private final String srcLabelText;

    private final String destLabelText;

    private JFrame frame = null;

    private FileCopyProgressPanel panel = null;

    private boolean firstTime = true;
    
    private int currentFile = 0;
    
    private int currentByte = 0;

    /**
     * Constructor with basic arguments.
     * 
     * @param cancelable
     *            Someone to notify if the user pressed the "cancel" button - If
     *            <code>null</code> the "cancel" button will be disabled.
     * @param title
     *            Title for the panel and frame - If <code>null</code> a default
     *            title will be used.
     * @param fileMax
     *            Number of files to be copied.
     */
    public FileCopyProgressMonitor(final Cancelable cancelable, final String title,
            final int fileMax) {
        this(cancelable, title, null, null, null, fileMax);
    }

    /**
     * Constructor with basic arguments.
     * 
     * @param cancelable
     *            Someone to notify if the user pressed the "cancel" button - If
     *            <code>null</code> the "cancel" button will be disabled.
     * @param title
     *            Title for the panel and frame - If <code>null</code> a default
     *            title will be used.
     * @param transferText
     *            Text for the "file transfer" (Example:
     *            "Transferring file $N of $M..."). Can contain two possible
     *            variables: $N = Current file number, $M = Max file count. ) -
     *            If <code>null</code> a default text will be used.
     * @param srcLabelText
     *            Text for the "source" label -If <code>null</code> a default
     *            text will be used.
     * @param destLabelText
     *            Text for the "destination" label -If <code>null</code> a
     *            default text will be used.
     * @param fileMax
     *            Number of files to be copied.
     */
    public FileCopyProgressMonitor(final Cancelable cancelable, final String title,
            final String transferText, final String srcLabelText, final String destLabelText,
            final int fileMax) {
        super();

        this.cancelable = cancelable;

        if (title == null) {
            this.title = DEFAULT_TITLE;
        } else {
            this.title = title;
        }

        if (transferText == null) {
            this.transferText = DEFAULT_TRANSFER_TEXT;
        } else {
            this.transferText = transferText;
        }

        if (srcLabelText == null) {
            this.srcLabelText = DEFAULT_SOURCE_LABEL;
        } else {
            this.srcLabelText = srcLabelText;
        }

        if (destLabelText == null) {
            this.destLabelText = DEFAULT_DESTINATION_LABEL;
        } else {
            this.destLabelText = destLabelText;
        }

        this.fileMax = fileMax;

    }

    /**
     * Return the total number of files to be copied.
     * 
     * @return File count.
     */
    public final int getFileMax() {
        return fileMax;
    }

    /**
     * Returns the text for the "file transfer" (Example:
     * "Transferring file $N of $M..."). Can contain two possible variables: $N
     * = Current file number, $M = Max file count. )
     * 
     * @return Text - Always non-null.
     */
    public final String getTransferText() {
        return transferText;
    }

    /**
     * Returns the title for the panel and frame.
     * 
     * @return Title - Always non-null.
     */
    public final String getTitle() {
        return title;
    }

    /**
     * The unit that gets notified when the user presses the "cancel" button -
     * If <code>null</code> the "cancel" button is disabled.
     * 
     * @return Listener or <code>null</code>
     */
    public final Cancelable getCancelable() {
        return cancelable;
    }

    /**
     * Returns the text for the "source" label.
     * 
     * @return Text.
     */
    public final String getSrcLabelText() {
        return srcLabelText;
    }

    /**
     * Returns the text for the "destination" label.
     * 
     * @return Text.
     */
    public final String getDestLabelText() {
        return destLabelText;
    }

    /**
     * Returns the byte counter.
     * 
     * @return Byte number.
     */
    public final int getCurrentByte() {
        return currentByte;
    }

    /**
     * Returns the file counter.
     * 
     * @return File number.
     */
    public final int getCurrentFile() {
        return currentFile;
    }

    
    /**
     * Show the monitor dialog.If called outside the EDT this method will switch
     * to the UI thread using
     * <code>SwingUtilities.invokeAndWait(Runnable)</code>.
     */
    public final void open() {
        if (firstTime) {
            firstTime = false;
            if (SwingUtilities.isEventDispatchThread()) {
                openIntern();
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            openIntern();
                        }
                    });
                } catch (final Exception ex) {
                    ignore();
                }
            }
        } else {
            throw new IllegalStateException("This object cannot be reused!");
        }
    }

    private final void openIntern() {
        panel = new FileCopyProgressPanel();
        panel.setTransferText(transferText);
        panel.setTitle(title);
        currentFile = 0;
        panel.setCurrentFile(0);
        panel.setMaxFile(fileMax);
        currentByte = 0;
        panel.setCurrentByte(0);
        panel.setMaxByte(0);
        panel.setSourceFile("");
        panel.setDestFile("");
        panel.setCancelable(cancelable);
        panel.setDestText(destLabelText);
        panel.setSourceText(srcLabelText);
        frame = Utils4Swing.createShowAndPosition(title, panel, false,
                new ScreenCenterPositioner());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public final void windowClosing(final WindowEvent e) {
                if (cancelable != null) {
                    panel.setCancelable(null);
                    cancelable.cancel();
                }
            }
        });

    }

    /**
     * Close the frame and free all resources. After calling "close" the monitor
     * object cannot be used again!If called outside the EDT this method will
     * switch to the UI thread using
     * <code>SwingUtilities.invokeAndWait(Runnable)</code>.
     */
    public final void close() {
        if (SwingUtilities.isEventDispatchThread()) {
            closeIntern();
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        closeIntern();
                    }
                });
            } catch (final Exception ex) {
                ignore();
            }
        }
    }

    private final void closeIntern() {
        if (frame != null) {
            frame.setVisible(false);
            frame.dispose();
            panel = null;
            frame = null;
        }
    }

    /**
     * Starts a new file being transferred.If called outside the EDT this method
     * will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param sourceFile
     *            Name and path of the source file.
     * @param destFile
     *            Name and path of the destination file.
     * @param currentFile
     *            Number of the current file.
     * @param fileSize
     *            Size of the file.
     */
    public final void updateFile(final String sourceFile, final String destFile,
            final int currentFile, final int fileSize) {
        this.currentFile = currentFile;
        if ((panel != null) && (fileSize >= 10000)) {
            panel.updateFile(sourceFile, destFile, currentFile, fileSize);
        }
    }

    /**
     * Updates the current byte number of the file transferred.If called outside
     * the EDT this method will switch to the UI thread using
     * <code>SwingUtilities.invokeLater(Runnable)</code>.
     * 
     * @param n
     *            Byte number.
     */
    public final void updateByte(final int n) {
        this.currentByte = n;
        if ((panel != null) && (n % 10000 == 0)) {
            panel.setCurrentByte(n);
        }
    }

    /**
     * Returns if the monitor was canceled.
     * 
     * @return If it was canceled <code>true</code> else <code>false</code>.
     */
    public final boolean isCanceled() {
        if (cancelable == null) {
            return false;
        }
        return cancelable.isCanceled();
    }

    private static void ignore() {
        // Dummy method to satisfy Checkstyle's empty block check...
    }

    /**
     * Main method to test the monitor. Only for testing purposes.
     * 
     * @param args
     *            Not used.
     */
    public static void main(final String[] args) {
        
        // This runs in the "main" thread (outside the EDT)
        
        // Initialize the L&F in a thread safe way
        Utils4Swing.initSystemLookAndFeel();

        // Create an cancel tracker
        final Cancelable cancelable = new CancelableVolatile();

        // Create the monitor dialog
        final FileCopyProgressMonitor monitor = new FileCopyProgressMonitor(cancelable,
                "Copy Test", 10);

        // Make the UI visible
        monitor.open();
        try {
            // Dummy loop to simulate copy...
            for (int i = 0; i < 10; i++) {
                // Check if the user canceled the copy process
                if (cancelable.isCanceled()) {
                    break;
                }

                // Create a dummy source and target name...
                final int n = i + 1;
                final String fileName = "file" + n + ".jar";
                final int fileSize = n * 10;
                final String sourceFilename = "http://www.fuin.org/demo-app/" + fileName;
                final String targetFilename = "/program files/demo app/" + fileName;

                // Update the UI with names, count and filesize
                monitor.updateFile(sourceFilename, targetFilename, n, fileSize);

                // Simulate copying a file
                // Normally this would be done with a
                // "FileCopyProgressInputStream"
                for (int j = 0; j < fileSize; j++) {
                    
                    // Update the lower progress bar
                    monitor.updateByte(j + 1);
                    
                    // Sleep to simulate slow copy...
                    sleep(100);
                }
            }
        } finally {
            // Hide the UI
            monitor.close();
        }
        
    }

    private static void sleep(final int ms) {
        try {
            Thread.sleep(ms);
        } catch (final InterruptedException ex) {
            ignore();
        }
    }

}
