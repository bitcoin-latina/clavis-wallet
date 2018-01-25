package web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import ui.Global;
import utils.Toolkit;

import java.io.File;

public class Setup {
    Web3j web3j;
    Admin geth;
    String macIPC = File.separator + "BCL_Node" + File.separator + "geth.ipc";
    String macIPCFull = Global.getPath() + File.separator + "BCL_Node" + File.separator + "geth.ipc";
    String windowsIPC = "\\\\.\\pipe\\geth.ipc";

    public void setupWeb3() {
        if (Global.getOS().contains("win")) {
            try {
                Thread.sleep(1000);
                Global.setWeb3j(Web3j.build(new WindowsIpcService(windowsIPC)));
                Global.setGeth(Admin.build(new WindowsIpcService(windowsIPC)));
            } catch (Exception e) {
                setupWeb3();
            }
        }
        if (Global.getOS().contains("mac")) {
            while (Toolkit.Is_Empty_File(macIPC)) {
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
                setupWeb3();
            }
        }
    }
}
