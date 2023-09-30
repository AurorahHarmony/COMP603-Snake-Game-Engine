package net.scriptronix.snakegame.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import net.scriptronix.snakegame.EngineConfig;
import net.scriptronix.snakegame.world.Scene;

/**
 * Renders the game into a JFrame, using Java Swing
 */
public class SwingRenderer extends JFrame implements IRenderer {
    /** Amount of pixels to scale rendering values */
    final private int UNIT_SCALE_FACTOR = 10;
    
    final private EngineConfig engineConfig;
    final private ArrayList<ISwingRenderable> renderList;

    public SwingRenderer(EngineConfig engineConfig) {
        this.engineConfig = engineConfig;
        this.renderList = new ArrayList<>();
        this.initJFrame();
    }
    
    /**
     * Setup configuration for the JFrame
     */
    private void initJFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(engineConfig.getVirtualWidth(), engineConfig.getVirtualHeight());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
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
        // For every element in the renderlist, draw it
    }


}
