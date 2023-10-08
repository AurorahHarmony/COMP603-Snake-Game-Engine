package net.scriptronix.snakegame.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.world.Scene;

/**
 * Renders the game into a JFrame, using Java Swing
 */
public class SwingRenderer extends JFrame implements IRenderer {

    /**
     * Amount of pixels to scale rendering values
     */
    final public int UNIT_SCALE_FACTOR = 20;

    final private EngineConfig engineConfig;
    final private ArrayList<ISwingRenderable> renderList;
    final private RenderPanel renderPanel;

    public SwingRenderer(EngineConfig engineConfig) {
        this.engineConfig = engineConfig;
        this.renderList = new ArrayList<>();
        this.initJFrame();

        renderPanel = new RenderPanel(this.renderList);
        this.getContentPane().add(renderPanel);
    }

    /**
     * Setup configuration for the JFrame
     */
    private void initJFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(engineConfig.getVirtualWidth(), engineConfig.getVirtualHeight());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width * UNIT_SCALE_FACTOR, height * UNIT_SCALE_FACTOR);
    }

    @Override
    public void setSize(Dimension d) {
        d.width = d.width * UNIT_SCALE_FACTOR;
        d.height = d.height * UNIT_SCALE_FACTOR;
        super.setSize(d);
    }

    @Override
    public void render(Scene scene) {
        this.refreshRenderList(scene);
        this.renderPanel.repaint();
    }

    /**
     * Updates the renderList, with all objects that are currently renderable.
     *
     * @param scene
     */
    private void refreshRenderList(Scene scene) {
        this.renderList.clear();

        scene.getSceneObjects().forEach((sceneObj) -> {
            if (sceneObj instanceof ISwingRenderable) {
                ISwingRenderable renderable = (ISwingRenderable) sceneObj;
                this.renderList.add(renderable);
            }
        });
    }

    private class RenderPanel extends JPanel {

        final private ArrayList<ISwingRenderable> renderList;

        public RenderPanel(ArrayList<ISwingRenderable> renderList) {
            this.renderList = renderList;
            this.setBackground(Color.BLACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setFont(new Font("Monospaced", Font.BOLD, UNIT_SCALE_FACTOR));

            for (ISwingRenderable renderable : this.renderList) {
                g.setColor(Color.LIGHT_GRAY);
                
                renderable.draw(g, this, UNIT_SCALE_FACTOR);
            }

        }
    }

}
