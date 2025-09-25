
package com.starface.hackathon.team3.modulebuilder;

import com.starface.hackathon.team3.pixooapi.FrameBuffer;
import de.vertico.starface.module.core.model.VariableType;
import de.vertico.starface.module.core.model.Visibility;
import de.vertico.starface.module.core.runtime.IBaseExecutable;
import de.vertico.starface.module.core.runtime.IRuntimeEnvironment;
import de.vertico.starface.module.core.runtime.annotations.Function;
import de.vertico.starface.module.core.runtime.annotations.InputVar;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Function(
        visibility = Visibility.Public,
        description = "Draw a PNG on Pixoo framebuffer"
)
public class PixooFramebufferDrawPNG implements IBaseExecutable
{
    @InputVar(label="Buffer", description="Buffer", type=VariableType.OBJECT)
    public Object BUFF;

    @InputVar(label="X", description="Top-left X position", type=VariableType.NUMBER)
    public Object POS_X = 0;

    @InputVar(label="Y", description="Top-left Y position", type=VariableType.NUMBER)
    public Object POS_Y = 0;

    @InputVar(label="PNG InputStream", description="PNG image data", type=VariableType.INPUT_STREAM)
    public Object PNG_STREAM;

    @Override
    public void execute(IRuntimeEnvironment context) throws Exception {
        Logger log = context.getLog();
        FrameBuffer fbuff = (FrameBuffer) BUFF;

        int x = ((Double) POS_X).intValue();
        int y = ((Double) POS_Y).intValue();

        if (!(PNG_STREAM instanceof InputStream)) {
            throw new IllegalArgumentException("PNG_STREAM must be an InputStream");
        }

        byte[] pngBytes = toByteArray((InputStream) PNG_STREAM);

        fbuff.drawPNG(x, y, pngBytes);
    }

    private static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int nRead;
        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}