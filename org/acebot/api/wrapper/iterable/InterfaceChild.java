package org.acebot.api.wrapper.iterable;


import java.awt.*;

import org.acebot.api.input.Mouse;
import org.acebot.api.methods.Interfaces;
import org.acebot.api.methods.Menu;
import org.acebot.core.bot.Bot;
import org.acebot.impl.Client;
import org.acebot.impl.InterfaceNode;
import org.acebot.util.HashTable;
import org.acebot.util.Random;

/**
 * @author Timer
 */
public class InterfaceChild {

	/**
	 * The index of this interface in the parent. If this
	 * component does not have a parent component, this
	 * represents the index in the parent interface;
	 * otherwise this represents the component index in
	 * the parent component.
	 */
	private final int index;
	/**
	 * The parent interface containing this component.
	 */
	private final Interface parentInterface;
	/**
	 * The parent component
	 */
	private final InterfaceChild parent;

	/**
	 * Initializes the component.
	 *
	 * @param parent The parent interface.
	 * @param index  The child index of this child.
	 */
	public InterfaceChild(final Interface parent, final int index) {
		parentInterface = parent;
		this.index = index;
		this.parent = null;
	}

	/**
	 * Initializes the component.
	 *
	 * @param parentWidget    The parent interface.
	 * @param parentComponent The parent component.
	 * @param index           The child index of this child.
	 */
	public InterfaceChild(final Interface parentWidget, final InterfaceChild parentComponent, final int index) {
		this.parentInterface = parentWidget;
		this.parent = parentComponent;
		this.index = index;
	}

	/**
	 * Gets the parent widget of this component.  This component may be nested from its parent widget in parent components.
	 *
	 * @return The parent widget.
	 */
	public Interface getParentInterface() {
		return parentInterface;
	}

	/**
	 * Gets the parent component of this component, or null if this is a top-level component.
	 *
	 * @return The parent component, or null.
	 */
	public InterfaceChild getParent() {
		return parent;
	}

	public boolean click() {
		return click(true);
	}

	public boolean click(final boolean left) {
		Mouse.click(Random.nextPoint(getAdjustedBounds()), left);
		return true;
	}

	public int getIndex() {
		return index;
	}

	public int getAbsoluteX() {
		return getAbsoluteLocation().x;
	}

	public int getAbsoluteY() {
		return getAbsoluteLocation().y;
	}

	public Point getAbsoluteLocation() {
		if (getInternal() == null) {
			return new Point(-1, -1);
		}
		final Client client = Bot.getClient();
		final int parentId = getParentId();
		int x = 0, y = 0;
		if (parentId != -1) {
			final Point point = Interfaces.get(parentId >> 0x10, parentId & 0xffff).getAbsoluteLocation();
			x = point.x;
			y = point.y;
		} else {
			final Rectangle[] bounds = client.getInterfaceBounds();
			final int index = getBoundsArrayIndex();
			if (bounds != null && index > 0 && index < bounds.length && bounds[index] != null) {
				return new Point(bounds[index].x, bounds[index].y);
			}
		}
		if (parentId != -1) {
			final InterfaceChild child = Interfaces.getChild(parentId);
			final int horizontalScrollSize = child.getScrollableContentWidth(), verticalScrollSize = child.getScrollableContentHeight();
			if (horizontalScrollSize > 0 || verticalScrollSize > 0) {
				x -= child.getHorizontalScrollPosition();
				y -= child.getVerticalScrollPosition();
			}
		}
		x += getRelativeX();
		y += getRelativeY();
		return new Point(x, y);
	}

	public int getRelativeX() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface.getX();
	}

	public int getRelativeY() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface.getY();
	}

	public Point getRelativeLocation() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		if (iface == null) {
			return new Point(-1, -1);
		}
		return new Point(iface.getX(), iface.getY());
	}

	public int getWidth() {
		if (isInScrollableArea()) {
			return getHorizontalScrollThumbSize();
		}
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface.getWidth();
	}

	public int getHeight() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface.getHeight();
	}

	public int getId() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface.getId();
	}

	public int getChildId() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getChildId() : -1;
	}

	public int getChildIndex() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getComponentIndex() : -1;
	}

	public String getName() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getName() : null;
	}

	public String getText() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? (String) iface.getText() : null;
	}

	public int getHorizontalScrollPosition() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getHorizontalScrollbarPosition() : -1;
	}

	public int getScrollableContentWidth() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getHorizontalScrollbarSize() : -1;
	}

	public int getHorizontalScrollThumbSize() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getHorizontalScrollbarThumbSize() : -1;
	}

	public int getVerticalScrollPosition() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getVerticalScrollbarPosition() : -1;
	}

	public int getScrollableContentHeight() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getVerticalScrollbarSize() : -1;
	}

	public int getVerticalScrollThumbSize() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getVerticalScrollbarThumbSize() : -1;
	}

	public int getBoundsArrayIndex() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getBoundsIndex() : -1;
	}

	public InterfaceChild[] getChildren() {
		final org.acebot.impl.InterfaceChild inter = getInternal();
		if (inter != null) {
			final org.acebot.impl.InterfaceChild[] interfaceComponents = inter.getComponents();
			if (interfaceComponents != null) {
				final InterfaceChild[] components = new InterfaceChild[interfaceComponents.length];
				for (int i = 0; i < components.length; i++) {
					components[i] = new InterfaceChild(parentInterface, this, i);
				}
				return components;
			}
		}
		return new InterfaceChild[0];
	}

	public int getXRotation() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getXRotation() : -1;
	}

	public int getYRotation() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getYRotation() : -1;
	}

	public int getZRotation() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getZRotation() : -1;
	}

	public int getModelZoom() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getModelZoom() : -1;
	}

	public InterfaceChild getChild(final int index) {
		final InterfaceChild[] children = getChildren();
		if (index >= 0 && index < children.length) {
			return children[index];
		}
		return null;
	}

	public int getParentId() {
		final org.acebot.impl.InterfaceChild inter = getInternal();
		if (inter == null) {
			return -1;
		}
		final Client client = Bot.getClient();
		final int parentId = inter.getParentId();
		if (parentId != -1) {
			return parentId;
		}
		final int mainID = getId() >>> 0x10;
		final HashTable ncI = new HashTable(client.getInterfaceNodeCache());
		for (InterfaceNode node = (InterfaceNode) ncI.getFirst(); node != null; node = (InterfaceNode) ncI.getNext()) {
			if (mainID == node.getNodeId()) {
				return (int) (node.getId());
			}
		}
		return -1;
	}

	public boolean isInScrollableArea() {
		if (getParentId() == -1) {
			return false;
		}
		InterfaceChild scrollableArea = Interfaces.getChild(getParentId());
		while (scrollableArea.getScrollableContentHeight() == 0 && scrollableArea.getParentId() != -1) {
			scrollableArea = Interfaces.getChild(scrollableArea.getParentId());
		}
		return scrollableArea.getScrollableContentHeight() != 0;
	}

	private org.acebot.impl.InterfaceChild getInternal() {
		if (parent != null) {
			final org.acebot.impl.InterfaceChild p = parent.getInternal();
			if (p != null) {
				final org.acebot.impl.InterfaceChild[] components = p.getComponents();
				if (components != null && index >= 0 && index < components.length) {
					return components[index];
				}
			}
		} else {
			final org.acebot.impl.InterfaceChild[] children = parentInterface.getChildrenInternal();
			if (children != null && index < children.length) {
				return children[index];
			}
		}
		return null;
	}

	public Rectangle getRealBounds() {
		final Point p = getAbsoluteLocation();
		final int w = getWidth();
		final int h = getHeight();
		return new Rectangle(p.x, p.y, w, h);
	}

	public Rectangle getAdjustedBounds() {
		Rectangle rect = getRealBounds();
		return new Rectangle(rect.x + 2, rect.y + 2, rect.width - 4, rect.height - 4);
	}

	public int getTextureId() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getTextureId() : -1;
	}

	public int getSpecialType() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getSpecialType() : -1;
	}

	public boolean isShowing() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null && iface.isShowing();
	}

	public int getTextColor() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getTextColor() : -1;
	}

	public int getBorderThickness() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getBorderThickness() : -1;
	}

	public int getShadowColor() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getShadowColor() : -1;
	}

	public int getModelType() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getModelType() : -1;
	}

	public int getModelId() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getModelId() : -1;
	}

	public String[] getActions() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return iface != null ? iface.getActions() : null;
	}

	public boolean isVisible() {
		final org.acebot.impl.InterfaceChild iface = getInternal();
		return (iface != null && iface.isVisible());
	}

	public boolean validate() {
		return getInternal() != null && isVisible();
	}

	public boolean interact(String action) {
		return interact(action, null);
	}

	public boolean interact(String action, String option) {
		Mouse.move(Random.nextPoint(getAdjustedBounds()));
		return Menu.select(action, option);
	}
}