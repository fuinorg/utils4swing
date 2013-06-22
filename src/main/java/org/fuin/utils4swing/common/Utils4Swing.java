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
package org.fuin.utils4swing.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.fuin.utils4j.Utils4J;

/**
 * Common utility methods for use in Swing applications and libraries.
 */
public final class Utils4Swing {

    /**
     * Private default constructor.
     */
    private Utils4Swing() {
        throw new UnsupportedOperationException(
                "This utility class is not intended to be instanciated!");
    }

    /**
     * Create a new resizeable frame with a panel as it's content pane and
     * position the frame.
     * 
     * @param title
     *            Frame title.
     * @param content
     *            Content.
     * @param exitOnClose
     *            Exit the program on closing the frame?
     * @param positioner
     *            FramePositioner.
     * 
     * @return A visible frame at the preferred position.
     */
    public static JFrame createShowAndPosition(final String title, final Container content,
            final boolean exitOnClose, final FramePositioner positioner) {
        return createShowAndPosition(title, content, exitOnClose, true, positioner);
    }

    /**
     * 
     * @param title
     *            Frame title.
     * @param content
     *            Content.
     * @param exitOnClose
     *            Exit the program on closing the frame?
     * @param resizable
     *            If the frame should be resizeable TRUE else FALSE.
     * @param positioner
     *            FramePositioner.
     * 
     * @return A visible frame at the preferred position.
     */
    public static JFrame createShowAndPosition(final String title, final Container content,
            final boolean exitOnClose, final boolean resizable,
            final FramePositioner positioner) {

        final JFrame frame = new JFrame(title);
        frame.setContentPane(content);
        if (exitOnClose) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }

        frame.setSize(content.getPreferredSize());

        positioner.position(frame);

        final Insets insets = frame.getInsets();

        frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight()
                + insets.top + insets.bottom);

        frame.setVisible(true);
        frame.setResizable(resizable);

        return frame;

    }

    /**
     * Load an icon located in the same package as a given class.
     * 
     * @param clasz
     *            Class with the same package where the icon is located.
     * @param name
     *            Filename of the icon.
     * 
     * @return New icon instance.
     */
    public static ImageIcon loadIcon(final Class clasz, final String name) {
        final URL url = Utils4J.getResource(clasz, name);
        return new ImageIcon(url);
    }

    /**
     * Initializes the look and feel and wraps exceptions into a runtime
     * exception. It's executed in the calling thread.
     * 
     * @param className
     *            Full qualified name of the look and feel class.
     */
    private static void initLookAndFeelIntern(final String className) {
        try {
            UIManager.setLookAndFeel(className);
        } catch (final Exception e) {
            throw new RuntimeException("Error initializing the Look And Feel!", e);
        }
    }

    /**
     * Initializes the look and feel and wraps exceptions into a runtime
     * exception. If this method is called outside the EDT it will switch
     * automatically to the UI thread using <code>invokeAndWait(Runnable)</code>
     * .
     * 
     * @param className
     *            Full qualified name of the look and feel class.
     */
    public static void initLookAndFeel(final String className) {
        if (SwingUtilities.isEventDispatchThread()) {
            initLookAndFeelIntern(className);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        initLookAndFeelIntern(className);
                    }
                });
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Initializes the system look and feel and wraps exceptions into a runtime
     * exception.If this method is called outside the EDT it will switch
     * automatically to the UI thread using <code>invokeAndWait(Runnable)</code>
     * .
     */
    public static void initSystemLookAndFeel() {
        initLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    /**
     * Find the root pane container in the current hierarchy.
     * 
     * @param source
     *            Component to start with.
     * 
     * @return Root pane container or NULL if it cannot be found.
     */
    public static RootPaneContainer findRootPaneContainer(final Component source) {
        Component comp = source;
        while ((comp != null) && !(comp instanceof RootPaneContainer)) {
            comp = comp.getParent();
        }
        if (comp instanceof RootPaneContainer) {
            return (RootPaneContainer) comp;
        }
        return null;
    }

    /**
     * Makes the glass pane visible and focused and stores the saves the current
     * state.
     * 
     * @param source
     *            Component to use when looking for the root pane container.
     * 
     * @return State of the UI before the glasspane was visible.
     */
    public static GlassPaneState showGlassPane(final Component source) {
        final Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .getFocusOwner();
        final RootPaneContainer rootPaneContainer = findRootPaneContainer(source);
        final Component glassPane = rootPaneContainer.getGlassPane();
        final MouseListener mouseListener = new MouseAdapter() {
        };
        final Cursor cursor = glassPane.getCursor();
        glassPane.addMouseListener(mouseListener);
        glassPane.setVisible(true);
        glassPane.requestFocus();
        glassPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        return new GlassPaneState(glassPane, mouseListener, focusOwner, cursor);
    }

    /**
     * Hides the glass pane and restores the saved state.
     * 
     * @param state
     *            State to restore - Cannot be <code>null</code>.
     */
    public static void hideGlassPane(final GlassPaneState state) {
    	Utils4J.checkNotNull("state", state);
        final Component glassPane = state.getGlassPane();
        glassPane.removeMouseListener(state.getMouseListener());
        glassPane.setCursor(state.getCursor());
        glassPane.setVisible(false);
        if (state.getFocusOwner() != null) {
            state.getFocusOwner().requestFocus();
        }
    }

    /**
     * State of the UI before showing a glass pane.
     */
    public static final class GlassPaneState {

        private final Component glassPane;

        private final MouseListener mouseListener;

        private final Component focusOwner;

        private final Cursor cursor;

        /**
         * Constructor with all data.
         * 
         * @param glassPane
         *            Glass pane to hide.
         * @param mouseListener
         *            Mouse listener to remove from the glass pane.
         * @param focusOwner
         *            Owner of the focus before the glass pane was shown.
         * @param cursor
         *            Cursor of the glass pane before it was shown.
         */
        public GlassPaneState(final Component glassPane, final MouseListener mouseListener,
                final Component focusOwner, final Cursor cursor) {
            super();
            this.glassPane = glassPane;
            this.mouseListener = mouseListener;
            this.focusOwner = focusOwner;
            this.cursor = cursor;
        }

        /**
         * Glass pane.
         * 
         * @return Glass pane to hide.
         */
        public final Component getGlassPane() {
            return glassPane;
        }

        /**
         * Mouse listener to remove from the glass pane.
         * 
         * @return Mouse listener.
         */
        public final MouseListener getMouseListener() {
            return mouseListener;
        }

        /**
         * Owner of the focus before the glass pane was shown.
         * 
         * @return Component to focus.
         */
        public final Component getFocusOwner() {
            return focusOwner;
        }

        /**
         * Cursor of the glass pane before it was shown.
         * 
         * @return Original cursor.
         */
        public final Cursor getCursor() {
            return cursor;
        }

    }

}
