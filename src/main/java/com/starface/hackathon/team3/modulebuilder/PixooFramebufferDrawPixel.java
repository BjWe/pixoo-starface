
package com.starface.hackathon.team3.modulebuilder;

import com.starface.hackathon.team3.pixooapi.FrameBuffer;
import de.vertico.starface.module.core.model.VariableType;
import de.vertico.starface.module.core.model.Visibility;
import de.vertico.starface.module.core.runtime.IBaseExecutable;
import de.vertico.starface.module.core.runtime.IRuntimeEnvironment;
import de.vertico.starface.module.core.runtime.annotations.Function;
import de.vertico.starface.module.core.runtime.annotations.InputVar;
import org.apache.logging.log4j.Logger;

@Function(visibility= Visibility.Public, description="Default")
public class PixooFramebufferDrawPixel implements IBaseExecutable
{
    //##########################################################################################

    @InputVar(label="Buffer", description="Buffer",type= VariableType.OBJECT)
    public Object BUFF;

    @InputVar(label="X", description="X Position",type= VariableType.NUMBER)
    public Object POS_X="";

    @InputVar(label="Y", description="Y Position",type= VariableType.NUMBER)
    public Object POS_Y="";

    @InputVar(label="ColorInt", description="Color",type= VariableType.NUMBER)
    public Object RGB_INT = 0;

    @InputVar(label="ColorHex", description="Color",type= VariableType.STRING)
    public Object RGB_STR = "";
    //##########################################################################################

    //################### Code Execution ############################
    @Override
    public void execute(IRuntimeEnvironment context) throws Exception
    {
        Logger log = context.getLog();
        FrameBuffer fbuff = (FrameBuffer) BUFF;

        int x = ((java.lang.Double) POS_X).intValue();
        int y = ((java.lang.Double) POS_Y).intValue();
        int rgb = ((java.lang.Double) RGB_INT).intValue();
        if(rgb == 0){
            rgb = Integer.parseInt(RGB_STR.toString(), 16);
        }


        fbuff.setPixel(x,y,rgb);

    } //END OF EXECUTION
}