
package com.starface.hackathon.team3.modulebuilder;

import com.starface.hackathon.team3.pixooapi.FrameBuffer;
import com.starface.hackathon.team3.pixooapi.PixooAPI;
import de.vertico.starface.module.core.model.VariableType;
import de.vertico.starface.module.core.model.Visibility;
import de.vertico.starface.module.core.runtime.IBaseExecutable;
import de.vertico.starface.module.core.runtime.IRuntimeEnvironment;
import de.vertico.starface.module.core.runtime.annotations.Function;
import de.vertico.starface.module.core.runtime.annotations.InputVar;
import de.vertico.starface.module.core.runtime.annotations.OutputVar;
import org.apache.logging.log4j.Logger;

@Function(visibility= Visibility.Public, description="Default")
public class PixooSendFramebuffer implements IBaseExecutable
{
    //##########################################################################################

    @InputVar(label="Buffer", description="Buffer",type= VariableType.OBJECT)
    public Object BUFF;

    @InputVar(label="IP", description="IP",type= VariableType.STRING)
    public Object HOST="";

    @InputVar(label="ResetPicId", description="PicId zuruecksetzen",type= VariableType.BOOLEAN)
    public boolean RESET_PICID;

    @InputVar(label="PicId", description="ID der Framesammlung. Muss hochgezaehlt werden",type= VariableType.NUMBER)
    public Double PIC_ID=1.0;

    @InputVar(label="PicNum", description="Anzahl der Frames die insgesamt zur PicId kommen",type= VariableType.NUMBER)
    public Double PIC_NUM=1.0;

    @InputVar(label="PicOffset", description="Framenummer der PicId",type= VariableType.NUMBER)
    public Double PIC_OFFSET=1.0;

    @InputVar(label="PicSpeed", description="Framedauer in ms",type= VariableType.NUMBER)
    public Double PIC_SPEED=1000.0;

    //##########################################################################################

    //################### Code Execution ############################
    @Override
    public void execute(IRuntimeEnvironment context) throws Exception
    {
        Logger log = context.getLog();
        FrameBuffer fbuff = (FrameBuffer) BUFF;

        PixooAPI api = new PixooAPI(HOST.toString(), true);
        if(RESET_PICID) {
            api.resetPicId();
        }

        int picId = PIC_ID.intValue();
        int picNum = PIC_NUM.intValue();
        int picSpeed = PIC_SPEED.intValue();
        int picOffset = PIC_OFFSET.intValue();

        api.sendFrameBuffer(fbuff, picId, picNum, picOffset, picSpeed);


    } //END OF EXECUTION
}