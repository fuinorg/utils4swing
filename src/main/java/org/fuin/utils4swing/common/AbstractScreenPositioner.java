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

/**
 * Base class for FramePositioner implementations.
 */
public abstract class AbstractScreenPositioner implements FramePositioner {

    private final int widthOffset;

    private final int heightOffset;

    /**
     * Constructor with an offset.
     * 
     * @param widthOffset Width to adjust the horizontal position.
     * @param heightOffset Height to adjust the vertical position.
     */
    public AbstractScreenPositioner(final int widthOffset,
            final int heightOffset) {
        super();
        this.widthOffset = widthOffset;
        this.heightOffset = heightOffset;
    }

    /**
     * Adjusts the width and height of the fraem if it's greater than the
     * screen.
     * 
     * @param screenSize Screen size.
     * @param frameSize Frame size.
     */
    protected final void checkMaxSize(final Dimension screenSize,
            final Dimension frameSize) {
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
    }

    /**
     * Returns the height.
     * 
     * @return Height to adjust the vertical position
     */
    public final int getHeightOffset() {
        return heightOffset;
    }

    /**
     * Returns the width.
     * 
     * @return Width to adjust the horizontal position
     */
    public final int getWidthOffset() {
        return widthOffset;
    }

}
