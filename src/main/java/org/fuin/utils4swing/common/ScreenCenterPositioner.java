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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Centers a frame on the Desktop.
 */
public class ScreenCenterPositioner extends AbstractScreenPositioner {

    /**
     * Default constructor.
     */
    public ScreenCenterPositioner() {
        super(0, 0);
    }

    /**
     * Constructor with an offset.
     * 
     * @param widthOffset Width to adjust the horizontal position.
     * @param heightOffset Height to adjust the vertical position.
     */
    public ScreenCenterPositioner(final int widthOffset, final int heightOffset) {
        super(widthOffset, heightOffset);
    }

    /**
     * {@inheritDoc}
     */
    public final void position(final JFrame frame) {
        final Dimension screenSize = Toolkit.getDefaultToolkit()
                .getScreenSize();
        final Dimension frameSize = frame.getSize();
        checkMaxSize(screenSize, frameSize);
        frame.setLocation(
                (screenSize.width - frameSize.width - getWidthOffset()) / 2,
                (screenSize.height - frameSize.height - getHeightOffset()) / 2);
    }

}
