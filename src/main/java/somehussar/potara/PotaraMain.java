package somehussar.potara;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import somehussar.potara.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = PotaraMain.MODID, name=PotaraMain.NAME, version=PotaraMain.VERSION, useMetadata = true, acceptableRemoteVersions = "*")
public class PotaraMain {

    public static final String MODID = "potara";
    public static final String NAME = "Potara Earrings";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(PotaraMain.MODID)
    private static PotaraMain instance;
    @SidedProxy(clientSide = "io.github.somehussar.potara.proxy.CommonProxy", serverSide = "io.github.somehussar.potara.proxy.CommonProxy")
    private static CommonProxy proxy;
    private static Logger logger;

    public static PotaraMain getInstance() {
        return instance;
    }

    public static CommonProxy getProxy(){
        return proxy;
    }

    public static Logger getLogger(){
        return logger;
    }


    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent e){
        logger = e.getModLog();
        proxy.onPreInit(e);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent e){
        proxy.onInit(e);
    }

    @Mod.EventHandler
    public void onStarting(FMLServerStartingEvent e) {
        proxy.onStarting(e);
    }

}
