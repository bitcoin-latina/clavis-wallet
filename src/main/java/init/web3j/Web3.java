package init.web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import ui.Global;
import utils.Toolkit;

import java.io.File;

/**
 * @Returns Web3j
 * @Purpose Connect Wallet to Geth by using IPC
 * @Difference This is for regular web3 calls (web3.eth.getBalance())
 */
public class Web3 {
    Web3j web3j;
    public Web3j get_web3() {
        String ipc_path = Global.getPath()+File.separator+"BCL_Node"+File.separator+"geth.ipc";
        //Windows
        if(Global.getOS().contains("win")){
            windowsIPC();
        }
        else {
            while (Toolkit.Is_Empty_File(File.separator + "BCL_Node" + File.separator + "geth.ipc")) {
               //Do Nothing
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
            return Web3j.build(new UnixIpcService(ipc_path));
        }
        return web3j;
    }
    public void windowsIPC(){
        try{
            System.out.println("\\\\.\\pipe\\geth.ipc");
            web3j = Web3j.build(new WindowsIpcService("\\\\.\\pipe\\geth.ipc"));

        }catch (Exception e){
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {}
            windowsIPC();
        }
    }
}
