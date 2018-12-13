package com.earnix.eo.gui.correlation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Informative temperature scale panel. Provides visual understanding of correlation by color.
 */
public class TemperatureScalePanel extends JPanel
{
	private static final int GRADIENT_WIDTH = 20;
	private static final int LABELS_COUNT = 10;
	private static final int LABELS_WIDTH = 20;
	private static final int LABELS_MARGIN = 5;
	private static final int FONT_SIZE = 12;

	private final CorrelationMatrix matrix;

	TemperatureScalePanel(CorrelationMatrix matrix)
	{
		this.matrix = matrix;
		setBackground(Color.WHITE);
		setOpaque(false);
	}

	int getDefinedWidth()
	{
		return GRADIENT_WIDTH + LABELS_WIDTH + LABELS_MARGIN * 2;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponent(g);
		
		// drawing gradient rect
		Point2D.Double gradientEnd = new Point2D.Double();
		gradientEnd.setLocation(0, getHeight());
		float[] gradientFractions = new float[] { 0, 0.5f, 1 };
		Color[] gradientColors = new Color[] { matrix.getPositiveColor(), Color.WHITE, matrix.getNegativeColor() };
		Paint paint = new LinearGradientPaint(0, 0, 0, getHeight(), gradientFractions, gradientColors);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, GRADIENT_WIDTH, getHeight());

		// drawing labels
		g2d.setColor(Color.black);
		g2d.setFont(matrix.getFont().deriveFont((float) FONT_SIZE));
		double current = 1.0;
		float step = 2 / (float) LABELS_COUNT;
		int heightStep = getHeight() / LABELS_COUNT;
		g2d.setColor(matrix.getLabelsColor());
		for (int i = 0; i < LABELS_COUNT; i++)
		{
			g2d.drawString(String.format("%.1f", current), GRADIENT_WIDTH + LABELS_MARGIN, i * heightStep);
			current -= step;
		}

	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(getDefinedWidth(), getParent().getHeight());
	}
}
