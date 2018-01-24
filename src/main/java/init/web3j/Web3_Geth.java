package init.web3j;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import ui.Global;
import utils.Toolkit;

import java.io.File;

/**
 * @Returns Web3j Geth
 * @Purpose Connect Wallet to Geth by using IPC
 * @Difference This is for admin web3 calls (personal.Unlock)
 */
public class Web3_Geth {
    Admin geth;
    public Admin get_web3() {
        System.out.println("Looking for IPC file...");
        if(Global.getOS().contains("win")){
            System.out.println("Getting IPC...");
            windowsIPC();
        }
        else {
            String ipc_path = Global.getPath() + File.separator + "BCL_Node" + File.separator + "geth.ipc";
            try {
                while (Toolkit.Is_Empty_File(File.separator + "BCL_Node" + File.separator + "geth.ipc")) {
                    Thread.sleep(1000);
                    return Admin.build(new UnixIpcService(ipc_path));
                }
            } catch (Exception e) {
            }
        }
        return geth;
    }
    public void windowsIPC(){
        try{
            System.out.println("Waiting for IPC File");
            geth = Admin.build(new WindowsIpcService("\\\\.\\pipe\\geth.ipc"));
        }catch (Exception e){
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            windowsIPC();
        }
    }
}
