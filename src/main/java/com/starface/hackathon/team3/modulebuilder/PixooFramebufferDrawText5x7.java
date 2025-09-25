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
        description = "Draw text on Pixoo framebuffer"
)
public class PixooFramebufferDrawText5x7 implements IBaseExecutable
{
    @InputVar(label="Buffer", description="Buffer", type=VariableType.OBJECT)
    public Object BUFF;

    @InputVar(label="X", description="Text X position", type=VariableType.NUMBER)
    public Object POS_X = 0;

    @InputVar(label="Y", description="Text Y position", type=VariableType.NUMBER)
    public Object POS_Y = 0;

    @InputVar(label="Text", description="The text to display", type=VariableType.STRING)
    public Object TEXT = "";

    @InputVar(label="ColorInt", description="Color (int)", type=VariableType.NUMBER)
    public Object RGB_INT = 0;

    @InputVar(label="ColorHex", description="Color (hex string)", type=VariableType.STRING)
    public Object RGB_STR = "";

    @Override
    public void execute(IRuntimeEnvironment context) throws Exception {
        Logger log = context.getLog();
        FrameBuffer fbuff = (FrameBuffer) BUFF;

        int x = ((Double) POS_X).intValue();
        int y = ((Double) POS_Y).intValue();

        String text = TEXT != null ? TEXT.toString() : "";

        int rgb = ((Double) RGB_INT).intValue();
        if (rgb == 0 && RGB_STR != null && !RGB_STR.toString().isEmpty()) {
            rgb = Integer.parseInt(RGB_STR.toString(), 16);
        }

        fbuff.drawText5x7(x, y, text, rgb);
    }
}