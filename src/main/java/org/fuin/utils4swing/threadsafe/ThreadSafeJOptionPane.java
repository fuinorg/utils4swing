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
package org.fuin.utils4swing.threadsafe;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * 1:1 version of <code>JOptionPane</code> for usage outside the EDT. You can
 * use this class if you want to display an option pane in any other thread
 * without violating the "Single Thread Rule".
 */
public final class ThreadSafeJOptionPane {

    /**
     * Private constructor.
     */
    private ThreadSafeJOptionPane() {
        throw new UnsupportedOperationException(
                "This utility class is not intended to be instanciated!");
    }

    private static int execute(final IntOptionPane optionPane) {
        final IntResult result = new IntResult();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    optionPane.show(result);
                }
            });
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
        return result.getResult();
    }

    private static String execute(final StringOptionPane optionPane) {
        final StringResult result = new StringResult();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    optionPane.show(result);
                }
            });
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
        return result.getResult();
    }

    private static Object execute(final ObjectOptionPane optionPane) {
        final ObjectResult result = new ObjectResult();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    optionPane.show(result);
                }
            });
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
        return result.getResult();
    }

    private static void execute(final VoidOptionPane optionPane) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    optionPane.show();
                }
            });
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Brings up a dialog with the options <i>Yes</i>, <i>No</i> and
     * <i>Cancel</i>; with the title, <b>Select an Option</b>.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @return an integer indicating the option selected by the user
     */
    public static int showConfirmDialog(final Component parentComponent, final Object message) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showConfirmDialog(parentComponent, message));
            }
        });

    }

    /**
     * Brings up a dialog where the number of choices is determined by the
     * <code>optionType</code> parameter.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an int designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, <code>YES_NO_CANCEL_OPTION</code>,
     *            or <code>OK_CANCEL_OPTION</code>
     * @return an int indicating the option selected by the user
     */
    public static int showConfirmDialog(final Component parentComponent, final Object message,
            final String title, final int optionType) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showConfirmDialog(parentComponent, message,
                        title, optionType));
            }
        });

    }

    /**
     * Brings up a dialog where the number of choices is determined by the
     * <code>optionType</code> parameter, where the <code>messageType</code>
     * parameter determines the icon to display. The <code>messageType</code>
     * parameter is primarily used to supply a default icon from the Look and
     * Feel.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used.
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an integer designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, <code>YES_NO_CANCEL_OPTION</code>,
     *            or <code>OK_CANCEL_OPTION</code>
     * @param messageType
     *            an integer designating the kind of message this is; primarily
     *            used to determine the icon from the pluggable Look and Feel:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @return an integer indicating the option selected by the user
     */
    public static int showConfirmDialog(final Component parentComponent, final Object message,
            final String title, final int optionType, final int messageType) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showConfirmDialog(parentComponent, message,
                        title, optionType, messageType));
            }
        });

    }

    /**
     * Brings up a dialog with a specified icon, where the number of choices is
     * determined by the <code>optionType</code> parameter. The
     * <code>messageType</code> parameter is primarily used to supply a default
     * icon from the look and feel.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the Object to display
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an int designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, <code>YES_NO_CANCEL_OPTION</code>,
     *            or <code>OK_CANCEL_OPTION</code>
     * @param messageType
     *            an int designating the kind of message this is, primarily used
     *            to determine the icon from the pluggable Look and Feel:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            the icon to display in the dialog
     * @return an int indicating the option selected by the user
     */
    public static int showConfirmDialog(final Component parentComponent, final Object message,
            final String title, final int optionType, final int messageType, final Icon icon) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showConfirmDialog(parentComponent, message,
                        title, optionType, messageType, icon));
            }
        });

    }

    /**
     * Shows a question-message dialog requesting input from the user parented
     * to <code>parentComponent</code>. The dialog is displayed on top of the
     * <code>Component</code>'s frame, and is usually positioned below the
     * <code>Component</code>.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInputDialog(final Component parentComponent, final Object message) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result.setResult(JOptionPane.showInputDialog(parentComponent, message));
            }
        });

    }

    /**
     * Shows a question-message dialog requesting input from the user and
     * parented to <code>parentComponent</code>. The input value will be
     * initialized to <code>initialSelectionValue</code>. The dialog is
     * displayed on top of the <code>Component</code>'s frame, and is usually
     * positioned below the <code>Component</code>.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @param initialSelectionValue
     *            the value used to initialize the input field
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInputDialog(final Component parentComponent,
            final Object message, final Object initialSelectionValue) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result.setResult(JOptionPane.showInputDialog(parentComponent, message,
                        initialSelectionValue));
            }
        });

    }

    /**
     * Shows a dialog requesting input from the user parented to
     * <code>parentComponent</code> with the dialog having the title
     * <code>title</code> and message type <code>messageType</code>.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the <code>String</code> to display in the dialog title bar
     * @param messageType
     *            the type of message that is to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInputDialog(final Component parentComponent,
            final Object message, final String title, final int messageType) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result.setResult(JOptionPane.showInputDialog(parentComponent, message, title,
                        messageType));
            }
        });

    }

    /**
     * Prompts the user for input in a blocking dialog where the initial
     * selection, possible selections, and all other options can be specified.
     * The user will able to choose from <code>selectionValues</code>, where
     * <code>null</code> implies the user can input whatever they wish, usually
     * by means of a <code>JTextField</code>. <code>initialSelectionValue</code>
     * is the initial value to prompt the user with. It is up to the UI to
     * decide how best to represent the <code>selectionValues</code>, but
     * usually a <code>JComboBox</code>, <code>JList</code>, or
     * <code>JTextField</code> will be used.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the <code>String</code> to display in the dialog title bar
     * @param messageType
     *            the type of message to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            the <code>Icon</code> image to display
     * @param selectionValues
     *            an array of <code>Object</code>s that gives the possible
     *            selections
     * @param initialSelectionValue
     *            the value used to initialize the input field
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static Object showInputDialog(final Component parentComponent,
            final Object message, final String title, final int messageType, final Icon icon,
            final Object[] selectionValues, final Object initialSelectionValue) {

        return execute(new ObjectOptionPane() {
            public void show(final ObjectResult result) {
                result.setResult(JOptionPane.showInputDialog(parentComponent, message, title,
                        messageType, icon, selectionValues, initialSelectionValue));
            }
        });

    }

    /**
     * Shows a question-message dialog requesting input from the user. The
     * dialog uses the default frame, which usually means it is centered on the
     * screen.
     * 
     * @param message
     *            the <code>Object</code> to display
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInputDialog(final Object message) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result.setResult(JOptionPane.showInputDialog(message));
            }
        });

    }

    /**
     * Shows a question-message dialog requesting input from the user, with the
     * input value initialized to <code>initialSelectionValue</code>. The dialog
     * uses the default frame, which usually means it is centered on the screen.
     * 
     * @param message
     *            the <code>Object</code> to display
     * @param initialSelectionValue
     *            the value used to initialize the input field
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInputDialog(final Object message,
            final Object initialSelectionValue) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result.setResult(JOptionPane.showInputDialog(message, initialSelectionValue));
            }
        });

    }

    /**
     * Brings up an internal dialog panel with the options <i>Yes</i>, <i>No</i>
     * and <i>Cancel</i>; with the title, <b>Select an Option</b>.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @return an integer indicating the option selected by the user
     */
    public static int showInternalConfirmDialog(final Component parentComponent,
            final Object message) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showInternalConfirmDialog(parentComponent,
                        message));
            }
        });

    }

    /**
     * Brings up a internal dialog panel where the number of choices is
     * determined by the <code>optionType</code> parameter.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the object to display in the dialog; a <code>Component</code>
     *            object is rendered as a <code>Component</code>; a
     *            <code>String</code> object is rendered as a string; other
     *            objects are converted to a <code>String</code> using the
     *            <code>toString</code> method
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an integer designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, or
     *            <code>YES_NO_CANCEL_OPTION</code>
     * @return an integer indicating the option selected by the user
     */
    public static int showInternalConfirmDialog(final Component parentComponent,
            final Object message, final String title, final int optionType) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showInternalConfirmDialog(parentComponent,
                        message, title, optionType));
            }
        });

    }

    /**
     * Brings up an internal dialog panel where the number of choices is
     * determined by the <code>optionType</code> parameter, where the
     * <code>messageType</code> parameter determines the icon to display. The
     * <code>messageType</code> parameter is primarily used to supply a default
     * icon from the Look and Feel.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the object to display in the dialog; a <code>Component</code>
     *            object is rendered as a <code>Component</code>; a
     *            <code>String</code> object is rendered as a string; other
     *            objects are converted to a <code>String</code> using the
     *            <code>toString</code> method
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an integer designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, or
     *            <code>YES_NO_CANCEL_OPTION</code>
     * @param messageType
     *            an integer designating the kind of message this is, primarily
     *            used to determine the icon from the pluggable Look and Feel:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @return an integer indicating the option selected by the user
     */
    public static int showInternalConfirmDialog(final Component parentComponent,
            final Object message, final String title, final int optionType,
            final int messageType) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showInternalConfirmDialog(parentComponent,
                        message, title, optionType, messageType));
            }
        });

    }

    /**
     * Brings up an internal dialog panel with a specified icon, where the
     * number of choices is determined by the <code>optionType</code> parameter.
     * The <code>messageType</code> parameter is primarily used to supply a
     * default icon from the look and feel.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the parentComponent has
     *            no Frame, a default <code>Frame</code> is used
     * @param message
     *            the object to display in the dialog; a <code>Component</code>
     *            object is rendered as a <code>Component</code>; a
     *            <code>String</code> object is rendered as a string; other
     *            objects are converted to a <code>String</code> using the
     *            <code>toString</code> method
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an integer designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, or
     *            <code>YES_NO_CANCEL_OPTION</code>.
     * @param messageType
     *            an integer designating the kind of message this is, primarily
     *            used to determine the icon from the pluggable Look and Feel:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            the icon to display in the dialog
     * @return an integer indicating the option selected by the user
     */
    public static int showInternalConfirmDialog(final Component parentComponent,
            final Object message, final String title, final int optionType,
            final int messageType, final Icon icon) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showInternalConfirmDialog(parentComponent,
                        message, title, optionType, messageType, icon));
            }
        });

    }

    /**
     * Shows an internal question-message dialog requesting input from the user
     * parented to <code>parentComponent</code>. The dialog is displayed in the
     * <code>Component</code>'s frame, and is usually positioned below the
     * <code>Component</code>.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInternalInputDialog(final Component parentComponent,
            final Object message) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result
                        .setResult(JOptionPane.showInternalInputDialog(parentComponent,
                                message));
            }
        });

    }

    /**
     * Shows an internal dialog requesting input from the user parented to
     * <code>parentComponent</code> with the dialog having the title
     * <code>title</code> and message type <code>messageType</code>.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the <code>String</code> to display in the dialog title bar
     * @param messageType
     *            the type of message that is to be displayed: ERROR_MESSAGE,
     *            INFORMATION_MESSAGE, WARNING_MESSAGE, QUESTION_MESSAGE, or
     *            PLAIN_MESSAGE
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static String showInternalInputDialog(final Component parentComponent,
            final Object message, final String title, final int messageType) {

        return execute(new StringOptionPane() {
            public void show(final StringResult result) {
                result.setResult(JOptionPane.showInternalInputDialog(parentComponent, message,
                        title, messageType));
            }
        });

    }

    /**
     * Prompts the user for input in a blocking internal dialog where the
     * initial selection, possible selections, and all other options can be
     * specified. The user will able to choose from <code>selectionValues</code>
     * , where <code>null</code> implies the user can input whatever they wish,
     * usually by means of a <code>JTextField</code>.
     * <code>initialSelectionValue</code> is the initial value to prompt the
     * user with. It is up to the UI to decide how best to represent the
     * <code>selectionValues</code>, but usually a <code>JComboBox</code>,
     * <code>JList</code>, or <code>JTextField</code> will be used.
     * 
     * @param parentComponent
     *            the parent <code>Component</code> for the dialog
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the <code>String</code> to display in the dialog title bar
     * @param messageType
     *            the type of message to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            the <code>Icon</code> image to display
     * @param selectionValues
     *            an array of <code>Objects</code> that gives the possible
     *            selections
     * @param initialSelectionValue
     *            the value used to initialize the input field
     * @return user's input, or <code>null</code> meaning the user canceled the
     *         input
     */
    public static Object showInternalInputDialog(final Component parentComponent,
            final Object message, final String title, final int messageType, final Icon icon,
            final Object[] selectionValues, final Object initialSelectionValue) {

        return execute(new ObjectOptionPane() {
            public void show(final ObjectResult result) {
                result.setResult(JOptionPane.showInternalInputDialog(parentComponent, message,
                        title, messageType, icon, selectionValues, initialSelectionValue));
            }
        });

    }

    /**
     * Brings up an internal confirmation dialog panel. The dialog is a
     * information-message dialog titled "Message".
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the object to display
     */
    public static void showInternalMessageDialog(final Component parentComponent,
            final Object message) {

        execute(new VoidOptionPane() {
            public void show() {
                JOptionPane.showInternalMessageDialog(parentComponent, message);
            }
        });

    }

    /**
     * Brings up an internal dialog panel that displays a message using a
     * default icon determined by the <code>messageType</code> parameter.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param messageType
     *            the type of message to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     */
    public static void showInternalMessageDialog(final Component parentComponent,
            final Object message, final String title, final int messageType) {

        execute(new VoidOptionPane() {
            public void show() {
                JOptionPane.showInternalMessageDialog(parentComponent, message, title,
                        messageType);
            }
        });

    }

    /**
     * Brings up an internal dialog panel displaying a message, specifying all
     * parameters.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param messageType
     *            the type of message to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            an icon to display in the dialog that helps the user identify
     *            the kind of message that is being displayed
     */
    public static void showInternalMessageDialog(final Component parentComponent,
            final Object message, final String title, final int messageType, final Icon icon) {

        execute(new VoidOptionPane() {
            public void show() {
                JOptionPane.showInternalMessageDialog(parentComponent, message, title,
                        messageType, icon);
            }
        });

    }

    /**
     * Brings up an internal dialog panel with a specified icon, where the
     * initial choice is determined by the <code>initialValue</code> parameter
     * and the number of choices is determined by the <code>optionType</code>
     * parameter.
     * <p>
     * If <code>optionType</code> is <code>YES_NO_OPTION</code>, or
     * <code>YES_NO_CANCEL_OPTION</code> and the <code>options</code> parameter
     * is <code>null</code>, then the options are supplied by the Look and Feel.
     * <p>
     * The <code>messageType</code> parameter is primarily used to supply a
     * default icon from the look and feel.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the object to display in the dialog; a <code>Component</code>
     *            object is rendered as a <code>Component</code>; a
     *            <code>String</code> object is rendered as a string. Other
     *            objects are converted to a <code>String</code> using the
     *            <code>toString</code> method
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an integer designating the options available on the dialog:
     *            <code>YES_NO_OPTION</code>, or
     *            <code>YES_NO_CANCEL_OPTION</code>
     * @param messageType
     *            an integer designating the kind of message this is; primarily
     *            used to determine the icon from the pluggable Look and Feel:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            the icon to display in the dialog
     * @param options
     *            an array of objects indicating the possible choices the user
     *            can make; if the objects are components, they are rendered
     *            properly; non-<code>String</code> objects are rendered using
     *            their <code>toString</code> methods; if this parameter is
     *            <code>null</code>, the options are determined by the Look and
     *            Feel
     * @param initialValue
     *            the object that represents the default selection for the
     *            dialog; only meaningful if <code>options</code> is used; can
     *            be <code>null</code>
     * @return an integer indicating the option chosen by the user, or
     *         <code>CLOSED_OPTION</code> if the user closed the Dialog
     */
    // CHECKSTYLE:OFF Maximum parameters disabled
    public static int showInternalOptionDialog(final Component parentComponent,
            final Object message, final String title, final int optionType,
            final int messageType, final Icon icon, final Object[] options,
            final Object initialValue) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showInternalOptionDialog(parentComponent,
                        message, title, optionType, messageType, icon, options, initialValue));
            }
        });

    }
    // CHECKSTYLE:ON

    /**
     * Brings up an information-message dialog titled "Message".
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     */
    public static void showMessageDialog(final Component parentComponent, final Object message) {

        execute(new VoidOptionPane() {
            public void show() {
                JOptionPane.showMessageDialog(parentComponent, message);
            }
        });

    }

    /**
     * Brings up a dialog that displays a message using a default icon
     * determined by the <code>messageType</code> parameter.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param messageType
     *            the type of message to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     */
    public static void showMessageDialog(final Component parentComponent,
            final Object message, final String title, final int messageType) {

        execute(new VoidOptionPane() {
            public void show() {
                JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
            }
        });

    }

    /**
     * Brings up a dialog displaying a message, specifying all parameters.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param messageType
     *            the type of message to be displayed:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            an icon to display in the dialog that helps the user identify
     *            the kind of message that is being displayed
     */
    public static void showMessageDialog(final Component parentComponent,
            final Object message, final String title, final int messageType, final Icon icon) {

        execute(new VoidOptionPane() {
            public void show() {
                JOptionPane.showMessageDialog(parentComponent, message, title, messageType,
                        icon);
            }
        });

    }

    /**
     * Brings up a dialog with a specified icon, where the initial choice is
     * determined by the <code>initialValue</code> parameter and the number of
     * choices is determined by the <code>optionType</code> parameter.
     * <p>
     * If <code>optionType</code> is <code>YES_NO_OPTION</code>, or
     * <code>YES_NO_CANCEL_OPTION</code> and the <code>options</code> parameter
     * is <code>null</code>, then the options are supplied by the look and feel.
     * <p>
     * The <code>messageType</code> parameter is primarily used to supply a
     * default icon from the look and feel.
     * 
     * @param parentComponent
     *            determines the <code>Frame</code> in which the dialog is
     *            displayed; if <code>null</code>, or if the
     *            <code>parentComponent</code> has no <code>Frame</code>, a
     *            default <code>Frame</code> is used
     * @param message
     *            the <code>Object</code> to display
     * @param title
     *            the title string for the dialog
     * @param optionType
     *            an integer designating the options available on the dialog:
     *            <code>DEFAULT_OPTION</code>, <code>YES_NO_OPTION</code>,
     *            <code>YES_NO_CANCEL_OPTION</code>, or
     *            <code>OK_CANCEL_OPTION</code>
     * @param messageType
     *            an integer designating the kind of message this is, primarily
     *            used to determine the icon from the pluggable Look and Feel:
     *            <code>ERROR_MESSAGE</code>, <code>INFORMATION_MESSAGE</code>,
     *            <code>WARNING_MESSAGE</code>, <code>QUESTION_MESSAGE</code>,
     *            or <code>PLAIN_MESSAGE</code>
     * @param icon
     *            the icon to display in the dialog
     * @param options
     *            an array of objects indicating the possible choices the user
     *            can make; if the objects are components, they are rendered
     *            properly; non-<code>String</code> objects are rendered using
     *            their <code>toString</code> methods; if this parameter is
     *            <code>null</code>, the options are determined by the Look and
     *            Feel
     * @param initialValue
     *            the object that represents the default selection for the
     *            dialog; only meaningful if <code>options</code> is used; can
     *            be <code>null</code>
     * @return an integer indicating the option chosen by the user, or
     *         <code>CLOSED_OPTION</code> if the user closed the dialog
     */
    // CHECKSTYLE:OFF Maximum parameters disabled
    public static int showOptionDialog(final Component parentComponent, final Object message,
            final String title, final int optionType, final int messageType, final Icon icon,
            final Object[] options, final Object initialValue) {

        return execute(new IntOptionPane() {
            public void show(final IntResult result) {
                result.setResult(JOptionPane.showOptionDialog(parentComponent, message, title,
                        optionType, messageType, icon, options, initialValue));
            }
        });

    }
    // CHECKSTYLE:ON

    /**
     * Helper class for storing int results.
     */
    private static class IntResult {

        private volatile int result = 0;

        public void setResult(final int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }

    }

    /**
     * Helper class for storing String results.
     */
    private static class StringResult {

        private volatile String result = null;

        public void setResult(final String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

    }

    /**
     * Helper class for storing Object results.
     */
    private static class ObjectResult {

        private volatile Object result = null;

        public void setResult(final Object result) {
            this.result = result;
        }

        public Object getResult() {
            return result;
        }

    }

    /**
     * Helper class for dialogs with int result.
     */
    private static interface IntOptionPane {

        public void show(IntResult result);

    }

    /**
     * Helper class for dialogs with String result.
     */
    private static interface StringOptionPane {

        public void show(StringResult result);

    }

    /**
     * Helper class for dialogs with Object result.
     */
    private static interface ObjectOptionPane {

        public void show(ObjectResult result);

    }

    /**
     * Helper class for dialogs with no result.
     */
    private static interface VoidOptionPane {

        public void show();

    }

}
