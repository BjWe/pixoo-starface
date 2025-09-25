package com.starface.hackathon.team3.modulebuilder;

import com.starface.hackathon.team3.pixooapi.FrameBuffer;
import de.vertico.starface.module.core.model.VariableType;
import de.vertico.starface.module.core.model.Visibility;
import de.vertico.starface.module.core.runtime.IBaseExecutable;
import de.vertico.starface.module.core.runtime.IRuntimeEnvironment;
import de.vertico.starface.module.core.runtime.annotations.Function;
import de.vertico.starface.module.core.runtime.annotations.InputVar;
import org.apache.logging.log4j.Logger;

@Function(
        visibility = Visibility.Public,
        description = "Draw a rectangle on Pixoo framebuffer"
)
public class PixooFramebufferDrawRectangle implements IBaseExecutable
{
    @InputVar(label="Buffer", description="Buffer", type=VariableType.OBJECT)
    public Object BUFF;

    @InputVar(label="X", description="Top-left X position", type=VariableType.NUMBER)
    public Object POS_X = 0;

    @InputVar(label="Y", description="Top-left Y position", type=VariableType.NUMBER)
    public Object POS_Y = 0;

    @InputVar(label="Width", description="Rectangle width", type=VariableType.NUMBER)
    public Object WIDTH = 0;

    @InputVar(label="Height", description="Rectangle height", type=VariableType.NUMBER)
    public Object HEIGHT = 0;

    @InputVar(label="ColorInt", description="Color (int)", type=VariableType.NUMBER)
    public Object RGB_INT = 0;

    @InputVar(label="ColorHex", description="Color (hex string)", type=VariableType.STRING)
    public Object RGB_STR = "";

    @InputVar(label="Fill", description="Fill rectangle (true/false)", type=VariableType.BOOLEAN)
    public Object FILL = false;

    @Override
    public void execute(IRuntimeEnvironment context) throws Exception {
        Logger log = context.getLog();
        FrameBuffer fbuff = (FrameBuffer) BUFF;

        int x = ((Double) POS_X).intValue();
        int y = ((Double) POS_Y).intValue();
        int w = ((Double) WIDTH).intValue();
        int h = ((Double) HEIGHT).intValue();

        int rgb = ((Double) RGB_INT).intValue();
        if (rgb == 0 && RGB_STR != null && !RGB_STR.toString().isEmpty()) {
            rgb = Integer.parseInt(RGB_STR.toString(), 16);
        }

        boolean fill = (FILL instanceof Boolean) ? (Boolean) FILL : Boolean.parseBoolean(FILL.toString());

        fbuff.drawRectangle(x, y, w, h, rgb, fill);
    }
}