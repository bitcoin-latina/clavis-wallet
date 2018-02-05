package build_structure;

import javafx.scene.control.Alert;
import ui.Global;
import ui.Init;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;

public class Build {
    /**
     * This Class Is Used To Insert Config Files And Binaries
     */
    private static final Logger LOGGER = Logger.getLogger(Build.class.getName());
    private String mainFolder = Global.getPath() + File.separator + "BCL_CL";
    //>> External Paths
    final private String winEthminerBinary = Global.getPath() + File.separator + "ethminer.exe";
    final private String winGenesis = Global.getPath() + File.separator + "genesis.json";//==Mac
    final private String winGethBinary = Global.getPath() + File.separator + "geth.exe";
    final private String winStartCommand = Global.getPath() + File.separator + "start.cmd";
    final private String winGethCommand = Global.getPath() + File.separator + "geth.cmd";
    final private String winEthMinerCommand = Global.getPath() + File.separator + "ethminer.cmd";
    final private String winStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Mac
    //>> External Paths
    final private String macEthminerBinary = Global.getPath() + File.separator + "ethminer";
    final private String macGenesis = Global.getPath() + File.separator + "genesis.json";//==Win
    final private String macGethBinary = Global.getPath() + File.separator + "geth";
    final private String macStartCommand = Global.getPath() + File.separator + "start.command";
    final private String macGethCommand = Global.getPath() + File.separator + "geth.command";
    final private String macEthMinerCommand = Global.getPath() + File.separator + "ethminer.command";
    final private String macStaticNode = Global.getPath() + File.separator + "BCL_Node" + File.separator
            + "geth" + File.separator + "static-nodes.json";//=Win

    public void BCLFolder() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.config("Adding BCL Folder");
        try {
            Path path = Paths.get(mainFolder);
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.warning("COULDN'T CREATE BCL_FOLDER \n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.warning("Attempted creating BCL Folder at " + mainFolder);
            //Create Alert ->
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Build BCL_CL Folder ");
            alert.setContentText("Please Report This Issue W/ Your Log File @ https://discord.gg/kDzP4P8");
            alert.show();
        }
    }

    public void binaries() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.config("Adding Binaries");
        if (Global.getOS().contains("win")) {
            String winGethBinaryRes = "/binaries/windows/geth.exe";
            Utils.export_resource(winGethBinaryRes, winGethBinary);
            LOGGER.config("Windows Geth Added");
            String winGenesisRes = "/json/genesis.json";
            Utils.export_resource(winGenesisRes, winGenesis);
            LOGGER.config("Windows Genesis File Added");
            String winEthminerBinaryRes = "/binaries/windows/ethminer.exe";
            Utils.export_resource(winEthminerBinaryRes, winEthminerBinary);
            LOGGER.config("Windows Ethminer Added");
            String winStaticNodeRes = "/json/static-nodes.json";
            Utils.export_resource(winStaticNodeRes, winStaticNode);
        } else if (Global.getOS().contains("mac")) {
            String macGethBinaryRes = "/binaries/mac/geth";
            Utils.export_resource(macGethBinaryRes, macGethBinary);
            LOGGER.config("Mac Geth Added");
            String macGenesisRes = "/json/genesis.json";
            Utils.export_resource(macGenesisRes, macGenesis);
            LOGGER.config("Mac Genesis File Added");
            String macEthminerBinaryRes = "/binaries/mac/ethminer";
            Utils.export_resource(macEthminerBinaryRes, macEthminerBinary);
            LOGGER.config("Mac Ethminer Added");
            String macStaticNodeRes = "/json/static-nodes.json";
            Utils.export_resource(macStaticNodeRes, macStaticNode);
        }
        //Linux Support Coming...
    }

    public void commands() {
        if (Global.getOS().contains("win")) {
            String winStartCommandRes = "/commands/windows/start.cmd";
            Utils.export_resource(winStartCommandRes, winStartCommand);
            String winGethCommandRes = "/commands/windows/geth.cmd";
            Utils.export_resource(winGethCommandRes, winGethCommand);
            String winEthMinerCommandRes = "/commands/windows/ethminer.cmd";
            Utils.export_resource(winEthMinerCommandRes, winEthMinerCommand);
        } else if (Global.getOS().contains("mac")) {
            String macStartCommandRes = "/commands/mac/start.command";
            Utils.export_resource(macStartCommandRes, macStartCommand);
            String macGethCommandRes = "/commands/mac/geth.command";
            Utils.export_resource(macGethCommandRes, macGethCommand);
            String macEthMinerCommandRes = "/commands/mac/ethminer.command";
            Utils.export_resource(macEthMinerCommandRes, macEthMinerCommand);
        }
        //Linux Support Coming...
    }
}
