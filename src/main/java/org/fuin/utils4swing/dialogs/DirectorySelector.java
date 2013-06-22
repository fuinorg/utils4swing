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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.fuin.utils4j.Utils4J;
import org.fuin.utils4swing.common.ScreenCenterPositioner;
import org.fuin.utils4swing.common.Utils4Swing;

/**
 * Helper class for selecting a directory. Can used from a non-EDT thread.
 */
public class DirectorySelector {

	private JFrame frame = null;

	private DirectorySelectionPanel panel = null;

	private boolean firstTime = true;

	private final String title;

	private final DirectorySelectorListener listener;

	private final String directory;

	private final boolean showIncludeSubdirs;

	/**
	 * Constructor with title and start directory. The "include sub directories"
	 * checkbox is not visible.
	 * 
	 * @param title
	 *            Title of the frame and panel.
	 * @param directory
	 *            Start directory.
	 * @param listener
	 *            Listener to inform about the result.
	 */
	public DirectorySelector(final String title, final String directory,
			final DirectorySelectorListener listener) {
		this(title, directory, false, listener);
	}

	/**
	 * Constructor with title and start directory.
	 * 
	 * @param title
	 *            Title of the frame and panel.
	 * @param directory
	 *            Start directory.
	 * @param showIncludeSubdirs
	 *            If the checkbox "include subdirectories" should be visible
	 *            <code>true</code> else <code>false</code>.
	 * @param listener
	 *            Listener to inform about the result.
	 */
	public DirectorySelector(final String title, final String directory,
			final boolean showIncludeSubdirs,
			final DirectorySelectorListener listener) {
		super();

		Utils4J.checkNotNull("title", title);
		this.title = title;

		Utils4J.checkNotNull("directory", directory);
		this.directory = directory;

		this.showIncludeSubdirs = showIncludeSubdirs;

		Utils4J.checkNotNull("listener", listener);
		this.listener = listener;

	}

	/**
	 * Show the monitor dialog.If called outside the EDT this method will switch
	 * to the UI thread using
	 * <code>SwingUtilities.invokeAndWait(Runnable)</code>.
	 */
	public final void show() {
		if (firstTime) {
			firstTime = false;
			if (SwingUtilities.isEventDispatchThread()) {
				showIntern();
			} else {
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						public void run() {
							showIntern();
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

	private final void showIntern() {
		panel = new DirectorySelectionPanel(this);
		panel.setTitle(title);
		panel.setDirectory(directory);
		panel.setIncludeSubdirsVisible(showIncludeSubdirs);
		frame = Utils4Swing.createShowAndPosition(title, panel, false,
				new ScreenCenterPositioner());
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.getRootPane().setDefaultButton(panel.getButtonOK());
		frame.addWindowListener(new WindowAdapter() {
			public final void windowClosing(final WindowEvent e) {
				cancel();
			}
		});
	}

	/**
	 * Close the frame and free all resources. After calling "close" the monitor
	 * object cannot be used again!If called outside the EDT this method will
	 * switch to the UI thread using
	 * <code>SwingUtilities.invokeAndWait(Runnable)</code>.
	 */
	protected final void close() {
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
	 * The user canceled the dialog.
	 */
	protected final void cancel() {
		close();
		listener.canceled();
	}

	/**
	 * The dialog was successfully finished.
	 */
	protected final void ok() {
		final String dir = panel.getDirectory();
		final boolean include = panel.isIncludeSubdirs();
		close();
		listener.finished(dir, include);
	}

	private static void ignore() {
		// Dummy method to satisfy Checkstyle's empty block check...
	}

	/**
	 * Blocks the current thread and waits for the result of the selection
	 * dialog. WARNING: Don't use this inside the Event Dispatching Thread
	 * (EDT)! This is only for usage outside the UI thread. The checkbox
	 * "include subdirectories" is not visible.
	 * 
	 * @param title
	 *            Title to display for the dialog.
	 * @param directory
	 *            Initial directory.
	 * 
	 * @return Selection result.
	 * 
	 * @throws CanceledException
	 *             The user canceled the dialog.
	 */
	public static Result selectDirectory(final String title,
			final String directory) throws CanceledException {
		return selectDirectory(title, directory, false);
	}

	/**
	 * Blocks the current thread and waits for the result of the selection
	 * dialog. WARNING: Don't use this inside the Event Dispatching Thread
	 * (EDT)! This is only for usage outside the UI thread.
	 * 
	 * @param title
	 *            Title to display for the dialog.
	 * @param directory
	 *            Initial directory.
	 * @param showIncludeSubdirs
	 *            If the checkbox "include subdirectories" should be visible
	 *            <code>true</code> else <code>false</code>.
	 * 
	 * @return Selection result.
	 * 
	 * @throws CanceledException
	 *             The user canceled the dialog.
	 */
	public static Result selectDirectory(final String title,
			final String directory, final boolean showIncludeSubdirs)
			throws CanceledException {

		/**
		 * Gets informed about the result.
		 */
		class MyListener implements DirectorySelectorListener {

			private volatile String directory = null;

			private volatile boolean includeSubdirs = false;

			/**
			 * Selected directory.
			 * 
			 * @return Directory.
			 */
			public String getDirectory() {
				return directory;
			}

			/**
			 * Returns if sub directories should be included.
			 * 
			 * @return If sub directories are included <code>true</code> else
			 *         <code>false</code>.
			 */
			public boolean isIncludeSubdirs() {
				return includeSubdirs;
			}

			/**
			 * {@inheritDoc}
			 */
			public void canceled() {
				this.directory = "";
			}

			/**
			 * {@inheritDoc}
			 */
			public void finished(final String directory,
					final boolean includeSubdirs) {
				this.directory = directory;
				this.includeSubdirs = includeSubdirs;
			}
		}

		final MyListener myListener = new MyListener();
		new DirectorySelector(title, directory, showIncludeSubdirs, myListener)
				.show();

		// Wait for the result
		while (myListener.getDirectory() == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				ignore();
			}
		}

		if (myListener.getDirectory().length() == 0) {
			throw new CanceledException();
		}

		return new Result(myListener.getDirectory(), myListener
				.isIncludeSubdirs());

	}

	/**
	 * Immutable directory selection result.
	 */
	public static final class Result {

		private final String directory;

		private final boolean includeSubdirs;

		/**
		 * Constructor with directory and subdirectory inclusion.
		 * 
		 * @param directory
		 *            Selected directory.
		 * @param includeSubdirs
		 *            If sub directories are included <code>true</code> else
		 *            <code>false</code>.
		 */
		public Result(final String directory, final boolean includeSubdirs) {
			Utils4J.checkNotNull("directory", directory);
			this.directory = directory;
			this.includeSubdirs = includeSubdirs;
		}

		/**
		 * Selected directory.
		 * 
		 * @return Directory.
		 */
		public final String getDirectory() {
			return directory;
		}

		/**
		 * Returns if sub directories should be included.
		 * 
		 * @return If sub directories are included <code>true</code> else
		 *         <code>false</code>.
		 */
		public final boolean isIncludeSubdirs() {
			return includeSubdirs;
		}

	}

	/**
	 * Starts the selector for a test.
	 * 
	 * @param args
	 *            Not used.
	 * 
	 * @throws IOException
	 *             Error creating the canonical path for the current directory.
	 */
	public static void main(final String[] args) throws IOException {
		Utils4Swing.initSystemLookAndFeel();
		DirectorySelector.Result result;
		try {
			result = DirectorySelector.selectDirectory(
					"Please select the destination directory:", new File(".")
							.getCanonicalPath(), true);
			System.out.println("SELECTED=" + result.getDirectory()
					+ ", SUBDIRS=" + result.isIncludeSubdirs());
		} catch (CanceledException e) {
			System.out.println("CANCELED!");
		}
	}

}
