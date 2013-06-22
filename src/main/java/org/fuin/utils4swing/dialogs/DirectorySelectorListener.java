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

/**
 * Listener for the result of selecting a directory.
 */
public interface DirectorySelectorListener {

    /**
     * The user canceled the selection.
     */
    public void canceled();

    /**
     * The user finished the selection successfully.
     * 
     * @param directory
     *            Selected directory.
     * @param includeSubdirs
     *            If subdirectories should be included <code>true</code> else
     *            <code>false</code>.
     */
    public void finished(String directory, boolean includeSubdirs);

}
