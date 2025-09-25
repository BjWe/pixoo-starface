package com.starface.hackathon.team3.modulebuilder;

import com.starface.hackathon.team3.pixooapi.FrameBuffer;
import de.vertico.starface.module.core.model.VariableType;
import de.vertico.starface.module.core.model.Visibility;
import de.vertico.starface.module.core.runtime.IBaseExecutable;
import de.vertico.starface.module.core.runtime.IRuntimeEnvironment;
import de.vertico.starface.module.core.runtime.annotations.Function;
import de.vertico.starface.module.core.runtime.annotations.OutputVar;
import org.apache.logging.log4j.Logger;

@Function(visibility=Visibility.Public, description="Default")
public class Pixoo64FrameBuffer implements IBaseExecutable
{
    //##########################################################################################

    @OutputVar(label="PixooBuffer", description="PixooBuffer",type=VariableType.OBJECT)
    public Object BUFF="";
    //##########################################################################################

    //################### Code Execution ############################
    @Override
    public void execute(IRuntimeEnvironment context) throws Exception
    {
        Logger log = context.getLog();
        FrameBuffer fbuff = new FrameBuffer(64,64);
        log.debug("Created FB");
        BUFF = fbuff;
    } //END OF EXECUTION
}