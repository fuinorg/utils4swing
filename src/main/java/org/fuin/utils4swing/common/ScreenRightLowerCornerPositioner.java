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
 * Move a frame to the lower right corner of the desktop.
 */
public final class ScreenRightLowerCornerPositioner extends
        AbstractScreenPositioner {

    /**
     * Default constructor.
     */
    public ScreenRightLowerCornerPositioner() {
        super(0, 0);
    }

    /**
     * Constructor with an offset.
     * 
     * @param widthOffset Width to adjust the horizontal position.
     * @param heightOffset Height to adjust the vertical position.
     */
    public ScreenRightLowerCornerPositioner(final int widthOffset, final int heightOffset) {
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
                (screenSize.width - frameSize.width - getWidthOffset()),
                (screenSize.height - frameSize.height - getHeightOffset()));
    }

}
