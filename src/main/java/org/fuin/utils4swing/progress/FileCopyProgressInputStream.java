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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Updates the attached <code>FileCopyProgressMonitor</code> while bytes are
 * read.
 */
public class FileCopyProgressInputStream extends FilterInputStream {

    private final FileCopyProgressListener listener;

    private int bytesRead = 0;

    private int size = 0;

    /**
     * Constructor with monitor, stream and file size.
     * 
     * @param listener
     *            Listener to inform about progress - Can be <code>null</code>
     *            but makes not much sense to use this class in this case.
     * @param inputStream
     *            Input stream to use.
     * @param size
     *            Size of the input stream.
     */
    public FileCopyProgressInputStream(final FileCopyProgressListener listener,
            final InputStream inputStream, final int size) {
        super(inputStream);
        this.listener = listener;
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    public final int read() throws IOException {
        final int count = in.read();
        if (count >= 0) {
            bytesRead = bytesRead + 1;
            if (listener != null) {
                listener.updateByte(bytesRead);
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    public final int read(final byte[] b) throws IOException {
        final int count = in.read(b);
        if (count > 0) {
            bytesRead = bytesRead + count;
            if (listener != null) {
                listener.updateByte(bytesRead);
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    public final int read(final byte[] b, final int off, final int len) throws IOException {
        final int count = in.read(b, off, len);
        if (count > 0) {
            bytesRead = bytesRead + count;
            if (listener != null) {
                listener.updateByte(bytesRead);
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    public final long skip(final long n) throws IOException {
        final long count = in.skip(n);
        if (count > 0) {
            final long p = bytesRead + count;
            if (p > Integer.MAX_VALUE) {
                bytesRead = size;
            }
            if (listener != null) {
                listener.updateByte(bytesRead);
            }
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    public final void close() throws IOException {
        in.close();
    }

    /**
     * {@inheritDoc}
     */
    public final synchronized void reset() throws IOException {
        in.reset();
        bytesRead = size - in.available();
        if (listener != null) {
            listener.updateByte(bytesRead);
        }
    }

}
