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

/**
 * Gets informed about file copy progress.
 */
public interface FileCopyProgressListener {

    /**
     * A new file transfer started.
     * 
     * @param sourceFile
     *            Name and path of the source file.
     * @param destFile
     *            Name and path of the destination file.
     * @param fileNo
     *            Number of the current file.
     * @param fileSize
     *            Size of the file.
     */
    public void updateFile(String sourceFile, String destFile, int fileNo, int fileSize);

    /**
     * Updates the current byte number of the file transferred.
     * 
     * @param n
     *            Byte number.
     */
    public void updateByte(int n);

}
