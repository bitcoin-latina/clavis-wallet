package web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import ui.Global;
import utils.Utils;

import java.io.File;
import java.util.logging.Logger;

public class Setup {
    private String macIPC = File.separator + "BCL_Node" + File.separator + "geth.ipc";
    private String macIPCFull = Global.getPath() + File.separator + "BCL_Node" + File.separator + "geth.ipc";
    private String windowsIPC = "\\\\.\\pipe\\geth.ipc";
    private static final Logger LOGGER = Logger.getLogger(Setup.class.getName());

    public void setupWeb3() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Setting Up WEB3J");
        if (Global.getOS().contains("win")) {
            try {
                Thread.sleep(1000);
                Global.setWeb3j(Web3j.build(new WindowsIpcService(windowsIPC)));
                Global.setGeth(Admin.build(new WindowsIpcService(windowsIPC)));
            } catch (Exception e) {
                LOGGER.warning("UNABLE TO FIND IPC FILE @ " + windowsIPC);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                setupWeb3();
            }
        }
        if (Global.getOS().contains("mac")) {
            while (Utils.Is_Empty_File(macIPC)) {
                //Do Nothing But Sleep
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Global.setWeb3j(Web3j.build(new UnixIpcService(macIPCFull)));
                Global.setGeth(Admin.build(new UnixIpcService(macIPCFull)));
            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                LOGGER.warning("UNABLE TO FIND IPC FILE @ " + macIPCFull);
                setupWeb3();
            }
        }
    }
}
